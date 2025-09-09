package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class GeoResultParser extends ResultParser {
    private static final Pattern GEO_URL_PATTERN = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);

    @Override // com.google.zxing.client.result.ResultParser
    public GeoParsedResult parse(Result result) throws NumberFormatException {
        Matcher matcher = GEO_URL_PATTERN.matcher(ResultParser.a(result));
        if (!matcher.matches()) {
            return null;
        }
        String strGroup = matcher.group(4);
        try {
            double d2 = Double.parseDouble(matcher.group(1));
            if (d2 <= 90.0d && d2 >= -90.0d) {
                double d3 = Double.parseDouble(matcher.group(2));
                if (d3 <= 180.0d && d3 >= -180.0d) {
                    double d4 = 0.0d;
                    if (matcher.group(3) != null) {
                        double d5 = Double.parseDouble(matcher.group(3));
                        if (d5 < 0.0d) {
                            return null;
                        }
                        d4 = d5;
                    }
                    return new GeoParsedResult(d2, d3, d4, strGroup);
                }
            }
        } catch (NumberFormatException unused) {
        }
        return null;
    }
}
