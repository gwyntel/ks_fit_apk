package com.huawei.hms.hmsscankit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.huawei.hms.scankit.R;

/* loaded from: classes4.dex */
public class a implements DialogInterface {

    /* renamed from: a, reason: collision with root package name */
    private final Context f16529a;

    /* renamed from: b, reason: collision with root package name */
    private final CharSequence f16530b;

    /* renamed from: c, reason: collision with root package name */
    private final CharSequence f16531c;

    /* renamed from: d, reason: collision with root package name */
    private final String f16532d;

    /* renamed from: e, reason: collision with root package name */
    private final String f16533e;

    /* renamed from: f, reason: collision with root package name */
    private final int f16534f;

    /* renamed from: g, reason: collision with root package name */
    private final int f16535g;

    /* renamed from: h, reason: collision with root package name */
    private final int f16536h;

    /* renamed from: i, reason: collision with root package name */
    private final int f16537i;

    /* renamed from: j, reason: collision with root package name */
    private final boolean f16538j;

    /* renamed from: k, reason: collision with root package name */
    private final int f16539k;

    /* renamed from: l, reason: collision with root package name */
    private final DialogInterface.OnClickListener f16540l;

    /* renamed from: m, reason: collision with root package name */
    private final DialogInterface.OnClickListener f16541m;

    /* renamed from: n, reason: collision with root package name */
    private AlertDialog f16542n;

    /* renamed from: o, reason: collision with root package name */
    TextView f16543o;

    /* renamed from: p, reason: collision with root package name */
    TextView f16544p;

    /* renamed from: q, reason: collision with root package name */
    TextView f16545q;

    /* renamed from: r, reason: collision with root package name */
    TextView f16546r;

    /* renamed from: com.huawei.hms.hmsscankit.a$a, reason: collision with other inner class name */
    class ViewOnClickListenerC0135a implements View.OnClickListener {
        ViewOnClickListenerC0135a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (a.this.f16541m != null) {
                a.this.f16541m.onClick(a.this, -2);
            }
            a.this.dismiss();
        }
    }

    class b implements View.OnClickListener {
        b() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (a.this.f16540l != null) {
                a.this.f16540l.onClick(a.this, -1);
            }
            a.this.dismiss();
        }
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        private final Context f16549a;

        /* renamed from: b, reason: collision with root package name */
        private CharSequence f16550b;

        /* renamed from: c, reason: collision with root package name */
        private CharSequence f16551c;

        /* renamed from: d, reason: collision with root package name */
        private String f16552d;

        /* renamed from: e, reason: collision with root package name */
        private String f16553e;

        /* renamed from: f, reason: collision with root package name */
        private int f16554f;

        /* renamed from: g, reason: collision with root package name */
        private int f16555g;

        /* renamed from: h, reason: collision with root package name */
        private int f16556h;

        /* renamed from: i, reason: collision with root package name */
        private int f16557i;

        /* renamed from: j, reason: collision with root package name */
        private boolean f16558j = true;

        /* renamed from: k, reason: collision with root package name */
        private int f16559k = 80;

        /* renamed from: l, reason: collision with root package name */
        private DialogInterface.OnClickListener f16560l;

        /* renamed from: m, reason: collision with root package name */
        private DialogInterface.OnClickListener f16561m;

        public c(Context context) {
            this.f16549a = context;
        }

        public c a(CharSequence charSequence) {
            this.f16551c = charSequence;
            return this;
        }

        public c b(CharSequence charSequence) {
            this.f16550b = charSequence;
            return this;
        }

        public c a(String str, DialogInterface.OnClickListener onClickListener) {
            this.f16552d = str;
            this.f16561m = onClickListener;
            return this;
        }

        public c b(String str, DialogInterface.OnClickListener onClickListener) {
            this.f16553e = str;
            this.f16560l = onClickListener;
            return this;
        }

        public a a() {
            return new a(this, null);
        }
    }

    /* synthetic */ a(c cVar, ViewOnClickListenerC0135a viewOnClickListenerC0135a) {
        this(cVar);
    }

    public void c() {
        AlertDialog alertDialog = this.f16542n;
        if (alertDialog != null) {
            alertDialog.show();
        }
    }

    @Override // android.content.DialogInterface
    public void cancel() {
        AlertDialog alertDialog = this.f16542n;
        if (alertDialog != null) {
            alertDialog.cancel();
        }
    }

    @Override // android.content.DialogInterface
    public void dismiss() {
        AlertDialog alertDialog = this.f16542n;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.f16542n.dismiss();
    }

    private a(c cVar) {
        this.f16529a = cVar.f16549a;
        this.f16530b = cVar.f16550b;
        this.f16531c = cVar.f16551c;
        this.f16532d = cVar.f16553e;
        this.f16533e = cVar.f16552d;
        this.f16534f = cVar.f16554f;
        this.f16535g = cVar.f16555g;
        this.f16536h = cVar.f16557i;
        this.f16537i = cVar.f16556h;
        this.f16538j = cVar.f16558j;
        this.f16539k = cVar.f16559k;
        this.f16540l = cVar.f16560l;
        this.f16541m = cVar.f16561m;
        a();
    }

    @SuppressLint({"SetTextI18n"})
    private void a() {
        if (this.f16529a != null) {
            this.f16542n = new AlertDialog.Builder(this.f16529a, R.style.BottomFullDialogStyle).create();
            View viewInflate = LayoutInflater.from(this.f16529a).inflate(R.layout.scankit_dialog_custom, (ViewGroup) null);
            Window window = this.f16542n.getWindow();
            if (window != null) {
                window.setGravity(this.f16539k);
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.y = 16;
                window.setAttributes(attributes);
            }
            this.f16543o = (TextView) viewInflate.findViewById(R.id.dialog_title);
            this.f16544p = (TextView) viewInflate.findViewById(R.id.dialog_message);
            this.f16545q = (TextView) viewInflate.findViewById(R.id.dialog_negative);
            this.f16546r = (TextView) viewInflate.findViewById(R.id.dialog_positive);
            this.f16542n.setView(viewInflate);
            CharSequence charSequence = this.f16530b;
            if (charSequence != null) {
                this.f16543o.setText(charSequence);
            }
            this.f16542n.setCanceledOnTouchOutside(false);
            this.f16543o.setMovementMethod(LinkMovementMethod.getInstance());
            this.f16544p.setMovementMethod(LinkMovementMethod.getInstance());
            this.f16544p.setText(this.f16531c);
            b();
        }
    }

    private void b() {
        this.f16545q.setText(this.f16533e);
        int i2 = this.f16537i;
        if (i2 != 0) {
            this.f16545q.setTextColor(i2);
        }
        this.f16545q.setOnClickListener(new ViewOnClickListenerC0135a());
        if (TextUtils.isEmpty(this.f16533e)) {
            this.f16545q.setVisibility(8);
        } else {
            this.f16545q.setVisibility(0);
        }
        this.f16546r.setText(this.f16532d);
        int i3 = this.f16536h;
        if (i3 != 0) {
            this.f16546r.setTextColor(i3);
        }
        this.f16546r.setOnClickListener(new b());
        if (TextUtils.isEmpty(this.f16532d)) {
            this.f16546r.setVisibility(8);
        } else {
            this.f16546r.setVisibility(0);
        }
        this.f16542n.setCancelable(this.f16538j);
    }
}
