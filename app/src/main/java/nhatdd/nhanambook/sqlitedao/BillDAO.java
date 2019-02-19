package nhatdd.nhanambook.sqlitedao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.database.DatabaseHelper;
import nhatdd.nhanambook.model.Bill;

public class BillDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TABLE_NAME = "Bill";
    public static final String SQL_BILL = "CREATE TABLE Bill (idBill text primary key, dop date);";
    public static final String TAG = "BillDAO";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public BillDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inserBill(Bill hd) {
        ContentValues values = new ContentValues();
        values.put("idBill", hd.getIdBill());
        values.put("dop", sdf.format(hd.getDOP()));
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
    public List<Bill> getAllBill() throws ParseException {
        List<Bill> dsHoaDon = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Bill ee = new Bill();
            ee.setIdBill(c.getString(0));
            ee.setDOP(sdf.parse(c.getString(1)));
            dsHoaDon.add(ee);
            Log.d("//=====", ee.toString());
            c.moveToNext();
        }
        c.close();
        return dsHoaDon;
    }



    //update
    public int updateBill(Bill hd) {
        ContentValues values = new ContentValues();
        values.put("idBill", hd.getIdBill());
        values.put("dop", hd.getDOP().toString());
        int result = db.update(TABLE_NAME, values, "idBill=?", new
                String[]{hd.getIdBill()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteBillByID(String mahoadon) {
        int result = db.delete(TABLE_NAME, "idBill=?", new String[]{mahoadon});
        if (result == 0)
            return -1;
        return 1;
    }
}
