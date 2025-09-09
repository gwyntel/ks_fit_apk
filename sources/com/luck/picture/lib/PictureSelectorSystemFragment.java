package com.luck.picture.lib;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.luck.picture.lib.basic.PictureCommonFragment;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener;
import com.luck.picture.lib.interfaces.OnRequestPermissionListener;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.luck.picture.lib.permissions.PermissionResultCallback;
import com.luck.picture.lib.utils.SdkVersionUtils;
import com.luck.picture.lib.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PictureSelectorSystemFragment extends PictureCommonFragment {
    public static final String TAG = "PictureSelectorSystemFragment";
    private ActivityResultLauncher<String> mContentLauncher;
    private ActivityResultLauncher<String> mContentsLauncher;
    private ActivityResultLauncher<String> mDocMultipleLauncher;
    private ActivityResultLauncher<String> mDocSingleLauncher;

    private void createContent() {
        this.mContentLauncher = registerForActivityResult(new ActivityResultContract<String, Uri>() { // from class: com.luck.picture.lib.PictureSelectorSystemFragment.9
            @Override // androidx.activity.result.contract.ActivityResultContract
            @NonNull
            public Intent createIntent(@NonNull Context context, String str) {
                return TextUtils.equals(SelectMimeType.SYSTEM_VIDEO, str) ? new Intent("android.intent.action.PICK", MediaStore.Video.Media.EXTERNAL_CONTENT_URI) : TextUtils.equals(SelectMimeType.SYSTEM_AUDIO, str) ? new Intent("android.intent.action.PICK", MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) : new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // androidx.activity.result.contract.ActivityResultContract
            public Uri parseResult(int i2, @Nullable Intent intent) {
                if (intent == null) {
                    return null;
                }
                return intent.getData();
            }
        }, new ActivityResultCallback<Uri>() { // from class: com.luck.picture.lib.PictureSelectorSystemFragment.10
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(Uri uri) throws Throwable {
                if (uri == null) {
                    PictureSelectorSystemFragment.this.onKeyBackFragmentFinish();
                    return;
                }
                LocalMedia localMediaG = PictureSelectorSystemFragment.this.g(uri.toString());
                localMediaG.setPath(SdkVersionUtils.isQ() ? localMediaG.getPath() : localMediaG.getRealPath());
                if (PictureSelectorSystemFragment.this.confirmSelect(localMediaG, false) == 0) {
                    PictureSelectorSystemFragment.this.h();
                } else {
                    PictureSelectorSystemFragment.this.onKeyBackFragmentFinish();
                }
            }
        });
    }

    private void createMultipleContents() {
        this.mContentsLauncher = registerForActivityResult(new ActivityResultContract<String, List<Uri>>() { // from class: com.luck.picture.lib.PictureSelectorSystemFragment.7
            @Override // androidx.activity.result.contract.ActivityResultContract
            @NonNull
            public Intent createIntent(@NonNull Context context, String str) {
                Intent intent = TextUtils.equals(SelectMimeType.SYSTEM_VIDEO, str) ? new Intent("android.intent.action.PICK", MediaStore.Video.Media.EXTERNAL_CONTENT_URI) : TextUtils.equals(SelectMimeType.SYSTEM_AUDIO, str) ? new Intent("android.intent.action.PICK", MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) : new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
                return intent;
            }

            @Override // androidx.activity.result.contract.ActivityResultContract
            public List<Uri> parseResult(int i2, @Nullable Intent intent) {
                ArrayList arrayList = new ArrayList();
                if (intent == null) {
                    return arrayList;
                }
                if (intent.getClipData() != null) {
                    ClipData clipData = intent.getClipData();
                    int itemCount = clipData.getItemCount();
                    for (int i3 = 0; i3 < itemCount; i3++) {
                        arrayList.add(clipData.getItemAt(i3).getUri());
                    }
                } else if (intent.getData() != null) {
                    arrayList.add(intent.getData());
                }
                return arrayList;
            }
        }, new ActivityResultCallback<List<Uri>>() { // from class: com.luck.picture.lib.PictureSelectorSystemFragment.8
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(List<Uri> list) throws Throwable {
                if (list == null || list.size() == 0) {
                    PictureSelectorSystemFragment.this.onKeyBackFragmentFinish();
                    return;
                }
                for (int i2 = 0; i2 < list.size(); i2++) {
                    LocalMedia localMediaG = PictureSelectorSystemFragment.this.g(list.get(i2).toString());
                    localMediaG.setPath(SdkVersionUtils.isQ() ? localMediaG.getPath() : localMediaG.getRealPath());
                    ((PictureCommonFragment) PictureSelectorSystemFragment.this).f19027d.addSelectResult(localMediaG);
                }
                PictureSelectorSystemFragment.this.h();
            }
        });
    }

    private void createMultipleDocuments() {
        this.mDocMultipleLauncher = registerForActivityResult(new ActivityResultContract<String, List<Uri>>() { // from class: com.luck.picture.lib.PictureSelectorSystemFragment.3
            @Override // androidx.activity.result.contract.ActivityResultContract
            @NonNull
            public Intent createIntent(@NonNull Context context, String str) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
                intent.setType(str);
                return intent;
            }

            @Override // androidx.activity.result.contract.ActivityResultContract
            public List<Uri> parseResult(int i2, @Nullable Intent intent) {
                ArrayList arrayList = new ArrayList();
                if (intent == null) {
                    return arrayList;
                }
                if (intent.getClipData() != null) {
                    ClipData clipData = intent.getClipData();
                    int itemCount = clipData.getItemCount();
                    for (int i3 = 0; i3 < itemCount; i3++) {
                        arrayList.add(clipData.getItemAt(i3).getUri());
                    }
                } else if (intent.getData() != null) {
                    arrayList.add(intent.getData());
                }
                return arrayList;
            }
        }, new ActivityResultCallback<List<Uri>>() { // from class: com.luck.picture.lib.PictureSelectorSystemFragment.4
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(List<Uri> list) throws Throwable {
                if (list == null || list.size() == 0) {
                    PictureSelectorSystemFragment.this.onKeyBackFragmentFinish();
                    return;
                }
                for (int i2 = 0; i2 < list.size(); i2++) {
                    LocalMedia localMediaG = PictureSelectorSystemFragment.this.g(list.get(i2).toString());
                    localMediaG.setPath(SdkVersionUtils.isQ() ? localMediaG.getPath() : localMediaG.getRealPath());
                    ((PictureCommonFragment) PictureSelectorSystemFragment.this).f19027d.addSelectResult(localMediaG);
                }
                PictureSelectorSystemFragment.this.h();
            }
        });
    }

    private void createSingleDocuments() {
        this.mDocSingleLauncher = registerForActivityResult(new ActivityResultContract<String, Uri>() { // from class: com.luck.picture.lib.PictureSelectorSystemFragment.5
            @Override // androidx.activity.result.contract.ActivityResultContract
            @NonNull
            public Intent createIntent(@NonNull Context context, String str) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType(str);
                return intent;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // androidx.activity.result.contract.ActivityResultContract
            public Uri parseResult(int i2, @Nullable Intent intent) {
                if (intent == null) {
                    return null;
                }
                return intent.getData();
            }
        }, new ActivityResultCallback<Uri>() { // from class: com.luck.picture.lib.PictureSelectorSystemFragment.6
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(Uri uri) throws Throwable {
                if (uri == null) {
                    PictureSelectorSystemFragment.this.onKeyBackFragmentFinish();
                    return;
                }
                LocalMedia localMediaG = PictureSelectorSystemFragment.this.g(uri.toString());
                localMediaG.setPath(SdkVersionUtils.isQ() ? localMediaG.getPath() : localMediaG.getRealPath());
                if (PictureSelectorSystemFragment.this.confirmSelect(localMediaG, false) == 0) {
                    PictureSelectorSystemFragment.this.h();
                } else {
                    PictureSelectorSystemFragment.this.onKeyBackFragmentFinish();
                }
            }
        });
    }

    private void createSystemContracts() {
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.selectionMode == 1) {
            if (selectorConfig.chooseMode == SelectMimeType.ofAll()) {
                createSingleDocuments();
                return;
            } else {
                createContent();
                return;
            }
        }
        if (selectorConfig.chooseMode == SelectMimeType.ofAll()) {
            createMultipleDocuments();
        } else {
            createMultipleContents();
        }
    }

    private String getInput() {
        return this.f19027d.chooseMode == SelectMimeType.ofVideo() ? SelectMimeType.SYSTEM_VIDEO : this.f19027d.chooseMode == SelectMimeType.ofAudio() ? SelectMimeType.SYSTEM_AUDIO : SelectMimeType.SYSTEM_IMAGE;
    }

    public static PictureSelectorSystemFragment newInstance() {
        return new PictureSelectorSystemFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openSystemAlbum() {
        onPermissionExplainEvent(false, null);
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.selectionMode == 1) {
            if (selectorConfig.chooseMode == SelectMimeType.ofAll()) {
                this.mDocSingleLauncher.launch(SelectMimeType.SYSTEM_ALL);
                return;
            } else {
                this.mContentLauncher.launch(getInput());
                return;
            }
        }
        if (selectorConfig.chooseMode == SelectMimeType.ofAll()) {
            this.mDocMultipleLauncher.launch(SelectMimeType.SYSTEM_ALL);
        } else {
            this.mContentsLauncher.launch(getInput());
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment
    public String getFragmentTag() {
        return TAG;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public int getResourceId() {
        return R.layout.ps_empty;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void handlePermissionSettingResult(String[] strArr) {
        onPermissionExplainEvent(false, null);
        SelectorConfig selectorConfig = this.f19027d;
        OnPermissionsInterceptListener onPermissionsInterceptListener = selectorConfig.onPermissionsEventListener;
        if (onPermissionsInterceptListener != null ? onPermissionsInterceptListener.hasPermissions(this, strArr) : PermissionChecker.isCheckReadStorage(selectorConfig.chooseMode, getContext())) {
            openSystemAlbum();
        } else {
            ToastUtils.showToast(getContext(), getString(R.string.ps_jurisdiction));
            onKeyBackFragmentFinish();
        }
        PermissionConfig.CURRENT_REQUEST_PERMISSION = new String[0];
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i3 == 0) {
            onKeyBackFragmentFinish();
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onApplyPermissionsEvent(int i2, String[] strArr) {
        if (i2 == -2) {
            this.f19027d.onPermissionsEventListener.requestPermission(this, PermissionConfig.getReadPermissionArray(i(), this.f19027d.chooseMode), new OnRequestPermissionListener() { // from class: com.luck.picture.lib.PictureSelectorSystemFragment.2
                @Override // com.luck.picture.lib.interfaces.OnRequestPermissionListener
                public void onCall(String[] strArr2, boolean z2) {
                    if (z2) {
                        PictureSelectorSystemFragment.this.openSystemAlbum();
                    } else {
                        PictureSelectorSystemFragment.this.handlePermissionDenied(strArr2);
                    }
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        ActivityResultLauncher<String> activityResultLauncher = this.mDocMultipleLauncher;
        if (activityResultLauncher != null) {
            activityResultLauncher.unregister();
        }
        ActivityResultLauncher<String> activityResultLauncher2 = this.mDocSingleLauncher;
        if (activityResultLauncher2 != null) {
            activityResultLauncher2.unregister();
        }
        ActivityResultLauncher<String> activityResultLauncher3 = this.mContentsLauncher;
        if (activityResultLauncher3 != null) {
            activityResultLauncher3.unregister();
        }
        ActivityResultLauncher<String> activityResultLauncher4 = this.mContentLauncher;
        if (activityResultLauncher4 != null) {
            activityResultLauncher4.unregister();
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        createSystemContracts();
        if (PermissionChecker.isCheckReadStorage(this.f19027d.chooseMode, getContext())) {
            openSystemAlbum();
            return;
        }
        final String[] readPermissionArray = PermissionConfig.getReadPermissionArray(i(), this.f19027d.chooseMode);
        onPermissionExplainEvent(true, readPermissionArray);
        if (this.f19027d.onPermissionsEventListener != null) {
            onApplyPermissionsEvent(-2, readPermissionArray);
        } else {
            PermissionChecker.getInstance().requestPermissions(this, readPermissionArray, new PermissionResultCallback() { // from class: com.luck.picture.lib.PictureSelectorSystemFragment.1
                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onDenied() {
                    PictureSelectorSystemFragment.this.handlePermissionDenied(readPermissionArray);
                }

                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onGranted() {
                    PictureSelectorSystemFragment.this.openSystemAlbum();
                }
            });
        }
    }
}
