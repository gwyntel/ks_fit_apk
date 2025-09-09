package retrofit;

/* loaded from: classes5.dex */
public interface Callback<T> {
    void onFailure(Throwable th);

    void onResponse(Response<T> response, Retrofit retrofit3);
}
