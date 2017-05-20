package interfata.ip.notifier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by munte on 20.05.2017.
 */

public class NotificationAdapter extends BaseAdapter {
    Context context;
    List<Notificare> rowItems;

    NotificationAdapter(Context context, List<Notificare> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    public int getCount() {
        return rowItems.size();
    }


    public Object getItem(int position) {
        return rowItems.get(position);
    }

    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView pic;
        TextView title;
        TextView content;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_view, null);
            holder = new ViewHolder();

            holder.title = (TextView) convertView
                    .findViewById(R.id.title);
           // holder.pic = (ImageView) convertView.findViewById(R.id.imageView);
            holder.content = (TextView) convertView.findViewById(R.id.content);


            Notificare row_pos = rowItems.get(position);

           // holder.pic.setImageResource(row_pos.getImageId());
            holder.title.setText(row_pos.getTitle());
            holder.content.setText(row_pos.getContent());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}