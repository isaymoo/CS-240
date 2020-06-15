package tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.familymap.DataCache;
import com.example.familymap.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.EventResult;
import Result.LoginResult;
import Result.PersonIDResult;
import Result.PersonResult;
import Result.RegisterResult;


public class GetDataTask extends AsyncTask<String, Void, Boolean> {

    private Fragment frag;
    private Context context;
    private LoginRequest loginRequest;
    private LoginResult loginResult;
    private RegisterResult registerResult;
    private RegisterRequest registerRequest;
    private PersonIDResult personResult;
    private PersonResult personsResult;
    private EventResult eventResult;
    public GetDataTask(Fragment frag, Context context, LoginRequest loginRequest, LoginResult loginResult, RegisterRequest registerRequest ,RegisterResult registerResult){
        this.frag = frag;
        this.context = context;
        this.loginRequest = loginRequest;
        this.loginResult = loginResult;
        this.registerRequest = registerRequest;
        this.registerResult = registerResult;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        DataCache cache = DataCache.getCache();
        String authToken = strings[0];
        cache.setAuthToken(authToken);
        ServerProxy serverProxy = new ServerProxy();
        try {
            URL url1 = new URL(strings[1]);
            personResult = serverProxy.getPerson(url1, authToken);
            URL url2 = new URL(strings[2]);
            personsResult = serverProxy.getPeople(url2, authToken);
            URL url3 = new URL(strings[3]);
            eventResult = serverProxy.getEvents(url3, authToken);

        } catch (MalformedURLException e) {
            personResult.setMessage("Error invalid URL");
            personsResult.setMessage("Error invalid URL");
            eventResult.setMessage("Error invalid URL");
        }
        return null;
    }
    protected void onPostExecute(Boolean bool) {
        if (bool){
            DataCache cache = DataCache.getCache();
            cache.setAllEvents(eventResult.getEvents());
            cache.setAllPersons(personsResult.getPersons());
            Toast.makeText(context, "Login Success!" + "\n" + personResult.getFirstName() + "\n" + personResult.getLastName(), Toast.LENGTH_SHORT);
        }

    }
}
