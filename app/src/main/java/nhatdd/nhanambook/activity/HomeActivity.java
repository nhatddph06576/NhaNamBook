package nhatdd.nhanambook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import nhatdd.nhanambook.R;


public class HomeActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_home_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Phương Nam Book", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.home_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            case R.id.home_support:
                Toast.makeText(this, "Support", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void user(View view) {

        Intent intent = new Intent(HomeActivity.this, UserActivity.class);
        startActivity(intent);
    }

    public void bill(View view) {

        startActivity(new Intent(HomeActivity.this, BillActivity.class));
    }

    public void booksearch(View view) {

        startActivity(new Intent(HomeActivity.this, BookActivity.class));
    }

    public void booktype(View view) {

        startActivity(new Intent(HomeActivity.this, BooktypeActivity.class));
    }

    public void sell(View view) {

        startActivity(new Intent(HomeActivity.this, SellActivity.class));
    }

    public void statistical(View view) {

        startActivity(new Intent(HomeActivity.this, RevenueActivity.class));
    }
}
