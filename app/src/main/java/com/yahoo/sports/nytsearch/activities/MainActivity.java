package com.yahoo.sports.nytsearch.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
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
import api.NetworkHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Article;
import models.Settings;
import presenters.EndlessRecyclerViewScrollListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SettingsDialog.SettingsDialogListener {
    @BindView(R.id.rvArticleItems)
    RecyclerView rvArticles;

    private ArticleRecyclerViewAdapter mArticleRecyclerViewAdapter;
    private ArrayList<Article> mArticles;
    private String mQueryTerm;
    private EndlessRecyclerViewScrollListener mScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CheckConnectivty();

        // set up recycler view
        mArticles = new ArrayList<>();
        mArticleRecyclerViewAdapter = new ArticleRecyclerViewAdapter(this, mArticles);
        rvArticles.setAdapter(mArticleRecyclerViewAdapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, 1);
        rvArticles.setLayoutManager(layoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        // Triggered only when new data needs to be appended to the list
        // Add whatever code is needed to append new items to the bottom of the list
        mScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                fetchNextArticleSet(mQueryTerm, page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvArticles.addOnScrollListener(mScrollListener);
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
        // show search menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_article_list, menu);

        // handle search actions
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (CheckConnectivty()) {
                    mQueryTerm = query;
                    fetchNextArticleSet(query);

                    // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                    // see https://code.google.com/p/android/issues/detail?id=24599
                    searchView.clearFocus();

                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem settings = menu.findItem(R.id.settings);
        settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SettingsDialog dialog = SettingsDialog.newInstance(getApplicationContext());
                Bundle args = dialog.getArguments();
                Settings settings = new Settings();
                args.putSerializable("settings", settings);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "settingsdialog");
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void fetchNextArticleSet(String query) {
        fetchNextArticleSet(query, 0);
    }

    private void fetchNextArticleSet(String query, int page) {
        Call<ArticleResponse> articlesRequest = new ArticleApiHelper(query, page).getArticleService().getArticles();
        articlesRequest.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                List<Article> articles = response.body().getArticles();
                populateRecyclerView(articles, page);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.d("Failed", t.getMessage());
            }
        });
    }

    private void populateRecyclerView(List<Article> articles, int page) {
        runOnUiThread(() -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (page == 0) {
                    mArticles.clear();
                }
                mArticles.addAll(articles);
                if (page == 0) {
                    mScrollListener.resetState();
                    mArticleRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    mArticleRecyclerViewAdapter.notifyItemRangeInserted(page * 10, articles.size());
                }
            }, 2000);
        });
    }

    @Override
    public void OnFinishedSettingsEdit(Bundle args) {
        Settings settings = (Settings) args.getSerializable("settings");
        Log.d("Nikhil", settings.mBeginDate);
    }
}
