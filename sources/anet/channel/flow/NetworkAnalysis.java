package anet.channel.flow;

/* loaded from: classes2.dex */
public class NetworkAnalysis {
    private static volatile INetworkAnalysis networkAnalysis = new AnalysisProxy(null);

    private static class AnalysisProxy implements INetworkAnalysis {
        private INetworkAnalysis networkAnalysis;

        AnalysisProxy(INetworkAnalysis iNetworkAnalysis) {
            this.networkAnalysis = iNetworkAnalysis;
        }

        @Override // anet.channel.flow.INetworkAnalysis
        public void commitFlow(FlowStat flowStat) {
            INetworkAnalysis iNetworkAnalysis = this.networkAnalysis;
            if (iNetworkAnalysis != null) {
                iNetworkAnalysis.commitFlow(flowStat);
            }
        }
    }

    public static INetworkAnalysis getInstance() {
        return networkAnalysis;
    }

    public static void setInstance(INetworkAnalysis iNetworkAnalysis) {
        networkAnalysis = new AnalysisProxy(iNetworkAnalysis);
    }
}
