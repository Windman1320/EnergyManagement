package com.tony.energymanagement.em;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tony.energymanagement.energymanagement.R;

import org.zaiqingyang.energymanagement.utils.LoginVerify;

public class LoginActivity extends AppCompatActivity {
    private EditText ed_username;
    private EditText ed_passwd;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_passwd = (EditText) findViewById(R.id.ed_passwd);
        btn_login = (Button)findViewById(R.id.btn_login);



        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = ed_username.getText().toString();
                String password = ed_passwd.getText().toString();
               Log.i("LoginActivity:","onClick執行了");
               Log.i("LoginActivity:","username->"+ed_username.getText());
               Log.i("LoginActivity:","username->"+ed_passwd.getText());
                /**
                 *
                 */
                class LoginRunnable implements Runnable{
                    private String username;
                    private String password;
                    public LoginRunnable(String username,String password){
                        this.username = username;
                        this.password = password;
                    }
                    @Override
                    public void run() {
                        if(LoginVerify.login(username,password)){
                            //跳转到下一个activity
                            Intent intent = new Intent(LoginActivity.this,Main.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,"用户名或者密码错误!",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                if(username==null||"".equals(username)) {
                    Log.d("test",username);
                    //弹出对话框提示用户
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("请输入用户名！")
                            .setPositiveButton("OK", null)
                            .show();

                }else if(password==null||"".equals(password)){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("密码不能为空！")
                            .setPositiveButton("OK", null)
                            .show();
                }else {

                    new Thread(new LoginRunnable(username,password)).start();

                }
            }
        });
    }
}
