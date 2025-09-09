package com.ta.a.c;

import android.text.TextUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/* loaded from: classes4.dex */
public class a {
    public static void b(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                File file = new File(str);
                if (file.exists()) {
                    f.b("FileUtils", "path exists", str);
                } else {
                    f.b("FileUtils", "mkdirs path", str, "created", Boolean.valueOf(file.mkdirs()));
                }
            }
        } catch (Exception e2) {
            f.b("FileUtils", e2, new Object[0]);
        }
    }

    public static String c(String str) throws Throwable {
        InputStreamReader inputStreamReader = null;
        try {
            try {
                InputStreamReader inputStreamReader2 = new InputStreamReader(new FileInputStream(str));
                try {
                    char[] cArr = new char[100];
                    StringBuilder sb = new StringBuilder("");
                    while (true) {
                        int i2 = inputStreamReader2.read(cArr);
                        if (i2 == -1) {
                            break;
                        }
                        sb.append(cArr, 0, i2);
                    }
                    String string = sb.toString();
                    try {
                        inputStreamReader2.close();
                    } catch (Exception e2) {
                        f.b("FileUtils", e2, new Object[0]);
                    }
                    return string;
                } catch (Exception e3) {
                    e = e3;
                    inputStreamReader = inputStreamReader2;
                    f.b("FileUtils", e, new Object[0]);
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (Exception e4) {
                            f.b("FileUtils", e4, new Object[0]);
                        }
                    }
                    return "";
                } catch (Throwable th) {
                    th = th;
                    inputStreamReader = inputStreamReader2;
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (Exception e5) {
                            f.b("FileUtils", e5, new Object[0]);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e6) {
            e = e6;
        }
    }

    public static boolean b(String str, String str2) throws Throwable {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter = null;
        try {
            try {
                fileWriter = new FileWriter(new File(str));
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter);
                    try {
                        bufferedWriter2.write(str2);
                        bufferedWriter2.flush();
                        try {
                            bufferedWriter2.close();
                        } catch (Exception e2) {
                            f.b("FileUtils", e2, new Object[0]);
                        }
                        try {
                            fileWriter.close();
                            return true;
                        } catch (Exception e3) {
                            f.b("FileUtils", e3, new Object[0]);
                            return true;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        bufferedWriter = bufferedWriter2;
                        f.b("FileUtils", e, new Object[0]);
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (Exception e5) {
                                f.b("FileUtils", e5, new Object[0]);
                            }
                        }
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                            } catch (Exception e6) {
                                f.b("FileUtils", e6, new Object[0]);
                            }
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (Exception e7) {
                                f.b("FileUtils", e7, new Object[0]);
                            }
                        }
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                                throw th;
                            } catch (Exception e8) {
                                f.b("FileUtils", e8, new Object[0]);
                                throw th;
                            }
                        }
                        throw th;
                    }
                } catch (Exception e9) {
                    e = e9;
                }
            } catch (Exception e10) {
                e = e10;
                fileWriter = null;
            } catch (Throwable th2) {
                th = th2;
                fileWriter = null;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
