package com.fluttercandies.image_editor.common.font;

import android.graphics.Typeface;
import android.os.Build;
import com.jaredrummler.truetypeparser.TTFFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes3.dex */
public class FontUtils {
    private static int fontIndex = -1;
    private static final Map<String, Typeface> map = new HashMap();

    @Nullable
    public static Typeface getFont(@NotNull String str) {
        return map.get(str);
    }

    public static String registerFont(String str) throws IOException {
        String fullName = Build.VERSION.SDK_INT >= 26 ? TTFFile.open(Files.newInputStream(Paths.get(str, new String[0]), new OpenOption[0])).getFullName() : TTFFile.open(new FileInputStream(str)).getFullName();
        if (fullName == null) {
            int i2 = fontIndex + 1;
            fontIndex = i2;
            fullName = String.valueOf(i2);
        }
        Map<String, Typeface> map2 = map;
        if (map2.containsKey(fullName)) {
            return fullName;
        }
        map2.put(fullName, Typeface.createFromFile(new File(str)));
        return fullName;
    }
}
