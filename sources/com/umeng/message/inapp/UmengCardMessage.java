package com.umeng.message.inapp;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.firebase.messaging.Constants;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.message.proguard.a;
import com.umeng.message.proguard.ai;
import com.umeng.message.proguard.bb;
import com.umeng.message.proguard.bd;
import com.umeng.message.proguard.f;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class UmengCardMessage extends DialogFragment {

    /* renamed from: b, reason: collision with root package name */
    private static final String f22646b = "com.umeng.message.inapp.UmengCardMessage";

    /* renamed from: a, reason: collision with root package name */
    public IUmengInAppMsgCloseCallback f22647a;

    /* renamed from: c, reason: collision with root package name */
    private Activity f22648c;

    /* renamed from: d, reason: collision with root package name */
    private UInAppMessage f22649d;

    /* renamed from: e, reason: collision with root package name */
    private String f22650e;

    /* renamed from: f, reason: collision with root package name */
    private Bitmap f22651f;

    /* renamed from: g, reason: collision with root package name */
    private ViewGroup f22652g;

    /* renamed from: h, reason: collision with root package name */
    private int f22653h;

    /* renamed from: i, reason: collision with root package name */
    private int f22654i;

    /* renamed from: j, reason: collision with root package name */
    private int f22655j;

    /* renamed from: k, reason: collision with root package name */
    private int f22656k;

    /* renamed from: l, reason: collision with root package name */
    private UInAppHandler f22657l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f22658m = false;

    /* renamed from: n, reason: collision with root package name */
    private boolean f22659n = false;

    /* renamed from: o, reason: collision with root package name */
    private boolean f22660o = false;

    /* renamed from: p, reason: collision with root package name */
    private boolean f22661p = false;

    /* renamed from: q, reason: collision with root package name */
    private String[] f22662q = {"18", "16", "16"};

    static /* synthetic */ boolean a(UmengCardMessage umengCardMessage) {
        umengCardMessage.f22659n = true;
        return true;
    }

    static /* synthetic */ boolean e(UmengCardMessage umengCardMessage) {
        umengCardMessage.f22661p = true;
        return true;
    }

    static /* synthetic */ boolean f(UmengCardMessage umengCardMessage) {
        umengCardMessage.f22660o = true;
        return true;
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.f22652g != null) {
            RelativeLayout.LayoutParams layoutParams = configuration.orientation == 1 ? new RelativeLayout.LayoutParams(-1, -2) : new RelativeLayout.LayoutParams(-2, -1);
            int iA = bb.a(30.0f);
            int iA2 = bb.a(15.0f);
            layoutParams.setMargins(iA, iA2, iA, iA2);
            layoutParams.addRule(13);
            this.f22652g.setLayoutParams(layoutParams);
        }
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, R.style.Theme.NoTitleBar);
        setRetainInstance(true);
        try {
            this.f22648c = getActivity();
            Bundle arguments = getArguments();
            this.f22649d = new UInAppMessage(new JSONObject(arguments.getString("msg")));
            this.f22650e = arguments.getString(Constants.ScionAnalytics.PARAM_LABEL);
            byte[] byteArray = arguments.getByteArray("bitmapByte");
            if (byteArray != null) {
                this.f22651f = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            }
            this.f22657l = InAppMessageManager.getInstance(this.f22648c).getInAppHandler();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        int i2 = this.f22649d.msg_type;
        if (i2 == 5 || i2 == 6) {
            String strA = InAppMessageManager.getInstance(this.f22648c).a("KEY_PLAIN_TEXT_SIZE", "");
            String[] strArrSplit = !TextUtils.isEmpty(strA) ? strA.split(",") : null;
            if (strArrSplit != null) {
                this.f22662q = strArrSplit;
            }
        }
    }

    @Override // android.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Dialog dialogOnCreateDialog = super.onCreateDialog(bundle);
        dialogOnCreateDialog.requestWindowFeature(1);
        return dialogOnCreateDialog;
    }

    @Override // android.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Rect rect;
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
            rect = new Rect();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            int iHeight = rect.height() - bb.a(65.0f);
            this.f22654i = iHeight;
            this.f22653h = (int) (iHeight * 1.2d);
            int iWidth = rect.width() - bb.a(70.0f);
            this.f22655j = iWidth;
            this.f22656k = (iWidth / 2) * 3;
        } else {
            rect = null;
        }
        int i2 = this.f22649d.msg_type;
        if (i2 == 2 || i2 == 3) {
            RelativeLayout relativeLayout = new RelativeLayout(this.f22648c);
            relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            relativeLayout.setBackgroundColor(Color.parseColor("#33000000"));
            RelativeLayout.LayoutParams layoutParams = getResources().getConfiguration().orientation == 1 ? new RelativeLayout.LayoutParams(-1, -2) : this.f22649d.msg_type == 2 ? new RelativeLayout.LayoutParams(this.f22653h, this.f22654i) : new RelativeLayout.LayoutParams(-2, -1);
            int iA = bb.a(30.0f);
            int iA2 = bb.a(15.0f);
            layoutParams.setMargins(iA, iA2, iA, iA2);
            layoutParams.addRule(13);
            FrameLayout frameLayout = new FrameLayout(this.f22648c);
            this.f22652g = frameLayout;
            frameLayout.setLayoutParams(layoutParams);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
            int iA3 = bb.a(12.0f);
            layoutParams2.setMargins(iA3, iA3, iA3, iA3);
            ImageView imageView = new ImageView(this.f22648c);
            imageView.setLayoutParams(layoutParams2);
            imageView.setAdjustViewBounds(true);
            imageView.setId(f.a());
            imageView.setImageBitmap(this.f22651f);
            this.f22652g.addView(imageView);
            int iA4 = bb.a(24.0f);
            FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(iA4, iA4, 5);
            bd bdVar = new bd(this.f22648c);
            bdVar.setLayoutParams(layoutParams3);
            this.f22652g.addView(bdVar);
            relativeLayout.addView(this.f22652g);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.message.inapp.UmengCardMessage.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UmengCardMessage.a(UmengCardMessage.this);
                    if (TextUtils.equals("none", UmengCardMessage.this.f22649d.action_type)) {
                        return;
                    }
                    UmengCardMessage.this.f22657l.handleInAppMessage(UmengCardMessage.this.f22648c, UmengCardMessage.this.f22649d, 16);
                    UmengCardMessage.this.dismiss();
                }
            });
            bdVar.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.message.inapp.UmengCardMessage.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UmengCardMessage.e(UmengCardMessage.this);
                    UmengCardMessage.this.dismiss();
                }
            });
            return relativeLayout;
        }
        if (i2 == 4) {
            View viewInflate = layoutInflater.inflate(a.a(a.a().f22703a, "umeng_custom_card_message"), viewGroup, false);
            ImageView imageView2 = (ImageView) viewInflate.findViewById(a.a("umeng_card_message_image"));
            Button button = (Button) viewInflate.findViewById(a.a("umeng_card_message_ok"));
            Button button2 = (Button) viewInflate.findViewById(a.a("umeng_card_message_close"));
            imageView2.setImageBitmap(this.f22651f);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.message.inapp.UmengCardMessage.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UmengCardMessage.a(UmengCardMessage.this);
                    if (TextUtils.equals("none", UmengCardMessage.this.f22649d.action_type)) {
                        return;
                    }
                    UmengCardMessage.this.f22657l.handleInAppMessage(UmengCardMessage.this.f22648c, UmengCardMessage.this.f22649d, 16);
                    UmengCardMessage.this.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.message.inapp.UmengCardMessage.5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UmengCardMessage.f(UmengCardMessage.this);
                    if (TextUtils.equals("none", UmengCardMessage.this.f22649d.action_type)) {
                        return;
                    }
                    UmengCardMessage.this.f22657l.handleInAppMessage(UmengCardMessage.this.f22648c, UmengCardMessage.this.f22649d, 19);
                    UmengCardMessage.this.dismiss();
                }
            });
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.message.inapp.UmengCardMessage.6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UmengCardMessage.e(UmengCardMessage.this);
                    UmengCardMessage.this.dismiss();
                }
            });
            return viewInflate;
        }
        if ((i2 != 5 && i2 != 6) || rect == null) {
            return null;
        }
        RelativeLayout relativeLayout2 = new RelativeLayout(this.f22648c);
        relativeLayout2.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        relativeLayout2.setBackgroundColor(Color.parseColor("#33000000"));
        if (getResources().getConfiguration().orientation == 1) {
            int iWidth2 = rect.width() - bb.a(70.0f);
            this.f22655j = iWidth2;
            if (this.f22649d.msg_type == 5) {
                this.f22656k = (iWidth2 / 6) * 5;
            } else {
                this.f22656k = (iWidth2 / 2) * 3;
            }
        } else {
            int iHeight2 = rect.height() - bb.a(65.0f);
            this.f22656k = iHeight2;
            this.f22655j = (iHeight2 / 5) * 6;
        }
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(this.f22655j, this.f22656k);
        layoutParams4.addRule(13);
        LinearLayout linearLayout = new LinearLayout(this.f22648c);
        linearLayout.setLayoutParams(layoutParams4);
        linearLayout.setGravity(1);
        linearLayout.setOrientation(1);
        linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, -2);
        int iA5 = bb.a(20.0f);
        layoutParams5.setMargins(iA5, iA5, iA5, iA5);
        TextView textView = new TextView(this.f22648c);
        textView.setLayoutParams(layoutParams5);
        textView.setGravity(17);
        textView.setText(this.f22649d.title);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        textView.setTextSize(Integer.parseInt(this.f22662q[0]));
        textView.setTextColor(Color.parseColor("#000000"));
        linearLayout.addView(textView);
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-1, 0);
        layoutParams6.setMargins(iA5, 0, iA5, 0);
        layoutParams6.weight = 1.0f;
        ScrollView scrollView = new ScrollView(this.f22648c);
        scrollView.setLayoutParams(layoutParams6);
        scrollView.setScrollBarStyle(16777216);
        scrollView.setVerticalScrollBarEnabled(false);
        TextView textView2 = new TextView(this.f22648c);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        textView2.setText(this.f22649d.content);
        textView2.setTextSize(Integer.parseInt(this.f22662q[1]));
        textView2.setTextColor(Color.parseColor("#000000"));
        scrollView.addView(textView2);
        linearLayout.addView(scrollView);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setStroke(bb.a(1.0f), Color.parseColor("#D8D8D8"));
        gradientDrawable.setCornerRadius(20.0f);
        gradientDrawable.setColor(-1);
        LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-1, bb.a(35.0f));
        layoutParams7.setMargins(iA5, bb.a(30.0f), iA5, iA5);
        TextView textView3 = new TextView(this.f22648c);
        textView3.setLayoutParams(layoutParams7);
        textView3.setGravity(17);
        textView3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        textView3.setText(this.f22649d.button_text);
        textView3.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        textView3.setTextSize(Integer.parseInt(this.f22662q[2]));
        textView3.setTextColor(Color.parseColor("#000000"));
        textView3.setBackgroundDrawable(gradientDrawable);
        linearLayout.addView(textView3);
        relativeLayout2.addView(linearLayout);
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.umeng.message.inapp.UmengCardMessage.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UmengCardMessage.a(UmengCardMessage.this);
                UmengCardMessage.this.f22657l.handleInAppMessage(UmengCardMessage.this.f22648c, UmengCardMessage.this.f22649d, 18);
                UmengCardMessage.this.dismiss();
            }
        });
        return relativeLayout2;
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        ai aiVarA = ai.a(this.f22648c);
        UInAppMessage uInAppMessage = this.f22649d;
        aiVarA.a(uInAppMessage.msg_id, uInAppMessage.msg_type, 0, this.f22659n ? 1 : 0, 0, 0, this.f22661p ? 1 : 0, 0, this.f22660o ? 1 : 0);
        this.f22661p = false;
        this.f22659n = false;
        this.f22660o = false;
        this.f22658m = false;
        IUmengInAppMsgCloseCallback iUmengInAppMsgCloseCallback = this.f22647a;
        if (iUmengInAppMsgCloseCallback != null) {
            iUmengInAppMsgCloseCallback.onClose();
        }
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public final void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public final void onStart() {
        super.onStart();
        if (!this.f22658m) {
            ai aiVarA = ai.a(this.f22648c);
            UInAppMessage uInAppMessage = this.f22649d;
            aiVarA.a(uInAppMessage.msg_id, uInAppMessage.msg_type, 1, 0, 0, 0, 0, 0, 0);
        }
        this.f22658m = true;
    }
}
