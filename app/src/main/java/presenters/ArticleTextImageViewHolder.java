package presenters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yahoo.sports.nytsearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nikhilba on 2/2/17.
 */

public class ArticleTextImageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvHeadlineTextImage)
    TextView tvHeadline;

    @BindView(R.id.ivThumbnail)
    ImageView ivThumbnail;

    public TextView getTvHeadline() {
        return tvHeadline;
    }

    public ImageView getIvThumbnail() {
        return ivThumbnail;
    }

    public ArticleTextImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
