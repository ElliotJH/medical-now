package westridge.hack.medicalnowandroid.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import westridge.hack.medicalnowandroid.Model.NotificationObject;
import westridge.hack.medicalnowandroid.R;

/**
 * Created by jordan on 7/6/13.
 */


public class NotificationListAdapter extends BaseAdapter{

    private List<NotificationObject> notifications;
    private LayoutInflater inflater;

    public NotificationListAdapter(LayoutInflater theInflater, List<NotificationObject> currentNotifications){
        inflater = theInflater;
        notifications = currentNotifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int i) {
        return notifications.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View correctView = view;

        if(correctView==null){
            correctView = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        TextView textView = (TextView) correctView.findViewById(R.id.description);

        textView.setText("BLAH " + i);

        return correctView;
    }

    public void AddItem(NotificationObject item){
        notifications.add(item);
        this.notifyDataSetChanged();
    }

}
