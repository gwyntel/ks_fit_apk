package com.aliyun.iot.link.ui.component.statusview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.aliyun.iot.link.ui.component.R;

/* loaded from: classes3.dex */
public class LinkLoadingStatusFragment extends Fragment {
    public static final String ARGS_CANCELABLE = "cancelable";
    public static final String ARGS_ICON_COLOR = "icon_color";
    public static final String ARGS_LOADING_MESSAGE = "message";
    public static final String TAG = "1563175434";
    private View dimmedView;
    private ImageView loadingIcon;
    private TextView loadingTextView;
    private int iconTintColor = Color.parseColor("#0079ff");
    private ObjectAnimator rotateAnimator = null;

    private void assignViews(@Nullable View view) {
        if (view == null) {
            return;
        }
        this.dimmedView = view.findViewById(R.id.link_status_loading_bg);
        this.loadingIcon = (ImageView) view.findViewById(R.id.link_status_loading_icon);
        this.loadingTextView = (TextView) view.findViewById(R.id.link_status_loading_text);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void show(@androidx.annotation.NonNull androidx.fragment.app.FragmentActivity r6, @androidx.annotation.StringRes int r7, @androidx.annotation.IdRes int r8, @androidx.annotation.ColorInt int r9, boolean r10, boolean r11) {
        /*
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            if (r7 == 0) goto L10
            java.lang.String r1 = "message"
            java.lang.String r2 = r6.getString(r7)
            r0.putString(r1, r2)
        L10:
            java.lang.String r1 = "icon_color"
            r0.putInt(r1, r9)
            java.lang.String r9 = "cancelable"
            r0.putBoolean(r9, r10)
            androidx.fragment.app.FragmentManager r9 = r6.getSupportFragmentManager()
            java.lang.String r1 = "1563175434"
            androidx.fragment.app.Fragment r2 = r9.findFragmentByTag(r1)
            boolean r3 = r2 instanceof com.aliyun.iot.link.ui.component.statusview.LinkLoadingStatusFragment
            r4 = 0
            if (r3 == 0) goto L31
            boolean r3 = r2.isAdded()
            if (r3 == 0) goto L31
            r3 = 1
            goto L32
        L31:
            r3 = r4
        L32:
            if (r3 == 0) goto L84
            r5 = r2
            com.aliyun.iot.link.ui.component.statusview.LinkLoadingStatusFragment r5 = (com.aliyun.iot.link.ui.component.statusview.LinkLoadingStatusFragment) r5
            r5.setCancelable(r10)
            if (r7 == 0) goto L44
            java.lang.String r6 = r6.getString(r7)
            r5.setLoadingText(r6)
            goto L48
        L44:
            r6 = 0
            r5.setLoadingText(r6)
        L48:
            boolean r6 = r2.isVisible()
            if (r6 != 0) goto L84
            boolean r6 = r2.isHidden()
            if (r6 == 0) goto L60
            androidx.fragment.app.FragmentTransaction r6 = r9.beginTransaction()
            androidx.fragment.app.FragmentTransaction r6 = r6.show(r2)
            r6.commitAllowingStateLoss()
            goto L84
        L60:
            android.view.View r6 = r2.getView()
            if (r6 == 0) goto L78
            android.view.View r6 = r2.getView()
            android.os.IBinder r6 = r6.getWindowToken()
            if (r6 == 0) goto L78
            android.view.View r6 = r2.getView()
            r6.setVisibility(r4)
            goto L84
        L78:
            androidx.fragment.app.FragmentTransaction r6 = r9.beginTransaction()
            androidx.fragment.app.FragmentTransaction r6 = r6.remove(r2)
            r6.commitAllowingStateLoss()
            goto L85
        L84:
            r4 = r3
        L85:
            if (r4 != 0) goto La3
            com.aliyun.iot.link.ui.component.statusview.LinkLoadingStatusFragment r6 = new com.aliyun.iot.link.ui.component.statusview.LinkLoadingStatusFragment
            r6.<init>()
            r6.setArguments(r0)
            androidx.fragment.app.FragmentTransaction r7 = r9.beginTransaction()
            androidx.fragment.app.FragmentTransaction r6 = r7.replace(r8, r6, r1)
            r6.disallowAddToBackStack()
            if (r11 == 0) goto La0
            r6.commitAllowingStateLoss()
            goto La3
        La0:
            r6.commit()
        La3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.iot.link.ui.component.statusview.LinkLoadingStatusFragment.show(androidx.fragment.app.FragmentActivity, int, int, int, boolean, boolean):void");
    }

    public void cancelLoadingAnimation() {
        if (getView() == null) {
            return;
        }
        Log.d(TAG, "cancelLoadingAnimation: ");
        ObjectAnimator objectAnimator = this.rotateAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        startLoadingAnimation();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.link_loading_status_fragment, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        cancelLoadingAnimation();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        ObjectAnimator objectAnimator = this.rotateAnimator;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            return;
        }
        this.rotateAnimator.pause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        ObjectAnimator objectAnimator = this.rotateAnimator;
        if (objectAnimator == null || !objectAnimator.isPaused()) {
            return;
        }
        this.rotateAnimator.resume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        Log.d(TAG, "onViewCreated: ");
        assignViews(view);
        setCancelable(getArguments().getBoolean(ARGS_CANCELABLE, false));
        setLoadingText(getArguments().getString("message"));
        this.loadingIcon.setImageTintList(ColorStateList.valueOf(getArguments().getInt(ARGS_ICON_COLOR)));
        startLoadingAnimation();
    }

    public void setCancelable(final boolean z2) {
        if (this.loadingTextView == null && getView() != null) {
            assignViews(getView());
        }
        View view = this.dimmedView;
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.statusview.LinkLoadingStatusFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (!z2 || LinkLoadingStatusFragment.this.getActivity() == null) {
                        return;
                    }
                    LinkLoadingStatusFragment.this.getActivity().getSupportFragmentManager().beginTransaction().remove(LinkLoadingStatusFragment.this).setTransition(4099).commitAllowingStateLoss();
                }
            });
        }
    }

    public void setLoadingText(String str) {
        if (this.loadingTextView == null && getView() != null) {
            assignViews(getView());
        }
        if (this.loadingTextView != null) {
            if (TextUtils.isEmpty(str)) {
                this.loadingTextView.setVisibility(8);
            } else {
                this.loadingTextView.setText(str);
            }
        }
    }

    public void startLoadingAnimation() {
        if (getView() == null || this.loadingIcon == null) {
            return;
        }
        Log.d(TAG, "startLoadingAnimation: ");
        ObjectAnimator objectAnimator = this.rotateAnimator;
        if (objectAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.loadingIcon, (Property<ImageView, Float>) View.ROTATION, 0.0f, 720.0f);
            this.rotateAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(1000L);
            this.rotateAnimator.setInterpolator(new LinearInterpolator());
            this.rotateAnimator.setRepeatCount(-1);
        } else {
            objectAnimator.end();
        }
        this.rotateAnimator.start();
    }
}
