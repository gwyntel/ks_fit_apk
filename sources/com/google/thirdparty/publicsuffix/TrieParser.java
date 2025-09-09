package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import java.util.Deque;

@GwtCompatible
/* loaded from: classes2.dex */
final class TrieParser {
    private static final Joiner DIRECT_JOINER = Joiner.on("");

    static ImmutableMap a(String str) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        int length = str.length();
        int iDoParseTrieToBuilder = 0;
        while (iDoParseTrieToBuilder < length) {
            iDoParseTrieToBuilder += doParseTrieToBuilder(Queues.newArrayDeque(), str, iDoParseTrieToBuilder, builder);
        }
        return builder.buildOrThrow();
    }

    static ImmutableMap b(CharSequence... charSequenceArr) {
        return a(DIRECT_JOINER.join(charSequenceArr));
    }

    private static int doParseTrieToBuilder(Deque<CharSequence> deque, CharSequence charSequence, int i2, ImmutableMap.Builder<String, PublicSuffixType> builder) {
        int length = charSequence.length();
        char cCharAt = 0;
        int i3 = i2;
        while (i3 < length && (cCharAt = charSequence.charAt(i3)) != '&' && cCharAt != '?' && cCharAt != '!' && cCharAt != ':' && cCharAt != ',') {
            i3++;
        }
        deque.push(reverse(charSequence.subSequence(i2, i3)));
        if (cCharAt == '!' || cCharAt == '?' || cCharAt == ':' || cCharAt == ',') {
            String strJoin = DIRECT_JOINER.join(deque);
            if (strJoin.length() > 0) {
                builder.put(strJoin, PublicSuffixType.fromCode(cCharAt));
            }
        }
        int iDoParseTrieToBuilder = i3 + 1;
        if (cCharAt != '?' && cCharAt != ',') {
            while (iDoParseTrieToBuilder < length) {
                iDoParseTrieToBuilder += doParseTrieToBuilder(deque, charSequence, iDoParseTrieToBuilder, builder);
                if (charSequence.charAt(iDoParseTrieToBuilder) == '?' || charSequence.charAt(iDoParseTrieToBuilder) == ',') {
                    iDoParseTrieToBuilder++;
                    break;
                }
            }
        }
        deque.pop();
        return iDoParseTrieToBuilder - i2;
    }

    private static CharSequence reverse(CharSequence charSequence) {
        return new StringBuilder(charSequence).reverse();
    }
}
