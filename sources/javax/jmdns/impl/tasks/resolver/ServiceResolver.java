package javax.jmdns.impl.tasks.resolver;

import java.io.IOException;
import javax.jmdns.ServiceInfo;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSQuestion;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

/* loaded from: classes4.dex */
public class ServiceResolver extends DNSResolverTask {
    private final String _type;

    public ServiceResolver(JmDNSImpl jmDNSImpl, String str) {
        super(jmDNSImpl);
        this._type = str;
    }

    @Override // javax.jmdns.impl.tasks.resolver.DNSResolverTask
    protected DNSOutgoing a(DNSOutgoing dNSOutgoing) throws IOException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (ServiceInfo serviceInfo : getDns().getServices().values()) {
            dNSOutgoing = addAnswer(dNSOutgoing, new DNSRecord.Pointer(serviceInfo.getType(), DNSRecordClass.CLASS_IN, false, 3600, serviceInfo.getQualifiedName()), jCurrentTimeMillis);
        }
        return dNSOutgoing;
    }

    @Override // javax.jmdns.impl.tasks.resolver.DNSResolverTask
    protected DNSOutgoing b(DNSOutgoing dNSOutgoing) {
        return addQuestion(dNSOutgoing, DNSQuestion.newQuestion(this._type, DNSRecordType.TYPE_PTR, DNSRecordClass.CLASS_IN, false));
    }

    @Override // javax.jmdns.impl.tasks.resolver.DNSResolverTask
    protected String c() {
        return "querying service";
    }

    @Override // javax.jmdns.impl.tasks.DNSTask
    public String getName() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServiceResolver(");
        sb.append(getDns() != null ? getDns().getName() : "");
        sb.append(")");
        return sb.toString();
    }
}
