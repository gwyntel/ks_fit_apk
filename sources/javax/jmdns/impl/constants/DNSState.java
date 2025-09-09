package javax.jmdns.impl.constants;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'PROBING_1' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes4.dex */
public final class DNSState {
    private static final /* synthetic */ DNSState[] $VALUES;
    public static final DNSState ANNOUNCED;
    public static final DNSState ANNOUNCING_1;
    public static final DNSState ANNOUNCING_2;
    public static final DNSState CANCELED;
    public static final DNSState CANCELING_1;
    public static final DNSState CANCELING_2;
    public static final DNSState CANCELING_3;
    public static final DNSState CLOSED;
    public static final DNSState CLOSING;
    public static final DNSState PROBING_1;
    public static final DNSState PROBING_2;
    public static final DNSState PROBING_3;
    private final String _name;
    private final StateClass _state;

    /* renamed from: javax.jmdns.impl.constants.DNSState$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f25385a;

        static {
            int[] iArr = new int[DNSState.values().length];
            f25385a = iArr;
            try {
                iArr[DNSState.PROBING_1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25385a[DNSState.PROBING_2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f25385a[DNSState.PROBING_3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f25385a[DNSState.ANNOUNCING_1.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f25385a[DNSState.ANNOUNCING_2.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f25385a[DNSState.ANNOUNCED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f25385a[DNSState.CANCELING_1.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f25385a[DNSState.CANCELING_2.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f25385a[DNSState.CANCELING_3.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f25385a[DNSState.CANCELED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f25385a[DNSState.CLOSING.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f25385a[DNSState.CLOSED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    private enum StateClass {
        probing,
        announcing,
        announced,
        canceling,
        canceled,
        closing,
        closed
    }

    static {
        StateClass stateClass = StateClass.probing;
        DNSState dNSState = new DNSState("PROBING_1", 0, "probing 1", stateClass);
        PROBING_1 = dNSState;
        DNSState dNSState2 = new DNSState("PROBING_2", 1, "probing 2", stateClass);
        PROBING_2 = dNSState2;
        DNSState dNSState3 = new DNSState("PROBING_3", 2, "probing 3", stateClass);
        PROBING_3 = dNSState3;
        StateClass stateClass2 = StateClass.announcing;
        DNSState dNSState4 = new DNSState("ANNOUNCING_1", 3, "announcing 1", stateClass2);
        ANNOUNCING_1 = dNSState4;
        DNSState dNSState5 = new DNSState("ANNOUNCING_2", 4, "announcing 2", stateClass2);
        ANNOUNCING_2 = dNSState5;
        DNSState dNSState6 = new DNSState("ANNOUNCED", 5, "announced", StateClass.announced);
        ANNOUNCED = dNSState6;
        StateClass stateClass3 = StateClass.canceling;
        DNSState dNSState7 = new DNSState("CANCELING_1", 6, "canceling 1", stateClass3);
        CANCELING_1 = dNSState7;
        DNSState dNSState8 = new DNSState("CANCELING_2", 7, "canceling 2", stateClass3);
        CANCELING_2 = dNSState8;
        DNSState dNSState9 = new DNSState("CANCELING_3", 8, "canceling 3", stateClass3);
        CANCELING_3 = dNSState9;
        DNSState dNSState10 = new DNSState("CANCELED", 9, "canceled", StateClass.canceled);
        CANCELED = dNSState10;
        DNSState dNSState11 = new DNSState("CLOSING", 10, "closing", StateClass.closing);
        CLOSING = dNSState11;
        DNSState dNSState12 = new DNSState("CLOSED", 11, "closed", StateClass.closed);
        CLOSED = dNSState12;
        $VALUES = new DNSState[]{dNSState, dNSState2, dNSState3, dNSState4, dNSState5, dNSState6, dNSState7, dNSState8, dNSState9, dNSState10, dNSState11, dNSState12};
    }

    private DNSState(String str, int i2, String str2, StateClass stateClass) {
        this._name = str2;
        this._state = stateClass;
    }

    public static DNSState valueOf(String str) {
        return (DNSState) Enum.valueOf(DNSState.class, str);
    }

    public static DNSState[] values() {
        return (DNSState[]) $VALUES.clone();
    }

    public final DNSState advance() {
        switch (AnonymousClass1.f25385a[ordinal()]) {
            case 1:
                return PROBING_2;
            case 2:
                return PROBING_3;
            case 3:
                return ANNOUNCING_1;
            case 4:
                return ANNOUNCING_2;
            case 5:
                return ANNOUNCED;
            case 6:
                return ANNOUNCED;
            case 7:
                return CANCELING_2;
            case 8:
                return CANCELING_3;
            case 9:
                return CANCELED;
            case 10:
                return CANCELED;
            case 11:
                return CLOSED;
            case 12:
                return CLOSED;
            default:
                return this;
        }
    }

    public final boolean isAnnounced() {
        return this._state == StateClass.announced;
    }

    public final boolean isAnnouncing() {
        return this._state == StateClass.announcing;
    }

    public final boolean isCanceled() {
        return this._state == StateClass.canceled;
    }

    public final boolean isCanceling() {
        return this._state == StateClass.canceling;
    }

    public final boolean isClosed() {
        return this._state == StateClass.closed;
    }

    public final boolean isClosing() {
        return this._state == StateClass.closing;
    }

    public final boolean isProbing() {
        return this._state == StateClass.probing;
    }

    public final DNSState revert() {
        switch (AnonymousClass1.f25385a[ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return PROBING_1;
            case 7:
            case 8:
            case 9:
                return CANCELING_1;
            case 10:
                return CANCELED;
            case 11:
                return CLOSING;
            case 12:
                return CLOSED;
            default:
                return this;
        }
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this._name;
    }
}
