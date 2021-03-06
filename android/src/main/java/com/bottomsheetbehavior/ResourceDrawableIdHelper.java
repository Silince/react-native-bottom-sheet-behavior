package com.bottomsheetbehavior;

import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * Helper class for obtaining information about local images.
 * https://github.com/facebook/react-native/blob/master/ReactAndroid/src/main/java/com/facebook/react/views/imagehelper/ResourceDrawableIdHelper.java
 */
public class ResourceDrawableIdHelper {

    private Map<String, Integer> mResourceDrawableIdMap;

    private static final String LOCAL_RESOURCE_SCHEME = "res";
    private static ResourceDrawableIdHelper sResourceDrawableIdHelper;

    private ResourceDrawableIdHelper() {
        mResourceDrawableIdMap = new HashMap<String, Integer>();
    }

    public static ResourceDrawableIdHelper getInstance() {
        if (sResourceDrawableIdHelper == null) {
            sResourceDrawableIdHelper = new ResourceDrawableIdHelper();
        }
        return sResourceDrawableIdHelper;
    }

    public void clear() {
        mResourceDrawableIdMap.clear();
    }

    public int getResourceDrawableId(Context context, @Nullable String name) {
        if (name == null || name.isEmpty()) {
            return 0;
        }
        name = name.toLowerCase().replace("-", "_");
        if (mResourceDrawableIdMap.containsKey(name)) {
            return mResourceDrawableIdMap.get(name);
        }
        int id = context.getResources().getIdentifier(
            name,
            "drawable",
            context.getPackageName());
        mResourceDrawableIdMap.put(name, id);
        return id;
    }

    public @Nullable Drawable getResourceDrawable(Context context, @Nullable String name) {
        int resId = getResourceDrawableId(context, name);
        return resId > 0 ? context.getResources().getDrawable(resId) : null;
    }

    public Uri getResourceDrawableUri(Context context, @Nullable String name) {
        int resId = getResourceDrawableId(context, name);
        return resId > 0 ? new Uri.Builder()
            .scheme(LOCAL_RESOURCE_SCHEME)
            .path(String.valueOf(resId))
            .build() : Uri.EMPTY;
    }
}
