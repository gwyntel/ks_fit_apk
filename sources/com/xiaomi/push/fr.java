package com.xiaomi.push;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;
import androidx.core.view.ViewCompat;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.Map;

/* loaded from: classes4.dex */
public class fr extends fs {

    /* renamed from: a, reason: collision with root package name */
    private int f23762a;

    /* renamed from: a, reason: collision with other field name */
    private PendingIntent f424a;

    /* renamed from: b, reason: collision with root package name */
    private int f23763b;

    /* renamed from: b, reason: collision with other field name */
    private Bitmap f425b;

    /* renamed from: c, reason: collision with root package name */
    private int f23764c;

    /* renamed from: c, reason: collision with other field name */
    private CharSequence f426c;

    public fr(Context context, int i2, String str) {
        super(context, i2, str);
        this.f23762a = 16777216;
        this.f23763b = 16777216;
        this.f23764c = 16777216;
    }

    @Override // com.xiaomi.push.fs
    /* renamed from: a */
    protected String mo414a() {
        return "notification_colorful";
    }

    @Override // com.xiaomi.push.fs
    protected String b() {
        return "notification_colorful_copy";
    }

    public fr c(String str) {
        if (m417b() && !TextUtils.isEmpty(str)) {
            try {
                this.f23764c = Color.parseColor(str);
            } catch (Exception unused) {
                com.xiaomi.channel.commonutils.logger.b.m91a("parse colorful notification image text color error");
            }
        }
        return this;
    }

    @Override // com.xiaomi.push.fs
    /* renamed from: a */
    protected boolean mo408a() {
        if (!j.m549a()) {
            return false;
        }
        Resources resources = a().getResources();
        String packageName = a().getPackageName();
        return (a(resources, RemoteMessageConst.Notification.ICON, "id", packageName) == 0 || a(resources, "title", "id", packageName) == 0 || a(resources, "content", "id", packageName) == 0) ? false : true;
    }

    public fr b(String str) {
        if (m417b() && !TextUtils.isEmpty(str)) {
            try {
                this.f23762a = Color.parseColor(str);
            } catch (Exception unused) {
                com.xiaomi.channel.commonutils.logger.b.m91a("parse colorful notification bg color error");
            }
        }
        return this;
    }

    public fr a(CharSequence charSequence, PendingIntent pendingIntent) {
        if (m417b()) {
            super.addAction(0, charSequence, pendingIntent);
            this.f426c = charSequence;
            this.f424a = pendingIntent;
        }
        return this;
    }

    @Override // com.xiaomi.push.fq
    /* renamed from: a */
    public fr mo409a(String str) {
        if (m417b() && !TextUtils.isEmpty(str)) {
            try {
                this.f23763b = Color.parseColor(str);
            } catch (Exception unused) {
                com.xiaomi.channel.commonutils.logger.b.m91a("parse colorful notification button bg color error");
            }
        }
        return this;
    }

    @Override // com.xiaomi.push.fs
    /* renamed from: a */
    public fr setLargeIcon(Bitmap bitmap) {
        if (m417b() && bitmap != null) {
            if (bitmap.getWidth() == 984 && bitmap.getHeight() >= 177 && bitmap.getHeight() <= 207) {
                this.f425b = bitmap;
            } else {
                com.xiaomi.channel.commonutils.logger.b.m91a("colorful notification bg image resolution error, must [984*177, 984*207]");
            }
        }
        return this;
    }

