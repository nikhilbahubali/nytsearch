package api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nikhilba on 2/2/17.
 */

public interface ArticleService {
    @GET("svc/search/v2/articlesearch.json")
    Call<ArticleResponse> getArticles();
}
