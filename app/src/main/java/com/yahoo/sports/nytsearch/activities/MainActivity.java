package com.yahoo.sports.nytsearch.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yahoo.sports.nytsearch.R;

import java.util.ArrayList;
import java.util.List;

import adapters.ArticleRecyclerViewAdapter;
import api.ArticleApiHelper;
import api.ArticleResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rvArticleItems)
    RecyclerView rvArticles;

    private ArticleRecyclerViewAdapter mArticleRecyclerViewAdapter;
    private ArrayList<Article> mArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mArticles = new ArrayList<>();
        mArticleRecyclerViewAdapter = new ArticleRecyclerViewAdapter(this, mArticles);
        rvArticles.setAdapter(mArticleRecyclerViewAdapter);
        //rvArticles.setLayoutManager(new LinearLayoutManager(this));
        rvArticles.setLayoutManager(new StaggeredGridLayoutManager(3, 1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show search menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_article_list, menu);

        // handle search actions
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<ArticleResponse> articlesRequest = ArticleApiHelper.getinstance(query).getArticleService().getArticles();
                articlesRequest.enqueue(new Callback<ArticleResponse>() {
                    @Override
                    public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                        List<Article> articles = response.body().getArticles();
                        populateRecyclerView(articles);
                    }

                    @Override
                    public void onFailure(Call<ArticleResponse> call, Throwable t) {
                        Log.d("Failed", t.getMessage());
                    }
                });

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void populateRecyclerView(List<Article> articles) {
        runOnUiThread(() -> {
            mArticles.clear();
            mArticles.addAll(articles);
            mArticleRecyclerViewAdapter.notifyDataSetChanged();
        });
    }
}
