package com.ling.suandashi.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ling.suandashi.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

/**
 * 自定义安全请求webView
 */
public class ZWebView extends WebView {

    public static final String TAG = "ZWebView";
    private ZProgressDialog zProgressDialog;
    private Context mContext;
    private boolean firstShow = false;

    class RendererTrackingWebViewClient extends WebViewClient {
        WebView mWebView;

        public RendererTrackingWebViewClient(WebView mWebView) {
            this.mWebView = mWebView;
        }

        @Override
        @TargetApi(Build.VERSION_CODES.O)
        public boolean onRenderProcessGone(WebView view,
                                           RenderProcessGoneDetail detail) {
            if (!detail.didCrash()) {
                // Renderer was killed because the system ran out of memory.
                // The app can recover gracefully by creating a new WebView instance
                // in the foreground.
                if (mWebView != null) {
                    ViewParent viewParent =  mWebView.getParent();
                    if(viewParent!=null && view instanceof ViewGroup){
                        ViewGroup viewGroup = (ViewGroup)viewParent;
                        viewGroup.removeView(mWebView);
                        mWebView.destroy();
                        mWebView = null;
                    }
                }
                return true; // The app continues executing.
            }
            return false;
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.isRedirect()) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
            return false;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.O) {
                view.loadUrl(url);
                return true;
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if(!firstShow){
                zProgressDialog.show();
                firstShow = true;
            }
        }

        @Override
        public void onPageFinished(final WebView view, String url) {
            super.onPageFinished(view, url);
            if (mContext != null && mContext instanceof Activity
                    && !((Activity) mContext).isFinishing()
                    && zProgressDialog != null && zProgressDialog.isShowing()) {
                zProgressDialog.dismiss();
            }
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }
    };

    OnKeyListener onKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    userGoBack();
                    return true;
                }
            }
            return false;
        }
    };


    public void userGoBack(){
        if (canGoBack()) {
            goBack();
        }else {
            if (mListener != null)
                mListener.webFinish();
        }
    }

    WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //不重写WebChromeClient则调用对象不起作用
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, final String title) {
            post(new Runnable() {
                @Override
                public void run() {
                    if (mListener != null) {
                        mListener.initTitle(title);
                    }
                }
            });
        }

        @Override
        public boolean onJsAlert(WebView view, String url, final String message, final JsResult result) {
            return false;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, final String message, final JsResult result) {
            return true;
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
        }


    };

    public ZWebView(Context context) {
        super(context);
        mContext = context;
        if (isInEditMode()) {
            return;
        }
        setDefaultSetting();
    }

    public ZWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (isInEditMode()) {
            return;
        }
        setDefaultSetting();
    }

    public ZWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (isInEditMode()) {
            return;
        }
        setDefaultSetting();
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
    }

    /**
     * Setting default
     */
    javaObj javaObj;

    private void setDefaultSetting() {
        setOnKeyListener(onKeyListener);
        setWebViewClient(new RendererTrackingWebViewClient(this));
        setWebChromeClient(webChromeClient);
        setBackgroundColor(0x00000000);

        if (mContext != null && zProgressDialog == null && mContext instanceof Activity) {
            zProgressDialog = new ZProgressDialog(mContext);
        }

        javaObj = new javaObj();
        WebSettings setting = getSettings();
        setting.setDefaultTextEncodingName("UTF-8");
        setting.setJavaScriptEnabled(true);
        addJavascriptInterface(javaObj, "javaObj");
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setAllowFileAccess(true);// 设置允许访问文件数据
        setting.setGeolocationEnabled(true); //启用地理定位
        setting.setSupportZoom(true); //支持缩放,缩放前提
        setting.setBuiltInZoomControls(true); //开启控件缩放
        setting.setDisplayZoomControls(false); //隐藏原生缩放控件
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        setting.setDomStorageEnabled(true); //开启DomStorage缓存
        setting.setDatabaseEnabled(true); //启用数据库

        setting.setUseWideViewPort(true);//推荐使用的窗口
        setting.setLoadWithOverviewMode(true);//加载的页面的模式

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    //请求代理模式===========================================================
    class javaObj {
        @JavascriptInterface
        public void doAction(final String jsonObj) {
            Log.i(TAG, "docation:" + jsonObj);
            post(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "docation: run");
                }
            });
        }
    }
    private  WebViewBackListener mListener;
    public void setWebViewBackListener(WebViewBackListener listener){
        mListener = listener;
    }
    public interface WebViewBackListener{
        void webFinish();
        void initTitle(String title);
        void webFininshAll(String url);
    }
    public static class WebViewBackListenerImpl implements WebViewBackListener{
        @Override
        public void webFinish() {
            //Do nothing
        }

        @Override
        public void initTitle(String title) {

        }

        @Override
        public void webFininshAll(String url) {
            //Do nothing
        }
    }
}