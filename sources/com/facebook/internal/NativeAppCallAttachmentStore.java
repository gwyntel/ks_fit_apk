package com.facebook.internal;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import com.facebook.FacebookContentProvider;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.huawei.hms.api.FailedBinderCallBack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class NativeAppCallAttachmentStore {
    static final String ATTACHMENTS_DIR_NAME = "com.facebook.NativeAppCallAttachmentStore.files";
    private static final String TAG = "com.facebook.internal.NativeAppCallAttachmentStore";
    private static File attachmentsDirectory;

    public static final class Attachment {
        private final String attachmentName;
        private final String attachmentUrl;
        private Bitmap bitmap;
        private final UUID callId;
        private boolean isContentUri;
        private Uri originalUri;
        private boolean shouldCreateFile;

        public String getAttachmentUrl() {
            return this.attachmentUrl;
        }

        public Uri getOriginalUri() {
            return this.originalUri;
        }

        private Attachment(UUID uuid, Bitmap bitmap, Uri uri) {
            this.callId = uuid;
            this.bitmap = bitmap;
            this.originalUri = uri;
            if (uri != null) {
                String scheme = uri.getScheme();
                if ("content".equalsIgnoreCase(scheme)) {
                    this.isContentUri = true;
                    this.shouldCreateFile = (uri.getAuthority() == null || uri.getAuthority().startsWith("media")) ? false : true;
                } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                    this.shouldCreateFile = true;
                } else if (!Utility.isWebUri(uri)) {
                    throw new FacebookException("Unsupported scheme for media Uri : " + scheme);
                }
            } else {
                if (bitmap == null) {
                    throw new FacebookException("Cannot share media without a bitmap or Uri set");
                }
                this.shouldCreateFile = true;
            }
            String string = !this.shouldCreateFile ? null : UUID.randomUUID().toString();
            this.attachmentName = string;
            this.attachmentUrl = !this.shouldCreateFile ? this.originalUri.toString() : FacebookContentProvider.getAttachmentUrl(FacebookSdk.getApplicationId(), uuid, string);
        }
    }

    private NativeAppCallAttachmentStore() {
    }

    public static void addAttachments(Collection<Attachment> collection) {
        if (collection == null || collection.size() == 0) {
            return;
        }
        if (attachmentsDirectory == null) {
            cleanupAllAttachments();
        }
        ensureAttachmentsDirectoryExists();
        ArrayList arrayList = new ArrayList();
        try {
            for (Attachment attachment : collection) {
                if (attachment.shouldCreateFile) {
                    File attachmentFile = getAttachmentFile(attachment.callId, attachment.attachmentName, true);
                    arrayList.add(attachmentFile);
                    if (attachment.bitmap != null) {
                        processAttachmentBitmap(attachment.bitmap, attachmentFile);
                    } else if (attachment.originalUri != null) {
                        processAttachmentFile(attachment.originalUri, attachment.isContentUri, attachmentFile);
                    }
                }
            }
        } catch (IOException e2) {
            Log.e(TAG, "Got unexpected exception:" + e2);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    ((File) it.next()).delete();
                } catch (Exception unused) {
                }
            }
            throw new FacebookException(e2);
        }
    }

    public static void cleanupAllAttachments() {
        Utility.deleteDirectory(getAttachmentsDirectory());
    }

    public static void cleanupAttachmentsForCall(UUID uuid) {
        File attachmentsDirectoryForCall = getAttachmentsDirectoryForCall(uuid, false);
        if (attachmentsDirectoryForCall != null) {
            Utility.deleteDirectory(attachmentsDirectoryForCall);
        }
    }

    public static Attachment createAttachment(UUID uuid, Bitmap bitmap) {
        Validate.notNull(uuid, FailedBinderCallBack.CALLER_ID);
        Validate.notNull(bitmap, "attachmentBitmap");
        return new Attachment(uuid, bitmap, null);
    }

    static File ensureAttachmentsDirectoryExists() {
        File attachmentsDirectory2 = getAttachmentsDirectory();
        attachmentsDirectory2.mkdirs();
        return attachmentsDirectory2;
    }

    static File getAttachmentFile(UUID uuid, String str, boolean z2) throws IOException {
        File attachmentsDirectoryForCall = getAttachmentsDirectoryForCall(uuid, z2);
        if (attachmentsDirectoryForCall == null) {
            return null;
        }
        try {
            return new File(attachmentsDirectoryForCall, URLEncoder.encode(str, "UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    static synchronized File getAttachmentsDirectory() {
        try {
            if (attachmentsDirectory == null) {
                attachmentsDirectory = new File(FacebookSdk.getApplicationContext().getCacheDir(), ATTACHMENTS_DIR_NAME);
            }
        } catch (Throwable th) {
            throw th;
        }
        return attachmentsDirectory;
    }

    static File getAttachmentsDirectoryForCall(UUID uuid, boolean z2) {
        if (attachmentsDirectory == null) {
            return null;
        }
        File file = new File(attachmentsDirectory, uuid.toString());
        if (z2 && !file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File openAttachment(UUID uuid, String str) throws FileNotFoundException {
        if (Utility.isNullOrEmpty(str) || uuid == null) {
            throw new FileNotFoundException();
        }
        try {
            return getAttachmentFile(uuid, str, false);
        } catch (IOException unused) {
            throw new FileNotFoundException();
        }
    }

    private static void processAttachmentBitmap(Bitmap bitmap, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } finally {
            Utility.closeQuietly(fileOutputStream);
        }
    }

    private static void processAttachmentFile(Uri uri, boolean z2, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            Utility.copyAndCloseInputStream(!z2 ? new FileInputStream(uri.getPath()) : FacebookSdk.getApplicationContext().getContentResolver().openInputStream(uri), fileOutputStream);
            Utility.closeQuietly(fileOutputStream);
        } catch (Throwable th) {
            Utility.closeQuietly(fileOutputStream);
            throw th;
        }
    }

    public static Attachment createAttachment(UUID uuid, Uri uri) {
        Validate.notNull(uuid, FailedBinderCallBack.CALLER_ID);
        Validate.notNull(uri, "attachmentUri");
        return new Attachment(uuid, null, uri);
    }
}
