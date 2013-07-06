package westridge.hack.medicalnowandroid.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

import java.util.ArrayList;

import westridge.hack.medicalnowandroid.Adapters.NotificationListAdapter;
import westridge.hack.medicalnowandroid.Model.NotificationObject;
import westridge.hack.medicalnowandroid.R;

public class NotificationList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        NotificationListAdapter listAdapter = new NotificationListAdapter(this.getLayoutInflater(), new ArrayList<NotificationObject>());

        listAdapter.AddItem(new NotificationObject());
        listAdapter.AddItem(new NotificationObject());
        listAdapter.AddItem(new NotificationObject());
        listAdapter.AddItem(new NotificationObject());
        listAdapter.AddItem(new NotificationObject());

        ListView listView = (ListView) findViewById(R.id.notification_list);
        listView.setAdapter(listAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification_list, menu);
        return true;
    }
    
}
