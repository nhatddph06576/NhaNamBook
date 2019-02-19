package nhatdd.nhanambook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import nhatdd.nhanambook.R;
import nhatdd.nhanambook.sqlitedao.TypeBookDAO;


public class EditBookTypeActivity extends AppCompatActivity {
    Toolbar tbTypeBook;
    EditText edtIDType;
    EditText edtNameType;
    EditText edtLocationType;
    EditText edtInfoTypebook;
    String id, name, location, info;
    TypeBookDAO typeBookDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booktype);

        tbTypeBook = findViewById(R.id.toolbar);
        edtIDType = (EditText) findViewById(R.id.edtIDType);
        edtNameType = (EditText) findViewById(R.id.edtNameType);
        edtLocationType = (EditText) findViewById(R.id.edtLocationType);
        edtInfoTypebook = (EditText) findViewById(R.id.edtInfoTypebook);
        typeBookDAO = new TypeBookDAO(this);


        setSupportActionBar(tbTypeBook);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tbTypeBook.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        tbTypeBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        id = bundle.getString("ID");
        name = bundle.getString("NAME");
        location = bundle.getString("LOCATION");
        info = bundle.getString("NOTI");

        edtNameType.setText(name);
        edtLocationType.setText(location);
        edtInfoTypebook.setText(info);


    }

    public void EditTypebook(View view) {
        if (typeBookDAO.updateTypeBook(id, edtNameType.getText().toString(), edtLocationType.getText().toString(), edtInfoTypebook.getText().toString()) > 0) {
            Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
