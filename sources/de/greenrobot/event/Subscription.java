package de.greenrobot.event;

/* loaded from: classes4.dex */
final class Subscription {

    /* renamed from: a, reason: collision with root package name */
    final Object f24982a;

    /* renamed from: b, reason: collision with root package name */
    final SubscriberMethod f24983b;

    Subscription(Object obj, SubscriberMethod subscriberMethod) {
        this.f24982a = obj;
        this.f24983b = subscriberMethod;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Subscription) {
            Subscription subscription = (Subscription) obj;
            if (this.f24982a == subscription.f24982a && this.f24983b.equals(subscription.f24983b)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.f24982a.hashCode() + this.f24983b.f24981d.hashCode();
    }
}
