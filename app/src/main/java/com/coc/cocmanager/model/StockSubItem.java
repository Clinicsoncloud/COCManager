package com.coc.cocmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StockSubItem implements Parcelable {

    public final String subItemName;
    public final String subItemCount;

    public StockSubItem(String subItemName, String subItemCount) {
        this.subItemName = subItemName;
        this.subItemCount = subItemCount;
    }

    protected StockSubItem(Parcel in) {
        subItemName = in.readString();
        subItemCount = in.readString();
    }

    public static final Creator<StockSubItem> CREATOR = new Creator<StockSubItem>() {
        @Override
        public StockSubItem createFromParcel(Parcel in) {
            return new StockSubItem(in);
        }

        @Override
        public StockSubItem[] newArray(int size) {
            return new StockSubItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subItemName);
        dest.writeString(subItemCount);
    }
}
