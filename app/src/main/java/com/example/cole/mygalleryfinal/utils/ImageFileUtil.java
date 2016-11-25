package com.example.cole.mygalleryfinal.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.cole.mygalleryfinal.R;
import com.example.cole.mygalleryfinal.model.MLYLocalImgBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.GalleryFinal.OnHanlderResultCallback;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 创建人: cole
 * 创建时间: 2016/11/11 下午3:17
 * 描述: 图片的处理类,从相机拍照/相册获取照片
 */

/**
 * setMutiSelect(boolean)//配置是否多选
 * setMutiSelectMaxSize(int maxSize)//配置多选数量
 * setEnableEdit(boolean)//开启编辑功能
 * setEnableCrop(boolean)//开启裁剪功能
 * setEnableRotate(boolean)//开启选择功能
 * setEnableCamera(boolean)//开启相机功能
 * setCropWidth(int width)//裁剪宽度
 * setCropHeight(int height)//裁剪高度
 * setCropSquare(boolean)//裁剪正方形
 * setSelected(List)//添加已选列表,只是在列表中默认呗选中不会过滤图片
 * setFilter(List list)//添加图片过滤，也就是不在GalleryFinal中显示
 * takePhotoFolter(File file)//配置拍照保存目录，不做配置的话默认是/sdcard/DCIM/GalleryFinal/
 * setRotateReplaceSource(boolean)//配置选择图片时是否替换原始图片，默认不替换
 * setCropReplaceSource(boolean)//配置裁剪图片时是否替换原始图片，默认不替换
 * setForceCrop(boolean)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
 * setForceCropEdit(boolean)//在开启强制裁剪功能时是否可以对图片进行编辑（也就是是否显示旋转图标和拍照图标）
 * setEnablePreview(boolean)//是否开启预览功能
 */


public class ImageFileUtil {

    private static String TAG = ImageFileUtil.class.getSimpleName();

    public interface OnGetPhotoResultListener {

        void OnSuccess(int requestCode, ArrayList<MLYLocalImgBean> imgList);

        void OnSuccess(int requestCode, int width, int height, Uri fileUri);

        void OnFail(int requestCode, String errMsg);
    }

    /**
     * 初始化相册读取的库
     *
     * @param context 上下文
     */
    public static void initImageFileUtil(Context context, FunctionConfig functionConfig) {

        // 主题设置,配置相片读取器的
        ThemeConfig themeConfig = new ThemeConfig.Builder()
                .setCropControlColor(context.getResources().getColor(R.color.theme_color))
                .setFabNornalColor(context.getResources().getColor(R.color.theme_color))
                .setFabPressedColor(context.getResources().getColor(R.color.theme_color_button_clicked))
                .setTitleBarBgColor(context.getResources().getColor(R.color.theme_tab_host_bg_color))
                .setTitleBarTextColor(context.getResources().getColor(R.color.theme_color_c2))
                .setCheckSelectedColor(context.getResources().getColor(R.color.theme_color))
//                .setIconCamera(R.drawable.photo)
                .setIconBack(R.mipmap.icon_back)
                .setTitleBarIconColor(context.getResources().getColor(R.color.theme_color_c2))
                .setIconClear(0)
                .build();

        //配置功能
        if (functionConfig == null) {
            functionConfig = getSingleDefault();
        }

        ImageLoader imageloader = new UILImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, themeConfig)
                .setFunctionConfig(functionConfig)
                .build();

