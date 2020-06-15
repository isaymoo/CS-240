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

import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResult> {
    private Fragment frag;
    private Context context;
    private RegisterRequest request;
    private RegisterResult result = new RegisterResult();
    public RegisterTask(Fragment frag, Context context){
        this.frag = frag;
        this.context = context;
    }
    @Override
    protected RegisterResult doInBackground(RegisterRequest... registerRequests) {
        try {
            URL url = new URL("http://" + registerRequests[0].getHost() + ":" + registerRequests[0].getPort() + "/user/register");
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
                result = resultGson.fromJson(reader, RegisterResult.class);
                result.setSuccess(true);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
