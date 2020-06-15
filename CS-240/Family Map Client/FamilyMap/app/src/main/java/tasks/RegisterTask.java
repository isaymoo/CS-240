package tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.familymap.ServerProxy;

import java.io.IOException;
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
            ServerProxy serverProxy = new ServerProxy();
            result = serverProxy.getRegister(url, registerRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(RegisterResult myResult){
        if (result.getSuccess()){
            String url1 = new String("http://" + request.getHost() + ":" + request.getPort() + "/person/" + myResult.getPersonID());
            String url2 = new String("http://" + request.getHost() + ":" + request.getPort() + "/person/");
            String url3 = new String("http://" + request.getHost() + ":" + request.getPort() + "/event/");
            GetDataTask data = new GetDataTask(frag, context, null, null, request, result);
            data.execute(result.getAuthToken(), url1, url2, url3);
        }
        else{
            Toast.makeText(frag.getContext(), "Register Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
