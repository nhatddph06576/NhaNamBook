package nhatdd.nhanambook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.adapter.TypeBookAdapter;
import nhatdd.nhanambook.model.TypeBook;
import nhatdd.nhanambook.sqlitedao.TypeBookDAO;

public class BooktypeActivity extends AppCompatActivity {
    Toolbar tbTypeBook;

    ListView lvTypeBook;

    public static List<TypeBook> arrTypeBook = new ArrayList<>();
    TypeBookAdapter adapter = null;
    TypeBookDAO typeBookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booktype);

        tbTypeBook = findViewById(R.id.toolbar);
        lvTypeBook = findViewById(R.id.lvType);
//        registerForContextMenu(lvTypeBook);
        setSupportActionBar(tbTypeBook);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        typeBookDAO = new TypeBookDAO(this);
        arrTypeBook = typeBookDAO.getAllTypeBook();
        adapter = new TypeBookAdapter(this, arrTypeBook);
        lvTypeBook.setAdapter(adapter);
        lvTypeBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BooktypeActivity.this, EditBookTypeActivity.class);
                Bundle b = new Bundle();
                b.putString("ID", arrTypeBook.get(position).getIdTypeBook());
                b.putString("NAME", arrTypeBook.get(position).getTypeName());
                b.putString("LOCATION", String.valueOf(arrTypeBook.get(position).getLocation()));
                b.putString("NOTI", arrTypeBook.get(position).getNoti());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        tbTypeBook.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        tbTypeBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
//
    @Override
    protected void onResume() {
        arrTypeBook.clear();
        arrTypeBook = typeBookDAO.getAllTypeBook();
        adapter.changeDataset(arrTypeBook);
        super.onResume();
    }

    public void addType(View view) {
        startActivity(new Intent(BooktypeActivity.this, AddBooktypeActivity.class));

    }
}
