package com.alibaba.fastjson.parser.deserializer;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public abstract class AbstractDateDeserializer extends ContextObjectDeserializer implements ObjectDeserializer {
    protected abstract <T> T cast(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2);

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null, 0);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i2) throws ParseException {
        Object objValueOf;
        SimpleDateFormat simpleDateFormat;
        Date date;
        SimpleDateFormat simpleDateFormat2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 2) {
            long jLongValue = jSONLexer.longValue();
            jSONLexer.nextToken(16);
            if ("unixtime".equals(str)) {
                jLongValue *= 1000;
            }
            objValueOf = Long.valueOf(jLongValue);
        } else {
            Date date2 = null;
            if (jSONLexer.token() == 4) {
                String strStringVal = jSONLexer.stringVal();
                if (str != null) {
                    if ("yyyy-MM-dd HH:mm:ss.SSSSSSSSS".equals(str) && (type instanceof Class) && ((Class) type).getName().equals("java.sql.Timestamp")) {
                        return (T) TypeUtils.castToTimestamp(strStringVal);
                    }
                    try {
                        simpleDateFormat = new SimpleDateFormat(str, defaultJSONParser.lexer.getLocale());
                    } catch (IllegalArgumentException e2) {
                        if (str.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                            try {
                                simpleDateFormat = new SimpleDateFormat(str.replaceAll(ExifInterface.GPS_DIRECTION_TRUE, "'T'"), defaultJSONParser.lexer.getLocale());
                            } catch (IllegalArgumentException unused) {
                                throw e2;
                            }
                        } else {
                            simpleDateFormat = null;
                        }
                    }
                    if (JSON.defaultTimeZone != null) {
                        simpleDateFormat.setTimeZone(defaultJSONParser.lexer.getTimeZone());
                    }
                    try {
                        date = simpleDateFormat.parse(strStringVal);
                    } catch (ParseException unused2) {
                        date = null;
                    }
                    if (date == null && JSON.defaultLocale == Locale.CHINA) {
                        try {
                            simpleDateFormat2 = new SimpleDateFormat(str, Locale.US);
                        } catch (IllegalArgumentException e3) {
                            simpleDateFormat2 = simpleDateFormat;
                            if (str.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                                try {
                                    simpleDateFormat2 = new SimpleDateFormat(str.replaceAll(ExifInterface.GPS_DIRECTION_TRUE, "'T'"), defaultJSONParser.lexer.getLocale());
                                } catch (IllegalArgumentException unused3) {
                                    throw e3;
                                }
                            }
                        }
                        simpleDateFormat2.setTimeZone(defaultJSONParser.lexer.getTimeZone());
                        try {
                            date = simpleDateFormat2.parse(strStringVal);
                        } catch (ParseException unused4) {
                            date = null;
                        }
                    }
                    if (date != null) {
                        date2 = date;
                    } else if (str.equals("yyyy-MM-dd'T'HH:mm:ss.SSS") && strStringVal.length() == 19) {
                        try {
                            SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", JSON.defaultLocale);
                            simpleDateFormat3.setTimeZone(JSON.defaultTimeZone);
                            date2 = simpleDateFormat3.parse(strStringVal);
                        } catch (ParseException unused5) {
                        }
                    }
                }
                if (date2 == null) {
                    jSONLexer.nextToken(16);
                    Object obj2 = strStringVal;
                    if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                        JSONScanner jSONScanner = new JSONScanner(strStringVal);
                        Object time = strStringVal;
                        if (jSONScanner.scanISO8601DateIfMatch()) {
                            time = jSONScanner.getCalendar().getTime();
                        }
                        jSONScanner.close();
                        obj2 = time;
                    }
                    objValueOf = obj2;
                } else {
                    objValueOf = date2;
                }
            } else if (jSONLexer.token() == 8) {
                jSONLexer.nextToken();
                objValueOf = date2;
            } else if (jSONLexer.token() == 12) {
                jSONLexer.nextToken();
                if (jSONLexer.token() != 4) {
                    throw new JSONException("syntax error");
                }
                if (JSON.DEFAULT_TYPE_KEY.equals(jSONLexer.stringVal())) {
                    jSONLexer.nextToken();
                    defaultJSONParser.accept(17);
                    Class<?> clsCheckAutoType = defaultJSONParser.getConfig().checkAutoType(jSONLexer.stringVal(), null, jSONLexer.getFeatures());
                    if (clsCheckAutoType != null) {
                        type = clsCheckAutoType;
                    }
                    defaultJSONParser.accept(4);
                    defaultJSONParser.accept(16);
                }
                jSONLexer.nextTokenWithColon(2);
                if (jSONLexer.token() != 2) {
                    throw new JSONException("syntax error : " + jSONLexer.tokenName());
                }
                long jLongValue2 = jSONLexer.longValue();
                jSONLexer.nextToken();
                objValueOf = Long.valueOf(jLongValue2);
                defaultJSONParser.accept(13);
            } else if (defaultJSONParser.getResolveStatus() == 2) {
                defaultJSONParser.setResolveStatus(0);
                defaultJSONParser.accept(16);
                if (jSONLexer.token() != 4) {
                    throw new JSONException("syntax error");
                }
                if (!"val".equals(jSONLexer.stringVal())) {
                    throw new JSONException("syntax error");
                }
                jSONLexer.nextToken();
                defaultJSONParser.accept(17);
                objValueOf = defaultJSONParser.parse();
                defaultJSONParser.accept(13);
            } else {
                objValueOf = defaultJSONParser.parse();
            }
        }
        return (T) cast(defaultJSONParser, type, obj, objValueOf);
    }
}
