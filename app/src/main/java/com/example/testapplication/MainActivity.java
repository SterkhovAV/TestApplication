package com.example.testapplication;

import static com.example.testapplication.Utils.NetworkUtils.ResponseParse;
import static com.example.testapplication.Utils.NetworkUtils.generateURL;
import static com.example.testapplication.Utils.NetworkUtils.getResponse;


import android.os.AsyncTask;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.Utils.News_model.News;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText searchField;
    private TextView result;
    private RecyclerView newsList;

    class SearchQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response=null;
            try {
                response=getResponse(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            News news = ResponseParse(response);

            //Согласно документации https://newsapi.org/ по дефолту 20 результатов на запрос (страницу).
            //Думаю вплоне уместно будет ограничиться этим числом в рамках тестового задания.
            NewsAdapter newsAdapter;
            if (news.totalResults<20) newsAdapter =new NewsAdapter(news.totalResults);
            else newsAdapter =new NewsAdapter(20);

            newsAdapter.setArticles(news.articles);
            newsList.setAdapter(newsAdapter);
            result.setText("Found "+news.totalResults+" results");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        searchField=findViewById(R.id.et_search_field);
        Button searchButton = findViewById(R.id.b_search_button1);
        result=findViewById(R.id.tv_news_result);
        newsList=findViewById(R.id.rv_news);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        newsList.setLayoutManager(layoutManager);

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL genURL=generateURL(searchField.getText().toString());
                new SearchQueryTask().execute(genURL);
            }
        };
        searchButton.setOnClickListener(onClickListener);
    }
}