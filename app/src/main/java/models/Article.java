package models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nikhilba on 2/2/17.
 */

public class Article implements Parcelable {
    private String mHeadline;
    private String mThumbnail;
    private String mWebUrl;

    public String getHeadline() {
        return mHeadline;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public void setHeadline(String headline) {
        this.mHeadline = headline;
    }

    public void setThumbnail(String thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public void setWebUrl(String webUrl) {
        this.mWebUrl = webUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mHeadline);
        dest.writeString(this.mThumbnail);
        dest.writeString(this.mWebUrl);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.mHeadline = in.readString();
        this.mThumbnail = in.readString();
        this.mWebUrl = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
