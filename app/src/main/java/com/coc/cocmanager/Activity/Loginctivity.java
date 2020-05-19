package com.coc.cocmanager.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.model.LoginData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created  by ketan 23-03-2020
 */

public class Loginctivity extends AppCompatActivity {

    //region variables
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_gmail_password)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private LoginData loginData;
    private SharedPreferences prefInfo;
    private Context context = Loginctivity.this;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginctivity);
        ButterKnife.bind(this);

        setupEvents();
    }

    /**
     * set up events
     * click events on button clicks
     */
    private void setupEvents() {
        btnLogin.setOnClickListener(View -> {
        doLogin();
        });
    }

    /**
     * login function
     * after entering email and password user can login
     */
    private void doLogin() {
        Map<String, String> headerParams = new HashMap<>();
        Map<String, String> requestBodyParams = new HashMap<>();

        requestBodyParams.put(Constants.Fields.USERNAME, edtEmail.getText().toString());
        requestBodyParams.put(Constants.Fields.PASSWORD, edtPassword.getText().toString());

        HttpService.accessWebServices(
                context,
                ApiUtils.LOGIN_URL,
                requestBodyParams,
                headerParams,
                (response, error, status) -> handleAPIResponse(response, error, status));
    }

    /**
     * handle the api response received from the server
     * check if getFount is true
     * then move to next
     * else show error message
     * @param response
     * @param error
     * @param status
     */
    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                loginData = (LoginData) Utils.parseResponse(response,LoginData.class);
                if(loginData.getFound()){
                    if(loginData.getData().getValid()) {
                        Toast.makeText(context, "Login successfull", Toast.LENGTH_SHORT).show();
                        Intent objIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(objIntent);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
                        writePersonalSharedPreferences(loginData.getData().getToken());
                    }else{
                        Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO: Handle exception
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            // TODO: Handle error
        }

    }

    private void writePersonalSharedPreferences(String token) {
        prefInfo = getSharedPreferences(Utils.PREFERENCE_PERSONAL, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefInfo.edit();
        editor.putString(Constants.Fields.TOKEN,token);
        editor.putBoolean(Constants.Fields.LOGGED,true);
        editor.commit();
    }
}
