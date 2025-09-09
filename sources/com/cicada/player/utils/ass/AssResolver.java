package com.cicada.player.utils.ass;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.media3.extractor.text.ttml.TtmlNode;
import androidx.webkit.ProxyConfig;
import com.alipay.sdk.m.u.i;
import com.facebook.appevents.UserDataStore;
import com.umeng.analytics.pro.bc;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes3.dex */
public class AssResolver {
    private static final String TAG = "AssResolver";
    public static final String TEXT_PATTERN = "\\{[^\\{]+\\}";
    public static final Pattern pattern = Pattern.compile(TEXT_PATTERN);
    private AssHeader mAssHeader;
    private TextViewPool mTextViewPool;
    private int videoDisplayWidth = -1;
    private int videoDisplayHeight = -1;

    private static class ContentAttribute {
        public String fontName;
        public double fontSize;
        public int mBackColour;
        public boolean mBold;
        public int mBorderStyle;
        public boolean mItalic;
        public int mOutlineColour;
        public double mOutlineWidth;
        public int mPrimaryColour;
        public int mSecondaryColour;
        public double mShadow;
        public boolean mStrikeOut;
        public boolean mUnderline;
        public String overrideStyle;
        public String text;

        private ContentAttribute() {
        }

        public String toString() {
            return "ContentAttribute{text='" + this.text + "', overrideStyle='" + this.overrideStyle + "', fontName='" + this.fontName + "', fontSize=" + this.fontSize + ", mPrimaryColour=" + this.mPrimaryColour + ", mSecondaryColour=" + this.mSecondaryColour + ", mBold=" + this.mBold + ", mItalic=" + this.mItalic + ", mUnderline=" + this.mUnderline + ", mStrikeOut=" + this.mStrikeOut + ", mOutlineColour=" + this.mOutlineColour + ", mOutlineWidth=" + this.mOutlineWidth + ", mBorderStyle=" + this.mBorderStyle + ", mBackColour=" + this.mBackColour + ", mShadow=" + this.mShadow + '}';
        }
    }

    private static class LocationAttribute {
        public int mAlignment;
        public double mAngle;
        public double mScaleX;
        public double mScaleY;
        public int marginL;
        public int marginR;
        public int marginV;
        public double posX;
        public double posY;

        private LocationAttribute() {
        }
    }

    public AssResolver(Context context) {
        this.mTextViewPool = new TextViewPool(context);
    }

    private String convertRgbColor(String str) {
        StringBuilder sb;
        String strSubstring;
        if (str.length() == 8) {
            sb = new StringBuilder();
            sb.append(str.substring(6, 8));
            sb.append(str.substring(4, 6));
            strSubstring = str.substring(2, 4);
        } else {
            sb = new StringBuilder();
            sb.append(str.substring(4, 6));
            sb.append(str.substring(2, 4));
            strSubstring = str.substring(0, 2);
        }
        sb.append(strSubstring);
        return sb.toString();
    }

