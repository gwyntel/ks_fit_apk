package meshprovisioner;

/* loaded from: classes5.dex */
public enum ProxyCommunicationQuality {
    QUALITY_LEVE_3(3),
    QUALITY_LEVE_2(2),
    QUALITY_LEVE_1(1),
    QUALITY_LEVE_0(0);

    public int level;

    ProxyCommunicationQuality(int i2) {
        this.level = i2;
    }

    public static ProxyCommunicationQuality getQualityViaLevel(int i2) {
        return i2 == 3 ? QUALITY_LEVE_3 : i2 == 2 ? QUALITY_LEVE_2 : i2 == 1 ? QUALITY_LEVE_1 : QUALITY_LEVE_0;
    }

    public static ProxyCommunicationQuality getQualityViaRssi(int i2) {
        return i2 >= -50 ? QUALITY_LEVE_3 : i2 >= -70 ? QUALITY_LEVE_2 : i2 >= -90 ? QUALITY_LEVE_1 : QUALITY_LEVE_0;
    }

    public int getLevel() {
        return this.level;
    }
}
