package com.alibaba.ailabs.iot.mesh.utils;

/* loaded from: classes2.dex */
public class Constants {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f8774a = true;

    public enum AliMeshSolutionType {
        SIG_MESH(0),
        TINY_MESH_ADV_V1(1),
        TINY_MESH_ADV_V2(2),
        TINY_MESH_GATT_V1(3),
        TINY_MESH_GATT_V2(4),
        UNKNOWN(5);

        public int solutionType;

        AliMeshSolutionType(int i2) {
            this.solutionType = i2;
        }

        public int getSolutionType() {
            return this.solutionType;
        }
    }
}
