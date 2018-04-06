package com.jm.newvista.enterprise.mvp.view;

import com.jm.newvista.enterprise.bean.CheckinEntity;
import com.jm.newvista.enterprise.mvp.base.BaseView;

public interface MainView extends BaseView {
    String onGetDecodedText();

    void onMakeToast(String message);

    void onDisplayLoadingDialog();

    void onDismissLoadingDialog();

    void onDisplayTicketInfoDialog(String checkinEntityJson);
}
