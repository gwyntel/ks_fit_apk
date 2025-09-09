package org.mozilla.classfile;

import androidx.core.view.ViewCompat;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.ObjArray;
import org.mozilla.javascript.UintMap;

/* loaded from: classes5.dex */
public class ClassFileWriter {
    public static final short ACC_ABSTRACT = 1024;
    public static final short ACC_FINAL = 16;
    public static final short ACC_NATIVE = 256;
    public static final short ACC_PRIVATE = 2;
    public static final short ACC_PROTECTED = 4;
    public static final short ACC_PUBLIC = 1;
    public static final short ACC_STATIC = 8;
    public static final short ACC_SUPER = 32;
    public static final short ACC_SYNCHRONIZED = 32;
    public static final short ACC_TRANSIENT = 128;
    public static final short ACC_VOLATILE = 64;
    private static final boolean DEBUGCODE = false;
    private static final boolean DEBUGLABELS = false;
    private static final boolean DEBUGSTACK = false;
    private static final int ExceptionTableSize = 4;
    private static final int FileHeaderConstant = -889275714;
    private static final boolean GenerateStackMap;
    private static final int LineNumberTableSize = 16;
    private static final int MIN_FIXUP_TABLE_SIZE = 40;
    private static final int MIN_LABEL_TABLE_SIZE = 32;
    private static final int MajorVersion;
    private static final int MinorVersion;
    private static final int SuperBlockStartsSize = 4;
    private String generatedClassName;
    private ObjArray itsBootstrapMethods;
    private int itsCodeBufferTop;
    private ConstantPool itsConstantPool;
    private ClassFileMethod itsCurrentMethod;
    private ExceptionTableEntry[] itsExceptionTable;
    private int itsExceptionTableTop;
    private long[] itsFixupTable;
    private int itsFixupTableTop;
    private short itsFlags;
    private int[] itsLabelTable;
    private int itsLabelTableTop;
    private int[] itsLineNumberTable;
    private int itsLineNumberTableTop;
    private short itsMaxLocals;
    private short itsMaxStack;
    private short itsSourceFileNameIndex;
    private short itsStackTop;
    private short itsSuperClassIndex;
    private short itsThisClassIndex;
    private ObjArray itsVarDescriptors;
    private int[] itsSuperBlockStarts = null;
    private int itsSuperBlockStartsTop = 0;
    private UintMap itsJumpFroms = null;
    private byte[] itsCodeBuffer = new byte[256];
    private ObjArray itsMethods = new ObjArray();
    private ObjArray itsFields = new ObjArray();
    private ObjArray itsInterfaces = new ObjArray();
    private int itsBootstrapMethodsLength = 0;
    private char[] tmpCharBuffer = new char[64];

    final class BootstrapEntry {
        final byte[] code;

        BootstrapEntry(MHandle mHandle, Object... objArr) {
            byte[] bArr = new byte[(objArr.length * 2) + 4];
            this.code = bArr;
            ClassFileWriter.putInt16(ClassFileWriter.this.itsConstantPool.addMethodHandle(mHandle), bArr, 0);
            ClassFileWriter.putInt16(objArr.length, bArr, 2);
            for (int i2 = 0; i2 < objArr.length; i2++) {
                ClassFileWriter.putInt16(ClassFileWriter.this.itsConstantPool.addConstant(objArr[i2]), this.code, (i2 * 2) + 4);
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof BootstrapEntry) && Arrays.equals(this.code, ((BootstrapEntry) obj).code);
        }

        public int hashCode() {
            return ~Arrays.hashCode(this.code);
        }
    }

    public static class ClassFileFormatException extends RuntimeException {
        private static final long serialVersionUID = 1263998431033790599L;

        ClassFileFormatException(String str) {
            super(str);
        }
    }

    public static final class MHandle {
        final String desc;
        final String name;
        final String owner;
        final byte tag;

