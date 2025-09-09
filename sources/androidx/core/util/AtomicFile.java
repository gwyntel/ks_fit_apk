package androidx.core.util;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SyncFailedException;

/* loaded from: classes.dex */
public class AtomicFile {
    private static final String LOG_TAG = "AtomicFile";
    private final File mBaseName;
    private final File mLegacyBackupName;
    private final File mNewName;

    public AtomicFile(@NonNull File file) {
        this.mBaseName = file;
        this.mNewName = new File(file.getPath() + ".new");
        this.mLegacyBackupName = new File(file.getPath() + ".bak");
    }

    private static void rename(@NonNull File file, @NonNull File file2) {
        if (file2.isDirectory() && !file2.delete()) {
            Log.e(LOG_TAG, "Failed to delete file which is a directory " + file2);
        }
        if (file.renameTo(file2)) {
            return;
        }
        Log.e(LOG_TAG, "Failed to rename " + file + " to " + file2);
    }

    private static boolean sync(@NonNull FileOutputStream fileOutputStream) throws SyncFailedException {
        try {
            fileOutputStream.getFD().sync();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public void delete() {
        this.mBaseName.delete();
        this.mNewName.delete();
        this.mLegacyBackupName.delete();
    }

    public void failWrite(@Nullable FileOutputStream fileOutputStream) throws IOException {
        if (fileOutputStream == null) {
            return;
        }
        if (!sync(fileOutputStream)) {
            Log.e(LOG_TAG, "Failed to sync file output stream");
        }
        try {
            fileOutputStream.close();
        } catch (IOException e2) {
            Log.e(LOG_TAG, "Failed to close file output stream", e2);
        }
        if (this.mNewName.delete()) {
            return;
        }
        Log.e(LOG_TAG, "Failed to delete new file " + this.mNewName);
    }

    public void finishWrite(@Nullable FileOutputStream fileOutputStream) throws IOException {
        if (fileOutputStream == null) {
            return;
        }
        if (!sync(fileOutputStream)) {
            Log.e(LOG_TAG, "Failed to sync file output stream");
        }
        try {
            fileOutputStream.close();
        } catch (IOException e2) {
            Log.e(LOG_TAG, "Failed to close file output stream", e2);
        }
        rename(this.mNewName, this.mBaseName);
    }

    @NonNull
    public File getBaseFile() {
        return this.mBaseName;
    }

    @NonNull
    public FileInputStream openRead() throws FileNotFoundException {
        if (this.mLegacyBackupName.exists()) {
            rename(this.mLegacyBackupName, this.mBaseName);
        }
        if (this.mNewName.exists() && this.mBaseName.exists() && !this.mNewName.delete()) {
            Log.e(LOG_TAG, "Failed to delete outdated new file " + this.mNewName);
        }
        return new FileInputStream(this.mBaseName);
    }

    @NonNull
    public byte[] readFully() throws IOException {
        FileInputStream fileInputStreamOpenRead = openRead();
        try {
            byte[] bArr = new byte[fileInputStreamOpenRead.available()];
            int i2 = 0;
            while (true) {
                int i3 = fileInputStreamOpenRead.read(bArr, i2, bArr.length - i2);
                if (i3 <= 0) {
                    return bArr;
                }
                i2 += i3;
                int iAvailable = fileInputStreamOpenRead.available();
                if (iAvailable > bArr.length - i2) {
                    byte[] bArr2 = new byte[iAvailable + i2];
                    System.arraycopy(bArr, 0, bArr2, 0, i2);
                    bArr = bArr2;
                }
            }
        } finally {
            fileInputStreamOpenRead.close();
        }
    }

    @NonNull
    public FileOutputStream startWrite() throws IOException {
        if (this.mLegacyBackupName.exists()) {
            rename(this.mLegacyBackupName, this.mBaseName);
        }
        try {
            return new FileOutputStream(this.mNewName);
        } catch (FileNotFoundException unused) {
            if (!this.mNewName.getParentFile().mkdirs()) {
                throw new IOException("Failed to create directory for " + this.mNewName);
            }
            try {
                return new FileOutputStream(this.mNewName);
            } catch (FileNotFoundException e2) {
                throw new IOException("Failed to create new file " + this.mNewName, e2);
            }
        }
    }
}
