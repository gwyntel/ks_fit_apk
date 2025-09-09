package com.alibaba.fastjson.support.geo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = {"type", "bbox", "coordinates"}, typeName = "Point")
/* loaded from: classes2.dex */
public class Point extends Geometry {
    private double latitude;
    private double longitude;

    public Point() {
        super("Point");
    }

    public double[] getCoordinates() {
        return new double[]{this.longitude, this.latitude};
    }

    @JSONField(serialize = false)
    public double getLatitude() {
        return this.latitude;
    }

    @JSONField(serialize = false)
    public double getLongitude() {
        return this.longitude;
    }

    public void setCoordinates(double[] dArr) {
        if (dArr == null || dArr.length == 0) {
            this.longitude = 0.0d;
            this.latitude = 0.0d;
        } else if (dArr.length == 1) {
            this.longitude = dArr[0];
        } else {
            this.longitude = dArr[0];
            this.latitude = dArr[1];
        }
    }

    @JSONField(deserialize = false)
    public void setLatitude(double d2) {
        this.latitude = d2;
    }

    @JSONField(deserialize = false)
    public void setLongitude(double d2) {
        this.longitude = d2;
    }
}
