package com.org.moneykeep.Activity.SignInView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.org.moneykeep.Activity.ForgetPasswordView.AuthenticationView.ForgetPasswordActivity;
import com.org.moneykeep.Activity.SignUpView.SignUpActivity;
import com.org.moneykeep.Activity.UserMainActivity;
import com.org.moneykeep.R;

public class SignInActivity extends AppCompatActivity implements SignInInterface.IView{

    private ImageView iv;
    private boolean isHideFirst = true;
    private Button sign_up, sign_in;
    private EditText editText_email, editText_password;
    private TextView fp_tv;
    private SignInInterface.IPresenter signInPresenterImplements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        signInPresenterImplements = new SignInPresenterImplements(this,getApplicationContext());


        FindId();
        iv.setImageResource(R.drawable.close_eye);
        SetListen();

    }

    private void SetListen() {
        Onclick onclick = new Onclick();
        sign_in.setOnClickListener(onclick);
        sign_up.setOnClickListener(onclick);
        fp_tv.setOnClickListener(onclick);
        iv.setOnClickListener(onclick);
    }

    @Override
    public void Successful(String successful,Bundle bundle) {
        Toast.makeText(this,successful,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignInActivity.this, UserMainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void UnSuccessful(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }

    private class Onclick implements View.OnClickListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.sign_in_eye_photo:
                    if (isHideFirst) {
                        iv.setImageResource(R.drawable.open_eye);
                        HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                        editText_password.setTransformationMethod(method1);
                        isHideFirst = false;
                    } else {
                        iv.setImageResource(R.drawable.close_eye);
                        TransformationMethod method = PasswordTransformationMethod.getInstance();
                        editText_password.setTransformationMethod(method);
                        isHideFirst = true;
                    }
                    int index = editText_password.getText().toString().length();
                    editText_password.setSelection(index);
                    break;
                case R.id.sign_in:
                    //UserSignIn();
                    String account = editText_email.getText().toString();
                    String password = editText_password.getText().toString();
                    String sql = "select * from User where account = ? and password = ?";
                    signInPresenterImplements.UserSignIn(account, password,sql);
                    break;
                case R.id.sign_up:
                    intent = new Intent(SignInActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fp_tv:
                    intent = new Intent(SignInActivity.this, ForgetPasswordActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }



    private void FindId() {
        iv = findViewById(R.id.sign_in_eye_photo);
        sign_up = findViewById(R.id.sign_up);
        sign_in = findViewById(R.id.sign_in);
        editText_email = findViewById(R.id.sign_in_account);
        editText_password = findViewById(R.id.sign_in_passward);
        fp_tv = findViewById(R.id.fp_tv);
    }
}