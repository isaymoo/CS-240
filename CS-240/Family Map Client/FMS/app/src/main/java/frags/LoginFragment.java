package frags;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.fms.R;

import Request.LoginRequest;
import Request.RegisterRequest;
import tasks.LoginTask;
import tasks.RegisterTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private EditText hostField;
    private EditText portField;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioGroup gender;
    private Button signIn;
    private Button register;
    private String host;
    private String port;
    private static Context context;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param context Parameter 1.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(Context context) {
        LoginFragment.context = context;
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginRequest = new LoginRequest();
        registerRequest = new RegisterRequest();
    }
    private LoginTask loginCreator(){
        LoginTask task = new LoginTask(this, context);
        return task;
    }
    private RegisterTask registerCreator(){
        RegisterTask task = new RegisterTask(this, context);
        return task;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        registerRequest.setGender("m");
        signIn = view.findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  LoginTask task = loginCreator();
                  task.execute(loginRequest);
              }
            }
        );

        register = view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RegisterTask task = registerCreator();
                task.execute(registerRequest);
            }
        });

        hostField = view.findViewById(R.id.hostField);
        loginRequest.setHost(hostField.toString());
        registerRequest.setHost(hostField.toString());

        portField = view.findViewById(R.id.portField);
        loginRequest.setPort(portField.toString());
        registerRequest.setPort(portField.toString());

        username = view.findViewById(R.id.username);
        loginRequest.setUserName(username.toString());
        registerRequest.setUserName(username.toString());

        password = view.findViewById(R.id.password);
        loginRequest.setPassword(password.toString());
        registerRequest.setPassword(password.toString());

        firstName = view.findViewById(R.id.firstName);
        registerRequest.setFirstName(firstName.toString());

        lastName = view.findViewById(R.id.lastName);
        registerRequest.setLastName(lastName.toString());

        email = view.findViewById(R.id.email);
        registerRequest.setEmail(email.toString());

        gender = view.findViewById(R.id.gender);
        if(gender.getCheckedRadioButtonId() == R.id.male){
            registerRequest.setGender("m");
        }
        else registerRequest.setGender("f");

        return view;
    }
}