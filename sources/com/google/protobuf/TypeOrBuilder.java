package com.google.protobuf;

import java.util.List;

/* loaded from: classes2.dex */
public interface TypeOrBuilder extends MessageOrBuilder {
    Field getFields(int i2);

    int getFieldsCount();

    List<Field> getFieldsList();

    FieldOrBuilder getFieldsOrBuilder(int i2);

    List<? extends FieldOrBuilder> getFieldsOrBuilderList();

    String getName();

    ByteString getNameBytes();

    String getOneofs(int i2);

    ByteString getOneofsBytes(int i2);

    int getOneofsCount();

    List<String> getOneofsList();

    Option getOptions(int i2);

    int getOptionsCount();

    List<Option> getOptionsList();

    OptionOrBuilder getOptionsOrBuilder(int i2);

    List<? extends OptionOrBuilder> getOptionsOrBuilderList();

    SourceContext getSourceContext();

    SourceContextOrBuilder getSourceContextOrBuilder();

    Syntax getSyntax();

    int getSyntaxValue();

    boolean hasSourceContext();
}
