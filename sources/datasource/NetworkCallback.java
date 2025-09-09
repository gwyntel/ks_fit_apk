package datasource;

/* loaded from: classes4.dex */
public interface NetworkCallback<T> {
    void onFailure(String str, String str2);

    void onSuccess(T t2);
}
