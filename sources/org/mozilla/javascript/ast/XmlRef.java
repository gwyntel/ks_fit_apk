package org.mozilla.javascript.ast;

/* loaded from: classes5.dex */
public abstract class XmlRef extends AstNode {
    protected int atPos;
    protected int colonPos;
    protected Name namespace;

    public XmlRef() {
        this.atPos = -1;
        this.colonPos = -1;
    }

    public int getAtPos() {
        return this.atPos;
    }

    public int getColonPos() {
        return this.colonPos;
    }

    public Name getNamespace() {
        return this.namespace;
    }

    public boolean isAttributeAccess() {
        return this.atPos >= 0;
    }

    public void setAtPos(int i2) {
        this.atPos = i2;
    }

    public void setColonPos(int i2) {
        this.colonPos = i2;
    }

    public void setNamespace(Name name) {
        this.namespace = name;
        if (name != null) {
            name.setParent(this);
        }
    }

    public XmlRef(int i2) {
        super(i2);
        this.atPos = -1;
        this.colonPos = -1;
    }

    public XmlRef(int i2, int i3) {
        super(i2, i3);
        this.atPos = -1;
        this.colonPos = -1;
    }
}
