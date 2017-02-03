package com.yahoo.sports.nytsearch.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yahoo.sports.nytsearch.R;

import api.NetworkHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Article;

public class ArticleDetailsActivity extends AppCompatActivity {
    @BindView(R.id.wvArticleDetails)
    WebView wvArticleDetails;

    private Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        ButterKnife.bind(this);

        if (CheckConnectivty()) {
            Intent intent = getIntent();
            mArticle = intent.getParcelableExtra("article");

            Toolbar toolbar = (Toolbar) findViewById(R.id.tbArticledetails);
            toolbar.setTitle("Article Details");
            setSupportActionBar(toolbar);

            wvArticleDetails.setWebViewClient(new WebViewClient());
            wvArticleDetails.loadUrl(mArticle.getWebUrl());
        }
    }

    private boolean CheckConnectivty() {
        if (!NetworkHelper.isOnline(this)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.OfflineMessage)
                    .setPositiveButton(R.string.OkText, (dialog, which) -> {
                    })
                    .create();
            alertDialog.show();
            return false;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_article_details, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        // pass in the URL currently being used by the WebView
        shareIntent.putExtra(Intent.EXTRA_TEXT, mArticle.getWebUrl());

        miShare.setShareIntent(shareIntent);
        return super.onCreateOptionsMenu(menu);
    }
}
