package nhatdd.nhanambook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import nhatdd.nhanambook.R;
import nhatdd.nhanambook.sqlitedao.UserDAO;

public class EditInfoUserActivity extends AppCompatActivity {

    private Toolbar toolbar;


    EditText edFullName, edPhone;
    UserDAO userDAO;
    String username, fullname, phone;


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

        Intent in = getIntent();
        Bundle b = in.getExtras();
        fullname = b.getString("FULLNAME");
        phone = b.getString("PHONE");
        username = b.getString("USERNAME");
        edFullName.setText(fullname);
        edPhone.setText(phone);


    }

    private void initView() {
        setContentView(R.layout.activity_edit_info);
        setSupportActionBar(toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edPhone = (EditText) findViewById(R.id.edtPhone);
        edFullName = (EditText) findViewById(R.id.edtFullName);
        userDAO = new UserDAO(this);


    }


    public void btEdit(View view) {
        if (userDAO.updateInfoUser(username, edPhone.getText().toString(), edFullName.getText().toString()) > 0) {
            Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
        }
    }


}
