package com.leeson.image_pickers.activitys;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.leeson.image_pickers.AppPath;
import com.leeson.image_pickers.R;
import com.leeson.image_pickers.utils.CommonUtils;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import uk.co.senab.photoview.PhotoViewAttacher;

/* loaded from: classes4.dex */
public class PhotosActivity extends BaseActivity {
    public static final String CURRENT_POSITION = "CURRENT_POSITION";
    public static final String IMAGES = "IMAGES";
    private ImageView currentPlay;
    private Number currentPosition;
    private ImageView currentSrc;
    private VideoView currentVideoView;
    private List<String> images;
    private LayoutInflater inflater;
    private DisplayMetrics outMetrics;

    /* renamed from: s, reason: collision with root package name */
    ViewPager f18730s;

    /* renamed from: t, reason: collision with root package name */
    LinearLayout f18731t;
    private int videoHeight;
    private int videoWidth;

    private class Adapter extends PagerAdapter {
        private View setupImage(ViewGroup viewGroup, String str) {
            View viewInflate = PhotosActivity.this.inflater.inflate(R.layout.item_activity_photos, viewGroup, false);
            final ProgressBar progressBar = (ProgressBar) viewInflate.findViewById(R.id.progressBar);
            final ImageView imageView = (ImageView) viewInflate.findViewById(R.id.photoView);
            final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
            photoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.Adapter.6
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    return true;
                }
            });
            photoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.Adapter.7
                @Override // uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener
                public void onViewTap(View view, float f2, float f3) {
                    PhotosActivity.this.finish();
                }
            });
            progressBar.setVisibility(0);
            if (TextUtils.isEmpty(str) || !str.endsWith(PictureMimeType.GIF)) {
                Glide.with((FragmentActivity) PhotosActivity.this).asDrawable().load(str).listener(new RequestListener<Drawable>() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.Adapter.9
                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z2) {
                        return false;
                    }

                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z2) {
                        imageView.setImageDrawable(drawable);
                        photoViewAttacher.update();
                        progressBar.setVisibility(8);
                        return false;
                    }
                }).into(imageView);
            } else {
                Glide.with((FragmentActivity) PhotosActivity.this).asGif().diskCacheStrategy(DiskCacheStrategy.NONE).priority(Priority.HIGH).load(str).listener(new RequestListener<GifDrawable>() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.Adapter.8
                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<GifDrawable> target, boolean z2) {
                        return false;
                    }

                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onResourceReady(GifDrawable gifDrawable, Object obj, Target<GifDrawable> target, DataSource dataSource, boolean z2) {
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        layoutParams.width = CommonUtils.getScreenWidth(PhotosActivity.this);
                        layoutParams.height = (int) (CommonUtils.getScreenWidth(PhotosActivity.this) / (gifDrawable.getIntrinsicWidth() / gifDrawable.getIntrinsicHeight()));
                        imageView.setLayoutParams(layoutParams);
                        photoViewAttacher.update();
                        progressBar.setVisibility(8);
                        imageView.setImageDrawable(gifDrawable);
                        return false;
                    }
                }).into(imageView);
            }
            return viewInflate;
        }

        private View setupVideo(ViewGroup viewGroup, final String str) {
            View viewInflate = PhotosActivity.this.inflater.inflate(R.layout.item_activity_video, viewGroup, false);
            final VideoView videoView = (VideoView) viewInflate.findViewById(R.id.videoView);
            LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.layout_root);
            final ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_src);
            final ImageView imageView2 = (ImageView) viewInflate.findViewById(R.id.iv_play);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.Adapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Uri uriForFile;
                    if (PhotosActivity.this.currentVideoView != null) {
                        PhotosActivity.this.currentVideoView.suspend();
                        PhotosActivity.this.currentVideoView = null;
                    }
                    if (!str.startsWith("http") && Build.VERSION.SDK_INT >= 24) {
                        uriForFile = FileProvider.getUriForFile(PhotosActivity.this, PhotosActivity.this.getPackageName() + ".luckProvider", new File(str));
                    } else {
                        uriForFile = Uri.parse(str);
                    }
                    PhotosActivity.this.currentVideoView = videoView;
                    PhotosActivity.this.currentPlay = imageView2;
                    videoView.setVideoURI(uriForFile);
                    videoView.start();
                }
            });
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.Adapter.2
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer) {
                    PhotosActivity.this.videoHeight = mediaPlayer.getVideoHeight();
                    PhotosActivity.this.videoWidth = mediaPlayer.getVideoWidth();
                    PhotosActivity.this.updateVideoViewSize();
                    mediaPlayer.setVideoScalingMode(1);
                    mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.Adapter.2.1
                        @Override // android.media.MediaPlayer.OnInfoListener
                        public boolean onInfo(MediaPlayer mediaPlayer2, int i2, int i3) {
                            if (i2 != 3) {
                                return true;
                            }
                            imageView.setVisibility(8);
                            imageView2.setVisibility(8);
                            return true;
                        }
                    });
                }
            });
            videoView.postDelayed(new Runnable() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.Adapter.3
                @Override // java.lang.Runnable
                public void run() throws IOException, IllegalArgumentException {
                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    if (!str.startsWith("http")) {
                        try {
                            mediaMetadataRetriever.setDataSource(str);
                            imageView.setImageBitmap(mediaMetadataRetriever.getFrameAtTime());
                            mediaMetadataRetriever.release();
                            return;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    String str2 = str;
                    sb.append(str2.substring(str2.lastIndexOf("/") + 1).replaceAll("\\.", OpenAccountUIConstants.UNDER_LINE));
                    sb.append(PictureMimeType.PNG);
                    String string = sb.toString();
                    AppPath appPath = new AppPath(PhotosActivity.this);
                    File file = new File(appPath.getAppImgDirPath(), string);
                    if (file.exists()) {
                        Glide.with((FragmentActivity) PhotosActivity.this).load(file).into(imageView);
                        return;
                    }
                    try {
                        mediaMetadataRetriever.setDataSource(str, new HashMap());
                        Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime();
                        imageView.setImageBitmap(frameAtTime);
                        CommonUtils.saveBitmapByPath(PhotosActivity.this, appPath.getAppImgDirPath(), string, frameAtTime);
                        mediaMetadataRetriever.release();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }, 200L);
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.Adapter.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (PhotosActivity.this.currentVideoView == null || PhotosActivity.this.currentPlay == null) {
                        PhotosActivity.this.finish();
                    } else if (!PhotosActivity.this.currentVideoView.isPlaying()) {
                        PhotosActivity.this.finish();
                    } else {
                        PhotosActivity.this.currentVideoView.pause();
                        PhotosActivity.this.currentPlay.setVisibility(0);
                    }
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.Adapter.5
                @Override // android.media.MediaPlayer.OnCompletionListener
                public void onCompletion(MediaPlayer mediaPlayer) {
                    videoView.start();
                }
            });
            return viewInflate;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i2, Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return PhotosActivity.this.images.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup viewGroup, int i2) {
            String str = (String) PhotosActivity.this.images.get(i2);
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(str));
            View view = (TextUtils.isEmpty(mimeTypeFromExtension) || !mimeTypeFromExtension.contains("video")) ? setupImage(viewGroup, str) : setupVideo(viewGroup, str);
            viewGroup.addView(view);
            return view;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }

        private Adapter() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reset(int i2) {
        for (int i3 = 0; i3 < this.f18731t.getChildCount(); i3++) {
            this.f18731t.getChildAt(i3).setBackground(ContextCompat.getDrawable(this, R.drawable.circle_gray));
        }
        this.f18731t.getChildAt(i2).setBackground(ContextCompat.getDrawable(this, R.drawable.circle_white));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVideoViewSize() {
        if (this.videoHeight == 0 || this.videoWidth == 0 || this.currentVideoView == null) {
            return;
        }
        if (getResources().getConfiguration().orientation == 1) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, (int) (((this.videoHeight * 1.0f) / this.videoWidth) * this.outMetrics.widthPixels));
            layoutParams.addRule(13);
            this.currentVideoView.setLayoutParams(layoutParams);
        } else {
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) (((this.videoWidth * 1.0f) / this.videoHeight) * this.outMetrics.widthPixels), -1);
            layoutParams2.addRule(13);
            this.currentVideoView.setLayoutParams(layoutParams2);
        }
    }

    public int dp2px(float f2) {
        return (int) ((f2 * getResources().getDisplayMetrics().density) + 0.5f);
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
    protected void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_photos);
        this.f18730s = (ViewPager) findViewById(R.id.viewPager);
        this.f18731t = (LinearLayout) findViewById(R.id.layout_tip);
        this.inflater = LayoutInflater.from(this);
        this.images = getIntent().getStringArrayListExtra(IMAGES);
        this.currentPosition = Integer.valueOf(getIntent().getIntExtra(CURRENT_POSITION, 0));
        WindowManager windowManager = (WindowManager) getSystemService("window");
        this.outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(this.outMetrics);
        List<String> list = this.images;
        if (list != null && list.size() > 0 && this.images.size() < 10 && this.images.size() > 1) {
            for (int i2 = 0; i2 < this.images.size(); i2++) {
                View view = new View(this);
                if (i2 == 0) {
                    view.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_white));
                } else {
                    view.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_gray));
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                int iDp2px = dp2px(6.0f);
                layoutParams.height = iDp2px;
                layoutParams.width = iDp2px;
                int iDp2px2 = dp2px(5.0f);
                layoutParams.rightMargin = iDp2px2;
                layoutParams.leftMargin = iDp2px2;
                view.setLayoutParams(layoutParams);
                this.f18731t.addView(view);
            }
        }
        this.f18730s.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.leeson.image_pickers.activitys.PhotosActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i3) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i3, float f2, int i4) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i3) {
                if (PhotosActivity.this.images.size() < 10) {
                    PhotosActivity.this.reset(i3);
                }
                if (PhotosActivity.this.currentVideoView != null) {
                    PhotosActivity.this.currentVideoView.suspend();
                    PhotosActivity.this.currentVideoView = null;
                }
                if (PhotosActivity.this.currentPlay != null) {
                    PhotosActivity.this.currentPlay.setVisibility(0);
                }
            }
        });
        this.f18730s.setAdapter(new Adapter());
        this.f18730s.setCurrentItem(this.currentPosition.intValue());
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        VideoView videoView = this.currentVideoView;
        if (videoView != null) {
            videoView.suspend();
        }
    }
}
