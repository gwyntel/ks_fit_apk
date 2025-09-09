package com.kingsmith.xiaojin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/kingsmith/xiaojin/HomeWidget;", "", "()V", "homeWidgetType", "", "getHomeWidgetType", "()Ljava/lang/String;", "setHomeWidgetType", "(Ljava/lang/String;)V", "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class HomeWidget {

    @NotNull
    private String homeWidgetType = "unknow";

    @NotNull
    public final String getHomeWidgetType() {
        return this.homeWidgetType;
    }

    public final void setHomeWidgetType(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.homeWidgetType = str;
    }
}
