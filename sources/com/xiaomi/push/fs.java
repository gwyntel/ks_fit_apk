package com.xiaomi.push;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.widget.RemoteViews;
import androidx.media3.extractor.text.ttml.TtmlNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class fs extends fq {

    /* renamed from: a, reason: collision with root package name */
    private int f23765a;

    /* renamed from: a, reason: collision with other field name */
    protected Bitmap f427a;

    /* renamed from: a, reason: collision with other field name */
    private RemoteViews f428a;

    /* renamed from: a, reason: collision with other field name */
    protected CharSequence f429a;

    /* renamed from: a, reason: collision with other field name */
    private String f430a;

    /* renamed from: a, reason: collision with other field name */
    private ArrayList<Notification.Action> f431a;

    /* renamed from: a, reason: collision with other field name */
    protected Map<String, String> f432a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f433a;

    /* renamed from: b, reason: collision with root package name */
    private int f23766b;

    /* renamed from: b, reason: collision with other field name */
    protected CharSequence f434b;

    /* renamed from: b, reason: collision with other field name */
    private boolean f435b;

    public fs(Context context, String str) {
        this(context, 0, str);
    }

    /* renamed from: c, reason: collision with other method in class */
    private boolean m411c() {
        Map<String, String> map = this.f432a;
        return map != null && Boolean.parseBoolean(map.get("custom_builder_set_title"));
    }

    private void d() {
        super.setContentTitle(this.f429a);
        super.setContentText(this.f434b);
    }

    private boolean e() {
        return m412d() && f();
    }

    private boolean f() {
        List<StatusBarNotification> listM749b = com.xiaomi.push.service.aw.a(a(), this.f430a).m749b();
        if (listM749b != null && !listM749b.isEmpty()) {
            for (StatusBarNotification statusBarNotification : listM749b) {
                if (statusBarNotification.getId() == this.f23765a) {
                    if (statusBarNotification.getNotification() == null) {
                        return false;
                    }
                    return !r0.extras.getBoolean("mipush.customCopyLayout", true);
                }
            }
        }
        return false;
    }

    /* renamed from: a, reason: collision with other method in class */
    protected abstract String mo414a();

    protected void a(int i2, Notification.Action action) {
    }

    /* renamed from: a */
    protected abstract boolean mo408a();

    @Override // android.app.Notification.Builder
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public fs setContentText(CharSequence charSequence) {
        this.f434b = charSequence;
        return this;
    }

    protected abstract String b();

    public fs(Context context, int i2, String str) {
        super(context);
        this.f431a = new ArrayList<>();
        this.f23766b = 0;
        this.f430a = str;
        this.f23765a = i2;
        m410c();
    }

    @Override // android.app.Notification.Builder
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public fs setContentTitle(CharSequence charSequence) {
        this.f429a = charSequence;
        return this;
    }

    /* renamed from: b, reason: collision with other method in class */
    protected final void m416b() {
        super.setContentTitle(this.f429a);
        super.setContentText(this.f434b);
        Bitmap bitmap = this.f427a;
        if (bitmap != null) {
            super.setLargeIcon(bitmap);
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    private boolean m412d() {
        return (TextUtils.isEmpty(b()) || TextUtils.isEmpty(this.f430a)) ? false : true;
    }

    @Override // android.app.Notification.Builder
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public fs setLargeIcon(Bitmap bitmap) {
        this.f427a = bitmap;
        return this;
    }

    /* renamed from: c, reason: collision with other method in class */
    private void m410c() {
        int iA = a(a().getResources(), c(), TtmlNode.TAG_LAYOUT, a().getPackageName());
        if (iA != 0) {
            this.f428a = new RemoteViews(a().getPackageName(), iA);
            this.f433a = mo408a();
        } else {
            com.xiaomi.channel.commonutils.logger.b.m91a("create RemoteViews failed, no such layout resource was found");
        }
    }

    @Override // com.xiaomi.push.fq
    public fq a(Map<String, String> map) {
        this.f432a = map;
        return this;
    }

    @Override // android.app.Notification.Builder
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public fs addAction(int i2, CharSequence charSequence, PendingIntent pendingIntent) {
        addAction(new Notification.Action(i2, charSequence, pendingIntent));
        return this;
    }

    @Override // android.app.Notification.Builder
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public fs addAction(Notification.Action action) {
        if (action != null) {
            this.f431a.add(action);
        }
        int i2 = this.f23766b;
        this.f23766b = i2 + 1;
        a(i2, action);
        return this;
    }

    /* renamed from: b, reason: collision with other method in class */
    protected final boolean m417b() {
        return this.f433a;
    }

    @Override // com.xiaomi.push.fq
    /* renamed from: a */
    protected void mo407a() {
        super.mo407a();
        Bundle bundle = new Bundle();
        if (m412d()) {
            bundle.putBoolean("mipush.customCopyLayout", this.f435b);
        } else {
            bundle.putBoolean("mipush.customCopyLayout", false);
        }
        bundle.putBoolean("miui.customHeight", false);
        bundle.putBoolean("mipush.customNotification", true);
        bundle.putInt("mipush.customLargeIconId", a("large_icon"));
        if (this.f431a.size() > 0) {
            Notification.Action[] actionArr = new Notification.Action[this.f431a.size()];
            this.f431a.toArray(actionArr);
            bundle.putParcelableArray("mipush.customActions", actionArr);
        }
        if (!m411c() && com.xiaomi.push.service.ax.m751a(a().getContentResolver())) {
            bundle.putCharSequence("mipush.customTitle", this.f429a);
            bundle.putCharSequence("mipush.customContent", this.f434b);
        } else {
            d();
        }
        addExtras(bundle);
    }

    private String c() {
        boolean zE = e();
        this.f435b = zE;
        return zE ? b() : mo414a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public final RemoteViews m413a() {
        return this.f428a;
    }

    protected void a(int i2) {
        Bitmap bitmapA = a();
        if (bitmapA != null) {
            m413a().setImageViewBitmap(i2, bitmapA);
            return;
        }
        int iB = g.b(a(), this.f430a);
        if (iB != 0) {
            m413a().setImageViewResource(i2, iB);
        }
    }

    private Bitmap a() {
        return com.xiaomi.push.service.al.a(g.m421a(a(), this.f430a));
    }

    protected int a(float f2) {
        return (int) ((f2 * a().getResources().getDisplayMetrics().density) + 0.5f);
    }

    protected Bitmap a(Bitmap bitmap, float f2) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawRoundRect(new RectF(rect), f2, f2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    /* renamed from: a, reason: collision with other method in class */
    protected final boolean m415a(int i2) {
        return ((((double) Color.red(i2)) * 0.299d) + (((double) Color.green(i2)) * 0.587d)) + (((double) Color.blue(i2)) * 0.114d) < 192.0d;
    }
}
