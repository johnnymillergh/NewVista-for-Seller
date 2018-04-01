package com.jm.newvista.enterprise.mvp.view;

import com.jm.newvista.enterprise.mvp.base.BaseView;

public interface MainView extends BaseView {
    String onGetDecodedText();

    void onMakeToast(String message);
}