    @Override // com.xiaomi.push.fs, com.xiaomi.push.fq
    /* renamed from: a */
    public void mo407a() {
        if (m417b()) {
            super.mo407a();
            Resources resources = a().getResources();
            String packageName = a().getPackageName();
            int iA = a(resources, RemoteMessageConst.Notification.ICON, "id", packageName);
            if (((fs) this).f427a == null) {
                a(iA);
            } else {
                m413a().setImageViewBitmap(iA, ((fs) this).f427a);
            }
            int iA2 = a(resources, "title", "id", packageName);
            int iA3 = a(resources, "content", "id", packageName);
            m413a().setTextViewText(iA2, ((fs) this).f429a);
            m413a().setTextViewText(iA3, ((fs) this).f434b);
            if (!TextUtils.isEmpty(this.f426c)) {
                int iA4 = a(resources, "buttonContainer", "id", packageName);
                int iA5 = a(resources, "button", "id", packageName);
                int iA6 = a(resources, "buttonBg", "id", packageName);
                m413a().setViewVisibility(iA4, 0);
                m413a().setTextViewText(iA5, this.f426c);
                m413a().setOnClickPendingIntent(iA4, this.f424a);
                if (this.f23763b != 16777216) {
                    int iA7 = a(70.0f);
                    int iA8 = a(29.0f);
                    m413a().setImageViewBitmap(iA6, com.xiaomi.push.service.al.a(a(this.f23763b, iA7, iA8, iA8 / 2.0f)));
                    m413a().setTextColor(iA5, m415a(this.f23763b) ? -1 : ViewCompat.MEASURED_STATE_MASK);
                }
            }
            int iA9 = a(resources, "bg", "id", packageName);
            int iA10 = a(resources, TtmlNode.RUBY_CONTAINER, "id", packageName);
            if (this.f23762a != 16777216) {
                if (j.a(a()) >= 10) {
                    m413a().setImageViewBitmap(iA9, com.xiaomi.push.service.al.a(a(this.f23762a, 984, 192, 30.0f)));
                } else {
                    m413a().setImageViewBitmap(iA9, com.xiaomi.push.service.al.a(a(this.f23762a, 984, 192, 0.0f)));
                }
                a(m413a(), iA10, iA2, iA3, m415a(this.f23762a));
            } else if (this.f425b != null) {
                if (j.a(a()) >= 10) {
                    m413a().setImageViewBitmap(iA9, a(this.f425b, 30.0f));
                } else {
                    m413a().setImageViewBitmap(iA9, this.f425b);
                }
                Map<String, String> map = ((fs) this).f432a;
                if (map != null && this.f23764c == 16777216) {
                    c(map.get("notification_image_text_color"));
                }
                int i2 = this.f23764c;
                a(m413a(), iA10, iA2, iA3, i2 == 16777216 || !m415a(i2));
            } else if (Build.VERSION.SDK_INT >= 24) {
                m413a().setViewVisibility(iA, 8);
                m413a().setViewVisibility(iA9, 8);
                try {
                    bk.a((Object) this, "setStyle", C0472r.a(a(), "android.app.Notification$DecoratedCustomViewStyle").getConstructor(null).newInstance(null));
                } catch (Exception unused) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("load class DecoratedCustomViewStyle failed");
                }
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("miui.customHeight", true);
            addExtras(bundle);
            setCustomContentView(m413a());
            return;
        }
        m416b();
    }

    private void a(RemoteViews remoteViews, int i2, int i3, int i4, boolean z2) {
        int iA = a(6.0f);
        remoteViews.setViewPadding(i2, iA, 0, iA, 0);
        if (z2) {
            remoteViews.setTextColor(i3, -1);
            remoteViews.setTextColor(i4, -1);
        } else {
            remoteViews.setTextColor(i3, ViewCompat.MEASURED_STATE_MASK);
            remoteViews.setTextColor(i4, ViewCompat.MEASURED_STATE_MASK);
        }
    }

    private Drawable a(int i2, int i3, int i4, float f2) {
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setShape(new RoundRectShape(new float[]{f2, f2, f2, f2, f2, f2, f2, f2}, null, null));
        shapeDrawable.getPaint().setColor(i2);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.setIntrinsicWidth(i3);
        shapeDrawable.setIntrinsicHeight(i4);
        return shapeDrawable;
    }
}
