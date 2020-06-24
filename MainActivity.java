package com.example.getrecentphotos;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    private EndlessRecyclerViewScrollListener scrollListener;
    ProgressDialog prgDialog;
    RecyclerView newsRec;
    List<photoClass>  data;
    photoAdapter adp;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Flickr_GetRecent");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        newsRec = findViewById(R.id.newrecycler);
        newsRec.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();
        adp = new photoAdapter(data, this, new photoAdapter.itemClickLister() {
            @Override
            public void itemclicked(photoClass selected) {

                Intent i = new Intent(MainActivity.this , photo_detail.class);
                i.putExtra("selectednew" ,  selected);
                startActivity(i);

            }
        });
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //Log.i("DEV","END OF THE PAGE!!!!");
                loadNextDataFromApi(page);




            }
        };
        newsRec.addOnScrollListener(scrollListener);





        //newsRec.setLayoutManager(new LinearLayoutManager(this));
        newsRec.setAdapter(adp);

        NewsTask tsk = new NewsTask();
        tsk.execute("https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=e0da70aaa4b9cfab8e6c7438e4ace034&per_page=20&format=json&nojsoncallback=1");





    }

    public void loadNextDataFromApi(int offset) {

        NewsTask tsk = new NewsTask();
        Log.i("DEV", String.valueOf(offset));
        tsk.execute("https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=e0da70aaa4b9cfab8e6c7438e4ace034&per_page=20&format=json&nojsoncallback=1");
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    class NewsTask  extends AsyncTask<String , Void , String>{

        @Override
        protected void onPreExecute() {
            prgDialog = new ProgressDialog(MainActivity.this);
            prgDialog.setTitle("Loading");
            prgDialog.setMessage("Please wait...");
            prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgDialog.show();
            Log.i("DEV" , "im in back0");


        }

        @Override
        protected String doInBackground(String... strings) {
            String urlStr = strings[0];
            StringBuffer buffer = new StringBuffer();
            Log.i("DEV" , urlStr);
            try {
                URL url = new URL(urlStr);

                HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
                Log.i("DEV" , "im in connection");

                InputStreamReader newreader = (new InputStreamReader(conn.getInputStream()));
                BufferedReader reader = new BufferedReader(newreader);


                Log.i("DEV" , "im in back2");
                String line = "";
                while ((line = reader.readLine()) != null) {

                    buffer.append(line);


                }
                Log.i("DEV", buffer.toString());
                reader.close();











            } catch (IOException e) {
                e.printStackTrace();

            }


            return buffer.toString();

        }

        @Override
        protected void onPostExecute(String s) {
            //data.clear();
            try {
                JSONObject obj = new JSONObject(s);

                if(obj.getString("stat").equals("ok"))
                {
                    JSONArray arr = obj.getJSONObject("photos").getJSONArray("photo");
                    Log.i("DEV", arr.toString());
                    for (int i = 0; i < arr.length(); i++)
                    {
                        JSONObject current = (JSONObject) arr .get(i);



                        photoClass item = new photoClass(current.getString("id") , current.getString("server") , current.getInt("farm") , current.getString("secret") );
                        data.add(item);



                    }






                }
                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("Cant Download Data");
                    builder1.setTitle("ERROR");

                    builder1.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog downloadalert = builder1.create();
                    downloadalert.show();
                }

                adp.notifyDataSetChanged();
                prgDialog.dismiss();
                scrollListener.resetState();



            } catch (JSONException e) {
                Log.e("DEV" , e.getMessage());
            }
        }


    }




}
