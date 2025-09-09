package com.luck.picture.lib.basic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class PictureSelector {
    private final SoftReference<Activity> mActivity;
    private final SoftReference<Fragment> mFragment;

    private PictureSelector(Activity activity) {
        this(activity, null);
    }

    public static PictureSelector create(Context context) {
        return new PictureSelector((Activity) context);
    }

    public static ArrayList<LocalMedia> obtainSelectorList(Intent intent) {
        if (intent == null) {
            return new ArrayList<>();
        }
        ArrayList<LocalMedia> parcelableArrayListExtra = intent.getParcelableArrayListExtra(PictureConfig.EXTRA_RESULT_SELECTION);
        return parcelableArrayListExtra != null ? parcelableArrayListExtra : new ArrayList<>();
    }

    public static Intent putIntentResult(ArrayList<LocalMedia> arrayList) {
        return new Intent().putParcelableArrayListExtra(PictureConfig.EXTRA_RESULT_SELECTION, arrayList);
    }

    Activity a() {
        return this.mActivity.get();
    }

    Fragment b() {
        SoftReference<Fragment> softReference = this.mFragment;
        if (softReference != null) {
            return softReference.get();
        }
        return null;
    }

    public PictureSelectionQueryModel dataSource(int i2) {
        return new PictureSelectionQueryModel(this, i2);
    }

    public PictureSelectionCameraModel openCamera(int i2) {
        return new PictureSelectionCameraModel(this, i2);
    }

    public PictureSelectionModel openGallery(int i2) {
        return new PictureSelectionModel(this, i2);
    }

    public PictureSelectionPreviewModel openPreview() {
        return new PictureSelectionPreviewModel(this);
    }

    public PictureSelectionSystemModel openSystemGallery(int i2) {
        return new PictureSelectionSystemModel(this, i2);
    }

    private PictureSelector(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    public static PictureSelector create(AppCompatActivity appCompatActivity) {
        return new PictureSelector(appCompatActivity);
    }

    private PictureSelector(Activity activity, Fragment fragment) {
        this.mActivity = new SoftReference<>(activity);
        this.mFragment = new SoftReference<>(fragment);
    }

    public static PictureSelector create(FragmentActivity fragmentActivity) {
        return new PictureSelector(fragmentActivity);
    }

    public static PictureSelector create(Fragment fragment) {
        return new PictureSelector(fragment);
    }
}
