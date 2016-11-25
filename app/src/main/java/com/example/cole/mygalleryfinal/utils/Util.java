package com.example.cole.mygalleryfinal.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Util {
    /**
     * 弹出框带有"取消"按钮,并且可设置监听器
     * 自定义布局的Dialogs
     * 如果按钮按钮文案为空时,默认显示确定,取消文案
     *
     * @param context        上下文
     * @param charSequences  提示文案数组
     * @param cancelBtn      取消按钮的文案
     * @param cancelListener 取消按钮的监听器
     * @param charListener   数组按钮的监听器
     */
    public static void displayDisenbleCancelBtnDialog(Context context, CharSequence[] charSequences,
                                                      String cancelBtn, DialogInterface.OnClickListener cancelListener, DialogInterface.OnClickListener charListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        if (cancelBtn == null || "".equals(cancelBtn)) {
            cancelBtn = "取消";
        }
        builder.setItems(charSequences, charListener);
        builder.setNegativeButton(cancelBtn, cancelListener);
        builder.show();
    }
}
