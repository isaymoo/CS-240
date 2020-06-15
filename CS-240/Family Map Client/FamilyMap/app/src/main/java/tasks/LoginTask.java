package tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.familymap.ServerProxy;

import java.io.IOException;
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
            ServerProxy serverProxy = new ServerProxy();

            result = serverProxy.getLogIn(url, req[0]);
            return result;


        } catch (IOException e) {
            result.setMessage("Error invalid URL");
            result.setSuccess(false);
            return result;
        }
    }

    protected void onPostExecute(LoginResult myResult){
        if (result.getSuccess()){
            String url1 = new String("http://" + request.getHost() + ":" + request.getPort() + "/person/" + myResult.getPersonID());
            String url2 = new String("http://" + request.getHost() + ":" + request.getPort() + "/person/");
            String url3 = new String("http://" + request.getHost() + ":" + request.getPort() + "/event/");
            GetDataTask data = new GetDataTask(frag, context, request, result, null, null);
            data.execute(result.getAuthToken(), url1, url2, url3);
        }
        else{
            Toast.makeText(frag.getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }


}
