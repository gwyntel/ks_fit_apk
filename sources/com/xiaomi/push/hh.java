package com.xiaomi.push;

import com.vivo.push.PushClientConstants;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes4.dex */
public final class hh {

    /* renamed from: a, reason: collision with root package name */
    private static int f23872a = 5000;

    /* renamed from: a, reason: collision with other field name */
    private static Vector<String> f535a = new Vector<>();

    /* renamed from: b, reason: collision with root package name */
    private static int f23873b = 330000;

    /* renamed from: c, reason: collision with root package name */
    private static int f23874c = 600000;

    /* renamed from: d, reason: collision with root package name */
    private static int f23875d = 330000;

    static {
        try {
            for (ClassLoader classLoader : m478a()) {
                Enumeration<URL> resources = classLoader.getResources("META-INF/smack-config.xml");
                while (resources.hasMoreElements()) {
                    InputStream inputStreamOpenStream = null;
                    try {
                        try {
                            inputStreamOpenStream = resources.nextElement().openStream();
                            XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
                            xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
                            xmlPullParserNewPullParser.setInput(inputStreamOpenStream, "UTF-8");
                            int eventType = xmlPullParserNewPullParser.getEventType();
                            do {
                                if (eventType == 2) {
                                    if (xmlPullParserNewPullParser.getName().equals(PushClientConstants.TAG_CLASS_NAME)) {
                                        a(xmlPullParserNewPullParser);
                                    } else if (xmlPullParserNewPullParser.getName().equals("packetReplyTimeout")) {
                                        f23872a = a(xmlPullParserNewPullParser, f23872a);
                                    } else if (xmlPullParserNewPullParser.getName().equals("keepAliveInterval")) {
                                        f23873b = a(xmlPullParserNewPullParser, f23873b);
                                    } else if (xmlPullParserNewPullParser.getName().equals("mechName")) {
                                        f535a.add(xmlPullParserNewPullParser.nextText());
                                    }
                                }
                                eventType = xmlPullParserNewPullParser.next();
                            } while (eventType != 1);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        try {
                            inputStreamOpenStream.close();
                        } catch (Exception unused) {
                        }
                    } catch (Throwable th) {
                        try {
                            inputStreamOpenStream.close();
                        } catch (Exception unused2) {
                        }
                        throw th;
                    }
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    private hh() {
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m477a() {
        return com.pushsdk.BuildConfig.VERSION_NAME;
    }

    public static int b() {
        return f23874c;
    }

    public static int a() {
        return f23873b;
    }

    private static void a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, ClassNotFoundException {
        String strNextText = xmlPullParser.nextText();
        try {
            Class.forName(strNextText);
        } catch (ClassNotFoundException unused) {
            System.err.println("Error! A startup class specified in smack-config.xml could not be loaded: " + strNextText);
        }
    }

    private static int a(XmlPullParser xmlPullParser, int i2) {
        try {
            return Integer.parseInt(xmlPullParser.nextText());
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return i2;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static ClassLoader[] m478a() {
        ClassLoader[] classLoaderArr = {hh.class.getClassLoader(), Thread.currentThread().getContextClassLoader()};
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 2; i2++) {
            ClassLoader classLoader = classLoaderArr[i2];
            if (classLoader != null) {
                arrayList.add(classLoader);
            }
        }
        return (ClassLoader[]) arrayList.toArray(new ClassLoader[arrayList.size()]);
    }
}
