package anetwork.channel.cache;

import anet.channel.util.HttpHelper;
import anetwork.channel.cache.Cache;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final TimeZone f7142a = TimeZone.getTimeZone("GMT");

    /* renamed from: b, reason: collision with root package name */
    private static final ThreadLocal<SimpleDateFormat> f7143b = new ThreadLocal<>();

    public static String a(long j2) {
        return a().format(new Date(j2));
    }

    private static long a(String str) {
        if (str.length() == 0) {
            return 0L;
        }
        try {
            ParsePosition parsePosition = new ParsePosition(0);
            Date date = a().parse(str, parsePosition);
            if (parsePosition.getIndex() == str.length()) {
                return date.getTime();
            }
        } catch (Exception unused) {
        }
        return 0L;
    }

    public static Cache.Entry a(Map<String, List<String>> map) throws NumberFormatException {
        long j2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        String singleHeaderFieldByKey = HttpHelper.getSingleHeaderFieldByKey(map, "Cache-Control");
        int i2 = 0;
        if (singleHeaderFieldByKey != null) {
            String[] strArrSplit = singleHeaderFieldByKey.split(",");
            j2 = 0;
            while (true) {
                if (i2 >= strArrSplit.length) {
                    break;
                }
                String strTrim = strArrSplit[i2].trim();
                if (strTrim.equals("no-store")) {
                    return null;
                }
                if (strTrim.equals("no-cache")) {
                    j2 = 0;
                    break;
                }
                if (strTrim.startsWith("max-age=")) {
                    try {
                        j2 = Long.parseLong(strTrim.substring(8));
                    } catch (Exception unused) {
                    }
                }
                i2++;
            }
            i2 = 1;
        } else {
            j2 = 0;
        }
        String singleHeaderFieldByKey2 = HttpHelper.getSingleHeaderFieldByKey(map, "Date");
        long jA = singleHeaderFieldByKey2 != null ? a(singleHeaderFieldByKey2) : 0L;
        String singleHeaderFieldByKey3 = HttpHelper.getSingleHeaderFieldByKey(map, "Expires");
        long jA2 = singleHeaderFieldByKey3 != null ? a(singleHeaderFieldByKey3) : 0L;
        String singleHeaderFieldByKey4 = HttpHelper.getSingleHeaderFieldByKey(map, "Last-Modified");
        long jA3 = singleHeaderFieldByKey4 != null ? a(singleHeaderFieldByKey4) : 0L;
        String singleHeaderFieldByKey5 = HttpHelper.getSingleHeaderFieldByKey(map, "ETag");
        if (i2 != 0) {
            jCurrentTimeMillis += j2 * 1000;
        } else if (jA > 0 && jA2 >= jA) {
            jCurrentTimeMillis += jA2 - jA;
        } else if (jA3 <= 0) {
            jCurrentTimeMillis = 0;
        }
        if (jCurrentTimeMillis == 0 && singleHeaderFieldByKey5 == null) {
            return null;
        }
        Cache.Entry entry = new Cache.Entry();
        entry.etag = singleHeaderFieldByKey5;
        entry.ttl = jCurrentTimeMillis;
        entry.serverDate = jA;
        entry.lastModified = jA3;
        entry.responseHeaders = map;
        return entry;
    }

    private static SimpleDateFormat a() {
        ThreadLocal<SimpleDateFormat> threadLocal = f7143b;
        SimpleDateFormat simpleDateFormat = threadLocal.get();
        if (simpleDateFormat != null) {
            return simpleDateFormat;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        simpleDateFormat2.setTimeZone(f7142a);
        threadLocal.set(simpleDateFormat2);
        return simpleDateFormat2;
    }
}
