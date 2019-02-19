package nhatdd.nhanambook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.adapter.InfoBillAdapter;
import nhatdd.nhanambook.model.InfoBill;
import nhatdd.nhanambook.sqlitedao.InfoBillDAO;

public class InfoBillActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ListView lvifBill;
    public List<InfoBill> infoBills = new ArrayList<>();
    InfoBillAdapter adapter = null;
    InfoBillDAO infoBillDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        infoBillDAO = new InfoBillDAO(InfoBillActivity.this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            infoBills = infoBillDAO.getAllInfoBillByID(b.getString("IDBILL"));
            adapter = new InfoBillAdapter(this, infoBills);
            lvifBill.setAdapter(adapter);
        }

    }

    private void initView() {
        setContentView(R.layout.activity_info_bill);
        setSupportActionBar(toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvifBill = (ListView) findViewById(R.id.lvIfbill);


    }


}
