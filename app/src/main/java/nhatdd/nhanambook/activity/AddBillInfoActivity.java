package nhatdd.nhanambook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.adapter.InfoBillAdapter;
import nhatdd.nhanambook.model.Bill;
import nhatdd.nhanambook.model.Book;
import nhatdd.nhanambook.model.InfoBill;
import nhatdd.nhanambook.sqlitedao.BillDAO;
import nhatdd.nhanambook.sqlitedao.BookDAO;
import nhatdd.nhanambook.sqlitedao.InfoBillDAO;

public class AddBillInfoActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    EditText edMaSach, edMaHoaDon, edSoLuong;
    TextView tvThanhTien;
    InfoBillDAO infoBillDAO;
    BillDAO hoaDonDAO;
    BookDAO sachDAO;
    public List<InfoBill> dsHDCT = new ArrayList<>();
    private List<Book> dsSach = new ArrayList<>();
    private List<Bill> dshoadon = new ArrayList<>();
    ListView lvCart;
    InfoBillAdapter adapter = null;
    public Spinner spinner, spinnerhd;
    String masach, mahd;
    double thanhTien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_billinfo);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edSoLuong = (EditText) findViewById(R.id.edtAmount);
        lvCart = (ListView) findViewById(R.id.lvBillInfo);
        spinner = (Spinner) findViewById(R.id.spIDBook);
        spinnerhd = findViewById(R.id.spidbill);
        sachDAO = new BookDAO(this);
        infoBillDAO = new InfoBillDAO(this);
        hoaDonDAO = new BillDAO(this);
        tvThanhTien = (TextView) findViewById(R.id.tvThanhtien);
        adapter = new InfoBillAdapter(this, dsHDCT);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AddBillInfoActivity.this, BillActivity.class));
            }
        });
        getMahd();
        getMaSach();

//        edMaHoaDon= b.getString("IDBILL");
//        if (b != null) {
//            edMaHoaDon.setText(b.getString("IDBILL"));
//        }
        spinnerhd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mahd = dshoadon.get(spinnerhd.getSelectedItemPosition()).getIdBill();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Intent in = getIntent();
        Bundle b = in.getExtras();
        mahd = b.getString("IDBILL");

        spinnerhd.setSelection(checkPositionHD(mahd));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                masach = dsSach.get(spinner.getSelectedItemPosition()).getIdBook();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(checkMaSach(dsHDCT, masach));

        lvCart.setAdapter(adapter);


    }


    public void addBillinfo(View view) {

        try {
            if (validation() > 0) {
                infoBillDAO = new InfoBillDAO(AddBillInfoActivity.this);
                sachDAO = new BookDAO(AddBillInfoActivity.this);
                Book sach = sachDAO.getBookByID(masach);
                if (sach != null) {
                    int pos = checkMaSach(dsHDCT, masach);
//                    HoaDon hoaDon = new HoaDon(edMaHoaDon.getText().toString(), new Date());
                    Bill hoaDon = new Bill(mahd, new Date());
                    InfoBill hoaDonChiTiet = new
                            InfoBill(1, hoaDon, sach, Integer.parseInt(edSoLuong.getText().toString()));
                    if (pos >= 0) {
                        int soluong = dsHDCT.get(pos).getAmountPay();
                        hoaDonChiTiet.setAmountPay(soluong + Integer.parseInt(edSoLuong.getText().toString()));
                        dsHDCT.set(pos, hoaDonChiTiet);
                    } else {
                        dsHDCT.add(hoaDonChiTiet);
                    }
                    adapter.changeDataset(dsHDCT);

                }
//                else {
//                    Toast.makeText(this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
//                }
            }
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }

    }

    public void DoneInfoBillInfo(View view) {
        infoBillDAO = new InfoBillDAO(AddBillInfoActivity.this);
        //tinh tien
        thanhTien = 0;
        try {
            for (InfoBill hd : dsHDCT) {
                infoBillDAO.inserInfoBill(hd);
                thanhTien = thanhTien + hd.getAmountPay() * hd.getBook().getPrice();
            }
            tvThanhTien.setText("Tổng tiền: " + thanhTien + " VNĐ");
            Toast.makeText(this, "Xong", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public int checkMaSach(List<InfoBill> lsHD, String maSach) {
        int pos = -1;
        for (int i = 0; i < lsHD.size(); i++) {
            InfoBill hd = lsHD.get(i);
            if (hd.getBook().getIdBook().equalsIgnoreCase(maSach)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public int validation() {
        if (edSoLuong.getText().toString().isEmpty()) {
            return -1;
        }
        return 1;
    }

    public void getMaSach() {
        sachDAO = new BookDAO(this);
        dsSach = sachDAO.getAllBook();
        ArrayAdapter<Book> dataAdapter = new ArrayAdapter<Book>(this, android.R.layout.simple_spinner_item, dsSach);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void getMahd() {
        hoaDonDAO = new BillDAO(this);
        try {
            dshoadon = hoaDonDAO.getAllBill();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayAdapter<Bill> dataAdapter = new ArrayAdapter<Bill>(this, android.R.layout.simple_spinner_item, dshoadon);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerhd.setAdapter(dataAdapter);
    }

    public int checkPositionHD(String strTheLoai) {
        for (int i = 0; i < dshoadon.size(); i++) {
            if (strTheLoai.equals(dshoadon.get(i).getIdBill())) {
                return i;
            }
        }
        return 0;
    }
}
