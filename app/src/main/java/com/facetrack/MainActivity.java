package com.facetrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.facetrack.util.ConUtil;
import com.facetrack.util.Util;
import com.megvii.facepp.sdk.Facepp;
import com.megvii.licensemanager.sdk.LicenseManager;

public class MainActivity extends Activity {

    Facepp facepp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void showtoast(String content) {

        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    private void init() {

//        facepp = new Facepp();
//        facepp.init(MainActivity.this, null, 1);

        byte[] model = ConUtil.getFileContent(this, R.raw.megviifacepp_0_5_2_model);
        int type = Facepp.getSDKAuthType(model);
        if (type == 2) {//本地授权

            return;
        }

        final LicenseManager licenseManager = new LicenseManager(this);
        licenseManager.setExpirationMillis(Facepp.getApiExpirationMillis(this, ConUtil.getFileContent(this, R.raw
                .megviifacepp_0_5_2_model)));

        String uuid = ConUtil.getUUIDString(MainActivity.this);
        long apiName = Facepp.getApiName();

        licenseManager.setAuthTimeBufferMillis(0);

        licenseManager.takeLicenseFromNetwork(uuid, Util.API_KEY, Util.API_SECRET, apiName,
                LicenseManager.DURATION_30DAYS, "Landmark", "1", true, new LicenseManager.TakeLicenseCallback() {
                    @Override
                    public void onSuccess() {
                        authState(true,0,"");
                    }

                    @Override
                    public void onFailed(int i, byte[] bytes) {
                        if (TextUtils.isEmpty(Util.API_KEY)||TextUtils.isEmpty(Util.API_SECRET)) {
                            if (!ConUtil.isReadKey(MainActivity.this)) {
                                authState(false,1001,"");
                            }else{
                                authState(false,1001,"");
                            }
                        }else{
                            String msg="";
                            if (bytes!=null&&bytes.length>0){
                                msg=  new String(bytes);
                            }
                            authState(false,i,msg);
                        }
                    }
                });
    }

    private void authState(boolean isSuccess,int code,String msg)
    {
        if (isSuccess) {

            Intent intent = new Intent();
            intent.setClass(this, FaceppActionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//If set, and the activity being launched is already running in the current task, then instead of launching a new instance of that activity,all of the other activities on top of it will be closed and this Intent will be delivered to the (now on top) old activity as a new Intent.
            startActivity(intent);

            finish();
        }
    }
}
