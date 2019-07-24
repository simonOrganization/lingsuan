package com.ling.suandashi.net;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.ling.suandashi.BuildConfig;
import com.ling.suandashi.data.UserSession;
import com.ling.suandashi.data.request.tools.APIException;
import com.ling.suandashi.data.request.tools.HttpException;
import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.data.request.tools.ResponseListener;
import com.ling.suandashi.tools.AesUtil;
import com.ling.suandashi.view.ZProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Imxu
 * @time 2019/6/30 14:35
 * @des ${TODO}
 */
public class HttpRequestUtils {

    private Context mContext;
    private RequestData params;
    private ResponseListener responseListener;

    /*
    传递控制组件
     */
    private View mButton; //禁用按钮

    /**
     * 设置默认参数
     */
    private Boolean showLoader = true;

    /*
    实例化组件
     */
    private ZProgressDialog zProgressDialog;

    private RetrofitTools mTools;
    private BaseSubscriber<Response<ResponseBody>> mSubscriber;

    public HttpRequestUtils(Context context, RequestData params, ResponseListener responseListener) {
        this.mContext = context;
        this.params = params;
        this.responseListener = responseListener;
        /*
         * 实例化加载进度条
         */
        if (mContext != null && zProgressDialog == null && mContext instanceof Activity) {
            zProgressDialog = new ZProgressDialog(mContext);
        }

        if(mSubscriber == null){
            mSubscriber = new BaseSubscriber<Response<ResponseBody>>() {
                @Override
                public void onNext(Response<ResponseBody> response) {
                    dismissLoading();
                    if(response.body() != null){
                        RequestResult res = null;
                        try {
                            String json = response.body().string();
                            res = parseResponse(json);
                            if(responseListener != null){
                                if (res.getStatus() == 200) {
                                    responseListener.onResponse(params.getResponse(res));

                                } else {
                                    responseListener.onFailure(params, res);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            dismissLoading();
                            if (mButton != null) {
                                mButton.setEnabled(true);
                            }
                        }
                    }else {
                        APIException apiException = null;
                        try {
                            String errorStr = response.errorBody().string();
                            apiException = new APIException(new HttpException(response.code(), errorStr), params.getApiCode());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(responseListener != null){
                            responseListener.onNetworkError(apiException);
                        }
                    }
                }

                @Override
                public void onError(Throwable ex) {
                    super.onError(ex);
//                    ((retrofit2.HttpException)ex).response().raw().receivedResponseAtMillis();//请求结束时间
                    ex.printStackTrace();
                    dismissLoading();
                    if (mButton != null) {
                        mButton.setEnabled(true);
                    }
                    Integer errorCode = 0;
                    if (ex instanceof HttpException) {
                        try {
                            errorCode = Integer.parseInt(((HttpException) ex).getErrorCode());
                        } catch (Exception e) {
                        }
                    }
                    APIException apiException = new APIException(new HttpException(errorCode, ex.getMessage()), params.getApiCode());
                    if(responseListener != null){
                        responseListener.onNetworkError(apiException);
                    }
                }
            };
        }
    }


    public void execute(){
        if (mContext == null) {
            //主窗体不存在，终止执行
            return;
        }
        if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
            return;
        }
        if (mButton != null) {
            mButton.setEnabled(false);
        }
        if (mContext instanceof Activity && showLoader) {
            zProgressDialog.show();
        }

        getRetrofiTools();

        if(params.getRequestMethod().equals(RetrofitTools.RequestType.BODY)){
            mTools.json(params.getUrl(),params.getBody(),mSubscriber);
        }else {
            RetrofitRequest request = null;
            if(params.getRequestMethod().equals(RetrofitTools.RequestType.GET)){
                request = new RetrofitRequest.Builder().addParams(params.getParams()).get().url(params.getUrl()).build();
            }else if(params.getRequestMethod().equals(RetrofitTools.RequestType.POST)){
                request = new RetrofitRequest.Builder().post(params.getParams()).url(params.getUrl()).build();
            }
            mTools.executResponse(request, mSubscriber);
        }

    }

    private void getRetrofiTools() {
        if (mTools == null) {
            Map<String, String> headers = new HashMap<>();
            headers.put("ak", UserSession.getInstances(mContext).getValue("session", "")); //accessKey,访问设备key
            headers.put("ut", UserSession.getInstances(mContext).getValue("token", "")); //token,用户token
            headers.put("ts", String.valueOf(System.currentTimeMillis())); //timestamp,时间戳
            headers.put("osVersion", Build.VERSION.RELEASE);//版本号
            headers.put("Connection", "close");

            mTools = new RetrofitTools.Builder(mContext)
                    .connectTimeout(40)
                    .writeTimeout(40)
                    .addHeader(headers)
                    .baseUrl(BuildConfig.API_SERVER_URL)
                    .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))//默认5个线程池
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addLog(false)//默认false
                    .build();
        }
    }

    public void setmButton(View mButton) {
        this.mButton = mButton;
    }

    public void setShowLoader(Boolean showLoader) {
        this.showLoader = showLoader;
    }

    public RequestData getParams() {
        return params;
    }

    private void dismissLoading() {
        try {
            if (mContext != null && mContext instanceof Activity
                    && !((Activity) mContext).isFinishing()
                    && zProgressDialog != null && zProgressDialog.isShowing()) {
                zProgressDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 解析返回Java接口返回值
     */
    public RequestResult parseResponse(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        RequestResult requestResult = new RequestResult();
        requestResult.setStatus(jsonObject.optInt("code"));
        String data = AesUtil.decrypt(jsonObject.optString("data"),"G$B#SN39T@18JCZR","0123456789");
        try {
            Log.e("--------------","----data----=="+data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestResult.setData(jsonObject.optString("data"));
        requestResult.setMessage(jsonObject.optString("msg"));
        return requestResult;
    }
}
