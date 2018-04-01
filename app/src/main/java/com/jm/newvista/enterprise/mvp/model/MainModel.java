package com.jm.newvista.enterprise.mvp.model;

import com.jm.newvista.enterprise.bean.CustomerOrderEntity;
import com.jm.newvista.enterprise.mvp.base.BaseModel;
import com.jm.newvista.enterprise.util.NetworkUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainModel extends BaseModel {
    private MyOkHttp myOkHttp = NetworkUtil.myOkHttp;

    public void postOrderInfo(CustomerOrderEntity orderEntity, PostOrderInfoListener postOrderInfoListener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("orderOperation", "use");
        params.put("userId", String.valueOf(orderEntity.getUserId()));

        Date date = orderEntity.getOrderDatetime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        params.put("orderDatetime", dateStr);

        myOkHttp.post().url(NetworkUtil.ORDER_URL).params(params).tag(this).enqueue(new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                postOrderInfoListener.onSuccess();
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                postOrderInfoListener.onFailure(error_msg);
            }
        });
    }

    @Override
    public void cancel() {
        myOkHttp.cancel(this);
    }

    public interface PostOrderInfoListener {
        void onSuccess();

        void onFailure(String errorMessage);
    }
}
