package com.facebook.appevents.codeless.internal;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.NestedScrollingChild;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.facebook.internal.Utility;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ViewHierarchy {
    private static final String CLASS_RCTROOTVIEW = "com.facebook.react.ReactRootView";
    private static final String CLASS_RCTTEXTVIEW = "com.facebook.react.views.view.ReactTextView";
    private static final String CLASS_RCTVIEWGROUP = "com.facebook.react.views.view.ReactViewGroup";
    private static final String CLASS_TOUCHTARGETHELPER = "com.facebook.react.uimanager.TouchTargetHelper";
    private static final int ICON_MAX_EDGE_LENGTH = 44;
    private static final String METHOD_FIND_TOUCHTARGET_VIEW = "findTouchTargetView";
    private static final String TAG = "com.facebook.appevents.codeless.internal.ViewHierarchy";
    private static WeakReference<View> RCTRootViewReference = new WeakReference<>(null);

    @Nullable
    private static Method methodFindTouchTargetView = null;

    @Nullable
    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static View findRCTRootView(View view) {
        while (view != null) {
            if (isRCTRootView(view)) {
                return view;
            }
            Object parent = view.getParent();
            if (!(parent instanceof View)) {
                return null;
            }
            view = (View) parent;
        }
        return null;
    }

    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static List<View> getChildrenOfView(View view) {
        ArrayList arrayList = new ArrayList();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                arrayList.add(viewGroup.getChildAt(i2));
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0033 A[PHI: r1
      0x0033: PHI (r1v12 int) = (r1v11 int), (r1v13 int) binds: [B:15:0x001f, B:20:0x002c] A[DONT_GENERATE, DONT_INLINE]] */
    @androidx.annotation.RestrictTo({androidx.annotation.RestrictTo.Scope.GROUP_ID})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getClassTypeBitmask(android.view.View r3) {
        /*
            boolean r0 = r3 instanceof android.widget.ImageView
            if (r0 == 0) goto L6
            r0 = 2
            goto L7
        L6:
            r0 = 0
        L7:
            boolean r1 = r3.isClickable()
            if (r1 == 0) goto Lf
            r0 = r0 | 32
        Lf:
            boolean r1 = isAdapterViewItem(r3)
            if (r1 == 0) goto L17
            r0 = r0 | 512(0x200, float:7.17E-43)
        L17:
            boolean r1 = r3 instanceof android.widget.TextView
            if (r1 == 0) goto L3b
            r1 = r0 | 1025(0x401, float:1.436E-42)
            boolean r2 = r3 instanceof android.widget.Button
            if (r2 == 0) goto L33
            r1 = r0 | 1029(0x405, float:1.442E-42)
            boolean r2 = r3 instanceof android.widget.Switch
            if (r2 == 0) goto L2a
            r0 = r0 | 9221(0x2405, float:1.2921E-41)
            goto L34
        L2a:
            boolean r2 = r3 instanceof android.widget.CheckBox
            if (r2 == 0) goto L33
            r1 = 33797(0x8405, float:4.736E-41)
            r0 = r0 | r1
            goto L34
        L33:
            r0 = r1
        L34:
            boolean r3 = r3 instanceof android.widget.EditText
            if (r3 == 0) goto L6a
            r0 = r0 | 2048(0x800, float:2.87E-42)
            goto L6a
        L3b:
            boolean r1 = r3 instanceof android.widget.Spinner
            if (r1 != 0) goto L68
            boolean r1 = r3 instanceof android.widget.DatePicker
            if (r1 == 0) goto L44
            goto L68
        L44:
            boolean r1 = r3 instanceof android.widget.RatingBar
            if (r1 == 0) goto L4c
            r3 = 65536(0x10000, float:9.1835E-41)
            r0 = r0 | r3
            goto L6a
        L4c:
            boolean r1 = r3 instanceof android.widget.RadioGroup
            if (r1 == 0) goto L53
            r0 = r0 | 16384(0x4000, float:2.2959E-41)
            goto L6a
        L53:
            boolean r1 = r3 instanceof android.view.ViewGroup
            if (r1 == 0) goto L6a
            java.lang.ref.WeakReference<android.view.View> r1 = com.facebook.appevents.codeless.internal.ViewHierarchy.RCTRootViewReference
            java.lang.Object r1 = r1.get()
            android.view.View r1 = (android.view.View) r1
            boolean r3 = isRCTButton(r3, r1)
            if (r3 == 0) goto L6a
            r0 = r0 | 64
            goto L6a
        L68:
            r0 = r0 | 4096(0x1000, float:5.74E-42)
        L6a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.codeless.internal.ViewHierarchy.getClassTypeBitmask(android.view.View):int");
    }

    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static JSONObject getDictionaryOfView(View view) throws JSONException {
        if (view.getClass().getName().equals(CLASS_RCTROOTVIEW)) {
            RCTRootViewReference = new WeakReference<>(view);
        }
        JSONObject jSONObject = new JSONObject();
        try {
            updateBasicInfoOfView(view, jSONObject);
            JSONArray jSONArray = new JSONArray();
            List<View> childrenOfView = getChildrenOfView(view);
            for (int i2 = 0; i2 < childrenOfView.size(); i2++) {
                jSONArray.put(getDictionaryOfView(childrenOfView.get(i2)));
            }
            jSONObject.put(ViewHierarchyConstants.CHILDREN_VIEW_KEY, jSONArray);
        } catch (JSONException e2) {
            Log.e(TAG, "Failed to create JSONObject for view.", e2);
        }
        return jSONObject;
    }

    private static JSONObject getDimensionOfView(View view) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ViewHierarchyConstants.DIMENSION_TOP_KEY, view.getTop());
            jSONObject.put("left", view.getLeft());
            jSONObject.put(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, view.getWidth());
            jSONObject.put(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, view.getHeight());
            jSONObject.put(ViewHierarchyConstants.DIMENSION_SCROLL_X_KEY, view.getScrollX());
            jSONObject.put(ViewHierarchyConstants.DIMENSION_SCROLL_Y_KEY, view.getScrollY());
            jSONObject.put("visibility", view.getVisibility());
        } catch (JSONException e2) {
            Log.e(TAG, "Failed to create JSONObject for dimension.", e2);
        }
        return jSONObject;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static View.OnClickListener getExistingOnClickListener(View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Field declaredField;
        try {
            Field declaredField2 = Class.forName("android.view.View").getDeclaredField("mListenerInfo");
            if (declaredField2 != null) {
                declaredField2.setAccessible(true);
            }
            Object obj = declaredField2.get(view);
            if (obj == null || (declaredField = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnClickListener")) == null) {
                return null;
            }
            declaredField.setAccessible(true);
            return (View.OnClickListener) declaredField.get(obj);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException unused) {
            return null;
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static View.OnTouchListener getExistingOnTouchListener(View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Field declaredField;
        try {
            Field declaredField2 = Class.forName("android.view.View").getDeclaredField("mListenerInfo");
            if (declaredField2 != null) {
                declaredField2.setAccessible(true);
            }
            Object obj = declaredField2.get(view);
            if (obj == null || (declaredField = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnTouchListener")) == null) {
                return null;
            }
            declaredField.setAccessible(true);
            return (View.OnTouchListener) declaredField.get(obj);
        } catch (ClassNotFoundException e2) {
            Utility.logd(TAG, e2);
            return null;
        } catch (IllegalAccessException e3) {
            Utility.logd(TAG, e3);
            return null;
        } catch (NoSuchFieldException e4) {
            Utility.logd(TAG, e4);
            return null;
        }
    }

    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static String getHintOfView(View view) {
        CharSequence hint = view instanceof EditText ? ((EditText) view).getHint() : view instanceof TextView ? ((TextView) view).getHint() : null;
        return hint == null ? "" : hint.toString();
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static ViewGroup getParentOfView(View view) {
        if (view == null) {
            return null;
        }
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            return (ViewGroup) parent;
        }
        return null;
    }

    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static String getTextOfView(View view) {
        CharSequence charSequenceValueOf;
        String text;
        Object selectedItem;
        if (view instanceof TextView) {
            charSequenceValueOf = ((TextView) view).getText();
            if (view instanceof Switch) {
                text = ((Switch) view).isChecked() ? "1" : "0";
                charSequenceValueOf = text;
                break;
            }
        } else if (view instanceof Spinner) {
            Spinner spinner = (Spinner) view;
            if (spinner.getCount() > 0 && (selectedItem = spinner.getSelectedItem()) != null) {
                text = selectedItem.toString();
                charSequenceValueOf = text;
                break;
            }
            charSequenceValueOf = null;
        } else if (view instanceof DatePicker) {
            DatePicker datePicker = (DatePicker) view;
            charSequenceValueOf = String.format("%04d-%02d-%02d", Integer.valueOf(datePicker.getYear()), Integer.valueOf(datePicker.getMonth()), Integer.valueOf(datePicker.getDayOfMonth()));
        } else if (view instanceof TimePicker) {
            TimePicker timePicker = (TimePicker) view;
            Integer currentHour = timePicker.getCurrentHour();
            currentHour.intValue();
            Integer currentMinute = timePicker.getCurrentMinute();
            currentMinute.intValue();
            charSequenceValueOf = String.format("%02d:%02d", currentHour, currentMinute);
        } else {
            if (view instanceof RadioGroup) {
                RadioGroup radioGroup = (RadioGroup) view;
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                int childCount = radioGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = radioGroup.getChildAt(i2);
                    if (childAt.getId() == checkedRadioButtonId && (childAt instanceof RadioButton)) {
                        text = ((RadioButton) childAt).getText();
                        charSequenceValueOf = text;
                        break;
                    }
                }
            } else if (view instanceof RatingBar) {
                charSequenceValueOf = String.valueOf(((RatingBar) view).getRating());
            }
            charSequenceValueOf = null;
        }
        return charSequenceValueOf == null ? "" : charSequenceValueOf.toString();
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static View getTouchReactView(float[] fArr, @Nullable View view) throws NoSuchMethodException, SecurityException {
        initTouchTargetHelperMethods();
        Method method = methodFindTouchTargetView;
        if (method != null && view != null) {
            try {
                View view2 = (View) method.invoke(null, fArr, view);
                if (view2 != null && view2.getId() > 0) {
                    View view3 = (View) view2.getParent();
                    if (view3 != null) {
                        return view3;
                    }
                    return null;
                }
            } catch (IllegalAccessException e2) {
                Utility.logd(TAG, e2);
            } catch (InvocationTargetException e3) {
                Utility.logd(TAG, e3);
            }
        }
        return null;
    }

    private static float[] getViewLocationOnScreen(View view) {
        view.getLocationOnScreen(new int[2]);
        return new float[]{r1[0], r1[1]};
    }

    private static void initTouchTargetHelperMethods() throws NoSuchMethodException, SecurityException {
        if (methodFindTouchTargetView != null) {
            return;
        }
        try {
            Method declaredMethod = Class.forName(CLASS_TOUCHTARGETHELPER).getDeclaredMethod(METHOD_FIND_TOUCHTARGET_VIEW, float[].class, ViewGroup.class);
            methodFindTouchTargetView = declaredMethod;
            declaredMethod.setAccessible(true);
        } catch (ClassNotFoundException e2) {
            Utility.logd(TAG, e2);
        } catch (NoSuchMethodException e3) {
            Utility.logd(TAG, e3);
        }
    }

    private static boolean isAdapterViewItem(View view) {
        ViewParent parent = view.getParent();
        return (parent instanceof AdapterView) || (parent instanceof NestedScrollingChild);
    }

    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static boolean isRCTButton(View view, @Nullable View view2) {
        View touchReactView;
        return view.getClass().getName().equals(CLASS_RCTVIEWGROUP) && (touchReactView = getTouchReactView(getViewLocationOnScreen(view), view2)) != null && touchReactView.getId() == view.getId();
    }

    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static boolean isRCTRootView(View view) {
        return view.getClass().getName().equals(CLASS_RCTROOTVIEW);
    }

    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static boolean isRCTTextView(View view) {
        return view.getClass().getName().equals(CLASS_RCTTEXTVIEW);
    }

    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static boolean isRCTViewGroup(View view) {
        return view.getClass().getName().equals(CLASS_RCTVIEWGROUP);
    }

    public static void setOnClickListener(View view, View.OnClickListener onClickListener) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Field declaredField;
        Field declaredField2;
        Object obj = null;
        try {
            try {
                declaredField = Class.forName("android.view.View").getDeclaredField("mListenerInfo");
            } catch (ClassNotFoundException | NoSuchFieldException unused) {
                declaredField = null;
            }
            try {
                declaredField2 = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnClickListener");
            } catch (ClassNotFoundException | NoSuchFieldException unused2) {
                declaredField2 = null;
                if (declaredField != null) {
                }
                view.setOnClickListener(onClickListener);
                return;
            }
            if (declaredField != null || declaredField2 == null) {
                view.setOnClickListener(onClickListener);
                return;
            }
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            try {
                declaredField.setAccessible(true);
                obj = declaredField.get(view);
            } catch (IllegalAccessException unused3) {
            }
            if (obj == null) {
                view.setOnClickListener(onClickListener);
            } else {
                declaredField2.set(obj, onClickListener);
            }
        } catch (Exception unused4) {
        }
    }

    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static void updateAppearanceOfView(View view, JSONObject jSONObject, float f2) throws JSONException {
        Bitmap bitmap;
        TextView textView;
        Typeface typeface;
        try {
            JSONObject jSONObject2 = new JSONObject();
            if ((view instanceof TextView) && (typeface = (textView = (TextView) view).getTypeface()) != null) {
                jSONObject2.put(ViewHierarchyConstants.TEXT_SIZE, textView.getTextSize());
                jSONObject2.put(ViewHierarchyConstants.TEXT_IS_BOLD, typeface.isBold());
                jSONObject2.put(ViewHierarchyConstants.TEXT_IS_ITALIC, typeface.isItalic());
                jSONObject.put(ViewHierarchyConstants.TEXT_STYLE, jSONObject2);
            }
            if (view instanceof ImageView) {
                Drawable drawable = ((ImageView) view).getDrawable();
                if (!(drawable instanceof BitmapDrawable) || view.getHeight() / f2 > 44.0f || view.getWidth() / f2 > 44.0f || (bitmap = ((BitmapDrawable) drawable).getBitmap()) == null) {
                    return;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                jSONObject.put(ViewHierarchyConstants.ICON_BITMAP, Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0));
            }
        } catch (JSONException e2) {
            Utility.logd(TAG, e2);
        }
    }

    @RestrictTo({RestrictTo.Scope.GROUP_ID})
    public static void updateBasicInfoOfView(View view, JSONObject jSONObject) throws JSONException {
        try {
            String textOfView = getTextOfView(view);
            String hintOfView = getHintOfView(view);
            Object tag = view.getTag();
            CharSequence contentDescription = view.getContentDescription();
            jSONObject.put(ViewHierarchyConstants.CLASS_NAME_KEY, view.getClass().getCanonicalName());
            jSONObject.put(ViewHierarchyConstants.CLASS_TYPE_BITMASK_KEY, getClassTypeBitmask(view));
            jSONObject.put("id", view.getId());
            if (SensitiveUserDataUtils.isSensitiveUserData(view)) {
                jSONObject.put("text", "");
                jSONObject.put(ViewHierarchyConstants.IS_USER_INPUT_KEY, true);
            } else {
                jSONObject.put("text", Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(textOfView), ""));
            }
            jSONObject.put(ViewHierarchyConstants.HINT_KEY, Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(hintOfView), ""));
            if (tag != null) {
                jSONObject.put("tag", Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(tag.toString()), ""));
            }
            if (contentDescription != null) {
                jSONObject.put("description", Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(contentDescription.toString()), ""));
            }
            jSONObject.put("dimension", getDimensionOfView(view));
        } catch (JSONException e2) {
            Utility.logd(TAG, e2);
        }
    }
}
