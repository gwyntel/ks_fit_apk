package com.alibaba.ailabs.iot.mesh.contant;

import com.google.android.gms.fitness.FitnessStatusCodes;

/* loaded from: classes2.dex */
public enum MeshUtConst$MeshErrorEnum {
    NO_INITIALIZED_ERROR(5001, "Not initialized..."),
    NULL_PROVISION_INFO_ERROR(5002, "get_provisioninfo_request : provisionInfo is null"),
    CALLBACK_ERROR(5003, "gma callback error"),
    TIMEOUT_ERROR(5004, "timeout"),
    GET_PROVISION_4_MASTER_REQUEST_ERROR(FitnessStatusCodes.UNKNOWN_AUTH_ERROR, "get_provisioninfo4master_request_failed"),
    GET_PROVISION_REQUEST_ERROR(FitnessStatusCodes.MISSING_BLE_PERMISSION, "get_provisioninfo_request_failed"),
    DEVICE_NOT_SUPPORT_ERROR(FitnessStatusCodes.UNSUPPORTED_PLATFORM, "Device not supported"),
    PROVISION_COMPLETE_REQUEST_ERROR(FitnessStatusCodes.TRANSIENT_ERROR, "provisionComplete_request_failed"),
    PROVISION_CONFIRM_REQUEST_ERROR(FitnessStatusCodes.EQUIVALENT_SESSION_ENDED, "provisionConfirm_request_failed"),
    PROVISION_AUTH_REQUEST_ERROR(FitnessStatusCodes.APP_NOT_FIT_ENABLED, "provisionAuth_request_failed"),
    MESH_MANAGER_NOT_INITIALIZED_ERROR(FitnessStatusCodes.API_EXCEPTION, "mesh manager not initialized"),
    MESH_MANAGER_NOT_BIND_SERVICE_ERROR(FitnessStatusCodes.AGGREGATION_NOT_SUPPORTED, "TgMeshManager has not bond to service"),
    LOCATION_NOT_ENABLED_ERROR(FitnessStatusCodes.UNSUPPORTED_ACCOUNT, "Location not enabled"),
    BLUETOOTH_NOT_ENABLED_ERROR(FitnessStatusCodes.DISABLED_BLUETOOTH, "Bluetooth disabled"),
    APPKEY_ADD_ERROR(FitnessStatusCodes.INCONSISTENT_PACKAGE_NAME, "appKey add status error"),
    APPKEY_BIND_ERROR(5016, "appKey bind status error"),
    CREATE_TINYMESH_CHANNEL_ERROR(5017, "Failed to establish connection to tinyMesh device"),
    ILLEGAL_PROVISION_DATA_RECEIVED(FitnessStatusCodes.DATA_SOURCE_NOT_FOUND, "Illegal data received in provision progress"),
    INVALID_PROVISIONING_PARAMETER(FitnessStatusCodes.DATASET_TIMESTAMP_INCONSISTENT_WITH_UPDATE_TIME_RANGE, "invalid provisioning parameter"),
    GATT_CONNECT_TIMEOUT(FitnessStatusCodes.INVALID_SESSION_TIMESTAMPS, "gatt connection was not established successfully within 7 seconds"),
    BIND_API_FAILED(FitnessStatusCodes.INVALID_DATA_POINT, "bind device failed");

    public int errorCode;
    public String errorMsg;

    MeshUtConst$MeshErrorEnum(int i2, String str) {
        this.errorCode = i2;
        this.errorMsg = str;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
}
