package net.kentandcds4r.chuckerview;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;

class RequestTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... uri) {
        String responseString = "";


        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}
