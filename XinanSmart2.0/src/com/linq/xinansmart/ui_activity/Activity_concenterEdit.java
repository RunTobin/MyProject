package com.linq.xinansmart.ui_activity;

import com.linq.xinansmart.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_concenterEdit extends Activity implements OnClickListener {
	private EditText edit_name = null;
	private EditText edit_user= null;
	private EditText edit_password = null;
	private Button btn_login = null;
	private Button btn_cancel = null;
	private String name = null;
	private String user = null;
	private String password = null;
	private Intent intent;
	private int pos=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		intent = getIntent();
	    getData();
		findView();
	}

	private void getData() {
		
		name=intent.getExtras().getString("name");
		user=intent.getExtras().getString("user");
		password=intent.getExtras().getString("password");
		pos=intent.getIntExtra("pos",-1);
		
		
		
	}

	private void findView() {
		edit_name = (EditText) findViewById(R.id.edit_name);
		edit_user = (EditText) findViewById(R.id.edit_user);
		edit_password = (EditText) findViewById(R.id.edit_password);
		edit_name.setText(name);
		edit_user.setText(user);
		edit_password.setText(password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_login.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		user = edit_user.getText().toString();
		password = edit_password.getText().toString();
		name = edit_name.getText().toString();
		switch (v.getId()) {
		case R.id.btn_login:
			if ("".equalsIgnoreCase(name)) {
				Log.e("username", name + "11");
				Toast.makeText(Activity_concenterEdit.this, "名称不能为空！",
						Toast.LENGTH_SHORT).show();

			} else if ("".equalsIgnoreCase(user)) {
				Toast.makeText(Activity_concenterEdit.this, "用户名不能为空！",
						Toast.LENGTH_SHORT).show();
			} else if ("".equalsIgnoreCase(password)) {
				Toast.makeText(Activity_concenterEdit.this, "密码不能为空！",
						Toast.LENGTH_SHORT).show();
			}
			else {
				intent.putExtra("name", name);
				intent.putExtra("user", user);
				intent.putExtra("password", password);
				intent.putExtra("pos", pos);
				setResult(4, intent);
				finish();
			}

			break;

		case R.id.btn_cancel:

			finish();
			break;

		}

	}
}
