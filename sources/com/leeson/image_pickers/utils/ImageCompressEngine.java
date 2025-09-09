package com.leeson.image_pickers.utils;

import android.content.Context;
import android.net.Uri;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.utils.DateUtils;
import java.io.File;
import java.util.ArrayList;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;

/* loaded from: classes4.dex */
public class ImageCompressEngine implements CompressFileEngine {

    /* renamed from: a, reason: collision with root package name */
    int f18774a;

    public ImageCompressEngine(int i2) {
        this.f18774a = i2;
    }

    @Override // com.luck.picture.lib.engine.CompressFileEngine
    public void onStartCompress(Context context, ArrayList<Uri> arrayList, final OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
        Luban.with(context).load(arrayList).ignoreBy(this.f18774a).filter(new CompressionPredicate() { // from class: com.leeson.image_pickers.utils.ImageCompressEngine.3
            @Override // top.zibin.luban.CompressionPredicate
            public boolean apply(String str) {
                return !str.endsWith(PictureMimeType.GIF);
            }
        }).setRenameListener(new OnRenameListener() { // from class: com.leeson.image_pickers.utils.ImageCompressEngine.2
            @Override // top.zibin.luban.OnRenameListener
            public String rename(String str) {
                int iLastIndexOf = str.lastIndexOf(".");
                return DateUtils.getCreateFileName("CMP_") + (iLastIndexOf != -1 ? str.substring(iLastIndexOf) : PictureMimeType.JPG);
            }
        }).setCompressListener(new OnNewCompressListener() { // from class: com.leeson.image_pickers.utils.ImageCompressEngine.1
            @Override // top.zibin.luban.OnNewCompressListener
            public void onError(String str, Throwable th) {
                OnKeyValueResultCallbackListener onKeyValueResultCallbackListener2 = onKeyValueResultCallbackListener;
                if (onKeyValueResultCallbackListener2 != null) {
                    onKeyValueResultCallbackListener2.onCallback(str, null);
                }
            }

            @Override // top.zibin.luban.OnNewCompressListener
            public void onStart() {
            }

            @Override // top.zibin.luban.OnNewCompressListener
            public void onSuccess(String str, File file) {
                OnKeyValueResultCallbackListener onKeyValueResultCallbackListener2 = onKeyValueResultCallbackListener;
                if (onKeyValueResultCallbackListener2 != null) {
                    onKeyValueResultCallbackListener2.onCallback(str, file.getAbsolutePath());
                }
            }
        }).launch();
    }
}
