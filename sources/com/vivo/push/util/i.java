package com.vivo.push.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import androidx.core.view.ViewCompat;
import androidx.media3.extractor.text.ttml.TtmlNode;

/* loaded from: classes4.dex */
public final class i implements BaseNotifyLayoutAdapter {

    /* renamed from: a, reason: collision with root package name */
    private Resources f23238a;

    /* renamed from: b, reason: collision with root package name */
    private String f23239b;

    @Override // com.vivo.push.util.BaseNotifyLayoutAdapter
    public final int getNotificationLayout() {
        return this.f23238a.getIdentifier("push_notify", TtmlNode.TAG_LAYOUT, this.f23239b);
    }

    @Override // com.vivo.push.util.BaseNotifyLayoutAdapter
    public final int getSuitIconId() {
        Resources resources;
        String str;
        if (j.f23242c) {
            resources = this.f23238a;
            str = "notify_icon_rom30";
        } else if (j.f23241b) {
            resources = this.f23238a;
            str = "notify_icon_rom20";
        } else {
            resources = this.f23238a;
            str = "notify_icon";
        }
        return resources.getIdentifier(str, "id", this.f23239b);
    }

    @Override // com.vivo.push.util.BaseNotifyLayoutAdapter
    public final int getTitleColor() {
        int iIntValue;
        try {
            iIntValue = ((Integer) z.a("com.android.internal.R$color", "vivo_notification_title_text_color")).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            iIntValue = 0;
        }
        if (iIntValue > 0) {
            return this.f23238a.getColor(iIntValue);
        }
        boolean z2 = j.f23242c;
        if (z2) {
            return -1;
        }
        if (!j.f23241b) {
            return ViewCompat.MEASURED_STATE_MASK;
        }
        if (z2) {
            return Color.parseColor("#ff999999");
        }
        return -1;
    }

    @Override // com.vivo.push.util.BaseNotifyLayoutAdapter
    public final void init(Context context) {
        this.f23239b = context.getPackageName();
        this.f23238a = context.getResources();
    }
}
