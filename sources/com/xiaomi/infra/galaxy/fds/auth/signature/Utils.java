package com.xiaomi.infra.galaxy.fds.auth.signature;

import com.google.common.collect.LinkedListMultimap;
import com.huawei.hms.framework.common.ContainerUtils;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class Utils {
    private static final ThreadLocal<SimpleDateFormat> RFC_822_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.infra.galaxy.fds.auth.signature.Utils.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat;
        }
    };
    private static final ThreadLocal<SimpleDateFormat> RFC_850_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.infra.galaxy.fds.auth.signature.Utils.2
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yy HH:mm:ss z", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat;
        }
    };
    private static final ThreadLocal<SimpleDateFormat> ANSI_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.infra.galaxy.fds.auth.signature.Utils.3
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat;
        }
    };

    public static String getGMTDatetime(Date date) {
        return RFC_822_DATE_FORMAT.get().format(date);
    }

    public static Date parseDateTimeFromString(String str) {
        Date dateTryToParse = tryToParse(str, RFC_822_DATE_FORMAT.get());
        if (dateTryToParse == null) {
            dateTryToParse = tryToParse(str, RFC_850_DATE_FORMAT.get());
        }
        return dateTryToParse == null ? tryToParse(str, ANSI_DATE_FORMAT.get()) : dateTryToParse;
    }

    public static long parseDateTimeToMilliseconds(String str) {
        Date dateTimeFromString = parseDateTimeFromString(str);
        if (dateTimeFromString != null) {
            return dateTimeFromString.getTime();
        }
        return 0L;
    }

    public static LinkedListMultimap<String, String> parseUriParameters(URI uri) {
        LinkedListMultimap<String, String> linkedListMultimapCreate = LinkedListMultimap.create();
        String query = uri.getQuery();
        if (query != null) {
            for (String str : query.split("&")) {
                String[] strArrSplit = str.split(ContainerUtils.KEY_VALUE_DELIMITER);
                if (strArrSplit.length >= 2) {
                    String str2 = strArrSplit[0];
                    linkedListMultimapCreate.put(str2, str.substring(str2.length() + 1));
                } else {
                    linkedListMultimapCreate.put(strArrSplit[0], "");
                }
            }
        }
        return linkedListMultimapCreate;
    }

    private static Date tryToParse(String str, SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException unused) {
            return null;
        }
    }
}
