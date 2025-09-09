package com.xiaomi.push;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes4.dex */
public class gv {

    /* renamed from: a, reason: collision with root package name */
    private XmlPullParser f23853a;

    gv() throws XmlPullParserException {
        try {
            XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
            this.f23853a = xmlPullParserNewPullParser;
            xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        } catch (XmlPullParserException unused) {
        }
    }

    hs a(byte[] bArr, hb hbVar) throws XmlPullParserException, IOException, hm {
        this.f23853a.setInput(new InputStreamReader(new ByteArrayInputStream(bArr)));
        this.f23853a.next();
        int eventType = this.f23853a.getEventType();
        String name = this.f23853a.getName();
        if (eventType != 2) {
            return null;
        }
        if (name.equals("message")) {
            return ia.a(this.f23853a);
        }
        if (name.equals("iq")) {
            return ia.a(this.f23853a, hbVar);
        }
        if (name.equals("presence")) {
            return ia.m502a(this.f23853a);
        }
        if (this.f23853a.getName().equals("stream")) {
            return null;
        }
        if (this.f23853a.getName().equals("error")) {
            throw new hm(ia.m503a(this.f23853a));
        }
        if (!this.f23853a.getName().equals("warning")) {
            this.f23853a.getName().equals("bind");
            return null;
        }
        this.f23853a.next();
        this.f23853a.getName().equals("multi-login");
        return null;
    }
}
