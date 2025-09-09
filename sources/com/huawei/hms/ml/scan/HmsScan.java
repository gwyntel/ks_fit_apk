package com.huawei.hms.ml.scan;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.huawei.hms.scankit.p.z6;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class HmsScan extends HmsScanBase {
    public ContactDetail contactDetail;
    public DriverInfo driverInfo;
    public EmailContent emailContent;
    public EventInfo eventInfo;
    public LinkUrl linkUrl;
    public LocationCoordinate locationCoordinate;
    public SmsContent smsContent;
    public TelPhoneNumber telPhoneNumber;
    public WiFiConnectionInfo wifiConnectionInfo;
    public static final int ARTICLE_NUMBER_FORM = SCAN_TYPE_FORMAT.ARTICLE_NUMBER_FORM.getScanType();
    public static final int PURE_TEXT_FORM = SCAN_TYPE_FORMAT.PURE_TEXT_FORM.getScanType();
    public static final int ISBN_NUMBER_FORM = SCAN_TYPE_FORMAT.ISBN_NUMBER_FORM.getScanType();
    public static final int EVENT_INFO_FORM = SCAN_TYPE_FORMAT.EVENT_INFO_FORM.getScanType();
    public static final int CONTACT_DETAIL_FORM = SCAN_TYPE_FORMAT.CONTACT_DETAIL_FORM.getScanType();
    public static final int DRIVER_INFO_FORM = SCAN_TYPE_FORMAT.DRIVER_INFO_FORM.getScanType();
    public static final int EMAIL_CONTENT_FORM = SCAN_TYPE_FORMAT.EMAIL_CONTENT_FORM.getScanType();
    public static final int LOCATION_COORDINATE_FORM = SCAN_TYPE_FORMAT.LOCATION_COORDINATE_FORM.getScanType();
    public static final int TEL_PHONE_NUMBER_FORM = SCAN_TYPE_FORMAT.TEL_PHONE_NUMBER_FORM.getScanType();
    public static final int SMS_FORM = SCAN_TYPE_FORMAT.SMS_FORM.getScanType();
    public static final int URL_FORM = SCAN_TYPE_FORMAT.URL_FORM.getScanType();
    public static final int WIFI_CONNECT_INFO_FORM = SCAN_TYPE_FORMAT.WIFI_CONNECT_INFO_FORM.getScanType();
    public static final Parcelable.Creator<HmsScan> CREATOR = new Parcelable.Creator<HmsScan>() { // from class: com.huawei.hms.ml.scan.HmsScan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HmsScan createFromParcel(Parcel parcel) {
            return new HmsScan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HmsScan[] newArray(int i2) {
            return new HmsScan[i2];
        }
    };

    public static class AddressInfo implements Parcelable {
        public String[] addressDetails;
        public int addressType;
        public static final int OTHER_USE_TYPE = ADDRESS_TYPE.OTHER_USE_TYPE.getAddressType();
        public static final int OFFICE_TYPE = ADDRESS_TYPE.OFFICE_TYPE.getAddressType();
        public static final int RESIDENTIAL_USE_TYPE = ADDRESS_TYPE.RESIDENTIAL_USE_TYPE.getAddressType();
        public static final Parcelable.Creator<AddressInfo> CREATOR = new Parcelable.Creator<AddressInfo>() { // from class: com.huawei.hms.ml.scan.HmsScan.AddressInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AddressInfo createFromParcel(Parcel parcel) {
                return new AddressInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AddressInfo[] newArray(int i2) {
                return new AddressInfo[i2];
            }
        };

        enum ADDRESS_TYPE {
            OTHER_USE_TYPE(-1),
            OFFICE_TYPE(1),
            RESIDENTIAL_USE_TYPE(0);

            private final int addressType;

            ADDRESS_TYPE(int i2) {
                this.addressType = i2;
            }

            int getAddressType() {
                return this.addressType;
            }
        }

        public AddressInfo() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String[] getAddressDetails() {
            return this.addressDetails;
        }

        public int getAddressType() {
            return this.addressType;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeStringArray(this.addressDetails);
            parcel.writeInt(this.addressType);
        }

        public AddressInfo(String[] strArr, int i2) {
            this.addressDetails = strArr;
            this.addressType = i2;
        }

        protected AddressInfo(Parcel parcel) {
            this.addressDetails = parcel.createStringArray();
            this.addressType = parcel.readInt();
        }
    }

    enum SCAN_TYPE_FORMAT {
        ARTICLE_NUMBER_FORM(1001),
        EMAIL_CONTENT_FORM(1002),
        TEL_PHONE_NUMBER_FORM(1003),
        PURE_TEXT_FORM(1004),
        SMS_FORM(1005),
        URL_FORM(1006),
        WIFI_CONNECT_INFO_FORM(1007),
        EVENT_INFO_FORM(1008),
        CONTACT_DETAIL_FORM(1009),
        DRIVER_INFO_FORM(1010),
        LOCATION_COORDINATE_FORM(1011),
        ISBN_NUMBER_FORM(1012),
        BOOK_MARK_FORM(1013),
        VEHICLEINFO_FORM(1014);

        private final int scanTypeFormat;

        SCAN_TYPE_FORMAT(int i2) {
            this.scanTypeFormat = i2;
        }

        int getScanType() {
            return this.scanTypeFormat;
        }
    }

    public HmsScan() {
    }

    @Override // com.huawei.hms.ml.scan.HmsScanBase, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.huawei.hms.ml.scan.HmsScanBase
    protected void detailTrans(Parcel parcel) {
        int i2 = this.scanTypeForm;
        if (i2 == ARTICLE_NUMBER_FORM) {
            Log.i("Hms", "ARTICLE_NUMBER_FORM");
            return;
        }
        if (i2 == EMAIL_CONTENT_FORM) {
            this.emailContent = (EmailContent) parcel.readParcelable(EmailContent.class.getClassLoader());
            return;
        }
        if (i2 == TEL_PHONE_NUMBER_FORM) {
            this.telPhoneNumber = (TelPhoneNumber) parcel.readParcelable(TelPhoneNumber.class.getClassLoader());
            return;
        }
        if (i2 == PURE_TEXT_FORM) {
            Log.i("Hms", "PURE_TEXT_FORM");
            return;
        }
        if (i2 == SMS_FORM) {
            this.smsContent = (SmsContent) parcel.readParcelable(SmsContent.class.getClassLoader());
            return;
        }
        if (i2 == URL_FORM) {
            this.linkUrl = (LinkUrl) parcel.readParcelable(LinkUrl.class.getClassLoader());
            return;
        }
        if (i2 == WIFI_CONNECT_INFO_FORM) {
            this.wifiConnectionInfo = (WiFiConnectionInfo) parcel.readParcelable(WiFiConnectionInfo.class.getClassLoader());
            return;
        }
        if (i2 == EVENT_INFO_FORM) {
            this.eventInfo = (EventInfo) parcel.readParcelable(EventInfo.class.getClassLoader());
            return;
        }
        if (i2 == CONTACT_DETAIL_FORM) {
            this.contactDetail = (ContactDetail) parcel.readParcelable(ContactDetail.class.getClassLoader());
        } else if (i2 == DRIVER_INFO_FORM) {
            this.driverInfo = (DriverInfo) parcel.readParcelable(DriverInfo.class.getClassLoader());
        } else if (i2 == LOCATION_COORDINATE_FORM) {
            this.locationCoordinate = (LocationCoordinate) parcel.readParcelable(LocationCoordinate.class.getClassLoader());
        }
    }

    @Override // com.huawei.hms.ml.scan.HmsScanBase
    protected void detailTransOut(Parcel parcel, int i2) {
        int i3 = this.scanTypeForm;
        if (i3 == ARTICLE_NUMBER_FORM) {
            Log.i("Hms", "ARTICLE_NUMBER_FORM");
            return;
        }
        if (i3 == EMAIL_CONTENT_FORM) {
            parcel.writeParcelable(this.emailContent, i2);
            return;
        }
        if (i3 == TEL_PHONE_NUMBER_FORM) {
            parcel.writeParcelable(this.telPhoneNumber, i2);
            return;
        }
        if (i3 == PURE_TEXT_FORM) {
            Log.i("Hms", "PURE_TEXT_FORM");
            return;
        }
        if (i3 == SMS_FORM) {
            parcel.writeParcelable(this.smsContent, i2);
            return;
        }
        if (i3 == URL_FORM) {
            parcel.writeParcelable(this.linkUrl, i2);
            return;
        }
        if (i3 == WIFI_CONNECT_INFO_FORM) {
            parcel.writeParcelable(this.wifiConnectionInfo, i2);
            return;
        }
        if (i3 == EVENT_INFO_FORM) {
            parcel.writeParcelable(this.eventInfo, i2);
            return;
        }
        if (i3 == CONTACT_DETAIL_FORM) {
            parcel.writeParcelable(this.contactDetail, i2);
        } else if (i3 == DRIVER_INFO_FORM) {
            parcel.writeParcelable(this.driverInfo, i2);
        } else if (i3 == LOCATION_COORDINATE_FORM) {
            parcel.writeParcelable(this.locationCoordinate, i2);
        }
    }

    public ContactDetail getContactDetail() {
        return this.contactDetail;
    }

    public DriverInfo getDriverInfo() {
        return this.driverInfo;
    }

    public EmailContent getEmailContent() {
        return this.emailContent;
    }

    public EventInfo getEventInfo() {
        return this.eventInfo;
    }

    public LinkUrl getLinkUrl() {
        return this.linkUrl;
    }

    public LocationCoordinate getLocationCoordinate() {
        return this.locationCoordinate;
    }

    public SmsContent getSmsContent() {
        return this.smsContent;
    }

    public TelPhoneNumber getTelPhoneNumber() {
        return this.telPhoneNumber;
    }

    public WiFiConnectionInfo getWiFiConnectionInfo() {
        return this.wifiConnectionInfo;
    }

    public HmsScan setZoomValue(double d2) {
        this.zoomValue = d2;
        return this;
    }

    @Override // com.huawei.hms.ml.scan.HmsScanBase, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
    }

    public HmsScan(String str, int i2, String str2, int i3, byte[] bArr, Point[] pointArr, HmsScanAnalyzerOptions hmsScanAnalyzerOptions) {
        super(str, i2, str2, i3, bArr, pointArr, hmsScanAnalyzerOptions);
    }

    public static class LinkUrl implements Parcelable {
        public static final Parcelable.Creator<LinkUrl> CREATOR = new Parcelable.Creator<LinkUrl>() { // from class: com.huawei.hms.ml.scan.HmsScan.LinkUrl.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LinkUrl createFromParcel(Parcel parcel) {
                return new LinkUrl(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LinkUrl[] newArray(int i2) {
                return new LinkUrl[i2];
            }
        };
        public String linkvalue;
        public String theme;

        public LinkUrl(String str, String str2) {
            this.theme = str;
            this.linkvalue = str2;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String getLinkValue() {
            return this.linkvalue;
        }

        public String getTheme() {
            return this.theme;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.theme);
            parcel.writeString(this.linkvalue);
        }

        protected LinkUrl(Parcel parcel) {
            this.theme = parcel.readString();
            this.linkvalue = parcel.readString();
        }

        public LinkUrl() {
        }
    }

    public static class LocationCoordinate implements Parcelable {
        public static final Parcelable.Creator<LocationCoordinate> CREATOR = new Parcelable.Creator<LocationCoordinate>() { // from class: com.huawei.hms.ml.scan.HmsScan.LocationCoordinate.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LocationCoordinate createFromParcel(Parcel parcel) {
                return new LocationCoordinate(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LocationCoordinate[] newArray(int i2) {
                return new LocationCoordinate[i2];
            }
        };
        public double latitude;
        public double longitude;

        public LocationCoordinate(double d2, double d3) {
            this.latitude = d2;
            this.longitude = d3;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public double getLatitude() {
            return this.latitude;
        }

        public double getLongitude() {
            return this.longitude;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeDouble(this.latitude);
            parcel.writeDouble(this.longitude);
        }

        protected LocationCoordinate(Parcel parcel) {
            this.latitude = parcel.readDouble();
            this.longitude = parcel.readDouble();
        }

        public LocationCoordinate() {
        }
    }

    public static class SmsContent implements Parcelable {
        public static final Parcelable.Creator<SmsContent> CREATOR = new Parcelable.Creator<SmsContent>() { // from class: com.huawei.hms.ml.scan.HmsScan.SmsContent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SmsContent createFromParcel(Parcel parcel) {
                return new SmsContent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SmsContent[] newArray(int i2) {
                return new SmsContent[i2];
            }
        };
        public String destPhoneNumber;
        public String msgContent;

        public SmsContent(String str, String str2) {
            this.msgContent = str;
            this.destPhoneNumber = str2;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String getDestPhoneNumber() {
            return this.destPhoneNumber;
        }

        public String getMsgContent() {
            return this.msgContent;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.msgContent);
            parcel.writeString(this.destPhoneNumber);
        }

        protected SmsContent(Parcel parcel) {
            this.msgContent = parcel.readString();
            this.destPhoneNumber = parcel.readString();
        }

        public SmsContent() {
        }
    }

    public static class TelPhoneNumber implements Parcelable {
        public String telPhoneNumber;
        public int useType;
        public static final int OTHER_USE_TYPE = USE_TYPE.OTHER_USE_TYPE.getUseType();
        public static final int OFFICE_USE_TYPE = USE_TYPE.OFFICE_USE_TYPE.getUseType();
        public static final int RESIDENTIAL_USE_TYPE = USE_TYPE.RESIDENTIAL_USE_TYPE.getUseType();
        public static final int FAX_USE_TYPE = USE_TYPE.FAX_USE_TYPE.getUseType();
        public static final int CELLPHONE_NUMBER_USE_TYPE = USE_TYPE.CELLPHONE_NUMBER_USE_TYPE.getUseType();
        public static final Parcelable.Creator<TelPhoneNumber> CREATOR = new Parcelable.Creator<TelPhoneNumber>() { // from class: com.huawei.hms.ml.scan.HmsScan.TelPhoneNumber.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TelPhoneNumber createFromParcel(Parcel parcel) {
                return new TelPhoneNumber(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TelPhoneNumber[] newArray(int i2) {
                return new TelPhoneNumber[i2];
            }
        };

        enum USE_TYPE {
            OTHER_USE_TYPE(-1),
            RESIDENTIAL_USE_TYPE(1),
            FAX_USE_TYPE(0),
            CELLPHONE_NUMBER_USE_TYPE(2),
            OFFICE_USE_TYPE(3);

            private final int useType;

            USE_TYPE(int i2) {
                this.useType = i2;
            }

            int getUseType() {
                return this.useType;
            }
        }

        public TelPhoneNumber(int i2, String str) {
            this.useType = i2;
            this.telPhoneNumber = str;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String getTelPhoneNumber() {
            return this.telPhoneNumber;
        }

        public int getUseType() {
            return this.useType;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.telPhoneNumber);
            parcel.writeInt(this.useType);
        }

        protected TelPhoneNumber(Parcel parcel) {
            this.telPhoneNumber = parcel.readString();
            this.useType = parcel.readInt();
        }

        public TelPhoneNumber() {
        }
    }

    public HmsScan(String str, int i2, String str2, int i3, byte[] bArr, Point[] pointArr, HmsScanAnalyzerOptions hmsScanAnalyzerOptions, z6 z6Var) {
        super(str, i2, str2, i3, bArr, pointArr, hmsScanAnalyzerOptions);
        if (z6Var == null || z6Var.a() == null) {
            return;
        }
        if (i3 == ARTICLE_NUMBER_FORM) {
            Log.i("Hms", "ARTICLE_NUMBER_FORM");
            return;
        }
        if (i3 == EMAIL_CONTENT_FORM) {
            this.emailContent = (EmailContent) z6Var.a();
            return;
        }
        if (i3 == TEL_PHONE_NUMBER_FORM) {
            this.telPhoneNumber = (TelPhoneNumber) z6Var.a();
            return;
        }
        if (i3 == PURE_TEXT_FORM) {
            Log.i("Hms", "PURE_TEXT_FORM");
            return;
        }
        if (i3 == SMS_FORM) {
            this.smsContent = (SmsContent) z6Var.a();
            return;
        }
        if (i3 == URL_FORM) {
            this.linkUrl = (LinkUrl) z6Var.a();
            return;
        }
        if (i3 == WIFI_CONNECT_INFO_FORM) {
            this.wifiConnectionInfo = (WiFiConnectionInfo) z6Var.a();
            return;
        }
        if (i3 == EVENT_INFO_FORM) {
            this.eventInfo = (EventInfo) z6Var.a();
            return;
        }
        if (i3 == CONTACT_DETAIL_FORM) {
            this.contactDetail = (ContactDetail) z6Var.a();
            return;
        }
        if (i3 == DRIVER_INFO_FORM) {
            this.driverInfo = (DriverInfo) z6Var.a();
        } else if (i3 == LOCATION_COORDINATE_FORM) {
            this.locationCoordinate = (LocationCoordinate) z6Var.a();
        } else if (i3 == ISBN_NUMBER_FORM) {
            Log.i("Hms", "ISBN_NUMBER_FORM");
        }
    }

    public static class WiFiConnectionInfo implements Parcelable {
        public int cipherMode;
        public String password;
        public String ssidNumber;
        public static final int NO_PASSWORD_MODE_TYPE = CIPHER_MODE.NO_PASSWORD_MODE_TYPE.getCipherMode();
        public static final int WEP_MODE_TYPE = CIPHER_MODE.WEP_MODE_TYPE.getCipherMode();
        public static final int WPA_MODE_TYPE = CIPHER_MODE.WPA_MODE_TYPE.getCipherMode();
        public static final int SAE_MODE_TYPE = CIPHER_MODE.SAE_MODE_TYPE.getCipherMode();
        public static final Parcelable.Creator<WiFiConnectionInfo> CREATOR = new Parcelable.Creator<WiFiConnectionInfo>() { // from class: com.huawei.hms.ml.scan.HmsScan.WiFiConnectionInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WiFiConnectionInfo createFromParcel(Parcel parcel) {
                return new WiFiConnectionInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WiFiConnectionInfo[] newArray(int i2) {
                return new WiFiConnectionInfo[i2];
            }
        };

        enum CIPHER_MODE {
            SAE_MODE_TYPE(3),
            WEP_MODE_TYPE(2),
            NO_PASSWORD_MODE_TYPE(0),
            WPA_MODE_TYPE(1);

            private final int cipherMode;

            CIPHER_MODE(int i2) {
                this.cipherMode = i2;
            }

            int getCipherMode() {
                return this.cipherMode;
            }
        }

        public WiFiConnectionInfo(String str, String str2, int i2) {
            this.ssidNumber = str;
            this.password = str2;
            this.cipherMode = i2;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int getCipherMode() {
            return this.cipherMode;
        }

        public String getPassword() {
            return this.password;
        }

        public String getSsidNumber() {
            return this.ssidNumber;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.cipherMode);
            parcel.writeString(this.password);
            parcel.writeString(this.ssidNumber);
        }

        protected WiFiConnectionInfo(Parcel parcel) {
            this.cipherMode = parcel.readInt();
            this.password = parcel.readString();
            this.ssidNumber = parcel.readString();
        }

        public WiFiConnectionInfo() {
        }
    }

    public static class EmailContent implements Parcelable {
        public String addressInfo;
        public int addressType;
        public String bodyInfo;
        public String subjectInfo;
        public static final int OTHER_USE_TYPE = EMAIL_TYPE.OTHER_USE_TYPE.getAddressType();
        public static final int OFFICE_USE_TYPE = EMAIL_TYPE.OFFICE_USE_TYPE.getAddressType();
        public static final int RESIDENTIAL_USE_TYPE = EMAIL_TYPE.RESIDENTIAL_USE_TYPE.getAddressType();
        public static final Parcelable.Creator<EmailContent> CREATOR = new Parcelable.Creator<EmailContent>() { // from class: com.huawei.hms.ml.scan.HmsScan.EmailContent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EmailContent createFromParcel(Parcel parcel) {
                return new EmailContent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EmailContent[] newArray(int i2) {
                return new EmailContent[i2];
            }
        };

        enum EMAIL_TYPE {
            OTHER_USE_TYPE(-1),
            OFFICE_USE_TYPE(1),
            RESIDENTIAL_USE_TYPE(0);

            private final int addressType;

            EMAIL_TYPE(int i2) {
                this.addressType = i2;
            }

            int getAddressType() {
                return this.addressType;
            }
        }

        public EmailContent(String str, String str2, String str3, int i2) {
            this.addressInfo = str;
            this.subjectInfo = str2;
            this.bodyInfo = str3;
            this.addressType = i2;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String getAddressInfo() {
            return this.addressInfo;
        }

        public int getAddressType() {
            return this.addressType;
        }

        public String getBodyInfo() {
            return this.bodyInfo;
        }

        public String getSubjectInfo() {
            return this.subjectInfo;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.addressInfo);
            parcel.writeString(this.bodyInfo);
            parcel.writeString(this.subjectInfo);
            parcel.writeInt(this.addressType);
        }

        protected EmailContent(Parcel parcel) {
            this.addressInfo = parcel.readString();
            this.bodyInfo = parcel.readString();
            this.subjectInfo = parcel.readString();
            this.addressType = parcel.readInt();
        }

        public EmailContent() {
        }
    }

    public static class EventInfo implements Parcelable {
        public static final Parcelable.Creator<EventInfo> CREATOR = new Parcelable.Creator<EventInfo>() { // from class: com.huawei.hms.ml.scan.HmsScan.EventInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EventInfo createFromParcel(Parcel parcel) {
                return new EventInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EventInfo[] newArray(int i2) {
                return new EventInfo[i2];
            }
        };
        public String abstractInfo;
        public EventTime beginTime;
        public EventTime closeTime;
        public String condition;
        public String placeInfo;
        public String sponsor;
        public String theme;

        public EventInfo(String str, EventTime eventTime, EventTime eventTime2, String str2, String str3, String str4, String str5) {
            this.theme = str;
            this.beginTime = eventTime;
            this.closeTime = eventTime2;
            this.placeInfo = str2;
            this.abstractInfo = str3;
            this.sponsor = str4;
            this.condition = str5;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String getAbstractInfo() {
            return this.abstractInfo;
        }

        public EventTime getBeginTime() {
            return this.beginTime;
        }

        public EventTime getCloseTime() {
            return this.closeTime;
        }

        public String getCondition() {
            return this.condition;
        }

        public String getPlaceInfo() {
            return this.placeInfo;
        }

        public String getSponsor() {
            return this.sponsor;
        }

        public String getTheme() {
            return this.theme;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.abstractInfo);
            parcel.writeParcelable(this.closeTime, i2);
            parcel.writeString(this.placeInfo);
            parcel.writeString(this.sponsor);
            parcel.writeParcelable(this.beginTime, i2);
            parcel.writeString(this.condition);
            parcel.writeString(this.theme);
        }

        public EventInfo(String str, EventTime eventTime, EventTime eventTime2, String str2) {
            this(str, eventTime, eventTime2, str2, null, null, null);
        }

        protected EventInfo(Parcel parcel) {
            this.abstractInfo = parcel.readString();
            this.closeTime = (EventTime) parcel.readParcelable(EventTime.class.getClassLoader());
            this.placeInfo = parcel.readString();
            this.sponsor = parcel.readString();
            this.beginTime = (EventTime) parcel.readParcelable(EventTime.class.getClassLoader());
            this.condition = parcel.readString();
            this.theme = parcel.readString();
        }

        public EventInfo() {
        }
    }

    public static class PeopleName implements Parcelable {
        public static final Parcelable.Creator<PeopleName> CREATOR = new Parcelable.Creator<PeopleName>() { // from class: com.huawei.hms.ml.scan.HmsScan.PeopleName.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PeopleName createFromParcel(Parcel parcel) {
                return new PeopleName(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PeopleName[] newArray(int i2) {
                return new PeopleName[i2];
            }
        };
        public String familyName;
        public String fullName;
        public String givenName;
        public String middleName;
        public String namePrefix;
        public String nameSuffix;
        public String spelling;

        public PeopleName(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
            this.fullName = str;
            this.spelling = str2;
            this.namePrefix = str3;
            this.givenName = str4;
            this.middleName = str5;
            this.familyName = str6;
            this.nameSuffix = str7;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String getFamilyName() {
            return this.familyName;
        }

        public String getFullName() {
            return this.fullName;
        }

        public String getGivenName() {
            return this.givenName;
        }

        public String getMiddleName() {
            return this.middleName;
        }

        public String getNamePrefix() {
            return this.namePrefix;
        }

        public String getNameSuffix() {
            return this.nameSuffix;
        }

        public String getSpelling() {
            return this.spelling;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.fullName);
            parcel.writeString(this.spelling);
            parcel.writeString(this.namePrefix);
            parcel.writeString(this.givenName);
            parcel.writeString(this.middleName);
            parcel.writeString(this.familyName);
            parcel.writeString(this.nameSuffix);
        }

        public PeopleName(String str, String str2, String str3) {
            this(str, null, null, str2, null, str3, null);
        }

        protected PeopleName(Parcel parcel) {
            this.fullName = parcel.readString();
            this.spelling = parcel.readString();
            this.namePrefix = parcel.readString();
            this.givenName = parcel.readString();
            this.middleName = parcel.readString();
            this.familyName = parcel.readString();
            this.nameSuffix = parcel.readString();
        }

        public PeopleName() {
        }
    }

    public static class ContactDetail implements Parcelable {
        public static final Parcelable.Creator<ContactDetail> CREATOR = new Parcelable.Creator<ContactDetail>() { // from class: com.huawei.hms.ml.scan.HmsScan.ContactDetail.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ContactDetail createFromParcel(Parcel parcel) {
                return new ContactDetail(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ContactDetail[] newArray(int i2) {
                return new ContactDetail[i2];
            }
        };
        public AddressInfo[] addressesInfos;
        public String company;
        public String[] contactLinks;
        public EmailContent[] eMailContents;
        public String note;
        public PeopleName peopleName;
        public TelPhoneNumber[] telPhoneNumbers;
        public String title;

        public ContactDetail(PeopleName peopleName, String str, String str2, TelPhoneNumber[] telPhoneNumberArr, EmailContent[] emailContentArr, AddressInfo[] addressInfoArr, String[] strArr, String str3) {
            this.peopleName = peopleName;
            this.title = str;
            this.company = str2;
            this.telPhoneNumbers = telPhoneNumberArr;
            this.eMailContents = emailContentArr;
            this.addressesInfos = addressInfoArr;
            this.contactLinks = strArr;
            this.note = str3;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public List<AddressInfo> getAddressesInfos() {
            return new ArrayList(Arrays.asList(this.addressesInfos));
        }

        public String getCompany() {
            return this.company;
        }

        public String[] getContactLinks() {
            return this.contactLinks;
        }

        public List<EmailContent> getEmailContents() {
            return new ArrayList(Arrays.asList(this.eMailContents));
        }

        public String getNote() {
            return this.note;
        }

        public PeopleName getPeopleName() {
            return this.peopleName;
        }

        public List<TelPhoneNumber> getTelPhoneNumbers() {
            return new ArrayList(Arrays.asList(this.telPhoneNumbers));
        }

        public String getTitle() {
            return this.title;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeTypedArray(this.addressesInfos, i2);
            parcel.writeTypedArray(this.eMailContents, i2);
            parcel.writeString(this.company);
            parcel.writeTypedArray(this.telPhoneNumbers, i2);
            parcel.writeString(this.title);
            parcel.writeStringArray(this.contactLinks);
            parcel.writeParcelable(this.peopleName, i2);
            parcel.writeString(this.note);
        }

        public ContactDetail(PeopleName peopleName, TelPhoneNumber[] telPhoneNumberArr, EmailContent[] emailContentArr) {
            this(peopleName, null, null, telPhoneNumberArr, emailContentArr, null, null, null);
        }

        protected ContactDetail(Parcel parcel) {
            this.addressesInfos = (AddressInfo[]) parcel.createTypedArray(AddressInfo.CREATOR);
            this.eMailContents = (EmailContent[]) parcel.createTypedArray(EmailContent.CREATOR);
            this.company = parcel.readString();
            this.telPhoneNumbers = (TelPhoneNumber[]) parcel.createTypedArray(TelPhoneNumber.CREATOR);
            this.title = parcel.readString();
            this.contactLinks = parcel.createStringArray();
            this.peopleName = (PeopleName) parcel.readParcelable(PeopleName.class.getClassLoader());
            this.note = parcel.readString();
        }

        public ContactDetail() {
        }
    }

    public static class EventTime implements Parcelable {
        public static final Parcelable.Creator<EventTime> CREATOR = new Parcelable.Creator<EventTime>() { // from class: com.huawei.hms.ml.scan.HmsScan.EventTime.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EventTime createFromParcel(Parcel parcel) {
                return new EventTime(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EventTime[] newArray(int i2) {
                return new EventTime[i2];
            }
        };
        public int day;
        public int hours;
        public boolean isUTCTime;
        public int minutes;
        public int month;
        public String originalValue;
        public int seconds;
        public int year;

        public EventTime(int i2, int i3, int i4, int i5, int i6, int i7, boolean z2, String str) {
            this.year = i2;
            this.month = i3;
            this.day = i4;
            this.hours = i5;
            this.minutes = i6;
            this.seconds = i7;
            this.isUTCTime = z2;
            this.originalValue = str;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int getDay() {
            return this.day;
        }

        public int getHours() {
            return this.hours;
        }

        public int getMinutes() {
            return this.minutes;
        }

        public int getMonth() {
            return this.month;
        }

        public String getOriginalValue() {
            return this.originalValue;
        }

        public int getSeconds() {
            return this.seconds;
        }

        public int getYear() {
            return this.year;
        }

        public boolean isUTCTime() {
            return this.isUTCTime;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.day);
            parcel.writeInt(this.hours);
            parcel.writeByte(this.isUTCTime ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.minutes);
            parcel.writeInt(this.month);
            parcel.writeString(this.originalValue);
            parcel.writeInt(this.seconds);
            parcel.writeInt(this.year);
        }

        public EventTime(int i2, int i3, int i4, int i5, int i6, int i7) {
            this(i2, i3, i4, i5, i6, i7, true, null);
        }

        protected EventTime(Parcel parcel) {
            this.day = parcel.readInt();
            this.hours = parcel.readInt();
            this.isUTCTime = parcel.readByte() != 0;
            this.minutes = parcel.readInt();
            this.month = parcel.readInt();
            this.originalValue = parcel.readString();
            this.seconds = parcel.readInt();
            this.year = parcel.readInt();
        }

        public EventTime() {
        }
    }

    public static class DriverInfo implements Parcelable {
        public static final Parcelable.Creator<DriverInfo> CREATOR = new Parcelable.Creator<DriverInfo>() { // from class: com.huawei.hms.ml.scan.HmsScan.DriverInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DriverInfo createFromParcel(Parcel parcel) {
                return new DriverInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DriverInfo[] newArray(int i2) {
                return new DriverInfo[i2];
            }
        };
        public String avenue;
        public String certificateNumber;
        public String certificateType;
        public String city;
        public String countryOfIssue;
        public String dateOfBirth;
        public String dateOfExpire;
        public String dateOfIssue;
        public String eyeColor;
        public String familyName;
        public String givenName;
        public String hairColor;
        public String height;
        public String middleName;
        public String province;
        public String sex;
        public String weightRange;
        public String zipCode;

        public DriverInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18) {
            this.certificateType = str;
            this.givenName = str2;
            this.middleName = str3;
            this.familyName = str4;
            this.sex = str5;
            this.dateOfBirth = str6;
            this.avenue = str7;
            this.city = str8;
            this.province = str9;
            this.zipCode = str10;
            this.certificateNumber = str11;
            this.countryOfIssue = str12;
            this.dateOfIssue = str13;
            this.dateOfExpire = str14;
            this.eyeColor = str15;
            this.hairColor = str16;
            this.height = str17;
            this.weightRange = str18;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String getAvenue() {
            return this.avenue;
        }

        public String getCertificateNumber() {
            return this.certificateNumber;
        }

        public String getCertificateType() {
            return this.certificateType;
        }

        public String getCity() {
            return this.city;
        }

        public String getCountryOfIssue() {
            return this.countryOfIssue;
        }

        public String getDateOfBirth() {
            return this.dateOfBirth;
        }

        public String getDateOfExpire() {
            return this.dateOfExpire;
        }

        public String getDateOfIssue() {
            return this.dateOfIssue;
        }

        public String getEyeColor() {
            return this.eyeColor;
        }

        public String getFamilyName() {
            return this.familyName;
        }

        public String getGivenName() {
            return this.givenName;
        }

        public String getHairColor() {
            return this.hairColor;
        }

        public String getHeight() {
            return this.height;
        }

        public String getMiddleName() {
            return this.middleName;
        }

        public String getProvince() {
            return this.province;
        }

        public String getSex() {
            return this.sex;
        }

        public String getWeightRange() {
            return this.weightRange;
        }

        public String getZipCode() {
            return this.zipCode;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.city);
            parcel.writeString(this.province);
            parcel.writeString(this.avenue);
            parcel.writeString(this.zipCode);
            parcel.writeString(this.dateOfBirth);
            parcel.writeString(this.certificateType);
            parcel.writeString(this.dateOfExpire);
            parcel.writeString(this.givenName);
            parcel.writeString(this.sex);
            parcel.writeString(this.dateOfIssue);
            parcel.writeString(this.countryOfIssue);
            parcel.writeString(this.familyName);
            parcel.writeString(this.certificateNumber);
            parcel.writeString(this.middleName);
            parcel.writeString(this.eyeColor);
            parcel.writeString(this.hairColor);
            parcel.writeString(this.height);
            parcel.writeString(this.weightRange);
        }

        public DriverInfo(String str, String str2, String str3, String str4, String str5) {
            this(str, str2, str3, str4, str5, null, null, null, null, null, null, null, null, null, null, null, null, null);
        }

        protected DriverInfo(Parcel parcel) {
            this.city = parcel.readString();
            this.province = parcel.readString();
            this.avenue = parcel.readString();
            this.zipCode = parcel.readString();
            this.dateOfBirth = parcel.readString();
            this.certificateType = parcel.readString();
            this.dateOfExpire = parcel.readString();
            this.givenName = parcel.readString();
            this.sex = parcel.readString();
            this.dateOfIssue = parcel.readString();
            this.countryOfIssue = parcel.readString();
            this.familyName = parcel.readString();
            this.certificateNumber = parcel.readString();
            this.middleName = parcel.readString();
            this.eyeColor = parcel.readString();
            this.hairColor = parcel.readString();
            this.height = parcel.readString();
            this.weightRange = parcel.readString();
        }

        public DriverInfo() {
        }
    }

    protected HmsScan(Parcel parcel) {
        super(parcel);
    }
}
