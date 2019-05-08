package com.gengqiquan.imui.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.gengqiquan.imui.R;
import com.gengqiquan.imui.help.IMHelp;

public class ImagePreviewActivity extends Activity {
    private ImageView mVideoView;
    private boolean pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.im_activity_image_preview);
        String imagePath = getIntent().getStringExtra(IMHelp.IMAGE_PATH);

        ImageView iv_img = findViewById(R.id.iv_img);
        IMHelp.getImageDisplayer().display(imagePath, iv_img, null);

        findViewById(R.id.fl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
