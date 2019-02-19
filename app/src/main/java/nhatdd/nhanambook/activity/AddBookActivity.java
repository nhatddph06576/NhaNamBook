package nhatdd.nhanambook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.model.Book;
import nhatdd.nhanambook.model.TypeBook;
import nhatdd.nhanambook.sqlitedao.BookDAO;
import nhatdd.nhanambook.sqlitedao.TypeBookDAO;

public class AddBookActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    BookDAO bookDAO;
    TypeBookDAO typeBookDAO;
    Spinner spnTypeBook;
    EditText edIDBook, edBookName, edPublisher, edAuthor, edPrice, edAmount;
    String idTypeBook = "";
    List<TypeBook> typeBookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        spnTypeBook = (Spinner) findViewById(R.id.spinerBook);
        getTypeBook();
        edIDBook = (EditText) findViewById(R.id.edtIDBook);
        edBookName = (EditText) findViewById(R.id.edtNameBook);
        edPublisher = (EditText) findViewById(R.id.edtPublishB);
        edAuthor = (EditText) findViewById(R.id.edtAuthorB);
        edPrice = (EditText) findViewById(R.id.edtPriceBook);
        edAmount = (EditText) findViewById(R.id.edtAmountBook);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spnTypeBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idTypeBook = typeBookList.get(spnTypeBook.getSelectedItemPosition()).getIdTypeBook();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //load data into form
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            edIDBook.setText(b.getString("IDBOOK"));
            String idTypeBook = b.getString("IDTYPEBOOK");
            edBookName.setText(b.getString("BOOKNAME"));
            edPublisher.setText(b.getString("PUBLISHER"));
            edAuthor.setText(b.getString("AUTHOR"));
            edPrice.setText(b.getString("PRICE"));
            edAmount.setText(b.getString("AMOUNT"));
            spnTypeBook.setSelection(checkPositionTypeBook(idTypeBook));
        }
    }

    public void showSpinner(View view) {
        bookDAO = new BookDAO(AddBookActivity.this);
        bookDAO.getAllBook();
    }

    public void getTypeBook() {
        typeBookDAO = new TypeBookDAO(AddBookActivity.this);
        typeBookList = typeBookDAO.getAllTypeBook();
        ArrayAdapter<TypeBook> dataAdapter = new ArrayAdapter<TypeBook>(this,
                android.R.layout.simple_spinner_item, typeBookList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTypeBook.setAdapter(dataAdapter);
    }

    public void Addbook(View view) {
        if (edIDBook.getText().length() == 0 ||
                edBookName.getText().length() == 0 ||
                edPublisher.getText().length() == 0 ||
                edPrice.getText().length() == 0 ||
                edAuthor.getText().length() == 0 || edAmount.getText().length() == 0) {
            Toast.makeText(this, "Bạn phải nhập đủ thông tin ", Toast.LENGTH_SHORT).show();

        } else {
            bookDAO = new BookDAO(AddBookActivity.this);
            Book book = new
                    Book(edIDBook.getText().toString(), idTypeBook, edBookName.getText().toString(),
                    edAuthor.getText().toString(), edPublisher.getText().toString(),
                    Double.parseDouble(edPrice.getText().toString()), Integer.parseInt(edAmount.getText().toString()));
            try {
                if (bookDAO.insertBook(book) > 0) {
                    Toast.makeText(getApplicationContext(), "Hoàn thành",
                            Toast.LENGTH_SHORT).show();

                    finish();
                    startActivity(new Intent(AddBookActivity.this, BookActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Thất bại",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Log.e("Error", ex.toString());
            }
        }

    }

    public int checkPositionTypeBook(String strTypeBook) {
        for (int i = 0; i < typeBookList.size(); i++) {
            if (strTypeBook.equals(typeBookList.get(i).getIdTypeBook())) {
                return i;
            }
        }
        return 0;
    }
}
