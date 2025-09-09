package com.baseflow.geocoding.utils;

import android.location.Address;
import com.alipay.sdk.m.t.a;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes3.dex */
public class AddressMapper {
    private static Map<String, Object> toAddressHashMap(Address address) {
        HashMap map = new HashMap();
        String street = AddressLineParser.getStreet(address.getAddressLine(0));
        map.put("name", address.getFeatureName());
        map.put("street", street);
        map.put("isoCountryCode", address.getCountryCode());
        map.put("country", address.getCountryName());
        map.put("thoroughfare", address.getThoroughfare());
        map.put("subThoroughfare", address.getSubThoroughfare());
        map.put("postalCode", address.getPostalCode());
        map.put("administrativeArea", address.getAdminArea());
        map.put("subAdministrativeArea", address.getSubAdminArea());
        map.put("locality", address.getLocality());
        map.put("subLocality", address.getSubLocality());
        return map;
    }

    public static List<Map<String, Object>> toAddressHashMapList(List<Address> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Address> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(toAddressHashMap(it.next()));
        }
        return arrayList;
    }

    public static List<Map<String, Object>> toLocationHashMapList(List<Address> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Address> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(toLocationHashmap(it.next()));
        }
        return arrayList;
    }

    private static Map<String, Object> toLocationHashmap(Address address) {
        HashMap map = new HashMap();
        map.put("latitude", Double.valueOf(address.getLatitude()));
        map.put("longitude", Double.valueOf(address.getLongitude()));
        map.put(a.f9743k, Long.valueOf(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis()));
        return map;
    }
}
