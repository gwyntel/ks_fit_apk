package org.apache.commons.codec.language.bm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.apache.commons.codec.language.bm.Languages;

/* loaded from: classes5.dex */
public class Rule {
    public static final String ALL = "ALL";
    private static final String DOUBLE_QUOTE = "\"";
    private static final String HASH_INCLUDE = "#include";
    private final RPattern lContext;
    private final String pattern;
    private final PhonemeExpr phoneme;
    private final RPattern rContext;
    public static final RPattern ALL_STRINGS_RMATCHER = new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.1
        @Override // org.apache.commons.codec.language.bm.Rule.RPattern
        public boolean isMatch(CharSequence charSequence) {
            return true;
        }
    };
    private static final Map<NameType, Map<RuleType, Map<String, List<Rule>>>> RULES = new EnumMap(NameType.class);

    public static final class Phoneme implements PhonemeExpr {
        public static final Comparator<Phoneme> COMPARATOR = new Comparator<Phoneme>() { // from class: org.apache.commons.codec.language.bm.Rule.Phoneme.1
            @Override // java.util.Comparator
            public int compare(Phoneme phoneme, Phoneme phoneme2) {
                for (int i2 = 0; i2 < phoneme.phonemeText.length(); i2++) {
                    if (i2 >= phoneme2.phonemeText.length()) {
                        return 1;
                    }
                    int iCharAt = phoneme.phonemeText.charAt(i2) - phoneme2.phonemeText.charAt(i2);
                    if (iCharAt != 0) {
                        return iCharAt;
                    }
                }
                return phoneme.phonemeText.length() < phoneme2.phonemeText.length() ? -1 : 0;
            }
        };
        private final Languages.LanguageSet languages;
        private final CharSequence phonemeText;

        public Phoneme(CharSequence charSequence, Languages.LanguageSet languageSet) {
            this.phonemeText = charSequence;
            this.languages = languageSet;
        }

        public Phoneme append(CharSequence charSequence) {
            return new Phoneme(this.phonemeText.toString() + charSequence.toString(), this.languages);
        }

        public Languages.LanguageSet getLanguages() {
            return this.languages;
        }

        public CharSequence getPhonemeText() {
            return this.phonemeText;
        }

        @Override // org.apache.commons.codec.language.bm.Rule.PhonemeExpr
        public Iterable<Phoneme> getPhonemes() {
            return Collections.singleton(this);
        }

        public Phoneme join(Phoneme phoneme) {
            return new Phoneme(this.phonemeText.toString() + phoneme.phonemeText.toString(), this.languages.restrictTo(phoneme.languages));
        }
    }

    public interface PhonemeExpr {
        Iterable<Phoneme> getPhonemes();
    }

    public static final class PhonemeList implements PhonemeExpr {
        private final List<Phoneme> phonemes;

        public PhonemeList(List<Phoneme> list) {
            this.phonemes = list;
        }

        @Override // org.apache.commons.codec.language.bm.Rule.PhonemeExpr
        public List<Phoneme> getPhonemes() {
            return this.phonemes;
        }
    }

    public interface RPattern {
        boolean isMatch(CharSequence charSequence);
    }

    static {
        for (NameType nameType : NameType.values()) {
            EnumMap enumMap = new EnumMap(RuleType.class);
            for (RuleType ruleType : RuleType.values()) {
                HashMap map = new HashMap();
                for (String str : Languages.getInstance(nameType).getLanguages()) {
                    try {
                        map.put(str, parseRules(createScanner(nameType, ruleType, str), createResourceName(nameType, ruleType, str)));
                    } catch (IllegalStateException e2) {
                        throw new IllegalStateException("Problem processing " + createResourceName(nameType, ruleType, str), e2);
                    }
                }
                if (!ruleType.equals(RuleType.RULES)) {
                    map.put("common", parseRules(createScanner(nameType, ruleType, "common"), createResourceName(nameType, ruleType, "common")));
                }
                enumMap.put((EnumMap) ruleType, (RuleType) Collections.unmodifiableMap(map));
            }
            RULES.put(nameType, Collections.unmodifiableMap(enumMap));
        }
    }

    public Rule(String str, String str2, String str3, PhonemeExpr phonemeExpr) {
        this.pattern = str;
        this.lContext = pattern(str2 + "$");
        this.rContext = pattern("^" + str3);
        this.phoneme = phonemeExpr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean contains(CharSequence charSequence, char c2) {
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            if (charSequence.charAt(i2) == c2) {
                return true;
            }
        }
        return false;
    }

    private static String createResourceName(NameType nameType, RuleType ruleType, String str) {
        return String.format("org/apache/commons/codec/language/bm/%s_%s_%s.txt", nameType.getName(), ruleType.getName(), str);
    }

    private static Scanner createScanner(NameType nameType, RuleType ruleType, String str) {
        String strCreateResourceName = createResourceName(nameType, ruleType, str);
        InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(strCreateResourceName);
        if (resourceAsStream != null) {
            return new Scanner(resourceAsStream, "UTF-8");
        }
        throw new IllegalArgumentException("Unable to load resource: " + strCreateResourceName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean endsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        int length = charSequence.length() - 1;
        for (int length2 = charSequence2.length() - 1; length2 >= 0; length2--) {
            if (charSequence.charAt(length) != charSequence2.charAt(length2)) {
                return false;
            }
            length--;
        }
        return true;
    }

    public static List<Rule> getInstance(NameType nameType, RuleType ruleType, Languages.LanguageSet languageSet) {
        return getInstance(nameType, ruleType, languageSet.isSingleton() ? languageSet.getAny() : Languages.ANY);
    }

    private static Phoneme parsePhoneme(String str) {
        int iIndexOf = str.indexOf("[");
        if (iIndexOf < 0) {
            return new Phoneme(str, Languages.ANY_LANGUAGE);
        }
        if (str.endsWith("]")) {
            return new Phoneme(str.substring(0, iIndexOf), Languages.LanguageSet.from(new HashSet(Arrays.asList(str.substring(iIndexOf + 1, str.length() - 1).split("[+]")))));
        }
        throw new IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
    }

    private static PhonemeExpr parsePhonemeExpr(String str) {
        if (!str.startsWith("(")) {
            return parsePhoneme(str);
        }
        if (!str.endsWith(")")) {
            throw new IllegalArgumentException("Phoneme starts with '(' so must end with ')'");
        }
        ArrayList arrayList = new ArrayList();
        String strSubstring = str.substring(1, str.length() - 1);
        for (String str2 : strSubstring.split("[|]")) {
            arrayList.add(parsePhoneme(str2));
        }
        if (strSubstring.startsWith("|") || strSubstring.endsWith("|")) {
            arrayList.add(new Phoneme("", Languages.ANY_LANGUAGE));
        }
        return new PhonemeList(arrayList);
    }

    private static List<Rule> parseRules(Scanner scanner, String str) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        boolean z2 = false;
        while (scanner.hasNextLine()) {
            i2++;
            String strNextLine = scanner.nextLine();
            if (z2) {
                if (strNextLine.endsWith("*/")) {
                    z2 = false;
                }
            } else if (strNextLine.startsWith("/*")) {
                z2 = true;
            } else {
                int iIndexOf = strNextLine.indexOf("//");
                String strTrim = (iIndexOf >= 0 ? strNextLine.substring(0, iIndexOf) : strNextLine).trim();
                if (strTrim.length() == 0) {
                    continue;
                } else if (strTrim.startsWith(HASH_INCLUDE)) {
                    String strTrim2 = strTrim.substring(8).trim();
                    if (strTrim2.contains(" ")) {
                        System.err.println("Warining: malformed import statement: " + strNextLine);
                    } else {
                        arrayList.addAll(parseRules(createScanner(strTrim2), str + "->" + strTrim2));
                    }
                } else {
                    String[] strArrSplit = strTrim.split("\\s+");
                    if (strArrSplit.length != 4) {
                        System.err.println("Warning: malformed rule statement split into " + strArrSplit.length + " parts: " + strNextLine);
                    } else {
                        try {
                            arrayList.add(new Rule(stripQuotes(strArrSplit[0]), stripQuotes(strArrSplit[1]), stripQuotes(strArrSplit[2]), parsePhonemeExpr(stripQuotes(strArrSplit[3])), i2, str) { // from class: org.apache.commons.codec.language.bm.Rule.2

                                /* renamed from: a, reason: collision with root package name */
                                final /* synthetic */ int f26580a;

                                /* renamed from: b, reason: collision with root package name */
                                final /* synthetic */ String f26581b;
                                private final String loc;
                                private final int myLine;

                                {
                                    this.f26580a = i2;
                                    this.f26581b = str;
                                    this.myLine = i2;
                                    this.loc = str;
                                }

                                public String toString() {
                                    return "Rule{line=" + this.myLine + ", loc='" + this.loc + "'}";
                                }
                            });
                        } catch (IllegalArgumentException e2) {
                            throw new IllegalStateException("Problem parsing line " + i2, e2);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private static RPattern pattern(String str) {
        boolean zStartsWith = str.startsWith("^");
        boolean zEndsWith = str.endsWith("$");
        int length = str.length();
        if (zEndsWith) {
            length--;
        }
        final String strSubstring = str.substring(zStartsWith ? 1 : 0, length);
        if (strSubstring.contains("[")) {
            boolean zStartsWith2 = strSubstring.startsWith("[");
            boolean zEndsWith2 = strSubstring.endsWith("]");
            if (zStartsWith2 && zEndsWith2) {
                final String strSubstring2 = strSubstring.substring(1, strSubstring.length() - 1);
                if (!strSubstring2.contains("[")) {
                    boolean zStartsWith3 = strSubstring2.startsWith("^");
                    if (zStartsWith3) {
                        strSubstring2 = strSubstring2.substring(1);
                    }
                    final boolean z2 = !zStartsWith3;
                    if (zStartsWith && zEndsWith) {
                        return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.7
                            @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                            public boolean isMatch(CharSequence charSequence) {
                                return charSequence.length() == 1 && Rule.contains(strSubstring2, charSequence.charAt(0)) == z2;
                            }
                        };
                    }
                    if (zStartsWith) {
                        return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.8
                            @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                            public boolean isMatch(CharSequence charSequence) {
                                return charSequence.length() > 0 && Rule.contains(strSubstring2, charSequence.charAt(0)) == z2;
                            }
                        };
                    }
                    if (zEndsWith) {
                        return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.9
                            @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                            public boolean isMatch(CharSequence charSequence) {
                                return charSequence.length() > 0 && Rule.contains(strSubstring2, charSequence.charAt(charSequence.length() - 1)) == z2;
                            }
                        };
                    }
                }
            }
        } else {
            if (zStartsWith && zEndsWith) {
                return strSubstring.length() == 0 ? new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.3
                    @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                    public boolean isMatch(CharSequence charSequence) {
                        return charSequence.length() == 0;
                    }
                } : new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.4
                    @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                    public boolean isMatch(CharSequence charSequence) {
                        return charSequence.equals(strSubstring);
                    }
                };
            }
            if ((zStartsWith || zEndsWith) && strSubstring.length() == 0) {
                return ALL_STRINGS_RMATCHER;
            }
            if (zStartsWith) {
                return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.5
                    @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                    public boolean isMatch(CharSequence charSequence) {
                        return Rule.startsWith(charSequence, strSubstring);
                    }
                };
            }
            if (zEndsWith) {
                return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.6
                    @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                    public boolean isMatch(CharSequence charSequence) {
                        return Rule.endsWith(charSequence, strSubstring);
                    }
                };
            }
        }
        return new RPattern(str) { // from class: org.apache.commons.codec.language.bm.Rule.10

            /* renamed from: a, reason: collision with root package name */
            Pattern f26578a;

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ String f26579b;

            {
                this.f26579b = str;
                this.f26578a = Pattern.compile(str);
            }

            @Override // org.apache.commons.codec.language.bm.Rule.RPattern
            public boolean isMatch(CharSequence charSequence) {
                return this.f26578a.matcher(charSequence).find();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean startsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        for (int i2 = 0; i2 < charSequence2.length(); i2++) {
            if (charSequence.charAt(i2) != charSequence2.charAt(i2)) {
                return false;
            }
        }
        return true;
    }

    private static String stripQuotes(String str) {
        if (str.startsWith("\"")) {
            str = str.substring(1);
        }
        return str.endsWith("\"") ? str.substring(0, str.length() - 1) : str;
    }

    public RPattern getLContext() {
        return this.lContext;
    }

    public String getPattern() {
        return this.pattern;
    }

    public PhonemeExpr getPhoneme() {
        return this.phoneme;
    }

    public RPattern getRContext() {
        return this.rContext;
    }

    public boolean patternAndContextMatches(CharSequence charSequence, int i2) {
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("Can not match pattern at negative indexes");
        }
        int length = this.pattern.length() + i2;
        if (length > charSequence.length()) {
            return false;
        }
        return charSequence.subSequence(i2, length).equals(this.pattern) && this.rContext.isMatch(charSequence.subSequence(length, charSequence.length())) && this.lContext.isMatch(charSequence.subSequence(0, i2));
    }

    public static List<Rule> getInstance(NameType nameType, RuleType ruleType, String str) {
        List<Rule> list = RULES.get(nameType).get(ruleType).get(str);
        if (list != null) {
            return list;
        }
        throw new IllegalArgumentException(String.format("No rules found for %s, %s, %s.", nameType.getName(), ruleType.getName(), str));
    }

    private static Scanner createScanner(String str) {
        String str2 = String.format("org/apache/commons/codec/language/bm/%s.txt", str);
        InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(str2);
        if (resourceAsStream != null) {
            return new Scanner(resourceAsStream, "UTF-8");
        }
        throw new IllegalArgumentException("Unable to load resource: " + str2);
    }
}
