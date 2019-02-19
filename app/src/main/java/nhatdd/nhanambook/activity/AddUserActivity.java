package nhatdd.nhanambook.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.model.User;
import nhatdd.nhanambook.sqlitedao.UserDAO;

public class AddUserActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtSGUname;
    EditText edtSGPassw;
    EditText edtSgCPassw;
    EditText edtSgPhone;
    EditText edtSgFullName;
    UserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();


    }

    public void initView() {
        setContentView(R.layout.activity_add_user);
        setSupportActionBar(toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edtSGUname = (EditText) findViewById(R.id.edtSGUname);
        edtSGPassw = (EditText) findViewById(R.id.edtSGPassw);
        edtSgCPassw = (EditText) findViewById(R.id.edtSgCPassw);
        edtSgPhone = (EditText) findViewById(R.id.edtSgPhone);
        edtSgFullName = (EditText) findViewById(R.id.edtSgFullName);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    public void addUser(View view) {
        userDAO = new UserDAO(this);
        User user = new User(edtSGUname.getText().toString(),
                edtSGPassw.getText().toString(),
                edtSgPhone.getText().toString(), edtSgFullName.getText().toString());
        try {
            if (validateForm() > 0) {
                if (userDAO.insertUser(user) > 0) {
                    Toast.makeText(getApplicationContext(), "Thêm thành công",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm thất bại",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }

    }

    public int validateForm() {
        int check = 1;
        if (edtSGUname.getText().length() == 0 ||
                edtSgFullName.getText().length() == 0 ||
                edtSgPhone.getText().length() == 0 ||
                edtSGPassw.getText().length() == 0 ||
                edtSgCPassw.getText().length() == 0) {
            Toast.makeText(this, "Bạn phải nhập đủ thông tin ", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edtSGPassw.getText().toString();
            String repass = edtSgCPassw.getText().toString();
            if (!pass.equals(repass)) {
                Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}
