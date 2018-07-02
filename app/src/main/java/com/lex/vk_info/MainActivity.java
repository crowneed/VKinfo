package com.lex.vk_info;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static com.lex.vk_info.utils.NetworkUtils.generateURL;
import static com.lex.vk_info.utils.NetworkUtils.getResponceFromURL;

import com.lex.vk_info.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText et_search;
    Button b_search;
    TextView tv_result;

    class VKQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponceFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            String first_name = null;
            String last_name = null;

            try {
                JSONObject jsonRespnce = new JSONObject(response);
                JSONArray jsonArray = jsonRespnce.getJSONArray("response");
                JSONObject jsonUserInfo = jsonArray.getJSONObject(0);

                first_name = jsonUserInfo.getString("first_name");
                last_name = jsonUserInfo.getString("last_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            tv_result.setText("Имя: " + first_name + "\nФамилия: " + last_name);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_search = findViewById(R.id.et_search_field);
        b_search = findViewById(R.id.b_search_vk);
        tv_result = findViewById(R.id.tv_result);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL genaratedURL = generateURL(et_search.getText().toString());

                new VKQueryTask().execute(genaratedURL);
            }
        };

        b_search.setOnClickListener(ocl);
    }
}
