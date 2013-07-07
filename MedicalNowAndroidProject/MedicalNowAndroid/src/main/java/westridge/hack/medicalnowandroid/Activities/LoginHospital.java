package westridge.hack.medicalnowandroid.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import westridge.hack.medicalnowandroid.R;

/**
 * Created by jordan on 7/6/13.
 */
public class LoginHospital extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        SharedPreferences prefs = getSharedPreferences(USER_PREFS, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("has_appID", false);

        editor.commit();

        //setUpGCM();

    }

    public final static String USER_PREFS = "thisIsAPrefsFile", REGISTRATION_ID="thisIsAUserRegistrationID";

    public final static String HAS_ID = "has_appID", APP_ID = "app_id";

    public void loginClick(View button){

        TextView loginBox = (TextView) findViewById(R.id.username_box);
        TextView passwordBox = (TextView) findViewById(R.id.password_edit);

        String userID = loginBox.getText().toString();
        String password = passwordBox.getText().toString();
        String appID;

        boolean waiting = true;

        SharedPreferences prefs = getSharedPreferences(USER_PREFS, MODE_PRIVATE);

        SendLogin loginSender = new SendLogin();

        loginSender.execute(userID, password);

    }

    private class SendLogin extends AsyncTask<String, Object, String>{
        private String LOGIN_URL="172.16.63.93:9000/authenticate";
        @Override
        protected String doInBackground(String... strings) {
            String userID, password, appID;
            String apiKey = "";
            userID = strings[0];
            password = strings[1];
            appID = strings[2];

            boolean isIOS = false;

            try {
                URL url = new URL(LOGIN_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");

                conn.setDoOutput(true);

                conn.setDoInput(true);
                    // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                InputStream is = conn.getInputStream();

                apiKey = readIt(is);

            } catch (Exception e){
                  e.printStackTrace();
            }

            return apiKey;
        }

        public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            boolean ready = true;
            String output = "";
            while(ready){

                int read = reader.read();

                if(read < 0)
                    ready = false;
                else
                    output+= (char) read;

            }

            return output;
        }

        @Override
        protected void onPostExecute(String result){

            try {

                JSONObject jsonObj = new JSONObject(result);
                String apiKey = jsonObj.getString("api-key");
                Toast.makeText(LoginHospital.this, apiKey, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();

                findViewById(R.id.errorLabel).setVisibility(View.VISIBLE);

            }


        }

    }

}