package nhatdd.nhanambook.sqlitedao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.database.DatabaseHelper;
import nhatdd.nhanambook.model.User;

public class UserDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "User";
    public static final String SQL_USER = "CREATE TABLE User (username text primary key, password text, phone text, name text);";
    public static final String TAG = "UserDAO";

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    //insert
    public int insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("phone", user.getPhone());
        values.put("name", user.getName());
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            User ee = new User();
            ee.setUsername(c.getString(0));
            ee.setPassword(c.getString(1));
            ee.setPhone(c.getString(2));
            ee.setName(c.getString(3));
            userList.add(ee);
            Log.d("//=====", ee.toString());
            c.moveToNext();
        }
        c.close();
        return userList;
    }

    public User getUser(String username) {
        List<User> userList = new ArrayList<>();
        User ee = new User();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {

            ee.setUsername(c.getString(0));
            ee.setPassword(c.getString(1));
            ee.setPhone(c.getString(2));
            ee.setName(c.getString(3));
            userList.add(ee);
            Log.d("//=====", ee.toString());
            c.moveToNext();
        }
        c.close();
        return ee;
    }

    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("phone", user.getPhone());
        values.put("name", user.getName());
        int result = db.update(TABLE_NAME, values, "username=?", new
                String[]{user.getUsername()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int changePasswordUser(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        int result = db.update(TABLE_NAME, values, "username=?", new
                String[]{user.getUsername()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int updateInfoUser(String username, String phone, String name) {
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        values.put("name", name);
        int result = db.update(TABLE_NAME, values, "username=?", new
                String[]{username});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteUserByID(String username) {
        int result = db.delete(TABLE_NAME, "username=?", new String[]{username});
        if (result == 0)
            return -1;
        return 1;
    }

    public int checkLogin(String username, String password) {
        int result = db.delete(TABLE_NAME, "username=? AND password=?", new String[]{username, password});
        if (result == 0)
            return -1;
        return 1;
    }
}

