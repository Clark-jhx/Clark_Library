package com.clark.xlibrary;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘 工具类
 * Created by clark on 2017/6/5 10:42.
 */
public class InputMethodUtil {

    /**
     * 为特定editView打开软键盘
     * @param context
     * @param editText
     */
    public static void openSoftKeyboard(Context context, EditText editText){
        editText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        // 光标定位到末尾
        Editable editable = editText.getEditableText();
        Selection.setSelection((Spannable)editable, editable.toString().length());
    }

    /**
     * 关闭软键盘
     */
    public static void closeSoftKeyboard(Activity activity) {
        //隐藏软键盘
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 切换软键盘的状态
     */
    public static void toggleSoftKeyboardState(Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(
                InputMethodManager.SHOW_IMPLICIT,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断隐藏软键盘是否弹出,弹出就隐藏
     * @param mActivity
     * @return
     */
    public boolean keyBoxIsShow(Activity mActivity) {
        if (mActivity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            //隐藏软键盘
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            return true;
        }
        else {
            return false;
        }
    }
}
