package com.forkboard.forkboard;

import android.content.Context;

/**
 * Created by Kyle on 6/16/2016.
 */
public interface DataHandler {
    public void load();
    public void save(Context context);
    public void update(Object... params);
}
