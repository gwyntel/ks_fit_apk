package org.mozilla.javascript.tools.idswitch;

import com.alipay.sdk.m.u.i;
import com.huawei.hms.framework.common.ContainerUtils;
import com.umeng.analytics.pro.bc;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.tools.ToolErrorReporter;

/* loaded from: classes5.dex */
public class SwitchGenerator {
    private CodePrinter P;
    private ToolErrorReporter R;
    private boolean c_was_defined;
    private int[] columns;
    private String default_value;
    private IdValuePair[] pairs;
    private String source_file;
    String v_switch_label = "L0";
    String v_label = "L";
    String v_s = "s";
    String v_c = bc.aL;
    String v_guess = "X";
    String v_id = "id";
    String v_length_suffix = "_length";
    int use_if_threshold = 3;
    int char_tail_test_threshold = 2;

    private static boolean bigger(IdValuePair idValuePair, IdValuePair idValuePair2, int i2) {
        if (i2 >= 0) {
            return idValuePair.id.charAt(i2) > idValuePair2.id.charAt(i2);
        }
        int i3 = idValuePair.idLength - idValuePair2.idLength;
        return i3 != 0 ? i3 > 0 : idValuePair.id.compareTo(idValuePair2.id) > 0;
    }

    private void check_all_is_different(int i2, int i3) {
        if (i2 == i3) {
            return;
        }
        IdValuePair idValuePair = this.pairs[i2];
        while (true) {
            i2++;
            if (i2 == i3) {
                return;
            }
            IdValuePair idValuePair2 = this.pairs[i2];
            if (idValuePair.id.equals(idValuePair2.id)) {
                throw on_same_pair_fail(idValuePair, idValuePair2);
            }
            idValuePair = idValuePair2;
        }
    }

    private int count_different_chars(int i2, int i3, int i4) {
        int i5 = 0;
        char c2 = 65535;
        while (i2 != i3) {
            char cCharAt = this.pairs[i2].id.charAt(i4);
            if (cCharAt != c2) {
                i5++;
                c2 = cCharAt;
            }
            i2++;
        }
        return i5;
    }

    private int count_different_lengths(int i2, int i3) {
        int i4 = 0;
        int i5 = -1;
        while (i2 != i3) {
            int i6 = this.pairs[i2].idLength;
            if (i5 != i6) {
                i4++;
                i5 = i6;
            }
            i2++;
        }
        return i4;
    }

