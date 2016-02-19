package com.gp.gifshot.test;

/**
 * @author 高攀
 * @上午11:39:47
 */
import java.io.File;

import com.gp.gifshot.R;
import com.gp.gifshot.myoverride.MyThumbnailUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

/**
 * 获取图片和视频的缩略图 这两个方法必须在2.2及以上版本使用，因为其中使用了ThumbnailUtils这个类
 */
public class AndroidTestActivity extends Activity {
	private ImageView imageThumbnail;
	private ImageView videoThumbnail;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thumbnailtest);
		imageThumbnail = (ImageView) findViewById(R.id.image_thumbnail);
		videoThumbnail = (ImageView) findViewById(R.id.video_thumbnail);
		String imagePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator
				+ "photo"
				+ File.separator
				+ "yexuan.jpg";

		String videoPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator
				+ "video"
				+ File.separator
				+ "醋点灯.avi";

//		imageThumbnail.setImageBitmap(getImageThumbnail("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1159510577,969324470&fm=116&gp=0.jpg", 60, 60));
		videoThumbnail.setImageBitmap(getVideoThumbnail("http://125.64.129.111/vplay.aixifan.com/des/20151216/2987957_mp4/2987957_lvbr.mp4?k=d2e66f58827cf0aa29d03d348092f13c&t=1452132375", 60, 60,
				MediaStore.Images.Thumbnails.MICRO_KIND));
	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1.
	 * 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2.
	 * 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使 用这个工具生成的图像不会被拉伸。
	 * 
	 * @param imagePath
	 *            图像的路径
	 * @param width
	 *            指定输出图像的宽度
	 * @param height
	 *            指定输出图像的高度
	 * @return 生成的缩略图
	 */
	private Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = MyThumbnailUtils.extractThumbnail(bitmap, width, height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 * 
	 * @param videoPath
	 *            视频的路径
	 * @param width
	 *            指定输出视频缩略图的宽度
	 * @param height
	 *            指定输出视频缩略图的高度度
	 * @param kind
	 *            参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图
	 */
	private Bitmap getVideoThumbnail(String videoPath, int width, int height,
			int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = MyThumbnailUtils.createVideoThumbnail(videoPath, kind);
		System.out.println("w" + bitmap.getWidth());
		System.out.println("h" + bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

}
