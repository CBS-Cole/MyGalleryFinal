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

public class MLYImageFileUtil {

    private static String TAG = MLYImageFileUtil.class.getSimpleName();

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

        ImageLoader imageloader = new MLYUILImageLoader();
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
                .setCropSquare(forceCrop)
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
