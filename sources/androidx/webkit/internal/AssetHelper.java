package androidx.webkit.internal;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/* loaded from: classes2.dex */
public class AssetHelper {
    public static final String DEFAULT_MIME_TYPE = "text/plain";

    @NonNull
    private Context mContext;

    public AssetHelper(@NonNull Context context) {
        this.mContext = context;
    }

    @NonNull
    public static String getCanonicalDirPath(@NonNull File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath.endsWith("/")) {
            return canonicalPath;
        }
        return canonicalPath + "/";
    }

    @Nullable
    public static File getCanonicalFileIfChild(@NonNull File file, @NonNull String str) throws IOException {
        String canonicalDirPath = getCanonicalDirPath(file);
        String canonicalPath = new File(file, str).getCanonicalPath();
        if (canonicalPath.startsWith(canonicalDirPath)) {
            return new File(canonicalPath);
        }
        return null;
    }

    @NonNull
    public static File getDataDir(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 24 ? ApiHelperForN.getDataDir(context) : context.getCacheDir().getParentFile();
    }

    private int getFieldId(@NonNull String str, @NonNull String str2) {
        return this.mContext.getResources().getIdentifier(str2, str, this.mContext.getPackageName());
    }

    private int getValueType(int i2) throws Resources.NotFoundException {
        TypedValue typedValue = new TypedValue();
        this.mContext.getResources().getValue(i2, typedValue, true);
        return typedValue.type;
    }

    @NonNull
    public static String guessMimeType(@NonNull String str) {
        String mimeFromFileName = MimeUtil.getMimeFromFileName(str);
        return mimeFromFileName == null ? DEFAULT_MIME_TYPE : mimeFromFileName;
    }

    @NonNull
    private static InputStream handleSvgzStream(@NonNull String str, @NonNull InputStream inputStream) throws IOException {
        return str.endsWith(".svgz") ? new GZIPInputStream(inputStream) : inputStream;
    }

    @NonNull
    public static InputStream openFile(@NonNull File file) throws IOException {
        return handleSvgzStream(file.getPath(), new FileInputStream(file));
    }

    @NonNull
    private static String removeLeadingSlash(@NonNull String str) {
        return (str.length() <= 1 || str.charAt(0) != '/') ? str : str.substring(1);
    }

    @NonNull
    public InputStream openAsset(@NonNull String str) throws IOException {
        String strRemoveLeadingSlash = removeLeadingSlash(str);
        return handleSvgzStream(strRemoveLeadingSlash, this.mContext.getAssets().open(strRemoveLeadingSlash, 2));
    }

    @NonNull
    public InputStream openResource(@NonNull String str) throws Resources.NotFoundException, IOException {
        String strRemoveLeadingSlash = removeLeadingSlash(str);
        String[] strArrSplit = strRemoveLeadingSlash.split("/", -1);
        if (strArrSplit.length != 2) {
            throw new IllegalArgumentException("Incorrect resource path: " + strRemoveLeadingSlash);
        }
        String str2 = strArrSplit[0];
        String strSubstring = strArrSplit[1];
        int iLastIndexOf = strSubstring.lastIndexOf(46);
        if (iLastIndexOf != -1) {
            strSubstring = strSubstring.substring(0, iLastIndexOf);
        }
        int fieldId = getFieldId(str2, strSubstring);
        int valueType = getValueType(fieldId);
        if (valueType == 3) {
            return handleSvgzStream(strRemoveLeadingSlash, this.mContext.getResources().openRawResource(fieldId));
        }
        throw new IOException(String.format("Expected %s resource to be of TYPE_STRING but was %d", strRemoveLeadingSlash, Integer.valueOf(valueType)));
    }
}
