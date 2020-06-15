package frags;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


import com.example.familymap.R;

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
        signIn.setEnabled(false);
        signIn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  LoginTask task = loginCreator();
                  task.execute(loginRequest);
              }
            }
        );

        register = view.findViewById(R.id.register);
        register.setEnabled(false);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RegisterTask task = registerCreator();
                task.execute(registerRequest);
            }
        });

        hostField = (EditText) view.findViewById(R.id.hostField);
        hostField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                host = s.toString();
                loginRequest.setHost(s.toString());
                registerRequest.setHost(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hostField.getText().toString().equals("") && !portField.getText().toString().equals("") &&
                        !username.getText().toString().equals("") && !password.getText().toString().equals("")){
                    signIn.setEnabled(true);
                    if (!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") &&
                            !email.getText().toString().equals("")){
                        register.setEnabled(true);
                    }
                }
            }
        });


        portField = (EditText) view.findViewById(R.id.portField);
        portField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                port = s.toString();
                loginRequest.setPort(s.toString());
                registerRequest.setPort(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hostField.getText().toString().equals("") && !portField.getText().toString().equals("") &&
                        !username.getText().toString().equals("") && !password.getText().toString().equals("")){
                    signIn.setEnabled(true);
                    if (!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") &&
                            !email.getText().toString().equals("")){
                        register.setEnabled(true);
                    }
                }
            }
        });

        username = (EditText) view.findViewById(R.id.username);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginRequest.setUserName(s.toString());
                registerRequest.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hostField.getText().toString().equals("") && !portField.getText().toString().equals("") &&
                        !username.getText().toString().equals("") && !password.getText().toString().equals("")){
                    signIn.setEnabled(true);
                    if (!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") &&
                            !email.getText().toString().equals("")){
                        register.setEnabled(true);
                    }
                }
            }
        });

        password = (EditText) view.findViewById(R.id.password);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginRequest.setPassword(s.toString());
                registerRequest.setPassword(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hostField.getText().toString().equals("") && !portField.getText().toString().equals("") &&
                        !username.getText().toString().equals("") && !password.getText().toString().equals("")){
                    signIn.setEnabled(true);
                    if (!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") &&
                            !email.getText().toString().equals("")){
                        register.setEnabled(true);
                    }
                }
            }
        });

        firstName = (EditText) view.findViewById(R.id.firstName);
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerRequest.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hostField.getText().toString().equals("") && !portField.getText().toString().equals("") &&
                        !username.getText().toString().equals("") && !password.getText().toString().equals("")){
                    if (!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") &&
                            !email.getText().toString().equals("")){
                        register.setEnabled(true);
                    }
                }
            }
        });


        lastName = (EditText) view.findViewById(R.id.lastName);
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerRequest.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hostField.getText().toString().equals("") && !portField.getText().toString().equals("") &&
                        !username.getText().toString().equals("") && !password.getText().toString().equals("")){
                    if (!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") &&
                            !email.getText().toString().equals("")){
                        register.setEnabled(true);
                    }
                }
            }
        });

        email = (EditText) view.findViewById(R.id.email);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerRequest.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hostField.getText().toString().equals("") && !portField.getText().toString().equals("") &&
                        !username.getText().toString().equals("") && !password.getText().toString().equals("")){
                    if (!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") &&
                            !email.getText().toString().equals("")){
                        register.setEnabled(true);
                    }
                }
            }
        });

        gender = view.findViewById(R.id.gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(gender.getCheckedRadioButtonId() == R.id.female){
                    registerRequest.setGender("f");
                }
                else registerRequest.setGender("m");
            }
        });
        if (!hostField.getText().toString().equals("") && !portField.getText().toString().equals("") &&
                !username.getText().toString().equals("") && !password.getText().toString().equals("")){
            signIn.setEnabled(true);
            if (!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") &&
                    !email.getText().toString().equals("")){
                register.setEnabled(true);
            }
        }

        return view;
    }
}