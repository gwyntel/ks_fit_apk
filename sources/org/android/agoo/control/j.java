package org.android.agoo.control;

import org.android.agoo.message.MessageService;

/* loaded from: classes5.dex */
class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ BaseIntentService f26538a;

    j(BaseIntentService baseIntentService) {
        this.f26538a = baseIntentService;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.taobao.accs.client.a.f20084f.incrementAndGet();
        this.f26538a.notifyManager = new NotifManager();
        this.f26538a.notifyManager.init(this.f26538a.getApplicationContext());
        this.f26538a.messageService = new MessageService();
        this.f26538a.messageService.a(this.f26538a.getApplicationContext());
        this.f26538a.agooFactory = new AgooFactory();
        this.f26538a.agooFactory.init(this.f26538a.getApplicationContext(), this.f26538a.notifyManager, this.f26538a.messageService);
    }
}
