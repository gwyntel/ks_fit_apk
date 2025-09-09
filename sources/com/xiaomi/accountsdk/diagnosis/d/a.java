package com.xiaomi.accountsdk.diagnosis.d;

import android.text.TextUtils;
import android.util.Base64;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.MemoryLruHelper;
import com.xiaomi.infra.galaxy.fds.android.FDSClientConfiguration;
import com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl;
import com.xiaomi.infra.galaxy.fds.android.auth.SignatureCredential;
import com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException;
import com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult;
import java.io.File;

/* loaded from: classes4.dex */
public class a {
    public static PutObjectResult a(File file, String str) {
        FDSClientConfiguration fDSClientConfiguration = new FDSClientConfiguration();
        if (TextUtils.isEmpty(str)) {
            str = a("G25iajFoZiJzLmFAWR5IWVFfXVkeU19d");
        }
        fDSClientConfiguration.setEndpoint(str);
        fDSClientConfiguration.enableHttps(true);
        fDSClientConfiguration.enableCdnForUpload(false);
        fDSClientConfiguration.setUploadPartSize(MemoryLruHelper.MAX_LRU_SIZE);
        fDSClientConfiguration.setCredential(new SignatureCredential(a("OUtaT1NwVAs2RlAEeAcEdQV4"), a("PUtCcHIzdy5ZcDZaamQbUmR+d0QIWGZ0ZwVbeWABfHt8SF9oekJXBA==")));
        try {
            return new GalaxyFDSClientImpl(fDSClientConfiguration).putObject(a("HGlhZ24pcy9z"), file.getName(), file);
        } catch (GalaxyFDSClientException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String a(String str) {
        return new String(a(Base64.decode(str, 2)));
    }

    private static byte[] a(byte[] bArr) {
        byte[] bArr2 = new byte[256];
        for (int i2 = 0; i2 < "0xCAFEBABE".getBytes().length; i2++) {
            bArr2[i2] = "0xCAFEBABE".getBytes()[i2 % "0xCAFEBABE".getBytes().length];
        }
        int length = bArr.length;
        byte[] bArr3 = new byte[length];
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            i3 = (i3 + 1) & 255;
            byte b2 = bArr2[i3];
            i4 = (i4 + b2) & 255;
            bArr2[i3] = bArr2[i4];
            byte b3 = b2;
            bArr2[i4] = b3;
            bArr3[i5] = (byte) (bArr2[(b3 + bArr2[i3]) & 255] ^ bArr[i5]);
        }
        return bArr3;
    }
}
