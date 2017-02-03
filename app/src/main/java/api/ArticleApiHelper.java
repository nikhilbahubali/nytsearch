package api;

import android.util.Log;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikhilba on 2/2/17.
 */

public class ArticleApiHelper {
    public static final String BASE_URL = "https://api.nytimes.com/";
    private ArticleService mArticleService;
    private Retrofit mRetrofit;
    private String mQuery;

    public ArticleApiHelper(String query) {
        mQuery = query;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            HttpUrl url = request.url()
                    .newBuilder()
                    .addQueryParameter("api-key", "e5b624cc2a9d4af084913ffed1a8fa67")
                    .addQueryParameter("fl", "web_url,headline,multimedia")
                    .addQueryParameter("q", mQuery)
                    .build();
            request = request.newBuilder().url(url).build();
            Log.d("OKHttp", request.toString());
            return chain.proceed(request);
        });

        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        mArticleService = mRetrofit.create(ArticleService.class);
    }

    public ArticleService getArticleService() {
        return mArticleService;
    }
}
