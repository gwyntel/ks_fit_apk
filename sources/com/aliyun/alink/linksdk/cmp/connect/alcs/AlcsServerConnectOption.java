package com.aliyun.alink.linksdk.cmp.connect.alcs;

import com.aliyun.alink.linksdk.cmp.core.base.AConnectOption;

/* loaded from: classes2.dex */
public class AlcsServerConnectOption extends AConnectOption {
    private String blackClients = null;
    private OptionFlag optionFlag;
    private String prefix;
    private String secrect;

    public enum OptionFlag {
        ADD_PREFIX_SECRET,
        DELETE_PREFIX,
        UPDATE_BLACK_LIST
    }

    public String getBlackClients() {
        return this.blackClients;
    }

    public OptionFlag getOptionFlag() {
        return this.optionFlag;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSecrect() {
        return this.secrect;
    }

    public void setBlackClients(String str) {
        this.blackClients = str;
    }

    public void setOptionFlag(OptionFlag optionFlag) {
        this.optionFlag = optionFlag;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public void setSecrect(String str) {
        this.secrect = str;
    }
}
