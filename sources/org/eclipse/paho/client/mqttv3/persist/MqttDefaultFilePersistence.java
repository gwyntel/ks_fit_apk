package org.eclipse.paho.client.mqttv3.persist;

import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.internal.FileLock;
import org.eclipse.paho.client.mqttv3.internal.MqttPersistentData;

/* loaded from: classes5.dex */
public class MqttDefaultFilePersistence implements MqttClientPersistence {
    private static FilenameFilter FILENAME_FILTER = null;
    private static final String LOCK_FILENAME = ".lck";
    private static final String MESSAGE_BACKUP_FILE_EXTENSION = ".bup";
    private static final String MESSAGE_FILE_EXTENSION = ".msg";
    private File clientDir;
    private File dataDir;
    private FileLock fileLock;

    public MqttDefaultFilePersistence() {
        this(System.getProperty("user.dir"));
    }

    private void checkIsOpen() throws MqttPersistenceException {
        if (this.clientDir == null) {
            throw new MqttPersistenceException();
        }
    }

    private static FilenameFilter getFilenameFilter() {
        if (FILENAME_FILTER == null) {
            FILENAME_FILTER = new PersistanceFileNameFilter(MESSAGE_FILE_EXTENSION);
        }
        return FILENAME_FILTER;
    }

    private File[] getFiles() throws MqttPersistenceException {
        checkIsOpen();
        File[] fileArrListFiles = this.clientDir.listFiles(getFilenameFilter());
        if (fileArrListFiles != null) {
            return fileArrListFiles;
        }
        throw new MqttPersistenceException();
    }

    private boolean isSafeChar(char c2) {
        return Character.isJavaIdentifierPart(c2) || c2 == '-';
    }

    private void restoreBackups(File file) throws MqttPersistenceException {
        File[] fileArrListFiles = file.listFiles(new PersistanceFileFilter(MESSAGE_BACKUP_FILE_EXTENSION));
        if (fileArrListFiles == null) {
            throw new MqttPersistenceException();
        }
        for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
            File file2 = new File(file, fileArrListFiles[i2].getName().substring(0, fileArrListFiles[i2].getName().length() - 4));
            if (!fileArrListFiles[i2].renameTo(file2)) {
                file2.delete();
                fileArrListFiles[i2].renameTo(file2);
            }
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void clear() throws MqttPersistenceException {
        checkIsOpen();
        for (File file : getFiles()) {
            file.delete();
        }
        this.clientDir.delete();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void close() throws MqttPersistenceException {
        synchronized (this) {
            try {
                FileLock fileLock = this.fileLock;
                if (fileLock != null) {
                    fileLock.release();
                }
                if (getFiles().length == 0) {
                    this.clientDir.delete();
                }
                this.clientDir = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public boolean containsKey(String str) throws MqttPersistenceException {
        checkIsOpen();
        return new File(this.clientDir, str + MESSAGE_FILE_EXTENSION).exists();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public MqttPersistable get(String str) throws IOException, MqttPersistenceException {
        checkIsOpen();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(this.clientDir, str + MESSAGE_FILE_EXTENSION));
            int iAvailable = fileInputStream.available();
            byte[] bArr = new byte[iAvailable];
            for (int i2 = 0; i2 < iAvailable; i2 += fileInputStream.read(bArr, i2, iAvailable - i2)) {
            }
            fileInputStream.close();
            return new MqttPersistentData(str, bArr, 0, iAvailable, null, 0, 0);
        } catch (IOException e2) {
            throw new MqttPersistenceException(e2);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public Enumeration keys() throws MqttPersistenceException {
        checkIsOpen();
        File[] files = getFiles();
        Vector vector = new Vector(files.length);
        for (File file : files) {
            vector.addElement(file.getName().substring(0, r4.length() - 4));
        }
        return vector.elements();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void open(String str, String str2) throws MqttPersistenceException {
        if (this.dataDir.exists() && !this.dataDir.isDirectory()) {
            throw new MqttPersistenceException();
        }
        if (!this.dataDir.exists() && !this.dataDir.mkdirs()) {
            throw new MqttPersistenceException();
        }
        if (!this.dataDir.canWrite()) {
            throw new MqttPersistenceException();
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (isSafeChar(cCharAt)) {
                stringBuffer.append(cCharAt);
            }
        }
        stringBuffer.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        for (int i3 = 0; i3 < str2.length(); i3++) {
            char cCharAt2 = str2.charAt(i3);
            if (isSafeChar(cCharAt2)) {
                stringBuffer.append(cCharAt2);
            }
        }
        synchronized (this) {
            if (this.clientDir == null) {
                File file = new File(this.dataDir, stringBuffer.toString());
                this.clientDir = file;
                if (!file.exists()) {
                    this.clientDir.mkdir();
                }
            }
            try {
                this.fileLock = new FileLock(this.clientDir, LOCK_FILENAME);
            } catch (Exception unused) {
            }
            restoreBackups(this.clientDir);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void put(String str, MqttPersistable mqttPersistable) throws MqttPersistenceException {
        checkIsOpen();
        File file = new File(this.clientDir, str + MESSAGE_FILE_EXTENSION);
        File file2 = new File(this.clientDir, str + MESSAGE_FILE_EXTENSION + MESSAGE_BACKUP_FILE_EXTENSION);
        if (file.exists() && !file.renameTo(file2)) {
            file2.delete();
            file.renameTo(file2);
        }
        try {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(mqttPersistable.getHeaderBytes(), mqttPersistable.getHeaderOffset(), mqttPersistable.getHeaderLength());
                if (mqttPersistable.getPayloadBytes() != null) {
                    fileOutputStream.write(mqttPersistable.getPayloadBytes(), mqttPersistable.getPayloadOffset(), mqttPersistable.getPayloadLength());
                }
                fileOutputStream.getFD().sync();
                fileOutputStream.close();
                if (file2.exists()) {
                    file2.delete();
                }
                if (!file2.exists() || file2.renameTo(file)) {
                    return;
                }
                file.delete();
                file2.renameTo(file);
            } catch (IOException e2) {
                throw new MqttPersistenceException(e2);
            }
        } catch (Throwable th) {
            if (file2.exists() && !file2.renameTo(file)) {
                file.delete();
                file2.renameTo(file);
            }
            throw th;
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void remove(String str) throws MqttPersistenceException {
        checkIsOpen();
        File file = new File(this.clientDir, str + MESSAGE_FILE_EXTENSION);
        if (file.exists()) {
            file.delete();
        }
    }

    public MqttDefaultFilePersistence(String str) {
        this.clientDir = null;
        this.fileLock = null;
        this.dataDir = new File(str);
    }
}
