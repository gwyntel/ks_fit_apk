package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Time;

/* loaded from: classes2.dex */
public class TimeDeserializer implements ObjectDeserializer {
    public static final TimeDeserializer instance = new TimeDeserializer();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) throws NumberFormatException {
        long timeInMillis;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 16) {
            jSONLexer.nextToken(4);
            if (jSONLexer.token() != 4) {
                throw new JSONException("syntax error");
            }
            jSONLexer.nextTokenWithColon(2);
            if (jSONLexer.token() != 2) {
                throw new JSONException("syntax error");
            }
            long jLongValue = jSONLexer.longValue();
            jSONLexer.nextToken(13);
            if (jSONLexer.token() != 13) {
                throw new JSONException("syntax error");
            }
            jSONLexer.nextToken(16);
            return (T) new Time(jLongValue);
        }
        T t2 = (T) defaultJSONParser.parse();
        if (t2 == 0) {
            return null;
        }
        if (t2 instanceof Time) {
            return t2;
        }
        if (t2 instanceof BigDecimal) {
            return (T) new Time(TypeUtils.longValue((BigDecimal) t2));
        }
        if (t2 instanceof Number) {
            return (T) new Time(((Number) t2).longValue());
        }
        if (!(t2 instanceof String)) {
            throw new JSONException("parse error");
        }
        String str = (String) t2;
        if (str.length() == 0) {
            return null;
        }
        JSONScanner jSONScanner = new JSONScanner(str);
        if (jSONScanner.scanISO8601DateIfMatch()) {
            timeInMillis = jSONScanner.getCalendar().getTimeInMillis();
        } else {
            for (int i2 = 0; i2 < str.length(); i2++) {
                char cCharAt = str.charAt(i2);
                if (cCharAt < '0' || cCharAt > '9') {
                    jSONScanner.close();
                    return (T) Time.valueOf(str);
                }
            }
            timeInMillis = Long.parseLong(str);
        }
        jSONScanner.close();
        return (T) new Time(timeInMillis);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}
