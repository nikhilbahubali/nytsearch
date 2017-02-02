package api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import models.Article;

/**
 * Created by nikhilba on 2/2/17.
 */
public class ArticleResponse {
    @SerializedName("response")
    private ResponseObject mResponseObject;

    public List<Article> getArticles() {
        List<Article> articles = new ArrayList<>(mResponseObject.mResponseDocuments.size());

        for (ResponseDocument doc: mResponseObject.mResponseDocuments) {
            Article article = new Article();
            article.setHeadline(doc.mHeadline.mMain);
            article.setWebUrl(doc.mWebUrl);

            if (doc.mMultiMediaList != null && !doc.mMultiMediaList.isEmpty()) {
                article.setThumbnail(doc.mMultiMediaList.get(0).mUrl);
            }

            articles.add(article);
        }

        return articles;
    }
}
