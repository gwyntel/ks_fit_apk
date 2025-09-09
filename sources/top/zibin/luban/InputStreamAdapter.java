package top.zibin.luban;

import java.io.IOException;
import java.io.InputStream;
import top.zibin.luban.io.ArrayPoolProvide;

/* loaded from: classes5.dex */
public abstract class InputStreamAdapter implements InputStreamProvider {
    @Override // top.zibin.luban.InputStreamProvider
    public void close() throws IOException {
        ArrayPoolProvide.getInstance().clearMemory();
    }

    @Override // top.zibin.luban.InputStreamProvider
    public InputStream open() throws IOException {
        return openInternal();
    }

    public abstract InputStream openInternal() throws IOException;
}
