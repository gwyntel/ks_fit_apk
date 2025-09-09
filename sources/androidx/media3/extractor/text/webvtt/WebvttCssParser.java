package androidx.media3.extractor.text.webvtt;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ColorParser;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import com.alipay.sdk.m.u.i;
import com.google.common.base.Ascii;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
final class WebvttCssParser {
    private static final String PROPERTY_BGCOLOR = "background-color";
    private static final String PROPERTY_COLOR = "color";
    private static final String PROPERTY_FONT_FAMILY = "font-family";
    private static final String PROPERTY_FONT_SIZE = "font-size";
    private static final String PROPERTY_FONT_STYLE = "font-style";
    private static final String PROPERTY_FONT_WEIGHT = "font-weight";
    private static final String PROPERTY_RUBY_POSITION = "ruby-position";
    private static final String PROPERTY_TEXT_COMBINE_UPRIGHT = "text-combine-upright";
    private static final String PROPERTY_TEXT_DECORATION = "text-decoration";
    private static final String RULE_END = "}";
    private static final String RULE_START = "{";
    private static final String TAG = "WebvttCssParser";
    private static final String VALUE_ALL = "all";
    private static final String VALUE_BOLD = "bold";
    private static final String VALUE_DIGITS = "digits";
    private static final String VALUE_ITALIC = "italic";
    private static final String VALUE_OVER = "over";
    private static final String VALUE_UNDER = "under";
    private static final String VALUE_UNDERLINE = "underline";
    private static final Pattern VOICE_NAME_PATTERN = Pattern.compile("\\[voice=\"([^\"]*)\"\\]");
    private static final Pattern FONT_SIZE_PATTERN = Pattern.compile("^((?:[0-9]*\\.)?[0-9]+)(px|em|%)$");
    private final ParsableByteArray styleInput = new ParsableByteArray();
    private final StringBuilder stringBuilder = new StringBuilder();

    static String a(ParsableByteArray parsableByteArray, StringBuilder sb) {
        c(parsableByteArray);
        if (parsableByteArray.bytesLeft() == 0) {
            return null;
        }
        String identifier = parseIdentifier(parsableByteArray, sb);
        if (!"".equals(identifier)) {
            return identifier;
        }
        return "" + ((char) parsableByteArray.readUnsignedByte());
    }

    private void applySelectorToStyle(WebvttCssStyle webvttCssStyle, String str) {
        if ("".equals(str)) {
            return;
        }
        int iIndexOf = str.indexOf(91);
        if (iIndexOf != -1) {
            Matcher matcher = VOICE_NAME_PATTERN.matcher(str.substring(iIndexOf));
            if (matcher.matches()) {
                webvttCssStyle.setTargetVoice((String) Assertions.checkNotNull(matcher.group(1)));
            }
            str = str.substring(0, iIndexOf);
        }
        String[] strArrSplit = Util.split(str, "\\.");
        String str2 = strArrSplit[0];
        int iIndexOf2 = str2.indexOf(35);
        if (iIndexOf2 != -1) {
            webvttCssStyle.setTargetTagName(str2.substring(0, iIndexOf2));
            webvttCssStyle.setTargetId(str2.substring(iIndexOf2 + 1));
        } else {
            webvttCssStyle.setTargetTagName(str2);
        }
        if (strArrSplit.length > 1) {
            webvttCssStyle.setTargetClasses((String[]) Util.nullSafeArrayCopyOfRange(strArrSplit, 1, strArrSplit.length));
        }
    }

    static void b(ParsableByteArray parsableByteArray) {
        while (!TextUtils.isEmpty(parsableByteArray.readLine())) {
        }
    }

    static void c(ParsableByteArray parsableByteArray) {
        while (true) {
            for (boolean z2 = true; parsableByteArray.bytesLeft() > 0 && z2; z2 = false) {
                if (maybeSkipWhitespace(parsableByteArray) || maybeSkipComment(parsableByteArray)) {
                    break;
                }
            }
            return;
        }
    }

