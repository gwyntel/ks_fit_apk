package com.xiaomi.push;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;
import androidx.core.view.ViewCompat;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.Map;

/* loaded from: classes4.dex */
public class fp extends fs {

    /* renamed from: a, reason: collision with root package name */
    private int f23758a;

    /* renamed from: b, reason: collision with root package name */
    private Bitmap f23759b;

    /* renamed from: c, reason: collision with root package name */
    private Bitmap f23760c;

    public fp(Context context, String str) {
        super(context, str);
        this.f23758a = 16777216;
    }

    @Override // com.xiaomi.push.fs, android.app.Notification.Builder
    /* renamed from: a */
    public fs setLargeIcon(Bitmap bitmap) {
        return this;
    }

    @Override // com.xiaomi.push.fs
    protected String b() {
        return null;
    }

    @Override // com.xiaomi.push.fs
    /* renamed from: a */
    protected String mo414a() {
        return "notification_banner";
    }

    public fp b(Bitmap bitmap) {
        if (m417b() && bitmap != null) {
            this.f23760c = bitmap;
        }
        return this;
    }

    @Override // com.xiaomi.push.fs
    /* renamed from: a, reason: collision with other method in class */
    protected boolean mo408a() {
        if (!j.m549a()) {
            return false;
        }
        Resources resources = a().getResources();
        String packageName = a().getPackageName();
        return (a(a().getResources(), "bg", "id", a().getPackageName()) == 0 || a(resources, RemoteMessageConst.Notification.ICON, "id", packageName) == 0 || a(resources, "title", "id", packageName) == 0 || j.a(a()) < 9) ? false : true;
    }

    @Override // com.xiaomi.push.fs
    /* renamed from: a */
    public fp setLargeIcon(Bitmap bitmap) {
        if (m417b() && bitmap != null) {
            if (bitmap.getWidth() == 984 && 184 <= bitmap.getHeight() && bitmap.getHeight() <= 1678) {
                this.f23759b = bitmap;
            } else {
                com.xiaomi.channel.commonutils.logger.b.m91a("colorful notification banner image resolution error, must belong to [984*184, 984*1678]");
            }
        }
        return this;
    }

    @Override // com.xiaomi.push.fq
    /* renamed from: a */
    public fp mo409a(String str) {
        if (m417b() && !TextUtils.isEmpty(str)) {
            try {
                this.f23758a = Color.parseColor(str);
            } catch (Exception unused) {
                com.xiaomi.channel.commonutils.logger.b.m91a("parse banner notification image text color error");
            }
        }
        return this;
    }

    @Override // com.xiaomi.push.fs, com.xiaomi.push.fq
    /* renamed from: a, reason: collision with other method in class */
    public void mo407a() {
        if (m417b() && this.f23759b != null) {
            super.mo407a();
            Resources resources = a().getResources();
            String packageName = a().getPackageName();
            int iA = a(resources, "bg", "id", packageName);
            if (j.a(a()) >= 10) {
                m413a().setImageViewBitmap(iA, a(this.f23759b, 30.0f));
            } else {
                m413a().setImageViewBitmap(iA, this.f23759b);
            }
            int iA2 = a(resources, RemoteMessageConst.Notification.ICON, "id", packageName);
            if (this.f23760c != null) {
                m413a().setImageViewBitmap(iA2, this.f23760c);
            } else {
                a(iA2);
            }
            int iA3 = a(resources, "title", "id", packageName);
            m413a().setTextViewText(iA3, ((fs) this).f429a);
            Map<String, String> map = ((fs) this).f432a;
            if (map != null && this.f23758a == 16777216) {
                mo409a(map.get("notification_image_text_color"));
            }
            RemoteViews remoteViewsM413a = m413a();
            int i2 = this.f23758a;
            remoteViewsM413a.setTextColor(iA3, (i2 == 16777216 || !m415a(i2)) ? -1 : ViewCompat.MEASURED_STATE_MASK);
            setCustomContentView(m413a());
            Bundle bundle = new Bundle();
            bundle.putBoolean("miui.customHeight", true);
            addExtras(bundle);
            return;
        }
        m416b();
    }
}
