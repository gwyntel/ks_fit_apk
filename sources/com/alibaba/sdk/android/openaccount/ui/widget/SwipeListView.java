package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;

/* loaded from: classes2.dex */
public class SwipeListView extends ListView {
    private static final String TAG = "oa_ui_swipe";
    private View mCurrentItemView;
    private ScrollLinearLayout mCurrentScrollView;
    private int mDeleteButtonWidth;
    private View mDeleteView;
    private float mFirstX;
    private float mFirstY;
    private boolean mIsGiveupTouchEvent;
    private Boolean mIsHorizontal;
    private boolean mIsShown;
    private boolean mIsStatusMarked;
    private AtomListListener mListener;
    private STATUS mStatus;
    private OnStatusChangeListener mStatusListener;
    private boolean mSupportQuickMark;
    private boolean mSupportSwipeDelete;

    public interface AtomListListener {
        void deleteItem(int i2);
    }

    public enum DRAG_STATUS {
        MOVING,
        FINISH
    }

    public interface OnStatusChangeListener {
        void onStatusChange(View view, int i2, READ_STATUS read_status, DRAG_STATUS drag_status);
    }

    public enum READ_STATUS {
        UNREAD,
        READ
    }

    private enum STATUS {
        DRAGGING,
        SHOW,
        IDLE
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SwipeListView(Context context) {
        super(context);
        this.mDeleteButtonWidth = 80;
        this.mIsShown = false;
        this.mSupportSwipeDelete = true;
        this.mSupportQuickMark = false;
        this.mIsStatusMarked = true;
        this.mStatusListener = null;
        this.mIsGiveupTouchEvent = false;
        this.mStatus = STATUS.IDLE;
        this.mListener = (AtomListListener) context;
        this.mDeleteButtonWidth = context.getResources().getDimensionPixelSize(ResourceUtils.getIdentifier(context, "dimen", "ali_sdk_openaccount_swipe_delete_button_width"));
    }

    private boolean judgeScrollDirection(float f2, float f3) {
        boolean z2;
        if (Math.abs(f2) > 30.0f) {
            if (Math.abs(f2) > Math.abs(f3) * 2.0f) {
                this.mIsHorizontal = Boolean.TRUE;
            } else {
                this.mIsHorizontal = Boolean.FALSE;
            }
            z2 = true;
        } else {
            z2 = false;
        }
        if (AliSDKLogger.isDebugEnabled()) {
            AliSDKLogger.d(TAG, "judgeScrollDirection, mIsHorizontal=" + this.mIsHorizontal);
        }
        return z2;
    }

    private void showDeleteButton(View view) {
        ScrollLinearLayout scrollLinearLayout = this.mCurrentScrollView;
        if (scrollLinearLayout == null) {
            if (AliSDKLogger.isDebugEnabled()) {
                AliSDKLogger.d(TAG, "showDeleteButton nothing");
                return;
            }
            return;
        }
        scrollLinearLayout.smoothScrollTo(scrollLinearLayout.getScrollX(), 0, this.mDeleteButtonWidth, 0);
        this.mIsShown = true;
        this.mStatus = STATUS.SHOW;
        this.mDeleteView.setClickable(true);
        if (AliSDKLogger.isDebugEnabled()) {
            AliSDKLogger.d(TAG, "showDeleteButton viewl");
        }
    }

    public void clearState() {
        this.mCurrentItemView = null;
    }

    public boolean deleteButtonShown() {
        return this.mIsShown || this.mStatus != STATUS.IDLE;
    }

    public void deleteItem(View view) {
        hiddenDeleteButton(true, true);
    }

    public int getRightViewWidth() {
        return this.mDeleteButtonWidth;
    }

