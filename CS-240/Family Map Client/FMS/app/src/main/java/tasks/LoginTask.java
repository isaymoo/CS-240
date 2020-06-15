package tasks;

import android.content.Context;
import android.os.AsyncTask;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import Request.LoginRequest;
import Result.LoginResult;

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResult> {
    private LoginResult result = new LoginResult();
    private LoginRequest request;
    private Fragment frag;
    private Context context;
    public LoginTask(Fragment frag, Context context){
        this.frag = frag;
        this.context = context;
    }

    @Override
    public LoginResult doInBackground(LoginRequest... req) {
        request = req[0];
        try {
            URL url = new URL("http://" + req[0].getHost() + ":" + req[0].getPort() + "/user/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.addRequestProperty("Accept", "application/json");
            Gson gson = new Gson();
            String json = gson.toJson(request);
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter sw = new OutputStreamWriter(os);
            sw.write(json);
            sw.flush();
            os.close();
            conn.connect();
            if(conn.getResponseCode() != 200) {
                result.setSuccess(false);
                result.setMessage(conn.getResponseMessage());
            }
            else{
                Reader reader = new InputStreamReader(conn.getInputStream());
                Gson resultGson = new Gson();
                result = resultGson.fromJson(reader, LoginResult.class);
                result.setSuccess(true);
            }
            return result;


        } catch (IOException e) {
            result.setMessage("Error invalid URL");
            result.setSuccess(false);
            return result;
        }
    }


}
