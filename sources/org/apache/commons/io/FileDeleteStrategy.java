package org.apache.commons.io;

import java.io.File;
import java.io.IOException;

/* loaded from: classes5.dex */
public class FileDeleteStrategy {
    private final String name;
    public static final FileDeleteStrategy NORMAL = new FileDeleteStrategy("Normal");
    public static final FileDeleteStrategy FORCE = new ForceFileDeleteStrategy();

    static class ForceFileDeleteStrategy extends FileDeleteStrategy {
        ForceFileDeleteStrategy() {
            super("Force");
        }

        @Override // org.apache.commons.io.FileDeleteStrategy
        protected boolean a(File file) throws IOException {
            FileUtils.forceDelete(file);
            return true;
        }
    }

    protected FileDeleteStrategy(String str) {
        this.name = str;
    }

    protected boolean a(File file) {
        return file.delete();
    }

    public void delete(File file) throws IOException {
        if (!file.exists() || a(file)) {
            return;
        }
        throw new IOException("Deletion failed: " + file);
    }

    public boolean deleteQuietly(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        try {
            return a(file);
        } catch (IOException unused) {
            return false;
        }
    }

    public String toString() {
        return "FileDeleteStrategy[" + this.name + "]";
    }
}
