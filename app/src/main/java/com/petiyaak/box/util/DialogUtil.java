package com.petiyaak.box.util;

import android.content.Context;

import com.petiyaak.box.customview.AddUserDialog;
import com.petiyaak.box.customview.DialogContent;
import com.petiyaak.box.customview.InputDialog;
import com.petiyaak.box.customview.OnDialogClick;

/**
 * Created by chenzhaolin on 2019/11/7.
 */
public class DialogUtil {

    /**
     *   添加一个设备
     */

    public static void addPetiyaak(Context context, OnDialogClick onDialogClick) {
        DialogContent content = new DialogContent();
        content.desc = "title";
        content.ok = "next";
        InputDialog.show(context, content, onDialogClick);
    }

    /**
     *   删除一个设备
     */

    public static void delPetiyaak(Context context, OnDialogClick onDialogClick) {
        DialogContent content = new DialogContent();
        content.desc = "Are you sure want to delete this Petiyaakbox?";
        InputDialog.show(context, content, onDialogClick);
    }

    /**
     *   添加一个用户
     */

    public static void addUser(Context context, OnDialogClick onDialogClick) {
        DialogContent content = new DialogContent();
        content.desc = "Add User";
        content.ok = "save";
        content.cancle = "cancle";
        AddUserDialog.show(context, content, onDialogClick);
    }


    /**
     *   添加一个用户
     */

    public static void delUser(Context context, OnDialogClick onDialogClick) {
        DialogContent content = new DialogContent();
        content.desc = "Delete User";
        content.ok = "Ok";
        content.cancle = "No";
        content.hideEidt = true;
        AddUserDialog.show(context, content, onDialogClick);
    }
}
