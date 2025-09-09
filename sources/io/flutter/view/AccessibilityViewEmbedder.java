package io.flutter.view;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.accessibility.AccessibilityRecord;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import io.flutter.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Keep
/* loaded from: classes4.dex */
class AccessibilityViewEmbedder {
    private static final String TAG = "AccessibilityBridge";
    private int nextFlutterId;
    private final View rootAccessibilityView;
    private final ReflectionAccessors reflectionAccessors = new ReflectionAccessors();
    private final SparseArray<ViewAndId> flutterIdToOrigin = new SparseArray<>();
    private final Map<ViewAndId, Integer> originToFlutterId = new HashMap();
    private final Map<View, Rect> embeddedViewToDisplayBounds = new HashMap();

    private static class ReflectionAccessors {

        @Nullable
        private final Field childNodeIdsField;

        @Nullable
        private final Method getChildId;

        @Nullable
        private final Method getParentNodeId;

        @Nullable
        private final Method getRecordSourceNodeId;

        @Nullable
        private final Method getSourceNodeId;

        @Nullable
        private final Method longArrayGetIndex;

        /* JADX INFO: Access modifiers changed from: private */
        @Nullable
        public Long getChildId(@NonNull AccessibilityNodeInfo accessibilityNodeInfo, int i2) {
            Method method = this.getChildId;
            if (method == null && (this.childNodeIdsField == null || this.longArrayGetIndex == null)) {
                return null;
            }
            if (method != null) {
                try {
                    return (Long) method.invoke(accessibilityNodeInfo, Integer.valueOf(i2));
                } catch (IllegalAccessException e2) {
                    Log.w(AccessibilityViewEmbedder.TAG, "Failed to access getChildId method.", e2);
                } catch (InvocationTargetException e3) {
                    Log.w(AccessibilityViewEmbedder.TAG, "The getChildId method threw an exception when invoked.", e3);
                }
            } else {
                try {
                    Long l2 = (Long) this.longArrayGetIndex.invoke(this.childNodeIdsField.get(accessibilityNodeInfo), Integer.valueOf(i2));
                    l2.longValue();
                    return l2;
                } catch (ArrayIndexOutOfBoundsException e4) {
                    e = e4;
                    Log.w(AccessibilityViewEmbedder.TAG, "The longArrayGetIndex method threw an exception when invoked.", e);
                    return null;
                } catch (IllegalAccessException e5) {
                    Log.w(AccessibilityViewEmbedder.TAG, "Failed to access longArrayGetIndex method or the childNodeId field.", e5);
                } catch (InvocationTargetException e6) {
                    e = e6;
                    Log.w(AccessibilityViewEmbedder.TAG, "The longArrayGetIndex method threw an exception when invoked.", e);
                    return null;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Nullable
        public Long getParentNodeId(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
            Method method = this.getParentNodeId;
            if (method != null) {
                try {
                    Long l2 = (Long) method.invoke(accessibilityNodeInfo, null);
                    l2.longValue();
                    return l2;
                } catch (IllegalAccessException e2) {
                    Log.w(AccessibilityViewEmbedder.TAG, "Failed to access getParentNodeId method.", e2);
                } catch (InvocationTargetException e3) {
                    Log.w(AccessibilityViewEmbedder.TAG, "The getParentNodeId method threw an exception when invoked.", e3);
                }
            }
            return yoinkParentIdFromParcel(accessibilityNodeInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Nullable
        public Long getRecordSourceNodeId(@NonNull AccessibilityRecord accessibilityRecord) {
            Method method = this.getRecordSourceNodeId;
            if (method == null) {
                return null;
            }
            try {
                return (Long) method.invoke(accessibilityRecord, null);
            } catch (IllegalAccessException e2) {
                Log.w(AccessibilityViewEmbedder.TAG, "Failed to access the getRecordSourceNodeId method.", e2);
                return null;
            } catch (InvocationTargetException e3) {
                Log.w(AccessibilityViewEmbedder.TAG, "The getRecordSourceNodeId method threw an exception when invoked.", e3);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Nullable
        public Long getSourceNodeId(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
            Method method = this.getSourceNodeId;
            if (method == null) {
                return null;
            }
            try {
                return (Long) method.invoke(accessibilityNodeInfo, null);
            } catch (IllegalAccessException e2) {
                Log.w(AccessibilityViewEmbedder.TAG, "Failed to access getSourceNodeId method.", e2);
                return null;
            } catch (InvocationTargetException e3) {
                Log.w(AccessibilityViewEmbedder.TAG, "The getSourceNodeId method threw an exception when invoked.", e3);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int getVirtualNodeId(long j2) {
            return (int) (j2 >> 32);
        }

        private static boolean isBitSet(long j2, int i2) {
            return (j2 & (1 << i2)) != 0;
        }

        @Nullable
        private static Long yoinkParentIdFromParcel(AccessibilityNodeInfo accessibilityNodeInfo) {
            if (Build.VERSION.SDK_INT < 26) {
                Log.w(AccessibilityViewEmbedder.TAG, "Unexpected Android version. Unable to find the parent ID.");
                return null;
            }
            AccessibilityNodeInfo accessibilityNodeInfoObtain = AccessibilityNodeInfo.obtain(accessibilityNodeInfo);
            Parcel parcelObtain = Parcel.obtain();
            parcelObtain.setDataPosition(0);
            accessibilityNodeInfoObtain.writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            long j2 = parcelObtain.readLong();
            if (isBitSet(j2, 0)) {
                parcelObtain.readInt();
            }
            if (isBitSet(j2, 1)) {
                parcelObtain.readLong();
            }
            if (isBitSet(j2, 2)) {
                parcelObtain.readInt();
            }
            Long lValueOf = isBitSet(j2, 3) ? Long.valueOf(parcelObtain.readLong()) : null;
            parcelObtain.recycle();
            return lValueOf;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @SuppressLint({"DiscouragedPrivateApi,PrivateApi"})
        private ReflectionAccessors() throws NoSuchFieldException, NoSuchMethodException, SecurityException {
            Method method;
            Method method2;
            Method method3;
            Method method4;
            Field field;
            Method method5;
            Method method6 = null;
            try {
                method = AccessibilityNodeInfo.class.getMethod("getSourceNodeId", null);
            } catch (NoSuchMethodException unused) {
                Log.w(AccessibilityViewEmbedder.TAG, "can't invoke AccessibilityNodeInfo#getSourceNodeId with reflection");
                method = null;
            }
            try {
                method2 = AccessibilityRecord.class.getMethod("getSourceNodeId", null);
            } catch (NoSuchMethodException unused2) {
                Log.w(AccessibilityViewEmbedder.TAG, "can't invoke AccessibiiltyRecord#getSourceNodeId with reflection");
                method2 = null;
            }
            if (Build.VERSION.SDK_INT <= 26) {
                try {
                    method5 = AccessibilityNodeInfo.class.getMethod("getParentNodeId", null);
                } catch (NoSuchMethodException unused3) {
                    Log.w(AccessibilityViewEmbedder.TAG, "can't invoke getParentNodeId with reflection");
                    method5 = null;
                }
                try {
                    method3 = AccessibilityNodeInfo.class.getMethod("getChildId", Integer.TYPE);
                    method4 = null;
                } catch (NoSuchMethodException unused4) {
                    Log.w(AccessibilityViewEmbedder.TAG, "can't invoke getChildId with reflection");
                    method3 = null;
                    method4 = null;
                }
                field = method4;
                method6 = method5;
            } else {
                try {
                    Field declaredField = AccessibilityNodeInfo.class.getDeclaredField("mChildNodeIds");
                    declaredField.setAccessible(true);
                    method4 = Class.forName("android.util.LongArray").getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, Integer.TYPE);
                    method3 = null;
                    field = declaredField;
                } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | NullPointerException unused5) {
                    Log.w(AccessibilityViewEmbedder.TAG, "can't access childNodeIdsField with reflection");
                    method3 = null;
                    method4 = null;
                    field = 0;
                }
            }
            this.getSourceNodeId = method;
            this.getParentNodeId = method6;
            this.getRecordSourceNodeId = method2;
            this.getChildId = method3;
            this.childNodeIdsField = field;
            this.longArrayGetIndex = method4;
        }
    }

    private static class ViewAndId {
        final int id;
        final View view;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ViewAndId)) {
                return false;
            }
            ViewAndId viewAndId = (ViewAndId) obj;
            return this.id == viewAndId.id && this.view.equals(viewAndId.view);
        }

        public int hashCode() {
            return ((this.view.hashCode() + 31) * 31) + this.id;
        }

        private ViewAndId(View view, int i2) {
            this.view = view;
            this.id = i2;
        }
    }

    AccessibilityViewEmbedder(@NonNull View view, int i2) {
        this.rootAccessibilityView = view;
        this.nextFlutterId = i2;
    }

    private void addChildrenToFlutterNode(@NonNull AccessibilityNodeInfo accessibilityNodeInfo, @NonNull View view, @NonNull AccessibilityNodeInfo accessibilityNodeInfo2) {
        int iIntValue;
        for (int i2 = 0; i2 < accessibilityNodeInfo.getChildCount(); i2++) {
            Long childId = this.reflectionAccessors.getChildId(accessibilityNodeInfo, i2);
            if (childId != null) {
                int virtualNodeId = ReflectionAccessors.getVirtualNodeId(childId.longValue());
                ViewAndId viewAndId = new ViewAndId(view, virtualNodeId);
                if (this.originToFlutterId.containsKey(viewAndId)) {
                    iIntValue = this.originToFlutterId.get(viewAndId).intValue();
                } else {
                    int i3 = this.nextFlutterId;
                    this.nextFlutterId = i3 + 1;
                    cacheVirtualIdMappings(view, virtualNodeId, i3);
                    iIntValue = i3;
                }
                accessibilityNodeInfo2.addChild(this.rootAccessibilityView, iIntValue);
            }
        }
    }

    private void cacheVirtualIdMappings(@NonNull View view, int i2, int i3) {
        ViewAndId viewAndId = new ViewAndId(view, i2);
        this.originToFlutterId.put(viewAndId, Integer.valueOf(i3));
        this.flutterIdToOrigin.put(i3, viewAndId);
    }

    @NonNull
    private AccessibilityNodeInfo convertToFlutterNode(@NonNull AccessibilityNodeInfo accessibilityNodeInfo, int i2, @NonNull View view) {
        AccessibilityNodeInfo accessibilityNodeInfoObtain = AccessibilityNodeInfo.obtain(this.rootAccessibilityView, i2);
        accessibilityNodeInfoObtain.setPackageName(this.rootAccessibilityView.getContext().getPackageName());
        accessibilityNodeInfoObtain.setSource(this.rootAccessibilityView, i2);
        accessibilityNodeInfoObtain.setClassName(accessibilityNodeInfo.getClassName());
        Rect rect = this.embeddedViewToDisplayBounds.get(view);
        copyAccessibilityFields(accessibilityNodeInfo, accessibilityNodeInfoObtain);
        setFlutterNodesTranslateBounds(accessibilityNodeInfo, rect, accessibilityNodeInfoObtain);
        addChildrenToFlutterNode(accessibilityNodeInfo, view, accessibilityNodeInfoObtain);
        setFlutterNodeParent(accessibilityNodeInfo, view, accessibilityNodeInfoObtain);
        return accessibilityNodeInfoObtain;
    }

    private void copyAccessibilityFields(@NonNull AccessibilityNodeInfo accessibilityNodeInfo, @NonNull AccessibilityNodeInfo accessibilityNodeInfo2) {
        accessibilityNodeInfo2.setAccessibilityFocused(accessibilityNodeInfo.isAccessibilityFocused());
        accessibilityNodeInfo2.setCheckable(accessibilityNodeInfo.isCheckable());
        accessibilityNodeInfo2.setChecked(accessibilityNodeInfo.isChecked());
        accessibilityNodeInfo2.setContentDescription(accessibilityNodeInfo.getContentDescription());
        accessibilityNodeInfo2.setEnabled(accessibilityNodeInfo.isEnabled());
        accessibilityNodeInfo2.setClickable(accessibilityNodeInfo.isClickable());
        accessibilityNodeInfo2.setFocusable(accessibilityNodeInfo.isFocusable());
        accessibilityNodeInfo2.setFocused(accessibilityNodeInfo.isFocused());
        accessibilityNodeInfo2.setLongClickable(accessibilityNodeInfo.isLongClickable());
        accessibilityNodeInfo2.setMovementGranularities(accessibilityNodeInfo.getMovementGranularities());
        accessibilityNodeInfo2.setPassword(accessibilityNodeInfo.isPassword());
        accessibilityNodeInfo2.setScrollable(accessibilityNodeInfo.isScrollable());
        accessibilityNodeInfo2.setSelected(accessibilityNodeInfo.isSelected());
        accessibilityNodeInfo2.setText(accessibilityNodeInfo.getText());
        accessibilityNodeInfo2.setVisibleToUser(accessibilityNodeInfo.isVisibleToUser());
        accessibilityNodeInfo2.setEditable(accessibilityNodeInfo.isEditable());
        accessibilityNodeInfo2.setCanOpenPopup(accessibilityNodeInfo.canOpenPopup());
        accessibilityNodeInfo2.setCollectionInfo(accessibilityNodeInfo.getCollectionInfo());
        accessibilityNodeInfo2.setCollectionItemInfo(accessibilityNodeInfo.getCollectionItemInfo());
        accessibilityNodeInfo2.setContentInvalid(accessibilityNodeInfo.isContentInvalid());
        accessibilityNodeInfo2.setDismissable(accessibilityNodeInfo.isDismissable());
        accessibilityNodeInfo2.setInputType(accessibilityNodeInfo.getInputType());
        accessibilityNodeInfo2.setLiveRegion(accessibilityNodeInfo.getLiveRegion());
        accessibilityNodeInfo2.setMultiLine(accessibilityNodeInfo.isMultiLine());
        accessibilityNodeInfo2.setRangeInfo(accessibilityNodeInfo.getRangeInfo());
        accessibilityNodeInfo2.setError(accessibilityNodeInfo.getError());
        accessibilityNodeInfo2.setMaxTextLength(accessibilityNodeInfo.getMaxTextLength());
        int i2 = Build.VERSION.SDK_INT;
        accessibilityNodeInfo2.setContextClickable(accessibilityNodeInfo.isContextClickable());
        if (i2 >= 24) {
            accessibilityNodeInfo2.setDrawingOrder(accessibilityNodeInfo.getDrawingOrder());
            accessibilityNodeInfo2.setImportantForAccessibility(accessibilityNodeInfo.isImportantForAccessibility());
        }
        if (i2 >= 26) {
            accessibilityNodeInfo2.setAvailableExtraData(accessibilityNodeInfo.getAvailableExtraData());
            accessibilityNodeInfo2.setHintText(accessibilityNodeInfo.getHintText());
            accessibilityNodeInfo2.setShowingHintText(accessibilityNodeInfo.isShowingHintText());
        }
    }

    private void setFlutterNodeParent(@NonNull AccessibilityNodeInfo accessibilityNodeInfo, @NonNull View view, @NonNull AccessibilityNodeInfo accessibilityNodeInfo2) {
        Long parentNodeId = this.reflectionAccessors.getParentNodeId(accessibilityNodeInfo);
        if (parentNodeId == null) {
            return;
        }
        Integer num = this.originToFlutterId.get(new ViewAndId(view, ReflectionAccessors.getVirtualNodeId(parentNodeId.longValue())));
        if (num != null) {
            accessibilityNodeInfo2.setParent(this.rootAccessibilityView, num.intValue());
        }
    }

    private void setFlutterNodesTranslateBounds(@NonNull AccessibilityNodeInfo accessibilityNodeInfo, @NonNull Rect rect, @NonNull AccessibilityNodeInfo accessibilityNodeInfo2) {
        Rect rect2 = new Rect();
        accessibilityNodeInfo.getBoundsInParent(rect2);
        accessibilityNodeInfo2.setBoundsInParent(rect2);
        Rect rect3 = new Rect();
        accessibilityNodeInfo.getBoundsInScreen(rect3);
        rect3.offset(rect.left, rect.top);
        accessibilityNodeInfo2.setBoundsInScreen(rect3);
    }

    @Nullable
    public AccessibilityNodeInfo createAccessibilityNodeInfo(int i2) {
        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo;
        ViewAndId viewAndId = this.flutterIdToOrigin.get(i2);
        if (viewAndId == null || !this.embeddedViewToDisplayBounds.containsKey(viewAndId.view) || viewAndId.view.getAccessibilityNodeProvider() == null || (accessibilityNodeInfoCreateAccessibilityNodeInfo = viewAndId.view.getAccessibilityNodeProvider().createAccessibilityNodeInfo(viewAndId.id)) == null) {
            return null;
        }
        return convertToFlutterNode(accessibilityNodeInfoCreateAccessibilityNodeInfo, i2, viewAndId.view);
    }

    @Nullable
    public Integer getRecordFlutterId(@NonNull View view, @NonNull AccessibilityRecord accessibilityRecord) {
        Long recordSourceNodeId = this.reflectionAccessors.getRecordSourceNodeId(accessibilityRecord);
        if (recordSourceNodeId == null) {
            return null;
        }
        return this.originToFlutterId.get(new ViewAndId(view, ReflectionAccessors.getVirtualNodeId(recordSourceNodeId.longValue())));
    }

    public AccessibilityNodeInfo getRootNode(@NonNull View view, int i2, @NonNull Rect rect) {
        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = view.createAccessibilityNodeInfo();
        Long sourceNodeId = this.reflectionAccessors.getSourceNodeId(accessibilityNodeInfoCreateAccessibilityNodeInfo);
        if (sourceNodeId == null) {
            return null;
        }
        this.embeddedViewToDisplayBounds.put(view, rect);
        cacheVirtualIdMappings(view, ReflectionAccessors.getVirtualNodeId(sourceNodeId.longValue()), i2);
        return convertToFlutterNode(accessibilityNodeInfoCreateAccessibilityNodeInfo, i2, view);
    }

    public boolean onAccessibilityHoverEvent(int i2, @NonNull MotionEvent motionEvent) {
        ViewAndId viewAndId = this.flutterIdToOrigin.get(i2);
        if (viewAndId == null) {
            return false;
        }
        Rect rect = this.embeddedViewToDisplayBounds.get(viewAndId.view);
        int pointerCount = motionEvent.getPointerCount();
        MotionEvent.PointerProperties[] pointerPropertiesArr = new MotionEvent.PointerProperties[pointerCount];
        MotionEvent.PointerCoords[] pointerCoordsArr = new MotionEvent.PointerCoords[pointerCount];
        for (int i3 = 0; i3 < motionEvent.getPointerCount(); i3++) {
            MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
            pointerPropertiesArr[i3] = pointerProperties;
            motionEvent.getPointerProperties(i3, pointerProperties);
            MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
            motionEvent.getPointerCoords(i3, pointerCoords);
            MotionEvent.PointerCoords pointerCoords2 = new MotionEvent.PointerCoords(pointerCoords);
            pointerCoordsArr[i3] = pointerCoords2;
            pointerCoords2.x -= rect.left;
            pointerCoords2.y -= rect.top;
        }
        return viewAndId.view.dispatchGenericMotionEvent(MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), motionEvent.getAction(), motionEvent.getPointerCount(), pointerPropertiesArr, pointerCoordsArr, motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getFlags()));
    }

    public boolean performAction(int i2, int i3, @Nullable Bundle bundle) {
        AccessibilityNodeProvider accessibilityNodeProvider;
        ViewAndId viewAndId = this.flutterIdToOrigin.get(i2);
        if (viewAndId == null || (accessibilityNodeProvider = viewAndId.view.getAccessibilityNodeProvider()) == null) {
            return false;
        }
        return accessibilityNodeProvider.performAction(viewAndId.id, i3, bundle);
    }

    public View platformViewOfNode(int i2) {
        ViewAndId viewAndId = this.flutterIdToOrigin.get(i2);
        if (viewAndId == null) {
            return null;
        }
        return viewAndId.view;
    }

    public boolean requestSendAccessibilityEvent(@NonNull View view, @NonNull View view2, @NonNull AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(accessibilityEvent);
        Long recordSourceNodeId = this.reflectionAccessors.getRecordSourceNodeId(accessibilityEvent);
        if (recordSourceNodeId == null) {
            return false;
        }
        int virtualNodeId = ReflectionAccessors.getVirtualNodeId(recordSourceNodeId.longValue());
        Integer num = this.originToFlutterId.get(new ViewAndId(view, virtualNodeId));
        if (num == null) {
            int i2 = this.nextFlutterId;
            this.nextFlutterId = i2 + 1;
            Integer numValueOf = Integer.valueOf(i2);
            cacheVirtualIdMappings(view, virtualNodeId, i2);
            num = numValueOf;
        }
        accessibilityEventObtain.setSource(this.rootAccessibilityView, num.intValue());
        accessibilityEventObtain.setClassName(accessibilityEvent.getClassName());
        accessibilityEventObtain.setPackageName(accessibilityEvent.getPackageName());
        for (int i3 = 0; i3 < accessibilityEventObtain.getRecordCount(); i3++) {
            AccessibilityRecord record = accessibilityEventObtain.getRecord(i3);
            Long recordSourceNodeId2 = this.reflectionAccessors.getRecordSourceNodeId(record);
            if (recordSourceNodeId2 == null) {
                return false;
            }
            ViewAndId viewAndId = new ViewAndId(view, ReflectionAccessors.getVirtualNodeId(recordSourceNodeId2.longValue()));
            if (!this.originToFlutterId.containsKey(viewAndId)) {
                return false;
            }
            record.setSource(this.rootAccessibilityView, this.originToFlutterId.get(viewAndId).intValue());
        }
        return this.rootAccessibilityView.getParent().requestSendAccessibilityEvent(view2, accessibilityEventObtain);
    }
}
