package org.mozilla.javascript.tools.idswitch;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/* loaded from: classes5.dex */
public class FileBody {
    private char[] buffer = new char[16384];
    private int bufferEnd;
    ReplaceItem firstReplace;
    ReplaceItem lastReplace;
    private int lineBegin;
    private int lineEnd;
    private int lineNumber;
    private int nextLineStart;

    private static class ReplaceItem {
        int begin;
        int end;
        ReplaceItem next;
        String replacement;

        ReplaceItem(int i2, int i3, String str) {
            this.begin = i2;
            this.end = i3;
            this.replacement = str;
        }
    }

    private static boolean equals(String str, char[] cArr, int i2, int i3) {
        if (str.length() != i3 - i2) {
            return false;
        }
        int i4 = 0;
        while (i2 != i3) {
            if (cArr[i2] != str.charAt(i4)) {
                return false;
            }
            i2++;
            i4++;
        }
        return true;
    }

    public char[] getBuffer() {
        return this.buffer;
    }

    public int getLineBegin() {
        return this.lineBegin;
    }

    public int getLineEnd() {
        return this.lineEnd;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public boolean nextLine() {
        int i2;
        int i3;
        int i4 = this.nextLineStart;
        char c2 = 0;
        if (i4 == this.bufferEnd) {
            this.lineNumber = 0;
            return false;
        }
        while (true) {
            i2 = this.bufferEnd;
            if (i4 == i2 || (c2 = this.buffer[i4]) == '\n' || c2 == '\r') {
                break;
            }
            i4++;
        }
        this.lineBegin = this.nextLineStart;
        this.lineEnd = i4;
        if (i4 == i2) {
            this.nextLineStart = i4;
        } else if (c2 == '\r' && (i3 = i4 + 1) != i2 && this.buffer[i3] == '\n') {
            this.nextLineStart = i4 + 2;
        } else {
            this.nextLineStart = i4 + 1;
        }
        this.lineNumber++;
        return true;
    }

    public void readData(Reader reader) throws IOException {
        int length = this.buffer.length;
        int i2 = 0;
        while (true) {
            int i3 = reader.read(this.buffer, i2, length - i2);
            if (i3 < 0) {
                this.bufferEnd = i2;
                return;
            }
            i2 += i3;
            if (length == i2) {
                length *= 2;
                char[] cArr = new char[length];
                System.arraycopy(this.buffer, 0, cArr, 0, i2);
                this.buffer = cArr;
            }
        }
    }

    public boolean setReplacement(int i2, int i3, String str) {
        if (equals(str, this.buffer, i2, i3)) {
            return false;
        }
        ReplaceItem replaceItem = new ReplaceItem(i2, i3, str);
        ReplaceItem replaceItem2 = this.firstReplace;
        if (replaceItem2 == null) {
            this.lastReplace = replaceItem;
            this.firstReplace = replaceItem;
            return true;
        }
        if (i2 < replaceItem2.begin) {
            replaceItem.next = replaceItem2;
            this.firstReplace = replaceItem;
            return true;
        }
        ReplaceItem replaceItem3 = replaceItem2.next;
        while (true) {
            ReplaceItem replaceItem4 = replaceItem3;
            ReplaceItem replaceItem5 = replaceItem2;
            replaceItem2 = replaceItem4;
            if (replaceItem2 == null) {
                break;
            }
            if (i2 < replaceItem2.begin) {
                replaceItem.next = replaceItem2;
                replaceItem5.next = replaceItem;
                break;
            }
            replaceItem3 = replaceItem2.next;
        }
        if (replaceItem2 != null) {
            return true;
        }
        this.lastReplace.next = replaceItem;
        return true;
    }

    public void startLineLoop() {
        this.lineNumber = 0;
        this.nextLineStart = 0;
        this.lineEnd = 0;
        this.lineBegin = 0;
    }

    public boolean wasModified() {
        return this.firstReplace != null;
    }

    public void writeData(Writer writer) throws IOException {
        int i2 = 0;
        for (ReplaceItem replaceItem = this.firstReplace; replaceItem != null; replaceItem = replaceItem.next) {
            int i3 = replaceItem.begin - i2;
            if (i3 > 0) {
                writer.write(this.buffer, i2, i3);
            }
            writer.write(replaceItem.replacement);
            i2 = replaceItem.end;
        }
        int i4 = this.bufferEnd - i2;
        if (i4 != 0) {
            writer.write(this.buffer, i2, i4);
        }
    }

    public void writeInitialData(Writer writer) throws IOException {
        writer.write(this.buffer, 0, this.bufferEnd);
    }
}
