package com.henley.permissionhelper;

import java.util.Collection;

/**
 * 权限请求结果监听
 *
 * @author Henley
 * @date 2017/7/31 14:33
 */
interface OnRequestResultListener {

    /**
     * 当权限请求结果返回时调用该方法
     *
     * @param grantedPermissions 被授予的权限
     * @param deniedPermissions  被拒绝的权限
     */
    void onRequestResultListener(Collection<String> grantedPermissions, Collection<String> deniedPermissions);

}
