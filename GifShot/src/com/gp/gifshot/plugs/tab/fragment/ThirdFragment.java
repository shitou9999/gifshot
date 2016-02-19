package com.gp.gifshot.plugs.tab.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gp.gifshot.R;
import com.gp.gifshot.entity.MainItem;

/**
 * Description： Created by：CaMnter Time：2015-10-17 12:15
 */
public class ThirdFragment extends Fragment {

	private View self;
	private int screen_width = 0;
	private int screen_height = 0;

	protected Toast toast = null;

	private GridView gridView = null;
	private Myadapter adaper = null;
	private List<MainItem> mdatas = new ArrayList<MainItem>();

	private static ThirdFragment instance;
	private int getLastVisiblePosition = 0, lastVisiblePositionY = 0;

	private ThirdFragment() {
	}

	public static ThirdFragment getInstance() {
		if (instance == null)
			instance = new ThirdFragment();
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// onCreate比onCreateView更早运行
		// 初始化屏幕宽高
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		screen_width = wm.getDefaultDisplay().getWidth();
		screen_height = wm.getDefaultDisplay().getHeight();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/** 每次切换到此tab的时候都会执行 **/
		if (this.self == null) {
			this.self = inflater.inflate(R.layout.third_fragment, null);
		}
		if (this.self.getParent() != null) {
			ViewGroup parent = (ViewGroup) this.self.getParent();
			parent.removeView(this.self);
		}

		gridView = (GridView) self.findViewById(R.id.main_gridview);
		gridView.setNumColumns(2);
		MainItem item = new MainItem();
		item.setHead("");
		mdatas.add(item);
		// 适配器
		adaper = new Myadapter();
		gridView.setAdapter(adaper);
		// 滚动时间
		gridView.setOnScrollListener(new SlideListenerImpl());

		System.out.println("第一个tab的onCreateView");
		return this.self;
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewUtil holder = new ViewUtil();
			if (convertView == null) {
				// 每个item的布局
				LayoutInflater inflater = LayoutInflater.from(getContext());
				// 得到item布局资源并且实例化（动态加载布局）
				convertView = inflater.inflate(R.layout.activity_main_item,
						null);
				// convertView =
				// getLayoutInflater().inflate(R.layout.activity_main_item,
				// null);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.main_item_imageview_thum);
				holder.textView = (TextView) convertView
						.findViewById(R.id.main_item_textview_time);

				convertView.setTag(holder);
			} else {
				holder = (ViewUtil) convertView.getTag();
			}
			// 下载封面
			int radom = (((int) (Math.random() * 10))) % 3;
			switch (radom) {
			case 0:
				holder.imageView.setImageDrawable(getResources().getDrawable(
						R.drawable.test));
				break;
			case 1:
				holder.imageView.setImageDrawable(getResources().getDrawable(
						R.drawable.test2));
				break;
			case 2:
				holder.imageView.setImageDrawable(getResources().getDrawable(
						R.drawable.test3));
				break;
			default:
				break;
			}
			// 设置时间
			holder.textView.setText(((int) (Math.random() * 10)) + "小时");
			// 每一个viewItem的宽高
			convertView.setLayoutParams(new AbsListView.LayoutParams(
					screen_width / 2, (int) (0.4 * screen_height))); // 宽：刚好横排展示两个；高：屏幕的2/5

			// 点击事件
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});

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
					if (view.getLastVisiblePosition() != getLastVisiblePosition
							&& lastVisiblePositionY != y) {
						Toast.makeText(view.getContext(), "已经拖动至底部，再次拖动即可翻页", 500).show();
						getLastVisiblePosition = view.getLastVisiblePosition();
						lastVisiblePositionY = y;
						return;
					} else if (view.getLastVisiblePosition() == getLastVisiblePosition
							&& lastVisiblePositionY == y) {// 第二次拖至底部
						Toast.makeText(view.getContext(), "第二次拖至底部", 500) .show();
						// mCallback.execute();
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

		protected void showToast(String text) {
			try {
				// 判断toast是否为空 ，空则执行
				if (toast == null) {
					// 将toast的时间设置
					toast = Toast
							.makeText(getContext(), "", Toast.LENGTH_SHORT);
				}
				// 通过传参的方式将text赋值到toast上
				toast.setText(text);
				// 显示toast
				toast.show();
			} catch (Exception e) {
			}
		}
	}
}
