package com.yc.utesdk.bean;

import java.io.File;

/* loaded from: classes4.dex */
public class EphemerisFileInfo {
    private int constellation;
    private File file;
    private String filename;
    private int id;
    private int timestamp;
    private int type;

    public int getConstellation() {
        return this.constellation;
    }

    public File getFile() {
        return this.file;
    }

    public String getFilename() {
        return this.filename;
    }

    public int getId() {
        return this.id;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public int getType() {
        return this.type;
    }

    public void setConstellation(int i2) {
        this.constellation = i2;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFilename(String str) {
        this.filename = str;
    }

    public void setId(int i2) {
        this.id = i2;
    }

    public void setTimestamp(int i2) {
        this.timestamp = i2;
    }

    public void setType(int i2) {
        this.type = i2;
    }
}
