package org.apache.commons.lang;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public final class CharRange implements Serializable {
    private static final long serialVersionUID = 8270183163158333422L;
    private final char end;
    private transient String iToString;
    private final boolean negated;
    private final char start;

    private static class CharacterIterator implements Iterator {
        private char current;
        private boolean hasNext;
        private final CharRange range;

        private void prepareNext() {
            if (!this.range.negated) {
                if (this.current < this.range.end) {
                    this.current = (char) (this.current + 1);
                    return;
                } else {
                    this.hasNext = false;
                    return;
                }
            }
            char c2 = this.current;
            if (c2 == 65535) {
                this.hasNext = false;
                return;
            }
            if (c2 + 1 != this.range.start) {
                this.current = (char) (this.current + 1);
            } else if (this.range.end == 65535) {
                this.hasNext = false;
            } else {
                this.current = (char) (this.range.end + 1);
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.hasNext;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            char c2 = this.current;
            prepareNext();
            return new Character(c2);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private CharacterIterator(CharRange charRange) {
            this.range = charRange;
            this.hasNext = true;
            if (!charRange.negated) {
                this.current = charRange.start;
                return;
            }
            if (charRange.start != 0) {
                this.current = (char) 0;
            } else if (charRange.end == 65535) {
                this.hasNext = false;
            } else {
                this.current = (char) (charRange.end + 1);
            }
        }
    }

    public CharRange(char c2) {
        this(c2, c2, false);
    }

    public static CharRange is(char c2) {
        return new CharRange(c2, c2, false);
    }

    public static CharRange isIn(char c2, char c3) {
        return new CharRange(c2, c3, false);
    }

    public static CharRange isNot(char c2) {
        return new CharRange(c2, c2, true);
    }

    public static CharRange isNotIn(char c2, char c3) {
        return new CharRange(c2, c3, true);
    }

    public boolean contains(char c2) {
        return (c2 >= this.start && c2 <= this.end) != this.negated;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CharRange)) {
            return false;
        }
        CharRange charRange = (CharRange) obj;
        return this.start == charRange.start && this.end == charRange.end && this.negated == charRange.negated;
    }

    public char getEnd() {
        return this.end;
    }

    public char getStart() {
        return this.start;
    }

    public int hashCode() {
        return this.start + 'S' + (this.end * 7) + (this.negated ? 1 : 0);
    }

    public boolean isNegated() {
        return this.negated;
    }

    public Iterator iterator() {
        return new CharacterIterator();
    }

    public String toString() {
        if (this.iToString == null) {
            StrBuilder strBuilder = new StrBuilder(4);
            if (isNegated()) {
                strBuilder.append('^');
            }
            strBuilder.append(this.start);
            if (this.start != this.end) {
                strBuilder.append('-');
                strBuilder.append(this.end);
            }
            this.iToString = strBuilder.toString();
        }
        return this.iToString;
    }

    public CharRange(char c2, boolean z2) {
        this(c2, c2, z2);
    }

    public boolean contains(CharRange charRange) {
        if (charRange != null) {
            return this.negated ? charRange.negated ? this.start >= charRange.start && this.end <= charRange.end : charRange.end < this.start || charRange.start > this.end : charRange.negated ? this.start == 0 && this.end == 65535 : this.start <= charRange.start && this.end >= charRange.end;
        }
        throw new IllegalArgumentException("The Range must not be null");
    }

    public CharRange(char c2, char c3) {
        this(c2, c3, false);
    }

    public CharRange(char c2, char c3, boolean z2) {
        if (c2 > c3) {
            c3 = c2;
            c2 = c3;
        }
        this.start = c2;
        this.end = c3;
        this.negated = z2;
    }
}
