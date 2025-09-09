package com.fluttercandies.image_editor.util;

import android.graphics.PorterDuff;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.records.CervicalMucusRecord;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.fluttercandies.image_editor.core.BitmapWrapper;
import com.fluttercandies.image_editor.option.AddTextOpt;
import com.fluttercandies.image_editor.option.ClipOption;
import com.fluttercandies.image_editor.option.ColorOption;
import com.fluttercandies.image_editor.option.FlipOption;
import com.fluttercandies.image_editor.option.FormatOption;
import com.fluttercandies.image_editor.option.MixImageOpt;
import com.fluttercandies.image_editor.option.Option;
import com.fluttercandies.image_editor.option.RotateOption;
import com.fluttercandies.image_editor.option.ScaleOption;
import com.fluttercandies.image_editor.option.Text;
import com.fluttercandies.image_editor.option.draw.DrawOption;
import io.flutter.plugin.common.MethodCall;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0018\u0010\r\u001a\u00020\u000e2\u000e\u0010\u000f\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0010H\u0002J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0002J\u0012\u0010\u0014\u001a\u00020\u00152\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0002J\u0012\u0010\u0016\u001a\u00020\u00172\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0002J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0012\u0010\u001c\u001a\u00020\u001d2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0002J\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0002J\u0014\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u0002J\u0015\u0010#\u001a\u0002H$\"\u0004\b\u0000\u0010$*\u00020\u0001¢\u0006\u0002\u0010%¨\u0006&"}, d2 = {"Lcom/fluttercandies/image_editor/util/ConvertUtils;", "", "()V", "convertMapOption", "", "Lcom/fluttercandies/image_editor/option/Option;", "optionList", "bitmapWrapper", "Lcom/fluttercandies/image_editor/core/BitmapWrapper;", "convertToPorterDuffMode", "Landroid/graphics/PorterDuff$Mode;", "type", "", "convertToText", "Lcom/fluttercandies/image_editor/option/Text;", "v", "", "getClipOption", "Lcom/fluttercandies/image_editor/option/ClipOption;", "optionMap", "getColorOption", "Lcom/fluttercandies/image_editor/option/ColorOption;", "getFlipOption", "Lcom/fluttercandies/image_editor/option/FlipOption;", "getFormatOption", "Lcom/fluttercandies/image_editor/option/FormatOption;", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "getRotateOption", "Lcom/fluttercandies/image_editor/option/RotateOption;", "getScaleOption", "Lcom/fluttercandies/image_editor/option/ScaleOption;", "getTextOption", "Lcom/fluttercandies/image_editor/option/AddTextOpt;", "valueMap", "asValue", ExifInterface.GPS_DIRECTION_TRUE, "(Ljava/lang/Object;)Ljava/lang/Object;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nConvertUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConvertUtils.kt\ncom/fluttercandies/image_editor/util/ConvertUtils\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,193:1\n1549#2:194\n1620#2,3:195\n*S KotlinDebug\n*F\n+ 1 ConvertUtils.kt\ncom/fluttercandies/image_editor/util/ConvertUtils\n*L\n124#1:194\n124#1:195,3\n*E\n"})
/* loaded from: classes3.dex */
public final class ConvertUtils {

    @NotNull
    public static final ConvertUtils INSTANCE = new ConvertUtils();

    private ConvertUtils() {
    }

    private final Text convertToText(Map<?, ?> v2) {
        Object obj = v2.get("text");
        Intrinsics.checkNotNull(obj);
        String str = (String) asValue(obj);
        Object obj2 = v2.get("x");
        Intrinsics.checkNotNull(obj2);
        int iIntValue = ((Number) asValue(obj2)).intValue();
        Object obj3 = v2.get("y");
        Intrinsics.checkNotNull(obj3);
        int iIntValue2 = ((Number) asValue(obj3)).intValue();
        Object obj4 = v2.get("size");
        Intrinsics.checkNotNull(obj4);
        int iIntValue3 = ((Number) asValue(obj4)).intValue();
        Object obj5 = v2.get("r");
        Intrinsics.checkNotNull(obj5);
        int iIntValue4 = ((Number) asValue(obj5)).intValue();
        Object obj6 = v2.get("g");
        Intrinsics.checkNotNull(obj6);
        int iIntValue5 = ((Number) asValue(obj6)).intValue();
        Object obj7 = v2.get("b");
        Intrinsics.checkNotNull(obj7);
        int iIntValue6 = ((Number) asValue(obj7)).intValue();
        Object obj8 = v2.get("a");
        Intrinsics.checkNotNull(obj8);
        int iIntValue7 = ((Number) asValue(obj8)).intValue();
        Object obj9 = v2.get("fontName");
        Intrinsics.checkNotNull(obj9);
        String str2 = (String) asValue(obj9);
        Object obj10 = v2.get(TtmlNode.ATTR_TTS_TEXT_ALIGN);
        Intrinsics.checkNotNull(obj10);
        return new Text(str, iIntValue, iIntValue2, iIntValue3, iIntValue4, iIntValue5, iIntValue6, iIntValue7, str2, ConvertUtilsKt.toTextAlign(obj10));
    }

