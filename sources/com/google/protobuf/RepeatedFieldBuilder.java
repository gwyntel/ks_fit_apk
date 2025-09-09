package com.google.protobuf;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.MessageOrBuilder;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes2.dex */
public class RepeatedFieldBuilder<MType extends GeneratedMessage, BType extends GeneratedMessage.Builder, IType extends MessageOrBuilder> implements GeneratedMessage.BuilderParent {
    private List<SingleFieldBuilder<MType, BType, IType>> builders;
    private BuilderExternalList<MType, BType, IType> externalBuilderList;
    private MessageExternalList<MType, BType, IType> externalMessageList;
    private MessageOrBuilderExternalList<MType, BType, IType> externalMessageOrBuilderList;
    private boolean isClean;
    private boolean isMessagesListMutable;
    private List<MType> messages;
    private GeneratedMessage.BuilderParent parent;

    private static class BuilderExternalList<MType extends GeneratedMessage, BType extends GeneratedMessage.Builder, IType extends MessageOrBuilder> extends AbstractList<BType> implements List<BType>, RandomAccess {

        /* renamed from: a, reason: collision with root package name */
        RepeatedFieldBuilder f15310a;

        BuilderExternalList(RepeatedFieldBuilder repeatedFieldBuilder) {
            this.f15310a = repeatedFieldBuilder;
        }

        void a() {
            ((AbstractList) this).modCount++;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.f15310a.getCount();
        }

        @Override // java.util.AbstractList, java.util.List
        public BType get(int i2) {
            return (BType) this.f15310a.getBuilder(i2);
        }
    }

    private static class MessageExternalList<MType extends GeneratedMessage, BType extends GeneratedMessage.Builder, IType extends MessageOrBuilder> extends AbstractList<MType> implements List<MType>, RandomAccess {

        /* renamed from: a, reason: collision with root package name */
        RepeatedFieldBuilder f15311a;

        MessageExternalList(RepeatedFieldBuilder repeatedFieldBuilder) {
            this.f15311a = repeatedFieldBuilder;
        }

        void a() {
            ((AbstractList) this).modCount++;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.f15311a.getCount();
        }

        @Override // java.util.AbstractList, java.util.List
        public MType get(int i2) {
            return (MType) this.f15311a.getMessage(i2);
        }
    }

    private static class MessageOrBuilderExternalList<MType extends GeneratedMessage, BType extends GeneratedMessage.Builder, IType extends MessageOrBuilder> extends AbstractList<IType> implements List<IType>, RandomAccess {

        /* renamed from: a, reason: collision with root package name */
        RepeatedFieldBuilder f15312a;

        MessageOrBuilderExternalList(RepeatedFieldBuilder repeatedFieldBuilder) {
            this.f15312a = repeatedFieldBuilder;
        }

        void a() {
            ((AbstractList) this).modCount++;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.f15312a.getCount();
        }

        @Override // java.util.AbstractList, java.util.List
        public IType get(int i2) {
            return (IType) this.f15312a.getMessageOrBuilder(i2);
        }
    }

    public RepeatedFieldBuilder(List<MType> list, boolean z2, GeneratedMessage.BuilderParent builderParent, boolean z3) {
        this.messages = list;
        this.isMessagesListMutable = z2;
        this.parent = builderParent;
        this.isClean = z3;
    }

    private void ensureBuilders() {
        if (this.builders == null) {
            this.builders = new ArrayList(this.messages.size());
            for (int i2 = 0; i2 < this.messages.size(); i2++) {
                this.builders.add(null);
            }
        }
    }

    private void ensureMutableMessageList() {
        if (this.isMessagesListMutable) {
            return;
        }
        this.messages = new ArrayList(this.messages);
        this.isMessagesListMutable = true;
    }

    private void incrementModCounts() {
        MessageExternalList<MType, BType, IType> messageExternalList = this.externalMessageList;
        if (messageExternalList != null) {
            messageExternalList.a();
        }
        BuilderExternalList<MType, BType, IType> builderExternalList = this.externalBuilderList;
        if (builderExternalList != null) {
            builderExternalList.a();
        }
        MessageOrBuilderExternalList<MType, BType, IType> messageOrBuilderExternalList = this.externalMessageOrBuilderList;
        if (messageOrBuilderExternalList != null) {
            messageOrBuilderExternalList.a();
        }
    }

