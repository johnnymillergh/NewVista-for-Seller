package com.jm.newvista.enterprise.mvp.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jm.newvista.enterprise.bean.CustomerOrderEntity;
import com.jm.newvista.enterprise.mvp.base.BasePresenter;
import com.jm.newvista.enterprise.mvp.model.MainModel;
import com.jm.newvista.enterprise.mvp.view.MainView;

public class MainPresenter extends BasePresenter<MainModel, MainView> {
    private MainModel mainModel;
    private MainView mainView;

    public MainPresenter() {
        this.mainModel = new MainModel();
        super.BasePresenter(mainModel);
    }

    public void checkIn() {
        mainView = getView();

        String decodedText = mainView.onGetDecodedText();
        CustomerOrderEntity orderEntity = null;
        try {
            orderEntity = new Gson().fromJson(decodedText, CustomerOrderEntity.class);

            if (!orderEntity.getIsUsed()) {
                mainModel.postOrderInfo(orderEntity, new MainModel.PostOrderInfoListener() {
                    @Override
                    public void onSuccess() {
                        mainView.onMakeToast("Welcome to NewVista");
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        mainView.onMakeToast(errorMessage);
                    }
                });
            } else {
                mainView.onMakeToast("User ID: " + orderEntity.getUserId()
                        + "\nOrder datetime: " + orderEntity.getOrderDatetime()
                        + "\nWARNING: Ticket has been used.");
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            mainView.onMakeToast("Invalid QR code");
        }
    }
}
