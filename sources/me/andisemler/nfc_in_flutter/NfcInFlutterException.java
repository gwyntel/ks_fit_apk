package me.andisemler.nfc_in_flutter;

/* loaded from: classes5.dex */
class NfcInFlutterException extends Exception {
    String code;
    Object details;
    String message;

    NfcInFlutterException(String str, String str2, Object obj) {
        this.code = str;
        this.message = str2;
        this.details = obj;
    }
}
