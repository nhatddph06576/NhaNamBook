package nhatdd.nhanambook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.adapter.BookAdapter;
import nhatdd.nhanambook.model.Book;
import nhatdd.nhanambook.sqlitedao.BookDAO;

public class BookActivity extends AppCompatActivity {
    Toolbar tbTypeBook;
    ListView lvBook;
    public static List<Book> arrayList = new ArrayList<>();

    BookAdapter adapter = null;
    BookDAO bookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        tbTypeBook = findViewById(R.id.toolbar);
        lvBook = findViewById(R.id.lvBook);

        setSupportActionBar(tbTypeBook);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        tbTypeBook.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        tbTypeBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//

            }
        });

        bookDAO = new BookDAO(BookActivity.this);
        arrayList = bookDAO.getAllBook();
        adapter = new BookAdapter(this, arrayList);
        lvBook.setAdapter(adapter);
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book book = (Book) parent.getItemAtPosition(position);
                Intent intent = new Intent(BookActivity.this, AddBookActivity.class);
                Bundle b = new Bundle();
                b.putString("IDBOOK", book.getIdBook());
                b.putString("IDTYPEBOOK", book.getIdTypeBook());
                b.putString("BOOKNAME", book.getBookName());
                b.putString("AUTHOR", book.getAuthor());
                b.putString("PUBLISHER", book.getPublisher());
                b.putString("PRICE", String.valueOf(book.getPrice()));
                b.putString("AMOUNT", String.valueOf(book.getAmount()));
                intent.putExtras(b);

                startActivity(intent);

            }
        });
// TextFilter
        lvBook.setTextFilterEnabled(true);
        EditText edSeach = (EditText) findViewById(R.id.edtSe);
        edSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before) {
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void addType(View view) {
        finish();
        startActivity(new Intent(BookActivity.this, AddBookActivity.class));

    }
}
