package com.luck.picture.lib.widget;

import com.luck.picture.lib.widget.SlideSelectTouchListener;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public class SlideSelectionHandler implements SlideSelectTouchListener.OnAdvancedSlideSelectListener {
    private HashSet<Integer> mOriginalSelection;
    private final ISelectionHandler mSelectionHandler;
    private ISelectionStartFinishedListener mStartFinishedListener = null;

    public interface ISelectionHandler {
        void changeSelection(int i2, int i3, boolean z2, boolean z3);

        Set<Integer> getSelection();
    }

    public interface ISelectionStartFinishedListener {
        void onSelectionFinished(int i2);

        void onSelectionStarted(int i2, boolean z2);
    }

    public SlideSelectionHandler(ISelectionHandler iSelectionHandler) {
        this.mSelectionHandler = iSelectionHandler;
    }

    private void checkedChangeSelection(int i2, int i3, boolean z2) {
        this.mSelectionHandler.changeSelection(i2, i3, z2, false);
    }

    @Override // com.luck.picture.lib.widget.SlideSelectTouchListener.OnSlideSelectListener
    public void onSelectChange(int i2, int i3, boolean z2) {
        while (i2 <= i3) {
            checkedChangeSelection(i2, i2, z2 != this.mOriginalSelection.contains(Integer.valueOf(i2)));
            i2++;
        }
    }

    @Override // com.luck.picture.lib.widget.SlideSelectTouchListener.OnAdvancedSlideSelectListener
    public void onSelectionFinished(int i2) {
        this.mOriginalSelection = null;
        ISelectionStartFinishedListener iSelectionStartFinishedListener = this.mStartFinishedListener;
        if (iSelectionStartFinishedListener != null) {
            iSelectionStartFinishedListener.onSelectionFinished(i2);
        }
    }

    @Override // com.luck.picture.lib.widget.SlideSelectTouchListener.OnAdvancedSlideSelectListener
    public void onSelectionStarted(int i2) {
        this.mOriginalSelection = new HashSet<>();
        Set<Integer> selection = this.mSelectionHandler.getSelection();
        if (selection != null) {
            this.mOriginalSelection.addAll(selection);
        }
        boolean zContains = this.mOriginalSelection.contains(Integer.valueOf(i2));
        this.mSelectionHandler.changeSelection(i2, i2, !this.mOriginalSelection.contains(Integer.valueOf(i2)), true);
        ISelectionStartFinishedListener iSelectionStartFinishedListener = this.mStartFinishedListener;
        if (iSelectionStartFinishedListener != null) {
            iSelectionStartFinishedListener.onSelectionStarted(i2, zContains);
        }
    }

    public SlideSelectionHandler withStartFinishedListener(ISelectionStartFinishedListener iSelectionStartFinishedListener) {
        this.mStartFinishedListener = iSelectionStartFinishedListener;
        return this;
    }
}