    private int find_max_different_column(int i2, int i3, int i4) {
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 != i4; i7++) {
            int i8 = this.columns[i7];
            sort_pairs(i2, i3, i8);
            int iCount_different_chars = count_different_chars(i2, i3, i8);
            if (iCount_different_chars == i3 - i2) {
                return i7;
            }
            if (i6 < iCount_different_chars) {
                i5 = i7;
                i6 = iCount_different_chars;
            }
        }
        if (i5 != i4 - 1) {
            sort_pairs(i2, i3, this.columns[i5]);
        }
        return i5;
    }

    private void generate_body(int i2, int i3, int i4) {
        this.P.indent(i4);
        this.P.p(this.v_switch_label);
        this.P.p(": { ");
        this.P.p(this.v_id);
        this.P.p(" = ");
        this.P.p(this.default_value);
        this.P.p("; String ");
        this.P.p(this.v_guess);
        this.P.p(" = null;");
        this.c_was_defined = false;
        int offset = this.P.getOffset();
        this.P.p(" int ");
        this.P.p(this.v_c);
        this.P.p(';');
        int offset2 = this.P.getOffset();
        this.P.nl();
        int i5 = i4 + 1;
        generate_length_switch(i2, i3, i5);
        if (!this.c_was_defined) {
            this.P.erase(offset, offset2);
        }
        this.P.indent(i5);
        this.P.p("if (");
        this.P.p(this.v_guess);
        this.P.p("!=null && ");
        this.P.p(this.v_guess);
        this.P.p("!=");
        this.P.p(this.v_s);
        this.P.p(" && !");
        this.P.p(this.v_guess);
        this.P.p(".equals(");
        this.P.p(this.v_s);
        this.P.p(")) ");
        this.P.p(this.v_id);
        this.P.p(" = ");
        this.P.p(this.default_value);
        this.P.p(i.f9802b);
        this.P.nl();
        this.P.indent(i5);
        this.P.p("break ");
        this.P.p(this.v_switch_label);
        this.P.p(i.f9802b);
        this.P.nl();
        this.P.line(i4, i.f9804d);
    }

    private void generate_length_switch(int i2, int i3, int i4) {
        boolean z2;
        int i5;
        sort_pairs(i2, i3, -1);
        check_all_is_different(i2, i3);
        int iCount_different_lengths = count_different_lengths(i2, i3);
        this.columns = new int[this.pairs[i3 - 1].idLength];
        int i6 = 0;
        if (iCount_different_lengths <= this.use_if_threshold) {
            if (iCount_different_lengths != 1) {
                this.P.indent(i4);
                this.P.p("int ");
                this.P.p(this.v_s);
                this.P.p(this.v_length_suffix);
                this.P.p(" = ");
                this.P.p(this.v_s);
                this.P.p(".length();");
                this.P.nl();
            }
            z2 = true;
        } else {
            this.P.indent(i4);
            this.P.p(this.v_label);
            this.P.p(": switch (");
            this.P.p(this.v_s);
            this.P.p(".length()) {");
            this.P.nl();
            z2 = false;
        }
        int i7 = this.pairs[i2].idLength;
        int i8 = i2;
        while (true) {
            int i9 = i8;
            while (true) {
                i5 = i9 + 1;
                if (i5 == i3 || (i6 = this.pairs[i5].idLength) != i7) {
                    break;
                } else {
                    i9 = i5;
                }
            }
            if (z2) {
                this.P.indent(i4);
                if (i8 != i2) {
                    this.P.p("else ");
                }
                this.P.p("if (");
                if (iCount_different_lengths == 1) {
                    this.P.p(this.v_s);
                    this.P.p(".length()==");
                } else {
                    this.P.p(this.v_s);
                    this.P.p(this.v_length_suffix);
                    this.P.p("==");
                }
                this.P.p(i7);
                this.P.p(") {");
            } else {
                this.P.indent(i4);
                this.P.p("case ");
                this.P.p(i7);
                this.P.p(":");
            }
            generate_letter_switch(i8, i5, i4 + 1, !z2, z2);
            if (z2) {
                this.P.p(i.f9804d);
                this.P.nl();
            } else {
                this.P.p("break ");
                this.P.p(this.v_label);
                this.P.p(i.f9802b);
                this.P.nl();
            }
            if (i5 == i3) {
                break;
            }
            i7 = i6;
            i8 = i5;
        }
        if (z2) {
            return;
        }
        this.P.indent(i4);
        this.P.p(i.f9804d);
        this.P.nl();
    }

    private void generate_letter_switch(int i2, int i3, int i4, boolean z2, boolean z3) {
        int i5 = this.pairs[i2].idLength;
        for (int i6 = 0; i6 != i5; i6++) {
            this.columns[i6] = i6;
        }
        generate_letter_switch_r(i2, i3, i5, i4, z2, z3);
    }

    private boolean generate_letter_switch_r(int i2, int i3, int i4, int i5, boolean z2, boolean z3) {
        boolean z4;
        boolean z5;
        boolean z6;
        int i6;
        boolean z7 = false;
        int i7 = 1;
        if (i2 + 1 == i3) {
            this.P.p(' ');
            IdValuePair idValuePair = this.pairs[i2];
            if (i4 > this.char_tail_test_threshold) {
                this.P.p(this.v_guess);
                this.P.p(ContainerUtils.KEY_VALUE_DELIMITER);
                this.P.qstring(idValuePair.id);
                this.P.p(i.f9802b);
                this.P.p(this.v_id);
                this.P.p(ContainerUtils.KEY_VALUE_DELIMITER);
                this.P.p(idValuePair.value);
                this.P.p(i.f9802b);
            } else if (i4 == 0) {
                this.P.p(this.v_id);
                this.P.p(ContainerUtils.KEY_VALUE_DELIMITER);
                this.P.p(idValuePair.value);
                this.P.p("; break ");
                this.P.p(this.v_switch_label);
                this.P.p(i.f9802b);
                z7 = true;
            } else {
                this.P.p("if (");
                int i8 = this.columns[0];
                this.P.p(this.v_s);
                this.P.p(".charAt(");
                this.P.p(i8);
                this.P.p(")==");
                this.P.qchar(idValuePair.id.charAt(i8));
                while (i7 != i4) {
                    this.P.p(" && ");
                    int i9 = this.columns[i7];
                    this.P.p(this.v_s);
                    this.P.p(".charAt(");
                    this.P.p(i9);
                    this.P.p(")==");
                    this.P.qchar(idValuePair.id.charAt(i9));
                    i7++;
                }
                this.P.p(") {");
                this.P.p(this.v_id);
                this.P.p(ContainerUtils.KEY_VALUE_DELIMITER);
                this.P.p(idValuePair.value);
                this.P.p("; break ");
                this.P.p(this.v_switch_label);
                this.P.p(";}");
            }
            this.P.p(' ');
            return z7;
        }
        int iFind_max_different_column = find_max_different_column(i2, i3, i4);
        int i10 = this.columns[iFind_max_different_column];
        int iCount_different_chars = count_different_chars(i2, i3, i10);
        int[] iArr = this.columns;
        int i11 = i4 - 1;
        iArr[iFind_max_different_column] = iArr[i11];
        if (z3) {
            this.P.nl();
            this.P.indent(i5);
        } else {
            this.P.p(' ');
        }
        if (iCount_different_chars <= this.use_if_threshold) {
            this.c_was_defined = true;
            this.P.p(this.v_c);
            this.P.p(ContainerUtils.KEY_VALUE_DELIMITER);
            this.P.p(this.v_s);
            this.P.p(".charAt(");
            this.P.p(i10);
            this.P.p(");");
            z5 = z2;
            z6 = true;
        } else {
            if (z2) {
                z4 = z2;
            } else {
                this.P.p(this.v_label);
                this.P.p(": ");
                z4 = true;
            }
            this.P.p("switch (");
            this.P.p(this.v_s);
            this.P.p(".charAt(");
            this.P.p(i10);
            this.P.p(")) {");
            z5 = z4;
            z6 = false;
        }
        char cCharAt = this.pairs[i2].id.charAt(i10);
        int i12 = i2;
        int i13 = i12;
        char cCharAt2 = 0;
        while (true) {
            int i14 = i13 + 1;
            if (i14 == i3 || (cCharAt2 = this.pairs[i14].id.charAt(i10)) != cCharAt) {
                char c2 = cCharAt2;
                if (z6) {
                    this.P.nl();
                    this.P.indent(i5);
                    if (i12 != i2) {
                        this.P.p("else ");
                    }
                    this.P.p("if (");
                    this.P.p(this.v_c);
                    this.P.p("==");
                    this.P.qchar(cCharAt);
                    this.P.p(") {");
                } else {
                    this.P.nl();
                    this.P.indent(i5);
                    this.P.p("case ");
                    this.P.qchar(cCharAt);
                    this.P.p(":");
                }
                i6 = i10;
                int i15 = i7;
                boolean zGenerate_letter_switch_r = generate_letter_switch_r(i12, i14, i11, i5 + 1, z5, z6);
                if (z6) {
                    this.P.p(i.f9804d);
                } else if (!zGenerate_letter_switch_r) {
                    this.P.p("break ");
                    this.P.p(this.v_label);
                    this.P.p(i.f9802b);
                }
                if (i14 == i3) {
                    break;
                }
                i12 = i14;
                i13 = i12;
                cCharAt = c2;
                cCharAt2 = cCharAt;
                i10 = i6;
                i7 = i15;
            } else {
                i13 = i14;
            }
        }
        if (z6) {
            this.P.nl();
            if (z3) {
                this.P.indent(i5 - 1);
            } else {
                this.P.indent(i5);
            }
        } else {
            this.P.nl();
            this.P.indent(i5);
            this.P.p(i.f9804d);
            if (z3) {
                this.P.nl();
                this.P.indent(i5 - 1);
            } else {
                this.P.p(' ');
            }
        }
        this.columns[iFind_max_different_column] = i6;
        return false;
    }

    private static void heap4Sort(IdValuePair[] idValuePairArr, int i2, int i3, int i4) {
        if (i3 <= 1) {
            return;
        }
        makeHeap4(idValuePairArr, i2, i3, i4);
        while (i3 > 1) {
            i3--;
            int i5 = i2 + i3;
            IdValuePair idValuePair = idValuePairArr[i5];
            idValuePairArr[i5] = idValuePairArr[i2];
            idValuePairArr[i2] = idValuePair;
            heapify4(idValuePairArr, i2, i3, 0, i4);
        }
    }

    private static void heapify4(IdValuePair[] idValuePairArr, int i2, int i3, int i4, int i5) {
        IdValuePair idValuePair = idValuePairArr[i2 + i4];
        while (true) {
            int i6 = i4 << 2;
            int i7 = i6 | 1;
            int i8 = i6 | 2;
            int i9 = i6 | 3;
            int i10 = i6 + 4;
            if (i10 >= i3) {
                if (i7 < i3) {
                    IdValuePair idValuePair2 = idValuePairArr[i2 + i7];
                    if (i8 != i3) {
                        IdValuePair idValuePair3 = idValuePairArr[i2 + i8];
                        if (bigger(idValuePair3, idValuePair2, i5)) {
                            i7 = i8;
                            idValuePair2 = idValuePair3;
                        }
                        if (i9 != i3) {
                            IdValuePair idValuePair4 = idValuePairArr[i2 + i9];
                            if (bigger(idValuePair4, idValuePair2, i5)) {
                                idValuePair2 = idValuePair4;
                                i7 = i9;
                            }
                        }
                    }
                    if (bigger(idValuePair2, idValuePair, i5)) {
                        idValuePairArr[i4 + i2] = idValuePair2;
                        idValuePairArr[i2 + i7] = idValuePair;
                        return;
                    }
                    return;
                }
                return;
            }
            IdValuePair idValuePair5 = idValuePairArr[i2 + i7];
            IdValuePair idValuePair6 = idValuePairArr[i2 + i8];
            IdValuePair idValuePair7 = idValuePairArr[i2 + i9];
            IdValuePair idValuePair8 = idValuePairArr[i2 + i10];
            if (bigger(idValuePair6, idValuePair5, i5)) {
                i7 = i8;
                idValuePair5 = idValuePair6;
            }
            if (bigger(idValuePair8, idValuePair7, i5)) {
                i9 = i10;
                idValuePair7 = idValuePair8;
            }
            if (bigger(idValuePair7, idValuePair5, i5)) {
                i7 = i9;
                idValuePair5 = idValuePair7;
            }
            if (bigger(idValuePair, idValuePair5, i5)) {
                return;
            }
            idValuePairArr[i4 + i2] = idValuePair5;
            idValuePairArr[i2 + i7] = idValuePair;
            i4 = i7;
        }
    }

    private static void makeHeap4(IdValuePair[] idValuePairArr, int i2, int i3, int i4) {
        int i5 = (i3 + 2) >> 2;
        while (i5 != 0) {
            i5--;
            heapify4(idValuePairArr, i2, i3, i5, i4);
        }
    }

    private EvaluatorException on_same_pair_fail(IdValuePair idValuePair, IdValuePair idValuePair2) {
        int i2;
        int lineNumber = idValuePair.getLineNumber();
        int lineNumber2 = idValuePair2.getLineNumber();
        if (lineNumber2 > lineNumber) {
            i2 = lineNumber2;
        } else {
            i2 = lineNumber;
            lineNumber = lineNumber2;
        }
        return this.R.runtimeError(ToolErrorReporter.getMessage("msg.idswitch.same_string", idValuePair.id, new Integer(lineNumber)), this.source_file, i2, null, 0);
    }

    private void sort_pairs(int i2, int i3, int i4) {
        heap4Sort(this.pairs, i2, i3 - i2, i4);
    }

    public void generateSwitch(String[] strArr, String str) {
        int length = strArr.length / 2;
        IdValuePair[] idValuePairArr = new IdValuePair[length];
        for (int i2 = 0; i2 != length; i2++) {
            int i3 = i2 * 2;
            idValuePairArr[i2] = new IdValuePair(strArr[i3], strArr[i3 + 1]);
        }
        generateSwitch(idValuePairArr, str);
    }

    public CodePrinter getCodePrinter() {
        return this.P;
    }

    public ToolErrorReporter getReporter() {
        return this.R;
    }

    public String getSourceFileName() {
        return this.source_file;
    }

    public void setCodePrinter(CodePrinter codePrinter) {
        this.P = codePrinter;
    }

    public void setReporter(ToolErrorReporter toolErrorReporter) {
        this.R = toolErrorReporter;
    }

    public void setSourceFileName(String str) {
        this.source_file = str;
    }

    public void generateSwitch(IdValuePair[] idValuePairArr, String str) {
        int length = idValuePairArr.length;
        if (length == 0) {
            return;
        }
        this.pairs = idValuePairArr;
        this.default_value = str;
        generate_body(0, length, 2);
    }
}
