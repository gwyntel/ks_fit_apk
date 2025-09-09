package javax.jmdns.impl.constants;

import aisble.error.GattError;
import com.aliyun.alink.linksdk.alcs.coap.resources.LinkFormat;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.kingsmith.miot.KsProperty;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import java.util.logging.Logger;
import org.apache.commons.codec.language.bm.Languages;

/* loaded from: classes4.dex */
public enum DNSRecordType {
    TYPE_IGNORE("ignore", 0),
    TYPE_A("a", 1),
    TYPE_NS(NotificationStyle.NOTIFICATION_STYLE, 2),
    TYPE_MD("md", 3),
    TYPE_MF("mf", 4),
    TYPE_CNAME("cname", 5),
    TYPE_SOA("soa", 6),
    TYPE_MB("mb", 7),
    TYPE_MG("mg", 8),
    TYPE_MR("mr", 9),
    TYPE_NULL(TmpConstant.GROUP_ROLE_UNKNOWN, 10),
    TYPE_WKS("wks", 11),
    TYPE_PTR("ptr", 12),
    TYPE_HINFO("hinfo", 13),
    TYPE_MINFO("minfo", 14),
    TYPE_MX("mx", 15),
    TYPE_TXT("txt", 16),
    TYPE_RP("rp", 17),
    TYPE_AFSDB("afsdb", 18),
    TYPE_X25("x25", 19),
    TYPE_ISDN("isdn", 20),
    TYPE_RT(LinkFormat.RESOURCE_TYPE, 21),
    TYPE_NSAP("nsap", 22),
    TYPE_NSAP_PTR("nsap-otr", 23),
    TYPE_SIG("sig", 24),
    TYPE_KEY(KsProperty.Key, 25),
    TYPE_PX("px", 26),
    TYPE_GPOS("gpos", 27),
    TYPE_AAAA("aaaa", 28),
    TYPE_LOC("loc", 29),
    TYPE_NXT("nxt", 30),
    TYPE_EID("eid", 31),
    TYPE_NIMLOC("nimloc", 32),
    TYPE_SRV("srv", 33),
    TYPE_ATMA("atma", 34),
    TYPE_NAPTR("naptr", 35),
    TYPE_KX("kx", 36),
    TYPE_CERT("cert", 37),
    TYPE_A6("a6", 38),
    TYPE_DNAME("dname", 39),
    TYPE_SINK("sink", 40),
    TYPE_OPT("opt", 41),
    TYPE_APL("apl", 42),
    TYPE_DS("ds", 43),
    TYPE_SSHFP("sshfp", 44),
    TYPE_RRSIG("rrsig", 46),
    TYPE_NSEC("nsec", 47),
    TYPE_DNSKEY("dnskey", 48),
    TYPE_UINFO("uinfo", 100),
    TYPE_UID("uid", 101),
    TYPE_GID("gid", 102),
    TYPE_UNSPEC("unspec", 103),
    TYPE_TKEY("tkey", 249),
    TYPE_TSIG("tsig", 250),
    TYPE_IXFR("ixfr", 251),
    TYPE_AXFR("axfr", 252),
    TYPE_MAILA("mails", GattError.GATT_CCCD_CFG_ERROR),
    TYPE_MAILB("mailb", 254),
    TYPE_ANY(Languages.ANY, 255);

    private static Logger logger = Logger.getLogger(DNSRecordType.class.getName());
    private final String _externalName;
    private final int _index;

    DNSRecordType(String str, int i2) {
        this._externalName = str;
        this._index = i2;
    }

    public static DNSRecordType typeForIndex(int i2) {
        for (DNSRecordType dNSRecordType : values()) {
            if (dNSRecordType._index == i2) {
                return dNSRecordType;
            }
        }
        logger.severe("Could not find record type for index: " + i2);
        return TYPE_IGNORE;
    }

    public static DNSRecordType typeForName(String str) {
        if (str != null) {
            String lowerCase = str.toLowerCase();
            for (DNSRecordType dNSRecordType : values()) {
                if (dNSRecordType._externalName.equals(lowerCase)) {
                    return dNSRecordType;
                }
            }
        }
        logger.severe("Could not find record type for name: " + str);
        return TYPE_IGNORE;
    }

    public String externalName() {
        return this._externalName;
    }

    public int indexValue() {
        return this._index;
    }

    @Override // java.lang.Enum
    public String toString() {
        return name() + " index " + indexValue();
    }
}
