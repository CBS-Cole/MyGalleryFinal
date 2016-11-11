package com.example.cole.mygalleryfinal.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

public class ImageCacheUtil {

    private static ImageCacheUtil mImageCacheHandler = new ImageCacheUtil();

    private static DisplayImageOptions defaultNoDefaultDisplayOptions = null;
    private static DisplayImageOptions defaultDisplayOptions = null;
    private static DisplayImageOptions defaultScaleTypeDisplayOptions = null;

    private boolean mIsInitilize = false;
    private Context mContext = null;

    private ImageCacheUtil() {
        mIsInitilize = false;
        mContext = null;
    }

    public static ImageCacheUtil getInstance() {
        return mImageCacheHandler;
    }

    public void initImageCacheData(Context context) {
        if (mIsInitilize != true) {
            mContext = context;

//            String cachePath = context.getApplicationContext().getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath() + File.separator + "cache";
//            File cacheDir = StorageUtils.getCacheDirectory(context);

            defaultNoDefaultDisplayOptions = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new FadeInBitmapDisplayer(150))
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .resetViewBeforeLoading(true)
                    .build();

            defaultDisplayOptions = new DisplayImageOptions.Builder()
                    // TODO: 16/8/1  这里需要添加默认的图片
//                    .showImageOnLoading(R.drawable.default_avatar) // resource or drawable
//                    .showImageForEmptyUri(R.drawable.default_avatar) // resource or drawable
//                    .showImageOnFail(R.drawable.default_avatar) // resource or drawable
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();

            defaultScaleTypeDisplayOptions = new DisplayImageOptions.Builder()
                    // TODO: 16/8/1  这里需要添加默认的图片
//                    .showImageOnLoading(R.drawable.default_avatar) // resource or drawable
//                    .showImageForEmptyUri(R.drawable.default_avatar) // resource or drawable
//                    .showImageOnFail(R.drawable.default_avatar) // resource or drawable
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.NONE_SAFE)
                    .build();

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                    .defaultDisplayImageOptions(defaultOptions)
                    .diskCacheExtraOptions(480, 800, null)
                    .memoryCacheExtraOptions(480, 800)
//                    .diskCacheExtraOptions(240, 400, null)
                    .denyCacheImageMultipleSizesInMemory()
                    .threadPoolSize(5).threadPriority(Thread.NORM_PRIORITY)
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .diskCacheFileCount(1000)
                    .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                    .memoryCacheSize(10 * 1024 * 1024)
                    .diskCacheSize(100 * 1024 * 1024)
                    .memoryCache(new WeakMemoryCache())
                    .build();

            ImageLoader.getInstance().init(config);
            mIsInitilize = true;
        }
    }

    public void saveImageCacheData() {
        if (mIsInitilize == true) {
            mContext = null;
            mIsInitilize = false;
        }
    }

    public void changeUrlToBitmap(String url, DisplayImageOptions config, ImageLoadingListener listener) {
        ImageLoader.getInstance().loadImage(url, config, listener);
    }

    public void displayImage(ImageView imageView, String url) {
        ImageLoader.getInstance().displayImage(url, imageView, defaultNoDefaultDisplayOptions);
    }

    public void displayImage(ImageView imageView, String url, DisplayImageOptions config) {
        ImageLoader.getInstance().displayImage(url, imageView, config);
    }

    public void displayImage(ImageView imageView, String url, DisplayImageOptions config, ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(url, imageView, config, listener);
    }

    public boolean getCache(String url) {
        Bitmap bitmap = ImageLoader.getInstance().getMemoryCache().get(url);
        if (bitmap == null) {
            File file = ImageLoader.getInstance().getDiscCache().get(url);
            if(file==null){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }

    public DisplayImageOptions getDisplayConfig(int loadingResid, int emptyResdi, int errorResid) {
        // TODO: 16/8/1  这里需要添加默认的图片
//        if (loadingResid == R.drawable.default_avatar) {
//            return defaultDisplayOptions;
//        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadingResid) // resource or drawable
                .showImageForEmptyUri(emptyResdi) // resource or drawable
                .showImageOnFail(errorResid) // resource or drawable
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        return options;
    }

    public DisplayImageOptions getNoneScaleDisplayConfig() {
        return defaultScaleTypeDisplayOptions;
    }
}
