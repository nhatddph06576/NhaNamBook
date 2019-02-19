package nhatdd.nhanambook.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.model.Bill;
import nhatdd.nhanambook.sqlitedao.BillDAO;

public class AddBillActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private android.support.v7.widget.Toolbar toolbar;
    EditText edDOP, edIDBill;
    BillDAO billDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    public List<Bill> dsHoaDon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        edDOP = findViewById(R.id.edtBillDate);
        edIDBill = findViewById(R.id.edtIDBill);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void addInfoBill(View view) {
        billDAO = new BillDAO(AddBillActivity.this);
        try {
            if (validation() < 0) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Bill hoaDon = new Bill(edIDBill.getText().toString(), sdf.parse(edDOP.getText().toString()));
                if (billDAO.inserBill(hoaDon) > 0) {
                    finish();
                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddBillActivity.this, AddBillInfoActivity.class);
                    Bundle b = new Bundle();
                    b.putString("IDBILL", edIDBill.getText().toString());
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public int validation() {
        if(edIDBill.getText().toString().isEmpty() || edDOP.getText().toString().isEmpty()) {
            return -1;
        }
        return 1;

    }

    public void chooseDate(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }

    private void setDate(final Calendar calendar) {
        edDOP.setText(sdf.format(calendar.getTime()));
    }

    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }
    }
}
