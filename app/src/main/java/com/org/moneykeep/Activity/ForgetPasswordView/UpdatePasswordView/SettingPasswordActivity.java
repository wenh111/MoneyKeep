package com.org.moneykeep.Activity.ForgetPasswordView.UpdatePasswordView;

import android.os.Build;
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

import androidx.appcompat.app.AppCompatActivity;

import com.org.moneykeep.Activity.ForgetPasswordView.AuthenticationView.ForgetPasswordActivity;
import com.org.moneykeep.BmobTable.User;
import com.org.moneykeep.Excention.error;
import com.org.moneykeep.Excention.error_email;
import com.org.moneykeep.R;


import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class SettingPasswordActivity extends AppCompatActivity implements SettingPasswordInterface.IView{

    private ImageView eye_photo,eye_photo2;
    private EditText new_password,new_password2,email;
    private boolean isHideFirst = true;
    private boolean isHideFirst2 = true;
    private Button summit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        Findid();
        eye_photo.setImageResource(R.drawable.close_eye);
        eye_photo2.setImageResource(R.drawable.close_eye);
        SetListen();
    }

    private void SetListen() {
        Onclick onclick =new Onclick();
        eye_photo.setOnClickListener(onclick);
        eye_photo2.setOnClickListener(onclick);
        summit.setOnClickListener(onclick);

    }

    private void Findid() {
        new_password = findViewById(R.id.new_password);
        new_password2 = findViewById(R.id.new_password2);
        email = findViewById(R.id.email);

        summit = findViewById(R.id.forget_summit);
        //小眼睛
        eye_photo = findViewById(R.id.eye_photo);
        eye_photo2 = findViewById(R.id.eye_photo2);
    }
    private void ForgetSummit(){
        SettingPasswordInterface.IPresenter iPresenter = new SettingPasswordPresenterImplements(this);
        String input_password = new_password.getText().toString();
        String input_password2 = new_password2.getText().toString();
        String input_email = email.getText().toString();
        iPresenter.UpdateUserPassword(input_password,input_password2,input_email);
    }

    @Override
    public void UpdateUserPasswordSuccessful(String successful) {
        Toast.makeText(this, successful, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void UpdateUserPasswordUnSuccessful(String message, int id) {
        if(id != -1){
            EditText editText = findViewById(id);
            editText.requestFocus();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    public class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.eye_photo:
                    if (isHideFirst == true) {
                        eye_photo.setImageResource(R.drawable.open_eye);
                        HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                        new_password.setTransformationMethod(method1);
                        isHideFirst = false;
                    } else {
                        eye_photo.setImageResource(R.drawable.close_eye);
                        TransformationMethod method = PasswordTransformationMethod.getInstance();
                        new_password.setTransformationMethod(method);
                        isHideFirst = true;
                    }
                    int index = new_password.getText().toString().length();
                    new_password.setSelection(index);
                    break;
                case R.id.eye_photo2:
                    if (isHideFirst2 == true) {
                        eye_photo2.setImageResource(R.drawable.open_eye);
                        HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                        new_password2.setTransformationMethod(method1);
                        isHideFirst2 = false;
                    } else {
                        eye_photo2.setImageResource(R.drawable.close_eye);
                        TransformationMethod method = PasswordTransformationMethod.getInstance();
                        new_password2.setTransformationMethod(method);
                        isHideFirst2 = true;
                    }
                    int index2 = new_password2.getText().toString().length();
                    new_password2.setSelection(index2);
                    break;
                case R.id.forget_summit:
                    /*try{
                        final String[] obid = {null};
                        String input_password = new_password.getText().toString();
                        String input_password2 = new_password2.getText().toString();
                        String input_email = email.getText().toString();
                        if(input_password.equals(input_password2)){
                            if(ForgetPasswordActivity.email.equals(input_email)){
                                BmobQuery<User> findid = new BmobQuery<>();
                                findid.addWhereEqualTo("account",input_email);
                                findid.findObjects(new FindListener<User>() {
                                    @Override
                                    public void done(List<User> list, BmobException e) {
                                        for(User oid : list){
                                            obid[0] = oid.getObjectId();
                                        }
                                        User u1 = new User();
                                        u1.setPassword(input_password);
                                        u1.update(obid[0],new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(v.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(v.getContext(), "修改失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            }else{
                                throw new error_email();
                            }
                        }else{
                            throw new error("两次密码不一样");
                        }
                    } catch (error error) {
                        Toast.makeText(v.getContext(), "两次密码不一样", Toast.LENGTH_SHORT).show();
                    } catch (error_email error_email) {
                        Toast.makeText(v.getContext(), "两次邮箱不一样", Toast.LENGTH_SHORT).show();
                    }*/
                    ForgetSummit();
                    break;
            }
        }
    }
}