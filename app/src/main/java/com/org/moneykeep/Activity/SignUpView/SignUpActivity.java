package com.org.moneykeep.Activity.SignUpView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.org.moneykeep.R;

public class SignUpActivity extends AppCompatActivity implements SignUpInterface.IView {
    private EditText sign_up_account, sign_up_password, sign_up_telephone_number, sign_up_name;
    private ImageView sign_up_eye_photo;
    private Button summit, sign_up_back;
    private boolean isHideFirst = true;
    private String user_password, user_account, user_telephone_number, user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        FindId();
        sign_up_eye_photo.setImageResource(R.drawable.close_eye);
        SetListen();
    }

    private void SetListen() {
        Onclick onclick = new Onclick();
        summit.setOnClickListener(onclick);
        sign_up_back.setOnClickListener(onclick);
        sign_up_eye_photo.setOnClickListener(onclick);
    }

    @Override
    public void UserSignUpSuccessful(String successfulMessage) {
        Toast.makeText(this, successfulMessage, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void UserSignUpUnSuccessful(String unsuccessfulMessage, int id) {
        if(id != 0){
            EditText editText = findViewById(id);
            editText.requestFocus();
        }
        Toast.makeText(this, unsuccessfulMessage, Toast.LENGTH_SHORT).show();
    }

    private class Onclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sign_up_back:
                    finish();
                    break;
                case R.id.sign_up_eye_photo:
                    if (isHideFirst == true) {
                        sign_up_eye_photo.setImageResource(R.drawable.open_eye);
                        HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                        sign_up_password.setTransformationMethod(method1);
                        isHideFirst = false;
                    } else {
                        sign_up_eye_photo.setImageResource(R.drawable.close_eye);
                        TransformationMethod method = PasswordTransformationMethod.getInstance();
                        sign_up_password.setTransformationMethod(method);
                        isHideFirst = true;
                    }
                    int index = sign_up_password.getText().toString().length();
                    sign_up_password.setSelection(index);
                    break;
                case R.id.sign_up_summit:
                    SignUpSummit();
                    break;

            }
        }
    }

    //user_password,user_account,user_telephone_number,user_name;
    /*private void SignUpSummit() {
        try {
            user_name = sign_up_name.getText().toString();
            user_account = sign_up_account.getText().toString();
            user_password = sign_up_password.getText().toString();
            user_telephone_number = sign_up_telephone_number.getText().toString();
            if (user_name.equals("") || user_account.equals("")
                    || user_password.equals("") || user_telephone_number.equals("")) {
                if (user_name.equals("")) {
                    sign_up_name.requestFocus();
                    throw new Nullexcetion();
                }
                if (user_account.equals("")) {
                    sign_up_account.requestFocus();
                    throw new Nullexcetion();
                }
                if (user_password.equals("")) {
                    sign_up_password.requestFocus();
                    throw new Nullexcetion();
                }
                if (user_telephone_number.equals("")) {
                    sign_up_telephone_number.requestFocus();
                    throw new Nullexcetion();
                }
            }else{
                User u1 = new User();
                u1.setName(user_name);
                u1.setAccount(user_account);
                u1.setPassword(user_password);
                u1.setTelephone_number(user_telephone_number);
                u1.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(SignUpActivity.this, "注册成功 ", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "注册失败 " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        } catch (Nullexcetion e) {
            Toast.makeText(SignUpActivity.this, "请完善资料", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void SignUpSummit() {
        SignUpInterface.IPresenter iPresenter = new SignUpPresenterImplements(this,this);
        user_name = sign_up_name.getText().toString();
        user_account = sign_up_account.getText().toString();
        user_password = sign_up_password.getText().toString();
        user_telephone_number = sign_up_telephone_number.getText().toString();
        iPresenter.UserSignUp(user_name,user_account,user_password,user_telephone_number);

    }

    private void FindId() {
        //按钮
        summit = findViewById(R.id.sign_up_summit);
        sign_up_back = findViewById(R.id.sign_up_back);

        //小眼睛
        sign_up_eye_photo = findViewById(R.id.sign_up_eye_photo);

        //输入框
        sign_up_name = findViewById(R.id.sign_up_name);
        sign_up_account = findViewById(R.id.sign_up_account);
        sign_up_password = findViewById(R.id.sign_up_password);
        sign_up_telephone_number = findViewById(R.id.sign_up_telephone_number);

    }
}