package com.leeson.image_pickers.utils;

import android.content.Context;
import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.utils.SandboxTransformUtils;

/* loaded from: classes4.dex */
public class MeSandboxFileEngine implements UriToFileTransformEngine {
    @Override // com.luck.picture.lib.engine.UriToFileTransformEngine
    public void onUriToFileAsyncTransform(Context context, String str, String str2, OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
        if (onKeyValueResultCallbackListener != null) {
            onKeyValueResultCallbackListener.onCallback(str, SandboxTransformUtils.copyPathToSandbox(context, str, str2));
        }
    }
}
