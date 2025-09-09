package org.apache.commons.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;

/* loaded from: classes5.dex */
public abstract class DirectoryWalker<T> {
    private final int depthLimit;
    private final FileFilter filter;

    public static class CancelException extends IOException {
        private static final long serialVersionUID = 1347339620135041008L;
        private final int depth;
        private final File file;

        public CancelException(File file, int i2) {
            this("Operation Cancelled", file, i2);
        }

        public int getDepth() {
            return this.depth;
        }

        public File getFile() {
            return this.file;
        }

        public CancelException(String str, File file, int i2) {
            super(str);
            this.file = file;
            this.depth = i2;
        }
    }

    private void walk(File file, int i2, Collection<T> collection) throws IOException {
        a(file, i2, collection);
        if (c(file, i2, collection)) {
            e(file, i2, collection);
            int i3 = i2 + 1;
            int i4 = this.depthLimit;
            if (i4 < 0 || i3 <= i4) {
                a(file, i2, collection);
                FileFilter fileFilter = this.filter;
                File[] fileArrB = b(file, i2, fileFilter == null ? file.listFiles() : file.listFiles(fileFilter));
                if (fileArrB == null) {
                    h(file, i3, collection);
                } else {
                    for (File file2 : fileArrB) {
                        if (file2.isDirectory()) {
                            walk(file2, i3, collection);
                        } else {
                            a(file2, i3, collection);
                            f(file2, i3, collection);
                            a(file2, i3, collection);
                        }
                    }
                }
            }
            d(file, i2, collection);
        }
        a(file, i2, collection);
    }

    protected final void a(File file, int i2, Collection collection) throws CancelException {
        if (g(file, i2, collection)) {
            throw new CancelException(file, i2);
        }
    }

    protected File[] b(File file, int i2, File[] fileArr) {
        return fileArr;
    }

    protected boolean c(File file, int i2, Collection collection) {
        return true;
    }

    protected void d(File file, int i2, Collection collection) {
    }

    protected void e(File file, int i2, Collection collection) {
    }

    protected void f(File file, int i2, Collection collection) {
    }

    protected boolean g(File file, int i2, Collection collection) {
        return false;
    }

    protected void h(File file, int i2, Collection collection) {
    }
}
