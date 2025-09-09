package bolts;

/* loaded from: classes2.dex */
public class Capture<T> {
    private T value;

    public Capture() {
    }

    public T get() {
        return this.value;
    }

    public void set(T t2) {
        this.value = t2;
    }

    public Capture(T t2) {
        this.value = t2;
    }
}
