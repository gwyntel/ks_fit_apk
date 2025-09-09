package com.xiaomi.infra.galaxy.fds.model;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class AccessControlList {
    private final Map<GrantKey, Integer> acls = new HashMap();

    public static class Grant {
        private String granteeId;
        private Permission permission;
        private GrantType type;

        public Grant(GrantKey grantKey, Permission permission) {
            this(grantKey.granteeId, permission, grantKey.type);
        }

        public static Grant fromString(String str) {
            int iLastIndexOf = str.lastIndexOf(":");
            Preconditions.checkState(iLastIndexOf > 0);
            return new Grant(GrantKey.fromString(str.substring(0, iLastIndexOf)), Permission.valueOf(str.substring(iLastIndexOf + 1)));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Grant grant = (Grant) obj;
            String str = this.granteeId;
            if (str == null ? grant.granteeId == null : str.equals(grant.granteeId)) {
                return this.permission == grant.permission && this.type == grant.type;
            }
            return false;
        }

        protected GrantKey getGrantKey() {
            return new GrantKey(this.granteeId, this.type);
        }

        public String getGranteeId() {
            return this.granteeId;
        }

        public Permission getPermission() {
            return this.permission;
        }

        public GrantType getType() {
            return this.type;
        }

        public int hashCode() {
            String str = this.granteeId;
            int iHashCode = (str != null ? str.hashCode() : 0) * 31;
            Permission permission = this.permission;
            int iHashCode2 = (iHashCode + (permission != null ? permission.hashCode() : 0)) * 31;
            GrantType grantType = this.type;
            return iHashCode2 + (grantType != null ? grantType.hashCode() : 0);
        }

        public void setGranteeId(String str) {
            this.granteeId = str;
        }

        public void setPermission(Permission permission) {
            this.permission = permission;
        }

        public void setType(GrantType grantType) {
            this.type = grantType;
        }

        public String toString() {
            return getGrantKey().toString() + ":" + this.permission.name();
        }

        public Grant(String str, Permission permission) {
            this(str, permission, GrantType.USER);
        }

        public Grant(String str, Permission permission, GrantType grantType) {
            this.granteeId = str;
            this.permission = permission;
            this.type = grantType;
        }
    }

    public static class GrantKey implements Comparable<GrantKey> {
        protected final String granteeId;
        protected final GrantType type;

        public GrantKey(String str, GrantType grantType) {
            this.granteeId = str;
            this.type = grantType;
        }

        public static GrantKey fromString(String str) {
            int iLastIndexOf = str.lastIndexOf(":");
            Preconditions.checkState(iLastIndexOf > 0);
            return new GrantKey(str.substring(0, iLastIndexOf), GrantType.valueOf(str.substring(iLastIndexOf + 1)));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof GrantKey)) {
                return false;
            }
            GrantKey grantKey = (GrantKey) obj;
            return this.granteeId.equals(grantKey.granteeId) && this.type.equals(grantKey.type);
        }

        public int hashCode() {
            return (this.granteeId.hashCode() ^ this.type.hashCode()) + this.granteeId.length();
        }

        public String toString() {
            return this.granteeId + ":" + this.type.name();
        }

        @Override // java.lang.Comparable
        public int compareTo(GrantKey grantKey) {
            int iCompareTo = this.granteeId.compareTo(grantKey.granteeId);
            return iCompareTo == 0 ? this.type.compareTo(grantKey.type) : iCompareTo;
        }
    }

    public enum GrantType {
        USER,
        GROUP
    }

    public enum Permission {
        READ(1),
        WRITE(2),
        READ_OBJECTS(4),
        SSO_WRITE(8),
        FULL_CONTROL(255);

        private final int value;

        Permission(int i2) {
            this.value = i2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum UserGroups {
        ALL_USERS,
        AUTHENTICATED_USERS
    }

    public void addGrant(Grant grant) {
        GrantKey grantKey = grant.getGrantKey();
        Integer num = this.acls.get(grantKey);
        if (num == null) {
            this.acls.put(grantKey, Integer.valueOf(grant.getPermission().getValue()));
            return;
        }
        this.acls.put(grantKey, Integer.valueOf(grant.getPermission().getValue() | num.intValue()));
    }

    public void addGrants(List<Grant> list) {
        Iterator<Grant> it = list.iterator();
        while (it.hasNext()) {
            addGrant(it.next());
        }
    }

    public boolean checkGroupReadPermission(String str) {
        return checkPermission(str, GrantType.GROUP, Permission.READ);
    }

    public boolean checkGroupWritePermission(String str) {
        return checkPermission(str, GrantType.GROUP, Permission.WRITE);
    }

    public boolean checkPermission(String str, GrantType grantType, Permission permission) {
        Integer num = this.acls.get(new GrantKey(str, grantType));
        if (num != null) {
            return (num.intValue() & permission.getValue()) > 0;
        }
        return false;
    }

    public boolean checkUserReadPermission(String str) {
        return checkPermission(str, GrantType.USER, Permission.READ);
    }

    public boolean checkUserWritePermission(String str) {
        return checkPermission(str, GrantType.USER, Permission.WRITE);
    }

    public List<Grant> getGrantList() {
        LinkedList linkedList = new LinkedList();
        for (Map.Entry<GrantKey, Integer> entry : this.acls.entrySet()) {
            GrantKey key = entry.getKey();
            int iIntValue = entry.getValue().intValue();
            Permission permission = Permission.FULL_CONTROL;
            if (iIntValue == permission.getValue()) {
                linkedList.add(new Grant(key, permission));
            } else {
                for (Permission permission2 : Permission.values()) {
                    if (permission2.getValue() != Permission.FULL_CONTROL.getValue() && (permission2.getValue() & entry.getValue().intValue()) > 0) {
                        linkedList.add(new Grant(key, permission2));
                    }
                }
            }
        }
        return linkedList;
    }
}
