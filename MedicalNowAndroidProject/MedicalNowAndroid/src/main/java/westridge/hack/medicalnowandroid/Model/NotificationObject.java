package westridge.hack.medicalnowandroid.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import android.net.Uri;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jordan on 7/6/13.
 */
public class NotificationObject {

    private String name;
    private String description;
    private String patient;
    private String patientEmail;
    private Bitmap img;

    private final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public NotificationObject(String name, String description, String patient, String patientEmail) {

        this.name = name;
        this.description = description;
        this.patient = patient;
        this.patientEmail = patientEmail;

        String url = "http://www.gravatar.com/avatar/"+md5(patientEmail);

        GravLoader loader = new GravLoader();
        loader.execute(url);

    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    private class GravLoader extends AsyncTask<String, Object, Object>{

        @Override
        protected Object doInBackground(String... strings) {

            InputStream is = null;

            try {
                URL url = new URL((String) strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
// Starts the query
                conn.connect();
                int response = conn.getResponseCode();

                is = conn.getInputStream();

                img = BitmapFactory.decodeStream(is);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
