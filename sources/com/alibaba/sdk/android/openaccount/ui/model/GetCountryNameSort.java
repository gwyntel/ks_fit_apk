package com.alibaba.sdk.android.openaccount.ui.model;

import androidx.webkit.ProxyConfig;
import com.alibaba.sdk.android.openaccount.ui.util.CharacterParserUtil;
import com.alibaba.sdk.android.openaccount.ui.util.LocaleUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class GetCountryNameSort {
    CharacterParserUtil characterParser = CharacterParserUtil.getInstance();
    String chReg = "[\\u4E00-\\u9FA5]+";

    public String getSortLetter(String str) {
        if (str == null) {
            return MqttTopic.MULTI_LEVEL_WILDCARD;
        }
        String strSubstring = this.characterParser.getSelling(str).substring(0, 1);
        Locale locale = Locale.CHINESE;
        String upperCase = strSubstring.toUpperCase(locale);
        return upperCase.matches("[A-Z]") ? upperCase.toUpperCase(locale) : MqttTopic.MULTI_LEVEL_WILDCARD;
    }

    public String getSortLetterBySortKey(String str) {
        if (str == null || "".equals(str.trim())) {
            return null;
        }
        String strSubstring = str.trim().substring(0, 1);
        Locale locale = Locale.CHINESE;
        String upperCase = strSubstring.toUpperCase(locale);
        return upperCase.matches("[A-Z]") ? upperCase.toUpperCase(locale) : MqttTopic.MULTI_LEVEL_WILDCARD;
    }

    public List<CountrySort> search(String str, List<CountrySort> list) {
        String str2;
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (str.matches("^([0-9]|[/+]).*")) {
            String strReplaceAll = str.replaceAll("\\-|\\s", "");
            for (CountrySort countrySort : list) {
                if (countrySort.name != null && (str2 = countrySort.code) != null && (str2.contains(strReplaceAll) || countrySort.displayName.contains(str))) {
                    if (!ProxyConfig.MATCH_ALL_SCHEMES.equals(countrySort.sortLetters)) {
                        arrayList.add(countrySort);
                    }
                }
            }
        } else {
            String currentLocale = LocaleUtils.getCurrentLocale();
            for (CountrySort countrySort2 : list) {
                if (countrySort2.code != null && countrySort2.name != null) {
                    Locale locale = Locale.CHINESE;
                    String lowerCase = str.toLowerCase(locale);
                    if ((countrySort2.name.toLowerCase(locale).contains(lowerCase) || countrySort2.sortWeightKey.toLowerCase(locale).replace(" ", "").contains(lowerCase) || countrySort2.englishName.toLowerCase(locale).contains(lowerCase) || countrySort2.domain.toLowerCase(locale).contains(lowerCase)) && !ProxyConfig.MATCH_ALL_SCHEMES.equals(countrySort2.sortLetters)) {
                        arrayList.add(countrySort2);
                    }
                    if (LocaleUtils.isZHLocale(currentLocale) && countrySort2.pinyin.toLowerCase(locale).contains(lowerCase)) {
                        arrayList.add(countrySort2);
                    }
                }
            }
        }
        return arrayList;
    }
}