    private void onChanged() {
        GeneratedMessage.BuilderParent builderParent;
        if (!this.isClean || (builderParent = this.parent) == null) {
            return;
        }
        builderParent.markDirty();
        this.isClean = false;
    }

    @CanIgnoreReturnValue
    public RepeatedFieldBuilder<MType, BType, IType> addAllMessages(Iterable<? extends MType> iterable) {
        int size;
        Iterator<? extends MType> it = iterable.iterator();
        while (it.hasNext()) {
            Internal.a(it.next());
        }
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            if (collection.isEmpty()) {
                return this;
            }
            size = collection.size();
        } else {
            size = -1;
        }
        ensureMutableMessageList();
        if (size >= 0) {
            List<MType> list = this.messages;
            if (list instanceof ArrayList) {
                ((ArrayList) list).ensureCapacity(list.size() + size);
            }
        }
        Iterator<? extends MType> it2 = iterable.iterator();
        while (it2.hasNext()) {
            addMessage(it2.next());
        }
        onChanged();
        incrementModCounts();
        return this;
    }

    public BType addBuilder(MType mtype) {
        ensureMutableMessageList();
        ensureBuilders();
        SingleFieldBuilder<MType, BType, IType> singleFieldBuilder = new SingleFieldBuilder<>(mtype, this, this.isClean);
        this.messages.add(null);
        this.builders.add(singleFieldBuilder);
        onChanged();
        incrementModCounts();
        return (BType) singleFieldBuilder.getBuilder();
    }

    @CanIgnoreReturnValue
    public RepeatedFieldBuilder<MType, BType, IType> addMessage(MType mtype) {
        Internal.a(mtype);
        ensureMutableMessageList();
        this.messages.add(mtype);
        List<SingleFieldBuilder<MType, BType, IType>> list = this.builders;
        if (list != null) {
            list.add(null);
        }
        onChanged();
        incrementModCounts();
        return this;
    }

    public List<MType> build() {
        this.isClean = true;
        boolean z2 = this.isMessagesListMutable;
        if (!z2 && this.builders == null) {
            return this.messages;
        }
        if (!z2) {
            for (int i2 = 0; i2 < this.messages.size(); i2++) {
                MType mtype = this.messages.get(i2);
                SingleFieldBuilder<MType, BType, IType> singleFieldBuilder = this.builders.get(i2);
                if (singleFieldBuilder == null || singleFieldBuilder.build() == mtype) {
                }
            }
            return this.messages;
        }
        ensureMutableMessageList();
        for (int i3 = 0; i3 < this.messages.size(); i3++) {
            this.messages.set(i3, getMessage(i3, true));
        }
        List<MType> listUnmodifiableList = Collections.unmodifiableList(this.messages);
        this.messages = listUnmodifiableList;
        this.isMessagesListMutable = false;
        return listUnmodifiableList;
    }

    public void clear() {
        this.messages = Collections.emptyList();
        this.isMessagesListMutable = false;
        List<SingleFieldBuilder<MType, BType, IType>> list = this.builders;
        if (list != null) {
            for (SingleFieldBuilder<MType, BType, IType> singleFieldBuilder : list) {
                if (singleFieldBuilder != null) {
                    singleFieldBuilder.dispose();
                }
            }
            this.builders = null;
        }
        onChanged();
        incrementModCounts();
    }

    public void dispose() {
        this.parent = null;
    }

    public BType getBuilder(int i2) {
        ensureBuilders();
        SingleFieldBuilder<MType, BType, IType> singleFieldBuilder = this.builders.get(i2);
        if (singleFieldBuilder == null) {
            SingleFieldBuilder<MType, BType, IType> singleFieldBuilder2 = new SingleFieldBuilder<>(this.messages.get(i2), this, this.isClean);
            this.builders.set(i2, singleFieldBuilder2);
            singleFieldBuilder = singleFieldBuilder2;
        }
        return (BType) singleFieldBuilder.getBuilder();
    }

    public List<BType> getBuilderList() {
        if (this.externalBuilderList == null) {
            this.externalBuilderList = new BuilderExternalList<>(this);
        }
        return this.externalBuilderList;
    }

    public int getCount() {
        return this.messages.size();
    }

    public MType getMessage(int i2) {
        return (MType) getMessage(i2, false);
    }

    public List<MType> getMessageList() {
        if (this.externalMessageList == null) {
            this.externalMessageList = new MessageExternalList<>(this);
        }
        return this.externalMessageList;
    }

    public IType getMessageOrBuilder(int i2) {
        List<SingleFieldBuilder<MType, BType, IType>> list = this.builders;
        if (list == null) {
            return this.messages.get(i2);
        }
        SingleFieldBuilder<MType, BType, IType> singleFieldBuilder = list.get(i2);
        return singleFieldBuilder == null ? this.messages.get(i2) : (IType) singleFieldBuilder.getMessageOrBuilder();
    }

    public List<IType> getMessageOrBuilderList() {
        if (this.externalMessageOrBuilderList == null) {
            this.externalMessageOrBuilderList = new MessageOrBuilderExternalList<>(this);
        }
        return this.externalMessageOrBuilderList;
    }

    public boolean isEmpty() {
        return this.messages.isEmpty();
    }

    @Override // com.google.protobuf.AbstractMessage.BuilderParent
    public void markDirty() {
        onChanged();
    }

    public void remove(int i2) {
        SingleFieldBuilder<MType, BType, IType> singleFieldBuilderRemove;
        ensureMutableMessageList();
        this.messages.remove(i2);
        List<SingleFieldBuilder<MType, BType, IType>> list = this.builders;
        if (list != null && (singleFieldBuilderRemove = list.remove(i2)) != null) {
            singleFieldBuilderRemove.dispose();
        }
        onChanged();
        incrementModCounts();
    }

    @CanIgnoreReturnValue
    public RepeatedFieldBuilder<MType, BType, IType> setMessage(int i2, MType mtype) {
        SingleFieldBuilder<MType, BType, IType> singleFieldBuilder;
        Internal.a(mtype);
        ensureMutableMessageList();
        this.messages.set(i2, mtype);
        List<SingleFieldBuilder<MType, BType, IType>> list = this.builders;
        if (list != null && (singleFieldBuilder = list.set(i2, null)) != null) {
            singleFieldBuilder.dispose();
        }
        onChanged();
        incrementModCounts();
        return this;
    }

    private MType getMessage(int i2, boolean z2) {
        List<SingleFieldBuilder<MType, BType, IType>> list = this.builders;
        if (list == null) {
            return this.messages.get(i2);
        }
        SingleFieldBuilder<MType, BType, IType> singleFieldBuilder = list.get(i2);
        return singleFieldBuilder == null ? this.messages.get(i2) : z2 ? (MType) singleFieldBuilder.build() : (MType) singleFieldBuilder.getMessage();
    }

    @CanIgnoreReturnValue
    public RepeatedFieldBuilder<MType, BType, IType> addMessage(int i2, MType mtype) {
        Internal.a(mtype);
        ensureMutableMessageList();
        this.messages.add(i2, mtype);
        List<SingleFieldBuilder<MType, BType, IType>> list = this.builders;
        if (list != null) {
            list.add(i2, null);
        }
        onChanged();
        incrementModCounts();
        return this;
    }

    public BType addBuilder(int i2, MType mtype) {
        ensureMutableMessageList();
        ensureBuilders();
        SingleFieldBuilder<MType, BType, IType> singleFieldBuilder = new SingleFieldBuilder<>(mtype, this, this.isClean);
        this.messages.add(i2, null);
        this.builders.add(i2, singleFieldBuilder);
        onChanged();
        incrementModCounts();
        return (BType) singleFieldBuilder.getBuilder();
    }
}
