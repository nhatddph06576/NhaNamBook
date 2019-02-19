package nhatdd.nhanambook.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.model.InfoBill;
import nhatdd.nhanambook.sqlitedao.InfoBillDAO;

public class InfoBillAdapter extends BaseAdapter {
    List<InfoBill> arrInfoBillList;
    public Activity context;
    public LayoutInflater inflater;
    InfoBillDAO infoBillDAO;

    public InfoBillAdapter(Activity context, List<InfoBill> arrInfoBillList) {
        super();
        this.context = context;
        this.arrInfoBillList = arrInfoBillList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        infoBillDAO = new InfoBillDAO(context);
    }

    @Override
    public int getCount() {
        return arrInfoBillList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrInfoBillList.get(position);
    }

    public static class ViewHolder {
        TextView txtMaSach;
        TextView txtSoLuong;
        TextView txtGiaBia;
        TextView txtThanhTien;
        ImageView imgDelete;
    }

// Commit code pust

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_lv_bill, null);
            holder.txtMaSach = (TextView) convertView.findViewById(R.id.IDBook);
            holder.txtSoLuong = (TextView) convertView.findViewById(R.id.Amount);
            holder.txtGiaBia = (TextView) convertView.findViewById(R.id.Price);
            holder.txtThanhTien = (TextView) convertView.findViewById(R.id.Money);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.clear);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infoBillDAO.deleteInfoBillByID(String.valueOf(arrInfoBillList.get(position).getIdInfoBill()));
                    arrInfoBillList.remove(position);
                    notifyDataSetChanged();

                }
            });
        } else
            holder = (ViewHolder) convertView.getTag();
        InfoBill entry = (InfoBill) arrInfoBillList.get(position);
        holder.txtMaSach.setText("Mã sách: " + entry.getBook().getIdBook());
        holder.txtSoLuong.setText("Số lượng: " + entry.getAmountPay());
        holder.txtGiaBia.setText("Giá bán: " + entry.getBook().getPrice() + " VNĐ");
        holder.txtThanhTien.setText("Thành tiền: " + entry.getAmountPay() * entry.getBook().getPrice() + " VNĐ");
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<InfoBill> items) {
        this.arrInfoBillList = items;
        notifyDataSetChanged();
    }
}
