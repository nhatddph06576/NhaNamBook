package nhatdd.nhanambook.sqlitedao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.database.DatabaseHelper;
import nhatdd.nhanambook.model.Bill;
import nhatdd.nhanambook.model.Book;
import nhatdd.nhanambook.model.InfoBill;

public class InfoBillDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TABLE_NAME = "InfoBill";
    public static final String SQL_INFO_BILL = "CREATE TABLE InfoBill(idInfoBill INTEGER PRIMARY KEY AUTOINCREMENT," + "idBill text NOT NULL, idBook text NOT NULL, amountPay INTEGER);";
    public static final String TAG = "InfoBill";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public InfoBillDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inserInfoBill(InfoBill hd) {
        ContentValues values = new ContentValues();
        values.put("idBill", hd.getBill().getIdBill());
        values.put("idBook", hd.getBook().getIdBook());
        values.put("amountPay", hd.getAmountPay());
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    //getAll
    public List<InfoBill> getAllInfoBill() {
        List<InfoBill> dsHoaDonChiTiet = new ArrayList<>();
        String sSQL = "SELECT idInfoBill, Bill.idBill,Bill.dop,Book.idBook, Book.idTypeBook, Book.bookName, Book.author, Book.publisher, Book.price,Book.amount,InfoBill.amountPay FROM InfoBill INNER JOIN Bill on InfoBill.idBill = Bill.idBill INNER JOIN Book on Book.idBook = InfoBill.idBook ";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (c.isAfterLast() == false) {
                InfoBill ee = new InfoBill();
                ee.setIdInfoBill(c.getInt(0));
                ee.setBill(new Bill(c.getString(1), sdf.parse(c.getString(2))));
                ee.setBook(new Book(c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getInt(8), c.getInt(9)));
                ee.setAmountPay(c.getInt(10));
                dsHoaDonChiTiet.add(ee);
                Log.d("//=====", ee.toString());
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return dsHoaDonChiTiet;
    }

    //getAll
    public List<InfoBill> getAllInfoBillByID(String maHoaDon) {
        List<InfoBill> dsHoaDonChiTiet = new ArrayList<>();
        String sSQL = "SELECT idInfoBill, Bill.idBill, Bill.dop, Book.idBook, Book.idTypeBook, Book.bookName, Book.author, Book.publisher, Book.price, Book.amount, InfoBill.amountPay FROM InfoBill INNER JOIN Bill on InfoBill.idBill = Bill.idBill INNER JOIN Book on Book.idBook = InfoBill.idBook where InfoBill.idBill = '" + maHoaDon + "' ";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (c.isAfterLast() == false) {
                InfoBill ee = new InfoBill();
                ee.setIdInfoBill(c.getInt(0));
                ee.setBill(new Bill(c.getString(1), sdf.parse(c.getString(2))));
                ee.setBook(new Book(c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getInt(8), c.getInt(9)));
                ee.setAmountPay(c.getInt(10));
                dsHoaDonChiTiet.add(ee);
                Log.d("//=====", ee.toString());
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return dsHoaDonChiTiet;
    }

    //update
    public int updateInfoBill(InfoBill hd) {
        ContentValues values = new ContentValues();
        values.put("idInfoBill", hd.getIdInfoBill());
        values.put("idBill", hd.getBill().getIdBill());
        values.put("idBook", hd.getBook().getIdBook());
        values.put("amountPay", hd.getAmountPay());
        int result = db.update(TABLE_NAME, values, "idInfoBill=?", new
                String[]{String.valueOf(hd.getIdInfoBill())});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteInfoBillByID(String idInfoBill) {
        int result = db.delete(TABLE_NAME, "idInfoBill=?", new String[]{idInfoBill});
        if (result == 0)
            return -1;
        return 1;
    }

    //check
    public boolean checkBill(String idBill) {
        //SELECT
        String[] columns = {"idBill"};
        //WHERE clause
        String selection = "idBill=?";
        //WHERE clause arguments
        String[] selectionArgs = {idBill};
        Cursor c = null;
        try {
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
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

    public double getDoanhThuTheoNgay() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(Book.price * InfoBill.amountPay) as 'tongtien' FROM Bill INNER JOIN InfoBill on Bill.idBill = InfoBill.idBill INNER JOIN Book on InfoBill.idBook = Book.idBook where Bill.dop = date('now') GROUP BY InfoBill.idBook)tmp ";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoThang() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(Book.price * InfoBill.amountPay)as 'tongtien' FROM Bill INNER JOIN InfoBill on Bill.idBill = InfoBill.idBill INNER JOIN Book on InfoBill.idBook = Book.idBook where strftime('%m', Bill.dop) = strftime('%m', 'now') GROUP BY InfoBill.idBook)tmp ";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoNam() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(Book.price * InfoBill.amountPay) as 'tongtien' FROM Bill INNER JOIN InfoBill on Bill.idBill = InfoBill.idBill INNER JOIN Book on InfoBill.idBook = Book.idBook where strftime('%Y', Bill.dop) = strftime('%Y', 'now') GROUP BY InfoBill.idBook)tmp ";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public int updateHoaDonChiTiet(String s, String s1, String s2) {
        ContentValues values = new ContentValues();
        values.put("idBook", s1);
        values.put("amountPay", s2);
        int result = db.update(TABLE_NAME, values, "idInfoBill=?", new String[]{s});
        if (result == 0) {
            return -1;
        }
        return 1;
    }
}
