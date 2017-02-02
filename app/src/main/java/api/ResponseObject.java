package api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nikhilba on 2/2/17.
 */

public class ResponseObject {
    @SerializedName("docs")
    public List<ResponseDocument> mResponseDocuments;
}
