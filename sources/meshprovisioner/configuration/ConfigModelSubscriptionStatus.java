package meshprovisioner.configuration;

import a.a.a.a.b.m.a;
import android.content.Context;
import b.InterfaceC0328c;
import b.d.b;
import b.d.c;
import com.alibaba.ailabs.iot.mesh.R;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes5.dex */
public class ConfigModelSubscriptionStatus extends ConfigMessageState {
    public static final int CONFIG_MODEL_APP_BIND_STATUS_VENDOR_MODEL_PDU_LENGTH = 11;
    public static final int CONFIG_MODEL_PUBLICATION_STATUS_SIG_MODEL_PDU_LENGTH = 9;
    public static final String TAG = ConfigModelAppStatus.class.getSimpleName();
    public byte[] elementAddress;
    public boolean isSuccessful;
    public byte[] mSubscriptionAddress;
    public final int messageType;
    public byte[] modelIdentifier;
    public int status;
    public String statusMessage;

    /* renamed from: meshprovisioner.configuration.ConfigModelSubscriptionStatus$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus;

        static {
            int[] iArr = new int[SubscriptionStatus.values().length];
            $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus = iArr;
            try {
                iArr[SubscriptionStatus.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.INVALID_ADDRESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.INVALID_MODEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.INVALID_APPKEY_INDEX.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.INVALID_NETKEY_INDEX.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.INSUFFICIENT_RESOURCES.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.KEY_INDEX_ALREADY_STORED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.INVALID_PUBLISH_PARAMETERS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.NOT_A_SUBSCRIBE_MODEL.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.STORAGE_FAILURE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.FEATURE_NOT_SUPPORTED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.CANNOT_UPDATE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.CANNOT_REMOVE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.CANNOT_BIND.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.TEMPORARILY_UNABLE_TO_CHANGE_STATE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.CANNOT_SET.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.UNSPECIFIED_ERROR.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.INVALID_BINDING.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.RFU.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
        }
    }

    public enum SubscriptionStatus {
        SUCCESS(0),
        INVALID_ADDRESS(1),
        INVALID_MODEL(2),
        INVALID_APPKEY_INDEX(3),
        INVALID_NETKEY_INDEX(4),
        INSUFFICIENT_RESOURCES(5),
        KEY_INDEX_ALREADY_STORED(6),
        INVALID_PUBLISH_PARAMETERS(7),
        NOT_A_SUBSCRIBE_MODEL(8),
        STORAGE_FAILURE(9),
        FEATURE_NOT_SUPPORTED(10),
        CANNOT_UPDATE(11),
        CANNOT_REMOVE(12),
        CANNOT_BIND(13),
        TEMPORARILY_UNABLE_TO_CHANGE_STATE(14),
        CANNOT_SET(15),
        UNSPECIFIED_ERROR(16),
        INVALID_BINDING(17),
        RFU(18);

        public final int statusCode;

        SubscriptionStatus(int i2) {
            this.statusCode = i2;
        }

        public static SubscriptionStatus fromStatusCode(int i2) {
            for (SubscriptionStatus subscriptionStatus : values()) {
                if (subscriptionStatus.getStatusCode() == i2) {
                    return subscriptionStatus;
                }
            }
            throw new IllegalArgumentException("Enum not found in AppKeyStatus");
        }

        public final int getStatusCode() {
            return this.statusCode;
        }
    }

    public ConfigModelSubscriptionStatus(Context context, ProvisionedMeshNode provisionedMeshNode, int i2, InterfaceC0328c interfaceC0328c) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.messageType = i2;
    }

    private int getElementAddressInt() {
        return ByteBuffer.wrap(this.elementAddress).order(ByteOrder.BIG_ENDIAN).getShort();
    }

    private int getModelIdentifierInt() {
        byte[] bArr = this.modelIdentifier;
        return bArr.length == 2 ? ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getShort() : ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    private void parseStatus(int i2) {
        if (AnonymousClass1.$SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.fromStatusCode(i2).ordinal()] != 1) {
            return;
        }
        this.isSuccessful = true;
    }

    public static String parseStatusMessage(Context context, int i2) {
        switch (AnonymousClass1.$SwitchMap$meshprovisioner$configuration$ConfigModelSubscriptionStatus$SubscriptionStatus[SubscriptionStatus.fromStatusCode(i2).ordinal()]) {
            case 1:
                return context.getString(R.string.model_subscription_success);
            case 2:
                return context.getString(R.string.status_invalid_address);
            case 3:
                return context.getString(R.string.status_invalid_model);
            case 4:
                return context.getString(R.string.status_invalid_appkey_index);
            case 5:
                return context.getString(R.string.status_invalid_netkey_index);
            case 6:
                return context.getString(R.string.status_insufficient_resources);
            case 7:
                return context.getString(R.string.status_key_index_already_stored);
            case 8:
                return context.getString(R.string.status_invalid_publish_parameters);
            case 9:
                return context.getString(R.string.status_not_a_subscribe_model);
            case 10:
                return context.getString(R.string.status_storage_failure);
            case 11:
                return context.getString(R.string.status_feature_not_supported);
            case 12:
                return context.getString(R.string.status_cannot_update);
            case 13:
                return context.getString(R.string.status_cannot_remove);
            case 14:
                return context.getString(R.string.status_cannot_bind);
            case 15:
                return context.getString(R.string.status_temporarily_unable_to_change_state);
            case 16:
                return context.getString(R.string.status_cannot_set);
            case 17:
                return context.getString(R.string.status_unspecified_error);
            case 18:
                return context.getString(R.string.status_success_invalid_binding);
            default:
                return context.getString(R.string.app_key_status_rfu);
        }
    }

    public byte[] getElementAddress() {
        return this.elementAddress;
    }

    public byte[] getModelIdentifier() {
        return this.modelIdentifier;
    }

    @Override // meshprovisioner.configuration.ConfigMessageState, meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return MeshMessageState.MessageState.CONFIG_MODEL_SUBSCRIPTION_STATUS_STATE;
    }

    public int getStatus() {
        return this.status;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public byte[] getSubscriptionAddress() {
        return this.mSubscriptionAddress;
    }

    public int getSubscriptionAddressInt() {
        return ByteBuffer.wrap(this.mSubscriptionAddress).order(ByteOrder.BIG_ENDIAN).getShort();
    }

    public boolean isSuccessful() {
        return this.isSuccessful;
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public final boolean parseMeshPdu(byte[] bArr) {
        c pdu = this.mMeshTransport.parsePdu(this.mSrc, bArr);
        if (pdu == null) {
            a.a(TAG, "Message reassembly may not be complete yet");
        } else if (pdu instanceof b.d.a) {
            byte[] bArrU = ((b.d.a) pdu).u();
            if (MeshParserUtils.getOpCode(bArrU, ((bArrU[0] >> 7) & 1) + 1) == -32737) {
                String str = TAG;
                a.a(str, "Received model subscription status");
                byte b2 = bArrU[2];
                this.status = b2;
                this.elementAddress = new byte[]{bArrU[4], bArrU[3]};
                this.mSubscriptionAddress = new byte[]{bArrU[6], bArrU[5]};
                if (bArrU.length == 9) {
                    this.modelIdentifier = new byte[]{bArrU[8], bArrU[7]};
                } else {
                    this.modelIdentifier = new byte[]{bArrU[8], bArrU[7], bArrU[10], bArrU[9]};
                }
                this.statusMessage = parseStatusMessage(this.mContext, b2);
                parseStatus(this.status);
                a.a(str, "Status: " + this.status);
                a.a(str, "Status message: " + this.statusMessage);
                a.a(str, "Element Address: " + MeshParserUtils.bytesToHex(this.elementAddress, false));
                a.a(str, "Subscription Address: " + MeshParserUtils.bytesToHex(this.mSubscriptionAddress, false));
                a.a(str, "Model Identifier: " + MeshParserUtils.bytesToHex(this.modelIdentifier, false));
                if (this.isSuccessful) {
                    MeshModel meshModel = this.mProvisionedMeshNode.getElements().get(Integer.valueOf(getElementAddressInt())).getMeshModels().get(Integer.valueOf(getModelIdentifierInt()));
                    int i2 = this.messageType;
                    if (i2 == 32795) {
                        meshModel.setPublicationStatus(this.mSubscriptionAddress);
                    } else if (i2 == 32796) {
                        meshModel.removeSubscriptionAddress(this.mSubscriptionAddress);
                    }
                }
                this.mInternalTransportCallbacks.a(this.mProvisionedMeshNode);
                this.mMeshStatusCallbacks.onSubscriptionStatusReceived(this.mProvisionedMeshNode, this.isSuccessful, this.status, this.elementAddress, this.mSubscriptionAddress, getModelIdentifierInt());
                return true;
            }
            this.mMeshStatusCallbacks.onUnknownPduReceived(this.mProvisionedMeshNode);
        } else {
            parseControlMessage((b) pdu, this.mPayloads.size());
        }
        return false;
    }

    @Override // b.f.f
    public void sendSegmentAcknowledgementMessage(b bVar) {
        b bVarCreateSegmentBlockAcknowledgementMessage = this.mMeshTransport.createSegmentBlockAcknowledgementMessage(bVar);
        a.a(TAG, "Sending acknowledgement: " + MeshParserUtils.bytesToHex(bVarCreateSegmentBlockAcknowledgementMessage.m().get(0), false));
        this.mInternalTransportCallbacks.sendPdu(this.mProvisionedMeshNode, bVarCreateSegmentBlockAcknowledgementMessage.m().get(0));
        this.mMeshStatusCallbacks.onBlockAcknowledgementSent(this.mProvisionedMeshNode);
    }
}