    private void fillContentAttribute(AssStyle assStyle, LinkedList<ContentAttribute> linkedList, LocationAttribute locationAttribute) {
        Map<String, Object> overrideStyle;
        int size = linkedList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ContentAttribute contentAttribute = linkedList.get(i2);
            contentAttribute.text = contentAttribute.text.replace("\\N", "\n").replace("\\n", "\n");
            contentAttribute.fontName = assStyle.mFontName;
            contentAttribute.fontSize = scaleYSize(assStyle.mFontSize);
            contentAttribute.mBold = assStyle.mBold == 1;
            contentAttribute.mItalic = assStyle.mItalic == 1;
            contentAttribute.mStrikeOut = assStyle.mStrikeOut == 1;
            contentAttribute.mUnderline = assStyle.mUnderline == 1;
            contentAttribute.mBorderStyle = assStyle.mBorderStyle;
            contentAttribute.mOutlineWidth = scaleYSize(assStyle.mOutline);
            contentAttribute.mShadow = scaleYSize(assStyle.mShadow);
            contentAttribute.mBackColour = rgbaToArgb(assStyle.mBackColour);
            contentAttribute.mOutlineColour = rgbaToArgb(assStyle.mOutlineColour);
            contentAttribute.mPrimaryColour = rgbaToArgb(assStyle.mPrimaryColour);
            contentAttribute.mSecondaryColour = rgbaToArgb(assStyle.mSecondaryColour);
            if (!TextUtils.isEmpty(contentAttribute.overrideStyle) && (overrideStyle = parseOverrideStyle(contentAttribute.overrideStyle)) != null) {
                if (overrideStyle.containsKey("primaryColour")) {
                    contentAttribute.mPrimaryColour = ((Integer) overrideStyle.get("primaryColour")).intValue();
                }
                if (overrideStyle.containsKey("strikeOut")) {
                    contentAttribute.mStrikeOut = ((Boolean) overrideStyle.get("strikeOut")).booleanValue();
                }
                if (overrideStyle.containsKey(TtmlNode.UNDERLINE)) {
                    contentAttribute.mUnderline = ((Boolean) overrideStyle.get(TtmlNode.UNDERLINE)).booleanValue();
                }
                if (overrideStyle.containsKey(TtmlNode.ITALIC)) {
                    contentAttribute.mItalic = ((Boolean) overrideStyle.get(TtmlNode.ITALIC)).booleanValue();
                }
                if (overrideStyle.containsKey(TtmlNode.BOLD)) {
                    contentAttribute.mBold = ((Boolean) overrideStyle.get(TtmlNode.BOLD)).booleanValue();
                }
                if (overrideStyle.containsKey(TtmlNode.ATTR_TTS_FONT_SIZE)) {
                    contentAttribute.fontSize = scaleYSize(((Double) overrideStyle.get(TtmlNode.ATTR_TTS_FONT_SIZE)).doubleValue());
                }
                if (overrideStyle.containsKey("fontName")) {
                    contentAttribute.fontName = (String) overrideStyle.get("fontName");
                }
                if (overrideStyle.containsKey("posX")) {
                    locationAttribute.posX = ((Double) overrideStyle.get("posX")).doubleValue();
                }
                if (overrideStyle.containsKey("posY")) {
                    locationAttribute.posY = ((Double) overrideStyle.get("posY")).doubleValue();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00cb A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.text.SpannableStringBuilder getFinalStr(java.util.LinkedList<com.cicada.player.utils.ass.AssResolver.ContentAttribute> r10) {
        /*
            r9 = this;
            android.text.SpannableStringBuilder r0 = new android.text.SpannableStringBuilder
            r0.<init>()
            java.util.Iterator r10 = r10.iterator()
            r1 = 0
            r2 = r1
        Lb:
            boolean r3 = r10.hasNext()
            if (r3 == 0) goto Ld4
            java.lang.Object r3 = r10.next()
            com.cicada.player.utils.ass.AssResolver$ContentAttribute r3 = (com.cicada.player.utils.ass.AssResolver.ContentAttribute) r3
            java.lang.String r4 = r3.text
            int r4 = r4.length()
            int r1 = r1 + r4
            java.lang.String r4 = r3.text
            r0.append(r4)
            double r4 = r3.mOutlineWidth
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            r5 = 1
            r6 = 33
            if (r4 <= 0) goto L6f
            com.cicada.player.utils.ass.BorderedSpan$BorderStyle r4 = new com.cicada.player.utils.ass.BorderedSpan$BorderStyle
            r4.<init>()
            java.lang.String r7 = r3.fontName
            r4.fontName = r7
            double r7 = r3.fontSize
            r4.fontSize = r7
            int r7 = r3.mPrimaryColour
            r4.mPrimaryColour = r7
            int r7 = r3.mSecondaryColour
            r4.mSecondaryColour = r7
            boolean r7 = r3.mBold
            r4.mBold = r7
            boolean r7 = r3.mItalic
            r4.mItalic = r7
            boolean r7 = r3.mUnderline
            r4.mUnderline = r7
            boolean r7 = r3.mStrikeOut
            r4.mStrikeOut = r7
            int r7 = r3.mOutlineColour
            r4.mOutlineColour = r7
            double r7 = r3.mOutlineWidth
            r4.mOutlineWidth = r7
            int r7 = r3.mBorderStyle
            if (r7 != r5) goto L67
            double r7 = r3.mShadow
            r4.mShadowWidth = r7
            int r7 = r3.mBackColour
            r4.mShadowColor = r7
        L67:
            com.cicada.player.utils.ass.BorderedSpan r7 = new com.cicada.player.utils.ass.BorderedSpan
            r7.<init>(r4)
            r0.setSpan(r7, r2, r1, r6)
        L6f:
            android.text.style.TypefaceSpan r4 = new android.text.style.TypefaceSpan
            java.lang.String r7 = r3.fontName
            r4.<init>(r7)
            r0.setSpan(r4, r2, r1, r6)
            android.text.style.AbsoluteSizeSpan r4 = new android.text.style.AbsoluteSizeSpan
            double r7 = r3.fontSize
            int r7 = (int) r7
            r4.<init>(r7)
            r0.setSpan(r4, r2, r1, r6)
            android.text.style.ForegroundColorSpan r4 = new android.text.style.ForegroundColorSpan
            int r7 = r3.mPrimaryColour
            r4.<init>(r7)
            r0.setSpan(r4, r2, r1, r6)
            boolean r4 = r3.mBold
            if (r4 == 0) goto La0
            boolean r7 = r3.mItalic
            if (r7 == 0) goto La0
            android.text.style.StyleSpan r4 = new android.text.style.StyleSpan
            r5 = 3
            r4.<init>(r5)
        L9c:
            r0.setSpan(r4, r2, r1, r6)
            goto Lb3
        La0:
            if (r4 == 0) goto La8
            android.text.style.StyleSpan r4 = new android.text.style.StyleSpan
            r4.<init>(r5)
            goto L9c
        La8:
            boolean r4 = r3.mItalic
            if (r4 == 0) goto Lb3
            android.text.style.StyleSpan r4 = new android.text.style.StyleSpan
            r5 = 2
            r4.<init>(r5)
            goto L9c
        Lb3:
            boolean r4 = r3.mUnderline
            if (r4 == 0) goto Lbf
            android.text.style.UnderlineSpan r4 = new android.text.style.UnderlineSpan
            r4.<init>()
            r0.setSpan(r4, r2, r1, r6)
        Lbf:
            boolean r4 = r3.mStrikeOut
            if (r4 == 0) goto Lcb
            android.text.style.StrikethroughSpan r4 = new android.text.style.StrikethroughSpan
            r4.<init>()
            r0.setSpan(r4, r2, r1, r6)
        Lcb:
            java.lang.String r3 = r3.text
            int r3 = r3.length()
            int r2 = r2 + r3
            goto Lb
        Ld4:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.cicada.player.utils.ass.AssResolver.getFinalStr(java.util.LinkedList):android.text.SpannableStringBuilder");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:24:0x0070. Please report as an issue. */
    private RelativeLayout.LayoutParams getLayoutParams(LocationAttribute locationAttribute, float f2, float f3) {
        int iScaleXSize;
        int iScaleYSize;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        double d2 = locationAttribute.posX;
        if (d2 > 0.0d || locationAttribute.posY > 0.0d) {
            int i2 = locationAttribute.mAlignment;
            double dScaleXSize = scaleXSize(d2);
            double dScaleYSize = scaleYSize(locationAttribute.posY);
            switch (i2) {
                case 1:
                    layoutParams.leftMargin = (int) dScaleXSize;
                    iScaleYSize = (int) (dScaleYSize - f3);
                    break;
                case 2:
                default:
                    f2 /= 2.0f;
                    dScaleXSize -= f2;
                    layoutParams.leftMargin = (int) dScaleXSize;
                    iScaleYSize = (int) (dScaleYSize - f3);
                    break;
                case 3:
                    dScaleXSize -= f2;
                    layoutParams.leftMargin = (int) dScaleXSize;
                    iScaleYSize = (int) (dScaleYSize - f3);
                    break;
                case 4:
                    layoutParams.leftMargin = (int) dScaleXSize;
                    f3 /= 2.0f;
                    iScaleYSize = (int) (dScaleYSize - f3);
                    break;
                case 5:
                    f2 /= 2.0f;
                    dScaleXSize -= f2;
                    layoutParams.leftMargin = (int) dScaleXSize;
                    f3 /= 2.0f;
                    iScaleYSize = (int) (dScaleYSize - f3);
                    break;
                case 6:
                    dScaleXSize -= f2;
                    layoutParams.leftMargin = (int) dScaleXSize;
                    f3 /= 2.0f;
                    iScaleYSize = (int) (dScaleYSize - f3);
                    break;
                case 7:
                    iScaleXSize = (int) scaleXSize(dScaleXSize);
                    layoutParams.leftMargin = iScaleXSize;
                    iScaleYSize = (int) scaleYSize(dScaleYSize);
                    break;
                case 8:
                    f2 /= 2.0f;
                    iScaleXSize = (int) (dScaleXSize - f2);
                    layoutParams.leftMargin = iScaleXSize;
                    iScaleYSize = (int) scaleYSize(dScaleYSize);
                    break;
                case 9:
                    iScaleXSize = (int) (dScaleXSize - f2);
                    layoutParams.leftMargin = iScaleXSize;
                    iScaleYSize = (int) scaleYSize(dScaleYSize);
                    break;
            }
        } else {
            switch (locationAttribute.mAlignment) {
                case 1:
                    layoutParams.addRule(12);
                    layoutParams.addRule(9);
                    break;
                case 2:
                default:
                    layoutParams.addRule(12);
                    layoutParams.addRule(14);
                    break;
                case 3:
                    layoutParams.addRule(12);
                    layoutParams.addRule(11);
                    break;
                case 4:
                    layoutParams.addRule(15);
                    layoutParams.addRule(9);
                    break;
                case 5:
                    layoutParams.addRule(13);
                    break;
                case 6:
                    layoutParams.addRule(15);
                    layoutParams.addRule(11);
                    break;
                case 7:
                    layoutParams.addRule(10);
                    layoutParams.addRule(9);
                    break;
                case 8:
                    layoutParams.addRule(10);
                    layoutParams.addRule(14);
                    break;
                case 9:
                    layoutParams.addRule(10);
                    layoutParams.addRule(11);
                    break;
            }
            layoutParams.leftMargin = locationAttribute.marginL;
            layoutParams.rightMargin = locationAttribute.marginR;
            iScaleYSize = locationAttribute.marginV;
        }
        layoutParams.topMargin = iScaleYSize;
        return layoutParams;
    }

    private LocationAttribute getLocationAttribute(AssDialogue assDialogue, AssStyle assStyle) {
        LocationAttribute locationAttribute = new LocationAttribute();
        locationAttribute.mAlignment = assStyle.mAlignment;
        locationAttribute.marginL = assStyle.mMarginL;
        locationAttribute.marginR = assStyle.mMarginR;
        locationAttribute.marginV = assStyle.mMarginV;
        int i2 = assDialogue.mMarginL;
        if (i2 != 0) {
            locationAttribute.marginL = i2;
        }
        int i3 = assDialogue.mMarginR;
        if (i3 != 0) {
            locationAttribute.marginR = i3;
        }
        int i4 = assDialogue.mMarginV;
        if (i4 != 0) {
            locationAttribute.marginV = i4;
        }
        locationAttribute.mAngle = assStyle.mAngle;
        locationAttribute.mScaleX = assStyle.mScaleX;
        locationAttribute.mScaleY = assStyle.mScaleY;
        return locationAttribute;
    }

    private Map<String, Object> parseOverrideStyle(String str) throws NumberFormatException {
        StringBuilder sb;
        String strSubstring;
        Object objValueOf;
        String str2;
        String strReplaceAll = str.substring(str.indexOf("{") + 1, str.lastIndexOf(i.f9804d)).replaceAll("\\\\", "\\$");
        if (!strReplaceAll.contains("$")) {
            return null;
        }
        HashMap map = new HashMap();
        for (String str3 : strReplaceAll.split("\\$")) {
            if (str3.startsWith(UserDataStore.FIRST_NAME)) {
                objValueOf = str3.substring(2).trim();
                str2 = "fontName";
            } else {
                if (str3.startsWith("fs")) {
                    try {
                        map.put(TtmlNode.ATTR_TTS_FONT_SIZE, Double.valueOf(str3.substring(2).trim()));
                    } catch (Exception unused) {
                    }
                } else if (str3.startsWith("b")) {
                    objValueOf = Boolean.valueOf(str3.startsWith("b1"));
                    str2 = TtmlNode.BOLD;
                } else if (str3.startsWith("i")) {
                    objValueOf = Boolean.valueOf(str3.startsWith("i1"));
                    str2 = TtmlNode.ITALIC;
                } else if (str3.startsWith(bc.aN)) {
                    objValueOf = Boolean.valueOf(str3.startsWith("u1"));
                    str2 = TtmlNode.UNDERLINE;
                } else if (str3.startsWith("s")) {
                    objValueOf = Boolean.valueOf(str3.startsWith("s1"));
                    str2 = "strikeOut";
                } else if (str3.startsWith("c&H") || str3.startsWith("1c&H")) {
                    String strTrim = str3.substring(0, str3.lastIndexOf("&")).trim();
                    if (strTrim.startsWith("c&H")) {
                        sb = new StringBuilder();
                        sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                        strSubstring = strTrim.substring(3);
                    } else {
                        sb = new StringBuilder();
                        sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                        strSubstring = strTrim.substring(4);
                    }
                    sb.append(convertRgbColor(strSubstring.trim()));
                    objValueOf = Integer.valueOf(Color.parseColor(sb.toString()));
                    str2 = "primaryColour";
                } else if (str3.startsWith("pos")) {
                    String[] strArrSplit = str3.substring(4, str3.length() - 1).split(",");
                    map.put("posX", Double.valueOf(strArrSplit[0]));
                    objValueOf = Double.valueOf(strArrSplit[1]);
                    str2 = "posY";
                }
            }
            map.put(str2, objValueOf);
        }
        return map;
    }

    private int rgbaToArgb(int i2) {
        return Color.parseColor(MqttTopic.MULTI_LEVEL_WILDCARD + String.format("%02x", Integer.valueOf(255 - (i2 & 255))) + String.format("%06x", Integer.valueOf(i2 >>> 8)));
    }

    private double scaleXSize(double d2) {
        int i2;
        int i3 = this.videoDisplayWidth;
        return (i3 <= 0 || (i2 = this.mAssHeader.mPlayResX) <= 0) ? d2 : (d2 * i3) / i2;
    }

    private double scaleYSize(double d2) {
        int i2;
        int i3 = this.videoDisplayHeight;
        return (i3 <= 0 || (i2 = this.mAssHeader.mPlayResY) <= 0) ? d2 : (d2 * i3) / i2;
    }

    private LinkedList<ContentAttribute> splitContent(AssDialogue assDialogue) {
        ContentAttribute contentAttribute;
        LinkedList<ContentAttribute> linkedList = new LinkedList<>();
        Matcher matcher = pattern.matcher(assDialogue.mText);
        if (matcher.find()) {
            String[] strArrSplit = assDialogue.mText.split(TEXT_PATTERN, -1);
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                if (TextUtils.isEmpty(strArrSplit[i2])) {
                    contentAttribute = null;
                } else {
                    contentAttribute = new ContentAttribute();
                    contentAttribute.text = strArrSplit[i2];
                }
                if (i2 != 0) {
                    String strGroup = matcher.group();
                    if (contentAttribute != null) {
                        contentAttribute.overrideStyle = strGroup;
                    }
                }
                if (contentAttribute != null) {
                    linkedList.add(contentAttribute);
                }
            }
        } else {
            ContentAttribute contentAttribute2 = new ContentAttribute();
            contentAttribute2.text = assDialogue.mText;
            linkedList.add(contentAttribute2);
        }
        return linkedList;
    }

    public void destroy() {
    }

    public void dismiss(AssTextView assTextView) {
        TextViewPool textViewPool = this.mTextViewPool;
        if (textViewPool != null) {
            textViewPool.recycle(assTextView);
        }
    }

    public AssTextView setAssDialog(String str) {
        AssTextView assTextViewObtain = this.mTextViewPool.obtain();
        assTextViewObtain.setContent(str);
        AssDialogue assDialogue = AssUtils.parseAssDialogue(this.mAssHeader, str);
        AssStyle assStyle = this.mAssHeader.mStyles.get(assDialogue.mStyle.replace(ProxyConfig.MATCH_ALL_SCHEMES, ""));
        if (assDialogue.mText.contains("{\\p0}")) {
            return assTextViewObtain;
        }
        LinkedList<ContentAttribute> linkedListSplitContent = splitContent(assDialogue);
        LocationAttribute locationAttribute = getLocationAttribute(assDialogue, assStyle);
        fillContentAttribute(assStyle, linkedListSplitContent, locationAttribute);
        assTextViewObtain.setText(getFinalStr(linkedListSplitContent), TextView.BufferType.SPANNABLE);
        assTextViewObtain.setScaleX((float) locationAttribute.mScaleX);
        assTextViewObtain.setScaleY((float) locationAttribute.mScaleY);
        assTextViewObtain.setRotation((float) locationAttribute.mAngle);
        assTextViewObtain.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        float measuredWidth = assTextViewObtain.getMeasuredWidth();
        float measuredHeight = assTextViewObtain.getMeasuredHeight();
        RelativeLayout.LayoutParams layoutParams = getLayoutParams(locationAttribute, measuredWidth, measuredHeight);
        layoutParams.width = (int) measuredWidth;
        layoutParams.height = (int) measuredHeight;
        assTextViewObtain.setLayoutParams(layoutParams);
        assTextViewObtain.setGravity(17);
        return assTextViewObtain;
    }

    public void setAssHeaders(String str) {
        this.mAssHeader = AssUtils.parseAssHeader(str);
    }

    public void setFontTypeMap(Map<String, Typeface> map) {
    }

    public void setVideoDisplaySize(int i2, int i3) {
        this.videoDisplayWidth = i2;
        this.videoDisplayHeight = i3;
    }
}