    public void hiddenDeleteButton(boolean z2, boolean z3) {
        ScrollLinearLayout scrollLinearLayout = this.mCurrentScrollView;
        if (scrollLinearLayout == null) {
            if (AliSDKLogger.isDebugEnabled()) {
                AliSDKLogger.d(TAG, "hiddenDeleteButton viewl=nothing");
                return;
            }
            return;
        }
        scrollLinearLayout.stopScroll();
        if (z2) {
            this.mCurrentScrollView.scrollTo(0, 0);
        } else {
            ScrollLinearLayout scrollLinearLayout2 = this.mCurrentScrollView;
            scrollLinearLayout2.smoothScrollTo(scrollLinearLayout2.getScrollX(), 0, 0, 0);
        }
        this.mIsShown = false;
        this.mStatus = STATUS.IDLE;
        this.mIsStatusMarked = false;
        this.mCurrentItemView = null;
        this.mDeleteView.setClickable(false);
        setLongClickable(true);
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mSupportSwipeDelete) {
            return super.onTouchEvent(motionEvent);
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mIsStatusMarked = false;
            this.mFirstX = x2;
            this.mFirstY = y2;
            this.mIsHorizontal = null;
            final int iPointToPosition = pointToPosition((int) x2, (int) y2);
            if (AliSDKLogger.isDebugEnabled()) {
                AliSDKLogger.d(TAG, "onTouchEvent, ACTION_DOWN, motionPosition=" + iPointToPosition);
            }
            if (iPointToPosition >= 0) {
                View childAt = getChildAt(iPointToPosition - getFirstVisiblePosition());
                if (!this.mIsShown) {
                    this.mCurrentItemView = childAt;
                    View viewFindViewById = childAt.findViewById(ResourceUtils.getRId("alisdk_openaccount_id_item_delete_bt"));
                    this.mDeleteView = viewFindViewById;
                    viewFindViewById.setVisibility(4);
                    this.mDeleteView.setOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.SwipeListView.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            if (AliSDKLogger.isDebugEnabled()) {
                                AliSDKLogger.d(SwipeListView.TAG, "Delete button clicked.");
                            }
                            SwipeListView.this.mListener.deleteItem(iPointToPosition);
                        }
                    });
                    this.mCurrentScrollView = (ScrollLinearLayout) this.mCurrentItemView.findViewById(ResourceUtils.getRId("alisdk_openaccount_id_item_swipe_left"));
                } else {
                    if (childAt != this.mCurrentItemView) {
                        this.mIsGiveupTouchEvent = true;
                        hiddenDeleteButton(false, true);
                        return true;
                    }
                    childAt.setPressed(false);
                }
            } else if (this.mIsShown) {
                hiddenDeleteButton(false, false);
            }
            this.mIsGiveupTouchEvent = false;
            if (AliSDKLogger.isDebugEnabled()) {
                AliSDKLogger.d(TAG, "onTouchEvent, ACTION_DOWN");
            }
        } else if (action == 1) {
            Log.d(TAG, "onTouchEvent, ACTION_UP, mIsHorizontal=" + this.mIsHorizontal);
            if (this.mIsGiveupTouchEvent) {
                MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                motionEventObtain.setAction(3);
                super.onTouchEvent(motionEventObtain);
                motionEventObtain.recycle();
                return true;
            }
            Boolean bool = this.mIsHorizontal;
            if (bool != null && bool.booleanValue()) {
                float f2 = this.mFirstX - x2;
                if (this.mIsShown) {
                    f2 += this.mDeleteButtonWidth;
                }
                if (f2 > this.mDeleteButtonWidth / 2) {
                    if (AliSDKLogger.isDebugEnabled()) {
                        AliSDKLogger.d(TAG, "onTouchEvent, ACTION_UP, showDeleteButton");
                    }
                    showDeleteButton(this.mCurrentItemView);
                } else {
                    if (AliSDKLogger.isDebugEnabled()) {
                        AliSDKLogger.d(TAG, "onTouchEvent, ACTION_UP, hiddenDeleteButton");
                    }
                    hiddenDeleteButton(false, true);
                }
                MotionEvent motionEventObtain2 = MotionEvent.obtain(motionEvent);
                motionEventObtain2.setAction(3);
                super.onTouchEvent(motionEventObtain2);
                motionEventObtain2.recycle();
                return true;
            }
        } else if (action == 2) {
            if (AliSDKLogger.isDebugEnabled()) {
                AliSDKLogger.d(TAG, "onTouchEvent, ACTION_MOVE");
            }
            if (this.mIsGiveupTouchEvent) {
                return true;
            }
            if (this.mCurrentItemView != null) {
                float f3 = x2 - this.mFirstX;
                float f4 = y2 - this.mFirstY;
                if (this.mIsHorizontal != null || judgeScrollDirection(f3, f4)) {
                    if (AliSDKLogger.isDebugEnabled()) {
                        AliSDKLogger.d(TAG, "onTouchEvent, ACTION_MOVE mIsHorizontal=" + this.mIsHorizontal);
                    }
                    if (this.mIsHorizontal.booleanValue()) {
                        setLongClickable(false);
                        setPressed(false);
                        Log.d("account", "1mCurrentItemView:" + this.mCurrentItemView);
                        this.mCurrentItemView.setPressed(false);
                        if (f3 <= 0.0f && this.mIsStatusMarked) {
                            this.mIsStatusMarked = false;
                        }
                        if (f3 < 0.0f || this.mIsShown) {
                            if (AliSDKLogger.isDebugEnabled()) {
                                AliSDKLogger.d(TAG, "onTouchEvent, ACTION_MOVE, dx=" + f3);
                            }
                            if (this.mIsShown) {
                                f3 -= this.mDeleteButtonWidth;
                                if (AliSDKLogger.isDebugEnabled()) {
                                    AliSDKLogger.d(TAG, "onTouchEvent, ACTION_MOVE, dx=" + f3);
                                }
                            }
                            if (f3 < 0.0f) {
                                Log.d("account", "mDeleteView.setVisibility");
                                this.mDeleteView.setVisibility(0);
                                this.mCurrentScrollView.scrollTo((int) (-f3), 0);
                                this.mStatus = STATUS.DRAGGING;
                            } else {
                                Log.d("account", "mDeleteView.setVisibility INVISIBLE");
                                this.mDeleteView.setVisibility(4);
                                if (this.mSupportQuickMark) {
                                    this.mCurrentScrollView.scrollTo((int) (-f3), 0);
                                    this.mStatus = STATUS.DRAGGING;
                                } else {
                                    hiddenDeleteButton(false, false);
                                }
                            }
                            if (AliSDKLogger.isDebugEnabled()) {
                                AliSDKLogger.d(TAG, "onTouchEvent, ACTION_MOVE");
                            }
                        } else if (f3 > 0.0f && this.mSupportQuickMark) {
                            if (AliSDKLogger.isDebugEnabled()) {
                                AliSDKLogger.d(TAG, "onTouchEvent, mSupportQuickMark  mDeleteView invisible ACTION_MOVE, dx=" + f3);
                            }
                            this.mDeleteView.setVisibility(4);
                            if (f3 > this.mDeleteButtonWidth && !this.mIsStatusMarked) {
                                this.mIsStatusMarked = true;
                            }
                            this.mCurrentScrollView.stopScroll();
                            float f5 = -f3;
                            this.mCurrentScrollView.scrollTo((int) f5, 0);
                            this.mStatus = STATUS.DRAGGING;
                            if (AliSDKLogger.isDebugEnabled()) {
                                AliSDKLogger.d(TAG, "onTouchEvent, ACTION_MOVE, scroll to " + f5);
                            }
                        }
                        MotionEvent motionEventObtain3 = MotionEvent.obtain(motionEvent);
                        motionEventObtain3.setAction(3);
                        super.onTouchEvent(motionEventObtain3);
                        motionEventObtain3.recycle();
                        return true;
                    }
                    if (this.mIsShown) {
                        if (AliSDKLogger.isDebugEnabled()) {
                            AliSDKLogger.d(TAG, "onTouchEvent, ACTION_MOVE, hiddenDeleteButton");
                        }
                        hiddenDeleteButton(false, false);
                        this.mIsGiveupTouchEvent = true;
                        MotionEvent motionEventObtain4 = MotionEvent.obtain(motionEvent);
                        motionEventObtain4.setAction(3);
                        super.onTouchEvent(motionEventObtain4);
                        motionEventObtain4.recycle();
                        return true;
                    }
                    this.mCurrentItemView = null;
                    Log.d("account", "mCurrentItemView=" + ((Object) null));
                } else {
                    if (this.mIsShown) {
                        if (AliSDKLogger.isDebugEnabled()) {
                            AliSDKLogger.d(TAG, "onTouchEvent, ACTION_MOVE but break, mIsHorizontal=" + this.mIsHorizontal + " return true");
                        }
                        return true;
                    }
                    if (AliSDKLogger.isDebugEnabled()) {
                        AliSDKLogger.d(TAG, "onTouchEvent, ACTION_MOVE but break, mIsHorizontal=" + this.mIsHorizontal + " return false");
                    }
                }
            }
        } else if (action == 3 && AliSDKLogger.isDebugEnabled()) {
            AliSDKLogger.d(TAG, "onTouchEvent, ACTION_CANCEL");
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setOnStatusChangeListener(OnStatusChangeListener onStatusChangeListener) {
        this.mStatusListener = onStatusChangeListener;
    }

    public void setRightViewWidth(int i2) {
        this.mDeleteButtonWidth = i2;
    }

    public void setSupportSwipeDelete(boolean z2) {
        this.mSupportSwipeDelete = z2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SwipeListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeleteButtonWidth = 80;
        this.mIsShown = false;
        this.mSupportSwipeDelete = true;
        this.mSupportQuickMark = false;
        this.mIsStatusMarked = true;
        this.mStatusListener = null;
        this.mIsGiveupTouchEvent = false;
        this.mStatus = STATUS.IDLE;
        this.mListener = (AtomListListener) context;
        this.mDeleteButtonWidth = context.getResources().getDimensionPixelSize(ResourceUtils.getIdentifier(context, "dimen", "ali_sdk_openaccount_swipe_delete_button_width"));
    }

    public SwipeListView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mDeleteButtonWidth = 80;
        this.mIsShown = false;
        this.mSupportSwipeDelete = true;
        this.mSupportQuickMark = false;
        this.mIsStatusMarked = true;
        this.mStatusListener = null;
        this.mIsGiveupTouchEvent = false;
        this.mStatus = STATUS.IDLE;
        this.mDeleteButtonWidth = context.getResources().getDimensionPixelSize(ResourceUtils.getIdentifier(context, "dimen", "ali_sdk_openaccount_swipe_delete_button_width"));
    }
}
