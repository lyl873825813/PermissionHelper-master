package com.henley.permissionhelper.demo;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.henley.permissionhelper.OnPermissionsCallback;
import com.henley.permissionhelper.Permission;
import com.henley.permissionhelper.PermissionHelper;
import com.henley.permissionhelper.Rationale;

import java.util.List;

/**
 * @author Henley
 * @date 2017/7/31 15:48
 */
public class SimplePermissionsCallback implements OnPermissionsCallback {

    private Context context;
    private Toast toast;

    public SimplePermissionsCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<Permission> permissions, int flag) {
        showToast("所有权限都已授予");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<Permission> permissions) {
        showToast("被拒绝的权限：" + permissions.toString());
        new AlertDialog.Builder(context)
                .setTitle("温馨提示")
                .setMessage("当前应用缺少必要权限，为了您能正常使用所有功能，请点击“设置”-“权限”-打开所需权限")
                .setNegativeButton("取消", null)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionHelper.startPackageSettings(context);
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onShowRequestPermissionRationale(int requestCode, List<Permission> permissions, final Rationale rationale) {
        showToast("被拒绝的权限：" + permissions.toString());
        new AlertDialog.Builder(context)
                .setTitle("温馨提示")
                .setMessage("为了您能正常使用所有功能，需要如下权限：" + getDesc(permissions))
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rationale.cancel();
                    }
                })
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rationale.resume();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onPermissionsRequestCancel(int requestCode, List<Permission> permissions) {
        showToast("权限请求被取消");
    }

    private String getDesc(List<Permission> permissions) {
        int size = permissions.size();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(PermissionConfig.getDesc(permissions.get(i).name));
            if (i < size - 1) {
                builder.append("、");
            }
        }
        return builder.toString();
    }

    private void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
