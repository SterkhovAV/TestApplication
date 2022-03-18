package com.example.testapplication;

import android.content.Context;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.Utils.News_model.Article;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private ArrayList<Article> articles;

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    private int newsItems;

    public NewsAdapter(int newsItems) {
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view=inflater.inflate(layoutIdForListItem, viewGroup,false);

        NewsViewHolder viewHolder = new NewsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  NewsViewHolder newsViewHolder, int i) {
            newsViewHolder.bind(articles.get(i));
    }

    @Override
    public int getItemCount() {
        return newsItems;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView image;
        TextView url;

        public NewsViewHolder(@NonNull  View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_news_item_title);
            description =itemView.findViewById(R.id.tv_news_item_description);
            image = itemView.findViewById(R.id.iv_news_item_pic);
            url = itemView.findViewById(R.id.tv_news_item_url);
        }

        void bind(Article article) {
            title.setText(article.title);
            description.setText(article.description);
            url.setText(article.url);
            Picasso.get()
                    .load(article.urlToImage)
                    .resize(500,400)
                    .centerInside()
                    .into(image);

        }
    }
}
