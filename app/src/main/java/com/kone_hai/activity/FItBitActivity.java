package com.kone_hai.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kone_hai.R;

import java.util.HashSet;
import java.util.Set;

public class FItBitActivity extends AppCompatActivity {

    String token = null;
    public static final String CONFIGURATION_VERSION = "CONFIGURATION_VERSION";
    public static final String AUTHENTICATION_RESULT_KEY = "AUTHENTICATION_RESULT_KEY";
    private static final String CLIENT_CREDENTIALS_KEY = "CLIENT_CREDENTIALS_KEY";
    private static final String EXPIRES_IN_KEY = "EXPIRES_IN_KEY";
    private static final String SCOPES_KEY = "SCOPES_KEY";

    private Set<String> scope = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_bit);

        String url = "https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=2285R9&redirect_uri=http://192.175.5.110:8080/auth/user&scope=activity%20nutrition%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800";


        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivityForResult(intent2, RESULT_OK);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith("http://192.175.5.110:8080/auth/user")) {
            String code = uri.getQueryParameter("code");

            Log.d("no code", "no code");

            if (code != null) {
                Log.d("code", code);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent resultIntent = new Intent();
        Log.d("extra", data.getExtras().getString("access_token"));
        resultIntent.putExtra(AUTHENTICATION_RESULT_KEY, data.getExtras().getString("access_token"));
        resultIntent.putExtra(CONFIGURATION_VERSION, getIntent().getIntExtra(CONFIGURATION_VERSION, 0));
        setResult(Activity.RESULT_OK, resultIntent);
        finishActivity(requestCode);
    }
}
