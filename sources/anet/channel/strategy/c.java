package anet.channel.strategy;

import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private final ConcurrentHashMap<String, String> f6986a = new ConcurrentHashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private boolean f6987b = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static c f6988a = new c();

        private a() {
        }
    }

    public void a(boolean z2) {
        this.f6987b = z2;
    }

    public void b(String str) {
        this.f6986a.put(str, "http");
    }

    public String a(String str) {
        if (!this.f6987b) {
            return null;
        }
        String str2 = this.f6986a.get(str);
        if (str2 != null) {
            return str2;
        }
        this.f6986a.put(str, "https");
        return "https";
    }
}
