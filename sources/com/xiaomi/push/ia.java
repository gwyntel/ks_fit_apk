package com.xiaomi.push;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.tekartik.sqflite.Constant;
import com.xiaomi.push.hq;
import com.xiaomi.push.hu;
import com.xiaomi.push.hw;
import com.xiaomi.push.service.bf;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes4.dex */
public class ia {

    /* renamed from: a, reason: collision with root package name */
    private static XmlPullParser f23972a;

    public static hs a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, hm {
        String attributeValue;
        boolean z2 = false;
        String strNextText = null;
        if ("1".equals(xmlPullParser.getAttributeValue("", "s"))) {
            String attributeValue2 = xmlPullParser.getAttributeValue("", "chid");
            String attributeValue3 = xmlPullParser.getAttributeValue("", "id");
            String attributeValue4 = xmlPullParser.getAttributeValue("", "from");
            String attributeValue5 = xmlPullParser.getAttributeValue("", "to");
            String attributeValue6 = xmlPullParser.getAttributeValue("", "type");
            bf.b bVarA = com.xiaomi.push.service.bf.a().a(attributeValue2, attributeValue5);
            if (bVarA == null) {
                bVarA = com.xiaomi.push.service.bf.a().a(attributeValue2, attributeValue4);
            }
            if (bVarA == null) {
                throw new hm("the channel id is wrong while receiving a encrypted message");
            }
            hs hsVarA = null;
            while (!z2) {
                int next = xmlPullParser.next();
                if (next == 2) {
                    if (!"s".equals(xmlPullParser.getName())) {
                        throw new hm("error while receiving a encrypted message with wrong format");
                    }
                    if (xmlPullParser.next() != 4) {
                        throw new hm("error while receiving a encrypted message with wrong format");
                    }
                    String text = xmlPullParser.getText();
                    if (AlcsPalConst.MODEL_TYPE_TGMESH.equals(attributeValue2) || "6".equals(attributeValue2)) {
                        hr hrVar = new hr();
                        hrVar.l(attributeValue2);
                        hrVar.b(true);
                        hrVar.n(attributeValue4);
                        hrVar.m(attributeValue5);
                        hrVar.k(attributeValue3);
                        hrVar.f(attributeValue6);
                        hp hpVar = new hp("s", null, null, null);
                        hpVar.m483a(text);
                        hrVar.a(hpVar);
                        return hrVar;
                    }
                    a(com.xiaomi.push.service.bo.a(com.xiaomi.push.service.bo.a(bVarA.f24504h, attributeValue3), text));
                    f23972a.next();
                    hsVarA = a(f23972a);
                } else if (next == 3 && xmlPullParser.getName().equals("message")) {
                    z2 = true;
                }
            }
            if (hsVarA != null) {
                return hsVarA;
            }
            throw new hm("error while receiving a encrypted message with wrong format");
        }
        hr hrVar2 = new hr();
        String attributeValue7 = xmlPullParser.getAttributeValue("", "id");
        if (attributeValue7 == null) {
            attributeValue7 = "ID_NOT_AVAILABLE";
        }
        hrVar2.k(attributeValue7);
        hrVar2.m(xmlPullParser.getAttributeValue("", "to"));
        hrVar2.n(xmlPullParser.getAttributeValue("", "from"));
        hrVar2.l(xmlPullParser.getAttributeValue("", "chid"));
        hrVar2.a(xmlPullParser.getAttributeValue("", "appid"));
        try {
            attributeValue = xmlPullParser.getAttributeValue("", FacebookRequestErrorClassification.KEY_TRANSIENT);
        } catch (Exception unused) {
            attributeValue = null;
        }
        try {
            String attributeValue8 = xmlPullParser.getAttributeValue("", "seq");
            if (!TextUtils.isEmpty(attributeValue8)) {
                hrVar2.b(attributeValue8);
            }
        } catch (Exception unused2) {
        }
        try {
            String attributeValue9 = xmlPullParser.getAttributeValue("", "mseq");
            if (!TextUtils.isEmpty(attributeValue9)) {
                hrVar2.c(attributeValue9);
            }
        } catch (Exception unused3) {
        }
        try {
            String attributeValue10 = xmlPullParser.getAttributeValue("", "fseq");
            if (!TextUtils.isEmpty(attributeValue10)) {
                hrVar2.d(attributeValue10);
            }
        } catch (Exception unused4) {
        }
        try {
            String attributeValue11 = xmlPullParser.getAttributeValue("", "status");
            if (!TextUtils.isEmpty(attributeValue11)) {
                hrVar2.e(attributeValue11);
            }
        } catch (Exception unused5) {
        }
        hrVar2.a(!TextUtils.isEmpty(attributeValue) && attributeValue.equalsIgnoreCase("true"));
        hrVar2.f(xmlPullParser.getAttributeValue("", "type"));
        String strB = b(xmlPullParser);
        if (strB == null || "".equals(strB.trim())) {
            hs.q();
        } else {
            hrVar2.j(strB);
        }
        while (!z2) {
            int next2 = xmlPullParser.next();
            if (next2 == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (TextUtils.isEmpty(namespace)) {
                    namespace = "xm";
                }
                if (name.equals("subject")) {
                    b(xmlPullParser);
                    hrVar2.g(m505a(xmlPullParser));
                } else if (name.equals("body")) {
                    String attributeValue12 = xmlPullParser.getAttributeValue("", "encode");
                    String strM505a = m505a(xmlPullParser);
                    if (TextUtils.isEmpty(attributeValue12)) {
                        hrVar2.h(strM505a);
                    } else {
                        hrVar2.a(strM505a, attributeValue12);
                    }
                } else if (name.equals("thread")) {
                    if (strNextText == null) {
                        strNextText = xmlPullParser.nextText();
                    }
                } else if (name.equals("error")) {
                    hrVar2.a(m504a(xmlPullParser));
                } else {
                    hrVar2.a(a(name, namespace, xmlPullParser));
                }
            } else if (next2 == 3 && xmlPullParser.getName().equals("message")) {
                z2 = true;
            }
        }
        hrVar2.i(strNextText);
        return hrVar2;
    }

