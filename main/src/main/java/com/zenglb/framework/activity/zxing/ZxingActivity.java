package com.zenglb.framework.activity.zxing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.zenglb.commonlib.base.BaseActivity;
import com.zenglb.framework.R;
import java.util.List;

/**
 * 二维码扫描
 * <p>
 * anylife.zlb@gmail.com
 */
public class ZxingActivity extends BaseActivity {
    private DecoratedBarcodeView barcodeView;
    private boolean hasSuccess;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (!TextUtils.isEmpty(result.getText()) && !hasSuccess) {
                hasSuccess = true;
                barcodeView.setStatusText(result.getText());
                Vibrator vibrator = (Vibrator) ZxingActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(200); //
                Intent data = new Intent();
                data.putExtra("qrcode", result.getText());             //把数据返回给发起的Activity
                setResult(RESULT_OK, data);
                ZxingActivity.this.finish();
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("二维码");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initViews() {
        barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);
        barcodeView.setStatusText("音量加减按键可以控制手电筒开关");
    }


    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


}