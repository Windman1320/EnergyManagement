package com.tony.energymanagement.em;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tony.energymanagement.energymanagement.R;

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
               Log.i("LoginActivity:","onClick執行了");
               Log.i("LoginActivity:","username->"+ed_username.getText());
               Log.i("LoginActivity:","username->"+ed_passwd.getText());


//                if("".equals(ed_username.getText())||ed_username.getText()==null) {
//                    Toast.makeText(LoginActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
//                    if("".equals(ed_passwd.getText())||ed_passwd.getText()==null){
//                        Toast.makeText(LoginActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
//                    }else {
//
//                    }
//                }
                //验证用户名和密码是否正确


                //跳转到下一个activity
                Intent intent = new Intent(LoginActivity.this,Main.class);
                startActivity(intent);
            }
        });
    }
}
