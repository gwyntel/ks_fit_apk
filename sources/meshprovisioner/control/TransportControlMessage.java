package meshprovisioner.control;

/* loaded from: classes5.dex */
public abstract class TransportControlMessage {

    public enum TransportControlMessageState {
        LOWER_TRANSPORT_BLOCK_ACKNOWLEDGEMENT(0);

        public int state;

        TransportControlMessageState(int i2) {
            this.state = i2;
        }

        public int getState() {
            return this.state;
        }
    }

    public abstract TransportControlMessageState a();
}
