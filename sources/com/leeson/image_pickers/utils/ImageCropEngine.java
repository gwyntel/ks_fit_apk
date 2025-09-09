package com.leeson.image_pickers.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.leeson.image_pickers.AppPath;
import com.luck.picture.lib.engine.CropFileEngine;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;
import com.yalantis.ucrop.model.AspectRatio;
import java.io.File;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ImageCropEngine implements CropFileEngine {
    private float aspectRatioX;
    private float aspectRatioY;
    private Context context;
    private UCrop.Options options;

    public ImageCropEngine(Context context, UCrop.Options options, float f2, float f3) {
        this.context = context;
        this.options = options;
        this.aspectRatioX = f2;
        this.aspectRatioY = f3;
    }

    private String getSandboxPath() {
        return new AppPath(this.context).getAppImgDirPath() + File.separator;
    }

    @Override // com.luck.picture.lib.engine.CropFileEngine
    public void onStartCrop(Fragment fragment, Uri uri, Uri uri2, ArrayList<String> arrayList, int i2) {
        UCrop uCropOf = UCrop.of(uri, uri2, arrayList);
        this.options.isDragCropImages(true);
        this.options.setShowCropFrame(true);
        this.options.setFreeStyleCropEnabled(this.aspectRatioX <= 0.0f || this.aspectRatioY <= 0.0f);
        this.options.setHideBottomControls(true);
        this.options.setAllowedGestures(3, 3, 3);
        AspectRatio[] aspectRatioArr = new AspectRatio[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            aspectRatioArr[i3] = new AspectRatio("", this.aspectRatioX, this.aspectRatioY);
        }
        this.options.setMultipleCropAspectRatio(aspectRatioArr);
        uCropOf.withOptions(this.options);
        uCropOf.withAspectRatio(this.aspectRatioX, this.aspectRatioY);
        uCropOf.setImageEngine(new UCropImageEngine() { // from class: com.leeson.image_pickers.utils.ImageCropEngine.1
            public void loadImage(Context context, String str, ImageView imageView) {
                if (ImageLoaderUtils.assertValidRequest(context)) {
                    Glide.with(context).load(str).override(180, 180).into(imageView);
                }
            }

            public void loadImage(Context context, Uri uri3, int i4, int i5, final UCropImageEngine.OnCallbackListener<Bitmap> onCallbackListener) {
                if (ImageLoaderUtils.assertValidRequest(context)) {
                    Glide.with(context).asBitmap().override(i4, i5).load(uri3).into((RequestBuilder) new CustomTarget<Bitmap>() { // from class: com.leeson.image_pickers.utils.ImageCropEngine.1.1
                        @Override // com.bumptech.glide.request.target.Target
                        public void onLoadCleared(@Nullable Drawable drawable) {
                        }

                        @Override // com.bumptech.glide.request.target.CustomTarget, com.bumptech.glide.request.target.Target
                        public void onLoadFailed(@Nullable Drawable drawable) {
                            UCropImageEngine.OnCallbackListener onCallbackListener2 = onCallbackListener;
                            if (onCallbackListener2 != null) {
                                onCallbackListener2.onCall((Object) null);
                            }
                        }

                        @Override // com.bumptech.glide.request.target.Target
                        public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                            onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
                        }

                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            UCropImageEngine.OnCallbackListener onCallbackListener2 = onCallbackListener;
                            if (onCallbackListener2 != null) {
                                onCallbackListener2.onCall(bitmap);
                            }
                        }
                    });
                }
            }
        });
        uCropOf.start(fragment.getActivity(), fragment, i2);
    }
}
