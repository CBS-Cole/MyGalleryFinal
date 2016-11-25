# MyGalleryFinal
GalleryFinal图片库的二次封装

##1、引入GalleryFinal和universal-image-loader
在build.gradle中添加以下语句：

		compile 'cn.finalteam:galleryfinal:1.4.8.7'
		compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.4'

##2、配置
* 我这里就不做主题仔细的讲解，我的项目都是用默认的设置。
* 需要更详细的文档的伙伴们可以看一下这个[github](https://github.com/pengjianbo/GalleryFinal)。
* 功能配置类说明

		setMutiSelect(boolean)//配置是否多选
		setMutiSelectMaxSize(int maxSize)//配置多选数量
		setEnableEdit(boolean)//开启编辑功能
		setEnableCrop(boolean)//开启裁剪功能
		setEnableRotate(boolean)//开启选择功能
		setEnableCamera(boolean)//开启相机功能
		setCropWidth(int width)//裁剪宽度
		setCropHeight(int height)//裁剪高度
		setCropSquare(boolean)//裁剪正方形
		setSelected(List)//添加已选列表,只是在列表中默认呗选中不会过滤图片
		setFilter(List list)//添加图片过滤，也就是不在GalleryFinal中显示
		takePhotoFolter(File file)//配置拍照保存目录，不做配置的话默认是/sdcard/DCIM/GalleryFinal/
		setRotateReplaceSource(boolean)//配置选择图片时是否替换原始图片，默认不替换
		setCropReplaceSource(boolean)//配置裁剪图片时是否替换原始图片，默认不替换
		setForceCrop(boolean)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
		setForceCropEdit(boolean)//在开启强制裁剪功能时是否可以对图片进行编辑（也就是是否显示旋转图标和拍照图标）
		setEnablePreview(boolean)//是否开启预览功能