package com.ling.suandashi.base;

import android.content.Context;

import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.data.request.tools.APIException;
import com.ling.suandashi.data.request.tools.ErrorTools;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.data.request.tools.ResponseListener;

/**
 * @author Imxu
 * @time 2019/7/27 11:51
 * @des ${TODO}
 */
public abstract class DefaultListener implements ResponseListener {

    private final Context mContext;

    public DefaultListener(Context context) {
        mContext = context;
    }

    @Override
    public void onFailure(RequestData params, RequestResult result) {
        ErrorTools.doError(mContext,params,result.getMessage(),result.getStatus());
    }

    @Override
    public void onNetworkError(APIException error) {
        ErrorTools.doNetError(mContext,error);
    }
}
