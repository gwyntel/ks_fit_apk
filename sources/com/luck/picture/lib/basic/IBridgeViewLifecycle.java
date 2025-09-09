package com.luck.picture.lib.basic;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;

/* loaded from: classes4.dex */
public interface IBridgeViewLifecycle {
    void onDestroy(Fragment fragment);

    void onViewCreated(Fragment fragment, View view, Bundle bundle);
}
