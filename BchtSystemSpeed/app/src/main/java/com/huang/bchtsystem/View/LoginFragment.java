package com.huang.bchtsystem.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.MyPreference;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.View.Activity.BridgeActivity;
import com.huang.bchtsystem.View.Activity.MainActivity;
import com.huang.bchtsystem.View.Fragment.StorageFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/3/14.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{

    @InjectView(R.id.layout_login)
    LinearLayout layout_login;
    @InjectView(R.id.login_account)
    EditText login_account;
    @InjectView(R.id.login_password)
    EditText login_password;
    @InjectView(R.id.login_sure)
    Button login_sure;
    @InjectView(R.id.login_cancel)
    Button login_cancel;

    private Dialog progressDialog;
    private static String msg = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Activity activity = getActivity();
        ActionBar actionBar = ((BridgeActivity) activity).getSupportActionBar();
        actionBar.hide();
        View view = View.inflate(getActivity(), R.layout.login_activity, null);
        ButterKnife.inject(this, view);

        login_sure.setOnClickListener(this);
        login_cancel.setOnClickListener(this);
        layout_login.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.layout_login:
                InputMethodManager imm=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            case R.id.login_sure:
                progressDialog = Util.createLoadingDialog(getActivity(), "登录中");
                progressDialog.show();
                if (TextUtils.isEmpty(login_account.getText().toString()) && TextUtils.isEmpty(login_password.getText().toString())) {
                    msg = "账号和密码不能为空";
                    showStorage_dialog();
                    progressDialog.dismiss();
                    return;
                } else {
                    if (login_account.getText().toString().equals("admin") && login_password.getText().toString().equals("admin12345") ){
                        MyPreference.getInstance(getContext())
                                .SetLoginName(login_account.getText().toString().trim());
                        MyPreference.getInstance(getContext())
                                .SetPassword(login_password.getText().toString().trim());
                        //TODO====登录到主界面MainActivity
                        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                        progressDialog.dismiss();
                        getActivity().finish();
                    }else {
                        msg = "账号或密码错误";
                        showStorage_dialog();
                        progressDialog.dismiss();
                    }
                }
                break;
            case R.id.login_cancel:
                login_account.setText("");
                login_password.setText("");
                break;
            default:
                break;
        }
    }
    private void showStorage_dialog()
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 弹出的对话框
        new AlertDialog.Builder(getActivity())
					/* 弹出窗口的最上头文字 */
                .setTitle("提示")
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setMessage(msg)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                               dialoginterface.dismiss();
                            }
                        })
               .show();
    }
    //用户记住账号和密码
    @Override
    public void onResume()
    {
        super.onResume();
        getContext();
        login_account.setText(MyPreference.getInstance(getContext())
                .getLoginName());
        login_password
                .setText(MyPreference.getInstance(getContext()).getPassword());
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getActivity().finish();
    }
}
