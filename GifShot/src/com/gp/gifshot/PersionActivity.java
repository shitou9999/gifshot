package com.gp.gifshot;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gp.gifshot.activities.DetailActivity;
import com.gp.gifshot.entity.MainItem;
import com.gp.gifshot.myoverride.MyNoScrollGridView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class PersionActivity  extends Activity  {

	private MyNoScrollGridView gridView = null;
	private Myadapter adaper = null;
	private List<MainItem> mdatas = new ArrayList<MainItem>();
	private ImageView top_itv_back = null;
	private ImageView persion_top = null;
	
	private int screen_width = 0;
	private int screen_height = 0;
	
	private ImageLoader imageLoader = null;
	private ScrollView persion_scroll = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_persion);
		
		top_itv_back = (ImageView)findViewById(R.id.top_itv_back);
		persion_top = (ImageView)findViewById(R.id.persion_top);
		persion_scroll = (ScrollView) findViewById(R.id.persion_scroll);
		// TODO 初始化想滚到最上面
		persion_scroll.scrollTo(persion_top.getScrollX(),persion_top.getScrollY());
		
		top_itv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		// onCreate比onCreateView更早运行
		// 初始化屏幕宽高
		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		screen_width = wm.getDefaultDisplay().getWidth();
		screen_height = wm.getDefaultDisplay().getHeight();
		
		// imageloader
		ImageLoaderConfiguration config = new ImageLoaderConfiguration  
			    .Builder(getApplicationContext())  
			    .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();  
		
		
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
		// gridview
		gridView = (MyNoScrollGridView) findViewById(R.id.persion_grideview);
		gridView.setNumColumns(2);
		
		// 网络请求假数据
		initData();
		
		// 适配器
		adaper = new Myadapter();
		gridView.setAdapter(adaper);
		// 这样设置比在adapter里面设置每个item的监听要灵敏些，对于第一个item来说
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), mdatas.get(position).getThumurl(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(PersionActivity.this, DetailActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initData() {
		for (int i = 0; i <10; i++) {
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
				LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
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

				// 点击事件（放在最上面做了：上面的监听比在adapter里面设置每个item的监听要灵敏些，对于第一个item来说）
				/*convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(), mdatas.get(position).getThumurl(), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(PersionActivity.this, DetailActivity.class);
						startActivity(intent);
					}
				});*/
				
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.persion, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		finish(); // 销毁
		super.onBackPressed();
	}
	
	
}
