package presenters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yahoo.sports.nytsearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nikhilba on 2/2/17.
 */

public class ArticleTextViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvHeadlineText)
    TextView tvHeadline;

    public TextView getTvHeadline() {
        return tvHeadline;
    }

    public ArticleTextViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
