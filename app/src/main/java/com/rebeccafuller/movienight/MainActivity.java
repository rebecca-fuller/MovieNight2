package com.rebeccafuller.movienight;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    String apiKey, movieURL, tvURL, searching;
    int page;

    List<Movies> mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiKey = "7d1a5247e44585fd09d9d201fce8fcb1";
        page = 1;
        searching = "movie";

        movieURL = " https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&page=" + page;
        tvURL = "https://api.themoviedb.org/3/discover/tv?api_key=" + apiKey + "&page=" + page;

        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);

        final MovieRecyclerAdapter rvAdapter = new MovieRecyclerAdapter(mMoviesList);
        rv.setAdapter(rvAdapter);

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(movieURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String jsonData = response.body().string();
                    try {
                        getData(jsonData);
                        rvAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }

    public static List<Movies> getData(String json) throws JSONException{
        JSONObject jsonData = new JSONObject(json);
        {
            List<Movies> data = new ArrayList<>();
            JSONArray array = jsonData.getJSONArray("results");

            for (int i = 0; i < array.length(); i++) {
                Movies movies = new Movies();
                JSONObject j = array.getJSONObject(i);
                movies.date = j.getString("release_date");
                movies.overview = j.getString("overview");
                movies.poster = j.getString("poster_path");
                movies.title = j.getString("title");
                data.add(movies);
            }
            return data;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}
