package com.huawei.secure.android.common.util;

import android.util.Log;
import androidx.annotation.RequiresApi;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18539a = "FileUtil";

    @RequiresApi(api = 26)
    public static boolean a(File file) throws IOException {
        if (file == null) {
            Log.e(f18539a, "file is null when isInSecureDir");
            return false;
        }
        try {
            Path path = Paths.get(file.getCanonicalPath(), new String[0]);
            if (path != null) {
                return a(path, (UserPrincipal) null);
            }
            Log.e(f18539a, "path is null when isInSecureDir");
            return false;
        } catch (IOException e2) {
            Log.e(f18539a, "failed to get canonical path: ", e2);
            return false;
        }
    }

    @RequiresApi(api = 26)
    public static boolean b(Path path) throws IOException {
        try {
            BasicFileAttributes attributes = Files.readAttributes(path, (Class<BasicFileAttributes>) q.a(), LinkOption.NOFOLLOW_LINKS);
            if (attributes == null) {
                Log.e(f18539a, "attr is null when isRegularFile");
                return false;
            }
            if (attributes.isRegularFile()) {
                return true;
            }
            Log.e(f18539a, "file is not a regular file");
            return false;
        } catch (IOException unused) {
            Log.e(f18539a, "failed to read attributes of file: ");
            return false;
        }
    }

    @RequiresApi(api = 26)
    public static boolean a(Path path) throws IOException {
        return a(path, (UserPrincipal) null);
    }

    private static String b(String str) {
        int iLastIndexOf;
        int i2;
        return (str.isEmpty() || (iLastIndexOf = str.lastIndexOf(".")) == -1 || (i2 = iLastIndexOf + 1) == str.length()) ? "" : str.substring(i2).toLowerCase(Locale.ENGLISH);
    }

    @RequiresApi(api = 26)
    public static boolean a(Path path, UserPrincipal userPrincipal) throws IOException {
        return a(path, userPrincipal, 5);
    }

    @RequiresApi(api = 26)
    public static boolean a(Path path, UserPrincipal userPrincipal, int i2) throws IOException {
        if (!path.isAbsolute()) {
            path = path.toAbsolutePath();
        }
        if (i2 <= 0) {
            return false;
        }
        FileSystem fileSystem = null;
        try {
            try {
                fileSystem = Paths.get(path.getRoot().toString(), new String[0]).getFileSystem();
                UserPrincipalLookupService userPrincipalLookupService = fileSystem.getUserPrincipalLookupService();
                if (fileSystem != null) {
                    fileSystem.close();
                }
                try {
                    UserPrincipal userPrincipalLookupPrincipalByName = userPrincipalLookupService.lookupPrincipalByName("root");
                    if (userPrincipal == null) {
                        userPrincipal = userPrincipalLookupService.lookupPrincipalByName(System.getProperty("user.name"));
                    }
                    if (userPrincipalLookupPrincipalByName != null && userPrincipal != null) {
                        for (int i3 = 1; i3 <= path.getNameCount(); i3++) {
                            try {
                                Path path2 = Paths.get(path.getRoot().toString(), path.subpath(0, i3).toString());
                                try {
                                    if (!Files.isSymbolicLink(path2)) {
                                        UserPrincipal owner = Files.getOwner(path2, new LinkOption[0]);
                                        if (userPrincipal.equals(owner) || userPrincipalLookupPrincipalByName.equals(owner)) {
                                            Set setPermissions = k.a(Files.readAttributes(path2, b0.a(), new LinkOption[0])).permissions();
                                            if (!setPermissions.contains(PosixFilePermission.GROUP_WRITE) && !setPermissions.contains(PosixFilePermission.OTHERS_WRITE)) {
                                            }
                                            Log.w(f18539a, "permission of partial path is insecure by GROUP_WRITE or OTHERS_WRITE");
                                            return false;
                                        }
                                        Log.w(f18539a, "partial path , root: , user: , owner: ");
                                        return false;
                                    }
                                    Log.w(f18539a, "partial path " + path2.toString() + "is a symbolic link");
                                    if (!a(Files.readSymbolicLink(path2), userPrincipal, i2 - 1)) {
                                        return false;
                                    }
                                } catch (IOException e2) {
                                    Log.e(f18539a, "get IOException: ", e2);
                                    return false;
                                }
                            } catch (Exception e3) {
                                Log.e(f18539a, "get partial path error: ", e3);
                                return false;
                            }
                        }
                        return true;
                    }
                } catch (IOException unused) {
                }
                return false;
            } catch (Throwable th) {
                if (fileSystem != null) {
                    fileSystem.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            Log.e(f18539a, "get fileSystem error: ", e4);
            if (fileSystem != null) {
                fileSystem.close();
            }
            return false;
        }
    }

    public static boolean a(String str, String str2) throws IOException {
        if (str != null && !str.equals("") && str2 != null && !str2.equals("")) {
            String strDecode = URLDecoder.decode(str2, "utf-8");
            String strDecode2 = URLDecoder.decode(str, "utf-8");
            if (!strDecode2.contains("..") && !strDecode2.contains("./") && !strDecode2.contains(".\\.\\") && !strDecode2.contains("%00") && !strDecode.contains("..") && !strDecode.contains("./") && !strDecode.contains(".\\.\\") && !strDecode.contains("%00")) {
                return new File(strDecode2, strDecode).getCanonicalPath().startsWith(new File(strDecode2).getCanonicalPath());
            }
        }
        return false;
    }

    public static boolean a(String str) throws UnsupportedEncodingException {
        if (str == null || str.equals("")) {
            return true;
        }
        if (str.contains("%")) {
            str = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        }
        String strDecode = URLDecoder.decode(str, "utf-8");
        return (strDecode.contains("..") || strDecode.contains("./") || strDecode.contains(".\\.\\") || strDecode.contains("%00")) ? false : true;
    }

    public static boolean a(String str, String[] strArr) {
        if (str != null && !str.isEmpty() && strArr != null) {
            String strB = b(str);
            for (String str2 : strArr) {
                if (strB.equalsIgnoreCase(str2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
