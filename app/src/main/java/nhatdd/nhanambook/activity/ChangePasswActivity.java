package nhatdd.nhanambook.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.model.User;
import nhatdd.nhanambook.sqlitedao.UserDAO;

public class ChangePasswActivity extends AppCompatActivity {

    private Toolbar toolbar;


    private EditText edtSGPassw;
    private EditText edtSgCPassw;
    private Button btSignUp;
    UserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
                String strUserName = pref.getString("USERNAME","");
                userDAO = new UserDAO(ChangePasswActivity.this);
                User user = new User(strUserName, edtSGPassw.getText().toString(), "",
                        "");
                try {
                    if (validateForm()>0){
                        if (userDAO.changePasswordUser(user) > 0) {
                                Toast.makeText(getApplicationContext(), "Lưu thành công",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Lưu thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_change_passw);
        setSupportActionBar(toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        edtSGPassw = (EditText) findViewById(R.id.edtSGPassw);
        edtSgCPassw = (EditText) findViewById(R.id.edtSgCPassw);
        btSignUp = (Button) findViewById(R.id.btSignUp);
    }

    public int validateForm() {
        int check = 1;
        if (edtSGPassw.getText().length() == 0 || edtSgCPassw.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Bạn phải nhập đầy đủ thông ",
                    Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edtSGPassw.getText().toString();
            String rePass = edtSgCPassw.getText().toString();
            if (!pass.equals(rePass)) {
                Toast.makeText(getApplicationContext(), "Mật khẩu không trùng khớp",
                        Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }


}
