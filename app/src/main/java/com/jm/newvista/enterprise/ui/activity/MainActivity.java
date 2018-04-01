package com.jm.newvista.enterprise.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.jm.newvista.enterprise.R;
import com.jm.newvista.enterprise.mvp.model.MainModel;
import com.jm.newvista.enterprise.mvp.presenter.MainPresenter;
import com.jm.newvista.enterprise.mvp.view.MainView;
import com.jm.newvista.enterprise.ui.base.BaseActivity;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity
        extends BaseActivity<MainModel, MainView, MainPresenter>
        implements MainView,
        BarcodeCallback {
    private DecoratedBarcodeView barcodeView;
    private Button pause;
    private Button resume;
    private ImageView qrCodePreview;

    private BeepManager beepManager;

    private String decodedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keep screen on
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    private void initView() {
        barcodeView = findViewById(R.id.barcodeView);
        pause = findViewById(R.id.pause);
        resume = findViewById(R.id.resume);

        qrCodePreview = findViewById(R.id.qrCodePreview);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.decodeContinuous(this);

        beepManager = new BeepManager(this);
    }

    public void onClickPause(View view) {
        barcodeView.pause();
    }

    public void onClickResume(View view) {
        barcodeView.resume();
    }

    @Override
    public MainView createView() {
        return this;
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        if (result.getText() == null || result.getText().equals(decodedText)) {
            // Prevent duplicate scans
            return;
        }

        decodedText = result.getText();
        barcodeView.setStatusText(decodedText);

        beepManager.playBeepSoundAndVibrate();

        // Add preview of scanned barcode
        qrCodePreview.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));

        // Customer checked in
        getPresenter().checkIn();
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }

    @Override
    public String onGetDecodedText() {
        return decodedText;
    }

    @Override
    public void onMakeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