    private static boolean maybeSkipComment(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        int iLimit = parsableByteArray.limit();
        byte[] data = parsableByteArray.getData();
        if (position + 2 > iLimit) {
            return false;
        }
        int i2 = position + 1;
        if (data[position] != 47) {
            return false;
        }
        int i3 = position + 2;
        if (data[i2] != 42) {
            return false;
        }
        while (true) {
            int i4 = i3 + 1;
            if (i4 >= iLimit) {
                parsableByteArray.skipBytes(iLimit - parsableByteArray.getPosition());
                return true;
            }
            if (((char) data[i3]) == '*' && ((char) data[i4]) == '/') {
                i3 += 2;
                iLimit = i3;
            } else {
                i3 = i4;
            }
        }
    }

    private static boolean maybeSkipWhitespace(ParsableByteArray parsableByteArray) {
        char cPeekCharAtPosition = peekCharAtPosition(parsableByteArray, parsableByteArray.getPosition());
        if (cPeekCharAtPosition != '\t' && cPeekCharAtPosition != '\n' && cPeekCharAtPosition != '\f' && cPeekCharAtPosition != '\r' && cPeekCharAtPosition != ' ') {
            return false;
        }
        parsableByteArray.skipBytes(1);
        return true;
    }

    private static void parseFontSize(String str, WebvttCssStyle webvttCssStyle) {
        Matcher matcher = FONT_SIZE_PATTERN.matcher(Ascii.toLowerCase(str));
        if (!matcher.matches()) {
            Log.w(TAG, "Invalid font-size: '" + str + "'.");
            return;
        }
        String str2 = (String) Assertions.checkNotNull(matcher.group(2));
        str2.hashCode();
        switch (str2) {
            case "%":
                webvttCssStyle.setFontSizeUnit(3);
                break;
            case "em":
                webvttCssStyle.setFontSizeUnit(2);
                break;
            case "px":
                webvttCssStyle.setFontSizeUnit(1);
                break;
            default:
                throw new IllegalStateException();
        }
        webvttCssStyle.setFontSize(Float.parseFloat((String) Assertions.checkNotNull(matcher.group(1))));
    }

    private static String parseIdentifier(ParsableByteArray parsableByteArray, StringBuilder sb) {
        boolean z2 = false;
        sb.setLength(0);
        int position = parsableByteArray.getPosition();
        int iLimit = parsableByteArray.limit();
        while (position < iLimit && !z2) {
            char c2 = (char) parsableByteArray.getData()[position];
            if ((c2 < 'A' || c2 > 'Z') && ((c2 < 'a' || c2 > 'z') && !((c2 >= '0' && c2 <= '9') || c2 == '#' || c2 == '-' || c2 == '.' || c2 == '_'))) {
                z2 = true;
            } else {
                position++;
                sb.append(c2);
            }
        }
        parsableByteArray.skipBytes(position - parsableByteArray.getPosition());
        return sb.toString();
    }

    @Nullable
    private static String parsePropertyValue(ParsableByteArray parsableByteArray, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder();
        boolean z2 = false;
        while (!z2) {
            int position = parsableByteArray.getPosition();
            String strA = a(parsableByteArray, sb);
            if (strA == null) {
                return null;
            }
            if ("}".equals(strA) || i.f9802b.equals(strA)) {
                parsableByteArray.setPosition(position);
                z2 = true;
            } else {
                sb2.append(strA);
            }
        }
        return sb2.toString();
    }

    @Nullable
    private static String parseSelector(ParsableByteArray parsableByteArray, StringBuilder sb) {
        c(parsableByteArray);
        if (parsableByteArray.bytesLeft() < 5 || !"::cue".equals(parsableByteArray.readString(5))) {
            return null;
        }
        int position = parsableByteArray.getPosition();
        String strA = a(parsableByteArray, sb);
        if (strA == null) {
            return null;
        }
        if (RULE_START.equals(strA)) {
            parsableByteArray.setPosition(position);
            return "";
        }
        String cueTarget = "(".equals(strA) ? readCueTarget(parsableByteArray) : null;
        if (")".equals(a(parsableByteArray, sb))) {
            return cueTarget;
        }
        return null;
    }

