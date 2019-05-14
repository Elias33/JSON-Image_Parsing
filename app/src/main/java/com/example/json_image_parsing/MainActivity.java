package com.example.json_image_parsing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)

                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start




        l1= findViewById(R.id.l1);
        JsonTask jsonTask= new JsonTask();
        jsonTask.execute();



    }


    public class JsonTask extends AsyncTask <String,String,List<Model>>{

        @Override
        protected List<Model> doInBackground(String... strings) {



            BufferedReader bufferedReader= null;
            HttpURLConnection httpURLConnection=null;
            String jsonFile;
            try {
                URL url= new URL("https://api.myjson.com/bins/ay1ei");
                httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream= httpURLConnection.getInputStream();
                bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer= new StringBuffer();
                String line=null;
                while ((line=bufferedReader.readLine())!=null){
                    stringBuffer.append(line);

                }

                jsonFile= stringBuffer.toString();
                JSONObject JsonObject= new JSONObject(jsonFile);
                JSONArray jsonArray= JsonObject.getJSONArray("studentinfo");
                List<Model>carModel= new ArrayList<>();

                for (int i=0;i<jsonArray.length();i++){


                    JSONObject ArrayObject= jsonArray.getJSONObject(i);// First create object of the node
                    Model model= new Model();
                    model.setName(ArrayObject.getString("name"));
                    model.setImage(ArrayObject.getString("img"));
                    carModel.add(model);

                }

                return carModel;






            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }


        @Override
        protected void onPostExecute(List<Model> s) {
            super.onPostExecute(s);
            CustomAdapter customAdapter= new CustomAdapter(getApplicationContext(),R.layout.sample,s);
            l1.setAdapter(customAdapter);




        }
    }
}
