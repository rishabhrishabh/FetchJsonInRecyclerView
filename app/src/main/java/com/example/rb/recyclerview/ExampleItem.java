package com.example.rb.recyclerview;

import android.support.annotation.NonNull;

public class ExampleItem implements Comparable<ExampleItem> {
    private String mImageUrl;
    private String name;
    private int verify;
    private int priority;
    public ExampleItem(String imageUrl, String nam, int veri,int pri) {
        mImageUrl = imageUrl;
        name =nam;
        verify = veri;
        priority=pri;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return name;
    }

    public int getVerify() {
        return verify;
    }

    public int getPriority() {
        return priority;
    }
    @Override
    public int compareTo(@NonNull ExampleItem o) {
        if(this.priority<o.priority)
            return 1;
        else
            return -1;
    }
}
