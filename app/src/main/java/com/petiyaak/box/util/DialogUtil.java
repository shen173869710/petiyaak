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
        content.desc = "add petiyaak box";
        content.ok = "save";
        InputDialog.show(context, content, onDialogClick);
    }

    /**
     *   删除一个设备
     */

    public static void delPetiyaak(Context context, OnDialogClick onDialogClick) {
        DialogContent content = new DialogContent();
        content.desc = "Are you sure want to delete this Petiyaakbox?";
        content.hideEidt = true;
        content.ok = "Ok";
        content.cancle = "No";
        AddUserDialog.show(context, content, onDialogClick);
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


    /**
     *   删除一个指纹
     */

    public static void delFinger(Context context, int  id, OnDialogClick onDialogClick) {
        DialogContent content = new DialogContent();
        content.desc = "Delete finger" + id;
        content.ok = "Ok";
        content.cancle = "No";
        content.hideEidt = true;
        AddUserDialog.show(context, content, onDialogClick);
    }

    /**
     *   添加一个用户
     */

    public static void shareToUser(Context context, String name ,OnDialogClick onDialogClick) {
        DialogContent content = new DialogContent();
        content.desc = "share to User " + name;
        content.ok = "share";
        content.cancle = "cancle";
        content.hideEidt = true;
        AddUserDialog.show(context, content, onDialogClick);
    }

    /**
     *   添加一个用户
     */

    public static void cancleToUser(Context context, String name ,OnDialogClick onDialogClick) {
        DialogContent content = new DialogContent();
        content.desc = "cancle share to User " + name;
        content.ok = "YES";
        content.cancle = "NO";
        content.hideEidt = true;
        AddUserDialog.show(context, content, onDialogClick);
    }
}
