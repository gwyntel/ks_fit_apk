package com.xiaomi.infra.galaxy.fds.acl;

import com.xiaomi.infra.galaxy.fds.model.AccessControlList;
import java.util.ArrayList;
import java.util.List;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'PUBLIC_READ' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes4.dex */
public final class CannedAcl {
    private static final /* synthetic */ CannedAcl[] $VALUES;
    public static final CannedAcl AUTHENTICATED_READ;
    public static final CannedAcl AUTHENTICATED_READ_OBJECTS;
    public static final CannedAcl AUTHENTICATED_SSO_WRITE;
    public static final CannedAcl AUTHENTICATED_WRITE;
    public static final CannedAcl PUBLIC_READ;
    public static final CannedAcl PUBLIC_READ_OBJECTS;
    public static final CannedAcl PUBLIC_SSO_WRITE;
    public static final CannedAcl PUBLIC_WRITE;
    private final AccessControlList.UserGroups group;
    private final AccessControlList.Permission permission;

    static {
        AccessControlList.UserGroups userGroups = AccessControlList.UserGroups.ALL_USERS;
        AccessControlList.Permission permission = AccessControlList.Permission.READ;
        CannedAcl cannedAcl = new CannedAcl("PUBLIC_READ", 0, userGroups, permission);
        PUBLIC_READ = cannedAcl;
        AccessControlList.Permission permission2 = AccessControlList.Permission.WRITE;
        CannedAcl cannedAcl2 = new CannedAcl("PUBLIC_WRITE", 1, userGroups, permission2);
        PUBLIC_WRITE = cannedAcl2;
        AccessControlList.Permission permission3 = AccessControlList.Permission.READ_OBJECTS;
        CannedAcl cannedAcl3 = new CannedAcl("PUBLIC_READ_OBJECTS", 2, userGroups, permission3);
        PUBLIC_READ_OBJECTS = cannedAcl3;
        AccessControlList.Permission permission4 = AccessControlList.Permission.SSO_WRITE;
        CannedAcl cannedAcl4 = new CannedAcl("PUBLIC_SSO_WRITE", 3, userGroups, permission4);
        PUBLIC_SSO_WRITE = cannedAcl4;
        AccessControlList.UserGroups userGroups2 = AccessControlList.UserGroups.AUTHENTICATED_USERS;
        CannedAcl cannedAcl5 = new CannedAcl("AUTHENTICATED_READ", 4, userGroups2, permission);
        AUTHENTICATED_READ = cannedAcl5;
        CannedAcl cannedAcl6 = new CannedAcl("AUTHENTICATED_WRITE", 5, userGroups2, permission2);
        AUTHENTICATED_WRITE = cannedAcl6;
        CannedAcl cannedAcl7 = new CannedAcl("AUTHENTICATED_READ_OBJECTS", 6, userGroups2, permission3);
        AUTHENTICATED_READ_OBJECTS = cannedAcl7;
        CannedAcl cannedAcl8 = new CannedAcl("AUTHENTICATED_SSO_WRITE", 7, userGroups2, permission4);
        AUTHENTICATED_SSO_WRITE = cannedAcl8;
        $VALUES = new CannedAcl[]{cannedAcl, cannedAcl2, cannedAcl3, cannedAcl4, cannedAcl5, cannedAcl6, cannedAcl7, cannedAcl8};
    }

    private CannedAcl(String str, int i2, AccessControlList.UserGroups userGroups, AccessControlList.Permission permission) {
        this.group = userGroups;
        this.permission = permission;
    }

    public static List<AccessControlList.Grant> parseFromString(String str) {
        String[] strArrSplit = str.split(",");
        ArrayList arrayList = new ArrayList(strArrSplit.length);
        for (String str2 : strArrSplit) {
            arrayList.add(valueOf(str2.trim()).getGrant());
        }
        return arrayList;
    }

    public static CannedAcl valueOf(String str) {
        return (CannedAcl) Enum.valueOf(CannedAcl.class, str);
    }

    public static CannedAcl[] values() {
        return (CannedAcl[]) $VALUES.clone();
    }

    public AccessControlList.Grant getGrant() {
        return new AccessControlList.Grant(this.group.name(), this.permission, AccessControlList.GrantType.GROUP);
    }
}
