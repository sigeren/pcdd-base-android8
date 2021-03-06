package com.df.youxi.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.df.youxi.network.ApiInterface;
import com.df.youxi.network.bean.UserInfo;
import com.df.youxi.R;
import com.df.youxi.manager.UserInfoManager;
import com.df.youxi.network.HttpResultCallback;
import com.df.youxi.network.MySubcriber;
import com.df.youxi.network.request.EditUserInfoRequest;
import com.df.youxi.ui.base.BaseTopActivity;
import com.df.youxi.util.MD5Utils;
import com.df.youxi.util.T;

/**
 * Created by hang on 2017/2/28.
 * 修改登录密码
 */

public class EditLoginPwdActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edOldPwd;
    private EditText edNewPwd1;
    private EditText edNewPwd2;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_login_pwd);
        init();
    }

    private void init() {
        initTopBar("登录密码");
        edOldPwd = getView(R.id.edOldPwd);
        edNewPwd1 = getView(R.id.edNewPwd1);
        edNewPwd2 = getView(R.id.edNewPwd2);

        getView(R.id.btnOK).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnOK:
                if(TextUtils.isEmpty(edOldPwd.getText().toString())) {
                    T.showShort("请输入原密码");
                    return;
                }
                if(TextUtils.isEmpty(edNewPwd1.getText().toString()) || edNewPwd1.getText().toString().length()<6) {
                    T.showShort("请输入6~12位新密码");
                    return;
                }
                if(!edNewPwd1.getText().toString().equals(edNewPwd2.getText().toString())) {
                    T.showShort("确认密码不一致");
                    return;
                }
                submit();
                break;
        }
    }

    public void submit() {
        final EditUserInfoRequest req = new EditUserInfoRequest();
        String oldPwd = edOldPwd.getText().toString();
        for(int i=0; i<3; i++)
            oldPwd = MD5Utils.getMD5String(oldPwd);
        req.old_password = oldPwd;
        req.password = edNewPwd1.getText().toString();
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("设置成功");
                UserInfoManager.setUserPwd(EditLoginPwdActivity.this, req.password);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "设置中");
        ApiInterface.editUserInfo(req, s);
    }
}
