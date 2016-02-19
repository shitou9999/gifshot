package com.gp.gifshot.plugs.tab.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gp.gifshot.R;
import com.gp.gifshot.activities.DetailActivity;
import com.gp.gifshot.entity.Comment;
import com.gp.gifshot.entity.MainItem;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Description： Created by：CaMnter Time：2015-10-17 12:15
 */
public class FirstFragment extends Fragment {

	private View self;

	private int screen_width = 0;
	private int screen_height = 0;

	protected Toast toast = null;

	private static FirstFragment instance;
	private GridView gridView = null;
	private Myadapter adaper = null;
	private List<MainItem> mdatas = new ArrayList<MainItem>();
	
	private ImageLoader imageLoader = null;
	
	private int getLastVisiblePosition = 0, lastVisiblePositionY = 0;
	private FirstFragment() {
	}

	public static FirstFragment getInstance() {
		if (instance == null){
			instance = new FirstFragment();
		}
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// onCreate比onCreateView更早运行
		// 初始化屏幕宽高
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		screen_width = wm.getDefaultDisplay().getWidth();
		screen_height = wm.getDefaultDisplay().getHeight();
		
		// imageloader
		ImageLoaderConfiguration config = new ImageLoaderConfiguration  
			    .Builder(getContext())  
			    .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();  
		
		
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
		
		// 网络请求假数据
		initData();
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/** 每次切换到此tab的时候都会执行 **/
		// 加载资源
		if (this.self == null) {
			this.self = inflater.inflate(R.layout.first_fragment, null);
		}
		if (this.self.getParent() != null) {
			ViewGroup parent = (ViewGroup) this.self.getParent();
			parent.removeView(this.self);
		}
		
		gridView = (GridView) self.findViewById(R.id.main_gridview);
		gridView.setNumColumns(2);
		
		// 适配器
		adaper = new Myadapter();
		gridView.setAdapter(adaper);
		// 滚动事件
		gridView.setOnScrollListener(new SlideListenerImpl());
		// 点击事件（比在adapter里面设置每个item的监听要灵敏些，对于第一个item来说）
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getContext(), DetailActivity.class);
				startActivity(intent);
			}
		});
		
		return this.self;
	}

	private void initData() {
		for (int i = 0; i <30; i++) {
			MainItem object = new MainItem();
			
			int radom = (((int) (Math.random() * 10))) % 4;
			switch (radom) {
			case 0:
				object.setHead("http://img.iknow.bdimg.com/avatar/100/r6s1g3.gif");
				break;
			case 1:
				object.setHead("http://img.iknow.bdimg.com/avatar/100/r6s1g3.gif");
				break;
			case 2:
				object.setHead("http://img.iknow.bdimg.com/avatar/100/r6s1g3.gif");
				break;
			default:
				object.setHead("http://img.iknow.bdimg.com/avatar/100/r6s1g3.gif");
				break;
			}
			switch (radom) {
			case 0:
				object.setThumurl("http://www.sinaimg.cn/dy/slidenews/1_img/2016_02/2841_653307_671460.jpg");
				break;
			case 1:
				object.setThumurl("http://img1.gtimg.com/gd/pics/hv1/248/158/1987/129245213.jpg");
				break;
			case 2:
				object.setThumurl("http://photocdn.sohu.com/20151211/mp48038092_1449839937527_3.jpg");
				break;
			case 3:
				object.setThumurl("http://s13.sinaimg.cn/mw690/002BzA5pgy6Q9lEVUgA3c&690");
				break;
			default:
				object.setThumurl("http://s13.sinaimg.cn/mw690/002BzA5pgy6Q9lEVUgA3c&690");
				break;
			}
			object.setId(2541964+i);
			object.setTime("2015-02-03 00:00:00");
			object.setWatchnum("21次");
			mdatas.add(object);
		}
	}
	
	public class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mdatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mdatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mdatas.get(position).getId();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewUtil holder = new ViewUtil();
			if (convertView == null) {
				// 每个item的布局
				LayoutInflater inflater = LayoutInflater.from(getContext());
				// 得到item布局资源并且实例化（动态加载布局）
				convertView = inflater.inflate(R.layout.activity_main_item,null);
				// convertView = getLayoutInflater().inflate(R.layout.activity_main_item, null);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.main_item_imageview_thum);
				holder.textView = (TextView) convertView
						.findViewById(R.id.main_item_textview_time);

				/** 使用DisplayImageOptions.Builder()创建DisplayImageOptions **/
				DisplayImageOptions options = new DisplayImageOptions.Builder()
				 .showStubImage(R.drawable.defult) // 设置图片下载期间显示的图片
				 .showImageForEmptyUri(R.drawable.defult) //
				// 设置图片URL为空或是错误的时候显示的图片
				// .showImageOnFail(R.drawable.dmbg_default)//
				// 设置图片加载或解码过程中发生错误显示的图片
						.displayer(new RoundedBitmapDisplayer(4)) // 设置成圆角图片
						.displayer(new FadeInBitmapDisplayer(100)).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//						.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
						.build(); // 创建配置过得DisplayImageOption对象
				
				imageLoader.displayImage(mdatas.get(position).getThumurl(), holder.imageView, options);
				
				// 设置时间
				holder.textView.setText(mdatas.get(position).getTime() + "小时");
				// 每一个viewItem的宽高
				convertView.setLayoutParams(new AbsListView.LayoutParams( screen_width / 2, (int) (0.4 * screen_height))); // 宽：刚好横排展示两个；高：屏幕的2/5

				convertView.setTag(holder);
			} else {
				holder = (ViewUtil) convertView.getTag();
			}

			return convertView;
		}

		class ViewUtil {
			ImageView imageView = null;
			TextView textView = null;
		}
	}

	private class SlideListenerImpl implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				// 滚动到底部
				if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
					View v = (View) view.getChildAt(view.getChildCount() - 1);
					int[] location = new int[2];
					v.getLocationOnScreen(location);// 获取在整个屏幕内的绝对坐标
					int y = location[1];
					// 第一次拖至底部
					if (view.getLastVisiblePosition() != getLastVisiblePosition && lastVisiblePositionY != y) {
						Toast.makeText(view.getContext(), "已经拖动至底部，再次拖动即可翻页", 500).show();
						getLastVisiblePosition = view.getLastVisiblePosition();
						lastVisiblePositionY = y;
						return;
					} else if (view.getLastVisiblePosition() == getLastVisiblePosition && lastVisiblePositionY == y) {// 第二次拖至底部
						Toast.makeText(view.getContext(), "第二次拖至底部", 500).show();
						//mCallback.execute();
					}
				}
				// 未滚动到底部，第二次拖至底部都初始化
				getLastVisiblePosition = 0;
				lastVisiblePositionY = 0;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}

	}
	protected void showToast(String text) {
		try {
			// 判断toast是否为空 ，空则执行
			if (toast == null) {
				// 将toast的时间设置
				toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
			}
			// 通过传参的方式将text赋值到toast上
			toast.setText(text);
			// 显示toast
			toast.show();
		} catch (Exception e) {
		}
	}
}
