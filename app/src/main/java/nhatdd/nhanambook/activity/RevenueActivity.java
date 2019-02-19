package nhatdd.nhanambook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import nhatdd.nhanambook.R;
import nhatdd.nhanambook.sqlitedao.InfoBillDAO;

public class RevenueActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    TextView tvNgay, tvThang, tvNam;
    InfoBillDAO hoaDonChiTietDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvNgay = (TextView) findViewById(R.id.tvngay);
        tvThang = (TextView) findViewById(R.id.tvthang);
        tvNam = (TextView) findViewById(R.id.tvnam);
        hoaDonChiTietDAO = new InfoBillDAO(this);
        tvNgay.setText("Hôm nay: " + hoaDonChiTietDAO.getDoanhThuTheoNgay());
        tvThang.setText("Tháng này: " + hoaDonChiTietDAO.getDoanhThuTheoThang());
        tvNam.setText("Năm này: " + hoaDonChiTietDAO.getDoanhThuTheoNam());
    }
}
