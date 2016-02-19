package com.gp.gifshot;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.gp.gifshot.base.SlidingBaseActivity;
import com.gp.gifshot.myoverride.CircleImageView;
import com.gp.gifshot.myoverride.MyApplication;
import com.gp.gifshot.plugs.slidingmenu.lib.SlidingMenu;
import com.gp.gifshot.plugs.tab.EasySlidingTabs;
import com.gp.gifshot.plugs.tab.adapter.TabsFragmentAdapter;
import com.gp.gifshot.plugs.tab.fragment.FirstFragment;
import com.gp.gifshot.plugs.tab.fragment.FourthFragment;
import com.gp.gifshot.plugs.tab.fragment.SecondFragment;
import com.gp.gifshot.plugs.tab.fragment.ThirdFragment;

public class MainActivityGridView extends SlidingBaseActivity {

	private int screen_width = 0;
	private int screen_height = 0;
	
	protected Toast toast = null;
	private long temptime = 0;
	
	// tab
	private EasySlidingTabs easySlidingTabs;
	public static int easySlidingTabsHeight;
    private ViewPager easyVP;
    private TabsFragmentAdapter adapter;
    List<Fragment> fragments;
    public static final String[] titles = {"我的", "关注", "热门", "同城"};
    
    private ImageView back = null;
    private CircleImageView left_image_id1 = null; // 左边栏个人头像
    
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_gridview);
		
		back = (ImageView) findViewById(R.id.top_itv_back);
		
		// tab
		this.initViews();
		this.initData();
		
		// 初始化屏幕宽高
		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		screen_width = wm.getDefaultDisplay().getWidth();
		screen_height = wm.getDefaultDisplay().getHeight();
        
		// slideMenu
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		
		initSlidingMenu(1);
		
		// TODO 初始化程序全局数据，应该放在启动画面来做
		initAll();
	}
	
	/**
	 * 初始化侧边栏
	 */
	private void initSlidingMenu(int start) {
		// 设置左侧滑动菜单
		setBehindContentView(R.layout.menu_frame_left);
//		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new LeftFragment(start)).commit();
		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置可以左右滑动的菜单
		sm.setMode(SlidingMenu.LEFT);
		// 设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动菜单阴影的图像资源
		sm.setShadowDrawable(null);
		// 设置滑动菜单视图的宽度(距离左边的值)
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.6f);
		// 设置触摸屏幕的模式,这里设置为全屏
		// sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置下方视图的在滚动时的缩放比例
		sm.setBehindScrollScale(0.5f);
		
		// 1、初始化组件属性（加载menu_frame_left后才能拿得到）
		left_image_id1 = (CircleImageView) findViewById(R.id.left_image_id1);
		// 2、初始化网络数据到组件。。。
		
		// 3、初始化监听
		left_image_id1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("userid", "asdasf"); //用户id
				intent.setClass(getApplicationContext(), PersionActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		easySlidingTabsHeight = easySlidingTabs.getMeasuredHeight();
		super.onResume();
	}

	// TODO 初始化程序全局数据，应该放在启动画面来做
	public boolean initAll(){
		try {
			MyApplication app = (MyApplication)this.getApplication();
			// 创建文件缓存目录
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){// 外部sd卡已经挂载
				app.setMount(true);
				
				File file = new File(Environment.getExternalStorageDirectory()+File.separator+"gp_gifshot");
				if(!file.exists()){
					boolean success = file.mkdirs();
					if(success){
						showToast("创建"+Environment.getExternalStorageDirectory()+File.separator+"gp_gifshot"+"成功");
					}else {
						showToast("创建应用目录失败");
						finish();
						System.exit(0);
					}
				}else {
					//showToast("存在");
				}
			}else {
				app.setMount(false);
			}
			// .. 其他初始化
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	private void initViews(){
        this.easySlidingTabs = (EasySlidingTabs) this.findViewById(R.id.easy_sliding_tabs);
        this.easyVP = (ViewPager) this.findViewById(R.id.easy_vp);
    }

    private void initData() {
        this.fragments = new LinkedList<>();
        FirstFragment first = FirstFragment.getInstance();
        SecondFragment second = SecondFragment.getInstance();
        ThirdFragment third = ThirdFragment.getInstance();
        FourthFragment fourth = FourthFragment.getInstance();
        this.fragments.add(first);
        this.fragments.add(second);
        this.fragments.add(third);
        this.fragments.add(fourth);

        this.adapter = new TabsFragmentAdapter(this.getSupportFragmentManager(), titles, this.fragments);
        this.easyVP.setAdapter(this.adapter);
        this.easySlidingTabs.setViewPager(this.easyVP);
    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	// 2s之内退出
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
			// 2s内再次选择back键有效
			if (System.currentTimeMillis() - temptime > 2000) {
				showToast("在按一次返回退出");
				temptime = System.currentTimeMillis();
			} else {
				finish();
				// 凡是非零都表示异常退出!0表示正常退出!
				System.exit(0); 
			}
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}
	protected void showToast(String text) {
		try {
			// 判断toast是否为空 ，空则执行
			if (toast == null) {
				// 将toast的时间设置
				toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
			}
			// 通过传参的方式将text赋值到toast上
			toast.setText(text);
			// 显示toast
			toast.show();
		} catch (Exception e) {
		}
	}


	@Override
	public void onClick(View v) {
		
	}


	@Override
	protected void onInitData() {
		
	}


	@Override
	protected void onResload() {
		
	}


	@Override
	protected void setMyViewClick() {
		
	}
	
}
