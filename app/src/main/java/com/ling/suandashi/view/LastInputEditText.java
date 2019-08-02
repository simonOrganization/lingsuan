package com.ling.suandashi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @author Imxu
 * @time 2019/7/9 15:36
 * @des ${TODO}
 */
public class LastInputEditText extends AppCompatEditText {

    public LastInputEditText(Context context) {
        super(context);
    }
    public LastInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public LastInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selStart == selEnd) { // 防止不能多选
             setSelection(getText().length()); // 保证光标始终在最后面
        }
    }

    private OnFinishComposingListener mFinishComposingListener;

    public void setOnFinishComposingListener(OnFinishComposingListener listener){
        this.mFinishComposingListener =listener;
    }
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new MyInputConnection(super.onCreateInputConnection(outAttrs), false);
    }
    public class MyInputConnection extends InputConnectionWrapper {
        public MyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }
        @Override
        public boolean finishComposingText() {
            boolean finishComposing = super.finishComposingText();
            if(mFinishComposingListener != null){
                mFinishComposingListener.finishComposing();
            }
            return finishComposing;
        }
    }
    public interface OnFinishComposingListener{
        void finishComposing();
    }
}
