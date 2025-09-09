package com.kingsmith.xiaojin;

import android.content.SharedPreferences;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.kingsmith.miot.KsProperty;
import com.umeng.analytics.pro.bc;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/kingsmith/xiaojin/Sp;", "", "()V", KsProperty.Sp, "Landroid/content/SharedPreferences;", TmpConstant.PROPERTY_IDENTIFIER_GET, "", KsProperty.Key, "put", "", bc.N, "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class Sp {

    @NotNull
    public static final Sp INSTANCE = new Sp();

    @Nullable
    private static SharedPreferences sp;

    private Sp() {
    }

    @Nullable
    public final String get(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (sp == null) {
            sp = Application.INSTANCE.getINSTANCE().getApplicationContext().getSharedPreferences("config", 0);
        }
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return null;
    }

    public final void put(@NotNull String key, @NotNull String language) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(language, "language");
        if (sp == null) {
            sp = Application.INSTANCE.getINSTANCE().getApplicationContext().getSharedPreferences("config", 0);
        }
        SharedPreferences sharedPreferences = sp;
        SharedPreferences.Editor editorEdit = sharedPreferences != null ? sharedPreferences.edit() : null;
        if (editorEdit != null) {
            editorEdit.putString(key, language);
        }
        if (editorEdit != null) {
            editorEdit.commit();
        }
    }
}
