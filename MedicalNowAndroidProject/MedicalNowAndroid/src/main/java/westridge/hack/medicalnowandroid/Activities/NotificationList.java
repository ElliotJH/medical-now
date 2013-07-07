package westridge.hack.medicalnowandroid.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

import com.codebutler.android_websockets.WebSocketClient;

import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import westridge.hack.medicalnowandroid.Adapters.NotificationListAdapter;
import westridge.hack.medicalnowandroid.Model.NotificationObject;
import westridge.hack.medicalnowandroid.NetworkHandlers.SocketListener;
import westridge.hack.medicalnowandroid.R;

public class NotificationList extends Activity {

    WebSocketClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        NotificationListAdapter listAdapter = new NotificationListAdapter(this.getLayoutInflater(), new ArrayList<NotificationObject>());

        ListView listView = (ListView) findViewById(R.id.notification_list);
        listView.setAdapter(listAdapter);

        SocketListener listener = new SocketListener(listAdapter, this);

        CreateSocket socketCreator = new CreateSocket();

        socketCreator.execute(listener);


    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        try{
            client.disconnect();
        } catch (Exception e) {

        }

    }

    private class CreateSocket extends AsyncTask<SocketListener, Object, Object>{

        @Override
        protected Object doInBackground(SocketListener... socketListeners) {

            try {
                String url = "ws://172.16.63.93:12345";
                //String url = "ws://echo.websocket.org";
                client = new WebSocketClient(new URI(url), socketListeners[0], new ArrayList<BasicNameValuePair>());
                client.connect();
                client.send("1");

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification_list, menu);
        return true;
    }


    
}