    private static void parseStyleDeclaration(ParsableByteArray parsableByteArray, WebvttCssStyle webvttCssStyle, StringBuilder sb) {
        c(parsableByteArray);
        String identifier = parseIdentifier(parsableByteArray, sb);
        if (!"".equals(identifier) && ":".equals(a(parsableByteArray, sb))) {
            c(parsableByteArray);
            String propertyValue = parsePropertyValue(parsableByteArray, sb);
            if (propertyValue == null || "".equals(propertyValue)) {
                return;
            }
            int position = parsableByteArray.getPosition();
            String strA = a(parsableByteArray, sb);
            if (!i.f9802b.equals(strA)) {
                if (!"}".equals(strA)) {
                    return;
                } else {
                    parsableByteArray.setPosition(position);
                }
            }
            if ("color".equals(identifier)) {
                webvttCssStyle.setFontColor(ColorParser.parseCssColor(propertyValue));
                return;
            }
            if (PROPERTY_BGCOLOR.equals(identifier)) {
                webvttCssStyle.setBackgroundColor(ColorParser.parseCssColor(propertyValue));
                return;
            }
            boolean z2 = true;
            if (PROPERTY_RUBY_POSITION.equals(identifier)) {
                if (VALUE_OVER.equals(propertyValue)) {
                    webvttCssStyle.setRubyPosition(1);
                    return;
                } else {
                    if (VALUE_UNDER.equals(propertyValue)) {
                        webvttCssStyle.setRubyPosition(2);
                        return;
                    }
                    return;
                }
            }
            if (PROPERTY_TEXT_COMBINE_UPRIGHT.equals(identifier)) {
                if (!"all".equals(propertyValue) && !propertyValue.startsWith(VALUE_DIGITS)) {
                    z2 = false;
                }
                webvttCssStyle.setCombineUpright(z2);
                return;
            }
            if (PROPERTY_TEXT_DECORATION.equals(identifier)) {
                if ("underline".equals(propertyValue)) {
                    webvttCssStyle.setUnderline(true);
                    return;
                }
                return;
            }
            if (PROPERTY_FONT_FAMILY.equals(identifier)) {
                webvttCssStyle.setFontFamily(propertyValue);
                return;
            }
            if (PROPERTY_FONT_WEIGHT.equals(identifier)) {
                if ("bold".equals(propertyValue)) {
                    webvttCssStyle.setBold(true);
                }
            } else if (PROPERTY_FONT_STYLE.equals(identifier)) {
                if ("italic".equals(propertyValue)) {
                    webvttCssStyle.setItalic(true);
                }
            } else if (PROPERTY_FONT_SIZE.equals(identifier)) {
                parseFontSize(propertyValue, webvttCssStyle);
            }
        }
    }

    private static char peekCharAtPosition(ParsableByteArray parsableByteArray, int i2) {
        return (char) parsableByteArray.getData()[i2];
    }

    private static String readCueTarget(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        int iLimit = parsableByteArray.limit();
        boolean z2 = false;
        while (position < iLimit && !z2) {
            int i2 = position + 1;
            z2 = ((char) parsableByteArray.getData()[position]) == ')';
            position = i2;
        }
        return parsableByteArray.readString((position - 1) - parsableByteArray.getPosition()).trim();
    }

    public List<WebvttCssStyle> parseBlock(ParsableByteArray parsableByteArray) {
        this.stringBuilder.setLength(0);
        int position = parsableByteArray.getPosition();
        b(parsableByteArray);
        this.styleInput.reset(parsableByteArray.getData(), parsableByteArray.getPosition());
        this.styleInput.setPosition(position);
        ArrayList arrayList = new ArrayList();
        while (true) {
            String selector = parseSelector(this.styleInput, this.stringBuilder);
            if (selector == null) {
                return arrayList;
            }
            if (!RULE_START.equals(a(this.styleInput, this.stringBuilder))) {
                return arrayList;
            }
            WebvttCssStyle webvttCssStyle = new WebvttCssStyle();
            applySelectorToStyle(webvttCssStyle, selector);
            String str = null;
            boolean z2 = false;
            while (!z2) {
                int position2 = this.styleInput.getPosition();
                String strA = a(this.styleInput, this.stringBuilder);
                boolean z3 = strA == null || "}".equals(strA);
                if (!z3) {
                    this.styleInput.setPosition(position2);
                    parseStyleDeclaration(this.styleInput, webvttCssStyle, this.stringBuilder);
                }
                str = strA;
                z2 = z3;
            }
            if ("}".equals(str)) {
                arrayList.add(webvttCssStyle);
            }
        }
    }
}
