package com.luck.picture.lib.engine;

import android.content.Context;
import android.net.Uri;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public interface CompressFileEngine {
    void onStartCompress(Context context, ArrayList<Uri> arrayList, OnKeyValueResultCallbackListener onKeyValueResultCallbackListener);
}
