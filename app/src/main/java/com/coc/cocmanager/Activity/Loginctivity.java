package com.coc.cocmanager.Activity;

import android.content.Context;
import android.content.Intent;
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
    private Context context = Loginctivity.this;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginctivity);
        ButterKnife.bind(this);

        setupEvents();
        initializeData();
    }

    private void initializeData() {

    }

    /**
     * set up events
     * click events on button clicks
     */
    private void setupEvents() {
        btnLogin.setOnClickListener(View -> {
//        doLogin();
            context.startActivity(new Intent(context, MainActivity.class));
        });
    }

    /**
     * login function
     * after entering email and password user can login
     */
    private void doLogin() {
        Map<String, String> headerParams = new HashMap<>();
        Map<String, String> requestBodyParams = new HashMap<>();

        requestBodyParams.put(Constants.Fields.EMAIL, edtEmail.getText().toString());
        requestBodyParams.put(Constants.Fields.PASSWORD, edtPassword.getText().toString());

        HttpService.accessWebServices(
                context,
                ApiUtils.LOGIN_URL,
                requestBodyParams,
                headerParams,
                (response, error, status) -> handleAPIResponse(response, error, status));
    }

    private void handleAPIResponse(String response, VolleyError error, String status) {
        Log.e("status_log", " : " + status);
        if (status.equals("response")) {
            try {
                Intent objIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(objIntent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            } catch (Exception e) {
                // TODO: Handle exception
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            // TODO: Handle error
        }

    }

    private void writePersonalSharedPreferences(JSONObject jsonResponse) throws JSONException {
//        SharedPreferences.Editor editor = sharedPreferencesPersonal.edit();

//            editor.putString(Constants.Fields.ID, jsonResponse.getJSONObject("data").getJSONArray("patient").getJSONObject(0).getString(Constants.Fields.ID));
//        editor.commit();
    }
}
