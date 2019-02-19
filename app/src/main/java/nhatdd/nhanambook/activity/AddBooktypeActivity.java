package nhatdd.nhanambook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import nhatdd.nhanambook.R;
import nhatdd.nhanambook.model.TypeBook;
import nhatdd.nhanambook.sqlitedao.TypeBookDAO;

public class AddBooktypeActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    EditText edtIDType;
    EditText edtNameType;
    EditText edtLocationType;
    EditText edtInfoTypebook;
    TypeBookDAO typeBookDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booktype);
        edtIDType = (EditText) findViewById(R.id.edtIDType);
        edtNameType = (EditText) findViewById(R.id.edtNameType);
        edtLocationType = (EditText) findViewById(R.id.edtLocationType);
        edtInfoTypebook = (EditText) findViewById(R.id.edtInfoTypebook);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addtypebook(View view) {
        if (edtIDType.getText().length() == 0 ||
                edtNameType.getText().length() == 0 ||
                edtLocationType.getText().length() == 0 ||
                edtInfoTypebook.getText().length() == 0) {
            Toast.makeText(this, "Bạn phải nhập đủ thông tin ", Toast.LENGTH_SHORT).show();

        } else {
            typeBookDAO = new TypeBookDAO(this);
            TypeBook typeBook = new TypeBook(edtIDType.getText().toString(),
                    edtNameType.getText().toString(),
                    Integer.parseInt(edtLocationType.getText().toString()), edtInfoTypebook.getText().toString());
            try {
                if (validateForm() > 0) {
                    if (typeBookDAO.insertTypeBook(typeBook) > 0) {
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

    }

    public int validateForm() {
        int check = 1;
        if (edtIDType.getText().length() == 0 ||
                edtNameType.getText().length() == 0 ||
                edtLocationType.getText().length() == 0 ||
                edtLocationType.getText().length() == 0) {
            Toast.makeText(this, "Bạn phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}
