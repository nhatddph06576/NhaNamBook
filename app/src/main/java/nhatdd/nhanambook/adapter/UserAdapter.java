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
import nhatdd.nhanambook.model.User;
import nhatdd.nhanambook.sqlitedao.UserDAO;

public class UserAdapter extends BaseAdapter {

    List<User> arrUser;
    public Activity context;
    public LayoutInflater inflater;
    UserDAO userDAO;


    public UserAdapter(Activity context, List<User> arrUser) {
        super();
        this.arrUser = arrUser;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        userDAO = new UserDAO(context);
    }

    @Override
    public int getCount() {
        return arrUser.size();
    }

    @Override
    public Object getItem(int position) {
        return arrUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        ImageView img;
        TextView txtName;
        TextView txtPhone;
        ImageView imgDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_lv, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.txtName = (TextView) convertView.findViewById(R.id.title);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.subTitle);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.clear);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    userDAO.deleteUserByID(arrUser.get(position).getUsername());
                    arrUser.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        User _entry = (User) arrUser.get(position);
        holder.img.setImageResource(R.drawable.user);
        holder.txtName.setText(_entry.getName());
        holder.txtPhone.setText(_entry.getPhone());
        holder.imgDelete.setImageResource(R.drawable.ic_clear_black_24dp);
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<User> items) {
        this.arrUser = items;
        notifyDataSetChanged();
    }

}
