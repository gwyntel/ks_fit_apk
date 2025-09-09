package anet.channel.fulltrace;

import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static volatile IFullTraceAnalysis f6783a = new C0011a(null);

    /* renamed from: b, reason: collision with root package name */
    private static boolean f6784b = false;

    /* renamed from: anet.channel.fulltrace.a$a, reason: collision with other inner class name */
    private static class C0011a implements IFullTraceAnalysis {

        /* renamed from: a, reason: collision with root package name */
        private IFullTraceAnalysis f6785a;

        C0011a(IFullTraceAnalysis iFullTraceAnalysis) {
            this.f6785a = iFullTraceAnalysis;
            boolean unused = a.f6784b = true;
        }

        @Override // anet.channel.fulltrace.IFullTraceAnalysis
        public void commitRequest(String str, RequestStatistic requestStatistic) {
            IFullTraceAnalysis iFullTraceAnalysis;
            if (a.f6784b && (iFullTraceAnalysis = this.f6785a) != null) {
                try {
                    iFullTraceAnalysis.commitRequest(str, requestStatistic);
                } catch (Throwable th) {
                    boolean unused = a.f6784b = false;
                    ALog.e("anet.AnalysisFactory", "fulltrace commit fail.", null, th, new Object[0]);
                }
            }
        }

        @Override // anet.channel.fulltrace.IFullTraceAnalysis
        public String createRequest() {
            IFullTraceAnalysis iFullTraceAnalysis;
            if (!a.f6784b || (iFullTraceAnalysis = this.f6785a) == null) {
                return null;
            }
            try {
                return iFullTraceAnalysis.createRequest();
            } catch (Throwable th) {
                boolean unused = a.f6784b = false;
                ALog.e("anet.AnalysisFactory", "createRequest fail.", null, th, new Object[0]);
                return null;
            }
        }

        @Override // anet.channel.fulltrace.IFullTraceAnalysis
        public b getSceneInfo() {
            IFullTraceAnalysis iFullTraceAnalysis;
            if (!a.f6784b || (iFullTraceAnalysis = this.f6785a) == null) {
                return null;
            }
            try {
                return iFullTraceAnalysis.getSceneInfo();
            } catch (Throwable th) {
                boolean unused = a.f6784b = false;
                ALog.e("anet.AnalysisFactory", "getSceneInfo fail", null, th, new Object[0]);
                return null;
            }
        }
    }

    public static IFullTraceAnalysis a() {
        return f6783a;
    }

    public static void a(IFullTraceAnalysis iFullTraceAnalysis) {
        f6783a = new C0011a(iFullTraceAnalysis);
    }
}
