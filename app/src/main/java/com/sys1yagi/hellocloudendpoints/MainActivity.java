package com.sys1yagi.hellocloudendpoints;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import com.sys1yagi.hellocloudendpoints.api.myApi.MyApi;
import com.sys1yagi.hellocloudendpoints.api.myApi.model.MyBean;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        new SayHiAsyncTask() {
            @Override
            protected void onPostExecute(String s) {
                text.setText(s);
            }
        }.execute("Cookpad");
    }

    class SayHiAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                MyBean bean = getApiClient().sayHi(params[0]).execute();
                return bean.getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        private MyApi getApiClient() {
            return new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    //for genymotion.
                    //if you use android emulator, you should replace to "10.0.2.2".
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                    .build();
        }
    }

}
