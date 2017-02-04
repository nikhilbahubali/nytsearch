package models;

import java.io.Serializable;

/**
 * Created by nikhilba on 2/3/17.
 */

public class Settings implements Serializable {
    public enum SortOrder {oldest, newest}

    public String mBeginDate;
    public SortOrder mSortOrder;
    public boolean mIsArts;
    public boolean mIsFashion;
    public boolean mIsSports;

    public Settings() {
        mIsSports = false;
        mIsFashion = false;
        mIsArts = false;
        mBeginDate = "";
        mSortOrder = SortOrder.oldest;
    }
}
