package org.apache.commons.codec.language.bm;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.umeng.analytics.pro.f;
import com.xiaomi.mipush.sdk.Constants;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.codec.language.bm.Languages;
import org.apache.commons.codec.language.bm.Rule;

/* loaded from: classes5.dex */
public class PhoneticEngine {
    private static final Map<NameType, Set<String>> NAME_PREFIXES;
    private final boolean concat;
    private final Lang lang;
    private final NameType nameType;
    private final RuleType ruleType;

    /* renamed from: org.apache.commons.codec.language.bm.PhoneticEngine$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f26576a;

        static {
            int[] iArr = new int[NameType.values().length];
            f26576a = iArr;
            try {
                iArr[NameType.SEPHARDIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f26576a[NameType.ASHKENAZI.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f26576a[NameType.GENERIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static final class PhonemeBuilder {
        private final Set<Rule.Phoneme> phonemes;

        public static PhonemeBuilder empty(Languages.LanguageSet languageSet) {
            return new PhonemeBuilder(Collections.singleton(new Rule.Phoneme("", languageSet)));
        }

        public PhonemeBuilder append(CharSequence charSequence) {
            HashSet hashSet = new HashSet();
            Iterator<Rule.Phoneme> it = this.phonemes.iterator();
            while (it.hasNext()) {
                hashSet.add(it.next().append(charSequence));
            }
            return new PhonemeBuilder(hashSet);
        }

        public PhonemeBuilder apply(Rule.PhonemeExpr phonemeExpr) {
            HashSet hashSet = new HashSet();
            for (Rule.Phoneme phoneme : this.phonemes) {
                Iterator<Rule.Phoneme> it = phonemeExpr.getPhonemes().iterator();
                while (it.hasNext()) {
                    Rule.Phoneme phonemeJoin = phoneme.join(it.next());
                    if (!phonemeJoin.getLanguages().isEmpty()) {
                        hashSet.add(phonemeJoin);
                    }
                }
            }
            return new PhonemeBuilder(hashSet);
        }

        public Set<Rule.Phoneme> getPhonemes() {
            return this.phonemes;
        }

        public String makeString() {
            StringBuilder sb = new StringBuilder();
            for (Rule.Phoneme phoneme : this.phonemes) {
                if (sb.length() > 0) {
                    sb.append("|");
                }
                sb.append(phoneme.getPhonemeText());
            }
            return sb.toString();
        }

        private PhonemeBuilder(Set<Rule.Phoneme> set) {
            this.phonemes = set;
        }
    }

    private static final class RulesApplication {
        private final List<Rule> finalRules;
        private boolean found;

        /* renamed from: i, reason: collision with root package name */
        private int f26577i;
        private final CharSequence input;
        private PhonemeBuilder phonemeBuilder;

        public RulesApplication(List<Rule> list, CharSequence charSequence, PhonemeBuilder phonemeBuilder, int i2) {
            if (list == null) {
                throw new NullPointerException("The finalRules argument must not be null");
            }
            this.finalRules = list;
            this.phonemeBuilder = phonemeBuilder;
            this.input = charSequence;
            this.f26577i = i2;
        }

        public int getI() {
            return this.f26577i;
        }

        public PhonemeBuilder getPhonemeBuilder() {
            return this.phonemeBuilder;
        }

