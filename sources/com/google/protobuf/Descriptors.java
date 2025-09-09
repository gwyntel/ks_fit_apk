package com.google.protobuf;

import androidx.media3.common.C;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.FieldSet;
import com.google.protobuf.Internal;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.TextFormat;
import com.google.protobuf.WireFormat;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import kotlin.text.Typography;

@CheckReturnValue
/* loaded from: classes2.dex */
public final class Descriptors {
    private static final Logger logger = Logger.getLogger(Descriptors.class.getName());
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final Descriptor[] EMPTY_DESCRIPTORS = new Descriptor[0];
    private static final FieldDescriptor[] EMPTY_FIELD_DESCRIPTORS = new FieldDescriptor[0];
    private static final EnumDescriptor[] EMPTY_ENUM_DESCRIPTORS = new EnumDescriptor[0];
    private static final ServiceDescriptor[] EMPTY_SERVICE_DESCRIPTORS = new ServiceDescriptor[0];
    private static final OneofDescriptor[] EMPTY_ONEOF_DESCRIPTORS = new OneofDescriptor[0];

    /* renamed from: com.google.protobuf.Descriptors$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f15208a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f15209b;

        static {
            int[] iArr = new int[FieldDescriptor.JavaType.values().length];
            f15209b = iArr;
            try {
                iArr[FieldDescriptor.JavaType.ENUM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f15209b[FieldDescriptor.JavaType.MESSAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[FieldDescriptor.Type.values().length];
            f15208a = iArr2;
            try {
                iArr2[FieldDescriptor.Type.INT32.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f15208a[FieldDescriptor.Type.SINT32.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f15208a[FieldDescriptor.Type.SFIXED32.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f15208a[FieldDescriptor.Type.UINT32.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f15208a[FieldDescriptor.Type.FIXED32.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f15208a[FieldDescriptor.Type.INT64.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f15208a[FieldDescriptor.Type.SINT64.ordinal()] = 7;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f15208a[FieldDescriptor.Type.SFIXED64.ordinal()] = 8;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f15208a[FieldDescriptor.Type.UINT64.ordinal()] = 9;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f15208a[FieldDescriptor.Type.FIXED64.ordinal()] = 10;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f15208a[FieldDescriptor.Type.FLOAT.ordinal()] = 11;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f15208a[FieldDescriptor.Type.DOUBLE.ordinal()] = 12;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f15208a[FieldDescriptor.Type.BOOL.ordinal()] = 13;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f15208a[FieldDescriptor.Type.STRING.ordinal()] = 14;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f15208a[FieldDescriptor.Type.BYTES.ordinal()] = 15;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f15208a[FieldDescriptor.Type.ENUM.ordinal()] = 16;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f15208a[FieldDescriptor.Type.MESSAGE.ordinal()] = 17;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f15208a[FieldDescriptor.Type.GROUP.ordinal()] = 18;
            } catch (NoSuchFieldError unused20) {
            }
        }
    }

    public static final class Descriptor extends GenericDescriptor {
        private final Descriptor containingType;
        private final EnumDescriptor[] enumTypes;
        private final int[] extensionRangeLowerBounds;
        private final int[] extensionRangeUpperBounds;
        private final FieldDescriptor[] extensions;
        private final FieldDescriptor[] fields;
        private final FieldDescriptor[] fieldsSortedByNumber;
        private final FileDescriptor file;
        private final String fullName;
        private final int index;
        private final Descriptor[] nestedTypes;
        private final OneofDescriptor[] oneofs;
        private DescriptorProtos.DescriptorProto proto;
        private final int realOneofCount;

        /* synthetic */ Descriptor(DescriptorProtos.DescriptorProto descriptorProto, FileDescriptor fileDescriptor, Descriptor descriptor, int i2, AnonymousClass1 anonymousClass1) {
            this(descriptorProto, fileDescriptor, descriptor, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void crossLink() throws DescriptorValidationException {
            for (Descriptor descriptor : this.nestedTypes) {
                descriptor.crossLink();
            }
            for (FieldDescriptor fieldDescriptor : this.fields) {
                fieldDescriptor.crossLink();
            }
            Arrays.sort(this.fieldsSortedByNumber);
            validateNoDuplicateFieldNumbers();
            for (FieldDescriptor fieldDescriptor2 : this.extensions) {
                fieldDescriptor2.crossLink();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProto(DescriptorProtos.DescriptorProto descriptorProto) {
            this.proto = descriptorProto;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                Descriptor[] descriptorArr = this.nestedTypes;
                if (i3 >= descriptorArr.length) {
                    break;
                }
                descriptorArr[i3].setProto(descriptorProto.getNestedType(i3));
                i3++;
            }
            int i4 = 0;
            while (true) {
                OneofDescriptor[] oneofDescriptorArr = this.oneofs;
                if (i4 >= oneofDescriptorArr.length) {
                    break;
                }
                oneofDescriptorArr[i4].setProto(descriptorProto.getOneofDecl(i4));
                i4++;
            }
            int i5 = 0;
            while (true) {
                EnumDescriptor[] enumDescriptorArr = this.enumTypes;
                if (i5 >= enumDescriptorArr.length) {
                    break;
                }
                enumDescriptorArr[i5].setProto(descriptorProto.getEnumType(i5));
                i5++;
            }
            int i6 = 0;
            while (true) {
                FieldDescriptor[] fieldDescriptorArr = this.fields;
                if (i6 >= fieldDescriptorArr.length) {
                    break;
                }
                fieldDescriptorArr[i6].setProto(descriptorProto.getField(i6));
                i6++;
            }
            while (true) {
                FieldDescriptor[] fieldDescriptorArr2 = this.extensions;
                if (i2 >= fieldDescriptorArr2.length) {
                    return;
                }
                fieldDescriptorArr2[i2].setProto(descriptorProto.getExtension(i2));
                i2++;
            }
        }

        private void validateNoDuplicateFieldNumbers() throws DescriptorValidationException {
            int i2 = 0;
            while (true) {
                int i3 = i2 + 1;
                FieldDescriptor[] fieldDescriptorArr = this.fieldsSortedByNumber;
                if (i3 >= fieldDescriptorArr.length) {
                    return;
                }
                FieldDescriptor fieldDescriptor = fieldDescriptorArr[i2];
                FieldDescriptor fieldDescriptor2 = fieldDescriptorArr[i3];
                if (fieldDescriptor.getNumber() == fieldDescriptor2.getNumber()) {
                    throw new DescriptorValidationException(fieldDescriptor2, "Field number " + fieldDescriptor2.getNumber() + " has already been used in \"" + fieldDescriptor2.getContainingType().getFullName() + "\" by field \"" + fieldDescriptor.getName() + "\".", (AnonymousClass1) null);
                }
                i2 = i3;
            }
        }

        public EnumDescriptor findEnumTypeByName(String str) {
            GenericDescriptor genericDescriptorC = this.file.pool.c(this.fullName + '.' + str);
            if (genericDescriptorC instanceof EnumDescriptor) {
                return (EnumDescriptor) genericDescriptorC;
            }
            return null;
        }

        public FieldDescriptor findFieldByName(String str) {
            GenericDescriptor genericDescriptorC = this.file.pool.c(this.fullName + '.' + str);
            if (genericDescriptorC instanceof FieldDescriptor) {
                return (FieldDescriptor) genericDescriptorC;
            }
            return null;
        }

        public FieldDescriptor findFieldByNumber(int i2) {
            FieldDescriptor[] fieldDescriptorArr = this.fieldsSortedByNumber;
            return (FieldDescriptor) Descriptors.binarySearch(fieldDescriptorArr, fieldDescriptorArr.length, FieldDescriptor.NUMBER_GETTER, i2);
        }

        public Descriptor findNestedTypeByName(String str) {
            GenericDescriptor genericDescriptorC = this.file.pool.c(this.fullName + '.' + str);
            if (genericDescriptorC instanceof Descriptor) {
                return (Descriptor) genericDescriptorC;
            }
            return null;
        }

        public Descriptor getContainingType() {
            return this.containingType;
        }

        public List<EnumDescriptor> getEnumTypes() {
            return Collections.unmodifiableList(Arrays.asList(this.enumTypes));
        }

        public List<FieldDescriptor> getExtensions() {
            return Collections.unmodifiableList(Arrays.asList(this.extensions));
        }

        public List<FieldDescriptor> getFields() {
            return Collections.unmodifiableList(Arrays.asList(this.fields));
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public FileDescriptor getFile() {
            return this.file;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getFullName() {
            return this.fullName;
        }

        public int getIndex() {
            return this.index;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getName() {
            return this.proto.getName();
        }

        public List<Descriptor> getNestedTypes() {
            return Collections.unmodifiableList(Arrays.asList(this.nestedTypes));
        }

        public List<OneofDescriptor> getOneofs() {
            return Collections.unmodifiableList(Arrays.asList(this.oneofs));
        }

        public DescriptorProtos.MessageOptions getOptions() {
            return this.proto.getOptions();
        }

        public List<OneofDescriptor> getRealOneofs() {
            return Collections.unmodifiableList(Arrays.asList(this.oneofs).subList(0, this.realOneofCount));
        }

        public boolean isExtendable() {
            return !this.proto.getExtensionRangeList().isEmpty();
        }

        public boolean isExtensionNumber(int i2) {
            int iBinarySearch = Arrays.binarySearch(this.extensionRangeLowerBounds, i2);
            if (iBinarySearch < 0) {
                iBinarySearch = (~iBinarySearch) - 1;
            }
            return iBinarySearch >= 0 && i2 < this.extensionRangeUpperBounds[iBinarySearch];
        }

        public boolean isReservedName(String str) {
            Internal.a(str);
            Iterator<String> it = this.proto.getReservedNameList().iterator();
            while (it.hasNext()) {
                if (it.next().equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isReservedNumber(int i2) {
            for (DescriptorProtos.DescriptorProto.ReservedRange reservedRange : this.proto.getReservedRangeList()) {
                if (reservedRange.getStart() <= i2 && i2 < reservedRange.getEnd()) {
                    return true;
                }
            }
            return false;
        }

        Descriptor(String str) {
            String strSubstring;
            String strSubstring2;
            super(null);
            int iLastIndexOf = str.lastIndexOf(46);
            if (iLastIndexOf != -1) {
                strSubstring2 = str.substring(iLastIndexOf + 1);
                strSubstring = str.substring(0, iLastIndexOf);
            } else {
                strSubstring = "";
                strSubstring2 = str;
            }
            this.index = 0;
            this.proto = DescriptorProtos.DescriptorProto.newBuilder().setName(strSubstring2).addExtensionRange(DescriptorProtos.DescriptorProto.ExtensionRange.newBuilder().setStart(1).setEnd(C.BUFFER_FLAG_LAST_SAMPLE).build()).build();
            this.fullName = str;
            this.containingType = null;
            this.nestedTypes = Descriptors.EMPTY_DESCRIPTORS;
            this.enumTypes = Descriptors.EMPTY_ENUM_DESCRIPTORS;
            this.fields = Descriptors.EMPTY_FIELD_DESCRIPTORS;
            this.fieldsSortedByNumber = Descriptors.EMPTY_FIELD_DESCRIPTORS;
            this.extensions = Descriptors.EMPTY_FIELD_DESCRIPTORS;
            this.oneofs = Descriptors.EMPTY_ONEOF_DESCRIPTORS;
            this.realOneofCount = 0;
            this.file = new FileDescriptor(strSubstring, this);
            this.extensionRangeLowerBounds = new int[]{1};
            this.extensionRangeUpperBounds = new int[]{C.BUFFER_FLAG_LAST_SAMPLE};
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public DescriptorProtos.DescriptorProto toProto() {
            return this.proto;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        private Descriptor(DescriptorProtos.DescriptorProto descriptorProto, FileDescriptor fileDescriptor, Descriptor descriptor, int i2) throws DescriptorValidationException {
            OneofDescriptor[] oneofDescriptorArr;
            Descriptor[] descriptorArr;
            EnumDescriptor[] enumDescriptorArr;
            FieldDescriptor[] fieldDescriptorArr;
            FieldDescriptor[] fieldDescriptorArr2;
            AnonymousClass1 anonymousClass1 = null;
            super(anonymousClass1);
            this.index = i2;
            this.proto = descriptorProto;
            this.fullName = Descriptors.computeFullName(fileDescriptor, descriptor, descriptorProto.getName());
            this.file = fileDescriptor;
            this.containingType = descriptor;
            if (descriptorProto.getOneofDeclCount() <= 0) {
                oneofDescriptorArr = Descriptors.EMPTY_ONEOF_DESCRIPTORS;
            } else {
                oneofDescriptorArr = new OneofDescriptor[descriptorProto.getOneofDeclCount()];
            }
            this.oneofs = oneofDescriptorArr;
            int i3 = 0;
            for (int i4 = 0; i4 < descriptorProto.getOneofDeclCount(); i4++) {
                this.oneofs[i4] = new OneofDescriptor(descriptorProto.getOneofDecl(i4), fileDescriptor, this, i4, null);
            }
            if (descriptorProto.getNestedTypeCount() <= 0) {
                descriptorArr = Descriptors.EMPTY_DESCRIPTORS;
            } else {
                descriptorArr = new Descriptor[descriptorProto.getNestedTypeCount()];
            }
            this.nestedTypes = descriptorArr;
            for (int i5 = 0; i5 < descriptorProto.getNestedTypeCount(); i5++) {
                this.nestedTypes[i5] = new Descriptor(descriptorProto.getNestedType(i5), fileDescriptor, this, i5);
            }
            if (descriptorProto.getEnumTypeCount() <= 0) {
                enumDescriptorArr = Descriptors.EMPTY_ENUM_DESCRIPTORS;
            } else {
                enumDescriptorArr = new EnumDescriptor[descriptorProto.getEnumTypeCount()];
            }
            this.enumTypes = enumDescriptorArr;
            for (int i6 = 0; i6 < descriptorProto.getEnumTypeCount(); i6++) {
                this.enumTypes[i6] = new EnumDescriptor(descriptorProto.getEnumType(i6), fileDescriptor, this, i6, null);
            }
            if (descriptorProto.getFieldCount() <= 0) {
                fieldDescriptorArr = Descriptors.EMPTY_FIELD_DESCRIPTORS;
            } else {
                fieldDescriptorArr = new FieldDescriptor[descriptorProto.getFieldCount()];
            }
            this.fields = fieldDescriptorArr;
            for (int i7 = 0; i7 < descriptorProto.getFieldCount(); i7++) {
                this.fields[i7] = new FieldDescriptor(descriptorProto.getField(i7), fileDescriptor, this, i7, false, null);
            }
            this.fieldsSortedByNumber = descriptorProto.getFieldCount() > 0 ? (FieldDescriptor[]) this.fields.clone() : Descriptors.EMPTY_FIELD_DESCRIPTORS;
            if (descriptorProto.getExtensionCount() <= 0) {
                fieldDescriptorArr2 = Descriptors.EMPTY_FIELD_DESCRIPTORS;
            } else {
                fieldDescriptorArr2 = new FieldDescriptor[descriptorProto.getExtensionCount()];
            }
            this.extensions = fieldDescriptorArr2;
            for (int i8 = 0; i8 < descriptorProto.getExtensionCount(); i8++) {
                this.extensions[i8] = new FieldDescriptor(descriptorProto.getExtension(i8), fileDescriptor, this, i8, true, null);
            }
            for (int i9 = 0; i9 < descriptorProto.getOneofDeclCount(); i9++) {
                OneofDescriptor oneofDescriptor = this.oneofs[i9];
                oneofDescriptor.fields = new FieldDescriptor[oneofDescriptor.getFieldCount()];
                this.oneofs[i9].fieldCount = 0;
            }
            for (int i10 = 0; i10 < descriptorProto.getFieldCount(); i10++) {
                OneofDescriptor containingOneof = this.fields[i10].getContainingOneof();
                if (containingOneof != null) {
                    containingOneof.fields[OneofDescriptor.d(containingOneof)] = this.fields[i10];
                }
            }
            int i11 = 0;
            for (OneofDescriptor oneofDescriptor2 : this.oneofs) {
                if (oneofDescriptor2.isSynthetic()) {
                    i11++;
                } else if (i11 > 0) {
                    throw new DescriptorValidationException(this, "Synthetic oneofs must come last.", anonymousClass1);
                }
            }
            this.realOneofCount = this.oneofs.length - i11;
            fileDescriptor.pool.b(this);
            if (descriptorProto.getExtensionRangeCount() <= 0) {
                this.extensionRangeLowerBounds = Descriptors.EMPTY_INT_ARRAY;
                this.extensionRangeUpperBounds = Descriptors.EMPTY_INT_ARRAY;
                return;
            }
            this.extensionRangeLowerBounds = new int[descriptorProto.getExtensionRangeCount()];
            this.extensionRangeUpperBounds = new int[descriptorProto.getExtensionRangeCount()];
            for (DescriptorProtos.DescriptorProto.ExtensionRange extensionRange : descriptorProto.getExtensionRangeList()) {
                this.extensionRangeLowerBounds[i3] = extensionRange.getStart();
                this.extensionRangeUpperBounds[i3] = extensionRange.getEnd();
                i3++;
            }
            Arrays.sort(this.extensionRangeLowerBounds);
            Arrays.sort(this.extensionRangeUpperBounds);
        }
    }

    private static final class DescriptorPool {
        private final boolean allowUnknownDependencies;
        private final Set<FileDescriptor> dependencies;
        private final Map<String, GenericDescriptor> descriptorsByName = new HashMap();

        private static final class PackageDescriptor extends GenericDescriptor {
            private final FileDescriptor file;
            private final String fullName;
            private final String name;

            PackageDescriptor(String str, String str2, FileDescriptor fileDescriptor) {
                super(null);
                this.file = fileDescriptor;
                this.fullName = str2;
                this.name = str;
            }

            @Override // com.google.protobuf.Descriptors.GenericDescriptor
            public FileDescriptor getFile() {
                return this.file;
            }

            @Override // com.google.protobuf.Descriptors.GenericDescriptor
            public String getFullName() {
                return this.fullName;
            }

            @Override // com.google.protobuf.Descriptors.GenericDescriptor
            public String getName() {
                return this.name;
            }

            @Override // com.google.protobuf.Descriptors.GenericDescriptor
            public Message toProto() {
                return this.file.toProto();
            }
        }

        enum SearchFilter {
            TYPES_ONLY,
            AGGREGATES_ONLY,
            ALL_SYMBOLS
        }

        DescriptorPool(FileDescriptor[] fileDescriptorArr, boolean z2) {
            this.dependencies = Collections.newSetFromMap(new IdentityHashMap(fileDescriptorArr.length));
            this.allowUnknownDependencies = z2;
            for (FileDescriptor fileDescriptor : fileDescriptorArr) {
                this.dependencies.add(fileDescriptor);
                importPublicDependencies(fileDescriptor);
            }
            for (FileDescriptor fileDescriptor2 : this.dependencies) {
                try {
                    a(fileDescriptor2.getPackage(), fileDescriptor2);
                } catch (DescriptorValidationException e2) {
                    throw new AssertionError(e2);
                }
            }
        }

        static void h(GenericDescriptor genericDescriptor) throws DescriptorValidationException {
            String name = genericDescriptor.getName();
            AnonymousClass1 anonymousClass1 = null;
            if (name.length() == 0) {
                throw new DescriptorValidationException(genericDescriptor, "Missing name.", anonymousClass1);
            }
            for (int i2 = 0; i2 < name.length(); i2++) {
                char cCharAt = name.charAt(i2);
                if (('a' > cCharAt || cCharAt > 'z') && (('A' > cCharAt || cCharAt > 'Z') && cCharAt != '_' && ('0' > cCharAt || cCharAt > '9' || i2 <= 0))) {
                    throw new DescriptorValidationException(genericDescriptor, Typography.quote + name + "\" is not a valid identifier.", anonymousClass1);
                }
            }
        }

        private void importPublicDependencies(FileDescriptor fileDescriptor) {
            for (FileDescriptor fileDescriptor2 : fileDescriptor.getPublicDependencies()) {
                if (this.dependencies.add(fileDescriptor2)) {
                    importPublicDependencies(fileDescriptor2);
                }
            }
        }

        void a(String str, FileDescriptor fileDescriptor) throws DescriptorValidationException {
            String strSubstring;
            int iLastIndexOf = str.lastIndexOf(46);
            if (iLastIndexOf == -1) {
                strSubstring = str;
            } else {
                a(str.substring(0, iLastIndexOf), fileDescriptor);
                strSubstring = str.substring(iLastIndexOf + 1);
            }
            GenericDescriptor genericDescriptorPut = this.descriptorsByName.put(str, new PackageDescriptor(strSubstring, str, fileDescriptor));
            if (genericDescriptorPut != null) {
                this.descriptorsByName.put(str, genericDescriptorPut);
                if (genericDescriptorPut instanceof PackageDescriptor) {
                    return;
                }
                throw new DescriptorValidationException(fileDescriptor, Typography.quote + strSubstring + "\" is already defined (as something other than a package) in file \"" + genericDescriptorPut.getFile().getName() + "\".", (AnonymousClass1) null);
            }
        }

        void b(GenericDescriptor genericDescriptor) throws DescriptorValidationException {
            h(genericDescriptor);
            String fullName = genericDescriptor.getFullName();
            GenericDescriptor genericDescriptorPut = this.descriptorsByName.put(fullName, genericDescriptor);
            if (genericDescriptorPut != null) {
                this.descriptorsByName.put(fullName, genericDescriptorPut);
                AnonymousClass1 anonymousClass1 = null;
                if (genericDescriptor.getFile() != genericDescriptorPut.getFile()) {
                    throw new DescriptorValidationException(genericDescriptor, Typography.quote + fullName + "\" is already defined in file \"" + genericDescriptorPut.getFile().getName() + "\".", anonymousClass1);
                }
                int iLastIndexOf = fullName.lastIndexOf(46);
                if (iLastIndexOf == -1) {
                    throw new DescriptorValidationException(genericDescriptor, Typography.quote + fullName + "\" is already defined.", anonymousClass1);
                }
                throw new DescriptorValidationException(genericDescriptor, Typography.quote + fullName.substring(iLastIndexOf + 1) + "\" is already defined in \"" + fullName.substring(0, iLastIndexOf) + "\".", anonymousClass1);
            }
        }

        GenericDescriptor c(String str) {
            return d(str, SearchFilter.ALL_SYMBOLS);
        }

        GenericDescriptor d(String str, SearchFilter searchFilter) {
            GenericDescriptor genericDescriptor = this.descriptorsByName.get(str);
            if (genericDescriptor != null && (searchFilter == SearchFilter.ALL_SYMBOLS || ((searchFilter == SearchFilter.TYPES_ONLY && f(genericDescriptor)) || (searchFilter == SearchFilter.AGGREGATES_ONLY && e(genericDescriptor))))) {
                return genericDescriptor;
            }
            Iterator<FileDescriptor> it = this.dependencies.iterator();
            while (it.hasNext()) {
                GenericDescriptor genericDescriptor2 = it.next().pool.descriptorsByName.get(str);
                if (genericDescriptor2 != null && (searchFilter == SearchFilter.ALL_SYMBOLS || ((searchFilter == SearchFilter.TYPES_ONLY && f(genericDescriptor2)) || (searchFilter == SearchFilter.AGGREGATES_ONLY && e(genericDescriptor2))))) {
                    return genericDescriptor2;
                }
            }
            return null;
        }

        boolean e(GenericDescriptor genericDescriptor) {
            return (genericDescriptor instanceof Descriptor) || (genericDescriptor instanceof EnumDescriptor) || (genericDescriptor instanceof PackageDescriptor) || (genericDescriptor instanceof ServiceDescriptor);
        }

        boolean f(GenericDescriptor genericDescriptor) {
            return (genericDescriptor instanceof Descriptor) || (genericDescriptor instanceof EnumDescriptor);
        }

        GenericDescriptor g(String str, GenericDescriptor genericDescriptor, SearchFilter searchFilter) throws DescriptorValidationException {
            GenericDescriptor genericDescriptorD;
            String string;
            if (str.startsWith(".")) {
                string = str.substring(1);
                genericDescriptorD = d(string, searchFilter);
            } else {
                int iIndexOf = str.indexOf(46);
                String strSubstring = iIndexOf == -1 ? str : str.substring(0, iIndexOf);
                StringBuilder sb = new StringBuilder(genericDescriptor.getFullName());
                while (true) {
                    int iLastIndexOf = sb.lastIndexOf(".");
                    if (iLastIndexOf == -1) {
                        genericDescriptorD = d(str, searchFilter);
                        string = str;
                        break;
                    }
                    int i2 = iLastIndexOf + 1;
                    sb.setLength(i2);
                    sb.append(strSubstring);
                    GenericDescriptor genericDescriptorD2 = d(sb.toString(), SearchFilter.AGGREGATES_ONLY);
                    if (genericDescriptorD2 != null) {
                        if (iIndexOf != -1) {
                            sb.setLength(i2);
                            sb.append(str);
                            genericDescriptorD = d(sb.toString(), searchFilter);
                        } else {
                            genericDescriptorD = genericDescriptorD2;
                        }
                        string = sb.toString();
                    } else {
                        sb.setLength(iLastIndexOf);
                    }
                }
            }
            if (genericDescriptorD != null) {
                return genericDescriptorD;
            }
            if (!this.allowUnknownDependencies || searchFilter != SearchFilter.TYPES_ONLY) {
                throw new DescriptorValidationException(genericDescriptor, Typography.quote + str + "\" is not defined.", (AnonymousClass1) null);
            }
            Descriptors.logger.warning("The descriptor for message type \"" + str + "\" cannot be found and a placeholder is created for it");
            Descriptor descriptor = new Descriptor(string);
            this.dependencies.add(descriptor.getFile());
            return descriptor;
        }
    }

    public static class DescriptorValidationException extends Exception {
        private static final long serialVersionUID = 5750205775490483148L;
        private final String description;
        private final String name;
        private final Message proto;

        /* synthetic */ DescriptorValidationException(FileDescriptor fileDescriptor, String str, AnonymousClass1 anonymousClass1) {
            this(fileDescriptor, str);
        }

        public String getDescription() {
            return this.description;
        }

        public Message getProblemProto() {
            return this.proto;
        }

        public String getProblemSymbolName() {
            return this.name;
        }

        /* synthetic */ DescriptorValidationException(GenericDescriptor genericDescriptor, String str, AnonymousClass1 anonymousClass1) {
            this(genericDescriptor, str);
        }

        /* synthetic */ DescriptorValidationException(GenericDescriptor genericDescriptor, String str, Throwable th, AnonymousClass1 anonymousClass1) {
            this(genericDescriptor, str, th);
        }

        private DescriptorValidationException(GenericDescriptor genericDescriptor, String str) {
            super(genericDescriptor.getFullName() + ": " + str);
            this.name = genericDescriptor.getFullName();
            this.proto = genericDescriptor.toProto();
            this.description = str;
        }

        private DescriptorValidationException(GenericDescriptor genericDescriptor, String str, Throwable th) {
            this(genericDescriptor, str);
            initCause(th);
        }

        private DescriptorValidationException(FileDescriptor fileDescriptor, String str) {
            super(fileDescriptor.getName() + ": " + str);
            this.name = fileDescriptor.getName();
            this.proto = fileDescriptor.toProto();
            this.description = str;
        }
    }

    public static final class EnumDescriptor extends GenericDescriptor implements Internal.EnumLiteMap<EnumValueDescriptor> {
        private ReferenceQueue<EnumValueDescriptor> cleanupQueue;
        private final Descriptor containingType;
        private final int distinctNumbers;
        private final FileDescriptor file;
        private final String fullName;
        private final int index;
        private DescriptorProtos.EnumDescriptorProto proto;
        private Map<Integer, WeakReference<EnumValueDescriptor>> unknownValues;
        private final EnumValueDescriptor[] values;
        private final EnumValueDescriptor[] valuesSortedByNumber;

        private static class UnknownEnumValueReference extends WeakReference<EnumValueDescriptor> {
            private final int number;

            /* synthetic */ UnknownEnumValueReference(int i2, EnumValueDescriptor enumValueDescriptor, AnonymousClass1 anonymousClass1) {
                this(i2, enumValueDescriptor);
            }

            private UnknownEnumValueReference(int i2, EnumValueDescriptor enumValueDescriptor) {
                super(enumValueDescriptor);
                this.number = i2;
            }
        }

        /* synthetic */ EnumDescriptor(DescriptorProtos.EnumDescriptorProto enumDescriptorProto, FileDescriptor fileDescriptor, Descriptor descriptor, int i2, AnonymousClass1 anonymousClass1) {
            this(enumDescriptorProto, fileDescriptor, descriptor, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProto(DescriptorProtos.EnumDescriptorProto enumDescriptorProto) {
            this.proto = enumDescriptorProto;
            int i2 = 0;
            while (true) {
                EnumValueDescriptor[] enumValueDescriptorArr = this.values;
                if (i2 >= enumValueDescriptorArr.length) {
                    return;
                }
                enumValueDescriptorArr[i2].setProto(enumDescriptorProto.getValue(i2));
                i2++;
            }
        }

        public EnumValueDescriptor findValueByName(String str) {
            GenericDescriptor genericDescriptorC = this.file.pool.c(this.fullName + '.' + str);
            if (genericDescriptorC instanceof EnumValueDescriptor) {
                return (EnumValueDescriptor) genericDescriptorC;
            }
            return null;
        }

        public EnumValueDescriptor findValueByNumberCreatingIfUnknown(int i2) {
            EnumValueDescriptor enumValueDescriptor;
            EnumValueDescriptor enumValueDescriptorFindValueByNumber = findValueByNumber(i2);
            if (enumValueDescriptorFindValueByNumber != null) {
                return enumValueDescriptorFindValueByNumber;
            }
            synchronized (this) {
                try {
                    if (this.cleanupQueue == null) {
                        this.cleanupQueue = new ReferenceQueue<>();
                        this.unknownValues = new HashMap();
                    } else {
                        while (true) {
                            UnknownEnumValueReference unknownEnumValueReference = (UnknownEnumValueReference) this.cleanupQueue.poll();
                            if (unknownEnumValueReference == null) {
                                break;
                            }
                            this.unknownValues.remove(Integer.valueOf(unknownEnumValueReference.number));
                        }
                    }
                    WeakReference<EnumValueDescriptor> weakReference = this.unknownValues.get(Integer.valueOf(i2));
                    AnonymousClass1 anonymousClass1 = null;
                    enumValueDescriptor = weakReference == null ? null : weakReference.get();
                    if (enumValueDescriptor == null) {
                        enumValueDescriptor = new EnumValueDescriptor(this, Integer.valueOf(i2), anonymousClass1);
                        this.unknownValues.put(Integer.valueOf(i2), new UnknownEnumValueReference(i2, enumValueDescriptor, anonymousClass1));
                    }
                } finally {
                }
            }
            return enumValueDescriptor;
        }

        public Descriptor getContainingType() {
            return this.containingType;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public FileDescriptor getFile() {
            return this.file;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getFullName() {
            return this.fullName;
        }

        public int getIndex() {
            return this.index;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getName() {
            return this.proto.getName();
        }

        public DescriptorProtos.EnumOptions getOptions() {
            return this.proto.getOptions();
        }

        public List<EnumValueDescriptor> getValues() {
            return Collections.unmodifiableList(Arrays.asList(this.values));
        }

        public boolean isClosed() {
            return getFile().getSyntax() != FileDescriptor.Syntax.PROTO3;
        }

        public boolean isReservedName(String str) {
            Internal.a(str);
            Iterator<String> it = this.proto.getReservedNameList().iterator();
            while (it.hasNext()) {
                if (it.next().equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isReservedNumber(int i2) {
            for (DescriptorProtos.EnumDescriptorProto.EnumReservedRange enumReservedRange : this.proto.getReservedRangeList()) {
                if (enumReservedRange.getStart() <= i2 && i2 <= enumReservedRange.getEnd()) {
                    return true;
                }
            }
            return false;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        private EnumDescriptor(DescriptorProtos.EnumDescriptorProto enumDescriptorProto, FileDescriptor fileDescriptor, Descriptor descriptor, int i2) throws DescriptorValidationException {
            AnonymousClass1 anonymousClass1 = null;
            super(anonymousClass1);
            this.unknownValues = null;
            this.cleanupQueue = null;
            this.index = i2;
            this.proto = enumDescriptorProto;
            this.fullName = Descriptors.computeFullName(fileDescriptor, descriptor, enumDescriptorProto.getName());
            this.file = fileDescriptor;
            this.containingType = descriptor;
            if (enumDescriptorProto.getValueCount() == 0) {
                throw new DescriptorValidationException(this, "Enums must contain at least one value.", anonymousClass1);
            }
            this.values = new EnumValueDescriptor[enumDescriptorProto.getValueCount()];
            int i3 = 0;
            for (int i4 = 0; i4 < enumDescriptorProto.getValueCount(); i4++) {
                this.values[i4] = new EnumValueDescriptor(enumDescriptorProto.getValue(i4), fileDescriptor, this, i4, null);
            }
            EnumValueDescriptor[] enumValueDescriptorArr = (EnumValueDescriptor[]) this.values.clone();
            this.valuesSortedByNumber = enumValueDescriptorArr;
            Arrays.sort(enumValueDescriptorArr, EnumValueDescriptor.f15210a);
            for (int i5 = 1; i5 < enumDescriptorProto.getValueCount(); i5++) {
                EnumValueDescriptor[] enumValueDescriptorArr2 = this.valuesSortedByNumber;
                EnumValueDescriptor enumValueDescriptor = enumValueDescriptorArr2[i3];
                EnumValueDescriptor enumValueDescriptor2 = enumValueDescriptorArr2[i5];
                if (enumValueDescriptor.getNumber() != enumValueDescriptor2.getNumber()) {
                    i3++;
                    this.valuesSortedByNumber[i3] = enumValueDescriptor2;
                }
            }
            int i6 = i3 + 1;
            this.distinctNumbers = i6;
            Arrays.fill(this.valuesSortedByNumber, i6, enumDescriptorProto.getValueCount(), (Object) null);
            fileDescriptor.pool.b(this);
        }

        @Override // com.google.protobuf.Internal.EnumLiteMap
        public EnumValueDescriptor findValueByNumber(int i2) {
            return (EnumValueDescriptor) Descriptors.binarySearch(this.valuesSortedByNumber, this.distinctNumbers, EnumValueDescriptor.f15211b, i2);
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public DescriptorProtos.EnumDescriptorProto toProto() {
            return this.proto;
        }
    }

    public static final class EnumValueDescriptor extends GenericDescriptor implements Internal.EnumLite {

        /* renamed from: a, reason: collision with root package name */
        static final Comparator f15210a = new Comparator<EnumValueDescriptor>() { // from class: com.google.protobuf.Descriptors.EnumValueDescriptor.1
            @Override // java.util.Comparator
            public int compare(EnumValueDescriptor enumValueDescriptor, EnumValueDescriptor enumValueDescriptor2) {
                return Integer.valueOf(enumValueDescriptor.getNumber()).compareTo(Integer.valueOf(enumValueDescriptor2.getNumber()));
            }
        };

        /* renamed from: b, reason: collision with root package name */
        static final NumberGetter f15211b = new NumberGetter<EnumValueDescriptor>() { // from class: com.google.protobuf.Descriptors.EnumValueDescriptor.2
            @Override // com.google.protobuf.Descriptors.NumberGetter
            public int getNumber(EnumValueDescriptor enumValueDescriptor) {
                return enumValueDescriptor.getNumber();
            }
        };
        private final String fullName;
        private final int index;
        private DescriptorProtos.EnumValueDescriptorProto proto;
        private final EnumDescriptor type;

        /* synthetic */ EnumValueDescriptor(DescriptorProtos.EnumValueDescriptorProto enumValueDescriptorProto, FileDescriptor fileDescriptor, EnumDescriptor enumDescriptor, int i2, AnonymousClass1 anonymousClass1) {
            this(enumValueDescriptorProto, fileDescriptor, enumDescriptor, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProto(DescriptorProtos.EnumValueDescriptorProto enumValueDescriptorProto) {
            this.proto = enumValueDescriptorProto;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public FileDescriptor getFile() {
            return this.type.file;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getFullName() {
            return this.fullName;
        }

        public int getIndex() {
            return this.index;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getName() {
            return this.proto.getName();
        }

        @Override // com.google.protobuf.Internal.EnumLite
        public int getNumber() {
            return this.proto.getNumber();
        }

        public DescriptorProtos.EnumValueOptions getOptions() {
            return this.proto.getOptions();
        }

        public EnumDescriptor getType() {
            return this.type;
        }

        public String toString() {
            return this.proto.getName();
        }

        /* synthetic */ EnumValueDescriptor(EnumDescriptor enumDescriptor, Integer num, AnonymousClass1 anonymousClass1) {
            this(enumDescriptor, num);
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public DescriptorProtos.EnumValueDescriptorProto toProto() {
            return this.proto;
        }

        private EnumValueDescriptor(DescriptorProtos.EnumValueDescriptorProto enumValueDescriptorProto, FileDescriptor fileDescriptor, EnumDescriptor enumDescriptor, int i2) throws DescriptorValidationException {
            super(null);
            this.index = i2;
            this.proto = enumValueDescriptorProto;
            this.type = enumDescriptor;
            this.fullName = enumDescriptor.getFullName() + '.' + enumValueDescriptorProto.getName();
            fileDescriptor.pool.b(this);
        }

        private EnumValueDescriptor(EnumDescriptor enumDescriptor, Integer num) {
            super(null);
            DescriptorProtos.EnumValueDescriptorProto enumValueDescriptorProtoBuild = DescriptorProtos.EnumValueDescriptorProto.newBuilder().setName("UNKNOWN_ENUM_VALUE_" + enumDescriptor.getName() + OpenAccountUIConstants.UNDER_LINE + num).setNumber(num.intValue()).build();
            this.index = -1;
            this.proto = enumValueDescriptorProtoBuild;
            this.type = enumDescriptor;
            this.fullName = enumDescriptor.getFullName() + '.' + enumValueDescriptorProtoBuild.getName();
        }
    }

    public static final class FieldDescriptor extends GenericDescriptor implements Comparable<FieldDescriptor>, FieldSet.FieldDescriptorLite<FieldDescriptor> {
        private static final NumberGetter<FieldDescriptor> NUMBER_GETTER = new NumberGetter<FieldDescriptor>() { // from class: com.google.protobuf.Descriptors.FieldDescriptor.1
            @Override // com.google.protobuf.Descriptors.NumberGetter
            public int getNumber(FieldDescriptor fieldDescriptor) {
                return fieldDescriptor.getNumber();
            }
        };
        private static final WireFormat.FieldType[] table = WireFormat.FieldType.values();
        private OneofDescriptor containingOneof;
        private Descriptor containingType;
        private Object defaultValue;
        private EnumDescriptor enumType;
        private final Descriptor extensionScope;
        private final FileDescriptor file;
        private final String fullName;
        private final int index;
        private final boolean isProto3Optional;
        private String jsonName;
        private Descriptor messageType;
        private DescriptorProtos.FieldDescriptorProto proto;
        private Type type;

        public enum JavaType {
            INT(0),
            LONG(0L),
            FLOAT(Float.valueOf(0.0f)),
            DOUBLE(Double.valueOf(0.0d)),
            BOOLEAN(Boolean.FALSE),
            STRING(""),
            BYTE_STRING(ByteString.EMPTY),
            ENUM(null),
            MESSAGE(null);

            private final Object defaultDefault;

            JavaType(Object obj) {
                this.defaultDefault = obj;
            }
        }

        /* JADX WARN: Enum visitor error
        jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'INT64' uses external variables
        	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
        	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
        	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
        	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
        	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
        	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
         */
        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        public static final class Type {
            private static final /* synthetic */ Type[] $VALUES;
            public static final Type BOOL;
            public static final Type BYTES;
            public static final Type DOUBLE;
            public static final Type ENUM;
            public static final Type FIXED32;
            public static final Type FIXED64;
            public static final Type FLOAT;
            public static final Type GROUP;
            public static final Type INT32;
            public static final Type INT64;
            public static final Type MESSAGE;
            public static final Type SFIXED32;
            public static final Type SFIXED64;
            public static final Type SINT32;
            public static final Type SINT64;
            public static final Type STRING;
            public static final Type UINT32;
            public static final Type UINT64;
            private static final Type[] types;
            private final JavaType javaType;

            static {
                Type type = new Type("DOUBLE", 0, JavaType.DOUBLE);
                DOUBLE = type;
                Type type2 = new Type("FLOAT", 1, JavaType.FLOAT);
                FLOAT = type2;
                JavaType javaType = JavaType.LONG;
                Type type3 = new Type("INT64", 2, javaType);
                INT64 = type3;
                Type type4 = new Type("UINT64", 3, javaType);
                UINT64 = type4;
                JavaType javaType2 = JavaType.INT;
                Type type5 = new Type("INT32", 4, javaType2);
                INT32 = type5;
                Type type6 = new Type("FIXED64", 5, javaType);
                FIXED64 = type6;
                Type type7 = new Type("FIXED32", 6, javaType2);
                FIXED32 = type7;
                Type type8 = new Type("BOOL", 7, JavaType.BOOLEAN);
                BOOL = type8;
                Type type9 = new Type("STRING", 8, JavaType.STRING);
                STRING = type9;
                JavaType javaType3 = JavaType.MESSAGE;
                Type type10 = new Type("GROUP", 9, javaType3);
                GROUP = type10;
                Type type11 = new Type("MESSAGE", 10, javaType3);
                MESSAGE = type11;
                Type type12 = new Type("BYTES", 11, JavaType.BYTE_STRING);
                BYTES = type12;
                Type type13 = new Type("UINT32", 12, javaType2);
                UINT32 = type13;
                Type type14 = new Type("ENUM", 13, JavaType.ENUM);
                ENUM = type14;
                Type type15 = new Type("SFIXED32", 14, javaType2);
                SFIXED32 = type15;
                Type type16 = new Type("SFIXED64", 15, javaType);
                SFIXED64 = type16;
                Type type17 = new Type("SINT32", 16, javaType2);
                SINT32 = type17;
                Type type18 = new Type("SINT64", 17, javaType);
                SINT64 = type18;
                $VALUES = new Type[]{type, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17, type18};
                types = values();
            }

            private Type(String str, int i2, JavaType javaType) {
                this.javaType = javaType;
            }

            public static Type valueOf(String str) {
                return (Type) java.lang.Enum.valueOf(Type.class, str);
            }

            public static Type[] values() {
                return (Type[]) $VALUES.clone();
            }

            public JavaType getJavaType() {
                return this.javaType;
            }

            public DescriptorProtos.FieldDescriptorProto.Type toProto() {
                return DescriptorProtos.FieldDescriptorProto.Type.forNumber(ordinal() + 1);
            }

            public static Type valueOf(DescriptorProtos.FieldDescriptorProto.Type type) {
                return types[type.getNumber() - 1];
            }
        }

        static {
            if (Type.types.length != DescriptorProtos.FieldDescriptorProto.Type.values().length) {
                throw new RuntimeException("descriptor.proto has a new declared type but Descriptors.java wasn't updated.");
            }
        }

        /* synthetic */ FieldDescriptor(DescriptorProtos.FieldDescriptorProto fieldDescriptorProto, FileDescriptor fileDescriptor, Descriptor descriptor, int i2, boolean z2, AnonymousClass1 anonymousClass1) {
            this(fieldDescriptorProto, fileDescriptor, descriptor, i2, z2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void crossLink() throws DescriptorValidationException {
            AnonymousClass1 anonymousClass1 = null;
            if (this.proto.hasExtendee()) {
                GenericDescriptor genericDescriptorG = this.file.pool.g(this.proto.getExtendee(), this, DescriptorPool.SearchFilter.TYPES_ONLY);
                if (!(genericDescriptorG instanceof Descriptor)) {
                    throw new DescriptorValidationException(this, Typography.quote + this.proto.getExtendee() + "\" is not a message type.", anonymousClass1);
                }
                this.containingType = (Descriptor) genericDescriptorG;
                if (!getContainingType().isExtensionNumber(getNumber())) {
                    throw new DescriptorValidationException(this, Typography.quote + getContainingType().getFullName() + "\" does not declare " + getNumber() + " as an extension number.", anonymousClass1);
                }
            }
            if (this.proto.hasTypeName()) {
                GenericDescriptor genericDescriptorG2 = this.file.pool.g(this.proto.getTypeName(), this, DescriptorPool.SearchFilter.TYPES_ONLY);
                if (!this.proto.hasType()) {
                    if (genericDescriptorG2 instanceof Descriptor) {
                        this.type = Type.MESSAGE;
                    } else {
                        if (!(genericDescriptorG2 instanceof EnumDescriptor)) {
                            throw new DescriptorValidationException(this, Typography.quote + this.proto.getTypeName() + "\" is not a type.", anonymousClass1);
                        }
                        this.type = Type.ENUM;
                    }
                }
                if (getJavaType() == JavaType.MESSAGE) {
                    if (!(genericDescriptorG2 instanceof Descriptor)) {
                        throw new DescriptorValidationException(this, Typography.quote + this.proto.getTypeName() + "\" is not a message type.", anonymousClass1);
                    }
                    this.messageType = (Descriptor) genericDescriptorG2;
                    if (this.proto.hasDefaultValue()) {
                        throw new DescriptorValidationException(this, "Messages can't have default values.", anonymousClass1);
                    }
                } else {
                    if (getJavaType() != JavaType.ENUM) {
                        throw new DescriptorValidationException(this, "Field with primitive type has type_name.", anonymousClass1);
                    }
                    if (!(genericDescriptorG2 instanceof EnumDescriptor)) {
                        throw new DescriptorValidationException(this, Typography.quote + this.proto.getTypeName() + "\" is not an enum type.", anonymousClass1);
                    }
                    this.enumType = (EnumDescriptor) genericDescriptorG2;
                }
            } else if (getJavaType() == JavaType.MESSAGE || getJavaType() == JavaType.ENUM) {
                throw new DescriptorValidationException(this, "Field with message or enum type missing type_name.", anonymousClass1);
            }
            if (this.proto.getOptions().getPacked() && !isPackable()) {
                throw new DescriptorValidationException(this, "[packed = true] can only be specified for repeated primitive fields.", anonymousClass1);
            }
            if (this.proto.hasDefaultValue()) {
                if (isRepeated()) {
                    throw new DescriptorValidationException(this, "Repeated fields cannot have default values.", anonymousClass1);
                }
                try {
                    switch (AnonymousClass1.f15208a[getType().ordinal()]) {
                        case 1:
                        case 2:
                        case 3:
                            this.defaultValue = Integer.valueOf(TextFormat.d(this.proto.getDefaultValue()));
                            break;
                        case 4:
                        case 5:
                            this.defaultValue = Integer.valueOf(TextFormat.f(this.proto.getDefaultValue()));
                            break;
                        case 6:
                        case 7:
                        case 8:
                            this.defaultValue = Long.valueOf(TextFormat.e(this.proto.getDefaultValue()));
                            break;
                        case 9:
                        case 10:
                            this.defaultValue = Long.valueOf(TextFormat.g(this.proto.getDefaultValue()));
                            break;
                        case 11:
                            if (!this.proto.getDefaultValue().equals("inf")) {
                                if (!this.proto.getDefaultValue().equals("-inf")) {
                                    if (!this.proto.getDefaultValue().equals("nan")) {
                                        this.defaultValue = Float.valueOf(this.proto.getDefaultValue());
                                        break;
                                    } else {
                                        this.defaultValue = Float.valueOf(Float.NaN);
                                        break;
                                    }
                                } else {
                                    this.defaultValue = Float.valueOf(Float.NEGATIVE_INFINITY);
                                    break;
                                }
                            } else {
                                this.defaultValue = Float.valueOf(Float.POSITIVE_INFINITY);
                                break;
                            }
                        case 12:
                            if (!this.proto.getDefaultValue().equals("inf")) {
                                if (!this.proto.getDefaultValue().equals("-inf")) {
                                    if (!this.proto.getDefaultValue().equals("nan")) {
                                        this.defaultValue = Double.valueOf(this.proto.getDefaultValue());
                                        break;
                                    } else {
                                        this.defaultValue = Double.valueOf(Double.NaN);
                                        break;
                                    }
                                } else {
                                    this.defaultValue = Double.valueOf(Double.NEGATIVE_INFINITY);
                                    break;
                                }
                            } else {
                                this.defaultValue = Double.valueOf(Double.POSITIVE_INFINITY);
                                break;
                            }
                        case 13:
                            this.defaultValue = Boolean.valueOf(this.proto.getDefaultValue());
                            break;
                        case 14:
                            this.defaultValue = this.proto.getDefaultValue();
                            break;
                        case 15:
                            try {
                                this.defaultValue = TextFormat.unescapeBytes(this.proto.getDefaultValue());
                                break;
                            } catch (TextFormat.InvalidEscapeSequenceException e2) {
                                throw new DescriptorValidationException(this, "Couldn't parse default value: " + e2.getMessage(), e2, anonymousClass1);
                            }
                        case 16:
                            EnumValueDescriptor enumValueDescriptorFindValueByName = this.enumType.findValueByName(this.proto.getDefaultValue());
                            this.defaultValue = enumValueDescriptorFindValueByName;
                            if (enumValueDescriptorFindValueByName == null) {
                                throw new DescriptorValidationException(this, "Unknown enum default value: \"" + this.proto.getDefaultValue() + Typography.quote, anonymousClass1);
                            }
                            break;
                        case 17:
                        case 18:
                            throw new DescriptorValidationException(this, "Message type had default value.", anonymousClass1);
                    }
                } catch (NumberFormatException e3) {
                    throw new DescriptorValidationException(this, "Could not parse default value: \"" + this.proto.getDefaultValue() + Typography.quote, e3, anonymousClass1);
                }
            } else if (isRepeated()) {
                this.defaultValue = Collections.emptyList();
            } else {
                int i2 = AnonymousClass1.f15209b[getJavaType().ordinal()];
                if (i2 == 1) {
                    this.defaultValue = this.enumType.getValues().get(0);
                } else if (i2 != 2) {
                    this.defaultValue = getJavaType().defaultDefault;
                } else {
                    this.defaultValue = null;
                }
            }
            Descriptor descriptor = this.containingType;
            if (descriptor == null || !descriptor.getOptions().getMessageSetWireFormat()) {
                return;
            }
            if (!isExtension()) {
                throw new DescriptorValidationException(this, "MessageSets cannot have fields, only extensions.", anonymousClass1);
            }
            if (!isOptional() || getType() != Type.MESSAGE) {
                throw new DescriptorValidationException(this, "Extensions of MessageSets must be optional messages.", anonymousClass1);
            }
        }

        private static String fieldNameToJsonName(String str) {
            int length = str.length();
            StringBuilder sb = new StringBuilder(length);
            boolean z2 = false;
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = str.charAt(i2);
                if (cCharAt == '_') {
                    z2 = true;
                } else if (z2) {
                    if ('a' <= cCharAt && cCharAt <= 'z') {
                        cCharAt = (char) (cCharAt - ' ');
                    }
                    sb.append(cCharAt);
                    z2 = false;
                } else {
                    sb.append(cCharAt);
                }
            }
            return sb.toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProto(DescriptorProtos.FieldDescriptorProto fieldDescriptorProto) {
            this.proto = fieldDescriptorProto;
        }

        public OneofDescriptor getContainingOneof() {
            return this.containingOneof;
        }

        public Descriptor getContainingType() {
            return this.containingType;
        }

        public Object getDefaultValue() {
            if (getJavaType() != JavaType.MESSAGE) {
                return this.defaultValue;
            }
            throw new UnsupportedOperationException("FieldDescriptor.getDefaultValue() called on an embedded message field.");
        }

        public Descriptor getExtensionScope() {
            if (isExtension()) {
                return this.extensionScope;
            }
            throw new UnsupportedOperationException(String.format("This field is not an extension. (%s)", this.fullName));
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public FileDescriptor getFile() {
            return this.file;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getFullName() {
            return this.fullName;
        }

        public int getIndex() {
            return this.index;
        }

        public JavaType getJavaType() {
            return this.type.getJavaType();
        }

        public String getJsonName() {
            String str = this.jsonName;
            if (str != null) {
                return str;
            }
            if (this.proto.hasJsonName()) {
                String jsonName = this.proto.getJsonName();
                this.jsonName = jsonName;
                return jsonName;
            }
            String strFieldNameToJsonName = fieldNameToJsonName(this.proto.getName());
            this.jsonName = strFieldNameToJsonName;
            return strFieldNameToJsonName;
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public WireFormat.JavaType getLiteJavaType() {
            return getLiteType().getJavaType();
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public WireFormat.FieldType getLiteType() {
            return table[this.type.ordinal()];
        }

        public Descriptor getMessageType() {
            if (getJavaType() == JavaType.MESSAGE) {
                return this.messageType;
            }
            throw new UnsupportedOperationException(String.format("This field is not of message type. (%s)", this.fullName));
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getName() {
            return this.proto.getName();
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public int getNumber() {
            return this.proto.getNumber();
        }

        public DescriptorProtos.FieldOptions getOptions() {
            return this.proto.getOptions();
        }

        public OneofDescriptor getRealContainingOneof() {
            OneofDescriptor oneofDescriptor = this.containingOneof;
            if (oneofDescriptor == null || oneofDescriptor.isSynthetic()) {
                return null;
            }
            return this.containingOneof;
        }

        public Type getType() {
            return this.type;
        }

        public boolean hasDefaultValue() {
            return this.proto.hasDefaultValue();
        }

        public boolean hasOptionalKeyword() {
            return this.isProto3Optional || (this.file.getSyntax() == FileDescriptor.Syntax.PROTO2 && isOptional() && getContainingOneof() == null);
        }

        public boolean hasPresence() {
            if (isRepeated()) {
                return false;
            }
            return getType() == Type.MESSAGE || getType() == Type.GROUP || getContainingOneof() != null || this.file.getSyntax() == FileDescriptor.Syntax.PROTO2;
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public MessageLite.Builder internalMergeFrom(MessageLite.Builder builder, MessageLite messageLite) {
            return ((Message.Builder) builder).mergeFrom((Message) messageLite);
        }

        public boolean isExtension() {
            return this.proto.hasExtendee();
        }

        public boolean isMapField() {
            return getType() == Type.MESSAGE && isRepeated() && getMessageType().getOptions().getMapEntry();
        }

        public boolean isOptional() {
            return this.proto.getLabel() == DescriptorProtos.FieldDescriptorProto.Label.LABEL_OPTIONAL;
        }

        public boolean isPackable() {
            return isRepeated() && getLiteType().isPackable();
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public boolean isPacked() {
            if (isPackable()) {
                return getFile().getSyntax() == FileDescriptor.Syntax.PROTO2 ? getOptions().getPacked() : !getOptions().hasPacked() || getOptions().getPacked();
            }
            return false;
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public boolean isRepeated() {
            return this.proto.getLabel() == DescriptorProtos.FieldDescriptorProto.Label.LABEL_REPEATED;
        }

        public boolean isRequired() {
            return this.proto.getLabel() == DescriptorProtos.FieldDescriptorProto.Label.LABEL_REQUIRED;
        }

        public boolean needsUtf8Check() {
            if (this.type != Type.STRING) {
                return false;
            }
            if (getContainingType().getOptions().getMapEntry() || getFile().getSyntax() == FileDescriptor.Syntax.PROTO3) {
                return true;
            }
            return getFile().getOptions().getJavaStringCheckUtf8();
        }

        public String toString() {
            return getFullName();
        }

        /* JADX WARN: Illegal instructions before constructor call */
        private FieldDescriptor(DescriptorProtos.FieldDescriptorProto fieldDescriptorProto, FileDescriptor fileDescriptor, Descriptor descriptor, int i2, boolean z2) throws DescriptorValidationException {
            AnonymousClass1 anonymousClass1 = null;
            super(anonymousClass1);
            this.index = i2;
            this.proto = fieldDescriptorProto;
            this.fullName = Descriptors.computeFullName(fileDescriptor, descriptor, fieldDescriptorProto.getName());
            this.file = fileDescriptor;
            if (fieldDescriptorProto.hasType()) {
                this.type = Type.valueOf(fieldDescriptorProto.getType());
            }
            this.isProto3Optional = fieldDescriptorProto.getProto3Optional();
            if (getNumber() <= 0) {
                throw new DescriptorValidationException(this, "Field numbers must be positive integers.", anonymousClass1);
            }
            if (z2) {
                if (!fieldDescriptorProto.hasExtendee()) {
                    throw new DescriptorValidationException(this, "FieldDescriptorProto.extendee not set for extension field.", anonymousClass1);
                }
                this.containingType = null;
                if (descriptor != null) {
                    this.extensionScope = descriptor;
                } else {
                    this.extensionScope = null;
                }
                if (fieldDescriptorProto.hasOneofIndex()) {
                    throw new DescriptorValidationException(this, "FieldDescriptorProto.oneof_index set for extension field.", anonymousClass1);
                }
                this.containingOneof = null;
            } else {
                if (fieldDescriptorProto.hasExtendee()) {
                    throw new DescriptorValidationException(this, "FieldDescriptorProto.extendee set for non-extension field.", anonymousClass1);
                }
                this.containingType = descriptor;
                if (!fieldDescriptorProto.hasOneofIndex()) {
                    this.containingOneof = null;
                } else {
                    if (fieldDescriptorProto.getOneofIndex() < 0 || fieldDescriptorProto.getOneofIndex() >= descriptor.toProto().getOneofDeclCount()) {
                        throw new DescriptorValidationException(this, "FieldDescriptorProto.oneof_index is out of range for type " + descriptor.getName(), anonymousClass1);
                    }
                    OneofDescriptor oneofDescriptor = descriptor.getOneofs().get(fieldDescriptorProto.getOneofIndex());
                    this.containingOneof = oneofDescriptor;
                    OneofDescriptor.d(oneofDescriptor);
                }
                this.extensionScope = null;
            }
            fileDescriptor.pool.b(this);
        }

        @Override // java.lang.Comparable
        public int compareTo(FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.containingType == this.containingType) {
                return getNumber() - fieldDescriptor.getNumber();
            }
            throw new IllegalArgumentException("FieldDescriptors can only be compared to other FieldDescriptors for fields of the same message type.");
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public EnumDescriptor getEnumType() {
            if (getJavaType() == JavaType.ENUM) {
                return this.enumType;
            }
            throw new UnsupportedOperationException(String.format("This field is not of enum type. (%s)", this.fullName));
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public DescriptorProtos.FieldDescriptorProto toProto() {
            return this.proto;
        }
    }

    public static final class FileDescriptor extends GenericDescriptor {
        private final FileDescriptor[] dependencies;
        private final EnumDescriptor[] enumTypes;
        private final FieldDescriptor[] extensions;
        private final Descriptor[] messageTypes;
        private final DescriptorPool pool;
        private DescriptorProtos.FileDescriptorProto proto;
        private final FileDescriptor[] publicDependencies;
        private final ServiceDescriptor[] services;

        @Deprecated
        public interface InternalDescriptorAssigner {
            ExtensionRegistry assignDescriptors(FileDescriptor fileDescriptor);
        }

        public enum Syntax {
            UNKNOWN("unknown"),
            PROTO2("proto2"),
            PROTO3("proto3");

            private final String name;

            Syntax(String str) {
                this.name = str;
            }
        }

        /* JADX WARN: Illegal instructions before constructor call */
        private FileDescriptor(DescriptorProtos.FileDescriptorProto fileDescriptorProto, FileDescriptor[] fileDescriptorArr, DescriptorPool descriptorPool, boolean z2) throws DescriptorValidationException {
            AnonymousClass1 anonymousClass1 = null;
            super(anonymousClass1);
            this.pool = descriptorPool;
            this.proto = fileDescriptorProto;
            this.dependencies = (FileDescriptor[]) fileDescriptorArr.clone();
            HashMap map = new HashMap();
            for (FileDescriptor fileDescriptor : fileDescriptorArr) {
                map.put(fileDescriptor.getName(), fileDescriptor);
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < fileDescriptorProto.getPublicDependencyCount(); i2++) {
                int publicDependency = fileDescriptorProto.getPublicDependency(i2);
                if (publicDependency < 0 || publicDependency >= fileDescriptorProto.getDependencyCount()) {
                    throw new DescriptorValidationException(this, "Invalid public dependency index.", anonymousClass1);
                }
                String dependency = fileDescriptorProto.getDependency(publicDependency);
                FileDescriptor fileDescriptor2 = (FileDescriptor) map.get(dependency);
                if (fileDescriptor2 != null) {
                    arrayList.add(fileDescriptor2);
                } else if (!z2) {
                    throw new DescriptorValidationException(this, "Invalid public dependency: " + dependency, anonymousClass1);
                }
            }
            FileDescriptor[] fileDescriptorArr2 = new FileDescriptor[arrayList.size()];
            this.publicDependencies = fileDescriptorArr2;
            arrayList.toArray(fileDescriptorArr2);
            descriptorPool.a(getPackage(), this);
            this.messageTypes = fileDescriptorProto.getMessageTypeCount() > 0 ? new Descriptor[fileDescriptorProto.getMessageTypeCount()] : Descriptors.EMPTY_DESCRIPTORS;
            for (int i3 = 0; i3 < fileDescriptorProto.getMessageTypeCount(); i3++) {
                this.messageTypes[i3] = new Descriptor(fileDescriptorProto.getMessageType(i3), this, null, i3, null);
            }
            this.enumTypes = fileDescriptorProto.getEnumTypeCount() > 0 ? new EnumDescriptor[fileDescriptorProto.getEnumTypeCount()] : Descriptors.EMPTY_ENUM_DESCRIPTORS;
            for (int i4 = 0; i4 < fileDescriptorProto.getEnumTypeCount(); i4++) {
                this.enumTypes[i4] = new EnumDescriptor(fileDescriptorProto.getEnumType(i4), this, null, i4, null);
            }
            this.services = fileDescriptorProto.getServiceCount() > 0 ? new ServiceDescriptor[fileDescriptorProto.getServiceCount()] : Descriptors.EMPTY_SERVICE_DESCRIPTORS;
            for (int i5 = 0; i5 < fileDescriptorProto.getServiceCount(); i5++) {
                this.services[i5] = new ServiceDescriptor(fileDescriptorProto.getService(i5), this, i5, anonymousClass1);
            }
            this.extensions = fileDescriptorProto.getExtensionCount() > 0 ? new FieldDescriptor[fileDescriptorProto.getExtensionCount()] : Descriptors.EMPTY_FIELD_DESCRIPTORS;
            for (int i6 = 0; i6 < fileDescriptorProto.getExtensionCount(); i6++) {
                this.extensions[i6] = new FieldDescriptor(fileDescriptorProto.getExtension(i6), this, null, i6, true, null);
            }
        }

        public static FileDescriptor buildFrom(DescriptorProtos.FileDescriptorProto fileDescriptorProto, FileDescriptor[] fileDescriptorArr) throws DescriptorValidationException {
            return buildFrom(fileDescriptorProto, fileDescriptorArr, false);
        }

        private void crossLink() throws DescriptorValidationException {
            for (Descriptor descriptor : this.messageTypes) {
                descriptor.crossLink();
            }
            for (ServiceDescriptor serviceDescriptor : this.services) {
                serviceDescriptor.crossLink();
            }
            for (FieldDescriptor fieldDescriptor : this.extensions) {
                fieldDescriptor.crossLink();
            }
        }

        private static FileDescriptor[] findDescriptors(Class<?> cls, String[] strArr, String[] strArr2) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < strArr.length; i2++) {
                try {
                    arrayList.add((FileDescriptor) cls.getClassLoader().loadClass(strArr[i2]).getField("descriptor").get(null));
                } catch (Exception unused) {
                    Descriptors.logger.warning("Descriptors for \"" + strArr2[i2] + "\" can not be found.");
                }
            }
            return (FileDescriptor[]) arrayList.toArray(new FileDescriptor[0]);
        }

        @Deprecated
        public static void internalBuildGeneratedFileFrom(String[] strArr, FileDescriptor[] fileDescriptorArr, InternalDescriptorAssigner internalDescriptorAssigner) {
            byte[] bArrLatin1Cat = latin1Cat(strArr);
            try {
                DescriptorProtos.FileDescriptorProto from = DescriptorProtos.FileDescriptorProto.parseFrom(bArrLatin1Cat);
                try {
                    FileDescriptor fileDescriptorBuildFrom = buildFrom(from, fileDescriptorArr, true);
                    ExtensionRegistry extensionRegistryAssignDescriptors = internalDescriptorAssigner.assignDescriptors(fileDescriptorBuildFrom);
                    if (extensionRegistryAssignDescriptors != null) {
                        try {
                            fileDescriptorBuildFrom.setProto(DescriptorProtos.FileDescriptorProto.parseFrom(bArrLatin1Cat, extensionRegistryAssignDescriptors));
                        } catch (InvalidProtocolBufferException e2) {
                            throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", e2);
                        }
                    }
                } catch (DescriptorValidationException e3) {
                    throw new IllegalArgumentException("Invalid embedded descriptor for \"" + from.getName() + "\".", e3);
                }
            } catch (InvalidProtocolBufferException e4) {
                throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", e4);
            }
        }

        public static void internalUpdateFileDescriptor(FileDescriptor fileDescriptor, ExtensionRegistry extensionRegistry) {
            try {
                fileDescriptor.setProto(DescriptorProtos.FileDescriptorProto.parseFrom(fileDescriptor.proto.toByteString(), extensionRegistry));
            } catch (InvalidProtocolBufferException e2) {
                throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", e2);
            }
        }

        private static byte[] latin1Cat(String[] strArr) {
            if (strArr.length == 1) {
                return strArr[0].getBytes(Internal.f15297c);
            }
            StringBuilder sb = new StringBuilder();
            for (String str : strArr) {
                sb.append(str);
            }
            return sb.toString().getBytes(Internal.f15297c);
        }

        private void setProto(DescriptorProtos.FileDescriptorProto fileDescriptorProto) {
            this.proto = fileDescriptorProto;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                Descriptor[] descriptorArr = this.messageTypes;
                if (i3 >= descriptorArr.length) {
                    break;
                }
                descriptorArr[i3].setProto(fileDescriptorProto.getMessageType(i3));
                i3++;
            }
            int i4 = 0;
            while (true) {
                EnumDescriptor[] enumDescriptorArr = this.enumTypes;
                if (i4 >= enumDescriptorArr.length) {
                    break;
                }
                enumDescriptorArr[i4].setProto(fileDescriptorProto.getEnumType(i4));
                i4++;
            }
            int i5 = 0;
            while (true) {
                ServiceDescriptor[] serviceDescriptorArr = this.services;
                if (i5 >= serviceDescriptorArr.length) {
                    break;
                }
                serviceDescriptorArr[i5].setProto(fileDescriptorProto.getService(i5));
                i5++;
            }
            while (true) {
                FieldDescriptor[] fieldDescriptorArr = this.extensions;
                if (i2 >= fieldDescriptorArr.length) {
                    return;
                }
                fieldDescriptorArr[i2].setProto(fileDescriptorProto.getExtension(i2));
                i2++;
            }
        }

        boolean b() {
            return getSyntax() == Syntax.PROTO3;
        }

        public EnumDescriptor findEnumTypeByName(String str) {
            if (str.indexOf(46) != -1) {
                return null;
            }
            String str2 = getPackage();
            if (!str2.isEmpty()) {
                str = str2 + '.' + str;
            }
            GenericDescriptor genericDescriptorC = this.pool.c(str);
            if ((genericDescriptorC instanceof EnumDescriptor) && genericDescriptorC.getFile() == this) {
                return (EnumDescriptor) genericDescriptorC;
            }
            return null;
        }

        public FieldDescriptor findExtensionByName(String str) {
            if (str.indexOf(46) != -1) {
                return null;
            }
            String str2 = getPackage();
            if (!str2.isEmpty()) {
                str = str2 + '.' + str;
            }
            GenericDescriptor genericDescriptorC = this.pool.c(str);
            if ((genericDescriptorC instanceof FieldDescriptor) && genericDescriptorC.getFile() == this) {
                return (FieldDescriptor) genericDescriptorC;
            }
            return null;
        }

        public Descriptor findMessageTypeByName(String str) {
            if (str.indexOf(46) != -1) {
                return null;
            }
            String str2 = getPackage();
            if (!str2.isEmpty()) {
                str = str2 + '.' + str;
            }
            GenericDescriptor genericDescriptorC = this.pool.c(str);
            if ((genericDescriptorC instanceof Descriptor) && genericDescriptorC.getFile() == this) {
                return (Descriptor) genericDescriptorC;
            }
            return null;
        }

        public ServiceDescriptor findServiceByName(String str) {
            if (str.indexOf(46) != -1) {
                return null;
            }
            String str2 = getPackage();
            if (!str2.isEmpty()) {
                str = str2 + '.' + str;
            }
            GenericDescriptor genericDescriptorC = this.pool.c(str);
            if ((genericDescriptorC instanceof ServiceDescriptor) && genericDescriptorC.getFile() == this) {
                return (ServiceDescriptor) genericDescriptorC;
            }
            return null;
        }

        public List<FileDescriptor> getDependencies() {
            return Collections.unmodifiableList(Arrays.asList(this.dependencies));
        }

        public List<EnumDescriptor> getEnumTypes() {
            return Collections.unmodifiableList(Arrays.asList(this.enumTypes));
        }

        public List<FieldDescriptor> getExtensions() {
            return Collections.unmodifiableList(Arrays.asList(this.extensions));
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public FileDescriptor getFile() {
            return this;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getFullName() {
            return this.proto.getName();
        }

        public List<Descriptor> getMessageTypes() {
            return Collections.unmodifiableList(Arrays.asList(this.messageTypes));
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getName() {
            return this.proto.getName();
        }

        public DescriptorProtos.FileOptions getOptions() {
            return this.proto.getOptions();
        }

        public String getPackage() {
            return this.proto.getPackage();
        }

        public List<FileDescriptor> getPublicDependencies() {
            return Collections.unmodifiableList(Arrays.asList(this.publicDependencies));
        }

        public List<ServiceDescriptor> getServices() {
            return Collections.unmodifiableList(Arrays.asList(this.services));
        }

        public Syntax getSyntax() {
            Syntax syntax = Syntax.PROTO3;
            return syntax.name.equals(this.proto.getSyntax()) ? syntax : Syntax.PROTO2;
        }

        public static FileDescriptor buildFrom(DescriptorProtos.FileDescriptorProto fileDescriptorProto, FileDescriptor[] fileDescriptorArr, boolean z2) throws DescriptorValidationException {
            FileDescriptor fileDescriptor = new FileDescriptor(fileDescriptorProto, fileDescriptorArr, new DescriptorPool(fileDescriptorArr, z2), z2);
            fileDescriptor.crossLink();
            return fileDescriptor;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public DescriptorProtos.FileDescriptorProto toProto() {
            return this.proto;
        }

        public static FileDescriptor internalBuildGeneratedFileFrom(String[] strArr, FileDescriptor[] fileDescriptorArr) {
            try {
                DescriptorProtos.FileDescriptorProto from = DescriptorProtos.FileDescriptorProto.parseFrom(latin1Cat(strArr));
                try {
                    return buildFrom(from, fileDescriptorArr, true);
                } catch (DescriptorValidationException e2) {
                    throw new IllegalArgumentException("Invalid embedded descriptor for \"" + from.getName() + "\".", e2);
                }
            } catch (InvalidProtocolBufferException e3) {
                throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", e3);
            }
        }

        @Deprecated
        public static void internalBuildGeneratedFileFrom(String[] strArr, Class<?> cls, String[] strArr2, String[] strArr3, InternalDescriptorAssigner internalDescriptorAssigner) {
            internalBuildGeneratedFileFrom(strArr, findDescriptors(cls, strArr2, strArr3), internalDescriptorAssigner);
        }

        public static FileDescriptor internalBuildGeneratedFileFrom(String[] strArr, Class<?> cls, String[] strArr2, String[] strArr3) {
            return internalBuildGeneratedFileFrom(strArr, findDescriptors(cls, strArr2, strArr3));
        }

        FileDescriptor(String str, Descriptor descriptor) throws DescriptorValidationException {
            super(null);
            DescriptorPool descriptorPool = new DescriptorPool(new FileDescriptor[0], true);
            this.pool = descriptorPool;
            this.proto = DescriptorProtos.FileDescriptorProto.newBuilder().setName(descriptor.getFullName() + ".placeholder.proto").setPackage(str).addMessageType(descriptor.toProto()).build();
            this.dependencies = new FileDescriptor[0];
            this.publicDependencies = new FileDescriptor[0];
            this.messageTypes = new Descriptor[]{descriptor};
            this.enumTypes = Descriptors.EMPTY_ENUM_DESCRIPTORS;
            this.services = Descriptors.EMPTY_SERVICE_DESCRIPTORS;
            this.extensions = Descriptors.EMPTY_FIELD_DESCRIPTORS;
            descriptorPool.a(str, this);
            descriptorPool.b(descriptor);
        }
    }

    public static abstract class GenericDescriptor {
        private GenericDescriptor() {
        }

        public abstract FileDescriptor getFile();

        public abstract String getFullName();

        public abstract String getName();

        public abstract Message toProto();

        /* synthetic */ GenericDescriptor(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public static final class MethodDescriptor extends GenericDescriptor {
        private final FileDescriptor file;
        private final String fullName;
        private final int index;
        private Descriptor inputType;
        private Descriptor outputType;
        private DescriptorProtos.MethodDescriptorProto proto;
        private final ServiceDescriptor service;

        /* synthetic */ MethodDescriptor(DescriptorProtos.MethodDescriptorProto methodDescriptorProto, FileDescriptor fileDescriptor, ServiceDescriptor serviceDescriptor, int i2, AnonymousClass1 anonymousClass1) {
            this(methodDescriptorProto, fileDescriptor, serviceDescriptor, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void crossLink() throws DescriptorValidationException {
            DescriptorPool descriptorPool = getFile().pool;
            String inputType = this.proto.getInputType();
            DescriptorPool.SearchFilter searchFilter = DescriptorPool.SearchFilter.TYPES_ONLY;
            GenericDescriptor genericDescriptorG = descriptorPool.g(inputType, this, searchFilter);
            AnonymousClass1 anonymousClass1 = null;
            if (!(genericDescriptorG instanceof Descriptor)) {
                throw new DescriptorValidationException(this, Typography.quote + this.proto.getInputType() + "\" is not a message type.", anonymousClass1);
            }
            this.inputType = (Descriptor) genericDescriptorG;
            GenericDescriptor genericDescriptorG2 = getFile().pool.g(this.proto.getOutputType(), this, searchFilter);
            if (genericDescriptorG2 instanceof Descriptor) {
                this.outputType = (Descriptor) genericDescriptorG2;
                return;
            }
            throw new DescriptorValidationException(this, Typography.quote + this.proto.getOutputType() + "\" is not a message type.", anonymousClass1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProto(DescriptorProtos.MethodDescriptorProto methodDescriptorProto) {
            this.proto = methodDescriptorProto;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public FileDescriptor getFile() {
            return this.file;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getFullName() {
            return this.fullName;
        }

        public int getIndex() {
            return this.index;
        }

        public Descriptor getInputType() {
            return this.inputType;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getName() {
            return this.proto.getName();
        }

        public DescriptorProtos.MethodOptions getOptions() {
            return this.proto.getOptions();
        }

        public Descriptor getOutputType() {
            return this.outputType;
        }

        public ServiceDescriptor getService() {
            return this.service;
        }

        public boolean isClientStreaming() {
            return this.proto.getClientStreaming();
        }

        public boolean isServerStreaming() {
            return this.proto.getServerStreaming();
        }

        private MethodDescriptor(DescriptorProtos.MethodDescriptorProto methodDescriptorProto, FileDescriptor fileDescriptor, ServiceDescriptor serviceDescriptor, int i2) throws DescriptorValidationException {
            super(null);
            this.index = i2;
            this.proto = methodDescriptorProto;
            this.file = fileDescriptor;
            this.service = serviceDescriptor;
            this.fullName = serviceDescriptor.getFullName() + '.' + methodDescriptorProto.getName();
            fileDescriptor.pool.b(this);
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public DescriptorProtos.MethodDescriptorProto toProto() {
            return this.proto;
        }
    }

    private interface NumberGetter<T> {
        int getNumber(T t2);
    }

    public static final class OneofDescriptor extends GenericDescriptor {
        private Descriptor containingType;
        private int fieldCount;
        private FieldDescriptor[] fields;
        private final FileDescriptor file;
        private final String fullName;
        private final int index;
        private DescriptorProtos.OneofDescriptorProto proto;

        /* synthetic */ OneofDescriptor(DescriptorProtos.OneofDescriptorProto oneofDescriptorProto, FileDescriptor fileDescriptor, Descriptor descriptor, int i2, AnonymousClass1 anonymousClass1) {
            this(oneofDescriptorProto, fileDescriptor, descriptor, i2);
        }

        static /* synthetic */ int d(OneofDescriptor oneofDescriptor) {
            int i2 = oneofDescriptor.fieldCount;
            oneofDescriptor.fieldCount = i2 + 1;
            return i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProto(DescriptorProtos.OneofDescriptorProto oneofDescriptorProto) {
            this.proto = oneofDescriptorProto;
        }

        public Descriptor getContainingType() {
            return this.containingType;
        }

        public FieldDescriptor getField(int i2) {
            return this.fields[i2];
        }

        public int getFieldCount() {
            return this.fieldCount;
        }

        public List<FieldDescriptor> getFields() {
            return Collections.unmodifiableList(Arrays.asList(this.fields));
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public FileDescriptor getFile() {
            return this.file;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getFullName() {
            return this.fullName;
        }

        public int getIndex() {
            return this.index;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getName() {
            return this.proto.getName();
        }

        public DescriptorProtos.OneofOptions getOptions() {
            return this.proto.getOptions();
        }

        public boolean isSynthetic() {
            FieldDescriptor[] fieldDescriptorArr = this.fields;
            return fieldDescriptorArr.length == 1 && fieldDescriptorArr[0].isProto3Optional;
        }

        private OneofDescriptor(DescriptorProtos.OneofDescriptorProto oneofDescriptorProto, FileDescriptor fileDescriptor, Descriptor descriptor, int i2) {
            super(null);
            this.proto = oneofDescriptorProto;
            this.fullName = Descriptors.computeFullName(fileDescriptor, descriptor, oneofDescriptorProto.getName());
            this.file = fileDescriptor;
            this.index = i2;
            this.containingType = descriptor;
            this.fieldCount = 0;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public DescriptorProtos.OneofDescriptorProto toProto() {
            return this.proto;
        }
    }

    public static final class ServiceDescriptor extends GenericDescriptor {
        private final FileDescriptor file;
        private final String fullName;
        private final int index;
        private MethodDescriptor[] methods;
        private DescriptorProtos.ServiceDescriptorProto proto;

        /* synthetic */ ServiceDescriptor(DescriptorProtos.ServiceDescriptorProto serviceDescriptorProto, FileDescriptor fileDescriptor, int i2, AnonymousClass1 anonymousClass1) {
            this(serviceDescriptorProto, fileDescriptor, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void crossLink() throws DescriptorValidationException {
            for (MethodDescriptor methodDescriptor : this.methods) {
                methodDescriptor.crossLink();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProto(DescriptorProtos.ServiceDescriptorProto serviceDescriptorProto) {
            this.proto = serviceDescriptorProto;
            int i2 = 0;
            while (true) {
                MethodDescriptor[] methodDescriptorArr = this.methods;
                if (i2 >= methodDescriptorArr.length) {
                    return;
                }
                methodDescriptorArr[i2].setProto(serviceDescriptorProto.getMethod(i2));
                i2++;
            }
        }

        public MethodDescriptor findMethodByName(String str) {
            GenericDescriptor genericDescriptorC = this.file.pool.c(this.fullName + '.' + str);
            if (genericDescriptorC instanceof MethodDescriptor) {
                return (MethodDescriptor) genericDescriptorC;
            }
            return null;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public FileDescriptor getFile() {
            return this.file;
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getFullName() {
            return this.fullName;
        }

        public int getIndex() {
            return this.index;
        }

        public List<MethodDescriptor> getMethods() {
            return Collections.unmodifiableList(Arrays.asList(this.methods));
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public String getName() {
            return this.proto.getName();
        }

        public DescriptorProtos.ServiceOptions getOptions() {
            return this.proto.getOptions();
        }

        private ServiceDescriptor(DescriptorProtos.ServiceDescriptorProto serviceDescriptorProto, FileDescriptor fileDescriptor, int i2) throws DescriptorValidationException {
            super(null);
            this.index = i2;
            this.proto = serviceDescriptorProto;
            this.fullName = Descriptors.computeFullName(fileDescriptor, null, serviceDescriptorProto.getName());
            this.file = fileDescriptor;
            this.methods = new MethodDescriptor[serviceDescriptorProto.getMethodCount()];
            for (int i3 = 0; i3 < serviceDescriptorProto.getMethodCount(); i3++) {
                this.methods[i3] = new MethodDescriptor(serviceDescriptorProto.getMethod(i3), fileDescriptor, this, i3, null);
            }
            fileDescriptor.pool.b(this);
        }

        @Override // com.google.protobuf.Descriptors.GenericDescriptor
        public DescriptorProtos.ServiceDescriptorProto toProto() {
            return this.proto;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T binarySearch(T[] tArr, int i2, NumberGetter<T> numberGetter, int i3) {
        int i4 = i2 - 1;
        int i5 = 0;
        while (i5 <= i4) {
            int i6 = (i5 + i4) / 2;
            T t2 = tArr[i6];
            int number = numberGetter.getNumber(t2);
            if (i3 < number) {
                i4 = i6 - 1;
            } else {
                if (i3 <= number) {
                    return t2;
                }
                i5 = i6 + 1;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String computeFullName(FileDescriptor fileDescriptor, Descriptor descriptor, String str) {
        if (descriptor != null) {
            return descriptor.getFullName() + '.' + str;
        }
        String str2 = fileDescriptor.getPackage();
        if (str2.isEmpty()) {
            return str;
        }
        return str2 + '.' + str;
    }
}
