package com.clark.xlibrary;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

/**
 * 对话框
 * Created by clark on 2017/6/5 14:06.
 */
public class AlertDialogUtils {
    /**
     * 显示一个 对话框
     *
     * @param context 上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param title 标题
     * @param message 消息
     * @param confirmButton 确认按钮
     * @param confirmButtonClickListener 确认按钮点击监听器
     * @param centerButton 中间按钮
     * @param centerButtonClickListener 中间按钮点击监听器
     * @param cancelButton 取消按钮
     * @param cancelButtonClickListener 取消按钮点击监听器
     * @param onShowListener 显示监听器
     * @param cancelable false:不允许通过点击返回按钮或者点击对话框之外的位置关闭对话框
     * @param onCancelListener 取消监听器
     * @param onDismissListener 销毁监听器
     * @return 对话框
     */
    public static AlertDialog showAlert(Context context, String title, String message, String confirmButton
            , DialogInterface.OnClickListener confirmButtonClickListener, String centerButton
            , DialogInterface.OnClickListener centerButtonClickListener, String cancelButton
            , DialogInterface.OnClickListener cancelButtonClickListener, DialogInterface.OnShowListener onShowListener
            , boolean cancelable, DialogInterface.OnCancelListener onCancelListener, DialogInterface.OnDismissListener onDismissListener) {

        AlertDialog.Builder promptBuilder = new AlertDialog.Builder(context);
        if (title != null) {
            promptBuilder.setTitle(title);
        }
        if (message != null) {
            promptBuilder.setMessage(message);
        }
        if (confirmButton != null) {
            promptBuilder.setPositiveButton(confirmButton,
                    confirmButtonClickListener);
        }
        if (centerButton != null) {
            promptBuilder.setNeutralButton(centerButton,
                    centerButtonClickListener);
        }
        if (cancelButton != null) {
            promptBuilder.setNegativeButton(cancelButton,
                    cancelButtonClickListener);
        }
        promptBuilder.setCancelable(cancelable);
        if (cancelable) {
            promptBuilder.setOnCancelListener(onCancelListener);
        }
        AlertDialog alertDialog = promptBuilder.create();
        if (!(context instanceof Activity)) {
            alertDialog.getWindow()
                    .setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        alertDialog.setOnDismissListener(onDismissListener);
        alertDialog.setOnShowListener(onShowListener);
        alertDialog.show();
        return alertDialog;
    }


    /**
     * 显示一个 对话框
     *
     * @param context 上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param title 标题
     * @param message 消息
     * @param confirmButton 确认按钮
     * @param confirmButtonClickListener 确认按钮点击监听器
     * @param cancelButton 取消按钮
     * @param cancelButtonClickListener 取消按钮点击监听器
     * @param cancelable false:不允许通过点击返回按钮或者点击对话框之外的位置关闭对话框
     * @return 对话框
     */
    public static AlertDialog showAlert(Context context, String title, String message, String confirmButton
            , DialogInterface.OnClickListener confirmButtonClickListener, String cancelButton
            , DialogInterface.OnClickListener cancelButtonClickListener, boolean cancelable) {

        return showAlert(context, title, message, confirmButton,
                confirmButtonClickListener, null, null, cancelButton,
                cancelButtonClickListener, null, cancelable, null, null);
    }


    /**
     * 显示一个 提示框
     *
     * @param context 上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param message 提示的消息
     * @param confirmButton 确定按钮的名字
     *                      @param cancelable false:不允许通过点击返回按钮或者点击对话框之外的位置关闭对话框
     */
    public static AlertDialog showPrompt(Context context, String message, String confirmButton, boolean cancelable) {
        return showAlert(context, null, message, confirmButton, null, null,
                null, null, null, null, cancelable, null, null);
    }


    /**
     * 显示一个 提示框
     *
     * @param context 上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param message 提示的消息
     * @param cancelable false:不允许通过点击返回按钮或者点击对话框之外的位置关闭对话框
     */
    public static AlertDialog showPrompt(Context context, String message, boolean cancelable) {
        return showAlert(context, null, message, "OK", null, null, null, null,
                null, null, cancelable, null, null);
    }
}