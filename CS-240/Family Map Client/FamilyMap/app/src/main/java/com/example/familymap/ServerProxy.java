package com.example.familymap;

import android.util.EventLog;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.EventResult;
import Result.LoginResult;
import Result.PersonIDResult;
import Result.PersonResult;
import Result.RegisterResult;

public class ServerProxy {

    public LoginResult getLogIn(URL url, LoginRequest request){
        HttpURLConnection conn = null;
        LoginResult result = new LoginResult();
        try {
            conn = (HttpURLConnection) url.openConnection();
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
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return null;
    }
    public RegisterResult getRegister(URL url, RegisterRequest request){
        HttpURLConnection conn = null;
        RegisterResult result = new RegisterResult();
        try {
            conn = (HttpURLConnection) url.openConnection();
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
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return  null;
    }
    public PersonIDResult getPerson(URL url, String authToken){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", authToken);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();
            PersonIDResult result = new PersonIDResult();
            if (connection.getResponseCode() == 200){
                Reader reader = new InputStreamReader(connection.getInputStream());
                Gson resultGson = new Gson();
                result = resultGson.fromJson(reader, PersonIDResult.class);
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage(connection.getResponseMessage());
            }
            return result;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public PersonResult getPeople(URL url, String authToken){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", authToken);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();
            PersonResult result = new PersonResult();
            if (connection.getResponseCode() == 200){
                Reader reader = new InputStreamReader(connection.getInputStream());
                Gson resultGson = new Gson();
                result = resultGson.fromJson(reader, PersonResult.class);
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage(connection.getResponseMessage());
            }
            return result;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public EventResult getEvents(URL url, String authToken){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", authToken);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();
            EventResult result = new EventResult();
            if (connection.getResponseCode() == 200){
                Reader reader = new InputStreamReader(connection.getInputStream());
                Gson resultGson = new Gson();
                result = resultGson.fromJson(reader, EventResult.class);
                result.setSuccess(true);

            } else {
                result.setSuccess(false);
                result.setMessage(connection.getResponseMessage());
            }
            return result;

        } catch (Exception e){
            EventResult result = new EventResult();
            result.setMessage("Error request failed");
            result.setSuccess(false);
            return result;
        }
    }
}
