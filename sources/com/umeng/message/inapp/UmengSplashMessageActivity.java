package com.umeng.message.inapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.media3.common.C;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.message.proguard.ad;
import com.umeng.message.proguard.af;
import com.umeng.message.proguard.ah;
import com.umeng.message.proguard.ai;
import com.umeng.message.proguard.bb;
import com.umeng.message.proguard.f;
import java.io.File;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class UmengSplashMessageActivity extends Activity {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22673a = "com.umeng.message.inapp.UmengSplashMessageActivity";

    /* renamed from: s, reason: collision with root package name */
    private static int f22674s = 2000;

    /* renamed from: t, reason: collision with root package name */
    private static int f22675t = 1000;

    /* renamed from: b, reason: collision with root package name */
    private Activity f22676b;

    /* renamed from: c, reason: collision with root package name */
    private af f22677c;

    /* renamed from: d, reason: collision with root package name */
    private ImageView f22678d;

    /* renamed from: e, reason: collision with root package name */
    private ImageView f22679e;

    /* renamed from: f, reason: collision with root package name */
    private TextView f22680f;

    /* renamed from: i, reason: collision with root package name */
    private a f22683i;

    /* renamed from: j, reason: collision with root package name */
    private a f22684j;

    /* renamed from: k, reason: collision with root package name */
    private UInAppMessage f22685k;

    /* renamed from: l, reason: collision with root package name */
    private UInAppHandler f22686l;

    /* renamed from: q, reason: collision with root package name */
    private long f22691q;

    /* renamed from: r, reason: collision with root package name */
    private long f22692r;

    /* renamed from: g, reason: collision with root package name */
    private boolean f22681g = true;

    /* renamed from: h, reason: collision with root package name */
    private boolean f22682h = true;

    /* renamed from: m, reason: collision with root package name */
    private boolean f22687m = false;

    /* renamed from: n, reason: collision with root package name */
    private boolean f22688n = false;

    /* renamed from: o, reason: collision with root package name */
    private boolean f22689o = false;

    /* renamed from: p, reason: collision with root package name */
    private boolean f22690p = false;

    /* renamed from: u, reason: collision with root package name */
    private af.a f22693u = new af.a() { // from class: com.umeng.message.inapp.UmengSplashMessageActivity.1
        @Override // com.umeng.message.proguard.af.a
        public final void a(Bitmap[] bitmapArr) {
            if (UmengSplashMessageActivity.this.c()) {
                return;
            }
            if (UmengSplashMessageActivity.this.f22683i != null) {
                UmengSplashMessageActivity.this.f22683i.a();
                UmengSplashMessageActivity.c(UmengSplashMessageActivity.this);
            }
            try {
                if (bitmapArr.length == 1) {
                    UmengSplashMessageActivity.this.f22678d.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.message.inapp.UmengSplashMessageActivity.1.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            UmengSplashMessageActivity.d(UmengSplashMessageActivity.this);
                            if (TextUtils.equals("none", UmengSplashMessageActivity.this.f22685k.action_type)) {
                                return;
                            }
                            UmengSplashMessageActivity.a(UmengSplashMessageActivity.this, SystemClock.elapsedRealtime() - UmengSplashMessageActivity.this.f22691q);
                            ai.a(UmengSplashMessageActivity.this.f22676b).a(UmengSplashMessageActivity.this.f22685k.msg_id, UmengSplashMessageActivity.this.f22685k.msg_type, 1, 1, 0, 0, 0, (int) UmengSplashMessageActivity.this.f22692r, 0);
                            UmengSplashMessageActivity.this.d();
                            UmengSplashMessageActivity.this.f22686l.handleInAppMessage(UmengSplashMessageActivity.this.f22676b, UmengSplashMessageActivity.this.f22685k, 16);
                            UmengSplashMessageActivity.this.finish();
                        }
                    });
                    UmengSplashMessageActivity.this.f22679e.setVisibility(8);
                    UmengSplashMessageActivity.this.f22678d.setImageBitmap(bitmapArr[0]);
                    UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22678d);
                }
                if (bitmapArr.length == 2) {
                    UmengSplashMessageActivity.this.f22678d.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.message.inapp.UmengSplashMessageActivity.1.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            UmengSplashMessageActivity.m(UmengSplashMessageActivity.this);
                            if (TextUtils.equals("none", UmengSplashMessageActivity.this.f22685k.action_type)) {
                                return;
                            }
                            UmengSplashMessageActivity.a(UmengSplashMessageActivity.this, SystemClock.elapsedRealtime() - UmengSplashMessageActivity.this.f22691q);
                            ai.a(UmengSplashMessageActivity.this.f22676b).a(UmengSplashMessageActivity.this.f22685k.msg_id, UmengSplashMessageActivity.this.f22685k.msg_type, 1, 0, 1, UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22689o), 0, (int) UmengSplashMessageActivity.this.f22692r, 0);
                            UmengSplashMessageActivity.this.d();
                            UmengSplashMessageActivity.this.f22686l.handleInAppMessage(UmengSplashMessageActivity.this.f22676b, UmengSplashMessageActivity.this.f22685k, 16);
                            UmengSplashMessageActivity.this.finish();
                        }
                    });
                    UmengSplashMessageActivity.this.f22679e.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.message.inapp.UmengSplashMessageActivity.1.3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            UmengSplashMessageActivity.o(UmengSplashMessageActivity.this);
                            if (TextUtils.equals("none", UmengSplashMessageActivity.this.f22685k.bottom_action_type)) {
                                return;
                            }
                            UmengSplashMessageActivity.a(UmengSplashMessageActivity.this, SystemClock.elapsedRealtime() - UmengSplashMessageActivity.this.f22691q);
                            ai.a(UmengSplashMessageActivity.this.f22676b).a(UmengSplashMessageActivity.this.f22685k.msg_id, UmengSplashMessageActivity.this.f22685k.msg_type, 1, 0, UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22688n), 1, 0, (int) UmengSplashMessageActivity.this.f22692r, 0);
                            UmengSplashMessageActivity.this.d();
                            UmengSplashMessageActivity.this.f22686l.handleInAppMessage(UmengSplashMessageActivity.this.f22676b, UmengSplashMessageActivity.this.f22685k, 17);
                            UmengSplashMessageActivity.this.finish();
                        }
                    });
                    UmengSplashMessageActivity.this.f22678d.setImageBitmap(bitmapArr[0]);
                    UmengSplashMessageActivity.this.f22679e.setImageBitmap(bitmapArr[1]);
                    UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22678d);
                    UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22679e);
                }
                UmengSplashMessageActivity.this.f22691q = SystemClock.elapsedRealtime();
                if (UmengSplashMessageActivity.this.f22685k.display_button) {
                    UmengSplashMessageActivity.this.f22680f.setVisibility(0);
                    UmengSplashMessageActivity.this.f22680f.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.message.inapp.UmengSplashMessageActivity.1.4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            UmengSplashMessageActivity.a(UmengSplashMessageActivity.this, SystemClock.elapsedRealtime() - UmengSplashMessageActivity.this.f22691q);
                            ai.a(UmengSplashMessageActivity.this.f22676b).a(UmengSplashMessageActivity.this.f22685k.msg_id, UmengSplashMessageActivity.this.f22685k.msg_type, 1, UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22687m), UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22688n), UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22689o), 1, (int) UmengSplashMessageActivity.this.f22692r, 0);
                            UmengSplashMessageActivity.this.d();
                            UmengSplashMessageActivity.this.finish();
                        }
                    });
                } else {
                    UmengSplashMessageActivity.this.f22680f.setVisibility(8);
                }
                InAppMessageManager.getInstance(UmengSplashMessageActivity.this.f22676b).a(UmengSplashMessageActivity.this.f22685k);
                InAppMessageManager.getInstance(UmengSplashMessageActivity.this.f22676b).a(UmengSplashMessageActivity.this.f22685k.msg_id, 1);
                InAppMessageManager inAppMessageManager = InAppMessageManager.getInstance(UmengSplashMessageActivity.this.f22676b);
                StringBuilder sb = new StringBuilder();
                sb.append(System.currentTimeMillis());
                inAppMessageManager.b("KEY_LAST_SHOW_SPLASH_TS", sb.toString());
                UmengSplashMessageActivity.s(UmengSplashMessageActivity.this);
                UmengSplashMessageActivity umengSplashMessageActivity = UmengSplashMessageActivity.this;
                umengSplashMessageActivity.f22684j = umengSplashMessageActivity.new a(umengSplashMessageActivity.f22685k.display_time * 1000, UmengSplashMessageActivity.f22675t);
                UmengSplashMessageActivity.this.f22684j.b();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    };

    /* renamed from: v, reason: collision with root package name */
    private final ad f22694v = new ad() { // from class: com.umeng.message.inapp.UmengSplashMessageActivity.2
        @Override // com.umeng.message.proguard.ad
        public final void a(UInAppMessage uInAppMessage) {
            UInAppMessage uInAppMessage2;
            String strA = InAppMessageManager.getInstance(UmengSplashMessageActivity.this.f22676b).a();
            if (TextUtils.isEmpty(strA)) {
                uInAppMessage2 = null;
            } else {
                try {
                    uInAppMessage2 = new UInAppMessage(new JSONObject(strA));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            if (uInAppMessage != null) {
                if (uInAppMessage2 != null && !uInAppMessage.msg_id.equals(uInAppMessage2.msg_id)) {
                    InAppMessageManager.getInstance(UmengSplashMessageActivity.this.f22676b).a(new File(f.a((Context) UmengSplashMessageActivity.this.f22676b, uInAppMessage2.msg_id)));
                }
                UmengSplashMessageActivity.this.f22685k = uInAppMessage;
            } else if (uInAppMessage2 == null) {
                return;
            } else {
                UmengSplashMessageActivity.this.f22685k = uInAppMessage2;
            }
            if (UmengSplashMessageActivity.this.f22685k.show_type == 1 && !UmengSplashMessageActivity.u(UmengSplashMessageActivity.this)) {
                InAppMessageManager.getInstance(UmengSplashMessageActivity.this.f22676b).a(UmengSplashMessageActivity.this.f22685k.msg_id, 0);
            }
            InAppMessageManager.getInstance(UmengSplashMessageActivity.this.f22676b);
            if (InAppMessageManager.b(UmengSplashMessageActivity.this.f22685k) && InAppMessageManager.getInstance(UmengSplashMessageActivity.this.f22676b).c(UmengSplashMessageActivity.this.f22685k)) {
                if (UmengSplashMessageActivity.this.f22685k.msg_type == 0) {
                    UMLog.mutlInfo(UmengSplashMessageActivity.f22673a, 2, "SPLASH_A");
                    UmengSplashMessageActivity.this.f22677c = new af(UmengSplashMessageActivity.this.f22676b, UmengSplashMessageActivity.this.f22685k);
                    UmengSplashMessageActivity.this.f22677c.f22724a = UmengSplashMessageActivity.this.f22693u;
                    UmengSplashMessageActivity.this.f22677c.execute(UmengSplashMessageActivity.this.f22685k.image_url);
                }
                if (UmengSplashMessageActivity.this.f22685k.msg_type == 1) {
                    UMLog.mutlInfo(UmengSplashMessageActivity.f22673a, 2, "SPLASH_B");
                    UmengSplashMessageActivity.this.f22677c = new af(UmengSplashMessageActivity.this.f22676b, UmengSplashMessageActivity.this.f22685k);
                    UmengSplashMessageActivity.this.f22677c.f22724a = UmengSplashMessageActivity.this.f22693u;
                    UmengSplashMessageActivity.this.f22677c.execute(UmengSplashMessageActivity.this.f22685k.image_url, UmengSplashMessageActivity.this.f22685k.bottom_image_url);
                }
            }
        }

        @Override // com.umeng.message.proguard.ad
        public final void b(UInAppMessage uInAppMessage) {
        }
    };

    class a extends ah {
        a(long j2, long j3) {
            super(j2, j3);
        }

        @Override // com.umeng.message.proguard.ah
        public final void a(long j2) {
            if (UmengSplashMessageActivity.this.f22681g) {
                return;
            }
            UmengSplashMessageActivity.this.f22680f.setVisibility(0);
            UmengSplashMessageActivity.this.f22680f.setText(((int) Math.ceil((j2 * 1.0d) / UmengSplashMessageActivity.f22675t)) + " " + UmengSplashMessageActivity.this.f22685k.display_name);
        }

        @Override // com.umeng.message.proguard.ah
        public final void e() {
            if (UmengSplashMessageActivity.this.c() && UmengSplashMessageActivity.this.f22681g) {
                return;
            }
            if (!UmengSplashMessageActivity.this.f22681g) {
                ai.a(UmengSplashMessageActivity.this.f22676b).a(UmengSplashMessageActivity.this.f22685k.msg_id, UmengSplashMessageActivity.this.f22685k.msg_type, 1, UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22687m), UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22688n), UmengSplashMessageActivity.a(UmengSplashMessageActivity.this.f22689o), 0, UmengSplashMessageActivity.this.f22685k.display_time * 1000, 0);
            }
            UmengSplashMessageActivity.this.d();
            UmengSplashMessageActivity.this.finish();
        }
    }

    static /* synthetic */ int a(boolean z2) {
        return z2 ? 1 : 0;
    }

    static /* synthetic */ a c(UmengSplashMessageActivity umengSplashMessageActivity) {
        umengSplashMessageActivity.f22683i = null;
        return null;
    }

    static /* synthetic */ boolean d(UmengSplashMessageActivity umengSplashMessageActivity) {
        umengSplashMessageActivity.f22687m = true;
        return true;
    }

    static /* synthetic */ boolean m(UmengSplashMessageActivity umengSplashMessageActivity) {
        umengSplashMessageActivity.f22688n = true;
        return true;
    }

    static /* synthetic */ boolean o(UmengSplashMessageActivity umengSplashMessageActivity) {
        umengSplashMessageActivity.f22689o = true;
        return true;
    }

    static /* synthetic */ boolean s(UmengSplashMessageActivity umengSplashMessageActivity) {
        umengSplashMessageActivity.f22681g = false;
        return false;
    }

    static /* synthetic */ boolean u(UmengSplashMessageActivity umengSplashMessageActivity) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(InAppMessageManager.getInstance(umengSplashMessageActivity.f22676b).a("KEY_LAST_SHOW_SPLASH_TS", "0")));
        Calendar calendar2 = Calendar.getInstance();
        return calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1);
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
    }

    @Override // android.app.Activity
    protected final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f22676b = this;
        if ((getIntent().getFlags() & 4194304) > 0) {
            finish();
            return;
        }
        if (onCustomPretreatment()) {
            return;
        }
        setRequestedOrientation(1);
        FrameLayout frameLayout = new FrameLayout(this.f22676b);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        LinearLayout linearLayout = new LinearLayout(this.f22676b);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout.setOrientation(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 13.0f);
        ImageView imageView = new ImageView(this.f22676b);
        this.f22678d = imageView;
        imageView.setLayoutParams(layoutParams);
        ImageView imageView2 = this.f22678d;
        ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;
        imageView2.setScaleType(scaleType);
        linearLayout.addView(this.f22678d);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, 0, 3.0f);
        ImageView imageView3 = new ImageView(this.f22676b);
        this.f22679e = imageView3;
        imageView3.setLayoutParams(layoutParams2);
        this.f22679e.setScaleType(scaleType);
        linearLayout.addView(this.f22679e);
        frameLayout.addView(linearLayout);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams3.gravity = 5;
        layoutParams3.rightMargin = bb.a(30.0f);
        layoutParams3.topMargin = bb.a(20.0f);
        TextView textView = new TextView(this.f22676b);
        this.f22680f = textView;
        textView.setLayoutParams(layoutParams3);
        int iA = bb.a(6.0f);
        int i2 = iA / 3;
        this.f22680f.setPadding(iA, i2, iA, i2);
        this.f22680f.setTextSize(14.0f);
        this.f22680f.setBackgroundColor(Color.parseColor("#80000000"));
        this.f22680f.setTextColor(-1);
        this.f22680f.setVisibility(8);
        frameLayout.addView(this.f22680f);
        setContentView(frameLayout);
        if (!InAppMessageManager.f22623a && System.currentTimeMillis() - Long.parseLong(InAppMessageManager.getInstance(this.f22676b).a("KEY_SPLASH_TS", "0")) <= InAppMessageManager.f22624d) {
            this.f22694v.a(null);
        } else {
            ai.a(this).a(this.f22694v);
        }
        this.f22686l = InAppMessageManager.getInstance(this.f22676b).getInAppHandler();
        a aVar = new a(f22674s, f22675t);
        this.f22683i = aVar;
        aVar.b();
    }

    public boolean onCustomPretreatment() {
        return false;
    }

    @Override // android.app.Activity
    protected final void onDestroy() {
        a aVar = this.f22683i;
        if (aVar != null) {
            aVar.a();
        }
        a aVar2 = this.f22684j;
        if (aVar2 != null) {
            aVar2.a();
        }
        af afVar = this.f22677c;
        if (afVar != null) {
            afVar.f22724a = null;
        }
        this.f22690p = false;
        this.f22687m = false;
        this.f22688n = false;
        this.f22689o = false;
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected final void onPause() {
        super.onPause();
        a aVar = this.f22683i;
        if (aVar != null) {
            aVar.c();
        }
        if (this.f22684j != null) {
            this.f22692r += SystemClock.elapsedRealtime() - this.f22691q;
            this.f22684j.c();
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        a aVar = this.f22683i;
        if (aVar != null) {
            aVar.d();
        }
        if (this.f22684j != null) {
            this.f22691q = SystemClock.elapsedRealtime();
            this.f22684j.d();
        }
    }

    @Override // android.app.Activity
    protected final void onStart() {
        super.onStart();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean c() {
        boolean z2;
        z2 = this.f22690p;
        this.f22690p = true;
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void d() {
        if (this.f22682h) {
            this.f22682h = false;
            Intent intent = new Intent();
            intent.setClassName(this.f22676b, InAppMessageManager.getInstance(this).f22628c);
            intent.setFlags(C.BUFFER_FLAG_LAST_SAMPLE);
            try {
                this.f22676b.startActivity(intent);
            } catch (Exception unused) {
            }
        }
    }

    static /* synthetic */ long a(UmengSplashMessageActivity umengSplashMessageActivity, long j2) {
        long j3 = umengSplashMessageActivity.f22692r + j2;
        umengSplashMessageActivity.f22692r = j3;
        return j3;
    }

    static /* synthetic */ void a(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(500L);
        view.startAnimation(alphaAnimation);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle, PersistableBundle persistableBundle) {
        super.onCreate(bundle, persistableBundle);
    }
}
