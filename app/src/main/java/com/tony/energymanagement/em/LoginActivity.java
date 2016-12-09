package com.tony.energymanagement.em;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tony.energymanagement.energymanagement.R;

import org.zaiqingyang.energymanagement.utils.LoginVerify;

public class LoginActivity extends AppCompatActivity {
    private EditText ed_username;
    private EditText ed_passwd;
    private Button btn_login;
    private String username = "";
    private String password = "";
    private Boolean login_status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化
        initView();
    }

    private void initView() {
        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_passwd = (EditText) findViewById(R.id.ed_passwd);
        btn_login = (Button) findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LoginActivity:", "onClick執行了");
                Log.i("LoginActivity:", "username->" + ed_username.getText());
                Log.i("LoginActivity:", "username->" + ed_passwd.getText());
                username = ed_username.getText().toString();
                password = ed_passwd.getText().toString();
                new Thread(runnable).start();

                if ("".equals(ed_username.getText().toString()) || ed_username.getText() == null) {
                    //弹出对话框提示用户
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("请输入用户名！")
                            .setPositiveButton("OK", null)
                            .show();

                } else if ("".equals(ed_passwd.getText().toString()) || ed_passwd.getText() == null) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("密码不能为空！")
                            .setPositiveButton("OK", null)
                            .show();
                } else if (login_status) {
                    //跳转到下一个activity
                    Intent intent = new Intent(LoginActivity.this, Main.class);
                    startActivity(intent);

                }

            }
        });
    }

    //handler处理返回的请求结果
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            login_status = data.getBoolean("status");
        }
    };
    //开启新线程请求网络
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            boolean status = LoginVerify.login(username, password);
            Log.d("In runnable",String.valueOf(status));
            Message msg = Message.obtain();
            Bundle data = new Bundle();
            data.putBoolean("status", status);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };
}