        GalleryFinal.init(coreConfig);
    }

    private static FunctionConfig getSingleDefault() {
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setForceCrop(true)
                .setCropReplaceSource(false)
                .build();

        return functionConfig;
    }

    /**
     * 使用默认配置的获取照片库
     *
     * @param reqCode 请求码
     * @param listener  {@link OnGetPhotoResultListener}获取照片的监听器
     */
    public static void getPhotoSingle(final int reqCode, final OnGetPhotoResultListener listener) {
        GalleryFinal.openGallerySingle(reqCode, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (reqeustCode == reqCode) {
                    if (listener != null) {
                        if (resultList != null && resultList.size() > 0) {
                            PhotoInfo info = resultList.get(0);
                            Uri uri = Uri.fromFile(new File(info.getPhotoPath()));
                            listener.OnSuccess(reqCode, info.getWidth(), info.getHeight(), uri);

                            Log.i(TAG,TAG + " -- 相册获取照片成功");
                        } else {
                            listener.OnFail(reqCode, "获取照片失败,照片为空");
                            Log.i(TAG,TAG + " -- 获取照片失败(照片为空)");
                        }
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                if (reqCode == requestCode && listener != null) {
                    listener.OnFail(requestCode, errorMsg);
                    Log.i(TAG,TAG + " -- 获取照片失败(" + errorMsg + ")");
                }
            }
        });
    }

    /**
     * 使用默认配置的获取照片库
     *
     * @param reqCode 请求码
     * @param forceCrop 是否强制剪裁图片/强制剪裁正方形图片
     * @param listener  {@link OnGetPhotoResultListener}获取照片的监听器
     */
    public static void getPhotoSingleWithFunc(final int reqCode, boolean forceCrop, final OnGetPhotoResultListener listener) {
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(forceCrop)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(forceCrop)
                .setForceCrop(forceCrop)
                .setCropReplaceSource(false)
                .build();

        GalleryFinal.openGallerySingle(reqCode, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (reqeustCode == reqCode) {
                    if (listener != null) {
                        if (resultList != null && resultList.size() > 0) {
                            PhotoInfo info = resultList.get(0);
                            Uri uri = Uri.fromFile(new File(info.getPhotoPath()));
                            listener.OnSuccess(reqCode, info.getWidth(), info.getHeight(), uri);

                            Log.i(TAG,TAG + " -- 相册获取照片成功");
                        } else {
                            listener.OnFail(reqCode, "获取照片失败,照片为空");
                            Log.i(TAG,TAG + " -- 获取照片失败(照片为空)");
                        }
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                if (reqCode == requestCode && listener != null) {
                    listener.OnFail(requestCode, errorMsg);
                    Log.i(TAG,TAG + " -- 获取照片失败(" + errorMsg + ")");
                }
            }
        });
    }


    /**
     * 通过相机拍照获取照片
     *
     * @param reqCode 请求码
     * @param listener  {@link OnGetPhotoResultListener}获取照片的监听器
     */
    public static void getPhotoByCamera(final int reqCode, final OnGetPhotoResultListener listener) {
        GalleryFinal.openCamera(reqCode, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (reqeustCode == reqCode) {
                    if (listener != null) {
                        if (resultList != null && resultList.size() > 0) {
                            PhotoInfo info = resultList.get(0);
                            Uri uri = Uri.fromFile(new File(info.getPhotoPath()));
                            listener.OnSuccess(reqCode, info.getWidth(), info.getHeight(), uri);

                            Log.i(TAG,TAG + " -- 拍照获取照片成功");
                        } else {
                            listener.OnFail(reqCode, "获取照片失败,照片为空");
                            Log.i(TAG,TAG + " -- 获取照片失败(照片为空)");
                        }
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                if (reqCode == requestCode && listener != null) {
                    listener.OnFail(requestCode, errorMsg);
                    Log.i(TAG,TAG + " -- 获取照片失败(" + errorMsg + ")");
                }
            }
        });
    }

    /**
     * 通过相机拍照获取照片
     *
     * @param reqCode 请求码
     * @param forceCrop 是否强制剪裁图片/强制正方形剪裁
     * @param listener  {@link OnGetPhotoResultListener}获取照片的监听器
     */
    public static void getPhotoByCameraWithFunc(final int reqCode, boolean forceCrop, final OnGetPhotoResultListener listener) {
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(forceCrop)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(forceCrop)//控制是不是正方形
                .setForceCrop(forceCrop)
                .setCropReplaceSource(false)
                .build();

        GalleryFinal.openCamera(reqCode, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (reqeustCode == reqCode) {
                    if (listener != null) {
                        if (resultList != null && resultList.size() > 0) {
                            PhotoInfo info = resultList.get(0);
                            Uri uri = Uri.fromFile(new File(info.getPhotoPath()));
                            listener.OnSuccess(reqCode, info.getWidth(), info.getHeight(), uri);

                            Log.i(TAG,TAG + " -- 拍照获取照片成功");
                        } else {
                            listener.OnFail(reqCode, "获取照片失败,照片为空");
                            Log.i(TAG,TAG + " -- 获取照片失败(照片为空)");
                        }
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                if (reqCode == requestCode && listener != null) {
                    listener.OnFail(requestCode, errorMsg);
                    Log.i(TAG,TAG + " -- 获取照片失败(" + errorMsg + ")");
                }
            }
        });
    }

    public static void getMutiPhotoByConfig(final int reqCode, FunctionConfig config, final OnGetPhotoResultListener listener) {
        if (config == null) {
            config = getSingleDefault();
        }

        GalleryFinal.openGalleryMuti(reqCode, config, new OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (reqeustCode == reqCode) {
                    if (listener != null) {
                        ArrayList<MLYLocalImgBean> imgBeans = new ArrayList<>();
                        if(resultList!=null&&resultList.size()!=0){
                            for (PhotoInfo photoInfo : resultList) {
                                MLYLocalImgBean imgBean = new MLYLocalImgBean();
                                imgBean.setHeight(photoInfo.getHeight());
                                imgBean.setPhotoId(photoInfo.getPhotoId());
                                imgBean.setWidth(photoInfo.getWidth());
                                imgBean.setPhotoPath(photoInfo.getPhotoPath());
                                imgBeans.add(imgBean);
                            }
                        }
                        listener.OnSuccess(reqCode, imgBeans);
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                if (reqCode == requestCode && listener != null) {
                    listener.OnFail(requestCode, errorMsg);
                    Log.i(TAG,TAG + " -- 获取照片失败(" + errorMsg + ")");
                }
            }
        });
    }

}