        public RulesApplication invoke() {
            int i2 = 0;
            this.found = false;
            Iterator<Rule> it = this.finalRules.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Rule next = it.next();
                int length = next.getPattern().length();
                if (next.patternAndContextMatches(this.input, this.f26577i)) {
                    this.phonemeBuilder = this.phonemeBuilder.apply(next.getPhoneme());
                    this.found = true;
                    i2 = length;
                    break;
                }
                i2 = length;
            }
            this.f26577i += this.found ? i2 : 1;
            return this;
        }

        public boolean isFound() {
            return this.found;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(NameType.class);
        NAME_PREFIXES = enumMap;
        enumMap.put((EnumMap) NameType.ASHKENAZI, (NameType) Collections.unmodifiableSet(new HashSet(Arrays.asList("bar", "ben", "da", "de", "van", "von"))));
        enumMap.put((EnumMap) NameType.SEPHARDIC, (NameType) Collections.unmodifiableSet(new HashSet(Arrays.asList("al", "el", "da", "dal", "de", TmpConstant.GROUP_OP_DEL, "dela", "de la", "della", "des", AppIconSetting.DEFAULT_LARGE_ICON, "do", "dos", f.ac, "van", "von"))));
        enumMap.put((EnumMap) NameType.GENERIC, (NameType) Collections.unmodifiableSet(new HashSet(Arrays.asList("da", "dal", "de", TmpConstant.GROUP_OP_DEL, "dela", "de la", "della", "des", AppIconSetting.DEFAULT_LARGE_ICON, "do", "dos", f.ac, "van", "von"))));
    }

    public PhoneticEngine(NameType nameType, RuleType ruleType, boolean z2) {
        RuleType ruleType2 = RuleType.RULES;
        if (ruleType == ruleType2) {
            throw new IllegalArgumentException("ruleType must not be " + ruleType2);
        }
        this.nameType = nameType;
        this.ruleType = ruleType;
        this.concat = z2;
        this.lang = Lang.instance(nameType);
    }

    private PhonemeBuilder applyFinalRules(PhonemeBuilder phonemeBuilder, List<Rule> list) {
        if (list == null) {
            throw new NullPointerException("finalRules can not be null");
        }
        if (list.isEmpty()) {
            return phonemeBuilder;
        }
        TreeSet treeSet = new TreeSet(Rule.Phoneme.COMPARATOR);
        for (Rule.Phoneme phoneme : phonemeBuilder.getPhonemes()) {
            PhonemeBuilder phonemeBuilderEmpty = PhonemeBuilder.empty(phoneme.getLanguages());
            CharSequence charSequenceCacheSubSequence = cacheSubSequence(phoneme.getPhonemeText());
            int i2 = 0;
            while (i2 < charSequenceCacheSubSequence.length()) {
                RulesApplication rulesApplicationInvoke = new RulesApplication(list, charSequenceCacheSubSequence, phonemeBuilderEmpty, i2).invoke();
                boolean zIsFound = rulesApplicationInvoke.isFound();
                PhonemeBuilder phonemeBuilder2 = rulesApplicationInvoke.getPhonemeBuilder();
                PhonemeBuilder phonemeBuilderAppend = !zIsFound ? phonemeBuilder2.append(charSequenceCacheSubSequence.subSequence(i2, i2 + 1)) : phonemeBuilder2;
                i2 = rulesApplicationInvoke.getI();
                phonemeBuilderEmpty = phonemeBuilderAppend;
            }
            treeSet.addAll(phonemeBuilderEmpty.getPhonemes());
        }
        return new PhonemeBuilder(treeSet);
    }

    private static CharSequence cacheSubSequence(final CharSequence charSequence) {
        final CharSequence[][] charSequenceArr = (CharSequence[][]) Array.newInstance((Class<?>) CharSequence.class, charSequence.length(), charSequence.length());
        return new CharSequence() { // from class: org.apache.commons.codec.language.bm.PhoneticEngine.1
            @Override // java.lang.CharSequence
            public char charAt(int i2) {
                return charSequence.charAt(i2);
            }

            @Override // java.lang.CharSequence
            public int length() {
                return charSequence.length();
            }

            @Override // java.lang.CharSequence
            public CharSequence subSequence(int i2, int i3) {
                if (i2 == i3) {
                    return "";
                }
                int i4 = i3 - 1;
                CharSequence charSequence2 = charSequenceArr[i2][i4];
                if (charSequence2 != null) {
                    return charSequence2;
                }
                CharSequence charSequenceSubSequence = charSequence.subSequence(i2, i3);
                charSequenceArr[i2][i4] = charSequenceSubSequence;
                return charSequenceSubSequence;
            }
        };
    }

    private static String join(Iterable<String> iterable, String str) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = iterable.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
        }
        while (it.hasNext()) {
            sb.append(str);
            sb.append(it.next());
        }
        return sb.toString();
    }

    public String encode(String str) {
        return encode(str, this.lang.guessLanguages(str));
    }

    public Lang getLang() {
        return this.lang;
    }

    public NameType getNameType() {
        return this.nameType;
    }

    public RuleType getRuleType() {
        return this.ruleType;
    }

    public boolean isConcat() {
        return this.concat;
    }

    public String encode(String str, Languages.LanguageSet languageSet) {
        String strJoin;
        List<Rule> rule = Rule.getInstance(this.nameType, RuleType.RULES, languageSet);
        List<Rule> rule2 = Rule.getInstance(this.nameType, this.ruleType, "common");
        List<Rule> rule3 = Rule.getInstance(this.nameType, this.ruleType, languageSet);
        String strTrim = str.toLowerCase(Locale.ENGLISH).replace('-', ' ').trim();
        int i2 = 0;
        if (this.nameType == NameType.GENERIC) {
            if (strTrim.length() >= 2 && strTrim.substring(0, 2).equals("d'")) {
                String strSubstring = strTrim.substring(2);
                return "(" + encode(strSubstring) + ")-(" + encode("d" + strSubstring) + ")";
            }
            for (String str2 : NAME_PREFIXES.get(this.nameType)) {
                if (strTrim.startsWith(str2 + " ")) {
                    String strSubstring2 = strTrim.substring(str2.length() + 1);
                    return "(" + encode(strSubstring2) + ")-(" + encode(str2 + strSubstring2) + ")";
                }
            }
        }
        List listAsList = Arrays.asList(strTrim.split("\\s+"));
        ArrayList<String> arrayList = new ArrayList();
        int i3 = AnonymousClass2.f26576a[this.nameType.ordinal()];
        if (i3 == 1) {
            Iterator it = listAsList.iterator();
            while (it.hasNext()) {
                String[] strArrSplit = ((String) it.next()).split("'");
                arrayList.add(strArrSplit[strArrSplit.length - 1]);
            }
            arrayList.removeAll(NAME_PREFIXES.get(this.nameType));
        } else if (i3 == 2) {
            arrayList.addAll(listAsList);
            arrayList.removeAll(NAME_PREFIXES.get(this.nameType));
        } else if (i3 == 3) {
            arrayList.addAll(listAsList);
        } else {
            throw new IllegalStateException("Unreachable case: " + this.nameType);
        }
        if (this.concat) {
            strJoin = join(arrayList, " ");
        } else if (arrayList.size() == 1) {
            strJoin = (String) listAsList.iterator().next();
        } else {
            StringBuilder sb = new StringBuilder();
            for (String str3 : arrayList) {
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append(encode(str3));
            }
            return sb.substring(1);
        }
        PhonemeBuilder phonemeBuilderEmpty = PhonemeBuilder.empty(languageSet);
        CharSequence charSequenceCacheSubSequence = cacheSubSequence(strJoin);
        while (i2 < charSequenceCacheSubSequence.length()) {
            RulesApplication rulesApplicationInvoke = new RulesApplication(rule, charSequenceCacheSubSequence, phonemeBuilderEmpty, i2).invoke();
            i2 = rulesApplicationInvoke.getI();
            phonemeBuilderEmpty = rulesApplicationInvoke.getPhonemeBuilder();
        }
        return applyFinalRules(applyFinalRules(phonemeBuilderEmpty, rule2), rule3).makeString();
    }
}