    private static String b(XmlPullParser xmlPullParser) {
        for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); i2++) {
            String attributeName = xmlPullParser.getAttributeName(i2);
            if ("xml:lang".equals(attributeName) || ("lang".equals(attributeName) && "xml".equals(xmlPullParser.getAttributePrefix(i2)))) {
                return xmlPullParser.getAttributeValue(i2);
            }
        }
        return null;
    }

    private static void a(byte[] bArr) throws XmlPullParserException {
        if (f23972a == null) {
            try {
                XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
                f23972a = xmlPullParserNewPullParser;
                xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            } catch (XmlPullParserException e2) {
                e2.printStackTrace();
            }
        }
        f23972a.setInput(new InputStreamReader(new ByteArrayInputStream(bArr)));
    }

    /* renamed from: a, reason: collision with other method in class */
    private static String m505a(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        String str = "";
        while (true) {
            if (xmlPullParser.next() == 3 && xmlPullParser.getDepth() == depth) {
                return str;
            }
            str = str + xmlPullParser.getText();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static hu m502a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        hu.b bVarValueOf = hu.b.available;
        String attributeValue = xmlPullParser.getAttributeValue("", "type");
        if (attributeValue != null && !attributeValue.equals("")) {
            try {
                bVarValueOf = hu.b.valueOf(attributeValue);
            } catch (IllegalArgumentException unused) {
                System.err.println("Found invalid presence type " + attributeValue);
            }
        }
        hu huVar = new hu(bVarValueOf);
        huVar.m(xmlPullParser.getAttributeValue("", "to"));
        huVar.n(xmlPullParser.getAttributeValue("", "from"));
        huVar.l(xmlPullParser.getAttributeValue("", "chid"));
        String attributeValue2 = xmlPullParser.getAttributeValue("", "id");
        if (attributeValue2 == null) {
            attributeValue2 = "ID_NOT_AVAILABLE";
        }
        huVar.k(attributeValue2);
        boolean z2 = false;
        while (!z2) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (name.equals("status")) {
                    huVar.a(xmlPullParser.nextText());
                } else if (name.equals(RemoteMessageConst.Notification.PRIORITY)) {
                    try {
                        huVar.a(Integer.parseInt(xmlPullParser.nextText()));
                    } catch (NumberFormatException unused2) {
                    } catch (IllegalArgumentException unused3) {
                        huVar.a(0);
                    }
                } else if (name.equals("show")) {
                    String strNextText = xmlPullParser.nextText();
                    try {
                        huVar.a(hu.a.valueOf(strNextText));
                    } catch (IllegalArgumentException unused4) {
                        System.err.println("Found invalid presence mode " + strNextText);
                    }
                } else if (name.equals("error")) {
                    huVar.a(m504a(xmlPullParser));
                } else {
                    huVar.a(a(name, namespace, xmlPullParser));
                }
            } else if (next == 3 && xmlPullParser.getName().equals("presence")) {
                z2 = true;
            }
        }
        return huVar;
    }

    public static hq a(XmlPullParser xmlPullParser, hb hbVar) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue("", "id");
        String attributeValue2 = xmlPullParser.getAttributeValue("", "to");
        String attributeValue3 = xmlPullParser.getAttributeValue("", "from");
        String attributeValue4 = xmlPullParser.getAttributeValue("", "chid");
        hq.a aVarA = hq.a.a(xmlPullParser.getAttributeValue("", "type"));
        HashMap map = new HashMap();
        boolean z2 = false;
        for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); i2++) {
            String attributeName = xmlPullParser.getAttributeName(i2);
            map.put(attributeName, xmlPullParser.getAttributeValue("", attributeName));
        }
        hq icVar = null;
        hw hwVarM504a = null;
        while (!z2) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (name.equals("error")) {
                    hwVarM504a = m504a(xmlPullParser);
                } else {
                    icVar = new hq();
                    icVar.a(a(name, namespace, xmlPullParser));
                }
            } else if (next == 3 && xmlPullParser.getName().equals("iq")) {
                z2 = true;
            }
        }
        if (icVar == null) {
            if (hq.a.f23894a != aVarA && hq.a.f23895b != aVarA) {
                icVar = new ic();
            } else {
                ib ibVar = new ib();
                ibVar.k(attributeValue);
                ibVar.m(attributeValue3);
                ibVar.n(attributeValue2);
                ibVar.a(hq.a.f23897d);
                ibVar.l(attributeValue4);
                ibVar.a(new hw(hw.a.f23944e));
                hbVar.a(ibVar);
                com.xiaomi.channel.commonutils.logger.b.d("iq usage error. send packet in packet parser.");
                return null;
            }
        }
        icVar.k(attributeValue);
        icVar.m(attributeValue2);
        icVar.l(attributeValue4);
        icVar.n(attributeValue3);
        icVar.a(aVarA);
        icVar.a(hwVarM504a);
        icVar.a(map);
        return icVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static hv m503a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        hv hvVar = null;
        boolean z2 = false;
        while (!z2) {
            int next = xmlPullParser.next();
            if (next == 2) {
                hvVar = new hv(xmlPullParser.getName());
            } else if (next == 3 && xmlPullParser.getName().equals("error")) {
                z2 = true;
            }
        }
        return hvVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static hw m504a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        String attributeValue = "-1";
        boolean z2 = false;
        String attributeValue2 = null;
        String attributeValue3 = null;
        for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); i2++) {
            if (xmlPullParser.getAttributeName(i2).equals("code")) {
                attributeValue = xmlPullParser.getAttributeValue("", "code");
            }
            if (xmlPullParser.getAttributeName(i2).equals("type")) {
                attributeValue3 = xmlPullParser.getAttributeValue("", "type");
            }
            if (xmlPullParser.getAttributeName(i2).equals("reason")) {
                attributeValue2 = xmlPullParser.getAttributeValue("", "reason");
            }
        }
        String str = null;
        String strNextText = null;
        while (!z2) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (xmlPullParser.getName().equals("text")) {
                    strNextText = xmlPullParser.nextText();
                } else {
                    String name = xmlPullParser.getName();
                    String namespace = xmlPullParser.getNamespace();
                    if ("urn:ietf:params:xml:ns:xmpp-stanzas".equals(namespace)) {
                        str = name;
                    } else {
                        arrayList.add(a(name, namespace, xmlPullParser));
                    }
                }
            } else if (next == 3) {
                if (xmlPullParser.getName().equals("error")) {
                    z2 = true;
                }
            } else if (next == 4) {
                strNextText = xmlPullParser.getText();
            }
        }
        return new hw(Integer.parseInt(attributeValue), attributeValue3 == null ? Constant.PARAM_CANCEL : attributeValue3, attributeValue2, str, strNextText, arrayList);
    }

    public static hp a(String str, String str2, XmlPullParser xmlPullParser) {
        Object objM492a = hz.a().m492a("all", "xm:chat");
        if (objM492a == null || !(objM492a instanceof com.xiaomi.push.service.l)) {
            return null;
        }
        return ((com.xiaomi.push.service.l) objM492a).b(xmlPullParser);
    }
}
