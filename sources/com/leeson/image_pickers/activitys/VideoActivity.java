package com.leeson.image_pickers.activitys;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.leeson.image_pickers.R;
import java.io.File;

/* loaded from: classes4.dex */
public class VideoActivity extends BaseActivity {
    private static final int READ_SDCARD = 101;
    public static final String THUMB_PATH = "THUMB_PATH";
    public static final String VIDEO_PATH = "VIDEO_PATH";
    private DisplayMetrics outMetrics;

    /* renamed from: s, reason: collision with root package name */
    VideoView f18764s;

    /* renamed from: t, reason: collision with root package name */
    LinearLayout f18765t;
    private String thumbPath;

    /* renamed from: u, reason: collision with root package name */
    RelativeLayout f18766u;

    /* renamed from: v, reason: collision with root package name */
    ImageView f18767v;
    private int videoHeight;
    private String videoPath;
    private int videoWidth;

    /* renamed from: w, reason: collision with root package name */
    ProgressBar f18768w;

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVideoViewSize() {
        if (this.videoHeight == 0 || this.videoWidth == 0) {
            return;
        }
        if (getResources().getConfiguration().orientation == 1) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, (int) (((this.videoHeight * 1.0f) / this.videoWidth) * this.outMetrics.widthPixels));
            layoutParams.addRule(13);
            this.f18764s.setLayoutParams(layoutParams);
        } else {
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) (((this.videoWidth * 1.0f) / this.videoHeight) * this.outMetrics.widthPixels), -1);
            layoutParams2.addRule(13);
            this.f18764s.setLayoutParams(layoutParams2);
        }
    }

    protected void n() {
        Uri uriForFile;
        if (!TextUtils.isEmpty(this.thumbPath)) {
            Glide.with((FragmentActivity) this).asBitmap().load(this.thumbPath).into(this.f18767v);
            this.f18767v.setVisibility(0);
        }
        if (!this.videoPath.startsWith("http") && Build.VERSION.SDK_INT >= 24) {
            uriForFile = FileProvider.getUriForFile(this, getPackageName() + ".luckProvider", new File(this.videoPath));
        } else {
            uriForFile = Uri.parse(this.videoPath);
        }
        this.f18764s.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.leeson.image_pickers.activitys.VideoActivity.1
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mediaPlayer) {
                VideoActivity.this.videoHeight = mediaPlayer.getVideoHeight();
                VideoActivity.this.videoWidth = mediaPlayer.getVideoWidth();
                VideoActivity.this.updateVideoViewSize();
                mediaPlayer.setVideoScalingMode(1);
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() { // from class: com.leeson.image_pickers.activitys.VideoActivity.1.1
                    @Override // android.media.MediaPlayer.OnInfoListener
                    public boolean onInfo(MediaPlayer mediaPlayer2, int i2, int i3) {
                        if (i2 != 3) {
                            return true;
                        }
                        VideoActivity.this.f18767v.setVisibility(8);
                        VideoActivity.this.f18768w.setVisibility(8);
                        return true;
                    }
                });
            }
        });
        this.f18764s.setVideoURI(uriForFile);
        this.f18764s.start();
        this.f18765t.setOnClickListener(new View.OnClickListener() { // from class: com.leeson.image_pickers.activitys.VideoActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoActivity.this.finish();
            }
        });
        this.f18764s.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.leeson.image_pickers.activitys.VideoActivity.3
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) {
                VideoActivity.this.finish();
            }
        });
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i2 = configuration.orientation;
        if (i2 == 1) {
            updateVideoViewSize();
        } else if (i2 == 2) {
            updateVideoViewSize();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        super.onCreate(bundle);
        setContentView(R.layout.activity_video);
        this.f18764s = (VideoView) findViewById(R.id.videoView);
        this.f18765t = (LinearLayout) findViewById(R.id.layout_root);
        this.f18766u = (RelativeLayout) findViewById(R.id.videoParent);
        this.f18767v = (ImageView) findViewById(R.id.iv_src);
        this.f18768w = (ProgressBar) findViewById(R.id.progressBar);
        this.videoPath = getIntent().getStringExtra(VIDEO_PATH);
        this.thumbPath = getIntent().getStringExtra(THUMB_PATH);
        WindowManager windowManager = (WindowManager) getSystemService("window");
        this.outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(this.outMetrics);
        n();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        VideoView videoView = this.f18764s;
        if (videoView != null) {
            videoView.suspend();
        }
    }
}
