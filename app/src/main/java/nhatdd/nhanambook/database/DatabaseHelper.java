package nhatdd.nhanambook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tkgd.example.cuson.phuongnambook.sqlitedao.BillDAO;
import com.tkgd.example.cuson.phuongnambook.sqlitedao.BookDAO;
import com.tkgd.example.cuson.phuongnambook.sqlitedao.InfoBillDAO;
import com.tkgd.example.cuson.phuongnambook.sqlitedao.TypeBookDAO;
import com.tkgd.example.cuson.phuongnambook.sqlitedao.UserDAO;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "Phuongnambook", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.SQL_USER);
        db.execSQL(TypeBookDAO.SQL_TYPEBOOK);
        db.execSQL(BookDAO.SQL_BOOK);
        db.execSQL(BillDAO.SQL_BILL);
        db.execSQL(InfoBillDAO.SQL_INFO_BILL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TypeBookDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BookDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BillDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + InfoBillDAO.TABLE_NAME);
        onCreate(db);
    }

}
