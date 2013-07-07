package westridge.hack.medicalnowandroid.NetworkHandlers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.codebutler.android_websockets.WebSocketClient;

import org.json.JSONException;
import org.json.JSONObject;

import westridge.hack.medicalnowandroid.Adapters.NotificationListAdapter;
import westridge.hack.medicalnowandroid.Model.NotificationObject;

/**
 * Created by jordan on 7/7/13.
 */
public class SocketListener implements WebSocketClient.Listener{

    private NotificationListAdapter targetAdapter;
    private Activity context;
    public SocketListener(NotificationListAdapter adapter, Activity context){

        targetAdapter = adapter;
        this.context = context;

    }

    @Override
    public void onConnect() {
        Log.d("Web Socket", "Open");
    }

    @Override
    public void onMessage(String message) {


        try {
            JSONObject json = new JSONObject(message);

            String title = json.getString("Title");
            String description = json.getString("Description");
            String patient = json.getString("Patient");
            String email = json.getString("Email");

            final NotificationObject obj = new NotificationObject(title, description, patient, email);

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    targetAdapter.AddItem(obj);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMessage(byte[] data) {

    }

    @Override
    public void onDisconnect(int code, String reason) {

        Log.d("Web Socket", "Disconnect");

    }

    @Override
    public void onError(Exception error) {

    }
}
