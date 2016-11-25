package com.example.cole.mygalleryfinal;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cole.mygalleryfinal.model.MLYLocalImgBean;
import com.example.cole.mygalleryfinal.utils.ImageCacheUtil;
import com.example.cole.mygalleryfinal.utils.ImageFileUtil;
import com.example.cole.mygalleryfinal.utils.Util;

import java.util.ArrayList;

/**
 * 创建人: cole
 * 创建时间: 2016/11/15 上午10:04
 * 描述: 主页面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn1, mBtn2;
    private ImageView mImageView;
    private CharSequence[] charSequences = new CharSequence[]{"拍照", "从相片中选择"};
    private final int TAKE_SMALL_PICTURE = 0;
    private final int CHOOSE_BIG_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageFileUtil.initImageFileUtil(this, null);
        setContentView(R.layout.activity_main);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mImageView = (ImageView) findViewById(R.id.img);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                showDialog(false);
                break;
            case R.id.btn2:
                showDialog(true);
                break;
        }
    }

    private void showDialog(final boolean isOriginal) {
        Util.displayDisenbleCancelBtnDialog(this, charSequences, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (isOriginal) {
                        ImageFileUtil.getPhotoByCamera(TAKE_SMALL_PICTURE, new ImageFileUtil.OnGetPhotoResultListener() {
                            @Override
                            public void OnSuccess(int requestCode, ArrayList<MLYLocalImgBean> imgList) {

                            }

                            @Override
                            public void OnSuccess(int requestCode, int width, int height, Uri fileUri) {
                                if (requestCode == TAKE_SMALL_PICTURE) {
                                    if (fileUri != null) {
                                        ImageCacheUtil.getInstance().displayImage(mImageView, fileUri.toString());
                                    }
                                }
                            }

                            @Override
                            public void OnFail(int requestCode, String errMsg) {

                            }
                        });
                    } else {
                        ImageFileUtil.getPhotoByCameraWithFunc(TAKE_SMALL_PICTURE, true, new ImageFileUtil.OnGetPhotoResultListener() {
                            @Override
                            public void OnSuccess(int requestCode, ArrayList<MLYLocalImgBean> imgList) {

                            }

                            @Override
                            public void OnSuccess(int requestCode, int width, int height, Uri fileUri) {
                                if (requestCode == TAKE_SMALL_PICTURE) {
                                    if (fileUri != null) {
                                        ImageCacheUtil.getInstance().displayImage(mImageView, fileUri.toString());
                                    }
                                }
                            }

                            @Override
                            public void OnFail(int requestCode, String errMsg) {

                            }
                        });
                    }
                } else {
                    if (isOriginal) {
                        ImageFileUtil.getPhotoSingle(CHOOSE_BIG_PICTURE, new ImageFileUtil.OnGetPhotoResultListener() {
                            @Override
                            public void OnSuccess(int requestCode, ArrayList<MLYLocalImgBean> imgList) {
                            }

                            @Override
                            public void OnSuccess(int requestCode, int width, int height, Uri fileUri) {
                                if (fileUri != null) {
                                    ImageCacheUtil.getInstance().displayImage(mImageView, fileUri.toString());
                                }
                            }

                            @Override
                            public void OnFail(int requestCode, String errMsg) {
                            }
                        });
                    } else {
                        ImageFileUtil.getPhotoSingleWithFunc(CHOOSE_BIG_PICTURE, true, new ImageFileUtil.OnGetPhotoResultListener() {
                            @Override
                            public void OnSuccess(int requestCode, ArrayList<MLYLocalImgBean> imgList) {
                            }

                            @Override
                            public void OnSuccess(int requestCode, int width, int height, Uri fileUri) {
                                if (fileUri != null) {
                                    ImageCacheUtil.getInstance().displayImage(mImageView, fileUri.toString());
                                }
                            }

                            @Override
                            public void OnFail(int requestCode, String errMsg) {
                            }
                        });
                    }
                }
                dialog.dismiss();
            }
        });
    }
}
