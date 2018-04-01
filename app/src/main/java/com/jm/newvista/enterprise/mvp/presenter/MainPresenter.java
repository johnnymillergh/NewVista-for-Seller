package com.jm.newvista.enterprise.mvp.presenter;

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
}
