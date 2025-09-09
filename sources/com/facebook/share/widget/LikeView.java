package com.facebook.share.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.facebook.FacebookException;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.facebook.common.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.internal.LikeBoxCountView;
import com.facebook.share.internal.LikeButton;

@Deprecated
/* loaded from: classes3.dex */
public class LikeView extends FrameLayout {
    private static final int NO_FOREGROUND_COLOR = -1;
    private AuxiliaryViewPosition auxiliaryViewPosition;
    private BroadcastReceiver broadcastReceiver;
    private LinearLayout containerView;
    private LikeActionControllerCreationCallback creationCallback;
    private int edgePadding;
    private boolean explicitlyDisabled;
    private int foregroundColor;
    private HorizontalAlignment horizontalAlignment;
    private int internalPadding;
    private LikeActionController likeActionController;
    private LikeBoxCountView likeBoxCountView;
    private LikeButton likeButton;
    private Style likeViewStyle;
    private String objectId;
    private ObjectType objectType;
    private OnErrorListener onErrorListener;
    private FragmentWrapper parentFragment;
    private TextView socialSentenceView;

    /* renamed from: com.facebook.share.widget.LikeView$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$share$widget$LikeView$AuxiliaryViewPosition;

        static {
            int[] iArr = new int[AuxiliaryViewPosition.values().length];
            $SwitchMap$com$facebook$share$widget$LikeView$AuxiliaryViewPosition = iArr;
            try {
                iArr[AuxiliaryViewPosition.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$share$widget$LikeView$AuxiliaryViewPosition[AuxiliaryViewPosition.BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$share$widget$LikeView$AuxiliaryViewPosition[AuxiliaryViewPosition.INLINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Deprecated
    public enum AuxiliaryViewPosition {
        BOTTOM("bottom", 0),
        INLINE("inline", 1),
        TOP(ViewHierarchyConstants.DIMENSION_TOP_KEY, 2);

        static AuxiliaryViewPosition DEFAULT = BOTTOM;
        private int intValue;
        private String stringValue;

        AuxiliaryViewPosition(String str, int i2) {
            this.stringValue = str;
            this.intValue = i2;
        }

        static AuxiliaryViewPosition fromInt(int i2) {
            for (AuxiliaryViewPosition auxiliaryViewPosition : values()) {
                if (auxiliaryViewPosition.getValue() == i2) {
                    return auxiliaryViewPosition;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getValue() {
            return this.intValue;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.stringValue;
        }
    }

    @Deprecated
    public enum HorizontalAlignment {
        CENTER(TtmlNode.CENTER, 0),
        LEFT("left", 1),
        RIGHT(TtmlNode.RIGHT, 2);

        static HorizontalAlignment DEFAULT = CENTER;
        private int intValue;
        private String stringValue;

        HorizontalAlignment(String str, int i2) {
            this.stringValue = str;
            this.intValue = i2;
        }

        static HorizontalAlignment fromInt(int i2) {
            for (HorizontalAlignment horizontalAlignment : values()) {
                if (horizontalAlignment.getValue() == i2) {
                    return horizontalAlignment;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getValue() {
            return this.intValue;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.stringValue;
        }
    }

    private class LikeActionControllerCreationCallback implements LikeActionController.CreationCallback {
        private boolean isCancelled;

        private LikeActionControllerCreationCallback() {
        }

        public void cancel() {
            this.isCancelled = true;
        }

        @Override // com.facebook.share.internal.LikeActionController.CreationCallback
        public void onComplete(LikeActionController likeActionController, FacebookException facebookException) {
            if (this.isCancelled) {
                return;
            }
            if (likeActionController != null) {
                if (!likeActionController.shouldEnableView()) {
                    facebookException = new FacebookException("Cannot use LikeView. The device may not be supported.");
                }
                LikeView.this.associateWithLikeActionController(likeActionController);
                LikeView.this.updateLikeStateAndLayout();
            }
            if (facebookException != null && LikeView.this.onErrorListener != null) {
                LikeView.this.onErrorListener.onError(facebookException);
            }
            LikeView.this.creationCallback = null;
        }
    }

    private class LikeControllerBroadcastReceiver extends BroadcastReceiver {
        private LikeControllerBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String string = extras.getString(LikeActionController.ACTION_OBJECT_ID_KEY);
                if (!Utility.isNullOrEmpty(string) && !Utility.areObjectsEqual(LikeView.this.objectId, string)) {
                    return;
                }
            }
            if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED.equals(action)) {
                LikeView.this.updateLikeStateAndLayout();
                return;
            }
            if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR.equals(action)) {
                if (LikeView.this.onErrorListener != null) {
                    LikeView.this.onErrorListener.onError(NativeProtocol.getExceptionFromErrorData(extras));
                }
            } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET.equals(action)) {
                LikeView likeView = LikeView.this;
                likeView.setObjectIdAndTypeForced(likeView.objectId, LikeView.this.objectType);
                LikeView.this.updateLikeStateAndLayout();
            }
        }
    }

    @Deprecated
    public enum ObjectType {
        UNKNOWN("unknown", 0),
        OPEN_GRAPH("open_graph", 1),
        PAGE("page", 2);

        private int intValue;
        private String stringValue;
        public static ObjectType DEFAULT = UNKNOWN;

        ObjectType(String str, int i2) {
            this.stringValue = str;
            this.intValue = i2;
        }

        public static ObjectType fromInt(int i2) {
            for (ObjectType objectType : values()) {
                if (objectType.getValue() == i2) {
                    return objectType;
                }
            }
            return null;
        }

        public int getValue() {
            return this.intValue;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.stringValue;
        }
    }

    public interface OnErrorListener {
        void onError(FacebookException facebookException);
    }

    @Deprecated
    public enum Style {
        STANDARD("standard", 0),
        BUTTON("button", 1),
        BOX_COUNT("box_count", 2);

        private int intValue;
        private String stringValue;
        static Style DEFAULT = STANDARD;

        Style(String str, int i2) {
            this.stringValue = str;
            this.intValue = i2;
        }

        static Style fromInt(int i2) {
            for (Style style : values()) {
                if (style.getValue() == i2) {
                    return style;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getValue() {
            return this.intValue;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.stringValue;
        }
    }

    @Deprecated
    public LikeView(Context context) {
        super(context);
        this.likeViewStyle = Style.DEFAULT;
        this.horizontalAlignment = HorizontalAlignment.DEFAULT;
        this.auxiliaryViewPosition = AuxiliaryViewPosition.DEFAULT;
        this.foregroundColor = -1;
        this.explicitlyDisabled = true;
        initialize(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void associateWithLikeActionController(LikeActionController likeActionController) {
        this.likeActionController = likeActionController;
        this.broadcastReceiver = new LikeControllerBroadcastReceiver();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        intentFilter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR);
        intentFilter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
        localBroadcastManager.registerReceiver(this.broadcastReceiver, intentFilter);
    }

    private Activity getActivity() {
        boolean z2;
        Context context = getContext();
        while (true) {
            z2 = context instanceof Activity;
            if (z2 || !(context instanceof ContextWrapper)) {
                break;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (z2) {
            return (Activity) context;
        }
        throw new FacebookException("Unable to get Activity.");
    }

    private Bundle getAnalyticsParameters() {
        Bundle bundle = new Bundle();
        bundle.putString("style", this.likeViewStyle.toString());
        bundle.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_AUXILIARY_POSITION, this.auxiliaryViewPosition.toString());
        bundle.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_HORIZONTAL_ALIGNMENT, this.horizontalAlignment.toString());
        bundle.putString("object_id", Utility.coerceValueIfNullOrEmpty(this.objectId, ""));
        bundle.putString("object_type", this.objectType.toString());
        return bundle;
    }

    private void initialize(Context context) {
        this.edgePadding = getResources().getDimensionPixelSize(R.dimen.com_facebook_likeview_edge_padding);
        this.internalPadding = getResources().getDimensionPixelSize(R.dimen.com_facebook_likeview_internal_padding);
        if (this.foregroundColor == -1) {
            this.foregroundColor = getResources().getColor(R.color.com_facebook_likeview_text_color);
        }
        setBackgroundColor(0);
        this.containerView = new LinearLayout(context);
        this.containerView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        initializeLikeButton(context);
        initializeSocialSentenceView(context);
        initializeLikeCountView(context);
        this.containerView.addView(this.likeButton);
        this.containerView.addView(this.socialSentenceView);
        this.containerView.addView(this.likeBoxCountView);
        addView(this.containerView);
        setObjectIdAndTypeForced(this.objectId, this.objectType);
        updateLikeStateAndLayout();
    }

    private void initializeLikeButton(Context context) {
        LikeActionController likeActionController = this.likeActionController;
        LikeButton likeButton = new LikeButton(context, likeActionController != null && likeActionController.isObjectLiked());
        this.likeButton = likeButton;
        likeButton.setOnClickListener(new View.OnClickListener() { // from class: com.facebook.share.widget.LikeView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LikeView.this.toggleLike();
            }
        });
        this.likeButton.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    }

    private void initializeLikeCountView(Context context) {
        this.likeBoxCountView = new LikeBoxCountView(context);
        this.likeBoxCountView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    }

    private void initializeSocialSentenceView(Context context) {
        TextView textView = new TextView(context);
        this.socialSentenceView = textView;
        textView.setTextSize(0, getResources().getDimension(R.dimen.com_facebook_likeview_text_size));
        this.socialSentenceView.setMaxLines(2);
        this.socialSentenceView.setTextColor(this.foregroundColor);
        this.socialSentenceView.setGravity(17);
        this.socialSentenceView.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
    }

    private void parseAttributes(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes;
        if (attributeSet == null || getContext() == null || (typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.com_facebook_like_view)) == null) {
            return;
        }
        this.objectId = Utility.coerceValueIfNullOrEmpty(typedArrayObtainStyledAttributes.getString(R.styleable.com_facebook_like_view_com_facebook_object_id), null);
        this.objectType = ObjectType.fromInt(typedArrayObtainStyledAttributes.getInt(R.styleable.com_facebook_like_view_com_facebook_object_type, ObjectType.DEFAULT.getValue()));
        Style styleFromInt = Style.fromInt(typedArrayObtainStyledAttributes.getInt(R.styleable.com_facebook_like_view_com_facebook_style, Style.DEFAULT.getValue()));
        this.likeViewStyle = styleFromInt;
        if (styleFromInt == null) {
            throw new IllegalArgumentException("Unsupported value for LikeView 'style'");
        }
        AuxiliaryViewPosition auxiliaryViewPositionFromInt = AuxiliaryViewPosition.fromInt(typedArrayObtainStyledAttributes.getInt(R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position, AuxiliaryViewPosition.DEFAULT.getValue()));
        this.auxiliaryViewPosition = auxiliaryViewPositionFromInt;
        if (auxiliaryViewPositionFromInt == null) {
            throw new IllegalArgumentException("Unsupported value for LikeView 'auxiliary_view_position'");
        }
        HorizontalAlignment horizontalAlignmentFromInt = HorizontalAlignment.fromInt(typedArrayObtainStyledAttributes.getInt(R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment, HorizontalAlignment.DEFAULT.getValue()));
        this.horizontalAlignment = horizontalAlignmentFromInt;
        if (horizontalAlignmentFromInt == null) {
            throw new IllegalArgumentException("Unsupported value for LikeView 'horizontal_alignment'");
        }
        this.foregroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.com_facebook_like_view_com_facebook_foreground_color, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setObjectIdAndTypeForced(String str, ObjectType objectType) {
        tearDownObjectAssociations();
        this.objectId = str;
        this.objectType = objectType;
        if (Utility.isNullOrEmpty(str)) {
            return;
        }
        this.creationCallback = new LikeActionControllerCreationCallback();
        if (isInEditMode()) {
            return;
        }
        LikeActionController.getControllerForObjectId(str, objectType, this.creationCallback);
    }

    private void tearDownObjectAssociations() {
        if (this.broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.broadcastReceiver);
            this.broadcastReceiver = null;
        }
        LikeActionControllerCreationCallback likeActionControllerCreationCallback = this.creationCallback;
        if (likeActionControllerCreationCallback != null) {
            likeActionControllerCreationCallback.cancel();
            this.creationCallback = null;
        }
        this.likeActionController = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleLike() {
        if (this.likeActionController != null) {
            this.likeActionController.toggleLike(this.parentFragment == null ? getActivity() : null, this.parentFragment, getAnalyticsParameters());
        }
    }

    private void updateBoxCountCaretPosition() {
        int i2 = AnonymousClass2.$SwitchMap$com$facebook$share$widget$LikeView$AuxiliaryViewPosition[this.auxiliaryViewPosition.ordinal()];
        if (i2 == 1) {
            this.likeBoxCountView.setCaretPosition(LikeBoxCountView.LikeBoxCountViewCaretPosition.BOTTOM);
        } else if (i2 == 2) {
            this.likeBoxCountView.setCaretPosition(LikeBoxCountView.LikeBoxCountViewCaretPosition.TOP);
        } else {
            if (i2 != 3) {
                return;
            }
            this.likeBoxCountView.setCaretPosition(this.horizontalAlignment == HorizontalAlignment.RIGHT ? LikeBoxCountView.LikeBoxCountViewCaretPosition.RIGHT : LikeBoxCountView.LikeBoxCountViewCaretPosition.LEFT);
        }
    }

    private void updateLayout() {
        LikeActionController likeActionController;
        View view;
        LikeActionController likeActionController2;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.containerView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.likeButton.getLayoutParams();
        HorizontalAlignment horizontalAlignment = this.horizontalAlignment;
        int i2 = horizontalAlignment == HorizontalAlignment.LEFT ? 3 : horizontalAlignment == HorizontalAlignment.CENTER ? 1 : 5;
        layoutParams.gravity = i2 | 48;
        layoutParams2.gravity = i2;
        this.socialSentenceView.setVisibility(8);
        this.likeBoxCountView.setVisibility(8);
        if (this.likeViewStyle == Style.STANDARD && (likeActionController2 = this.likeActionController) != null && !Utility.isNullOrEmpty(likeActionController2.getSocialSentence())) {
            view = this.socialSentenceView;
        } else {
            if (this.likeViewStyle != Style.BOX_COUNT || (likeActionController = this.likeActionController) == null || Utility.isNullOrEmpty(likeActionController.getLikeCountString())) {
                return;
            }
            updateBoxCountCaretPosition();
            view = this.likeBoxCountView;
        }
        view.setVisibility(0);
        ((LinearLayout.LayoutParams) view.getLayoutParams()).gravity = i2;
        LinearLayout linearLayout = this.containerView;
        AuxiliaryViewPosition auxiliaryViewPosition = this.auxiliaryViewPosition;
        AuxiliaryViewPosition auxiliaryViewPosition2 = AuxiliaryViewPosition.INLINE;
        linearLayout.setOrientation(auxiliaryViewPosition != auxiliaryViewPosition2 ? 1 : 0);
        AuxiliaryViewPosition auxiliaryViewPosition3 = this.auxiliaryViewPosition;
        if (auxiliaryViewPosition3 == AuxiliaryViewPosition.TOP || (auxiliaryViewPosition3 == auxiliaryViewPosition2 && this.horizontalAlignment == HorizontalAlignment.RIGHT)) {
            this.containerView.removeView(this.likeButton);
            this.containerView.addView(this.likeButton);
        } else {
            this.containerView.removeView(view);
            this.containerView.addView(view);
        }
        int i3 = AnonymousClass2.$SwitchMap$com$facebook$share$widget$LikeView$AuxiliaryViewPosition[this.auxiliaryViewPosition.ordinal()];
        if (i3 == 1) {
            int i4 = this.edgePadding;
            view.setPadding(i4, i4, i4, this.internalPadding);
            return;
        }
        if (i3 == 2) {
            int i5 = this.edgePadding;
            view.setPadding(i5, this.internalPadding, i5, i5);
        } else {
            if (i3 != 3) {
                return;
            }
            if (this.horizontalAlignment == HorizontalAlignment.RIGHT) {
                int i6 = this.edgePadding;
                view.setPadding(i6, i6, this.internalPadding, i6);
            } else {
                int i7 = this.internalPadding;
                int i8 = this.edgePadding;
                view.setPadding(i7, i8, i8, i8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLikeStateAndLayout() {
        boolean zShouldEnableView = !this.explicitlyDisabled;
        LikeActionController likeActionController = this.likeActionController;
        if (likeActionController == null) {
            this.likeButton.setSelected(false);
            this.socialSentenceView.setText((CharSequence) null);
            this.likeBoxCountView.setText(null);
        } else {
            this.likeButton.setSelected(likeActionController.isObjectLiked());
            this.socialSentenceView.setText(this.likeActionController.getSocialSentence());
            this.likeBoxCountView.setText(this.likeActionController.getLikeCountString());
            zShouldEnableView &= this.likeActionController.shouldEnableView();
        }
        super.setEnabled(zShouldEnableView);
        this.likeButton.setEnabled(zShouldEnableView);
        updateLayout();
    }

    @Deprecated
    public OnErrorListener getOnErrorListener() {
        return this.onErrorListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        setObjectIdAndType(null, ObjectType.UNKNOWN);
        super.onDetachedFromWindow();
    }

    @Deprecated
    public void setAuxiliaryViewPosition(AuxiliaryViewPosition auxiliaryViewPosition) {
        if (auxiliaryViewPosition == null) {
            auxiliaryViewPosition = AuxiliaryViewPosition.DEFAULT;
        }
        if (this.auxiliaryViewPosition != auxiliaryViewPosition) {
            this.auxiliaryViewPosition = auxiliaryViewPosition;
            updateLayout();
        }
    }

    @Override // android.view.View
    @Deprecated
    public void setEnabled(boolean z2) {
        this.explicitlyDisabled = true;
        updateLikeStateAndLayout();
    }

    @Deprecated
    public void setForegroundColor(int i2) {
        if (this.foregroundColor != i2) {
            this.socialSentenceView.setTextColor(i2);
        }
    }

    @Deprecated
    public void setFragment(Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    @Deprecated
    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        if (horizontalAlignment == null) {
            horizontalAlignment = HorizontalAlignment.DEFAULT;
        }
        if (this.horizontalAlignment != horizontalAlignment) {
            this.horizontalAlignment = horizontalAlignment;
            updateLayout();
        }
    }

    @Deprecated
    public void setLikeViewStyle(Style style) {
        if (style == null) {
            style = Style.DEFAULT;
        }
        if (this.likeViewStyle != style) {
            this.likeViewStyle = style;
            updateLayout();
        }
    }

    @Deprecated
    public void setObjectIdAndType(String str, ObjectType objectType) {
        String strCoerceValueIfNullOrEmpty = Utility.coerceValueIfNullOrEmpty(str, null);
        if (objectType == null) {
            objectType = ObjectType.DEFAULT;
        }
        if (Utility.areObjectsEqual(strCoerceValueIfNullOrEmpty, this.objectId) && objectType == this.objectType) {
            return;
        }
        setObjectIdAndTypeForced(strCoerceValueIfNullOrEmpty, objectType);
        updateLikeStateAndLayout();
    }

    @Deprecated
    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    @Deprecated
    public void setFragment(android.app.Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    @Deprecated
    public LikeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.likeViewStyle = Style.DEFAULT;
        this.horizontalAlignment = HorizontalAlignment.DEFAULT;
        this.auxiliaryViewPosition = AuxiliaryViewPosition.DEFAULT;
        this.foregroundColor = -1;
        this.explicitlyDisabled = true;
        parseAttributes(attributeSet);
        initialize(context);
    }
}
