package com.taobao.accs.net;

import anet.channel.strategy.StrategyCenter;

/* loaded from: classes4.dex */
class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ i f20228a;

    j(i iVar) {
        this.f20228a = iVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        StrategyCenter.getInstance().saveData();
    }
}
