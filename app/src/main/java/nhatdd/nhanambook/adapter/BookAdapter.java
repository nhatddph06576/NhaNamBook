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


import java.util.ArrayList;
import java.util.List;

import nhatdd.nhanambook.R;
import nhatdd.nhanambook.model.Book;
import nhatdd.nhanambook.sqlitedao.BookDAO;

public class BookAdapter extends BaseAdapter implements Filterable {
    List<Book> arrBook;
    List<Book> arrSortBook;
    private Filter bookFilter;
    public Activity context;
    public LayoutInflater inflater;
    BookDAO bookDAO;

    public BookAdapter(Activity context, List<Book> arrBook) {
        super();
        this.arrBook = arrBook;
        this.arrSortBook = arrBook;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bookDAO = new BookDAO(context);
    }

    @Override
    public int getCount() {
        return arrBook.size();
    }

    @Override
    public Object getItem(int position) {
        return arrBook.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        ImageView img;
        TextView txtBookName;
        TextView txtAmount;
        ImageView imgDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_lv, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.title);
            holder.txtAmount = (TextView) convertView.findViewById(R.id.subTitle);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.clear);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookDAO.deleteBookByID(arrBook.get(position).getIdBook());
                    arrBook.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        Book _entry = (Book) arrBook.get(position);
        holder.img.setImageResource(R.drawable.booksearch);
        holder.txtBookName.setText("Mã sách: " + _entry.getIdBook());
        holder.txtAmount.setText("Số lượng: " + _entry.getAmount());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (bookFilter == null)
            bookFilter = new CustomFilter();
        return bookFilter;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public void resetData() {
        arrBook = arrSortBook;
    }

    public void changeDataset(List<Book> items) {
        this.arrBook = items;
        notifyDataSetChanged();
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortBook;
                results.count = arrSortBook.size();
            } else {
                List<Book> lsBook = new ArrayList<Book>();
                for (Book p : arrBook) {
                    if
                            (p.getIdBook().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsBook.add(p);
                }
                results.values = lsBook;
                results.count = lsBook.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                arrBook = (List<Book>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
