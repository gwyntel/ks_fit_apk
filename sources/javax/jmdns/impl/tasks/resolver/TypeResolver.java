package javax.jmdns.impl.tasks.resolver;

import java.io.IOException;
import java.util.Iterator;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSQuestion;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

/* loaded from: classes4.dex */
public class TypeResolver extends DNSResolverTask {
    public TypeResolver(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl);
    }

    @Override // javax.jmdns.impl.tasks.resolver.DNSResolverTask
    protected DNSOutgoing a(DNSOutgoing dNSOutgoing) throws IOException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Iterator<String> it = getDns().getServiceTypes().keySet().iterator();
        while (it.hasNext()) {
            dNSOutgoing = addAnswer(dNSOutgoing, new DNSRecord.Pointer("_services._dns-sd._udp.local.", DNSRecordClass.CLASS_IN, false, 3600, getDns().getServiceTypes().get(it.next()).getType()), jCurrentTimeMillis);
        }
        return dNSOutgoing;
    }

    @Override // javax.jmdns.impl.tasks.resolver.DNSResolverTask
    protected DNSOutgoing b(DNSOutgoing dNSOutgoing) {
        return addQuestion(dNSOutgoing, DNSQuestion.newQuestion("_services._dns-sd._udp.local.", DNSRecordType.TYPE_PTR, DNSRecordClass.CLASS_IN, false));
    }

    @Override // javax.jmdns.impl.tasks.resolver.DNSResolverTask
    protected String c() {
        return "querying type";
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public String getName() {
        StringBuilder sb = new StringBuilder();
        sb.append("TypeResolver(");
        sb.append(getDns() != null ? getDns().getName() : "");
        sb.append(")");
        return sb.toString();
    }
}
