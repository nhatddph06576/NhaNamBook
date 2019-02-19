package nhatdd.nhanambook.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import nhatdd.nhanambook.R;
import nhatdd.nhanambook.model.User;
import nhatdd.nhanambook.sqlitedao.UserDAO;


public class LoginActivity extends AppCompatActivity {

    private EditText edtUname;
    private EditText edtPassw;
    private CheckBox chkBox;

    public String strUser, strPass;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intView();
        restore();
    }

    private void intView() {
        setContentView(R.layout.activity_login);
        edtUname = findViewById(R.id.edtUname);
        edtPassw = findViewById(R.id.edtPw);
        chkBox = (CheckBox) findViewById(R.id.chkBox);
        userDAO = new UserDAO(LoginActivity.this);

    }


    public void Login(View view) {
        strUser = edtUname.getText().toString().trim();
        strPass = edtPassw.getText().toString().trim();
        User us = userDAO.getUser(strUser);
        boolean check = chkBox.isChecked();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không được bỏ trống",
                    Toast.LENGTH_SHORT).show();
        } else {
            String oPass = us.getPassword();
            String o = us.getUsername();
            if(strUser.equalsIgnoreCase("admin") && strPass.equalsIgnoreCase("admin")) {
                rememberUser(strUser, strPass,check);
                finish();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            } else if (o.equals(strUser) && oPass.equals(strPass)) {
                rememberUser(strUser, strPass, check);
                finish();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không đúng",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            //xoa tinh trang luu tru truoc do
            edit.clear();
        } else {
            //luu du lieu
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        //luu lai toan bo
        edit.commit();
    }
    public void restore() {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        boolean check = pref.getBoolean("REMEMBER",false);
        if (check) {
            String user = pref.getString("USERNAME","");
            String pass = pref.getString("PASSWORD","");
            edtUname.setText(user);
            edtPassw.setText(pass);

        }
        chkBox.setChecked(check);
    }

    public void Signup(View view) {
        startActivity(new Intent(LoginActivity.this, AddUserActivity.class));
    }
}

