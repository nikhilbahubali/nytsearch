package api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nikhilba on 2/2/17.
 */
public class ResponseDocument {
    @SerializedName("web_url")
    public String mWebUrl;

    @SerializedName("headline")
    public ResponseHeadline mHeadline;

    @SerializedName("multimedia")
    public List<ResponseMultiMedia> mMultiMediaList;
}
