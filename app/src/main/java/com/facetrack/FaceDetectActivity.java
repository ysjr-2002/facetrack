package com.facetrack;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.facetrack.util.ConUtil;
import com.megvii.facepp.sdk.Facepp;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FaceDetectActivity extends Activity implements View.OnClickListener {

    Button button;
    Facepp facepp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detect);


        button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    private void test() {

        facepp = new Facepp();
        String error = facepp.init(this, ConUtil.getFileContent(this, R.raw.megviifacepp_0_5_2_model));
        if (TextUtils.isEmpty(error)) {

        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ysj);
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);

        byte[] ysjdata = stream.toByteArray();
        int len = ysjdata.length;

        int detect_mode = Facepp.FaceppConfig.DETECTION_MODE_TRACKING_RECT;
        byte[] data = ConUtil.getFileContent(this, R.raw.ysj);
        int len1 = data.length;
        Facepp.Face[] faces = facepp.detect(ysjdata, 639, 1136, detect_mode);
        if (faces == null) {

        } else {

        }
    }

    @Override
    public void onClick(View view) {

//        test();
        //

        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = "https://api-cn.faceplusplus.com/facepp/v3/detect";
                Map<String, String> map = new HashMap<String, String>();
                map.put("api_key", "gVH6u5zYVJxdkUvhPUUy9mNLZH6NlIH6");
                map.put("api_secret", "ZRvi0dFEv1k0EVfzeU-r8l4t65abHHmZ");

                Map<String, byte[]> data = new HashMap<String, byte[]>();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bmp = BitmapFactory.decodeResource(FaceDetectActivity.this.getResources(), R.mipmap.ysj);
                bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);

                byte[] ysjdata = stream.toByteArray();
                data.put("image_file", ysjdata);

                HttpPostUtil.formUpload(url, map, data);
            }
        }).start();
    }

}
