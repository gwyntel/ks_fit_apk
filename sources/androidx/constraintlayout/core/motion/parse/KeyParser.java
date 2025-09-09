package androidx.constraintlayout.core.motion.parse;

import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.parser.CLElement;
import androidx.constraintlayout.core.parser.CLKey;
import androidx.constraintlayout.core.parser.CLObject;
import androidx.constraintlayout.core.parser.CLParser;
import androidx.constraintlayout.core.parser.CLParsingException;

/* loaded from: classes.dex */
public class KeyParser {

    /* JADX INFO: Access modifiers changed from: private */
    interface DataType {
        int get(int i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface Ids {
        int get(String str);
    }

    public static void main(String[] strArr) {
        parseAttributes("{frame:22,\ntarget:'widget1',\neasing:'easeIn',\ncurveFit:'spline',\nprogress:0.3,\nalpha:0.2,\nelevation:0.7,\nrotationZ:23,\nrotationX:25.0,\nrotationY:27.0,\npivotX:15,\npivotY:17,\npivotTarget:'32',\npathRotate:23,\nscaleX:0.5,\nscaleY:0.7,\ntranslationX:5,\ntranslationY:7,\ntranslationZ:11,\n}");
    }

    private static TypedBundle parse(String str, Ids ids, DataType dataType) {
        TypedBundle typedBundle = new TypedBundle();
        try {
            CLObject cLObject = CLParser.parse(str);
            int size = cLObject.size();
            for (int i2 = 0; i2 < size; i2++) {
                CLKey cLKey = (CLKey) cLObject.get(i2);
                String strContent = cLKey.content();
                CLElement value = cLKey.getValue();
                int i3 = ids.get(strContent);
                if (i3 == -1) {
                    System.err.println("unknown type " + strContent);
                } else {
                    int i4 = dataType.get(i3);
                    if (i4 == 1) {
                        typedBundle.add(i3, cLObject.getBoolean(i2));
                    } else if (i4 == 2) {
                        typedBundle.add(i3, value.getInt());
                        System.out.println("parse " + strContent + " INT_MASK > " + value.getInt());
                    } else if (i4 == 4) {
                        typedBundle.add(i3, value.getFloat());
                        System.out.println("parse " + strContent + " FLOAT_MASK > " + value.getFloat());
                    } else if (i4 == 8) {
                        typedBundle.add(i3, value.content());
                        System.out.println("parse " + strContent + " STRING_MASK > " + value.content());
                    }
                }
            }
        } catch (CLParsingException e2) {
            e2.printStackTrace();
        }
        return typedBundle;
    }

    public static TypedBundle parseAttributes(String str) {
        return parse(str, new Ids() { // from class: androidx.constraintlayout.core.motion.parse.a
            @Override // androidx.constraintlayout.core.motion.parse.KeyParser.Ids
            public final int get(String str2) {
                return androidx.constraintlayout.core.motion.utils.a.a(str2);
            }
        }, new DataType() { // from class: androidx.constraintlayout.core.motion.parse.b
            @Override // androidx.constraintlayout.core.motion.parse.KeyParser.DataType
            public final int get(int i2) {
                return androidx.constraintlayout.core.motion.utils.a.b(i2);
            }
        });
    }
}
