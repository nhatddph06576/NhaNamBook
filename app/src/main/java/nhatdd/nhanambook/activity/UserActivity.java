package nhatdd.nhanambook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.adapter.UserAdapter;
import nhatdd.nhanambook.model.User;
import nhatdd.nhanambook.sqlitedao.UserDAO;

public class UserActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView lvUser;
    UserAdapter userAdapter = null;
    UserDAO userDAO;
    public static List<User> arrUser = new ArrayList<>();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();


    }

    private void initData() {

        userDAO = new UserDAO(this);
        arrUser = userDAO.getAllUser();
        userAdapter = new UserAdapter(this, arrUser);
        lvUser.setAdapter(userAdapter);

        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UserActivity.this,EditInfoUserActivity.class);
                Bundle b = new Bundle();
                b.putString("USERNAME", arrUser.get(position).getUsername());
                b.putString("PHONE", arrUser.get(position).getPhone());
                b.putString("FULLNAME", arrUser.get(position).getName());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        arrUser.clear();
        arrUser = userDAO.getAllUser();
        userAdapter.changeDataset(userDAO.getAllUser());
    }

    private void initView() {
        setContentView(R.layout.activity_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvUser = (ListView) findViewById(R.id.lvUser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_add:

                startActivity(new Intent(UserActivity.this, AddUserActivity.class));
                return (true);
            case R.id.user_change_pw:
                startActivity(new Intent(UserActivity.this, ChangePasswActivity.class));
                return (true);
            case R.id.user_logout:
                finish();
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
