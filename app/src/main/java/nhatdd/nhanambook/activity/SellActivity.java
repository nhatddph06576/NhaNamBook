package nhatdd.nhanambook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.adapter.BookAdapter;
import nhatdd.nhanambook.model.Book;
import nhatdd.nhanambook.sqlitedao.BookDAO;

public class SellActivity extends AppCompatActivity {
    Toolbar tbSell;
    public static List<Book> dsSach = new ArrayList<>();

    BookAdapter adapter = null;
    BookDAO sachDAO;
    EditText edThang;
    ListView lvSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        tbSell = findViewById(R.id.toolbar);
        lvSell = findViewById(R.id.lvSell);
        edThang = findViewById(R.id.edttop10);

        setSupportActionBar(tbSell);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        tbSell.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        tbSell.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }


    public void view(View view) {
        if (Integer.parseInt(edThang.getText().toString()) > 13 ||
                Integer.parseInt(edThang.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(), "Không đúng định dạng tháng (1- 12)", Toast.LENGTH_SHORT).show();
        } else {
            sachDAO = new BookDAO(SellActivity.this);
            dsSach = sachDAO.getBookTop10(edThang.getText().toString());
            adapter = new BookAdapter(this, dsSach);
            lvSell.setAdapter(adapter);
        }
    }
}
