package anet.channel.strategy;

/* loaded from: classes2.dex */
public class StrategyCenter {
    private static volatile IStrategyInstance instance;

    private StrategyCenter() {
    }

    public static IStrategyInstance getInstance() {
        if (instance == null) {
            synchronized (StrategyCenter.class) {
                try {
                    if (instance == null) {
                        instance = new g();
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    public static void setInstance(IStrategyInstance iStrategyInstance) {
        instance = iStrategyInstance;
    }
}
