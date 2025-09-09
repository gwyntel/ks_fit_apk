package retrofit;

import java.io.IOException;

/* loaded from: classes5.dex */
public interface Call<T> extends Cloneable {
    void cancel();

    /* renamed from: clone */
    Call<T> mo2394clone();

    void enqueue(Callback<T> callback);

    Response<T> execute() throws IOException;
}
