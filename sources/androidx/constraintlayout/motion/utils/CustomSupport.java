package androidx.constraintlayout.motion.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.widget.ConstraintAttribute;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class CustomSupport {
    private static final String TAG = "CustomSupport";

    /* renamed from: androidx.constraintlayout.motion.utils.CustomSupport$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2980a;

        static {
            int[] iArr = new int[ConstraintAttribute.AttributeType.values().length];
            f2980a = iArr;
            try {
                iArr[ConstraintAttribute.AttributeType.INT_TYPE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2980a[ConstraintAttribute.AttributeType.FLOAT_TYPE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2980a[ConstraintAttribute.AttributeType.COLOR_DRAWABLE_TYPE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2980a[ConstraintAttribute.AttributeType.COLOR_TYPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f2980a[ConstraintAttribute.AttributeType.STRING_TYPE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f2980a[ConstraintAttribute.AttributeType.BOOLEAN_TYPE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f2980a[ConstraintAttribute.AttributeType.DIMENSION_TYPE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private static int clamp(int i2) {
        int i3 = (i2 & (~(i2 >> 31))) - 255;
        return (i3 & (i3 >> 31)) + 255;
    }

    public static void setInterpolatedValue(ConstraintAttribute constraintAttribute, View view, float[] fArr) {
        Class<?> cls = view.getClass();
        String str = TmpConstant.PROPERTY_IDENTIFIER_SET + constraintAttribute.getName();
        try {
            try {
                try {
                    switch (AnonymousClass1.f2980a[constraintAttribute.getType().ordinal()]) {
                        case 1:
                            cls.getMethod(str, Integer.TYPE).invoke(view, Integer.valueOf((int) fArr[0]));
                            return;
                        case 2:
                            cls.getMethod(str, Float.TYPE).invoke(view, Float.valueOf(fArr[0]));
                            return;
                        case 3:
                            Method method = cls.getMethod(str, Drawable.class);
                            int iClamp = (clamp((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (fArr[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8) | clamp((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f));
                            ColorDrawable colorDrawable = new ColorDrawable();
                            colorDrawable.setColor(iClamp);
                            method.invoke(view, colorDrawable);
                            return;
                        case 4:
                            try {
                                cls.getMethod(str, Integer.TYPE).invoke(view, Integer.valueOf(clamp((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f)) | (clamp((int) (fArr[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8)));
                                return;
                            } catch (IllegalAccessException e2) {
                                e = e2;
                                Log.e(TAG, "cannot access method " + str + " on View \"" + Debug.getName(view) + "\"");
                                e.printStackTrace();
                                return;
                            } catch (NoSuchMethodException e3) {
                                e = e3;
                                Log.e(TAG, "no method " + str + " on View \"" + Debug.getName(view) + "\"");
                                e.printStackTrace();
                                return;
                            }
                        case 5:
                            throw new RuntimeException("unable to interpolate strings " + constraintAttribute.getName());
                        case 6:
                            cls.getMethod(str, Boolean.TYPE).invoke(view, Boolean.valueOf(fArr[0] > 0.5f));
                            return;
                        case 7:
                            cls.getMethod(str, Float.TYPE).invoke(view, Float.valueOf(fArr[0]));
                            return;
                        default:
                            return;
                    }
                } catch (IllegalAccessException e4) {
                    e = e4;
                } catch (NoSuchMethodException e5) {
                    e = e5;
                }
            } catch (IllegalAccessException e6) {
                e = e6;
            } catch (NoSuchMethodException e7) {
                e = e7;
            }
        } catch (InvocationTargetException e8) {
            e8.printStackTrace();
        }
    }
}
