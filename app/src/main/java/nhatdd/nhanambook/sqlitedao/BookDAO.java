package nhatdd.nhanambook.sqlitedao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.database.DatabaseHelper;
import nhatdd.nhanambook.model.Book;

public class BookDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TABLE_NAME = "Book";
    public static final String SQL_BOOK = "CREATE TABLE Book(idBook text primary key, idTypeBook text, bookName text,author text, publisher text, price double, amount number);";
    public static final String TAG = "BookDAO";

    public BookDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //    insert
    public int insertBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("idBook", book.getIdBook());
        values.put("idTypeBook", book.getIdTypeBook());
        values.put("bookName", book.getBookName());
        values.put("author", book.getAuthor());
        values.put("publisher", book.getPublisher());
        values.put("price", book.getPrice());
        values.put("amount", book.getAmount());
        if (checkPrimaryKey(book.getIdBook())) {
            int result = db.update(TABLE_NAME, values, "idBook=?", new
                    String[]{book.getIdBook()});
            if (result == 0) {
                return -1;
            }
        } else {
            try {
                if (db.insert(TABLE_NAME, null, values) == -1) {
                    return -1;
                }
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
        }
        return 1;
    }

    //getAll
    public List<Book> getAllBook() {
        List<Book> dsSach = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Book book = new Book();
            book.setIdBook(c.getString(0));
            book.setIdTypeBook(c.getString(1));
            book.setBookName(c.getString(2));
            book.setAuthor(c.getString(3));
            book.setPublisher(c.getString(4));
            book.setPrice(c.getDouble(5));
            book.setAmount(c.getInt(6));
            dsSach.add(book);
            Log.d("//=====", book.toString());
            c.moveToNext();
        }
        c.close();
        return dsSach;
    }

    //update
    public int updateBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("idBook", book.getIdBook());
        values.put("idTypeBook", book.getIdTypeBook());
        values.put("bookName", book.getBookName());
        values.put("author", book.getAuthor());
        values.put("publisher", book.getPublisher());
        values.put("price", book.getPrice());
        values.put("amount", book.getAmount());
        int result = db.update(TABLE_NAME, values, "idBook=?", new
                String[]{book.getIdBook()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteBookByID(String idBook) {
        int result = db.delete(TABLE_NAME, "idBook=?", new String[]{idBook});
        if (result == 0)
            return -1;
        return 1;
    }

    //check
    public boolean checkPrimaryKey(String strPrimaryKey) {
        //SELECT
        String[] columns = {"idBook"};
        //WHERE clause
        String selection = "idBook=?";
        //WHERE clause arguments
        String[] selectionArgs = {strPrimaryKey};
        Cursor c = null;
        try {
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null,
                    null);
            c.moveToFirst();
            int i = c.getCount();
            c.close();
            if (i <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //check
    public Book checkBook(String strPrimaryKey) {
        Book book = new Book();
        //SELECT
        String[] columns = {"idBook"};
        //WHERE clause
        String selection = "idBook=?";
        //WHERE clause arguments
        String[] selectionArgs = {strPrimaryKey};
        Cursor c = null;
        try {
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null,
                    null);
            c.moveToFirst();
            while (c.isAfterLast() == false) {
                book.setIdBook(c.getString(0));
                book.setIdTypeBook(c.getString(1));
                book.setBookName(c.getString(2));
                book.setAuthor(c.getString(3));
                book.setPublisher(c.getString(4));
                book.setPrice(c.getDouble(5));
                book.setAmount(c.getInt(6));
                Log.d("//=====", book.toString());
                break;
            }
            c.close();
            return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //getAll
    public Book getBookByID(String idBook) {
        Book book = null;
        //WHERE clause
        String selection = "idBook=?";
        //WHERE clause arguments
        String[] selectionArgs = {idBook};
        Cursor c = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        Log.d("getBookByID", "===>" + c.getCount());
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            book = new Book();
            book.setIdBook(c.getString(0));
            book.setIdTypeBook(c.getString(1));
            book.setBookName(c.getString(2));
            book.setAuthor(c.getString(3));
            book.setPublisher(c.getString(4));
            book.setPrice(c.getDouble(5));
            book.setAmount(c.getInt(6));
            break;
        }
        c.close();
        return book;
    }

    //getAll
    public List<Book> getBookTop10(String month) {
        List<Book> bookList = new ArrayList<>();
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }

        String sSQL = "SELECT idBook, SUM(amount) as amount FROM InfoBill INNER JOIN Bill ON Bill.idBill = InfoBill.idBill WHERE strftime('%m', Bill.dop) = '" + month + "' GROUP BY idBook ORDER BY amount DESC LIMIT 10";
        Cursor c = db.rawQuery(sSQL, null);
        while (c.isAfterLast() == false) {
            Log.d("//=====", c.getString(0));
            Book s = new Book();
            s.setIdBook(c.getString(0));
            s.setIdTypeBook("");
            s.setBookName("");
            s.setAuthor("");
            s.setPublisher("");
            s.setPrice(0);
            s.setAmount(c.getInt(1));
            bookList.add(s);
            c.moveToNext();
        }
        c.close();
        return bookList;
    }
}
