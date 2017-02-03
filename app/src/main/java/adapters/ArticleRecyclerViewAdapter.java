package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yahoo.sports.nytsearch.R;

import java.util.ArrayList;

import models.Article;
import presenters.ArticleTextImageViewHolder;
import presenters.ArticleTextViewHolder;

/**
 * Created by nikhilba on 2/2/17.
 */

public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final ArrayList<Article> mArticles;
    private final Context mContext;
    private static final int TEXT_ONLY = 0;
    private static final int TEXT_AND_IMAGE = 1;

    public ArticleRecyclerViewAdapter(Context context, ArrayList<Article> articles) {
        mContext = context;
        mArticles = articles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return viewType == TEXT_ONLY ?
            new ArticleTextViewHolder(inflater.inflate(R.layout.article_item_text, parent, false)) :
            new ArticleTextImageViewHolder(inflater.inflate(R.layout.article_item_text_image, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article = mArticles.get(position);

        if (getItemViewType(position) == TEXT_ONLY) {
            ArticleTextViewHolder viewHolder = (ArticleTextViewHolder) holder;
            viewHolder.getTvHeadline().setText(article.getHeadline());
        } else {
            ArticleTextImageViewHolder viewHolder = (ArticleTextImageViewHolder) holder;
            viewHolder.getTvHeadline().setText(article.getHeadline());
            viewHolder.getIvThumbnail().setImageResource(0);
            Glide.with(mContext)
                    .load(article.getThumbnailUrl())
                    .into(viewHolder.getIvThumbnail());
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mArticles.get(position).getThumbnail() == null ? TEXT_ONLY : TEXT_AND_IMAGE;
    }
}
