package androidx.media3.extractor.mkv;

import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.extractor.ExtractorInput;
import java.io.IOException;
import java.util.ArrayDeque;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
final class DefaultEbmlReader implements EbmlReader {
    private static final int ELEMENT_STATE_READ_CONTENT = 2;
    private static final int ELEMENT_STATE_READ_CONTENT_SIZE = 1;
    private static final int ELEMENT_STATE_READ_ID = 0;
    private static final int MAX_ID_BYTES = 4;
    private static final int MAX_INTEGER_ELEMENT_SIZE_BYTES = 8;
    private static final int MAX_LENGTH_BYTES = 8;
    private static final int VALID_FLOAT32_ELEMENT_SIZE_BYTES = 4;
    private static final int VALID_FLOAT64_ELEMENT_SIZE_BYTES = 8;
    private long elementContentSize;
    private int elementId;
    private int elementState;
    private EbmlProcessor processor;
    private final byte[] scratch = new byte[8];
    private final ArrayDeque<MasterElement> masterElementsStack = new ArrayDeque<>();
    private final VarintReader varintReader = new VarintReader();

    private static final class MasterElement {
        private final long elementEndPosition;
        private final int elementId;

        private MasterElement(int i2, long j2) {
            this.elementId = i2;
            this.elementEndPosition = j2;
        }
    }

    @RequiresNonNull({"processor"})
    private long maybeResyncToNextLevel1Element(ExtractorInput extractorInput) throws IOException {
        extractorInput.resetPeekPosition();
        while (true) {
            extractorInput.peekFully(this.scratch, 0, 4);
            int unsignedVarintLength = VarintReader.parseUnsignedVarintLength(this.scratch[0]);
            if (unsignedVarintLength != -1 && unsignedVarintLength <= 4) {
                int iAssembleVarint = (int) VarintReader.assembleVarint(this.scratch, unsignedVarintLength, false);
                if (this.processor.isLevel1Element(iAssembleVarint)) {
                    extractorInput.skipFully(unsignedVarintLength);
                    return iAssembleVarint;
                }
            }
            extractorInput.skipFully(1);
        }
    }

    private double readFloat(ExtractorInput extractorInput, int i2) throws IOException {
        return i2 == 4 ? Float.intBitsToFloat((int) r0) : Double.longBitsToDouble(readInteger(extractorInput, i2));
    }

    private long readInteger(ExtractorInput extractorInput, int i2) throws IOException {
        extractorInput.readFully(this.scratch, 0, i2);
        long j2 = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j2 = (j2 << 8) | (this.scratch[i3] & 255);
        }
        return j2;
    }

    private static String readString(ExtractorInput extractorInput, int i2) throws IOException {
        if (i2 == 0) {
            return "";
        }
        byte[] bArr = new byte[i2];
        extractorInput.readFully(bArr, 0, i2);
        while (i2 > 0 && bArr[i2 - 1] == 0) {
            i2--;
        }
        return new String(bArr, 0, i2);
    }

    @Override // androidx.media3.extractor.mkv.EbmlReader
    public void init(EbmlProcessor ebmlProcessor) {
        this.processor = ebmlProcessor;
    }

    @Override // androidx.media3.extractor.mkv.EbmlReader
    public boolean read(ExtractorInput extractorInput) throws IOException {
        Assertions.checkStateNotNull(this.processor);
        while (true) {
            MasterElement masterElementPeek = this.masterElementsStack.peek();
            if (masterElementPeek != null && extractorInput.getPosition() >= masterElementPeek.elementEndPosition) {
                this.processor.endMasterElement(this.masterElementsStack.pop().elementId);
                return true;
            }
            if (this.elementState == 0) {
                long unsignedVarint = this.varintReader.readUnsignedVarint(extractorInput, true, false, 4);
                if (unsignedVarint == -2) {
                    unsignedVarint = maybeResyncToNextLevel1Element(extractorInput);
                }
                if (unsignedVarint == -1) {
                    return false;
                }
                this.elementId = (int) unsignedVarint;
                this.elementState = 1;
            }
            if (this.elementState == 1) {
                this.elementContentSize = this.varintReader.readUnsignedVarint(extractorInput, false, true, 8);
                this.elementState = 2;
            }
            int elementType = this.processor.getElementType(this.elementId);
            if (elementType != 0) {
                if (elementType == 1) {
                    long position = extractorInput.getPosition();
                    this.masterElementsStack.push(new MasterElement(this.elementId, this.elementContentSize + position));
                    this.processor.startMasterElement(this.elementId, position, this.elementContentSize);
                    this.elementState = 0;
                    return true;
                }
                if (elementType == 2) {
                    long j2 = this.elementContentSize;
                    if (j2 <= 8) {
                        this.processor.integerElement(this.elementId, readInteger(extractorInput, (int) j2));
                        this.elementState = 0;
                        return true;
                    }
                    throw ParserException.createForMalformedContainer("Invalid integer size: " + this.elementContentSize, null);
                }
                if (elementType == 3) {
                    long j3 = this.elementContentSize;
                    if (j3 <= 2147483647L) {
                        this.processor.stringElement(this.elementId, readString(extractorInput, (int) j3));
                        this.elementState = 0;
                        return true;
                    }
                    throw ParserException.createForMalformedContainer("String element size: " + this.elementContentSize, null);
                }
                if (elementType == 4) {
                    this.processor.binaryElement(this.elementId, (int) this.elementContentSize, extractorInput);
                    this.elementState = 0;
                    return true;
                }
                if (elementType != 5) {
                    throw ParserException.createForMalformedContainer("Invalid element type " + elementType, null);
                }
                long j4 = this.elementContentSize;
                if (j4 == 4 || j4 == 8) {
                    this.processor.floatElement(this.elementId, readFloat(extractorInput, (int) j4));
                    this.elementState = 0;
                    return true;
                }
                throw ParserException.createForMalformedContainer("Invalid float size: " + this.elementContentSize, null);
            }
            extractorInput.skipFully((int) this.elementContentSize);
            this.elementState = 0;
        }
    }

    @Override // androidx.media3.extractor.mkv.EbmlReader
    public void reset() {
        this.elementState = 0;
        this.masterElementsStack.clear();
        this.varintReader.reset();
    }
}