        public MHandle(byte b2, String str, String str2, String str3) {
            this.tag = b2;
            this.owner = str;
            this.name = str2;
            this.desc = str3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MHandle)) {
                return false;
            }
            MHandle mHandle = (MHandle) obj;
            return this.tag == mHandle.tag && this.owner.equals(mHandle.owner) && this.name.equals(mHandle.name) && this.desc.equals(mHandle.desc);
        }

        public int hashCode() {
            return this.tag + (this.owner.hashCode() * this.name.hashCode() * this.desc.hashCode());
        }

        public String toString() {
            return this.owner + '.' + this.name + this.desc + " (" + ((int) this.tag) + ')';
        }
    }

    final class StackMapTable {
        static final boolean DEBUGSTACKMAP = false;
        private SuperBlock[] superBlockDeps;
        private SuperBlock[] superBlocks = null;
        private int[] stack = null;
        private int[] locals = null;
        private SuperBlock[] workList = null;
        private byte[] rawStackMap = null;
        private int localsTop = 0;
        private int stackTop = 0;
        private int workListTop = 0;
        private int rawStackMapTop = 0;
        private boolean wide = false;

        StackMapTable() {
        }

        private void addToWorkList(SuperBlock superBlock) {
            if (superBlock.isInQueue()) {
                return;
            }
            superBlock.setInQueue(true);
            superBlock.setInitialized(true);
            int i2 = this.workListTop;
            SuperBlock[] superBlockArr = this.workList;
            if (i2 == superBlockArr.length) {
                SuperBlock[] superBlockArr2 = new SuperBlock[i2 * 2];
                System.arraycopy(superBlockArr, 0, superBlockArr2, 0, i2);
                this.workList = superBlockArr2;
            }
            SuperBlock[] superBlockArr3 = this.workList;
            int i3 = this.workListTop;
            this.workListTop = i3 + 1;
            superBlockArr3[i3] = superBlock;
        }

        private void clearStack() {
            this.stackTop = 0;
        }

        private void computeRawStackMap() {
            int[] trimmedLocals = this.superBlocks[0].getTrimmedLocals();
            int start = -1;
            int i2 = 1;
            while (true) {
                SuperBlock[] superBlockArr = this.superBlocks;
                if (i2 >= superBlockArr.length) {
                    return;
                }
                SuperBlock superBlock = superBlockArr[i2];
                int[] trimmedLocals2 = superBlock.getTrimmedLocals();
                int[] stack = superBlock.getStack();
                int start2 = (superBlock.getStart() - start) - 1;
                if (stack.length == 0) {
                    int length = trimmedLocals.length > trimmedLocals2.length ? trimmedLocals2.length : trimmedLocals.length;
                    int iAbs = Math.abs(trimmedLocals.length - trimmedLocals2.length);
                    int i3 = 0;
                    while (i3 < length && trimmedLocals[i3] == trimmedLocals2[i3]) {
                        i3++;
                    }
                    if (i3 == trimmedLocals2.length && iAbs == 0) {
                        writeSameFrame(trimmedLocals2, start2);
                    } else if (i3 == trimmedLocals2.length && iAbs <= 3) {
                        writeChopFrame(iAbs, start2);
                    } else if (i3 != trimmedLocals.length || iAbs > 3) {
                        writeFullFrame(trimmedLocals2, stack, start2);
                    } else {
                        writeAppendFrame(trimmedLocals2, iAbs, start2);
                    }
                } else if (stack.length != 1) {
                    writeFullFrame(trimmedLocals2, stack, start2);
                } else if (Arrays.equals(trimmedLocals, trimmedLocals2)) {
                    writeSameLocalsOneStackItemFrame(trimmedLocals2, stack, start2);
                } else {
                    writeFullFrame(trimmedLocals2, stack, start2);
                }
                start = superBlock.getStart();
                i2++;
                trimmedLocals = trimmedLocals2;
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:121:0x03ae  */
        /* JADX WARN: Removed duplicated region for block: B:124:0x03b8  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private int execute(int r12) {
            /*
                Method dump skipped, instructions count: 1366
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.mozilla.classfile.ClassFileWriter.StackMapTable.execute(int):int");
        }

        private void executeALoad(int i2) {
            int local = getLocal(i2);
            int tag = TypeInfo.getTag(local);
            if (tag == 7 || tag == 6 || tag == 8 || tag == 5) {
                push(local);
                return;
            }
            throw new IllegalStateException("bad local variable type: " + local + " at index: " + i2);
        }

        private void executeAStore(int i2) {
            setLocal(i2, pop());
        }

        private void executeBlock(SuperBlock superBlock) {
            int start = superBlock.getStart();
            int i2 = 0;
            while (start < superBlock.getEnd()) {
                i2 = ClassFileWriter.this.itsCodeBuffer[start] & 255;
                int iExecute = execute(start);
                if (isBranch(i2)) {
                    flowInto(getBranchTarget(start));
                } else if (i2 == 170) {
                    int i3 = start + 1 + ((~start) & 3);
                    flowInto(getSuperBlockFromOffset(getOperand(i3, 4) + start));
                    int operand = (getOperand(i3 + 8, 4) - getOperand(i3 + 4, 4)) + 1;
                    int i4 = i3 + 12;
                    for (int i5 = 0; i5 < operand; i5++) {
                        flowInto(getSuperBlockFromOffset(getOperand((i5 * 4) + i4, 4) + start));
                    }
                }
                for (int i6 = 0; i6 < ClassFileWriter.this.itsExceptionTableTop; i6++) {
                    ExceptionTableEntry exceptionTableEntry = ClassFileWriter.this.itsExceptionTable[i6];
                    short labelPC = (short) ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsStartLabel);
                    short labelPC2 = (short) ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsEndLabel);
                    if (start >= labelPC && start < labelPC2) {
                        SuperBlock superBlockFromOffset = getSuperBlockFromOffset((short) ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsHandlerLabel));
                        short s2 = exceptionTableEntry.itsCatchType;
                        superBlockFromOffset.merge(this.locals, this.localsTop, new int[]{s2 == 0 ? TypeInfo.OBJECT(ClassFileWriter.this.itsConstantPool.addClass("java/lang/Throwable")) : TypeInfo.OBJECT(s2)}, 1, ClassFileWriter.this.itsConstantPool);
                        addToWorkList(superBlockFromOffset);
                    }
                }
                start += iExecute;
            }
            if (isSuperBlockEnd(i2)) {
                return;
            }
            int index = superBlock.getIndex() + 1;
            SuperBlock[] superBlockArr = this.superBlocks;
            if (index < superBlockArr.length) {
                flowInto(superBlockArr[index]);
            }
        }

        private void executeStore(int i2, int i3) {
            pop();
            setLocal(i2, i3);
        }

        private void executeWorkList() {
            while (true) {
                int i2 = this.workListTop;
                if (i2 <= 0) {
                    return;
                }
                SuperBlock[] superBlockArr = this.workList;
                int i3 = i2 - 1;
                this.workListTop = i3;
                SuperBlock superBlock = superBlockArr[i3];
                superBlock.setInQueue(false);
                this.locals = superBlock.getLocals();
                int[] stack = superBlock.getStack();
                this.stack = stack;
                this.localsTop = this.locals.length;
                this.stackTop = stack.length;
                executeBlock(superBlock);
            }
        }

        private void flowInto(SuperBlock superBlock) {
            if (superBlock.merge(this.locals, this.localsTop, this.stack, this.stackTop, ClassFileWriter.this.itsConstantPool)) {
                addToWorkList(superBlock);
            }
        }

        private SuperBlock getBranchTarget(int i2) {
            return getSuperBlockFromOffset(i2 + ((ClassFileWriter.this.itsCodeBuffer[i2] & 255) == 200 ? getOperand(i2 + 1, 4) : (short) getOperand(i2 + 1, 2)));
        }

        private int getLocal(int i2) {
            if (i2 < this.localsTop) {
                return this.locals[i2];
            }
            return 0;
        }

        private int getOperand(int i2) {
            return getOperand(i2, 1);
        }

        private SuperBlock[] getSuperBlockDependencies() {
            SuperBlock[] superBlockArr = new SuperBlock[this.superBlocks.length];
            for (int i2 = 0; i2 < ClassFileWriter.this.itsExceptionTableTop; i2++) {
                ExceptionTableEntry exceptionTableEntry = ClassFileWriter.this.itsExceptionTable[i2];
                superBlockArr[getSuperBlockFromOffset((short) ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsHandlerLabel)).getIndex()] = getSuperBlockFromOffset((short) ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsStartLabel));
            }
            for (int i3 : ClassFileWriter.this.itsJumpFroms.getKeys()) {
                superBlockArr[getSuperBlockFromOffset(i3).getIndex()] = getSuperBlockFromOffset(ClassFileWriter.this.itsJumpFroms.getInt(i3, -1));
            }
            return superBlockArr;
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0030, code lost:
        
            throw new java.lang.IllegalArgumentException("bad offset: " + r4);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private org.mozilla.classfile.SuperBlock getSuperBlockFromOffset(int r4) {
            /*
                r3 = this;
                r0 = 0
            L1:
                org.mozilla.classfile.SuperBlock[] r1 = r3.superBlocks
                int r2 = r1.length
                if (r0 >= r2) goto L1a
                r1 = r1[r0]
                if (r1 == 0) goto L1a
                int r2 = r1.getStart()
                if (r4 < r2) goto L17
                int r2 = r1.getEnd()
                if (r4 >= r2) goto L17
                return r1
            L17:
                int r0 = r0 + 1
                goto L1
            L1a:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "bad offset: "
                r1.append(r2)
                r1.append(r4)
                java.lang.String r4 = r1.toString()
                r0.<init>(r4)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.mozilla.classfile.ClassFileWriter.StackMapTable.getSuperBlockFromOffset(int):org.mozilla.classfile.SuperBlock");
        }

        private int getWorstCaseWriteSize() {
            return (this.superBlocks.length - 1) * ((ClassFileWriter.this.itsMaxLocals * 3) + 7 + (ClassFileWriter.this.itsMaxStack * 3));
        }

        private void initializeTypeInfo(int i2, int i3) {
            initializeTypeInfo(i2, i3, this.locals, this.localsTop);
            initializeTypeInfo(i2, i3, this.stack, this.stackTop);
        }

        private boolean isBranch(int i2) {
            switch (i2) {
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 165:
                case 166:
                case 167:
                    return true;
                default:
                    switch (i2) {
                        case 198:
                        case 199:
                        case 200:
                            return true;
                        default:
                            return false;
                    }
            }
        }

        private boolean isSuperBlockEnd(int i2) {
            if (i2 == 167 || i2 == 191 || i2 == 200 || i2 == 176 || i2 == 177) {
                return true;
            }
            switch (i2) {
                case 170:
                case 171:
                case 172:
                case 173:
                case 174:
                    return true;
                default:
                    return false;
            }
        }

        private void killSuperBlock(SuperBlock superBlock) {
            int[] locals = new int[0];
            int[] iArr = {TypeInfo.OBJECT("java/lang/Throwable", ClassFileWriter.this.itsConstantPool)};
            for (int i2 = 0; i2 < ClassFileWriter.this.itsExceptionTableTop; i2++) {
                ExceptionTableEntry exceptionTableEntry = ClassFileWriter.this.itsExceptionTable[i2];
                int labelPC = ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsStartLabel);
                int labelPC2 = ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsEndLabel);
                SuperBlock superBlockFromOffset = getSuperBlockFromOffset(ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsHandlerLabel));
                if ((superBlock.getStart() > labelPC && superBlock.getStart() < labelPC2) || (labelPC > superBlock.getStart() && labelPC < superBlock.getEnd() && superBlockFromOffset.isInitialized())) {
                    locals = superBlockFromOffset.getLocals();
                    break;
                }
            }
            int[] iArr2 = locals;
            int i3 = 0;
            while (i3 < ClassFileWriter.this.itsExceptionTableTop) {
                if (ClassFileWriter.this.getLabelPC(ClassFileWriter.this.itsExceptionTable[i3].itsStartLabel) == superBlock.getStart()) {
                    for (int i4 = i3 + 1; i4 < ClassFileWriter.this.itsExceptionTableTop; i4++) {
                        ClassFileWriter.this.itsExceptionTable[i4 - 1] = ClassFileWriter.this.itsExceptionTable[i4];
                    }
                    ClassFileWriter.access$410(ClassFileWriter.this);
                    i3--;
                }
                i3++;
            }
            superBlock.merge(iArr2, iArr2.length, iArr, 1, ClassFileWriter.this.itsConstantPool);
            int end = superBlock.getEnd() - 1;
            ClassFileWriter.this.itsCodeBuffer[end] = -65;
            for (int start = superBlock.getStart(); start < end; start++) {
                ClassFileWriter.this.itsCodeBuffer[start] = 0;
            }
        }

        private int pop() {
            int[] iArr = this.stack;
            int i2 = this.stackTop - 1;
            this.stackTop = i2;
            return iArr[i2];
        }

        private long pop2() {
            long jPop = pop();
            return TypeInfo.isTwoWords((int) jPop) ? jPop : (jPop << 32) | (pop() & ViewCompat.MEASURED_SIZE_MASK);
        }

        private void push(int i2) {
            int i3 = this.stackTop;
            if (i3 == this.stack.length) {
                int[] iArr = new int[Math.max(i3 * 2, 4)];
                System.arraycopy(this.stack, 0, iArr, 0, this.stackTop);
                this.stack = iArr;
            }
            int[] iArr2 = this.stack;
            int i4 = this.stackTop;
            this.stackTop = i4 + 1;
            iArr2[i4] = i2;
        }

        private void push2(long j2) {
            push((int) (j2 & 16777215));
            long j3 = j2 >>> 32;
            if (j3 != 0) {
                push((int) (j3 & 16777215));
            }
        }

        private void setLocal(int i2, int i3) {
            int i4 = this.localsTop;
            if (i2 >= i4) {
                int i5 = i2 + 1;
                int[] iArr = new int[i5];
                System.arraycopy(this.locals, 0, iArr, 0, i4);
                this.locals = iArr;
                this.localsTop = i5;
            }
            this.locals[i2] = i3;
        }

        private void verify() {
            int[] iArrCreateInitialLocals = ClassFileWriter.this.createInitialLocals();
            int i2 = 0;
            this.superBlocks[0].merge(iArrCreateInitialLocals, iArrCreateInitialLocals.length, new int[0], 0, ClassFileWriter.this.itsConstantPool);
            this.workList = new SuperBlock[]{this.superBlocks[0]};
            this.workListTop = 1;
            executeWorkList();
            while (true) {
                SuperBlock[] superBlockArr = this.superBlocks;
                if (i2 >= superBlockArr.length) {
                    executeWorkList();
                    return;
                }
                SuperBlock superBlock = superBlockArr[i2];
                if (!superBlock.isInitialized()) {
                    killSuperBlock(superBlock);
                }
                i2++;
            }
        }

        private void writeAppendFrame(int[] iArr, int i2, int i3) {
            int length = iArr.length - i2;
            byte[] bArr = this.rawStackMap;
            int i4 = this.rawStackMapTop;
            int i5 = i4 + 1;
            this.rawStackMapTop = i5;
            bArr[i4] = (byte) (i2 + 251);
            this.rawStackMapTop = ClassFileWriter.putInt16(i3, bArr, i5);
            this.rawStackMapTop = writeTypes(iArr, length);
        }

        private void writeChopFrame(int i2, int i3) {
            byte[] bArr = this.rawStackMap;
            int i4 = this.rawStackMapTop;
            int i5 = i4 + 1;
            this.rawStackMapTop = i5;
            bArr[i4] = (byte) (251 - i2);
            this.rawStackMapTop = ClassFileWriter.putInt16(i3, bArr, i5);
        }

        private void writeFullFrame(int[] iArr, int[] iArr2, int i2) {
            byte[] bArr = this.rawStackMap;
            int i3 = this.rawStackMapTop;
            int i4 = i3 + 1;
            this.rawStackMapTop = i4;
            bArr[i3] = -1;
            int iPutInt16 = ClassFileWriter.putInt16(i2, bArr, i4);
            this.rawStackMapTop = iPutInt16;
            this.rawStackMapTop = ClassFileWriter.putInt16(iArr.length, this.rawStackMap, iPutInt16);
            int iWriteTypes = writeTypes(iArr);
            this.rawStackMapTop = iWriteTypes;
            this.rawStackMapTop = ClassFileWriter.putInt16(iArr2.length, this.rawStackMap, iWriteTypes);
            this.rawStackMapTop = writeTypes(iArr2);
        }

        private void writeSameFrame(int[] iArr, int i2) {
            if (i2 <= 63) {
                byte[] bArr = this.rawStackMap;
                int i3 = this.rawStackMapTop;
                this.rawStackMapTop = i3 + 1;
                bArr[i3] = (byte) i2;
                return;
            }
            byte[] bArr2 = this.rawStackMap;
            int i4 = this.rawStackMapTop;
            int i5 = i4 + 1;
            this.rawStackMapTop = i5;
            bArr2[i4] = -5;
            this.rawStackMapTop = ClassFileWriter.putInt16(i2, bArr2, i5);
        }

        private void writeSameLocalsOneStackItemFrame(int[] iArr, int[] iArr2, int i2) {
            if (i2 <= 63) {
                byte[] bArr = this.rawStackMap;
                int i3 = this.rawStackMapTop;
                this.rawStackMapTop = i3 + 1;
                bArr[i3] = (byte) (i2 + 64);
            } else {
                byte[] bArr2 = this.rawStackMap;
                int i4 = this.rawStackMapTop;
                int i5 = i4 + 1;
                this.rawStackMapTop = i5;
                bArr2[i4] = -9;
                this.rawStackMapTop = ClassFileWriter.putInt16(i2, bArr2, i5);
            }
            writeType(iArr2[0]);
        }

        private int writeType(int i2) {
            int i3 = i2 & 255;
            byte[] bArr = this.rawStackMap;
            int i4 = this.rawStackMapTop;
            int i5 = i4 + 1;
            this.rawStackMapTop = i5;
            bArr[i4] = (byte) i3;
            if (i3 == 7 || i3 == 8) {
                this.rawStackMapTop = ClassFileWriter.putInt16(i2 >>> 8, bArr, i5);
            }
            return this.rawStackMapTop;
        }

        private int writeTypes(int[] iArr) {
            return writeTypes(iArr, 0);
        }

        int computeWriteSize() {
            this.rawStackMap = new byte[getWorstCaseWriteSize()];
            computeRawStackMap();
            return this.rawStackMapTop + 2;
        }

        void generate() {
            this.superBlocks = new SuperBlock[ClassFileWriter.this.itsSuperBlockStartsTop];
            int[] iArrCreateInitialLocals = ClassFileWriter.this.createInitialLocals();
            int i2 = 0;
            while (i2 < ClassFileWriter.this.itsSuperBlockStartsTop) {
                this.superBlocks[i2] = new SuperBlock(i2, ClassFileWriter.this.itsSuperBlockStarts[i2], i2 == ClassFileWriter.this.itsSuperBlockStartsTop + (-1) ? ClassFileWriter.this.itsCodeBufferTop : ClassFileWriter.this.itsSuperBlockStarts[i2 + 1], iArrCreateInitialLocals);
                i2++;
            }
            this.superBlockDeps = getSuperBlockDependencies();
            verify();
        }

        int write(byte[] bArr, int i2) {
            int iPutInt16 = ClassFileWriter.putInt16(this.superBlocks.length - 1, bArr, ClassFileWriter.putInt32(this.rawStackMapTop + 2, bArr, i2));
            System.arraycopy(this.rawStackMap, 0, bArr, iPutInt16, this.rawStackMapTop);
            return iPutInt16 + this.rawStackMapTop;
        }

        private int getOperand(int i2, int i3) {
            if (i3 > 4) {
                throw new IllegalArgumentException("bad operand size");
            }
            int i4 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                i4 = (i4 << 8) | (ClassFileWriter.this.itsCodeBuffer[i2 + i5] & 255);
            }
            return i4;
        }

        private int writeTypes(int[] iArr, int i2) {
            while (i2 < iArr.length) {
                this.rawStackMapTop = writeType(iArr[i2]);
                i2++;
            }
            return this.rawStackMapTop;
        }

        private void initializeTypeInfo(int i2, int i3, int[] iArr, int i4) {
            for (int i5 = 0; i5 < i4; i5++) {
                if (iArr[i5] == i2) {
                    iArr[i5] = i3;
                }
            }
        }
    }

    static {
        int i2;
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = ClassFileWriter.class.getResourceAsStream("ClassFileWriter.class");
            if (resourceAsStream == null) {
                resourceAsStream = ClassLoader.getSystemResourceAsStream("org/mozilla/classfile/ClassFileWriter.class");
            }
            byte[] bArr = new byte[8];
            int i3 = 0;
            while (i3 < 8) {
                int i4 = resourceAsStream.read(bArr, i3, 8 - i3);
                if (i4 < 0) {
                    throw new IOException();
                }
                i3 += i4;
            }
            i2 = (bArr[4] << 8) | (bArr[5] & 255);
            try {
                int i5 = (bArr[7] & 255) | (bArr[6] << 8);
                MinorVersion = i2;
                MajorVersion = i5;
                GenerateStackMap = i5 >= 50;
                if (resourceAsStream == null) {
                    return;
                }
            } catch (Exception unused) {
                MinorVersion = i2;
                MajorVersion = 48;
                GenerateStackMap = false;
                if (resourceAsStream == null) {
                    return;
                }
                resourceAsStream.close();
            } catch (Throwable th) {
                th = th;
                MinorVersion = i2;
                MajorVersion = 48;
                GenerateStackMap = false;
                if (resourceAsStream != null) {
                    try {
                        resourceAsStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Exception unused3) {
            i2 = 0;
        } catch (Throwable th2) {
            th = th2;
            i2 = 0;
        }
        try {
            resourceAsStream.close();
        } catch (IOException unused4) {
        }
    }

    public ClassFileWriter(String str, String str2, String str3) {
        this.generatedClassName = str;
        ConstantPool constantPool = new ConstantPool(this);
        this.itsConstantPool = constantPool;
        this.itsThisClassIndex = constantPool.addClass(str);
        this.itsSuperClassIndex = this.itsConstantPool.addClass(str2);
        if (str3 != null) {
            this.itsSourceFileNameIndex = this.itsConstantPool.addUtf8(str3);
        }
        this.itsFlags = (short) 33;
    }

    static /* synthetic */ int access$410(ClassFileWriter classFileWriter) {
        int i2 = classFileWriter.itsExceptionTableTop;
        classFileWriter.itsExceptionTableTop = i2 - 1;
        return i2;
    }

    private void addLabelFixup(int i2, int i3) {
        if (i2 >= 0) {
            throw new IllegalArgumentException("Bad label, no biscuit");
        }
        int i4 = i2 & Integer.MAX_VALUE;
        if (i4 >= this.itsLabelTableTop) {
            throw new IllegalArgumentException("Bad label");
        }
        int i5 = this.itsFixupTableTop;
        long[] jArr = this.itsFixupTable;
        if (jArr == null || i5 == jArr.length) {
            if (jArr == null) {
                this.itsFixupTable = new long[40];
            } else {
                long[] jArr2 = new long[jArr.length * 2];
                System.arraycopy(jArr, 0, jArr2, 0, i5);
                this.itsFixupTable = jArr2;
            }
        }
        this.itsFixupTableTop = i5 + 1;
        this.itsFixupTable[i5] = i3 | (i4 << 32);
    }

    private int addReservedCodeSpace(int i2) {
        if (this.itsCurrentMethod == null) {
            throw new IllegalArgumentException("No method to add to");
        }
        int i3 = this.itsCodeBufferTop;
        int i4 = i2 + i3;
        byte[] bArr = this.itsCodeBuffer;
        if (i4 > bArr.length) {
            int length = bArr.length * 2;
            if (i4 > length) {
                length = i4;
            }
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, i3);
            this.itsCodeBuffer = bArr2;
        }
        this.itsCodeBufferTop = i4;
        return i3;
    }

    private void addSuperBlockStart(int i2) {
        if (GenerateStackMap) {
            int[] iArr = this.itsSuperBlockStarts;
            if (iArr == null) {
                this.itsSuperBlockStarts = new int[4];
            } else {
                int length = iArr.length;
                int i3 = this.itsSuperBlockStartsTop;
                if (length == i3) {
                    int[] iArr2 = new int[i3 * 2];
                    System.arraycopy(iArr, 0, iArr2, 0, i3);
                    this.itsSuperBlockStarts = iArr2;
                }
            }
            int[] iArr3 = this.itsSuperBlockStarts;
            int i4 = this.itsSuperBlockStartsTop;
            this.itsSuperBlockStartsTop = i4 + 1;
            iArr3[i4] = i2;
        }
    }

    private void addToCodeBuffer(int i2) {
        this.itsCodeBuffer[addReservedCodeSpace(1)] = (byte) i2;
    }

    private void addToCodeInt16(int i2) {
        putInt16(i2, this.itsCodeBuffer, addReservedCodeSpace(2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char arrayTypeToName(int i2) {
        switch (i2) {
            case 4:
                return 'Z';
            case 5:
                return 'C';
            case 6:
                return 'F';
            case 7:
                return 'D';
            case 8:
                return 'B';
            case 9:
                return 'S';
            case 10:
                return 'I';
            case 11:
                return 'J';
            default:
                throw new IllegalArgumentException("bad operand");
        }
    }

    private static void badStack(int i2) {
        String str;
        if (i2 < 0) {
            str = "Stack underflow: " + i2;
        } else {
            str = "Too big stack: " + i2;
        }
        throw new IllegalStateException(str);
    }

    private static String bytecodeStr(int i2) {
        return "";
    }

    private static String classDescriptorToInternalName(String str) {
        return str.substring(1, str.length() - 1);
    }

    public static String classNameToSignature(String str) {
        int length = str.length();
        int i2 = length + 1;
        int i3 = length + 2;
        char[] cArr = new char[i3];
        cArr[0] = 'L';
        cArr[i2] = ';';
        str.getChars(0, length, cArr, 1);
        for (int i4 = 1; i4 != i2; i4++) {
            if (cArr[i4] == '.') {
                cArr[i4] = IOUtils.DIR_SEPARATOR_UNIX;
            }
        }
        return new String(cArr, 0, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int[] createInitialLocals() {
        /*
            r10 = this;
            short r0 = r10.itsMaxLocals
            int[] r0 = new int[r0]
            org.mozilla.classfile.ClassFileMethod r1 = r10.itsCurrentMethod
            short r1 = r1.getFlags()
            r1 = r1 & 8
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L2c
            org.mozilla.classfile.ClassFileMethod r1 = r10.itsCurrentMethod
            java.lang.String r1 = r1.getName()
            java.lang.String r4 = "<init>"
            boolean r1 = r4.equals(r1)
            if (r1 == 0) goto L23
            r1 = 6
            r0[r3] = r1
        L21:
            r1 = r2
            goto L2d
        L23:
            short r1 = r10.itsThisClassIndex
            int r1 = org.mozilla.classfile.TypeInfo.OBJECT(r1)
            r0[r3] = r1
            goto L21
        L2c:
            r1 = r3
        L2d:
            org.mozilla.classfile.ClassFileMethod r4 = r10.itsCurrentMethod
            java.lang.String r4 = r4.getType()
            r5 = 40
            int r5 = r4.indexOf(r5)
            r6 = 41
            int r6 = r4.indexOf(r6)
            if (r5 != 0) goto Laf
            if (r6 < 0) goto Laf
            int r5 = r5 + r2
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
        L49:
            if (r5 >= r6) goto Lae
            char r8 = r4.charAt(r5)
            r9 = 70
            if (r8 == r9) goto L85
            r9 = 76
            if (r8 == r9) goto L75
            r9 = 83
            if (r8 == r9) goto L85
            r9 = 73
            if (r8 == r9) goto L85
            r9 = 74
            if (r8 == r9) goto L85
            r9 = 90
            if (r8 == r9) goto L85
            r9 = 91
            if (r8 == r9) goto L6f
            switch(r8) {
                case 66: goto L85;
                case 67: goto L85;
                case 68: goto L85;
                default: goto L6e;
            }
        L6e:
            goto L8e
        L6f:
            r7.append(r9)
            int r5 = r5 + 1
            goto L49
        L75:
            r8 = 59
            int r8 = r4.indexOf(r8, r5)
            int r8 = r8 + r2
            java.lang.String r5 = r4.substring(r5, r8)
            r7.append(r5)
            r5 = r8
            goto L8e
        L85:
            char r8 = r4.charAt(r5)
            r7.append(r8)
            int r5 = r5 + 1
        L8e:
            java.lang.String r8 = r7.toString()
            java.lang.String r8 = descriptorToInternalName(r8)
            org.mozilla.classfile.ConstantPool r9 = r10.itsConstantPool
            int r8 = org.mozilla.classfile.TypeInfo.fromType(r8, r9)
            int r9 = r1 + 1
            r0[r1] = r8
            boolean r8 = org.mozilla.classfile.TypeInfo.isTwoWords(r8)
            if (r8 == 0) goto La9
            int r1 = r1 + 2
            goto Laa
        La9:
            r1 = r9
        Laa:
            r7.setLength(r3)
            goto L49
        Lae:
            return r0
        Laf:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "bad method type"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.classfile.ClassFileWriter.createInitialLocals():int[]");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String descriptorToInternalName(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt == 'F') {
            return str;
        }
        if (cCharAt == 'L') {
            return classDescriptorToInternalName(str);
        }
        if (cCharAt == 'S' || cCharAt == 'V' || cCharAt == 'I' || cCharAt == 'J' || cCharAt == 'Z' || cCharAt == '[') {
            return str;
        }
        switch (cCharAt) {
            case 'B':
            case 'C':
            case 'D':
                return str;
            default:
                throw new IllegalArgumentException("bad descriptor:" + str);
        }
    }

    private void finalizeSuperBlockStarts() {
        if (GenerateStackMap) {
            for (int i2 = 0; i2 < this.itsExceptionTableTop; i2++) {
                addSuperBlockStart((short) getLabelPC(this.itsExceptionTable[i2].itsHandlerLabel));
            }
            Arrays.sort(this.itsSuperBlockStarts, 0, this.itsSuperBlockStartsTop);
            int i3 = this.itsSuperBlockStarts[0];
            int i4 = 1;
            for (int i5 = 1; i5 < this.itsSuperBlockStartsTop; i5++) {
                int[] iArr = this.itsSuperBlockStarts;
                int i6 = iArr[i5];
                if (i3 != i6) {
                    if (i4 != i5) {
                        iArr[i4] = i6;
                    }
                    i4++;
                    i3 = i6;
                }
            }
            this.itsSuperBlockStartsTop = i4;
            if (this.itsSuperBlockStarts[i4 - 1] == this.itsCodeBufferTop) {
                this.itsSuperBlockStartsTop = i4 - 1;
            }
        }
    }

    private void fixLabelGotos() throws RuntimeException {
        byte[] bArr = this.itsCodeBuffer;
        for (int i2 = 0; i2 < this.itsFixupTableTop; i2++) {
            long j2 = this.itsFixupTable[i2];
            int i3 = (int) (j2 >> 32);
            int i4 = (int) j2;
            int i5 = this.itsLabelTable[i3];
            if (i5 == -1) {
                throw new RuntimeException();
            }
            addSuperBlockStart(i5);
            int i6 = i4 - 1;
            this.itsJumpFroms.put(i5, i6);
            int i7 = i5 - i6;
            if (((short) i7) != i7) {
                throw new ClassFileFormatException("Program too complex: too big jump offset");
            }
            bArr[i4] = (byte) (i7 >> 8);
            bArr[i4 + 1] = (byte) i7;
        }
        this.itsFixupTableTop = 0;
    }

    static String getSlashedForm(String str) {
        return str.replace('.', IOUtils.DIR_SEPARATOR_UNIX);
    }

    private int getWriteSize() {
        if (this.itsSourceFileNameIndex != 0) {
            this.itsConstantPool.addUtf8("SourceFile");
        }
        int writeSize = this.itsConstantPool.getWriteSize() + 16 + (this.itsInterfaces.size() * 2) + 2;
        for (int i2 = 0; i2 < this.itsFields.size(); i2++) {
            writeSize += ((ClassFileField) this.itsFields.get(i2)).getWriteSize();
        }
        int writeSize2 = writeSize + 2;
        for (int i3 = 0; i3 < this.itsMethods.size(); i3++) {
            writeSize2 += ((ClassFileMethod) this.itsMethods.get(i3)).getWriteSize();
        }
        int i4 = writeSize2 + 2;
        if (this.itsSourceFileNameIndex != 0) {
            i4 = writeSize2 + 10;
        }
        return this.itsBootstrapMethods != null ? i4 + 8 + this.itsBootstrapMethodsLength : i4;
    }

    private static int opcodeCount(int i2) {
        if (i2 == 254 || i2 == 255) {
            return 0;
        }
        switch (i2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
                return 0;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
                return 1;
            case 132:
                return 2;
            case 170:
            case 171:
                return -1;
            default:
                switch (i2) {
                    case 187:
                    case 188:
                    case 189:
                    case 192:
                    case 193:
                    case 198:
                    case 199:
                    case 200:
                    case 201:
                        return 1;
                    case 190:
                    case 191:
                    case 194:
                    case 195:
                    case 196:
                    case 202:
                        return 0;
                    case 197:
                        return 2;
                    default:
                        throw new IllegalArgumentException("Bad opcode: " + i2);
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0034 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0035 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int opcodeLength(int r3, boolean r4) {
        /*
            r0 = 254(0xfe, float:3.56E-43)
            if (r3 == r0) goto L36
            r0 = 255(0xff, float:3.57E-43)
            if (r3 == r0) goto L36
            r0 = 2
            r1 = 5
            r2 = 3
            switch(r3) {
                case 0: goto L36;
                case 1: goto L36;
                case 2: goto L36;
                case 3: goto L36;
                case 4: goto L36;
                case 5: goto L36;
                case 6: goto L36;
                case 7: goto L36;
                case 8: goto L36;
                case 9: goto L36;
                case 10: goto L36;
                case 11: goto L36;
                case 12: goto L36;
                case 13: goto L36;
                case 14: goto L36;
                case 15: goto L36;
                case 16: goto L35;
                case 17: goto L34;
                case 18: goto L35;
                case 19: goto L34;
                case 20: goto L34;
                case 21: goto L30;
                case 22: goto L30;
                case 23: goto L30;
                case 24: goto L30;
                case 25: goto L30;
                case 26: goto L36;
                case 27: goto L36;
                case 28: goto L36;
                case 29: goto L36;
                case 30: goto L36;
                case 31: goto L36;
                case 32: goto L36;
                case 33: goto L36;
                case 34: goto L36;
                case 35: goto L36;
                case 36: goto L36;
                case 37: goto L36;
                case 38: goto L36;
                case 39: goto L36;
                case 40: goto L36;
                case 41: goto L36;
                case 42: goto L36;
                case 43: goto L36;
                case 44: goto L36;
                case 45: goto L36;
                case 46: goto L36;
                case 47: goto L36;
                case 48: goto L36;
                case 49: goto L36;
                case 50: goto L36;
                case 51: goto L36;
                case 52: goto L36;
                case 53: goto L36;
                case 54: goto L30;
                case 55: goto L30;
                case 56: goto L30;
                case 57: goto L30;
                case 58: goto L30;
                case 59: goto L36;
                case 60: goto L36;
                case 61: goto L36;
                case 62: goto L36;
                case 63: goto L36;
                case 64: goto L36;
                case 65: goto L36;
                case 66: goto L36;
                case 67: goto L36;
                case 68: goto L36;
                case 69: goto L36;
                case 70: goto L36;
                case 71: goto L36;
                case 72: goto L36;
                case 73: goto L36;
                case 74: goto L36;
                case 75: goto L36;
                case 76: goto L36;
                case 77: goto L36;
                case 78: goto L36;
                case 79: goto L36;
                case 80: goto L36;
                case 81: goto L36;
                case 82: goto L36;
                case 83: goto L36;
                case 84: goto L36;
                case 85: goto L36;
                case 86: goto L36;
                case 87: goto L36;
                case 88: goto L36;
                case 89: goto L36;
                case 90: goto L36;
                case 91: goto L36;
                case 92: goto L36;
                case 93: goto L36;
                case 94: goto L36;
                case 95: goto L36;
                case 96: goto L36;
                case 97: goto L36;
                case 98: goto L36;
                case 99: goto L36;
                case 100: goto L36;
                case 101: goto L36;
                case 102: goto L36;
                case 103: goto L36;
                case 104: goto L36;
                case 105: goto L36;
                case 106: goto L36;
                case 107: goto L36;
                case 108: goto L36;
                case 109: goto L36;
                case 110: goto L36;
                case 111: goto L36;
                case 112: goto L36;
                case 113: goto L36;
                case 114: goto L36;
                case 115: goto L36;
                case 116: goto L36;
                case 117: goto L36;
                case 118: goto L36;
                case 119: goto L36;
                case 120: goto L36;
                case 121: goto L36;
                case 122: goto L36;
                case 123: goto L36;
                case 124: goto L36;
                case 125: goto L36;
                case 126: goto L36;
                case 127: goto L36;
                case 128: goto L36;
                case 129: goto L36;
                case 130: goto L36;
                case 131: goto L36;
                case 132: goto L2b;
                case 133: goto L36;
                case 134: goto L36;
                case 135: goto L36;
                case 136: goto L36;
                case 137: goto L36;
                case 138: goto L36;
                case 139: goto L36;
                case 140: goto L36;
                case 141: goto L36;
                case 142: goto L36;
                case 143: goto L36;
                case 144: goto L36;
                case 145: goto L36;
                case 146: goto L36;
                case 147: goto L36;
                case 148: goto L36;
                case 149: goto L36;
                case 150: goto L36;
                case 151: goto L36;
                case 152: goto L36;
                case 153: goto L34;
                case 154: goto L34;
                case 155: goto L34;
                case 156: goto L34;
                case 157: goto L34;
                case 158: goto L34;
                case 159: goto L34;
                case 160: goto L34;
                case 161: goto L34;
                case 162: goto L34;
                case 163: goto L34;
                case 164: goto L34;
                case 165: goto L34;
                case 166: goto L34;
                case 167: goto L34;
                case 168: goto L34;
                case 169: goto L30;
                default: goto Le;
            }
        Le:
            switch(r3) {
                case 172: goto L36;
                case 173: goto L36;
                case 174: goto L36;
                case 175: goto L36;
                case 176: goto L36;
                case 177: goto L36;
                case 178: goto L34;
                case 179: goto L34;
                case 180: goto L34;
                case 181: goto L34;
                case 182: goto L34;
                case 183: goto L34;
                case 184: goto L34;
                case 185: goto L2a;
                case 186: goto L2a;
                case 187: goto L34;
                case 188: goto L35;
                case 189: goto L34;
                case 190: goto L36;
                case 191: goto L36;
                case 192: goto L34;
                case 193: goto L34;
                case 194: goto L36;
                case 195: goto L36;
                case 196: goto L36;
                case 197: goto L28;
                case 198: goto L34;
                case 199: goto L34;
                case 200: goto L2a;
                case 201: goto L2a;
                case 202: goto L36;
                default: goto L11;
            }
        L11:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Bad opcode: "
            r0.append(r1)
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            r4.<init>(r3)
            throw r4
        L28:
            r3 = 4
            return r3
        L2a:
            return r1
        L2b:
            if (r4 == 0) goto L2e
            goto L2f
        L2e:
            r1 = r2
        L2f:
            return r1
        L30:
            if (r4 == 0) goto L33
            r0 = r2
        L33:
            return r0
        L34:
            return r2
        L35:
            return r0
        L36:
            r3 = 1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.classfile.ClassFileWriter.opcodeLength(int, boolean):int");
    }

    static int putInt16(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) (i2 >>> 8);
        bArr[i3 + 1] = (byte) i2;
        return i3 + 2;
    }

    static int putInt32(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) (i2 >>> 24);
        bArr[i3 + 1] = (byte) (i2 >>> 16);
        bArr[i3 + 2] = (byte) (i2 >>> 8);
        bArr[i3 + 3] = (byte) i2;
        return i3 + 4;
    }

    static int putInt64(long j2, byte[] bArr, int i2) {
        return putInt32((int) j2, bArr, putInt32((int) (j2 >>> 32), bArr, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to find 'out' block for switch in B:21:0x0044. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b1 A[FALL_THROUGH, PHI: r6
      0x00b1: PHI (r6v3 int) = (r6v2 int), (r6v2 int), (r6v2 int), (r6v2 int), (r6v6 int), (r6v2 int), (r6v2 int), (r6v2 int) binds: [B:47:0x0098, B:48:0x009a, B:49:0x009c, B:52:0x00a2, B:58:0x00af, B:54:0x00a6, B:55:0x00a8, B:56:0x00aa] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b1 A[PHI: r6
      0x00b1: PHI (r6v3 int) = (r6v2 int), (r6v2 int), (r6v2 int), (r6v2 int), (r6v6 int), (r6v2 int), (r6v2 int), (r6v2 int) binds: [B:47:0x0098, B:48:0x009a, B:49:0x009c, B:52:0x00a2, B:58:0x00af, B:54:0x00a6, B:55:0x00a8, B:56:0x00aa] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int sizeOfParameters(java.lang.String r16) {
        /*
            Method dump skipped, instructions count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.classfile.ClassFileWriter.sizeOfParameters(java.lang.String):int");
    }

    private static int stackChange(int i2) {
        if (i2 == 254 || i2 == 255) {
            return 0;
        }
        switch (i2) {
            case 0:
            case 47:
            case 49:
            case 95:
            case 116:
            case 117:
            case 118:
            case 119:
            case 132:
            case 134:
            case 138:
            case 139:
            case 143:
            case 145:
            case 146:
            case 147:
            case 167:
            case 169:
            case 177:
            case 178:
            case 179:
            case 184:
            case 186:
            case 188:
            case 189:
            case 190:
            case 192:
            case 193:
            case 196:
            case 200:
            case 202:
                return 0;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
            case 13:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 34:
            case 35:
            case 36:
            case 37:
            case 42:
            case 43:
            case 44:
            case 45:
            case 89:
            case 90:
            case 91:
            case 133:
            case 135:
            case 140:
            case 141:
            case 168:
            case 187:
            case 197:
            case 201:
                return 1;
            case 9:
            case 10:
            case 14:
            case 15:
            case 20:
            case 22:
            case 24:
            case 30:
            case 31:
            case 32:
            case 33:
            case 38:
            case 39:
            case 40:
            case 41:
            case 92:
            case 93:
            case 94:
                return 2;
            case 46:
            case 48:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 56:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 67:
            case 68:
            case 69:
            case 70:
            case 75:
            case 76:
            case 77:
            case 78:
            case 87:
            case 96:
            case 98:
            case 100:
            case 102:
            case 104:
            case 106:
            case 108:
            case 110:
            case 112:
            case 114:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 128:
            case 130:
            case 136:
            case 137:
            case 142:
            case 144:
            case 149:
            case 150:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 170:
            case 171:
            case 172:
            case 174:
            case 176:
            case 180:
            case 181:
            case 182:
            case 183:
            case 185:
            case 191:
            case 194:
            case 195:
            case 198:
            case 199:
                return -1;
            case 55:
            case 57:
            case 63:
            case 64:
            case 65:
            case 66:
            case 71:
            case 72:
            case 73:
            case 74:
            case 88:
            case 97:
            case 99:
            case 101:
            case 103:
            case 105:
            case 107:
            case 109:
            case 111:
            case 113:
            case 115:
            case 127:
            case 129:
            case 131:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 173:
            case 175:
                return -2;
            case 79:
            case 81:
            case 83:
            case 84:
            case 85:
            case 86:
            case 148:
            case 151:
            case 152:
                return -3;
            case 80:
            case 82:
                return -4;
            default:
                throw new IllegalArgumentException("Bad opcode: " + i2);
        }
    }

    private void xop(int i2, int i3, int i4) throws RuntimeException {
        if (i4 == 0) {
            add(i2);
            return;
        }
        if (i4 == 1) {
            add(i2 + 1);
            return;
        }
        if (i4 == 2) {
            add(i2 + 2);
        } else if (i4 != 3) {
            add(i3, i4);
        } else {
            add(i2 + 3);
        }
    }

    public int acquireLabel() {
        int i2 = this.itsLabelTableTop;
        int[] iArr = this.itsLabelTable;
        if (iArr == null || i2 == iArr.length) {
            if (iArr == null) {
                this.itsLabelTable = new int[32];
            } else {
                int[] iArr2 = new int[iArr.length * 2];
                System.arraycopy(iArr, 0, iArr2, 0, i2);
                this.itsLabelTable = iArr2;
            }
        }
        this.itsLabelTableTop = i2 + 1;
        this.itsLabelTable[i2] = -1;
        return i2 | Integer.MIN_VALUE;
    }

    public void add(int i2) {
        if (opcodeCount(i2) != 0) {
            throw new IllegalArgumentException("Unexpected operands");
        }
        int iStackChange = this.itsStackTop + stackChange(i2);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        addToCodeBuffer(i2);
        short s2 = (short) iStackChange;
        this.itsStackTop = s2;
        if (iStackChange > this.itsMaxStack) {
            this.itsMaxStack = s2;
        }
        if (i2 == 191) {
            addSuperBlockStart(this.itsCodeBufferTop);
        }
    }

    public void addALoad(int i2) throws RuntimeException {
        xop(42, 25, i2);
    }

    public void addAStore(int i2) throws RuntimeException {
        xop(75, 58, i2);
    }

    public void addDLoad(int i2) throws RuntimeException {
        xop(38, 24, i2);
    }

    public void addDStore(int i2) throws RuntimeException {
        xop(71, 57, i2);
    }

    public void addExceptionHandler(int i2, int i3, int i4, String str) {
        if ((i2 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Bad startLabel");
        }
        if ((i3 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Bad endLabel");
        }
        if ((i4 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Bad handlerLabel");
        }
        ExceptionTableEntry exceptionTableEntry = new ExceptionTableEntry(i2, i3, i4, str == null ? (short) 0 : this.itsConstantPool.addClass(str));
        int i5 = this.itsExceptionTableTop;
        if (i5 == 0) {
            this.itsExceptionTable = new ExceptionTableEntry[4];
        } else {
            ExceptionTableEntry[] exceptionTableEntryArr = this.itsExceptionTable;
            if (i5 == exceptionTableEntryArr.length) {
                ExceptionTableEntry[] exceptionTableEntryArr2 = new ExceptionTableEntry[i5 * 2];
                System.arraycopy(exceptionTableEntryArr, 0, exceptionTableEntryArr2, 0, i5);
                this.itsExceptionTable = exceptionTableEntryArr2;
            }
        }
        this.itsExceptionTable[i5] = exceptionTableEntry;
        this.itsExceptionTableTop = i5 + 1;
    }

    public void addFLoad(int i2) throws RuntimeException {
        xop(34, 23, i2);
    }

    public void addFStore(int i2) throws RuntimeException {
        xop(67, 56, i2);
    }

    public void addField(String str, String str2, short s2) {
        this.itsFields.add(new ClassFileField(this.itsConstantPool.addUtf8(str), this.itsConstantPool.addUtf8(str2), s2));
    }

    public void addILoad(int i2) throws RuntimeException {
        xop(26, 21, i2);
    }

    public void addIStore(int i2) throws RuntimeException {
        xop(59, 54, i2);
    }

    public void addInterface(String str) {
        this.itsInterfaces.add(Short.valueOf(this.itsConstantPool.addClass(str)));
    }

    public void addInvoke(int i2, String str, String str2, String str3) {
        int iSizeOfParameters = sizeOfParameters(str3);
        int i3 = iSizeOfParameters >>> 16;
        int iStackChange = this.itsStackTop + ((short) iSizeOfParameters) + stackChange(i2);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        switch (i2) {
            case 182:
            case 183:
            case 184:
            case 185:
                addToCodeBuffer(i2);
                if (i2 == 185) {
                    addToCodeInt16(this.itsConstantPool.addInterfaceMethodRef(str, str2, str3));
                    addToCodeBuffer(i3 + 1);
                    addToCodeBuffer(0);
                } else {
                    addToCodeInt16(this.itsConstantPool.addMethodRef(str, str2, str3));
                }
                short s2 = (short) iStackChange;
                this.itsStackTop = s2;
                if (iStackChange > this.itsMaxStack) {
                    this.itsMaxStack = s2;
                    return;
                }
                return;
            default:
                throw new IllegalArgumentException("bad opcode for method reference");
        }
    }

    public void addInvokeDynamic(String str, String str2, MHandle mHandle, Object... objArr) {
        if (MajorVersion < 51) {
            throw new RuntimeException("Please build and run with JDK 1.7 for invokedynamic support");
        }
        int iSizeOfParameters = this.itsStackTop + ((short) sizeOfParameters(str2));
        if (iSizeOfParameters < 0 || 32767 < iSizeOfParameters) {
            badStack(iSizeOfParameters);
        }
        BootstrapEntry bootstrapEntry = new BootstrapEntry(mHandle, objArr);
        if (this.itsBootstrapMethods == null) {
            this.itsBootstrapMethods = new ObjArray();
        }
        int iIndexOf = this.itsBootstrapMethods.indexOf(bootstrapEntry);
        if (iIndexOf == -1) {
            iIndexOf = this.itsBootstrapMethods.size();
            this.itsBootstrapMethods.add(bootstrapEntry);
            this.itsBootstrapMethodsLength += bootstrapEntry.code.length;
        }
        short sAddInvokeDynamic = this.itsConstantPool.addInvokeDynamic(str, str2, iIndexOf);
        addToCodeBuffer(186);
        addToCodeInt16(sAddInvokeDynamic);
        addToCodeInt16(0);
        short s2 = (short) iSizeOfParameters;
        this.itsStackTop = s2;
        if (iSizeOfParameters > this.itsMaxStack) {
            this.itsMaxStack = s2;
        }
    }

    public void addLLoad(int i2) throws RuntimeException {
        xop(30, 22, i2);
    }

    public void addLStore(int i2) throws RuntimeException {
        xop(63, 55, i2);
    }

    public void addLineNumberEntry(short s2) {
        if (this.itsCurrentMethod == null) {
            throw new IllegalArgumentException("No method to stop");
        }
        int i2 = this.itsLineNumberTableTop;
        if (i2 == 0) {
            this.itsLineNumberTable = new int[16];
        } else {
            int[] iArr = this.itsLineNumberTable;
            if (i2 == iArr.length) {
                int[] iArr2 = new int[i2 * 2];
                System.arraycopy(iArr, 0, iArr2, 0, i2);
                this.itsLineNumberTable = iArr2;
            }
        }
        this.itsLineNumberTable[i2] = (this.itsCodeBufferTop << 16) + s2;
        this.itsLineNumberTableTop = i2 + 1;
    }

    public void addLoadConstant(int i2) throws RuntimeException {
        if (i2 == 0) {
            add(3);
            return;
        }
        if (i2 == 1) {
            add(4);
            return;
        }
        if (i2 == 2) {
            add(5);
            return;
        }
        if (i2 == 3) {
            add(6);
            return;
        }
        if (i2 == 4) {
            add(7);
        } else if (i2 != 5) {
            add(18, this.itsConstantPool.addConstant(i2));
        } else {
            add(8);
        }
    }

    public void addLoadThis() {
        add(42);
    }

    public void addPush(int i2) throws RuntimeException {
        byte b2 = (byte) i2;
        if (b2 != i2) {
            short s2 = (short) i2;
            if (s2 == i2) {
                add(17, s2);
                return;
            } else {
                addLoadConstant(i2);
                return;
            }
        }
        if (i2 == -1) {
            add(2);
        } else if (i2 < 0 || i2 > 5) {
            add(16, b2);
        } else {
            add((byte) (i2 + 3));
        }
    }

    public int addTableSwitch(int i2, int i3) {
        if (i2 > i3) {
            throw new ClassFileFormatException("Bad bounds: " + i2 + ' ' + i3);
        }
        int iStackChange = this.itsStackTop + stackChange(170);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        int i4 = (~this.itsCodeBufferTop) & 3;
        int iAddReservedCodeSpace = addReservedCodeSpace(i4 + 1 + (((i3 - i2) + 4) * 4));
        int i5 = iAddReservedCodeSpace + 1;
        this.itsCodeBuffer[iAddReservedCodeSpace] = -86;
        while (i4 != 0) {
            this.itsCodeBuffer[i5] = 0;
            i4--;
            i5++;
        }
        putInt32(i3, this.itsCodeBuffer, putInt32(i2, this.itsCodeBuffer, i5 + 4));
        short s2 = (short) iStackChange;
        this.itsStackTop = s2;
        if (iStackChange > this.itsMaxStack) {
            this.itsMaxStack = s2;
        }
        return iAddReservedCodeSpace;
    }

    public void addVariableDescriptor(String str, String str2, int i2, int i3) {
        int[] iArr = {this.itsConstantPool.addUtf8(str), this.itsConstantPool.addUtf8(str2), i2, i3};
        if (this.itsVarDescriptors == null) {
            this.itsVarDescriptors = new ObjArray();
        }
        this.itsVarDescriptors.add(iArr);
    }

    public void adjustStackTop(int i2) {
        int i3 = this.itsStackTop + i2;
        if (i3 < 0 || 32767 < i3) {
            badStack(i3);
        }
        short s2 = (short) i3;
        this.itsStackTop = s2;
        if (i3 > this.itsMaxStack) {
            this.itsMaxStack = s2;
        }
    }

    final char[] getCharBuffer(int i2) {
        char[] cArr = this.tmpCharBuffer;
        if (i2 > cArr.length) {
            int length = cArr.length * 2;
            if (i2 <= length) {
                i2 = length;
            }
            this.tmpCharBuffer = new char[i2];
        }
        return this.tmpCharBuffer;
    }

    public final String getClassName() {
        return this.generatedClassName;
    }

    public int getCurrentCodeOffset() {
        return this.itsCodeBufferTop;
    }

    public int getLabelPC(int i2) {
        if (i2 >= 0) {
            throw new IllegalArgumentException("Bad label, no biscuit");
        }
        int i3 = i2 & Integer.MAX_VALUE;
        if (i3 < this.itsLabelTableTop) {
            return this.itsLabelTable[i3];
        }
        throw new IllegalArgumentException("Bad label");
    }

    public short getStackTop() {
        return this.itsStackTop;
    }

    public boolean isUnderStringSizeLimit(String str) {
        return this.itsConstantPool.isUnderUtfEncodingLimit(str);
    }

    public void markHandler(int i2) {
        this.itsStackTop = (short) 1;
        markLabel(i2);
    }

    public void markLabel(int i2) {
        if (i2 >= 0) {
            throw new IllegalArgumentException("Bad label, no biscuit");
        }
        int i3 = i2 & Integer.MAX_VALUE;
        if (i3 > this.itsLabelTableTop) {
            throw new IllegalArgumentException("Bad label");
        }
        int[] iArr = this.itsLabelTable;
        if (iArr[i3] != -1) {
            throw new IllegalStateException("Can only mark label once");
        }
        iArr[i3] = this.itsCodeBufferTop;
    }

    public final void markTableSwitchCase(int i2, int i3) throws RuntimeException {
        addSuperBlockStart(this.itsCodeBufferTop);
        this.itsJumpFroms.put(this.itsCodeBufferTop, i2);
        setTableSwitchJump(i2, i3, this.itsCodeBufferTop);
    }

    public final void markTableSwitchDefault(int i2) throws RuntimeException {
        addSuperBlockStart(this.itsCodeBufferTop);
        this.itsJumpFroms.put(this.itsCodeBufferTop, i2);
        setTableSwitchJump(i2, -1, this.itsCodeBufferTop);
    }

    public void setFlags(short s2) {
        this.itsFlags = s2;
    }

    public void setStackTop(short s2) {
        this.itsStackTop = s2;
    }

    public void setTableSwitchJump(int i2, int i3, int i4) {
        int i5;
        if (i4 < 0 || i4 > (i5 = this.itsCodeBufferTop)) {
            throw new IllegalArgumentException("Bad jump target: " + i4);
        }
        if (i3 < -1) {
            throw new IllegalArgumentException("Bad case index: " + i3);
        }
        int i6 = (~i2) & 3;
        int i7 = i3 < 0 ? i2 + 1 + i6 : i2 + 1 + i6 + ((i3 + 3) * 4);
        if (i2 < 0 || i2 > ((i5 - 16) - i6) - 1) {
            throw new IllegalArgumentException(i2 + " is outside a possible range of tableswitch in already generated code");
        }
        byte[] bArr = this.itsCodeBuffer;
        if ((bArr[i2] & 255) != 170) {
            throw new IllegalArgumentException(i2 + " is not offset of tableswitch statement");
        }
        if (i7 >= 0 && i7 + 4 <= i5) {
            putInt32(i4 - i2, bArr, i7);
            return;
        }
        throw new ClassFileFormatException("Too big case index: " + i3);
    }

    public void startMethod(String str, String str2, short s2) {
        this.itsCurrentMethod = new ClassFileMethod(str, this.itsConstantPool.addUtf8(str), str2, this.itsConstantPool.addUtf8(str2), s2);
        this.itsJumpFroms = new UintMap();
        this.itsMethods.add(this.itsCurrentMethod);
        addSuperBlockStart(0);
    }

    public void stopMethod(short s2) throws RuntimeException {
        StackMapTable stackMapTable;
        int iPutInt16;
        int iComputeWriteSize;
        if (this.itsCurrentMethod == null) {
            throw new IllegalStateException("No method to stop");
        }
        fixLabelGotos();
        this.itsMaxLocals = s2;
        if (GenerateStackMap) {
            finalizeSuperBlockStarts();
            stackMapTable = new StackMapTable();
            stackMapTable.generate();
        } else {
            stackMapTable = null;
        }
        int i2 = this.itsLineNumberTable != null ? (this.itsLineNumberTableTop * 4) + 8 : 0;
        ObjArray objArray = this.itsVarDescriptors;
        int size = objArray != null ? (objArray.size() * 10) + 8 : 0;
        int i3 = (stackMapTable == null || (iComputeWriteSize = stackMapTable.computeWriteSize()) <= 0) ? 0 : iComputeWriteSize + 6;
        int i4 = this.itsCodeBufferTop + 16 + (this.itsExceptionTableTop * 8) + 2 + i2 + size + i3;
        if (i4 > 65536) {
            throw new ClassFileFormatException("generated bytecode for method exceeds 64K limit.");
        }
        byte[] bArr = new byte[i4];
        int iPutInt32 = putInt32(this.itsCodeBufferTop, bArr, putInt16(this.itsMaxLocals, bArr, putInt16(this.itsMaxStack, bArr, putInt32(i4 - 6, bArr, putInt16(this.itsConstantPool.addUtf8("Code"), bArr, 0)))));
        System.arraycopy(this.itsCodeBuffer, 0, bArr, iPutInt32, this.itsCodeBufferTop);
        int i5 = iPutInt32 + this.itsCodeBufferTop;
        int i6 = this.itsExceptionTableTop;
        if (i6 > 0) {
            iPutInt16 = putInt16(i6, bArr, i5);
            for (int i7 = 0; i7 < this.itsExceptionTableTop; i7++) {
                ExceptionTableEntry exceptionTableEntry = this.itsExceptionTable[i7];
                short labelPC = (short) getLabelPC(exceptionTableEntry.itsStartLabel);
                short labelPC2 = (short) getLabelPC(exceptionTableEntry.itsEndLabel);
                short labelPC3 = (short) getLabelPC(exceptionTableEntry.itsHandlerLabel);
                short s3 = exceptionTableEntry.itsCatchType;
                if (labelPC == -1) {
                    throw new IllegalStateException("start label not defined");
                }
                if (labelPC2 == -1) {
                    throw new IllegalStateException("end label not defined");
                }
                if (labelPC3 == -1) {
                    throw new IllegalStateException("handler label not defined");
                }
                iPutInt16 = putInt16(s3, bArr, putInt16(labelPC3, bArr, putInt16(labelPC2, bArr, putInt16(labelPC, bArr, iPutInt16))));
            }
        } else {
            iPutInt16 = putInt16(0, bArr, i5);
        }
        int i8 = this.itsLineNumberTable != null ? 1 : 0;
        if (this.itsVarDescriptors != null) {
            i8++;
        }
        if (i3 > 0) {
            i8++;
        }
        int iPutInt162 = putInt16(i8, bArr, iPutInt16);
        if (this.itsLineNumberTable != null) {
            iPutInt162 = putInt16(this.itsLineNumberTableTop, bArr, putInt32((this.itsLineNumberTableTop * 4) + 2, bArr, putInt16(this.itsConstantPool.addUtf8("LineNumberTable"), bArr, iPutInt162)));
            for (int i9 = 0; i9 < this.itsLineNumberTableTop; i9++) {
                iPutInt162 = putInt32(this.itsLineNumberTable[i9], bArr, iPutInt162);
            }
        }
        if (this.itsVarDescriptors != null) {
            int iPutInt163 = putInt16(this.itsConstantPool.addUtf8("LocalVariableTable"), bArr, iPutInt162);
            int size2 = this.itsVarDescriptors.size();
            iPutInt162 = putInt16(size2, bArr, putInt32((size2 * 10) + 2, bArr, iPutInt163));
            for (int i10 = 0; i10 < size2; i10++) {
                int[] iArr = (int[]) this.itsVarDescriptors.get(i10);
                int i11 = iArr[0];
                int i12 = iArr[1];
                int i13 = iArr[2];
                iPutInt162 = putInt16(iArr[3], bArr, putInt16(i12, bArr, putInt16(i11, bArr, putInt16(this.itsCodeBufferTop - i13, bArr, putInt16(i13, bArr, iPutInt162)))));
            }
        }
        if (i3 > 0) {
            stackMapTable.write(bArr, putInt16(this.itsConstantPool.addUtf8("StackMapTable"), bArr, iPutInt162));
        }
        this.itsCurrentMethod.setCodeAttribute(bArr);
        this.itsExceptionTable = null;
        this.itsExceptionTableTop = 0;
        this.itsLineNumberTableTop = 0;
        this.itsCodeBufferTop = 0;
        this.itsCurrentMethod = null;
        this.itsMaxStack = (short) 0;
        this.itsStackTop = (short) 0;
        this.itsLabelTableTop = 0;
        this.itsFixupTableTop = 0;
        this.itsVarDescriptors = null;
        this.itsSuperBlockStarts = null;
        this.itsSuperBlockStartsTop = 0;
        this.itsJumpFroms = null;
    }

    public byte[] toByteArray() {
        short sAddUtf8;
        int i2;
        short sAddUtf82;
        if (this.itsBootstrapMethods != null) {
            sAddUtf8 = this.itsConstantPool.addUtf8("BootstrapMethods");
            i2 = 1;
        } else {
            sAddUtf8 = 0;
            i2 = 0;
        }
        if (this.itsSourceFileNameIndex != 0) {
            i2++;
            sAddUtf82 = this.itsConstantPool.addUtf8("SourceFile");
        } else {
            sAddUtf82 = 0;
        }
        int writeSize = getWriteSize();
        byte[] bArr = new byte[writeSize];
        int iPutInt16 = putInt16(this.itsInterfaces.size(), bArr, putInt16(this.itsSuperClassIndex, bArr, putInt16(this.itsThisClassIndex, bArr, putInt16(this.itsFlags, bArr, this.itsConstantPool.write(bArr, putInt16(MajorVersion, bArr, putInt16(MinorVersion, bArr, putInt32(FileHeaderConstant, bArr, 0))))))));
        for (int i3 = 0; i3 < this.itsInterfaces.size(); i3++) {
            iPutInt16 = putInt16(((Short) this.itsInterfaces.get(i3)).shortValue(), bArr, iPutInt16);
        }
        int iPutInt162 = putInt16(this.itsFields.size(), bArr, iPutInt16);
        for (int i4 = 0; i4 < this.itsFields.size(); i4++) {
            iPutInt162 = ((ClassFileField) this.itsFields.get(i4)).write(bArr, iPutInt162);
        }
        int iPutInt163 = putInt16(this.itsMethods.size(), bArr, iPutInt162);
        for (int i5 = 0; i5 < this.itsMethods.size(); i5++) {
            iPutInt163 = ((ClassFileMethod) this.itsMethods.get(i5)).write(bArr, iPutInt163);
        }
        int iPutInt164 = putInt16(i2, bArr, iPutInt163);
        if (this.itsBootstrapMethods != null) {
            iPutInt164 = putInt16(this.itsBootstrapMethods.size(), bArr, putInt32(this.itsBootstrapMethodsLength + 2, bArr, putInt16(sAddUtf8, bArr, iPutInt164)));
            for (int i6 = 0; i6 < this.itsBootstrapMethods.size(); i6++) {
                BootstrapEntry bootstrapEntry = (BootstrapEntry) this.itsBootstrapMethods.get(i6);
                byte[] bArr2 = bootstrapEntry.code;
                System.arraycopy(bArr2, 0, bArr, iPutInt164, bArr2.length);
                iPutInt164 += bootstrapEntry.code.length;
            }
        }
        if (this.itsSourceFileNameIndex != 0) {
            iPutInt164 = putInt16(this.itsSourceFileNameIndex, bArr, putInt32(2, bArr, putInt16(sAddUtf82, bArr, iPutInt164)));
        }
        if (iPutInt164 == writeSize) {
            return bArr;
        }
        throw new RuntimeException();
    }

    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(toByteArray());
    }

    public void addField(String str, String str2, short s2, int i2) {
        ClassFileField classFileField = new ClassFileField(this.itsConstantPool.addUtf8(str), this.itsConstantPool.addUtf8(str2), s2);
        classFileField.setAttributes(this.itsConstantPool.addUtf8("ConstantValue"), (short) 0, (short) 0, this.itsConstantPool.addConstant(i2));
        this.itsFields.add(classFileField);
    }

    public final void markTableSwitchCase(int i2, int i3, int i4) throws RuntimeException {
        if (i4 >= 0 && i4 <= this.itsMaxStack) {
            this.itsStackTop = (short) i4;
            addSuperBlockStart(this.itsCodeBufferTop);
            this.itsJumpFroms.put(this.itsCodeBufferTop, i2);
            setTableSwitchJump(i2, i3, this.itsCodeBufferTop);
            return;
        }
        throw new IllegalArgumentException("Bad stack index: " + i4);
    }

    public void addPush(boolean z2) {
        add(z2 ? 4 : 3);
    }

    public void addPush(long j2) throws RuntimeException {
        int i2 = (int) j2;
        if (i2 == j2) {
            addPush(i2);
            add(133);
        } else {
            addLoadConstant(j2);
        }
    }

    public void markLabel(int i2, short s2) {
        markLabel(i2);
        this.itsStackTop = s2;
    }

    public void addLoadConstant(long j2) throws RuntimeException {
        add(20, this.itsConstantPool.addConstant(j2));
    }

    public void addLoadConstant(float f2) throws RuntimeException {
        add(18, this.itsConstantPool.addConstant(f2));
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0044 A[FALL_THROUGH] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ba A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void add(int r6, int r7) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 380
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.classfile.ClassFileWriter.add(int, int):void");
    }

    public void addLoadConstant(double d2) throws RuntimeException {
        add(20, this.itsConstantPool.addConstant(d2));
    }

    public void addPush(double d2) throws RuntimeException {
        if (d2 == 0.0d) {
            add(14);
            if (1.0d / d2 < 0.0d) {
                add(119);
                return;
            }
            return;
        }
        if (d2 != 1.0d && d2 != -1.0d) {
            addLoadConstant(d2);
            return;
        }
        add(15);
        if (d2 < 0.0d) {
            add(119);
        }
    }

    public void addField(String str, String str2, short s2, long j2) {
        ClassFileField classFileField = new ClassFileField(this.itsConstantPool.addUtf8(str), this.itsConstantPool.addUtf8(str2), s2);
        classFileField.setAttributes(this.itsConstantPool.addUtf8("ConstantValue"), (short) 0, (short) 2, this.itsConstantPool.addConstant(j2));
        this.itsFields.add(classFileField);
    }

    public void addLoadConstant(String str) throws RuntimeException {
        add(18, this.itsConstantPool.addConstant(str));
    }

    public void addPush(String str) throws RuntimeException {
        int length = str.length();
        int i2 = 0;
        int utfEncodingLimit = this.itsConstantPool.getUtfEncodingLimit(str, 0, length);
        if (utfEncodingLimit == length) {
            addLoadConstant(str);
            return;
        }
        add(187, "java/lang/StringBuilder");
        add(89);
        addPush(length);
        addInvoke(183, "java/lang/StringBuilder", "<init>", "(I)V");
        while (true) {
            add(89);
            addLoadConstant(str.substring(i2, utfEncodingLimit));
            addInvoke(182, "java/lang/StringBuilder", RequestParameters.SUBRESOURCE_APPEND, "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
            add(87);
            if (utfEncodingLimit == length) {
                addInvoke(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
                return;
            } else {
                i2 = utfEncodingLimit;
                utfEncodingLimit = this.itsConstantPool.getUtfEncodingLimit(str, utfEncodingLimit, length);
            }
        }
    }

    public void addField(String str, String str2, short s2, double d2) {
        ClassFileField classFileField = new ClassFileField(this.itsConstantPool.addUtf8(str), this.itsConstantPool.addUtf8(str2), s2);
        classFileField.setAttributes(this.itsConstantPool.addUtf8("ConstantValue"), (short) 0, (short) 2, this.itsConstantPool.addConstant(d2));
        this.itsFields.add(classFileField);
    }

    public void add(int i2, int i3, int i4) {
        int iStackChange = this.itsStackTop + stackChange(i2);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        if (i2 == 132) {
            if (i3 < 0 || i3 >= 65536) {
                throw new ClassFileFormatException("out of range variable");
            }
            if (i4 < 0 || i4 >= 65536) {
                throw new ClassFileFormatException("out of range increment");
            }
            if (i3 <= 255 && i4 >= -128 && i4 <= 127) {
                addToCodeBuffer(132);
                addToCodeBuffer(i3);
                addToCodeBuffer(i4);
            } else {
                addToCodeBuffer(196);
                addToCodeBuffer(132);
                addToCodeInt16(i3);
                addToCodeInt16(i4);
            }
        } else {
            if (i2 != 197) {
                throw new IllegalArgumentException("Unexpected opcode for 2 operands");
            }
            if (i3 < 0 || i3 >= 65536) {
                throw new IllegalArgumentException("out of range index");
            }
            if (i4 >= 0 && i4 < 256) {
                addToCodeBuffer(197);
                addToCodeInt16(i3);
                addToCodeBuffer(i4);
            } else {
                throw new IllegalArgumentException("out of range dimensions");
            }
        }
        short s2 = (short) iStackChange;
        this.itsStackTop = s2;
        if (iStackChange > this.itsMaxStack) {
            this.itsMaxStack = s2;
        }
    }

    public void add(int i2, String str) {
        int iStackChange = this.itsStackTop + stackChange(i2);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        if (i2 != 187 && i2 != 189 && i2 != 192 && i2 != 193) {
            throw new IllegalArgumentException("bad opcode for class reference");
        }
        short sAddClass = this.itsConstantPool.addClass(str);
        addToCodeBuffer(i2);
        addToCodeInt16(sAddClass);
        short s2 = (short) iStackChange;
        this.itsStackTop = s2;
        if (iStackChange > this.itsMaxStack) {
            this.itsMaxStack = s2;
        }
    }

    public void add(int i2, String str, String str2, String str3) {
        int i3;
        int iStackChange = this.itsStackTop + stackChange(i2);
        char cCharAt = str3.charAt(0);
        int i4 = (cCharAt == 'J' || cCharAt == 'D') ? 2 : 1;
        switch (i2) {
            case 178:
            case 180:
                i3 = iStackChange + i4;
                break;
            case 179:
            case 181:
                i3 = iStackChange - i4;
                break;
            default:
                throw new IllegalArgumentException("bad opcode for field reference");
        }
        if (i3 < 0 || 32767 < i3) {
            badStack(i3);
        }
        short sAddFieldRef = this.itsConstantPool.addFieldRef(str, str2, str3);
        addToCodeBuffer(i2);
        addToCodeInt16(sAddFieldRef);
        short s2 = (short) i3;
        this.itsStackTop = s2;
        if (i3 > this.itsMaxStack) {
            this.itsMaxStack = s2;
        }
    }
}
