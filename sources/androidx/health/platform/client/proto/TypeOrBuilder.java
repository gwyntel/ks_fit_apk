package androidx.health.platform.client.proto;

import java.util.List;

/* loaded from: classes.dex */
public interface TypeOrBuilder extends MessageLiteOrBuilder {
    Field getFields(int i2);

    int getFieldsCount();

    List<Field> getFieldsList();

    String getName();

    ByteString getNameBytes();

    String getOneofs(int i2);

    ByteString getOneofsBytes(int i2);

    int getOneofsCount();

    List<String> getOneofsList();

    Option getOptions(int i2);

    int getOptionsCount();

    List<Option> getOptionsList();

    SourceContext getSourceContext();

    Syntax getSyntax();

    int getSyntaxValue();

    boolean hasSourceContext();
}
