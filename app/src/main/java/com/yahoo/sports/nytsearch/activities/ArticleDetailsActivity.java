package com.yahoo.sports.nytsearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yahoo.sports.nytsearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Article;

public class ArticleDetailsActivity extends AppCompatActivity {
    @BindView(R.id.wvArticleDetails)
    WebView wvArticleDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Article article = intent.getParcelableExtra("article");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbArticledetails);
        toolbar.setTitle("Article Details");
        setSupportActionBar(toolbar);

        wvArticleDetails.setWebViewClient(new WebViewClient());
        wvArticleDetails.loadUrl(article.getWebUrl());
    }
}
