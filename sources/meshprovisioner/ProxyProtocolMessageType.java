package meshprovisioner;

/* loaded from: classes5.dex */
public enum ProxyProtocolMessageType {
    NetworkPDU(0),
    MeshBeacon(1),
    ProxyConfiguration(2),
    ProvisioningPDU(3),
    RFU(4);

    public int type;

    ProxyProtocolMessageType(int i2) {
        this.type = i2;
    }
}
