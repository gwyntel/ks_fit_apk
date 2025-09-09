package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes3.dex */
public abstract class AbstractExpandedDecoder {
    private final GeneralAppIdDecoder generalDecoder;
    private final BitArray information;

    AbstractExpandedDecoder(BitArray bitArray) {
        this.information = bitArray;
        this.generalDecoder = new GeneralAppIdDecoder(bitArray);
    }

    public static AbstractExpandedDecoder createDecoder(BitArray bitArray) {
        if (bitArray.get(1)) {
            return new AI01AndOtherAIs(bitArray);
        }
        if (!bitArray.get(2)) {
            return new AnyAIDecoder(bitArray);
        }
        int iD = GeneralAppIdDecoder.d(bitArray, 1, 4);
        if (iD == 4) {
            return new AI013103decoder(bitArray);
        }
        if (iD == 5) {
            return new AI01320xDecoder(bitArray);
        }
        int iD2 = GeneralAppIdDecoder.d(bitArray, 1, 5);
        if (iD2 == 12) {
            return new AI01392xDecoder(bitArray);
        }
        if (iD2 == 13) {
            return new AI01393xDecoder(bitArray);
        }
        switch (GeneralAppIdDecoder.d(bitArray, 1, 7)) {
            case 56:
                return new AI013x0x1xDecoder(bitArray, "310", AgooConstants.ACK_BODY_NULL);
            case 57:
                return new AI013x0x1xDecoder(bitArray, "320", AgooConstants.ACK_BODY_NULL);
            case 58:
                return new AI013x0x1xDecoder(bitArray, "310", AgooConstants.ACK_FLAG_NULL);
            case 59:
                return new AI013x0x1xDecoder(bitArray, "320", AgooConstants.ACK_FLAG_NULL);
            case 60:
                return new AI013x0x1xDecoder(bitArray, "310", AgooConstants.ACK_PACK_ERROR);
            case 61:
                return new AI013x0x1xDecoder(bitArray, "320", AgooConstants.ACK_PACK_ERROR);
            case 62:
                return new AI013x0x1xDecoder(bitArray, "310", "17");
            case 63:
                return new AI013x0x1xDecoder(bitArray, "320", "17");
            default:
                throw new IllegalStateException("unknown decoder: ".concat(String.valueOf(bitArray)));
        }
    }

    protected final GeneralAppIdDecoder a() {
        return this.generalDecoder;
    }

    protected final BitArray b() {
        return this.information;
    }

    public abstract String parseInformation() throws NotFoundException, FormatException;
}
