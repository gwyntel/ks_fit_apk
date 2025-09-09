package io.flutter.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.LocaleSpan;
import android.text.style.TtsSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.media3.common.C;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DiskLruHelper;
import io.flutter.Log;
import io.flutter.embedding.engine.systemchannels.AccessibilityChannel;
import io.flutter.plugin.platform.PlatformViewsAccessibilityDelegate;
import io.flutter.util.Predicate;
import io.flutter.util.ViewUtils;
import io.flutter.view.AccessibilityBridge;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class AccessibilityBridge extends AccessibilityNodeProvider {
    private static final int ACTION_SHOW_ON_SCREEN = 16908342;
    private static final int BOLD_TEXT_WEIGHT_ADJUSTMENT = 300;
    private static final int MIN_ENGINE_GENERATED_NODE_ID = 65536;
    private static final int ROOT_NODE_ID = 0;
    private static final float SCROLL_EXTENT_FOR_INFINITY = 100000.0f;
    private static final float SCROLL_POSITION_CAP_FOR_INFINITY = 70000.0f;
    private static final String TAG = "AccessibilityBridge";

    @NonNull
    private final AccessibilityChannel accessibilityChannel;
    private int accessibilityFeatureFlags;

    @Nullable
    private SemanticsNode accessibilityFocusedSemanticsNode;

    @NonNull
    private final AccessibilityManager accessibilityManager;
    private final AccessibilityChannel.AccessibilityMessageHandler accessibilityMessageHandler;
    private final AccessibilityManager.AccessibilityStateChangeListener accessibilityStateChangeListener;

    @NonNull
    private final AccessibilityViewEmbedder accessibilityViewEmbedder;
    private boolean accessibleNavigation;
    private final ContentObserver animationScaleObserver;

    @NonNull
    private final ContentResolver contentResolver;

    @NonNull
    private final Map<Integer, CustomAccessibilityAction> customAccessibilityActions;
    private Integer embeddedAccessibilityFocusedNodeId;
    private Integer embeddedInputFocusedNodeId;

    @NonNull
    private final List<Integer> flutterNavigationStack;

    @NonNull
    private final Map<Integer, SemanticsNode> flutterSemanticsTree;

    @Nullable
    private SemanticsNode hoveredObject;

    @Nullable
    private SemanticsNode inputFocusedSemanticsNode;
    private boolean isReleased;

    @Nullable
    private SemanticsNode lastInputFocusedSemanticsNode;

    @NonNull
    private Integer lastLeftFrameInset;

    @Nullable
    private OnAccessibilityChangeListener onAccessibilityChangeListener;

    @NonNull
    private final PlatformViewsAccessibilityDelegate platformViewsAccessibilityDelegate;
    private int previousRouteId;

    @NonNull
    private final View rootAccessibilityView;
    private final AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener;
    private static final int SCROLLABLE_ACTIONS = ((Action.SCROLL_RIGHT.value | Action.SCROLL_LEFT.value) | Action.SCROLL_UP.value) | Action.SCROLL_DOWN.value;
    private static final int FOCUSABLE_FLAGS = ((((((((((Flag.HAS_CHECKED_STATE.value | Flag.IS_CHECKED.value) | Flag.IS_SELECTED.value) | Flag.IS_TEXT_FIELD.value) | Flag.IS_FOCUSED.value) | Flag.HAS_ENABLED_STATE.value) | Flag.IS_ENABLED.value) | Flag.IS_IN_MUTUALLY_EXCLUSIVE_GROUP.value) | Flag.HAS_TOGGLED_STATE.value) | Flag.IS_TOGGLED.value) | Flag.IS_FOCUSABLE.value) | Flag.IS_SLIDER.value;
    private static int FIRST_RESOURCE_ID = 267386881;
    static int systemAction = (Action.DID_GAIN_ACCESSIBILITY_FOCUS.value & Action.DID_LOSE_ACCESSIBILITY_FOCUS.value) & Action.SHOW_ON_SCREEN.value;

    /* renamed from: io.flutter.view.AccessibilityBridge$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$io$flutter$view$AccessibilityBridge$StringAttributeType;

        static {
            int[] iArr = new int[StringAttributeType.values().length];
            $SwitchMap$io$flutter$view$AccessibilityBridge$StringAttributeType = iArr;
            try {
                iArr[StringAttributeType.SPELLOUT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$flutter$view$AccessibilityBridge$StringAttributeType[StringAttributeType.LOCALE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private enum AccessibilityFeature {
        ACCESSIBLE_NAVIGATION(1),
        INVERT_COLORS(2),
        DISABLE_ANIMATIONS(4),
        BOLD_TEXT(8),
        REDUCE_MOTION(16),
        HIGH_CONTRAST(32),
        ON_OFF_SWITCH_LABELS(64);

        final int value;

        AccessibilityFeature(int i2) {
            this.value = i2;
        }
    }

    public enum Action {
        TAP(1),
        LONG_PRESS(2),
        SCROLL_LEFT(4),
        SCROLL_RIGHT(8),
        SCROLL_UP(16),
        SCROLL_DOWN(32),
        INCREASE(64),
        DECREASE(128),
        SHOW_ON_SCREEN(256),
        MOVE_CURSOR_FORWARD_BY_CHARACTER(512),
        MOVE_CURSOR_BACKWARD_BY_CHARACTER(1024),
        SET_SELECTION(2048),
        COPY(4096),
        CUT(8192),
        PASTE(16384),
        DID_GAIN_ACCESSIBILITY_FOCUS(32768),
        DID_LOSE_ACCESSIBILITY_FOCUS(65536),
        CUSTOM_ACTION(131072),
        DISMISS(262144),
        MOVE_CURSOR_FORWARD_BY_WORD(524288),
        MOVE_CURSOR_BACKWARD_BY_WORD(1048576),
        SET_TEXT(2097152);

        public final int value;

        Action(int i2) {
            this.value = i2;
        }
    }

    private static class CustomAccessibilityAction {
        private String hint;
        private String label;
        private int resourceId = -1;
        private int id = -1;
        private int overrideId = -1;

        CustomAccessibilityAction() {
        }
    }

    enum Flag {
        HAS_CHECKED_STATE(1),
        IS_CHECKED(2),
        IS_SELECTED(4),
        IS_BUTTON(8),
        IS_TEXT_FIELD(16),
        IS_FOCUSED(32),
        HAS_ENABLED_STATE(64),
        IS_ENABLED(128),
        IS_IN_MUTUALLY_EXCLUSIVE_GROUP(256),
        IS_HEADER(512),
        IS_OBSCURED(1024),
        SCOPES_ROUTE(2048),
        NAMES_ROUTE(4096),
        IS_HIDDEN(8192),
        IS_IMAGE(16384),
        IS_LIVE_REGION(32768),
        HAS_TOGGLED_STATE(65536),
        IS_TOGGLED(131072),
        HAS_IMPLICIT_SCROLLING(262144),
        IS_MULTILINE(524288),
        IS_READ_ONLY(1048576),
        IS_FOCUSABLE(2097152),
        IS_LINK(4194304),
        IS_SLIDER(8388608),
        IS_KEYBOARD_KEY(16777216),
        IS_CHECK_STATE_MIXED(DiskLruHelper.DEFAULT_MAXSIZE),
        HAS_EXPANDED_STATE(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL),
        IS_EXPANDED(C.BUFFER_FLAG_FIRST_SAMPLE);

        final int value;

        Flag(int i2) {
            this.value = i2;
        }
    }

    private static class LocaleStringAttribute extends StringAttribute {
        String locale;

        private LocaleStringAttribute() {
            super();
        }
    }

    public interface OnAccessibilityChangeListener {
        void onAccessibilityChanged(boolean z2, boolean z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SemanticsNode {
        final AccessibilityBridge accessibilityBridge;
        private int actions;
        private float bottom;
        private int currentValueLength;
        private List<CustomAccessibilityAction> customAccessibilityActions;
        private String decreasedValue;
        private List<StringAttribute> decreasedValueAttributes;
        private int flags;
        private Rect globalRect;
        private float[] globalTransform;
        private String hint;
        private List<StringAttribute> hintAttributes;
        private String identifier;
        private String increasedValue;
        private List<StringAttribute> increasedValueAttributes;
        private float[] inverseTransform;
        private String label;
        private List<StringAttribute> labelAttributes;
        private float left;
        private int maxValueLength;
        private CustomAccessibilityAction onLongPressOverride;
        private CustomAccessibilityAction onTapOverride;
        private SemanticsNode parent;
        private int platformViewId;
        private int previousActions;
        private int previousFlags;
        private String previousLabel;
        private float previousScrollExtentMax;
        private float previousScrollExtentMin;
        private float previousScrollPosition;
        private int previousTextSelectionBase;
        private int previousTextSelectionExtent;
        private String previousValue;
        private float right;
        private int scrollChildren;
        private float scrollExtentMax;
        private float scrollExtentMin;
        private int scrollIndex;
        private float scrollPosition;
        private TextDirection textDirection;
        private int textSelectionBase;
        private int textSelectionExtent;

        @Nullable
        private String tooltip;

        /* renamed from: top, reason: collision with root package name */
        private float f25305top;
        private float[] transform;
        private String value;
        private List<StringAttribute> valueAttributes;
        private int id = -1;
        private int previousNodeId = -1;
        private boolean hadPreviousConfig = false;
        private List<SemanticsNode> childrenInTraversalOrder = new ArrayList();
        private List<SemanticsNode> childrenInHitTestOrder = new ArrayList();
        private boolean inverseTransformDirty = true;
        private boolean globalGeometryDirty = true;

        SemanticsNode(@NonNull AccessibilityBridge accessibilityBridge) {
            this.accessibilityBridge = accessibilityBridge;
        }

        static /* synthetic */ int access$2212(SemanticsNode semanticsNode, int i2) {
            int i3 = semanticsNode.textSelectionExtent + i2;
            semanticsNode.textSelectionExtent = i3;
            return i3;
        }

        static /* synthetic */ int access$2220(SemanticsNode semanticsNode, int i2) {
            int i3 = semanticsNode.textSelectionExtent - i2;
            semanticsNode.textSelectionExtent = i3;
            return i3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void collectRoutes(List<SemanticsNode> list) {
            if (hasFlag(Flag.SCOPES_ROUTE)) {
                list.add(this);
            }
            Iterator<SemanticsNode> it = this.childrenInTraversalOrder.iterator();
            while (it.hasNext()) {
                it.next().collectRoutes(list);
            }
        }

        private SpannableString createSpannableString(String str, List<StringAttribute> list) {
            if (str == null) {
                return null;
            }
            SpannableString spannableString = new SpannableString(str);
            if (list != null) {
                for (StringAttribute stringAttribute : list) {
                    int i2 = AnonymousClass5.$SwitchMap$io$flutter$view$AccessibilityBridge$StringAttributeType[stringAttribute.type.ordinal()];
                    if (i2 == 1) {
                        spannableString.setSpan(new TtsSpan.Builder("android.type.verbatim").build(), stringAttribute.start, stringAttribute.end, 0);
                    } else if (i2 == 2) {
                        spannableString.setSpan(new LocaleSpan(Locale.forLanguageTag(((LocaleStringAttribute) stringAttribute).locale)), stringAttribute.start, stringAttribute.end, 0);
                    }
                }
            }
            return spannableString;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean didChangeLabel() {
            String str;
            String str2 = this.label;
            if (str2 == null && this.previousLabel == null) {
                return false;
            }
            return str2 == null || (str = this.previousLabel) == null || !str2.equals(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean didScroll() {
            return (Float.isNaN(this.scrollPosition) || Float.isNaN(this.previousScrollPosition) || this.previousScrollPosition == this.scrollPosition) ? false : true;
        }

        private void ensureInverseTransform() {
            if (this.inverseTransformDirty) {
                this.inverseTransformDirty = false;
                if (this.inverseTransform == null) {
                    this.inverseTransform = new float[16];
                }
                if (Matrix.invertM(this.inverseTransform, 0, this.transform, 0)) {
                    return;
                }
                Arrays.fill(this.inverseTransform, 0.0f);
            }
        }

        private SemanticsNode getAncestor(Predicate<SemanticsNode> predicate) {
            for (SemanticsNode semanticsNode = this.parent; semanticsNode != null; semanticsNode = semanticsNode.parent) {
                if (predicate.test(semanticsNode)) {
                    return semanticsNode;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Rect getGlobalRect() {
            return this.globalRect;
        }

        private CharSequence getHint() {
            return createSpannableString(this.hint, this.hintAttributes);
        }

        private CharSequence getLabel() {
            return createSpannableString(this.label, this.labelAttributes);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getRouteName() {
            String str;
            if (hasFlag(Flag.NAMES_ROUTE) && (str = this.label) != null && !str.isEmpty()) {
                return this.label;
            }
            Iterator<SemanticsNode> it = this.childrenInTraversalOrder.iterator();
            while (it.hasNext()) {
                String routeName = it.next().getRouteName();
                if (routeName != null && !routeName.isEmpty()) {
                    return routeName;
                }
            }
            return null;
        }

        private List<StringAttribute> getStringAttributesFromBuffer(@NonNull ByteBuffer byteBuffer, @NonNull ByteBuffer[] byteBufferArr) {
            int i2 = byteBuffer.getInt();
            if (i2 == -1) {
                return null;
            }
            ArrayList arrayList = new ArrayList(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                int i4 = byteBuffer.getInt();
                int i5 = byteBuffer.getInt();
                StringAttributeType stringAttributeType = StringAttributeType.values()[byteBuffer.getInt()];
                int i6 = AnonymousClass5.$SwitchMap$io$flutter$view$AccessibilityBridge$StringAttributeType[stringAttributeType.ordinal()];
                if (i6 == 1) {
                    byteBuffer.getInt();
                    SpellOutStringAttribute spellOutStringAttribute = new SpellOutStringAttribute();
                    spellOutStringAttribute.start = i4;
                    spellOutStringAttribute.end = i5;
                    spellOutStringAttribute.type = stringAttributeType;
                    arrayList.add(spellOutStringAttribute);
                } else if (i6 == 2) {
                    ByteBuffer byteBuffer2 = byteBufferArr[byteBuffer.getInt()];
                    LocaleStringAttribute localeStringAttribute = new LocaleStringAttribute();
                    localeStringAttribute.start = i4;
                    localeStringAttribute.end = i5;
                    localeStringAttribute.type = stringAttributeType;
                    localeStringAttribute.locale = Charset.forName("UTF-8").decode(byteBuffer2).toString();
                    arrayList.add(localeStringAttribute);
                }
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CharSequence getTextFieldHint() {
            CharSequence[] charSequenceArr = {getLabel(), getHint()};
            CharSequence charSequenceConcat = null;
            for (int i2 = 0; i2 < 2; i2++) {
                CharSequence charSequence = charSequenceArr[i2];
                if (charSequence != null && charSequence.length() > 0) {
                    charSequenceConcat = (charSequenceConcat == null || charSequenceConcat.length() == 0) ? charSequence : TextUtils.concat(charSequenceConcat, ", ", charSequence);
                }
            }
            return charSequenceConcat;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CharSequence getValue() {
            return createSpannableString(this.value, this.valueAttributes);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CharSequence getValueLabelHint() {
            CharSequence[] charSequenceArr = {getValue(), getLabel(), getHint()};
            CharSequence charSequenceConcat = null;
            for (int i2 = 0; i2 < 3; i2++) {
                CharSequence charSequence = charSequenceArr[i2];
                if (charSequence != null && charSequence.length() > 0) {
                    charSequenceConcat = (charSequenceConcat == null || charSequenceConcat.length() == 0) ? charSequence : TextUtils.concat(charSequenceConcat, ", ", charSequence);
                }
            }
            return charSequenceConcat;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hadAction(@NonNull Action action) {
            return (action.value & this.previousActions) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hadFlag(@NonNull Flag flag) {
            return (flag.value & this.previousFlags) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasAction(@NonNull Action action) {
            return (action.value & this.actions) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasFlag(@NonNull Flag flag) {
            return (flag.value & this.flags) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public SemanticsNode hitTest(float[] fArr, boolean z2) {
            float f2 = fArr[3];
            boolean z3 = false;
            float f3 = fArr[0] / f2;
            float f4 = fArr[1] / f2;
            if (f3 < this.left || f3 >= this.right || f4 < this.f25305top || f4 >= this.bottom) {
                return null;
            }
            float[] fArr2 = new float[4];
            for (SemanticsNode semanticsNode : this.childrenInHitTestOrder) {
                if (!semanticsNode.hasFlag(Flag.IS_HIDDEN)) {
                    semanticsNode.ensureInverseTransform();
                    Matrix.multiplyMV(fArr2, 0, semanticsNode.inverseTransform, 0, fArr, 0);
                    SemanticsNode semanticsNodeHitTest = semanticsNode.hitTest(fArr2, z2);
                    if (semanticsNodeHitTest != null) {
                        return semanticsNodeHitTest;
                    }
                }
            }
            if (z2 && this.platformViewId != -1) {
                z3 = true;
            }
            if (isFocusable() || z3) {
                return this;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isFocusable() {
            String str;
            String str2;
            String str3;
            if (hasFlag(Flag.SCOPES_ROUTE)) {
                return false;
            }
            if (hasFlag(Flag.IS_FOCUSABLE)) {
                return true;
            }
            return ((this.actions & (~AccessibilityBridge.SCROLLABLE_ACTIONS)) == 0 && (this.flags & AccessibilityBridge.FOCUSABLE_FLAGS) == 0 && ((str = this.label) == null || str.isEmpty()) && (((str2 = this.value) == null || str2.isEmpty()) && ((str3 = this.hint) == null || str3.isEmpty()))) ? false : true;
        }

        private void log(@NonNull String str, boolean z2) {
        }

        private float max(float f2, float f3, float f4, float f5) {
            return Math.max(f2, Math.max(f3, Math.max(f4, f5)));
        }

        private float min(float f2, float f3, float f4, float f5) {
            return Math.min(f2, Math.min(f3, Math.min(f4, f5)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean nullableHasAncestor(SemanticsNode semanticsNode, Predicate<SemanticsNode> predicate) {
            return (semanticsNode == null || semanticsNode.getAncestor(predicate) == null) ? false : true;
        }

        private void transformPoint(float[] fArr, float[] fArr2, float[] fArr3) {
            Matrix.multiplyMV(fArr, 0, fArr2, 0, fArr3, 0);
            float f2 = fArr[3];
            fArr[0] = fArr[0] / f2;
            fArr[1] = fArr[1] / f2;
            fArr[2] = fArr[2] / f2;
            fArr[3] = 0.0f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateRecursively(float[] fArr, Set<SemanticsNode> set, boolean z2) {
            set.add(this);
            if (this.globalGeometryDirty) {
                z2 = true;
            }
            if (z2) {
                if (this.globalTransform == null) {
                    this.globalTransform = new float[16];
                }
                if (this.transform == null) {
                    this.transform = new float[16];
                }
                Matrix.multiplyMM(this.globalTransform, 0, fArr, 0, this.transform, 0);
                float[] fArr2 = {this.left, this.f25305top, 0.0f, 1.0f};
                float[] fArr3 = new float[4];
                float[] fArr4 = new float[4];
                float[] fArr5 = new float[4];
                float[] fArr6 = new float[4];
                transformPoint(fArr3, this.globalTransform, fArr2);
                fArr2[0] = this.right;
                fArr2[1] = this.f25305top;
                transformPoint(fArr4, this.globalTransform, fArr2);
                fArr2[0] = this.right;
                fArr2[1] = this.bottom;
                transformPoint(fArr5, this.globalTransform, fArr2);
                fArr2[0] = this.left;
                fArr2[1] = this.bottom;
                transformPoint(fArr6, this.globalTransform, fArr2);
                if (this.globalRect == null) {
                    this.globalRect = new Rect();
                }
                this.globalRect.set(Math.round(min(fArr3[0], fArr4[0], fArr5[0], fArr6[0])), Math.round(min(fArr3[1], fArr4[1], fArr5[1], fArr6[1])), Math.round(max(fArr3[0], fArr4[0], fArr5[0], fArr6[0])), Math.round(max(fArr3[1], fArr4[1], fArr5[1], fArr6[1])));
                this.globalGeometryDirty = false;
            }
            int i2 = -1;
            for (SemanticsNode semanticsNode : this.childrenInTraversalOrder) {
                semanticsNode.previousNodeId = i2;
                i2 = semanticsNode.id;
                semanticsNode.updateRecursively(this.globalTransform, set, z2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateWith(@NonNull ByteBuffer byteBuffer, @NonNull String[] strArr, @NonNull ByteBuffer[] byteBufferArr) {
            this.hadPreviousConfig = true;
            this.previousValue = this.value;
            this.previousLabel = this.label;
            this.previousFlags = this.flags;
            this.previousActions = this.actions;
            this.previousTextSelectionBase = this.textSelectionBase;
            this.previousTextSelectionExtent = this.textSelectionExtent;
            this.previousScrollPosition = this.scrollPosition;
            this.previousScrollExtentMax = this.scrollExtentMax;
            this.previousScrollExtentMin = this.scrollExtentMin;
            this.flags = byteBuffer.getInt();
            this.actions = byteBuffer.getInt();
            this.maxValueLength = byteBuffer.getInt();
            this.currentValueLength = byteBuffer.getInt();
            this.textSelectionBase = byteBuffer.getInt();
            this.textSelectionExtent = byteBuffer.getInt();
            this.platformViewId = byteBuffer.getInt();
            this.scrollChildren = byteBuffer.getInt();
            this.scrollIndex = byteBuffer.getInt();
            this.scrollPosition = byteBuffer.getFloat();
            this.scrollExtentMax = byteBuffer.getFloat();
            this.scrollExtentMin = byteBuffer.getFloat();
            int i2 = byteBuffer.getInt();
            this.identifier = i2 == -1 ? null : strArr[i2];
            int i3 = byteBuffer.getInt();
            this.label = i3 == -1 ? null : strArr[i3];
            this.labelAttributes = getStringAttributesFromBuffer(byteBuffer, byteBufferArr);
            int i4 = byteBuffer.getInt();
            this.value = i4 == -1 ? null : strArr[i4];
            this.valueAttributes = getStringAttributesFromBuffer(byteBuffer, byteBufferArr);
            int i5 = byteBuffer.getInt();
            this.increasedValue = i5 == -1 ? null : strArr[i5];
            this.increasedValueAttributes = getStringAttributesFromBuffer(byteBuffer, byteBufferArr);
            int i6 = byteBuffer.getInt();
            this.decreasedValue = i6 == -1 ? null : strArr[i6];
            this.decreasedValueAttributes = getStringAttributesFromBuffer(byteBuffer, byteBufferArr);
            int i7 = byteBuffer.getInt();
            this.hint = i7 == -1 ? null : strArr[i7];
            this.hintAttributes = getStringAttributesFromBuffer(byteBuffer, byteBufferArr);
            int i8 = byteBuffer.getInt();
            this.tooltip = i8 == -1 ? null : strArr[i8];
            this.textDirection = TextDirection.fromInt(byteBuffer.getInt());
            this.left = byteBuffer.getFloat();
            this.f25305top = byteBuffer.getFloat();
            this.right = byteBuffer.getFloat();
            this.bottom = byteBuffer.getFloat();
            if (this.transform == null) {
                this.transform = new float[16];
            }
            for (int i9 = 0; i9 < 16; i9++) {
                this.transform[i9] = byteBuffer.getFloat();
            }
            this.inverseTransformDirty = true;
            this.globalGeometryDirty = true;
            int i10 = byteBuffer.getInt();
            this.childrenInTraversalOrder.clear();
            this.childrenInHitTestOrder.clear();
            for (int i11 = 0; i11 < i10; i11++) {
                SemanticsNode orCreateSemanticsNode = this.accessibilityBridge.getOrCreateSemanticsNode(byteBuffer.getInt());
                orCreateSemanticsNode.parent = this;
                this.childrenInTraversalOrder.add(orCreateSemanticsNode);
            }
            for (int i12 = 0; i12 < i10; i12++) {
                SemanticsNode orCreateSemanticsNode2 = this.accessibilityBridge.getOrCreateSemanticsNode(byteBuffer.getInt());
                orCreateSemanticsNode2.parent = this;
                this.childrenInHitTestOrder.add(orCreateSemanticsNode2);
            }
            int i13 = byteBuffer.getInt();
            if (i13 == 0) {
                this.customAccessibilityActions = null;
                return;
            }
            List<CustomAccessibilityAction> list = this.customAccessibilityActions;
            if (list == null) {
                this.customAccessibilityActions = new ArrayList(i13);
            } else {
                list.clear();
            }
            for (int i14 = 0; i14 < i13; i14++) {
                CustomAccessibilityAction orCreateAccessibilityAction = this.accessibilityBridge.getOrCreateAccessibilityAction(byteBuffer.getInt());
                if (orCreateAccessibilityAction.overrideId == Action.TAP.value) {
                    this.onTapOverride = orCreateAccessibilityAction;
                } else if (orCreateAccessibilityAction.overrideId == Action.LONG_PRESS.value) {
                    this.onLongPressOverride = orCreateAccessibilityAction;
                } else {
                    this.customAccessibilityActions.add(orCreateAccessibilityAction);
                }
                this.customAccessibilityActions.add(orCreateAccessibilityAction);
            }
        }
    }

    private static class SpellOutStringAttribute extends StringAttribute {
        private SpellOutStringAttribute() {
            super();
        }
    }

    private static class StringAttribute {
        int end;
        int start;
        StringAttributeType type;

        private StringAttribute() {
        }
    }

    private enum StringAttributeType {
        SPELLOUT,
        LOCALE
    }

    private enum TextDirection {
        UNKNOWN,
        LTR,
        RTL;

        public static TextDirection fromInt(int i2) {
            return i2 != 1 ? i2 != 2 ? UNKNOWN : LTR : RTL;
        }
    }

    public AccessibilityBridge(@NonNull View view, @NonNull AccessibilityChannel accessibilityChannel, @NonNull AccessibilityManager accessibilityManager, @NonNull ContentResolver contentResolver, @NonNull PlatformViewsAccessibilityDelegate platformViewsAccessibilityDelegate) {
        this(view, accessibilityChannel, accessibilityManager, contentResolver, new AccessibilityViewEmbedder(view, 65536), platformViewsAccessibilityDelegate);
    }

    static /* synthetic */ int access$1172(AccessibilityBridge accessibilityBridge, int i2) {
        int i3 = i2 & accessibilityBridge.accessibilityFeatureFlags;
        accessibilityBridge.accessibilityFeatureFlags = i3;
        return i3;
    }

    static /* synthetic */ int access$1176(AccessibilityBridge accessibilityBridge, int i2) {
        int i3 = i2 | accessibilityBridge.accessibilityFeatureFlags;
        accessibilityBridge.accessibilityFeatureFlags = i3;
        return i3;
    }

    private AccessibilityEvent createTextChangedEvent(int i2, String str, String str2) {
        AccessibilityEvent accessibilityEventObtainAccessibilityEvent = obtainAccessibilityEvent(i2, 16);
        accessibilityEventObtainAccessibilityEvent.setBeforeText(str);
        accessibilityEventObtainAccessibilityEvent.getText().add(str2);
        int i3 = 0;
        while (i3 < str.length() && i3 < str2.length() && str.charAt(i3) == str2.charAt(i3)) {
            i3++;
        }
        if (i3 >= str.length() && i3 >= str2.length()) {
            return null;
        }
        accessibilityEventObtainAccessibilityEvent.setFromIndex(i3);
        int length = str.length() - 1;
        int length2 = str2.length() - 1;
        while (length >= i3 && length2 >= i3 && str.charAt(length) == str2.charAt(length2)) {
            length--;
            length2--;
        }
        accessibilityEventObtainAccessibilityEvent.setRemovedCount((length - i3) + 1);
        accessibilityEventObtainAccessibilityEvent.setAddedCount((length2 - i3) + 1);
        return accessibilityEventObtainAccessibilityEvent;
    }

    @RequiresApi(28)
    @TargetApi(28)
    private boolean doesLayoutInDisplayCutoutModeRequireLeftInset() {
        Activity activity = ViewUtils.getActivity(this.rootAccessibilityView.getContext());
        if (activity == null || activity.getWindow() == null) {
            return false;
        }
        int i2 = activity.getWindow().getAttributes().layoutInDisplayCutoutMode;
        return i2 == 2 || i2 == 0;
    }

    private Rect getBoundsInScreen(Rect rect) {
        Rect rect2 = new Rect(rect);
        int[] iArr = new int[2];
        this.rootAccessibilityView.getLocationOnScreen(iArr);
        rect2.offset(iArr[0], iArr[1]);
        return rect2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CustomAccessibilityAction getOrCreateAccessibilityAction(int i2) {
        CustomAccessibilityAction customAccessibilityAction = this.customAccessibilityActions.get(Integer.valueOf(i2));
        if (customAccessibilityAction != null) {
            return customAccessibilityAction;
        }
        CustomAccessibilityAction customAccessibilityAction2 = new CustomAccessibilityAction();
        customAccessibilityAction2.id = i2;
        customAccessibilityAction2.resourceId = FIRST_RESOURCE_ID + i2;
        this.customAccessibilityActions.put(Integer.valueOf(i2), customAccessibilityAction2);
        return customAccessibilityAction2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SemanticsNode getOrCreateSemanticsNode(int i2) {
        SemanticsNode semanticsNode = this.flutterSemanticsTree.get(Integer.valueOf(i2));
        if (semanticsNode != null) {
            return semanticsNode;
        }
        SemanticsNode semanticsNode2 = new SemanticsNode(this);
        semanticsNode2.id = i2;
        this.flutterSemanticsTree.put(Integer.valueOf(i2), semanticsNode2);
        return semanticsNode2;
    }

    private SemanticsNode getRootSemanticsNode() {
        return this.flutterSemanticsTree.get(0);
    }

    private void handleTouchExploration(float f2, float f3, boolean z2) {
        SemanticsNode semanticsNodeHitTest;
        if (this.flutterSemanticsTree.isEmpty() || (semanticsNodeHitTest = getRootSemanticsNode().hitTest(new float[]{f2, f3, 0.0f, 1.0f}, z2)) == this.hoveredObject) {
            return;
        }
        if (semanticsNodeHitTest != null) {
            sendAccessibilityEvent(semanticsNodeHitTest.id, 128);
        }
        SemanticsNode semanticsNode = this.hoveredObject;
        if (semanticsNode != null) {
            sendAccessibilityEvent(semanticsNode.id, 256);
        }
        this.hoveredObject = semanticsNodeHitTest;
    }

    private boolean isImportant(SemanticsNode semanticsNode) {
        if (semanticsNode.hasFlag(Flag.SCOPES_ROUTE)) {
            return false;
        }
        return (semanticsNode.getValueLabelHint() == null && (semanticsNode.actions & (~systemAction)) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$shouldSetCollectionInfo$0(SemanticsNode semanticsNode, SemanticsNode semanticsNode2) {
        return semanticsNode2 == semanticsNode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$shouldSetCollectionInfo$1(SemanticsNode semanticsNode) {
        return semanticsNode.hasFlag(Flag.HAS_IMPLICIT_SCROLLING);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AccessibilityEvent obtainAccessibilityEvent(int i2, int i3) {
        AccessibilityEvent accessibilityEventObtainAccessibilityEvent = obtainAccessibilityEvent(i3);
        accessibilityEventObtainAccessibilityEvent.setPackageName(this.rootAccessibilityView.getContext().getPackageName());
        accessibilityEventObtainAccessibilityEvent.setSource(this.rootAccessibilityView, i2);
        return accessibilityEventObtainAccessibilityEvent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTouchExplorationExit() {
        SemanticsNode semanticsNode = this.hoveredObject;
        if (semanticsNode != null) {
            sendAccessibilityEvent(semanticsNode.id, 256);
            this.hoveredObject = null;
        }
    }

    private void onWindowNameChange(@NonNull SemanticsNode semanticsNode) {
        String routeName = semanticsNode.getRouteName();
        if (routeName == null) {
            routeName = " ";
        }
        if (Build.VERSION.SDK_INT >= 28) {
            setAccessibilityPaneTitle(routeName);
            return;
        }
        AccessibilityEvent accessibilityEventObtainAccessibilityEvent = obtainAccessibilityEvent(semanticsNode.id, 32);
        accessibilityEventObtainAccessibilityEvent.getText().add(routeName);
        sendAccessibilityEvent(accessibilityEventObtainAccessibilityEvent);
    }

    private boolean performCursorMoveAction(@NonNull SemanticsNode semanticsNode, int i2, @NonNull Bundle bundle, boolean z2) {
        int i3 = bundle.getInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT);
        boolean z3 = bundle.getBoolean(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN);
        int i4 = semanticsNode.textSelectionBase;
        int i5 = semanticsNode.textSelectionExtent;
        predictCursorMovement(semanticsNode, i3, z2, z3);
        if (i4 != semanticsNode.textSelectionBase || i5 != semanticsNode.textSelectionExtent) {
            String str = semanticsNode.value != null ? semanticsNode.value : "";
            AccessibilityEvent accessibilityEventObtainAccessibilityEvent = obtainAccessibilityEvent(semanticsNode.id, 8192);
            accessibilityEventObtainAccessibilityEvent.getText().add(str);
            accessibilityEventObtainAccessibilityEvent.setFromIndex(semanticsNode.textSelectionBase);
            accessibilityEventObtainAccessibilityEvent.setToIndex(semanticsNode.textSelectionExtent);
            accessibilityEventObtainAccessibilityEvent.setItemCount(str.length());
            sendAccessibilityEvent(accessibilityEventObtainAccessibilityEvent);
        }
        if (i3 == 1) {
            if (z2) {
                Action action = Action.MOVE_CURSOR_FORWARD_BY_CHARACTER;
                if (semanticsNode.hasAction(action)) {
                    this.accessibilityChannel.dispatchSemanticsAction(i2, action, Boolean.valueOf(z3));
                    return true;
                }
            }
            if (z2) {
                return false;
            }
            Action action2 = Action.MOVE_CURSOR_BACKWARD_BY_CHARACTER;
            if (!semanticsNode.hasAction(action2)) {
                return false;
            }
            this.accessibilityChannel.dispatchSemanticsAction(i2, action2, Boolean.valueOf(z3));
            return true;
        }
        if (i3 != 2) {
            return i3 == 4 || i3 == 8 || i3 == 16;
        }
        if (z2) {
            Action action3 = Action.MOVE_CURSOR_FORWARD_BY_WORD;
            if (semanticsNode.hasAction(action3)) {
                this.accessibilityChannel.dispatchSemanticsAction(i2, action3, Boolean.valueOf(z3));
                return true;
            }
        }
        if (z2) {
            return false;
        }
        Action action4 = Action.MOVE_CURSOR_BACKWARD_BY_WORD;
        if (!semanticsNode.hasAction(action4)) {
            return false;
        }
        this.accessibilityChannel.dispatchSemanticsAction(i2, action4, Boolean.valueOf(z3));
        return true;
    }

    private boolean performSetText(SemanticsNode semanticsNode, int i2, @NonNull Bundle bundle) {
        String string = (bundle == null || !bundle.containsKey(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE)) ? "" : bundle.getString(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE);
        this.accessibilityChannel.dispatchSemanticsAction(i2, Action.SET_TEXT, string);
        semanticsNode.value = string;
        semanticsNode.valueAttributes = null;
        return true;
    }

    private void predictCursorMovement(@NonNull SemanticsNode semanticsNode, int i2, boolean z2, boolean z3) {
        if (semanticsNode.textSelectionExtent < 0 || semanticsNode.textSelectionBase < 0) {
            return;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 4) {
                    if (i2 == 8 || i2 == 16) {
                        if (z2) {
                            semanticsNode.textSelectionExtent = semanticsNode.value.length();
                        } else {
                            semanticsNode.textSelectionExtent = 0;
                        }
                    }
                } else if (z2 && semanticsNode.textSelectionExtent < semanticsNode.value.length()) {
                    Matcher matcher = Pattern.compile("(?!^)(\\n)").matcher(semanticsNode.value.substring(semanticsNode.textSelectionExtent));
                    if (matcher.find()) {
                        SemanticsNode.access$2212(semanticsNode, matcher.start(1));
                    } else {
                        semanticsNode.textSelectionExtent = semanticsNode.value.length();
                    }
                } else if (!z2 && semanticsNode.textSelectionExtent > 0) {
                    Matcher matcher2 = Pattern.compile("(?s:.*)(\\n)").matcher(semanticsNode.value.substring(0, semanticsNode.textSelectionExtent));
                    if (matcher2.find()) {
                        semanticsNode.textSelectionExtent = matcher2.start(1);
                    } else {
                        semanticsNode.textSelectionExtent = 0;
                    }
                }
            } else if (z2 && semanticsNode.textSelectionExtent < semanticsNode.value.length()) {
                Matcher matcher3 = Pattern.compile("\\p{L}(\\b)").matcher(semanticsNode.value.substring(semanticsNode.textSelectionExtent));
                matcher3.find();
                if (matcher3.find()) {
                    SemanticsNode.access$2212(semanticsNode, matcher3.start(1));
                } else {
                    semanticsNode.textSelectionExtent = semanticsNode.value.length();
                }
            } else if (!z2 && semanticsNode.textSelectionExtent > 0) {
                Matcher matcher4 = Pattern.compile("(?s:.*)(\\b)\\p{L}").matcher(semanticsNode.value.substring(0, semanticsNode.textSelectionExtent));
                if (matcher4.find()) {
                    semanticsNode.textSelectionExtent = matcher4.start(1);
                }
            }
        } else if (z2 && semanticsNode.textSelectionExtent < semanticsNode.value.length()) {
            SemanticsNode.access$2212(semanticsNode, 1);
        } else if (!z2 && semanticsNode.textSelectionExtent > 0) {
            SemanticsNode.access$2220(semanticsNode, 1);
        }
        if (z3) {
            return;
        }
        semanticsNode.textSelectionBase = semanticsNode.textSelectionExtent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendLatestAccessibilityFlagsToFlutter() {
        this.accessibilityChannel.setAccessibilityFeatures(this.accessibilityFeatureFlags);
    }

    private void sendWindowContentChangeEvent(int i2) {
        AccessibilityEvent accessibilityEventObtainAccessibilityEvent = obtainAccessibilityEvent(i2, 2048);
        accessibilityEventObtainAccessibilityEvent.setContentChangeTypes(1);
        sendAccessibilityEvent(accessibilityEventObtainAccessibilityEvent);
    }

    @RequiresApi(28)
    @TargetApi(28)
    private void setAccessibilityPaneTitle(String str) {
        this.rootAccessibilityView.setAccessibilityPaneTitle(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAccessibleNavigation(boolean z2) {
        if (this.accessibleNavigation == z2) {
            return;
        }
        this.accessibleNavigation = z2;
        if (z2) {
            this.accessibilityFeatureFlags |= AccessibilityFeature.ACCESSIBLE_NAVIGATION.value;
        } else {
            this.accessibilityFeatureFlags &= ~AccessibilityFeature.ACCESSIBLE_NAVIGATION.value;
        }
        sendLatestAccessibilityFlagsToFlutter();
    }

    @RequiresApi(31)
    @TargetApi(31)
    private void setBoldTextFlag() {
        View view = this.rootAccessibilityView;
        if (view == null || view.getResources() == null) {
            return;
        }
        int i2 = this.rootAccessibilityView.getResources().getConfiguration().fontWeightAdjustment;
        if (i2 == Integer.MAX_VALUE || i2 < 300) {
            this.accessibilityFeatureFlags &= AccessibilityFeature.BOLD_TEXT.value;
        } else {
            this.accessibilityFeatureFlags |= AccessibilityFeature.BOLD_TEXT.value;
        }
        sendLatestAccessibilityFlagsToFlutter();
    }

    private boolean shouldSetCollectionInfo(final SemanticsNode semanticsNode) {
        return semanticsNode.scrollChildren > 0 && (SemanticsNode.nullableHasAncestor(this.accessibilityFocusedSemanticsNode, new Predicate() { // from class: io.flutter.view.d
            @Override // io.flutter.util.Predicate
            public final boolean test(Object obj) {
                return AccessibilityBridge.lambda$shouldSetCollectionInfo$0(semanticsNode, (AccessibilityBridge.SemanticsNode) obj);
            }
        }) || !SemanticsNode.nullableHasAncestor(this.accessibilityFocusedSemanticsNode, new Predicate() { // from class: io.flutter.view.e
            @Override // io.flutter.util.Predicate
            public final boolean test(Object obj) {
                return AccessibilityBridge.lambda$shouldSetCollectionInfo$1((AccessibilityBridge.SemanticsNode) obj);
            }
        }));
    }

    private void willRemoveSemanticsNode(SemanticsNode semanticsNode) {
        View platformViewById;
        Integer num;
        semanticsNode.parent = null;
        if (semanticsNode.platformViewId != -1 && (num = this.embeddedAccessibilityFocusedNodeId) != null && this.accessibilityViewEmbedder.platformViewOfNode(num.intValue()) == this.platformViewsAccessibilityDelegate.getPlatformViewById(semanticsNode.platformViewId)) {
            sendAccessibilityEvent(this.embeddedAccessibilityFocusedNodeId.intValue(), 65536);
            this.embeddedAccessibilityFocusedNodeId = null;
        }
        if (semanticsNode.platformViewId != -1 && (platformViewById = this.platformViewsAccessibilityDelegate.getPlatformViewById(semanticsNode.platformViewId)) != null) {
            platformViewById.setImportantForAccessibility(4);
        }
        SemanticsNode semanticsNode2 = this.accessibilityFocusedSemanticsNode;
        if (semanticsNode2 == semanticsNode) {
            sendAccessibilityEvent(semanticsNode2.id, 65536);
            this.accessibilityFocusedSemanticsNode = null;
        }
        if (this.inputFocusedSemanticsNode == semanticsNode) {
            this.inputFocusedSemanticsNode = null;
        }
        if (this.hoveredObject == semanticsNode) {
            this.hoveredObject = null;
        }
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    @SuppressLint({"NewApi"})
    public AccessibilityNodeInfo createAccessibilityNodeInfo(int i2) {
        int i3;
        boolean z2 = true;
        setAccessibleNavigation(true);
        if (i2 >= 65536) {
            return this.accessibilityViewEmbedder.createAccessibilityNodeInfo(i2);
        }
        if (i2 == -1) {
            AccessibilityNodeInfo accessibilityNodeInfoObtainAccessibilityNodeInfo = obtainAccessibilityNodeInfo(this.rootAccessibilityView);
            this.rootAccessibilityView.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoObtainAccessibilityNodeInfo);
            if (this.flutterSemanticsTree.containsKey(0)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo.addChild(this.rootAccessibilityView, 0);
            }
            if (Build.VERSION.SDK_INT >= 24) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo.setImportantForAccessibility(false);
            }
            return accessibilityNodeInfoObtainAccessibilityNodeInfo;
        }
        SemanticsNode semanticsNode = this.flutterSemanticsTree.get(Integer.valueOf(i2));
        if (semanticsNode == null) {
            return null;
        }
        if (semanticsNode.platformViewId != -1 && this.platformViewsAccessibilityDelegate.usesVirtualDisplay(semanticsNode.platformViewId)) {
            View platformViewById = this.platformViewsAccessibilityDelegate.getPlatformViewById(semanticsNode.platformViewId);
            if (platformViewById == null) {
                return null;
            }
            return this.accessibilityViewEmbedder.getRootNode(platformViewById, semanticsNode.id, semanticsNode.getGlobalRect());
        }
        AccessibilityNodeInfo accessibilityNodeInfoObtainAccessibilityNodeInfo2 = obtainAccessibilityNodeInfo(this.rootAccessibilityView, i2);
        int i4 = Build.VERSION.SDK_INT;
        if (i4 >= 24) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setImportantForAccessibility(isImportant(semanticsNode));
        }
        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setViewIdResourceName("");
        if (semanticsNode.identifier != null) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setViewIdResourceName(semanticsNode.identifier);
        }
        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setPackageName(this.rootAccessibilityView.getContext().getPackageName());
        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClassName("android.view.View");
        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setSource(this.rootAccessibilityView, i2);
        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setFocusable(semanticsNode.isFocusable());
        SemanticsNode semanticsNode2 = this.inputFocusedSemanticsNode;
        if (semanticsNode2 != null) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setFocused(semanticsNode2.id == i2);
        }
        SemanticsNode semanticsNode3 = this.accessibilityFocusedSemanticsNode;
        if (semanticsNode3 != null) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setAccessibilityFocused(semanticsNode3.id == i2);
        }
        Flag flag = Flag.IS_TEXT_FIELD;
        if (semanticsNode.hasFlag(flag)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setPassword(semanticsNode.hasFlag(Flag.IS_OBSCURED));
            if (!semanticsNode.hasFlag(Flag.IS_READ_ONLY)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClassName("android.widget.EditText");
            }
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setEditable(!semanticsNode.hasFlag(r9));
            if (semanticsNode.textSelectionBase != -1 && semanticsNode.textSelectionExtent != -1) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setTextSelection(semanticsNode.textSelectionBase, semanticsNode.textSelectionExtent);
            }
            SemanticsNode semanticsNode4 = this.accessibilityFocusedSemanticsNode;
            if (semanticsNode4 != null && semanticsNode4.id == i2) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setLiveRegion(1);
            }
            if (semanticsNode.hasAction(Action.MOVE_CURSOR_FORWARD_BY_CHARACTER)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(256);
                i3 = 1;
            } else {
                i3 = 0;
            }
            if (semanticsNode.hasAction(Action.MOVE_CURSOR_BACKWARD_BY_CHARACTER)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(512);
                i3 = 1;
            }
            if (semanticsNode.hasAction(Action.MOVE_CURSOR_FORWARD_BY_WORD)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(256);
                i3 |= 2;
            }
            if (semanticsNode.hasAction(Action.MOVE_CURSOR_BACKWARD_BY_WORD)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(512);
                i3 |= 2;
            }
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setMovementGranularities(i3);
            if (semanticsNode.maxValueLength >= 0) {
                int length = semanticsNode.value == null ? 0 : semanticsNode.value.length();
                int unused = semanticsNode.currentValueLength;
                int unused2 = semanticsNode.maxValueLength;
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setMaxTextLength((length - semanticsNode.currentValueLength) + semanticsNode.maxValueLength);
            }
        }
        if (semanticsNode.hasAction(Action.SET_SELECTION)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(131072);
        }
        if (semanticsNode.hasAction(Action.COPY)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(16384);
        }
        if (semanticsNode.hasAction(Action.CUT)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(65536);
        }
        if (semanticsNode.hasAction(Action.PASTE)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(32768);
        }
        if (semanticsNode.hasAction(Action.SET_TEXT)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(2097152);
        }
        if (semanticsNode.hasFlag(Flag.IS_BUTTON) || semanticsNode.hasFlag(Flag.IS_LINK)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClassName("android.widget.Button");
        }
        if (semanticsNode.hasFlag(Flag.IS_IMAGE)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClassName("android.widget.ImageView");
        }
        if (semanticsNode.hasAction(Action.DISMISS)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setDismissable(true);
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(1048576);
        }
        if (semanticsNode.parent != null) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setParent(this.rootAccessibilityView, semanticsNode.parent.id);
        } else {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setParent(this.rootAccessibilityView);
        }
        if (semanticsNode.previousNodeId != -1) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setTraversalAfter(this.rootAccessibilityView, semanticsNode.previousNodeId);
        }
        Rect globalRect = semanticsNode.getGlobalRect();
        if (semanticsNode.parent != null) {
            Rect globalRect2 = semanticsNode.parent.getGlobalRect();
            Rect rect = new Rect(globalRect);
            rect.offset(-globalRect2.left, -globalRect2.top);
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setBoundsInParent(rect);
        } else {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setBoundsInParent(globalRect);
        }
        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setBoundsInScreen(getBoundsInScreen(globalRect));
        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setVisibleToUser(true);
        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setEnabled(!semanticsNode.hasFlag(Flag.HAS_ENABLED_STATE) || semanticsNode.hasFlag(Flag.IS_ENABLED));
        if (semanticsNode.hasAction(Action.TAP)) {
            if (semanticsNode.onTapOverride != null) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, semanticsNode.onTapOverride.hint));
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClickable(true);
            } else {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(16);
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClickable(true);
            }
        }
        if (semanticsNode.hasAction(Action.LONG_PRESS)) {
            if (semanticsNode.onLongPressOverride != null) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(new AccessibilityNodeInfo.AccessibilityAction(32, semanticsNode.onLongPressOverride.hint));
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setLongClickable(true);
            } else {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(32);
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setLongClickable(true);
            }
        }
        Action action = Action.SCROLL_LEFT;
        if (semanticsNode.hasAction(action) || semanticsNode.hasAction(Action.SCROLL_UP) || semanticsNode.hasAction(Action.SCROLL_RIGHT) || semanticsNode.hasAction(Action.SCROLL_DOWN)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setScrollable(true);
            if (semanticsNode.hasFlag(Flag.HAS_IMPLICIT_SCROLLING)) {
                if (semanticsNode.hasAction(action) || semanticsNode.hasAction(Action.SCROLL_RIGHT)) {
                    if (shouldSetCollectionInfo(semanticsNode)) {
                        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(0, semanticsNode.scrollChildren, false));
                    } else {
                        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClassName("android.widget.HorizontalScrollView");
                    }
                } else if (shouldSetCollectionInfo(semanticsNode)) {
                    accessibilityNodeInfoObtainAccessibilityNodeInfo2.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(semanticsNode.scrollChildren, 0, false));
                } else {
                    accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClassName("android.widget.ScrollView");
                }
            }
            if (semanticsNode.hasAction(action) || semanticsNode.hasAction(Action.SCROLL_UP)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(4096);
            }
            if (semanticsNode.hasAction(Action.SCROLL_RIGHT) || semanticsNode.hasAction(Action.SCROLL_DOWN)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(8192);
            }
        }
        Action action2 = Action.INCREASE;
        if (semanticsNode.hasAction(action2) || semanticsNode.hasAction(Action.DECREASE)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClassName("android.widget.SeekBar");
            if (semanticsNode.hasAction(action2)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(4096);
            }
            if (semanticsNode.hasAction(Action.DECREASE)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(8192);
            }
        }
        if (semanticsNode.hasFlag(Flag.IS_LIVE_REGION)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setLiveRegion(1);
        }
        if (semanticsNode.hasFlag(flag)) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setText(semanticsNode.getValue());
            if (i4 >= 28) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setHintText(semanticsNode.getTextFieldHint());
            }
        } else if (!semanticsNode.hasFlag(Flag.SCOPES_ROUTE)) {
            CharSequence valueLabelHint = semanticsNode.getValueLabelHint();
            if (i4 < 28 && semanticsNode.tooltip != null) {
                valueLabelHint = ((Object) (valueLabelHint != null ? valueLabelHint : "")) + "\n" + semanticsNode.tooltip;
            }
            if (valueLabelHint != null) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setContentDescription(valueLabelHint);
            }
        }
        if (i4 >= 28 && semanticsNode.tooltip != null) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setTooltipText(semanticsNode.tooltip);
        }
        boolean zHasFlag = semanticsNode.hasFlag(Flag.HAS_CHECKED_STATE);
        boolean zHasFlag2 = semanticsNode.hasFlag(Flag.HAS_TOGGLED_STATE);
        if (!zHasFlag && !zHasFlag2) {
            z2 = false;
        }
        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setCheckable(z2);
        if (zHasFlag) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setChecked(semanticsNode.hasFlag(Flag.IS_CHECKED));
            if (semanticsNode.hasFlag(Flag.IS_IN_MUTUALLY_EXCLUSIVE_GROUP)) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClassName("android.widget.RadioButton");
            } else {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClassName("android.widget.CheckBox");
            }
        } else if (zHasFlag2) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setChecked(semanticsNode.hasFlag(Flag.IS_TOGGLED));
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setClassName("android.widget.Switch");
        }
        accessibilityNodeInfoObtainAccessibilityNodeInfo2.setSelected(semanticsNode.hasFlag(Flag.IS_SELECTED));
        if (i4 >= 28) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.setHeading(semanticsNode.hasFlag(Flag.IS_HEADER));
        }
        SemanticsNode semanticsNode5 = this.accessibilityFocusedSemanticsNode;
        if (semanticsNode5 == null || semanticsNode5.id != i2) {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(64);
        } else {
            accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(128);
        }
        if (semanticsNode.customAccessibilityActions != null) {
            for (CustomAccessibilityAction customAccessibilityAction : semanticsNode.customAccessibilityActions) {
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addAction(new AccessibilityNodeInfo.AccessibilityAction(customAccessibilityAction.resourceId, customAccessibilityAction.label));
            }
        }
        for (SemanticsNode semanticsNode6 : semanticsNode.childrenInTraversalOrder) {
            if (!semanticsNode6.hasFlag(Flag.IS_HIDDEN)) {
                if (semanticsNode6.platformViewId != -1) {
                    View platformViewById2 = this.platformViewsAccessibilityDelegate.getPlatformViewById(semanticsNode6.platformViewId);
                    if (!this.platformViewsAccessibilityDelegate.usesVirtualDisplay(semanticsNode6.platformViewId)) {
                        accessibilityNodeInfoObtainAccessibilityNodeInfo2.addChild(platformViewById2);
                    }
                }
                accessibilityNodeInfoObtainAccessibilityNodeInfo2.addChild(this.rootAccessibilityView, semanticsNode6.id);
            }
        }
        return accessibilityNodeInfoObtainAccessibilityNodeInfo2;
    }

    @SuppressLint({"SwitchIntDef"})
    public boolean externalViewRequestSendAccessibilityEvent(View view, View view2, AccessibilityEvent accessibilityEvent) {
        Integer recordFlutterId;
        if (!this.accessibilityViewEmbedder.requestSendAccessibilityEvent(view, view2, accessibilityEvent) || (recordFlutterId = this.accessibilityViewEmbedder.getRecordFlutterId(view, accessibilityEvent)) == null) {
            return false;
        }
        int eventType = accessibilityEvent.getEventType();
        if (eventType == 8) {
            this.embeddedInputFocusedNodeId = recordFlutterId;
            this.inputFocusedSemanticsNode = null;
            return true;
        }
        if (eventType == 128) {
            this.hoveredObject = null;
            return true;
        }
        if (eventType == 32768) {
            this.embeddedAccessibilityFocusedNodeId = recordFlutterId;
            this.accessibilityFocusedSemanticsNode = null;
            return true;
        }
        if (eventType != 65536) {
            return true;
        }
        this.embeddedInputFocusedNodeId = null;
        this.embeddedAccessibilityFocusedNodeId = null;
        return true;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public AccessibilityNodeInfo findFocus(int i2) {
        if (i2 == 1) {
            SemanticsNode semanticsNode = this.inputFocusedSemanticsNode;
            if (semanticsNode != null) {
                return createAccessibilityNodeInfo(semanticsNode.id);
            }
            Integer num = this.embeddedInputFocusedNodeId;
            if (num != null) {
                return createAccessibilityNodeInfo(num.intValue());
            }
        } else if (i2 != 2) {
            return null;
        }
        SemanticsNode semanticsNode2 = this.accessibilityFocusedSemanticsNode;
        if (semanticsNode2 != null) {
            return createAccessibilityNodeInfo(semanticsNode2.id);
        }
        Integer num2 = this.embeddedAccessibilityFocusedNodeId;
        if (num2 != null) {
            return createAccessibilityNodeInfo(num2.intValue());
        }
        return null;
    }

    @VisibleForTesting
    public boolean getAccessibleNavigation() {
        return this.accessibleNavigation;
    }

    @VisibleForTesting
    public int getHoveredObjectId() {
        return this.hoveredObject.id;
    }

    public boolean isAccessibilityEnabled() {
        return this.accessibilityManager.isEnabled();
    }

    public boolean isTouchExplorationEnabled() {
        return this.accessibilityManager.isTouchExplorationEnabled();
    }

    @VisibleForTesting
    public AccessibilityNodeInfo obtainAccessibilityNodeInfo(View view) {
        return AccessibilityNodeInfo.obtain(view);
    }

    public boolean onAccessibilityHoverEvent(MotionEvent motionEvent) {
        return onAccessibilityHoverEvent(motionEvent, false);
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public boolean performAction(int i2, int i3, @Nullable Bundle bundle) {
        if (i2 >= 65536) {
            boolean zPerformAction = this.accessibilityViewEmbedder.performAction(i2, i3, bundle);
            if (zPerformAction && i3 == 128) {
                this.embeddedAccessibilityFocusedNodeId = null;
            }
            return zPerformAction;
        }
        SemanticsNode semanticsNode = this.flutterSemanticsTree.get(Integer.valueOf(i2));
        if (semanticsNode == null) {
            return false;
        }
        switch (i3) {
            case 16:
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.TAP);
                return true;
            case 32:
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.LONG_PRESS);
                return true;
            case 64:
                if (this.accessibilityFocusedSemanticsNode == null) {
                    this.rootAccessibilityView.invalidate();
                }
                this.accessibilityFocusedSemanticsNode = semanticsNode;
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.DID_GAIN_ACCESSIBILITY_FOCUS);
                HashMap map = new HashMap();
                map.put("type", "didGainFocus");
                map.put("nodeId", Integer.valueOf(semanticsNode.id));
                this.accessibilityChannel.channel.send(map);
                sendAccessibilityEvent(i2, 32768);
                if (semanticsNode.hasAction(Action.INCREASE) || semanticsNode.hasAction(Action.DECREASE)) {
                    sendAccessibilityEvent(i2, 4);
                }
                return true;
            case 128:
                SemanticsNode semanticsNode2 = this.accessibilityFocusedSemanticsNode;
                if (semanticsNode2 != null && semanticsNode2.id == i2) {
                    this.accessibilityFocusedSemanticsNode = null;
                }
                Integer num = this.embeddedAccessibilityFocusedNodeId;
                if (num != null && num.intValue() == i2) {
                    this.embeddedAccessibilityFocusedNodeId = null;
                }
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.DID_LOSE_ACCESSIBILITY_FOCUS);
                sendAccessibilityEvent(i2, 65536);
                return true;
            case 256:
                return performCursorMoveAction(semanticsNode, i2, bundle, true);
            case 512:
                return performCursorMoveAction(semanticsNode, i2, bundle, false);
            case 4096:
                Action action = Action.SCROLL_UP;
                if (semanticsNode.hasAction(action)) {
                    this.accessibilityChannel.dispatchSemanticsAction(i2, action);
                } else {
                    Action action2 = Action.SCROLL_LEFT;
                    if (semanticsNode.hasAction(action2)) {
                        this.accessibilityChannel.dispatchSemanticsAction(i2, action2);
                    } else {
                        Action action3 = Action.INCREASE;
                        if (!semanticsNode.hasAction(action3)) {
                            return false;
                        }
                        semanticsNode.value = semanticsNode.increasedValue;
                        semanticsNode.valueAttributes = semanticsNode.increasedValueAttributes;
                        sendAccessibilityEvent(i2, 4);
                        this.accessibilityChannel.dispatchSemanticsAction(i2, action3);
                    }
                }
                return true;
            case 8192:
                Action action4 = Action.SCROLL_DOWN;
                if (semanticsNode.hasAction(action4)) {
                    this.accessibilityChannel.dispatchSemanticsAction(i2, action4);
                } else {
                    Action action5 = Action.SCROLL_RIGHT;
                    if (semanticsNode.hasAction(action5)) {
                        this.accessibilityChannel.dispatchSemanticsAction(i2, action5);
                    } else {
                        Action action6 = Action.DECREASE;
                        if (!semanticsNode.hasAction(action6)) {
                            return false;
                        }
                        semanticsNode.value = semanticsNode.decreasedValue;
                        semanticsNode.valueAttributes = semanticsNode.decreasedValueAttributes;
                        sendAccessibilityEvent(i2, 4);
                        this.accessibilityChannel.dispatchSemanticsAction(i2, action6);
                    }
                }
                return true;
            case 16384:
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.COPY);
                return true;
            case 32768:
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.PASTE);
                return true;
            case 65536:
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.CUT);
                return true;
            case 131072:
                HashMap map2 = new HashMap();
                if (bundle != null && bundle.containsKey(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_START_INT) && bundle.containsKey(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_END_INT)) {
                    map2.put(TtmlNode.RUBY_BASE, Integer.valueOf(bundle.getInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_START_INT)));
                    map2.put(TtmlNode.ATTR_TTS_EXTENT, Integer.valueOf(bundle.getInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_END_INT)));
                } else {
                    map2.put(TtmlNode.RUBY_BASE, Integer.valueOf(semanticsNode.textSelectionExtent));
                    map2.put(TtmlNode.ATTR_TTS_EXTENT, Integer.valueOf(semanticsNode.textSelectionExtent));
                }
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.SET_SELECTION, map2);
                SemanticsNode semanticsNode3 = this.flutterSemanticsTree.get(Integer.valueOf(i2));
                semanticsNode3.textSelectionBase = ((Integer) map2.get(TtmlNode.RUBY_BASE)).intValue();
                semanticsNode3.textSelectionExtent = ((Integer) map2.get(TtmlNode.ATTR_TTS_EXTENT)).intValue();
                return true;
            case 1048576:
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.DISMISS);
                return true;
            case 2097152:
                return performSetText(semanticsNode, i2, bundle);
            case 16908342:
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.SHOW_ON_SCREEN);
                return true;
            default:
                CustomAccessibilityAction customAccessibilityAction = this.customAccessibilityActions.get(Integer.valueOf(i3 - FIRST_RESOURCE_ID));
                if (customAccessibilityAction == null) {
                    return false;
                }
                this.accessibilityChannel.dispatchSemanticsAction(i2, Action.CUSTOM_ACTION, Integer.valueOf(customAccessibilityAction.id));
                return true;
        }
    }

    public void release() {
        this.isReleased = true;
        this.platformViewsAccessibilityDelegate.detachAccessibilityBridge();
        setOnAccessibilityChangeListener(null);
        this.accessibilityManager.removeAccessibilityStateChangeListener(this.accessibilityStateChangeListener);
        this.accessibilityManager.removeTouchExplorationStateChangeListener(this.touchExplorationStateChangeListener);
        this.contentResolver.unregisterContentObserver(this.animationScaleObserver);
        this.accessibilityChannel.setAccessibilityMessageHandler(null);
    }

    public void reset() {
        this.flutterSemanticsTree.clear();
        SemanticsNode semanticsNode = this.accessibilityFocusedSemanticsNode;
        if (semanticsNode != null) {
            sendAccessibilityEvent(semanticsNode.id, 65536);
        }
        this.accessibilityFocusedSemanticsNode = null;
        this.hoveredObject = null;
        sendWindowContentChangeEvent(0);
    }

    @VisibleForTesting
    public void sendAccessibilityEvent(int i2, int i3) {
        if (this.accessibilityManager.isEnabled()) {
            sendAccessibilityEvent(obtainAccessibilityEvent(i2, i3));
        }
    }

    public void setOnAccessibilityChangeListener(@Nullable OnAccessibilityChangeListener onAccessibilityChangeListener) {
        this.onAccessibilityChangeListener = onAccessibilityChangeListener;
    }

    void updateCustomAccessibilityActions(@NonNull ByteBuffer byteBuffer, @NonNull String[] strArr) {
        while (byteBuffer.hasRemaining()) {
            CustomAccessibilityAction orCreateAccessibilityAction = getOrCreateAccessibilityAction(byteBuffer.getInt());
            orCreateAccessibilityAction.overrideId = byteBuffer.getInt();
            int i2 = byteBuffer.getInt();
            String str = null;
            orCreateAccessibilityAction.label = i2 == -1 ? null : strArr[i2];
            int i3 = byteBuffer.getInt();
            if (i3 != -1) {
                str = strArr[i3];
            }
            orCreateAccessibilityAction.hint = str;
        }
    }

    void updateSemantics(@NonNull ByteBuffer byteBuffer, @NonNull String[] strArr, @NonNull ByteBuffer[] byteBufferArr) {
        SemanticsNode semanticsNode;
        SemanticsNode semanticsNode2;
        float f2;
        float f3;
        WindowInsets rootWindowInsets;
        View platformViewById;
        ArrayList arrayList = new ArrayList();
        while (byteBuffer.hasRemaining()) {
            SemanticsNode orCreateSemanticsNode = getOrCreateSemanticsNode(byteBuffer.getInt());
            orCreateSemanticsNode.updateWith(byteBuffer, strArr, byteBufferArr);
            if (!orCreateSemanticsNode.hasFlag(Flag.IS_HIDDEN)) {
                if (orCreateSemanticsNode.hasFlag(Flag.IS_FOCUSED)) {
                    this.inputFocusedSemanticsNode = orCreateSemanticsNode;
                }
                if (orCreateSemanticsNode.hadPreviousConfig) {
                    arrayList.add(orCreateSemanticsNode);
                }
                if (orCreateSemanticsNode.platformViewId != -1 && !this.platformViewsAccessibilityDelegate.usesVirtualDisplay(orCreateSemanticsNode.platformViewId) && (platformViewById = this.platformViewsAccessibilityDelegate.getPlatformViewById(orCreateSemanticsNode.platformViewId)) != null) {
                    platformViewById.setImportantForAccessibility(0);
                }
            }
        }
        HashSet hashSet = new HashSet();
        SemanticsNode rootSemanticsNode = getRootSemanticsNode();
        ArrayList<SemanticsNode> arrayList2 = new ArrayList();
        if (rootSemanticsNode != null) {
            float[] fArr = new float[16];
            Matrix.setIdentityM(fArr, 0);
            if ((Build.VERSION.SDK_INT >= 28 ? doesLayoutInDisplayCutoutModeRequireLeftInset() : true) && (rootWindowInsets = this.rootAccessibilityView.getRootWindowInsets()) != null) {
                if (!this.lastLeftFrameInset.equals(Integer.valueOf(rootWindowInsets.getSystemWindowInsetLeft()))) {
                    rootSemanticsNode.globalGeometryDirty = true;
                    rootSemanticsNode.inverseTransformDirty = true;
                }
                this.lastLeftFrameInset = Integer.valueOf(rootWindowInsets.getSystemWindowInsetLeft());
                Matrix.translateM(fArr, 0, r4.intValue(), 0.0f, 0.0f);
            }
            rootSemanticsNode.updateRecursively(fArr, hashSet, false);
            rootSemanticsNode.collectRoutes(arrayList2);
        }
        SemanticsNode semanticsNode3 = null;
        for (SemanticsNode semanticsNode4 : arrayList2) {
            if (!this.flutterNavigationStack.contains(Integer.valueOf(semanticsNode4.id))) {
                semanticsNode3 = semanticsNode4;
            }
        }
        if (semanticsNode3 == null && arrayList2.size() > 0) {
            semanticsNode3 = (SemanticsNode) arrayList2.get(arrayList2.size() - 1);
        }
        if (semanticsNode3 != null && (semanticsNode3.id != this.previousRouteId || arrayList2.size() != this.flutterNavigationStack.size())) {
            this.previousRouteId = semanticsNode3.id;
            onWindowNameChange(semanticsNode3);
        }
        this.flutterNavigationStack.clear();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            this.flutterNavigationStack.add(Integer.valueOf(((SemanticsNode) it.next()).id));
        }
        Iterator<Map.Entry<Integer, SemanticsNode>> it2 = this.flutterSemanticsTree.entrySet().iterator();
        while (it2.hasNext()) {
            SemanticsNode value = it2.next().getValue();
            if (!hashSet.contains(value)) {
                willRemoveSemanticsNode(value);
                it2.remove();
            }
        }
        sendWindowContentChangeEvent(0);
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            SemanticsNode semanticsNode5 = (SemanticsNode) it3.next();
            if (semanticsNode5.didScroll()) {
                AccessibilityEvent accessibilityEventObtainAccessibilityEvent = obtainAccessibilityEvent(semanticsNode5.id, 4096);
                float f4 = semanticsNode5.scrollPosition;
                float f5 = semanticsNode5.scrollExtentMax;
                if (Float.isInfinite(semanticsNode5.scrollExtentMax)) {
                    if (f4 > SCROLL_POSITION_CAP_FOR_INFINITY) {
                        f4 = 70000.0f;
                    }
                    f5 = 100000.0f;
                }
                if (Float.isInfinite(semanticsNode5.scrollExtentMin)) {
                    f2 = f5 + SCROLL_EXTENT_FOR_INFINITY;
                    if (f4 < -70000.0f) {
                        f4 = -70000.0f;
                    }
                    f3 = f4 + SCROLL_EXTENT_FOR_INFINITY;
                } else {
                    f2 = f5 - semanticsNode5.scrollExtentMin;
                    f3 = f4 - semanticsNode5.scrollExtentMin;
                }
                if (semanticsNode5.hadAction(Action.SCROLL_UP) || semanticsNode5.hadAction(Action.SCROLL_DOWN)) {
                    accessibilityEventObtainAccessibilityEvent.setScrollY((int) f3);
                    accessibilityEventObtainAccessibilityEvent.setMaxScrollY((int) f2);
                } else if (semanticsNode5.hadAction(Action.SCROLL_LEFT) || semanticsNode5.hadAction(Action.SCROLL_RIGHT)) {
                    accessibilityEventObtainAccessibilityEvent.setScrollX((int) f3);
                    accessibilityEventObtainAccessibilityEvent.setMaxScrollX((int) f2);
                }
                if (semanticsNode5.scrollChildren > 0) {
                    accessibilityEventObtainAccessibilityEvent.setItemCount(semanticsNode5.scrollChildren);
                    accessibilityEventObtainAccessibilityEvent.setFromIndex(semanticsNode5.scrollIndex);
                    Iterator it4 = semanticsNode5.childrenInHitTestOrder.iterator();
                    int i2 = 0;
                    while (it4.hasNext()) {
                        if (!((SemanticsNode) it4.next()).hasFlag(Flag.IS_HIDDEN)) {
                            i2++;
                        }
                    }
                    accessibilityEventObtainAccessibilityEvent.setToIndex((semanticsNode5.scrollIndex + i2) - 1);
                }
                sendAccessibilityEvent(accessibilityEventObtainAccessibilityEvent);
            }
            if (semanticsNode5.hasFlag(Flag.IS_LIVE_REGION) && semanticsNode5.didChangeLabel()) {
                sendWindowContentChangeEvent(semanticsNode5.id);
            }
            SemanticsNode semanticsNode6 = this.accessibilityFocusedSemanticsNode;
            if (semanticsNode6 != null && semanticsNode6.id == semanticsNode5.id) {
                Flag flag = Flag.IS_SELECTED;
                if (!semanticsNode5.hadFlag(flag) && semanticsNode5.hasFlag(flag)) {
                    AccessibilityEvent accessibilityEventObtainAccessibilityEvent2 = obtainAccessibilityEvent(semanticsNode5.id, 4);
                    accessibilityEventObtainAccessibilityEvent2.getText().add(semanticsNode5.label);
                    sendAccessibilityEvent(accessibilityEventObtainAccessibilityEvent2);
                }
            }
            SemanticsNode semanticsNode7 = this.inputFocusedSemanticsNode;
            if (semanticsNode7 != null && semanticsNode7.id == semanticsNode5.id && ((semanticsNode2 = this.lastInputFocusedSemanticsNode) == null || semanticsNode2.id != this.inputFocusedSemanticsNode.id)) {
                this.lastInputFocusedSemanticsNode = this.inputFocusedSemanticsNode;
                sendAccessibilityEvent(obtainAccessibilityEvent(semanticsNode5.id, 8));
            } else if (this.inputFocusedSemanticsNode == null) {
                this.lastInputFocusedSemanticsNode = null;
            }
            SemanticsNode semanticsNode8 = this.inputFocusedSemanticsNode;
            if (semanticsNode8 != null && semanticsNode8.id == semanticsNode5.id) {
                Flag flag2 = Flag.IS_TEXT_FIELD;
                if (semanticsNode5.hadFlag(flag2) && semanticsNode5.hasFlag(flag2) && ((semanticsNode = this.accessibilityFocusedSemanticsNode) == null || semanticsNode.id == this.inputFocusedSemanticsNode.id)) {
                    String str = semanticsNode5.previousValue != null ? semanticsNode5.previousValue : "";
                    String str2 = semanticsNode5.value != null ? semanticsNode5.value : "";
                    AccessibilityEvent accessibilityEventCreateTextChangedEvent = createTextChangedEvent(semanticsNode5.id, str, str2);
                    if (accessibilityEventCreateTextChangedEvent != null) {
                        sendAccessibilityEvent(accessibilityEventCreateTextChangedEvent);
                    }
                    if (semanticsNode5.previousTextSelectionBase != semanticsNode5.textSelectionBase || semanticsNode5.previousTextSelectionExtent != semanticsNode5.textSelectionExtent) {
                        AccessibilityEvent accessibilityEventObtainAccessibilityEvent3 = obtainAccessibilityEvent(semanticsNode5.id, 8192);
                        accessibilityEventObtainAccessibilityEvent3.getText().add(str2);
                        accessibilityEventObtainAccessibilityEvent3.setFromIndex(semanticsNode5.textSelectionBase);
                        accessibilityEventObtainAccessibilityEvent3.setToIndex(semanticsNode5.textSelectionExtent);
                        accessibilityEventObtainAccessibilityEvent3.setItemCount(str2.length());
                        sendAccessibilityEvent(accessibilityEventObtainAccessibilityEvent3);
                    }
                }
            }
        }
    }

    @VisibleForTesting
    public AccessibilityBridge(@NonNull View view, @NonNull AccessibilityChannel accessibilityChannel, @NonNull final AccessibilityManager accessibilityManager, @NonNull ContentResolver contentResolver, @NonNull AccessibilityViewEmbedder accessibilityViewEmbedder, @NonNull PlatformViewsAccessibilityDelegate platformViewsAccessibilityDelegate) {
        this.flutterSemanticsTree = new HashMap();
        this.customAccessibilityActions = new HashMap();
        this.accessibilityFeatureFlags = 0;
        this.flutterNavigationStack = new ArrayList();
        this.previousRouteId = 0;
        this.lastLeftFrameInset = 0;
        this.accessibleNavigation = false;
        this.isReleased = false;
        this.accessibilityMessageHandler = new AccessibilityChannel.AccessibilityMessageHandler() { // from class: io.flutter.view.AccessibilityBridge.1
            @Override // io.flutter.embedding.engine.systemchannels.AccessibilityChannel.AccessibilityMessageHandler
            public void announce(@NonNull String str) {
                AccessibilityBridge.this.rootAccessibilityView.announceForAccessibility(str);
            }

            @Override // io.flutter.embedding.engine.systemchannels.AccessibilityChannel.AccessibilityMessageHandler
            public void onFocus(int i2) {
                AccessibilityBridge.this.sendAccessibilityEvent(i2, 8);
            }

            @Override // io.flutter.embedding.engine.systemchannels.AccessibilityChannel.AccessibilityMessageHandler
            public void onLongPress(int i2) {
                AccessibilityBridge.this.sendAccessibilityEvent(i2, 2);
            }

            @Override // io.flutter.embedding.engine.systemchannels.AccessibilityChannel.AccessibilityMessageHandler
            public void onTap(int i2) {
                AccessibilityBridge.this.sendAccessibilityEvent(i2, 1);
            }

            @Override // io.flutter.embedding.engine.systemchannels.AccessibilityChannel.AccessibilityMessageHandler
            public void onTooltip(@NonNull String str) {
                if (Build.VERSION.SDK_INT >= 28) {
                    return;
                }
                AccessibilityEvent accessibilityEventObtainAccessibilityEvent = AccessibilityBridge.this.obtainAccessibilityEvent(0, 32);
                accessibilityEventObtainAccessibilityEvent.getText().add(str);
                AccessibilityBridge.this.sendAccessibilityEvent(accessibilityEventObtainAccessibilityEvent);
            }

            @Override // io.flutter.embedding.engine.FlutterJNI.AccessibilityDelegate
            public void updateCustomAccessibilityActions(ByteBuffer byteBuffer, String[] strArr) {
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                AccessibilityBridge.this.updateCustomAccessibilityActions(byteBuffer, strArr);
            }

            @Override // io.flutter.embedding.engine.FlutterJNI.AccessibilityDelegate
            public void updateSemantics(ByteBuffer byteBuffer, String[] strArr, ByteBuffer[] byteBufferArr) {
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                for (ByteBuffer byteBuffer2 : byteBufferArr) {
                    byteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
                }
                AccessibilityBridge.this.updateSemantics(byteBuffer, strArr, byteBufferArr);
            }
        };
        AccessibilityManager.AccessibilityStateChangeListener accessibilityStateChangeListener = new AccessibilityManager.AccessibilityStateChangeListener() { // from class: io.flutter.view.AccessibilityBridge.2
            @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
            public void onAccessibilityStateChanged(boolean z2) {
                if (AccessibilityBridge.this.isReleased) {
                    return;
                }
                if (z2) {
                    AccessibilityBridge.this.accessibilityChannel.setAccessibilityMessageHandler(AccessibilityBridge.this.accessibilityMessageHandler);
                    AccessibilityBridge.this.accessibilityChannel.onAndroidAccessibilityEnabled();
                } else {
                    AccessibilityBridge.this.setAccessibleNavigation(false);
                    AccessibilityBridge.this.accessibilityChannel.setAccessibilityMessageHandler(null);
                    AccessibilityBridge.this.accessibilityChannel.onAndroidAccessibilityDisabled();
                }
                if (AccessibilityBridge.this.onAccessibilityChangeListener != null) {
                    AccessibilityBridge.this.onAccessibilityChangeListener.onAccessibilityChanged(z2, AccessibilityBridge.this.accessibilityManager.isTouchExplorationEnabled());
                }
            }
        };
        this.accessibilityStateChangeListener = accessibilityStateChangeListener;
        ContentObserver contentObserver = new ContentObserver(new Handler()) { // from class: io.flutter.view.AccessibilityBridge.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z2) {
                onChange(z2, null);
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z2, Uri uri) {
                if (AccessibilityBridge.this.isReleased) {
                    return;
                }
                String string = Settings.Global.getString(AccessibilityBridge.this.contentResolver, "transition_animation_scale");
                if (string == null || !string.equals("0")) {
                    AccessibilityBridge.access$1172(AccessibilityBridge.this, ~AccessibilityFeature.DISABLE_ANIMATIONS.value);
                } else {
                    AccessibilityBridge.access$1176(AccessibilityBridge.this, AccessibilityFeature.DISABLE_ANIMATIONS.value);
                }
                AccessibilityBridge.this.sendLatestAccessibilityFlagsToFlutter();
            }
        };
        this.animationScaleObserver = contentObserver;
        this.rootAccessibilityView = view;
        this.accessibilityChannel = accessibilityChannel;
        this.accessibilityManager = accessibilityManager;
        this.contentResolver = contentResolver;
        this.accessibilityViewEmbedder = accessibilityViewEmbedder;
        this.platformViewsAccessibilityDelegate = platformViewsAccessibilityDelegate;
        accessibilityStateChangeListener.onAccessibilityStateChanged(accessibilityManager.isEnabled());
        accessibilityManager.addAccessibilityStateChangeListener(accessibilityStateChangeListener);
        AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: io.flutter.view.AccessibilityBridge.4
            @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
            public void onTouchExplorationStateChanged(boolean z2) {
                if (AccessibilityBridge.this.isReleased) {
                    return;
                }
                if (!z2) {
                    AccessibilityBridge.this.setAccessibleNavigation(false);
                    AccessibilityBridge.this.onTouchExplorationExit();
                }
                if (AccessibilityBridge.this.onAccessibilityChangeListener != null) {
                    AccessibilityBridge.this.onAccessibilityChangeListener.onAccessibilityChanged(accessibilityManager.isEnabled(), z2);
                }
            }
        };
        this.touchExplorationStateChangeListener = touchExplorationStateChangeListener;
        touchExplorationStateChangeListener.onTouchExplorationStateChanged(accessibilityManager.isTouchExplorationEnabled());
        accessibilityManager.addTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
        contentObserver.onChange(false);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("transition_animation_scale"), false, contentObserver);
        if (Build.VERSION.SDK_INT >= 31) {
            setBoldTextFlag();
        }
        platformViewsAccessibilityDelegate.attachAccessibilityBridge(this);
    }

    @VisibleForTesting
    public AccessibilityNodeInfo obtainAccessibilityNodeInfo(View view, int i2) {
        return AccessibilityNodeInfo.obtain(view, i2);
    }

    public boolean onAccessibilityHoverEvent(MotionEvent motionEvent, boolean z2) {
        if (!this.accessibilityManager.isTouchExplorationEnabled() || this.flutterSemanticsTree.isEmpty()) {
            return false;
        }
        SemanticsNode semanticsNodeHitTest = getRootSemanticsNode().hitTest(new float[]{motionEvent.getX(), motionEvent.getY(), 0.0f, 1.0f}, z2);
        if (semanticsNodeHitTest != null && semanticsNodeHitTest.platformViewId != -1) {
            if (z2) {
                return false;
            }
            return this.accessibilityViewEmbedder.onAccessibilityHoverEvent(semanticsNodeHitTest.id, motionEvent);
        }
        if (motionEvent.getAction() == 9 || motionEvent.getAction() == 7) {
            handleTouchExploration(motionEvent.getX(), motionEvent.getY(), z2);
        } else {
            if (motionEvent.getAction() != 10) {
                Log.d("flutter", "unexpected accessibility hover event: " + motionEvent);
                return false;
            }
            onTouchExplorationExit();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAccessibilityEvent(@NonNull AccessibilityEvent accessibilityEvent) {
        if (this.accessibilityManager.isEnabled()) {
            this.rootAccessibilityView.getParent().requestSendAccessibilityEvent(this.rootAccessibilityView, accessibilityEvent);
        }
    }

    @VisibleForTesting
    public AccessibilityEvent obtainAccessibilityEvent(int i2) {
        return AccessibilityEvent.obtain(i2);
    }
}
