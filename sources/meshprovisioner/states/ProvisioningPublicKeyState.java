package meshprovisioner.states;

import a.a.a.a.b.m.a;
import b.InterfaceC0329d;
import b.p;
import c.a.d.b;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.KeyAgreement;
import meshprovisioner.states.ProvisioningState;
import meshprovisioner.utils.MeshParserUtils;
import org.spongycastle.jce.ECNamedCurveTable;
import org.spongycastle.jce.interfaces.ECPrivateKey;
import org.spongycastle.jce.interfaces.ECPublicKey;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECPublicKeySpec;
import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes5.dex */
public class ProvisioningPublicKeyState extends ProvisioningState {
    public static final int PROVISIONING_PUBLIC_KEY_XY_PDU_LENGTH = 69;
    public final InterfaceC0329d mInternalTransportCallbacks;
    public final p mMeshProvisioningStatusCallbacks;
    public byte[] mTempProvisioneeXY;
    public final UnprovisionedMeshNode mUnprovisionedMeshNode;
    public final String TAG = ProvisioningPublicKeyState.class.getSimpleName();
    public final byte[] publicKeyXY = new byte[69];
    public int segmentCount = 0;
    public ECPrivateKey mProvisionerPrivaetKey = null;

    public ProvisioningPublicKeyState(UnprovisionedMeshNode unprovisionedMeshNode, InterfaceC0329d interfaceC0329d, p pVar) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        this.mUnprovisionedMeshNode = unprovisionedMeshNode;
        this.mMeshProvisioningStatusCallbacks = pVar;
        this.mInternalTransportCallbacks = interfaceC0329d;
        generateKeyPairs();
    }

    private byte[] convertToLittleEndian(byte[] bArr, ByteOrder byteOrder) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length);
        byteBufferAllocate.order(byteOrder);
        byteBufferAllocate.put(bArr);
        return byteBufferAllocate.array();
    }

    private void generateKeyPairs() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        try {
            ECNamedCurveParameterSpec parameterSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
            keyPairGenerator.initialize(parameterSpec);
            KeyPair keyPairGenerateKeyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey eCPublicKey = (ECPublicKey) keyPairGenerateKeyPair.getPublic();
            this.mProvisionerPrivaetKey = (ECPrivateKey) keyPairGenerateKeyPair.getPrivate();
            ECPoint q2 = eCPublicKey.getQ();
            BigInteger bigInteger = q2.getXCoord().toBigInteger();
            BigInteger bigInteger2 = q2.getYCoord().toBigInteger();
            byte[] bArrA = b.a(32, bigInteger);
            byte[] bArrA2 = b.a(32, bigInteger2);
            String str = this.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("X: length: ");
            sb.append(bArrA.length);
            sb.append(" ");
            sb.append(MeshParserUtils.bytesToHex(bArrA, false));
            a.a(str, sb.toString());
            String str2 = this.TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Y: length: ");
            sb2.append(bArrA2.length);
            sb2.append(" ");
            sb2.append(MeshParserUtils.bytesToHex(bArrA2, false));
            a.a(str2, sb2.toString());
            byte[] bArr = new byte[64];
            System.arraycopy(bArrA, 0, bArr, 0, bArrA.length);
            System.arraycopy(bArrA2, 0, bArr, bArrA2.length, bArrA2.length);
            this.mUnprovisionedMeshNode.setProvisionerPublicKeyXY(bArr);
            String str3 = this.TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("XY: ");
            sb3.append(MeshParserUtils.bytesToHex(bArr, true));
            a.a(str3, sb3.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private byte[] generatePublicKeyXYPDU() {
        byte[] provisionerPublicKeyXY = this.mUnprovisionedMeshNode.getProvisionerPublicKeyXY();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(provisionerPublicKeyXY.length + 2);
        byteBufferAllocate.put((byte) 3);
        byteBufferAllocate.put((byte) 3);
        byteBufferAllocate.put(provisionerPublicKeyXY);
        return byteBufferAllocate.array();
    }

    private boolean generateSharedECDHSecret(byte[] bArr) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length - 2);
        byteBufferAllocate.put(bArr, 2, byteBufferAllocate.limit());
        byte[] bArrArray = byteBufferAllocate.array();
        this.mTempProvisioneeXY = bArrArray;
        this.mUnprovisionedMeshNode.setProvisioneePublicKeyXY(bArrArray);
        byte[] bArr2 = new byte[32];
        System.arraycopy(bArrArray, 0, bArr2, 0, 32);
        byte[] bArr3 = new byte[32];
        System.arraycopy(bArrArray, 32, bArr3, 0, 32);
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        byte[] bArrConvertToLittleEndian = convertToLittleEndian(bArr2, byteOrder);
        a.a(this.TAG, "Provsionee X: " + MeshParserUtils.bytesToHex(bArrConvertToLittleEndian, false));
        byte[] bArrConvertToLittleEndian2 = convertToLittleEndian(bArr3, byteOrder);
        a.a(this.TAG, "Provsionee Y: " + MeshParserUtils.bytesToHex(bArrConvertToLittleEndian2, false));
        BigInteger bigIntegerA = b.a(bArrArray, 0, 32);
        BigInteger bigIntegerA2 = b.a(bArrArray, 32, 32);
        ECNamedCurveParameterSpec parameterSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
        try {
            try {
                ECPublicKey eCPublicKey = (ECPublicKey) KeyFactory.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME).generatePublic(new ECPublicKeySpec(parameterSpec.getCurve().validatePoint(bigIntegerA, bigIntegerA2), parameterSpec));
                KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
                keyAgreement.init(this.mProvisionerPrivaetKey);
                keyAgreement.doPhase(eCPublicKey, true);
                byte[] bArrGenerateSecret = keyAgreement.generateSecret();
                this.mUnprovisionedMeshNode.setSharedECDHSecret(bArrGenerateSecret);
                String str = this.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("ECDH Secret: ");
                sb.append(MeshParserUtils.bytesToHex(bArrGenerateSecret, false));
                a.a(str, sb.toString());
                return true;
            } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e2) {
                a.b(this.TAG, e2.toString());
                return false;
            }
        } catch (IllegalArgumentException e3) {
            a.b(this.TAG, e3.toString());
            return false;
        }
    }

    @Override // meshprovisioner.states.ProvisioningState
    public void executeSend() {
        if (this.mProvisionerPrivaetKey == null) {
            generateKeyPairs();
        }
        try {
            this.mMeshProvisioningStatusCallbacks.onProvisioningPublicKeySent(this.mUnprovisionedMeshNode);
            this.mInternalTransportCallbacks.sendPdu(this.mUnprovisionedMeshNode, generatePublicKeyXYPDU());
        } catch (Exception unused) {
        }
    }

    @Override // meshprovisioner.states.ProvisioningState
    public ProvisioningState.State getState() {
        return ProvisioningState.State.PROVISIONING_PUBLIC_KEY;
    }

    @Override // meshprovisioner.states.ProvisioningState
    public boolean parseData(byte[] bArr) {
        this.mMeshProvisioningStatusCallbacks.onProvisioningPublicKeyReceived(this.mUnprovisionedMeshNode);
        return generateSharedECDHSecret(bArr);
    }
}
