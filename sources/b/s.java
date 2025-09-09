package b;

import android.content.Context;
import android.content.SharedPreferences;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes2.dex */
public class s extends r {

    /* renamed from: h, reason: collision with root package name */
    public final Context f7547h;

    public s(Context context) {
        this.f7547h = context;
        b();
    }

    public final void a() {
        Map<String, ?> all = this.f7547h.getSharedPreferences(Utils.APPLICATION_KEYS, 0).getAll();
        if (all.isEmpty()) {
            this.f7541b.add(SecureUtils.generateRandomApplicationKey().toUpperCase());
            this.f7541b.add(SecureUtils.generateRandomApplicationKey().toUpperCase());
            this.f7541b.add(SecureUtils.generateRandomApplicationKey().toUpperCase());
        } else {
            this.f7541b.clear();
            for (int i2 = 0; i2 < all.size(); i2++) {
                this.f7541b.add(i2, String.valueOf(all.get(String.valueOf(i2))));
            }
        }
        j();
    }

    public void b() {
        SharedPreferences sharedPreferences = this.f7547h.getSharedPreferences("PROVISIONING_DATA", 0);
        this.f7540a = sharedPreferences.getString("NETWORK_KEY", SecureUtils.generateRandomNetworkKey());
        this.f7544e = sharedPreferences.getInt("UNICAST_ADDRESS", 1);
        this.f7542c = sharedPreferences.getInt("KEY_INDEX", 0);
        this.f7543d = sharedPreferences.getInt("IV_INDEX", 0);
        this.f7545f = sharedPreferences.getInt("FLAGS", 0);
        this.f7546g = sharedPreferences.getInt("GLOBAL_TTL", 5);
        a();
        p();
    }

    public List<String> c() {
        return Collections.unmodifiableList(this.f7541b);
    }

    public void d(int i2) {
        this.f7542c = i2;
        n();
    }

    public void e(int i2) {
        this.f7544e = i2;
        q();
    }

    public int f() {
        return this.f7543d;
    }

    public int g() {
        return this.f7542c;
    }

    public String h() {
        return this.f7540a;
    }

    public int i() {
        return this.f7544e;
    }

    public void j() {
        SharedPreferences.Editor editorEdit = this.f7547h.getSharedPreferences(Utils.APPLICATION_KEYS, 0).edit();
        if (this.f7541b.isEmpty()) {
            editorEdit.clear();
        } else {
            for (int i2 = 0; i2 < this.f7541b.size(); i2++) {
                editorEdit.putString(String.valueOf(i2), this.f7541b.get(i2));
            }
        }
        editorEdit.apply();
    }

    public final void k() {
        SharedPreferences.Editor editorEdit = this.f7547h.getSharedPreferences("PROVISIONING_DATA", 0).edit();
        editorEdit.putInt("FLAGS", this.f7545f);
        editorEdit.apply();
    }

    public final void l() {
        SharedPreferences.Editor editorEdit = this.f7547h.getSharedPreferences("PROVISIONING_DATA", 0).edit();
        editorEdit.putInt("GLOBAL_TTL", this.f7546g);
        editorEdit.apply();
    }

    public final void m() {
        SharedPreferences.Editor editorEdit = this.f7547h.getSharedPreferences("PROVISIONING_DATA", 0).edit();
        editorEdit.putInt("IV_INDEX", this.f7543d);
        editorEdit.apply();
    }

    public final void n() {
        SharedPreferences.Editor editorEdit = this.f7547h.getSharedPreferences("PROVISIONING_DATA", 0).edit();
        editorEdit.putInt("KEY_INDEX", this.f7542c);
        editorEdit.apply();
    }

    public final void o() {
        SharedPreferences.Editor editorEdit = this.f7547h.getSharedPreferences("PROVISIONING_DATA", 0).edit();
        editorEdit.putString("NETWORK_KEY", this.f7540a);
        editorEdit.apply();
    }

    public void p() {
        o();
        q();
        n();
        m();
        k();
        l();
        j();
    }

    public final void q() {
        SharedPreferences.Editor editorEdit = this.f7547h.getSharedPreferences("PROVISIONING_DATA", 0).edit();
        editorEdit.putInt("UNICAST_ADDRESS", this.f7544e);
        editorEdit.apply();
    }

    public void c(int i2) {
        this.f7543d = i2;
        m();
    }

    public int d() {
        return this.f7545f;
    }

    public int e() {
        return this.f7546g;
    }

    public void b(int i2, String str) {
        if (!this.f7541b.contains(str)) {
            this.f7541b.set(i2, str);
            j();
            return;
        }
        throw new IllegalArgumentException("App key already exists");
    }

    public void a(String str) {
        this.f7540a = str;
        o();
    }

    public void a(int i2, String str) {
        if (!this.f7541b.contains(str)) {
            this.f7541b.add(i2, str);
            j();
            return;
        }
        throw new IllegalArgumentException("App key already exists");
    }

    public void b(int i2) {
        this.f7546g = i2;
        l();
    }

    public void a(int i2) {
        this.f7545f = i2;
        k();
    }
}
