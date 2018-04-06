package com.jm.newvista.enterprise.mvp.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jm.newvista.enterprise.bean.CheckinEntity;
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
        mainView.onDisplayLoadingDialog();

        String decodedText = mainView.onGetDecodedText();
        try {
            CustomerOrderEntity orderEntity = new Gson().fromJson(decodedText, CustomerOrderEntity.class);

            if (!orderEntity.getIsUsed()) {
                mainModel.postOrderInfo(orderEntity, new MainModel.PostOrderInfoListener() {
                    @Override
                    public void onSuccess(String checkinEntityJson) {
                        mainView.onDisplayTicketInfoDialog(checkinEntityJson);
                        mainView.onDismissLoadingDialog();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        mainView.onMakeToast(errorMessage);
                        mainView.onDismissLoadingDialog();
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
        } finally {
            mainView.onDismissLoadingDialog();
        }
    }
}
