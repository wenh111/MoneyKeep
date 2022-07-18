package com.org.moneykeep.Activity.ForgetPasswordView.AuthenticationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.org.moneykeep.Activity.ForgetPasswordView.UpdatePasswordView.SettingPasswordActivity;
import com.org.moneykeep.R;

public class ForgetPasswordActivity extends AppCompatActivity implements AuthenticationInterface.IView{
    public static String email, telephone_number;
    private EditText forget_Email, forget_telephone_number;
    private Button forget_summit, forget_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        FindId();

        SetListen();
    }

    private void SetListen() {
        Onclick onclick = new Onclick();
        forget_summit.setOnClickListener(onclick);
        forget_back.setOnClickListener(onclick);
    }

    @Override
    public void AuthenticationSuccessful(String successful) {
        Intent intent = new Intent(ForgetPasswordActivity.this, SettingPasswordActivity.class);
        startActivity(intent);
        Toast.makeText(ForgetPasswordActivity.this, successful, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void AuthenticationUnSuccessful(String unsuccessful) {
        Toast.makeText(ForgetPasswordActivity.this, unsuccessful, Toast.LENGTH_SHORT).show();
    }

    private class Onclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.forget_back:
                    finish();
                    break;
                case R.id.forget_summit:
                    ForgetPassword();
                    break;
            }
        }
    }

    /*private void ForgetPassword() {
        email = String.valueOf(forget_Email.getText());
        telephone_number = String.valueOf(forget_telephone_number.getText());
        String sql = "select * from User where account = ? and telephone_number = ?";
        new BmobQuery<User>().doSQLQuery(sql, new SQLQueryListener<User>() {
            @Override
            public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                if(e == null && bmobQueryResult != null){
                    Intent intent = new Intent(ForgetPasswordActivity.this, SettingPasswordActivity.class);
                    startActivity(intent);
                    Toast.makeText(ForgetPasswordActivity.this, "验证成功...", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    if(bmobQueryResult == null){
                        Toast.makeText(ForgetPasswordActivity.this, "邮箱或电话号码不正确", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ForgetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        },email,telephone_number);

    }*/
    private void ForgetPassword() {
        email = String.valueOf(forget_Email.getText());
        telephone_number = String.valueOf(forget_telephone_number.getText());
        AuthenticationPresenterImplements authenticationPresenterImplements = new AuthenticationPresenterImplements(this,this);
        authenticationPresenterImplements.Authentication(email,telephone_number);


    }
    private void FindId() {
        //按钮
        forget_summit = findViewById(R.id.forget_summit);
        forget_back = findViewById(R.id.forget_back);
        //输入框
        forget_Email = findViewById(R.id.forget_Email);
        forget_telephone_number = findViewById(R.id.forget_telephone_number);
    }
}