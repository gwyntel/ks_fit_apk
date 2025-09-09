package anet.channel.strategy;

/* loaded from: classes2.dex */
class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f7005a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ StrategyInfoHolder f7006b;

    e(StrategyInfoHolder strategyInfoHolder, String str) {
        this.f7006b = strategyInfoHolder;
        this.f7005a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f7006b.a(this.f7005a, true);
    }
}