    private final ClipOption getClipOption(Object optionMap) {
        if (!(optionMap instanceof Map)) {
            return new ClipOption(0, 0, -1, -1);
        }
        Map map = (Map) optionMap;
        Object obj = map.get(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
        int iIntValue = ((Number) obj).intValue();
        Object obj2 = map.get(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Number");
        int iIntValue2 = ((Number) obj2).intValue();
        Object obj3 = map.get("x");
        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Number");
        int iIntValue3 = ((Number) obj3).intValue();
        Object obj4 = map.get("y");
        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Number");
        return new ClipOption(iIntValue3, ((Number) obj4).intValue(), iIntValue, iIntValue2);
    }

    private final ColorOption getColorOption(Object optionMap) {
        if (!(optionMap instanceof Map)) {
            return ColorOption.INSTANCE.getSrc();
        }
        Object obj = ((Map) optionMap).get("matrix");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<*>");
        List list = (List) obj;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Object obj2 : list) {
            arrayList.add(Float.valueOf(obj2 instanceof Double ? (float) ((Number) obj2).doubleValue() : 0.0f));
        }
        return new ColorOption(CollectionsKt.toFloatArray(arrayList));
    }

    private final FlipOption getFlipOption(Object optionMap) {
        if (!(optionMap instanceof Map)) {
            return new FlipOption(false, false, 3, null);
        }
        Map map = (Map) optionMap;
        Object obj = map.get("h");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        Object obj2 = map.get("v");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Boolean");
        return new FlipOption(zBooleanValue, ((Boolean) obj2).booleanValue());
    }

    private final RotateOption getRotateOption(Object optionMap) {
        if (!(optionMap instanceof Map)) {
            return new RotateOption(0);
        }
        Object obj = ((Map) optionMap).get("degree");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
        return new RotateOption(((Integer) obj).intValue());
    }

    private final ScaleOption getScaleOption(Object optionMap) {
        if (!(optionMap instanceof Map)) {
            return null;
        }
        Map map = (Map) optionMap;
        Object obj = map.get(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue = ((Integer) obj).intValue();
        Object obj2 = map.get(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue2 = ((Integer) obj2).intValue();
        Object obj3 = map.get("keepRatio");
        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Boolean");
        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
        Object obj4 = map.get("keepWidthFirst");
        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Boolean");
        return new ScaleOption(iIntValue, iIntValue2, zBooleanValue, ((Boolean) obj4).booleanValue());
    }

    private final AddTextOpt getTextOption(Object valueMap) {
        if (!(valueMap instanceof Map)) {
            return null;
        }
        Object obj = ((Map) valueMap).get("texts");
        Intrinsics.checkNotNull(obj);
        List list = (List) asValue(obj);
        if (list.isEmpty()) {
            return null;
        }
        AddTextOpt addTextOpt = new AddTextOpt();
        for (Object obj2 : list) {
            if (obj2 instanceof Map) {
                addTextOpt.addText(convertToText((Map) obj2));
            }
        }
        return addTextOpt;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> T asValue(@NotNull Object obj) {
        Intrinsics.checkNotNullParameter(obj, "<this>");
        return obj;
    }

    @NotNull
    public final List<Option> convertMapOption(@NotNull List<? extends Object> optionList, @NotNull BitmapWrapper bitmapWrapper) {
        Intrinsics.checkNotNullParameter(optionList, "optionList");
        Intrinsics.checkNotNullParameter(bitmapWrapper, "bitmapWrapper");
        ArrayList arrayList = new ArrayList();
        if (bitmapWrapper.getDegree() != 0) {
            arrayList.add(new RotateOption(bitmapWrapper.getDegree()));
        }
        if (!bitmapWrapper.getFlipOption().canIgnore()) {
            arrayList.add(bitmapWrapper.getFlipOption());
        }
        for (Object obj : optionList) {
            if (obj instanceof Map) {
                Map map = (Map) obj;
                Object obj2 = map.get("value");
                if (obj2 instanceof Map) {
                    Object obj3 = map.get("type");
                    if (Intrinsics.areEqual(obj3, "flip")) {
                        arrayList.add(getFlipOption(obj2));
                    } else if (Intrinsics.areEqual(obj3, "clip")) {
                        arrayList.add(getClipOption(obj2));
                    } else if (Intrinsics.areEqual(obj3, "rotate")) {
                        arrayList.add(getRotateOption(obj2));
                    } else if (Intrinsics.areEqual(obj3, "color")) {
                        arrayList.add(getColorOption(obj2));
                    } else if (Intrinsics.areEqual(obj3, "scale")) {
                        ScaleOption scaleOption = getScaleOption(obj2);
                        if (scaleOption != null) {
                            arrayList.add(scaleOption);
                        }
                    } else if (Intrinsics.areEqual(obj3, "add_text")) {
                        AddTextOpt textOption = getTextOption(obj2);
                        if (textOption != null) {
                            arrayList.add(textOption);
                        }
                    } else if (Intrinsics.areEqual(obj3, "mix_image")) {
                        arrayList.add(new MixImageOpt((Map) obj2));
                    } else if (Intrinsics.areEqual(obj3, "draw")) {
                        arrayList.add(new DrawOption((Map) obj2));
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    @NotNull
    public final PorterDuff.Mode convertToPorterDuffMode(@NotNull String type) {
        Intrinsics.checkNotNullParameter(type, "type");
        switch (type.hashCode()) {
            case -1954086600:
                if (type.equals("srcATop")) {
                    return PorterDuff.Mode.SRC_ATOP;
                }
                break;
            case -1953637160:
                if (type.equals("srcOver")) {
                    return PorterDuff.Mode.SRC_OVER;
                }
                break;
            case -1338968417:
                if (type.equals("darken")) {
                    return PorterDuff.Mode.DARKEN;
                }
                break;
            case -1322311863:
                if (type.equals("dstOut")) {
                    return PorterDuff.Mode.DST_OUT;
                }
                break;
            case -1091287984:
                if (type.equals("overlay")) {
                    return PorterDuff.Mode.OVERLAY;
                }
                break;
            case -907689876:
                if (type.equals("screen")) {
                    return PorterDuff.Mode.SCREEN;
                }
                break;
            case -894304566:
                if (type.equals("srcOut")) {
                    return PorterDuff.Mode.SRC_OUT;
                }
                break;
            case 99781:
                if (type.equals("dst")) {
                    return PorterDuff.Mode.DST;
                }
                break;
            case 114148:
                if (type.equals("src")) {
                    return PorterDuff.Mode.SRC;
                }
                break;
            case 118875:
                if (type.equals("xor")) {
                    return PorterDuff.Mode.XOR;
                }
                break;
            case 94746189:
                if (type.equals(CervicalMucusRecord.Appearance.CLEAR)) {
                    return PorterDuff.Mode.CLEAR;
                }
                break;
            case 95891914:
                if (type.equals("dstIn")) {
                    return PorterDuff.Mode.DST_IN;
                }
                break;
            case 109698601:
                if (type.equals("srcIn")) {
                    return PorterDuff.Mode.SRC_IN;
                }
                break;
            case 170546239:
                if (type.equals("lighten")) {
                    return PorterDuff.Mode.LIGHTEN;
                }
                break;
            case 653829668:
                if (type.equals("multiply")) {
                    return PorterDuff.Mode.MULTIPLY;
                }
                break;
            case 1957556377:
                if (type.equals("dstATop")) {
                    return PorterDuff.Mode.DST_ATOP;
                }
                break;
            case 1958005817:
                if (type.equals("dstOver")) {
                    return PorterDuff.Mode.DST_OVER;
                }
                break;
        }
        return PorterDuff.Mode.SRC_OVER;
    }

    @NotNull
    public final FormatOption getFormatOption(@NotNull MethodCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        Object objArgument = call.argument("fmt");
        Intrinsics.checkNotNull(objArgument);
        return new FormatOption((Map) objArgument);
    }
}
