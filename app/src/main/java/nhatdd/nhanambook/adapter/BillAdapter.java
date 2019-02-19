package nhatdd.nhanambook.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.model.Bill;
import nhatdd.nhanambook.sqlitedao.BillDAO;
import nhatdd.nhanambook.sqlitedao.InfoBillDAO;


public class BillAdapter extends BaseAdapter implements Filterable {
    List<Bill> arrBill;
    List<Bill> arrSortBill;
    private Filter billFilter;
    public Activity context;
    public LayoutInflater inflater;
    BillDAO billDAO;
    InfoBillDAO infoBillDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public BillAdapter(Activity context, List<Bill> arrBill) {
        super();
        this.context = context;
        this.arrBill = arrBill;
        this.arrSortBill = arrBill;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        billDAO = new BillDAO(context);
        infoBillDAO = new InfoBillDAO(context);
    }

    @Override
    public int getCount() {
        return arrBill.size();
    }

    @Override
    public Object getItem(int position) {
        return arrBill.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_lv, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.txtIDBill = (TextView) convertView.findViewById(R.id.title);
            holder.txtDOP = (TextView) convertView.findViewById(R.id.subTitle);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.clear);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (infoBillDAO.checkBill(arrBill.get(position).getIdBill())) {
                        Toast.makeText(context, "Bạn phải xoá hoá đơn chi tiết trước khi xoá hoá đơn này", Toast.LENGTH_LONG).show();
                    } else {
                        billDAO.deleteBillByID(arrBill.get(position).getIdBill());
                        arrBill.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        Bill _entry = (Bill) arrBill.get(position);
        holder.img.setImageResource(R.drawable.bill);
        holder.txtIDBill.setText(_entry.getIdBill());
        holder.txtDOP.setText(sdf.format(_entry.getDOP()));

        return convertView;
    }

    public static class ViewHolder {
        ImageView img;
        TextView txtIDBill;
        TextView txtDOP;
        ImageView imgDelete;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<Bill> items) {
        this.arrBill = items;
        notifyDataSetChanged();
    }

    public void resetData() {
        arrBill = arrSortBill;
    }


    public Filter getFilter() {
        if (billFilter == null)
            billFilter = new CustomFilter() {
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results.count == 0)
                        notifyDataSetInvalidated();
                    else {
                        arrBill = (List<Bill>) results.values;
                        notifyDataSetChanged();
                    }
                }
            };
        return billFilter;
    }

    private abstract class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortBill;
                results.count = arrSortBill.size();
            } else {
                List<Bill> lsHoaDon = new ArrayList<Bill>();
                for (Bill p : arrBill) {
                    if (p.getIdBill().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsHoaDon.add(p);
                }
                results.values = lsHoaDon;
                results.count = lsHoaDon.size();
            }
            return results;
        }
    }
}
