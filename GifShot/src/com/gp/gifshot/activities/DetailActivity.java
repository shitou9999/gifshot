package com.gp.gifshot.activities;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gp.gifshot.R;
import com.gp.gifshot.entity.Comment;
import com.gp.gifshot.myoverride.MyApplication;
import com.gp.gifshot.myoverride.MyVideoView;
import com.gp.gifshot.plugs.sdlv.Menu;
import com.gp.gifshot.plugs.sdlv.MenuItem;
import com.gp.gifshot.plugs.sdlv.SlideAndDragListView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
@SuppressLint("ClickableViewAccessibility")
@SuppressWarnings("all")
public class DetailActivity extends FragmentActivity implements
		SlideAndDragListView.OnListItemLongClickListener,
		SlideAndDragListView.OnDragListener,
		SlideAndDragListView.OnSlideListener,
		SlideAndDragListView.OnMenuItemClickListener,
		SlideAndDragListView.OnListItemClickListener,
		SlideAndDragListView.OnItemDeleteListener,OnTouchListener, OnGestureListener{

	private MyApplication app = null;
	
	
	private SlideAndDragListView listView = null;
	private MyVideoView videoView = null;
	private boolean isPause = false;
	private int curPosion = 0;
	private List<Comment> commentList = new ArrayList<Comment>();

	private CommentAdapter adapter = null;
	protected Toast toast;
	// 图片插件
	private ImageLoader imageLoader = null;
	// 左滑
	private Menu mMenu;
	// 返回顶部
	private ImageView imageView_totop = null;
	private int screen_width = 0;
	private int screen_height = 0;

	private RelativeLayout louzhu = null;

	private ImageView back = null;
	private EditText edit_reply = null;
	private MediaController mc = null;
	// 视频播放源
	String playUrl = null;

	// 左滑关闭
	private GestureDetector mGestureDetector = null;
	private int verticalMinDistance = 20;
	private int minVelocity = 0;
	private boolean isCacheOk = false; 
	private boolean isCancel = false; 
	// 进度条
	private ProgressBar processbar = null;
	private DownLoadVideoTask asynTask = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		/** 组件变量 **/
		listView = (SlideAndDragListView) findViewById(R.id.detail_listview_comment);
		imageView_totop = (ImageView) findViewById(R.id.image_id_to_top);
		videoView = (MyVideoView) findViewById(R.id.detail_video);

		edit_reply = (EditText) findViewById(R.id.edit_reply);

		louzhu = (RelativeLayout) findViewById(R.id.detail_zone_master);
		back = (ImageView) findViewById(R.id.top_itv_back);

		app = (MyApplication)getApplication();
		processbar = (ProgressBar) findViewById(R.id.detail_process);
		
		// 初始化屏幕宽高
		WindowManager wm = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		screen_width = wm.getDefaultDisplay().getWidth();
		screen_height = wm.getDefaultDisplay().getHeight();

		/** ImageLoader **/
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		imageLoader = imageLoader.getInstance();
		imageLoader.init(config);

		// 全局右滑关闭
		
		mGestureDetector = new GestureDetector((OnGestureListener) this);    
		RelativeLayout viewSnsLayout = (RelativeLayout)findViewById(R.id.both);    
        viewSnsLayout.setOnTouchListener(this);    
        viewSnsLayout.setLongClickable(true);    
		
        // 视频占据屏幕的1/3
     	videoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,(int)(screen_height*0.3)));
		// 1、加载视频线程
        // （如果挂载了sk卡就保存到本地，否则缓冲播放）
//		playUrl = "http://192.168.1.30:8080/wot.kongzhong.com/ztm/factory/VID_20160115_131129.mp4"; // 从intent拿到的url
		playUrl = "http://yyl.keyhua.com:8080/wot.kongzhong.com/ztm/factory/2.mp4"; // 从intent拿到的url
		if(!app.isMount()){
			doPlay(playUrl);
		}else {
			asynTask = new DownLoadVideoTask();
			asynTask.execute(playUrl);
		}

		// 2、加载楼主和评论线程
		initCommentList();

		/** 左滑 **/
		initMenu();
		initUiAndListener();
		// 下拉监听
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE: // 空闲
					// 监听到顶部（getFirstVisiblePosition）
					if (view.getFirstVisiblePosition() != 0) { // 只要不在顶部就显示返回顶部按钮
						imageView_totop.setVisibility(View.VISIBLE); // 可见
						// 动态设置楼主区的高度
						louzhu.setVisibility(View.GONE); // 不占据空间
					} else {
						imageView_totop.setVisibility(View.INVISIBLE); // 不可见
						louzhu.setVisibility(View.VISIBLE);
					}
					// 监听listview滚到最底部（getLastVisiblePosition）
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						/** 到底部的网络请求，暂时考虑设置一个boolean来判断上一个下拉加载线程是否执行完毕 **/
						showToast("正在请求数据...");
					}
					break;
				case OnScrollListener.SCROLL_STATE_FLING:
					// Log.v("gp", "开始滚动：SCROLL_STATE_FLING");
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					// Log.v("gp", "正在滚动：SCROLL_STATE_TOUCH_SCROLL");
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});

	}

	// 播放视频
	public void doPlay(String urlOrLocalPath){
		if (null != urlOrLocalPath && !"".equals(urlOrLocalPath.trim())) {
			Uri uri = Uri.parse(urlOrLocalPath);
			videoView.setVideoURI(uri);
			videoView.start();
			mc = new MediaController(getApplicationContext());
			videoView.setMediaController(mc);
			mc.setVisibility(View.GONE);
			
			videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
//					showToast("播放完毕");
//					mp.release(); // 不释放就会一直调用这个监听？
					videoView.seekTo(0);//（循环播放）
					videoView.start();
				}
			});
			
			videoView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (!isPause) {
							Toast.makeText(getApplicationContext(), "暂停",Toast.LENGTH_SHORT).show();// 可能会用系统自带的
							curPosion = videoView.getCurrentPosition();
							videoView.pause();
							isPause = true;
						} else {
							showToast("恢复");
							if (curPosion >= 0) {
								videoView.seekTo(curPosion);
								videoView.start();
								curPosion = -1;
							}
							isPause = false;
						}
						break;

					default:
						break;
					}
					return true;
				}
			});

		} else {
			showToast("视频资源不可用");
		}
	}
	public void onStart() {
		super.onStart();
	}

	public void onPause() {
		super.onPause();
	}

	public void onResume() {
		super.onResume();
	}
	
	// 下载视频线程
	public class DownLoadVideoTask extends AsyncTask<String, Integer, byte[]>{
		
		public String filePath = null;
		ByteArrayOutputStream baos = null;
		FileOutputStream fileOutputStream = null;

		@Override
		protected byte[] doInBackground(String... params) {
			// 下载
			if (params.length > 0) {
				String url = params[0];
				try {
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(url);
					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						long total = entity.getContentLength();
						baos = new ByteArrayOutputStream();
						byte[] buf = new byte[1024];
						int count = 0;
						int length = -1;
						while (((length = is.read(buf)) != -1) && !isCancel) { // 给了一个是否取消下载了得标识，以免主线程完毕后还在下载
							Log.i("擦", "操");
							baos.write(buf, 0, length);
							count += length;
							// 调用publishProgress公布进度,最后onProgressUpdate方法将被执行
							publishProgress((int) ((count / (float) total) * 100));
						}
						is.close();
					}else{
						showToast("视频源不可用");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			return baos.toByteArray();
		}

		// 为了点了返回了及时停止下载线程（见下方官方文档@doc），所以把保存文件的步骤放在这里面了，操作的结果改成了byte[]
		@Override
		protected void onPostExecute(byte[] result) {
			try {
				// 保存文件
				if(app.isMount()){// 外部sd卡已经挂载
					if(null!=baos){
						String filaName = System.currentTimeMillis()+"_r"+(int)(Math.random()*100)+".mp4";
						filePath = Environment.getExternalStorageDirectory()+File.separator+"gp_gifshot"+File.separator+filaName;
						File file = new File(Environment.getExternalStorageDirectory()+File.separator+"gp_gifshot",filaName);
						fileOutputStream = new FileOutputStream(file);
						fileOutputStream.write(result); // 保存文件
						fileOutputStream.close();
						baos.close();
						showToast("下载完毕");
					}
				}
				
				Toast.makeText(getApplicationContext(), "文件已经保存至："+result, 0).show();
				if(null!=filePath){
					// 开始播放
					doPlay(filePath);
					processbar.setVisibility(View.GONE);
				}
				// 视频下载完成后才能点击返回上一级Activity（由于下面的全局右滑也要用到这个，所以在设置一个标志位）
				back.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (videoView != null) {
							videoView.suspend();
							mc.hide();
							videoView = null;
						}
						finish();
					}
				});
				isCacheOk = true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			Log.i("擦2", "操2");
			processbar.setProgress(values[0]);
//			super.onProgressUpdate(values);
		}

		@Override
		protected void onCancelled() { // 取消后
			Toast.makeText(app, "cancle", 0).show();
			try {
				if(null!=fileOutputStream){
					fileOutputStream.close();
				}
				if(null!=baos){
					baos.close();
				}
				super.onCancelled();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 按下返回键停止下载视频的线程---but:
	// @doc
	// You can stop a running task with cancel(true).
	// A cancel will let the task finish its doInBackground but will never call onPostExecute.
	// You could interrupt your background routine by checking isCanceled() and so return earlier since the task was killed
	@Override
	public void onBackPressed() {
		isCancel = true; // while不要下载数据了，不要读取了
		asynTask.cancel(true);//执行异步线程取消操作，避免耗费流量
		showToast("停止下载线程");
		super.onBackPressed();
	}

	
	// 初始化测试评论列表
	public void initCommentList() {
		for (int i = 0; i < 200; i++) {
			Comment comment = new Comment();
			comment.setHead((i / 2 == 0) ? "http://lib.huitu.com/pic/20151229/r27241.jpg" : "http://lib.huitu.com/pic/20151229/r27204.jpg");
			comment.setNickname(Math.random() * 1000 + "");
			int radom = (((int) (Math.random() * 10))) % 4;
			switch (radom % 5) {
			case 0:
				comment.setComment_content("说得好，啪啪啪！说得好，啪啪啪！说得好，啪啪啪！说得好，啪啪啪！说得好，啪啪啪！说得好，啪啪啪！说得好，啪啪啪！说得好，啪啪啪！\n\n说得好，啪啪啪！说得好，啪啪啪！");
				break;
			case 1:
				comment.setComment_content("垃圾");
				break;
			case 2:
				comment.setComment_content("加微信1254165165");
				break;
			case 3:
				comment.setComment_content("尿座零食店+8523544 :)");
				break;
			default:
				comment.setComment_content("不知道说啥");
				break;
			}
			comment.setComment_time("2016-01-02 12:20:30");
			comment.setUserid((long) (Math.random() * 10000));
			commentList.add(comment);
		}
	}

	public class CommentAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return commentList.size();
		}

		@Override
		public Object getItem(int position) {
			return commentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return commentList.get(position).getUserid();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewUtil holder = new ViewUtil();
			// 如果不为空就从缓存中拿
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.comment_item, null);
				// 头像
				holder.head = (ImageView) convertView
						.findViewById(R.id.comment_image_head);
				// 昵称
				holder.text_nickname = (TextView) convertView
						.findViewById(R.id.comment_text_usernickname);
				// 内容
				holder.text_cotent = (TextView) convertView
						.findViewById(R.id.comment_text_usercomment_content);
				// 时间
				holder.text_time = (TextView) convertView
						.findViewById(R.id.comment_text_usercomment_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewUtil) convertView.getTag();
			}

			/** 使用DisplayImageOptions.Builder()创建DisplayImageOptions **/
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.defult)
					// 设置图片下载期间显示的图片
					.showImageForEmptyUri(R.drawable.defult)
					//
					// 设置图片URL为空或是错误的时候显示的图片
					.showImageOnFail(R.drawable.defult)
					.displayer(new RoundedBitmapDisplayer(1))
					// 设置成圆角图片
					.displayer(new FadeInBitmapDisplayer(100))
					.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
					// .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
					.build(); // 创建配置过得DisplayImageOption对象
			// 图片加载器
			imageLoader.displayImage(commentList.get(position).getHead(),
					holder.head, options);

			holder.text_nickname.setText(commentList.get(position)
					.getNickname());
			holder.text_cotent.setText(commentList.get(position)
					.getComment_content().trim());
			holder.text_time.setText(commentList.get(position)
					.getComment_time());

			// 设置点击监听事件，查看详情（为了兼容左滑菜单，把这些item的点击监听放在了下面左滑菜单的自带方法里面了）
			/*
			 * convertView.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { dialog.show();
			 * 
			 * // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
			 * DisplayImageOptions options = new DisplayImageOptions.Builder()
			 * .showStubImage(R.drawable.defult) // 设置图片下载期间显示的图片 //
			 * .showImageForEmptyUri(R.drawable.dmbg_default) // //
			 * 设置图片URL为空或是错误的时候显示的图片 .showImageOnFail(R.drawable.defult)// //
			 * 设置图片加载或解码过程中发生错误显示的图片 .displayer(new RoundedBitmapDisplayer(4))
			 * // 设置成圆角图片 // .displayer(new
			 * FadeInBitmapDisplayer(100)).cacheInMemory(true) //
			 * 设置下载的图片是否缓存在内存中 // .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
			 * .build(); // 创建配置过得DisplayImageOption对象
			 * imageLoader.displayImage(filmList.get(position).getFilmImage(),
			 * imageView_Dialog,options);
			 * textView_Name_Dialog.setText(filmList.get
			 * (position).getFilmName());
			 * if("- N/A分".equals(filmList.get(position).getFilmScore())){
			 * textView_Score_Dialog.setText("暂无评分"); }else {
			 * textView_Score_Dialog
			 * .setText(filmList.get(position).getFilmScore().replaceAll("-",
			 * "").trim()); }
			 * textView_Type_Dialog.setText(filmList.get(position)
			 * .getFilmType());
			 * textView_Intro_Dialog.setText(filmList.get(position
			 * ).getFilmIntro());
			 * 
			 * // 播放事件 button_play_id1.setOnClickListener(new OnClickListener()
			 * {
			 * 
			 * @Override public void onClick(View v) { String playUrl =
			 * filmList.get(position).getFilmPlayUrl(); // String playUrl =
			 * "http://220.164.101.58/vplay.aixifan.com/des/acf-45/2538179_mp4/2538179_720p.mp4?k=aa6603a5fb84c36a82bd99a689577ead&t=1451268487"
			 * ; String filmname = filmList.get(position).getFilmName();
			 * if(null!=playUrl && !"".equals(playUrl.trim())){ Intent intent =
			 * new Intent(); intent.putExtra("playUrl", playUrl);
			 * intent.putExtra("filmname", filmname); //
			 * intent.setClass(getApplicationContext(),
			 * PlayFilmVideoViewActivity.class);
			 * intent.setClass(getApplicationContext(),
			 * PlayFilmWebViewActivity.class); startActivity(intent); }else {
			 * showToast("暂无资源"); } } }); } });
			 */
			return convertView;
		}

		class ViewUtil {
			ImageView head = null;
			TextView text_nickname = null;
			TextView text_cotent = null;
			TextView text_time = null;
		}
	}

	// 左滑
	public void initMenu() {
		mMenu = new Menu((int) getResources().getDimension(
				R.dimen.slv_item_height), new ColorDrawable(Color.LTGRAY),
				false); // true 滑动超出，false：不超出
		mMenu.addItem(new MenuItem.Builder()
				.setWidth(
						(int) getResources().getDimension(
								R.dimen.slv_item_bg_btn_width) + 30)
				.setBackground(new ColorDrawable(Color.RED))
				.setText("点赞")
				.setTextColor(
						getResources().getColor(R.drawable.comment_more_text))
				.setTextSize(15).build());
		mMenu.addItem(new MenuItem.Builder()
				.setWidth(
						(int) getResources().getDimension(
								R.dimen.slv_item_bg_btn_width) + 30)
				.setBackground(new ColorDrawable(Color.GREEN)).setText("举报")
				.setTextColor(Color.GRAY).setTextSize(15).build());
		mMenu.addItem(new MenuItem.Builder()
				.setWidth(
						(int) getResources().getDimension(
								R.dimen.slv_item_bg_btn_width) + 30)
				.setBackground(new ColorDrawable(Color.LTGRAY)).setText("回复")
				.setDirection(MenuItem.DIRECTION_RIGHT)
				.setTextColor(Color.BLACK).setTextSize(15).build());
		mMenu.addItem(new MenuItem.Builder()
				.setWidth(
						(int) getResources().getDimension(
								R.dimen.slv_item_bg_btn_width) + 30)
				.setBackground(
						getResources().getDrawable(R.drawable.comment_more))
				.setText("更多")
				.setTextSize(15)
				.setDirection(MenuItem.DIRECTION_RIGHT)
				.setTextColor(
						getResources().getColor(R.drawable.comment_more_text))
				.setTextSize(15).build());
		mMenu.addItem(new MenuItem.Builder()
				.setWidth(
						(int) getResources().getDimension(
								R.dimen.slv_item_bg_btn_width) + 30)
				.setBackground(
						getResources().getDrawable(R.drawable.comment_copy))
				.setText("复制")
				.setTextSize(15)
				.setDirection(MenuItem.DIRECTION_RIGHT)
				.setTextColor(getResources().getColor(R.drawable.detail_master))
				.setTextSize(15).build());
	}

	public void initUiAndListener() {
		listView.setMenu(mMenu);

		adapter = new CommentAdapter();
		listView.setAdapter(adapter);
		// listView.setOnListItemLongClickListener(this); // 长按事件
		// listView.setOnDragListener(this, commentList); // 拖拽排序事件
		// 自己实现item的点击事件，不需要这个(有问题，还是放在给的点击事件里面做业务了)
		listView.setOnListItemClickListener(this);
		listView.setOnSlideListener(this);
		listView.setOnMenuItemClickListener(this);
		listView.setOnItemDeleteListener(this);
	}

	@Override
	public void onListItemLongClick(View view, int position) {
		// Toast.makeText(MainActivity.this, "1" + position,
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDragViewStart(int position) {
		// Toast.makeText(MainActivity.this, "2" + position,
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDragViewMoving(int position) {
		// Toast.makeText(MainActivity.this, "3" + position,
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDragViewDown(final int position) {
		// Toast.makeText(MainActivity.this, "4" + position,
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSlideOpen(View view, View parentView, int position,
			int direction) {
		// Toast.makeText(MainActivity.this, "5" + position,
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSlideClose(View view, View parentView, int position,
			int direction) {
		// Toast.makeText(MainActivity.this, "6" + position,
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onListItemClick(View v, final int position) {
		
		// 每个item被点击
		edit_reply.setHint("");
		edit_reply.setText("");
		edit_reply.setVisibility(View.GONE);
	}

	// 回复（评论）用户
	private void doReply(long userid,String content) {
		// 评论的网络异步请求...
		
	}

	private void showInputBox(String hint){
		edit_reply.setVisibility(View.VISIBLE);
		edit_reply.setHint("@" + hint);
		edit_reply.setFocusable(true);
		edit_reply.setFocusableInTouchMode(true);
		edit_reply.requestFocus();
	}
	/** menu监听事件--gp **/
	@Override
	public int onMenuItemClick(View v, int itemPosition, int buttonPosition,
			int direction) {
		switch (direction) {
		case MenuItem.DIRECTION_LEFT:
			switch (buttonPosition) {
			case 0:
				// 左边第一颗--gp
				return Menu.ITEM_NOTHING;
				// 左边第二颗--gp
			case 1:
				return Menu.ITEM_SCROLL_BACK;
			}
			break;
		case MenuItem.DIRECTION_RIGHT:
			switch (buttonPosition) {
			case 0:
				// 右边（即从右至左第一颗按键）回复
				/** 评论/回复 **/
				showInputBox(commentList.get(itemPosition).getNickname());
				return Menu.ITEM_SCROLL_BACK;// Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP删除item--gp，会执行下面的onItemDelete方法！！！可以做网络请求之类的操作
			case 1:
				// 右边（从右至左第二颗按键）退出菜单--gp
				return Menu.ITEM_SCROLL_BACK;
			case 2:
				// 右边（从右至左第三颗按键）
				showToast(commentList.get(itemPosition).getComment_content() + "被复制");
				return Menu.ITEM_NOTHING;
			}
		}
		return Menu.ITEM_NOTHING;
	}

	@Override
	public void onItemDelete(View view, int position) {
		// ITEM_DELETE_FROM_BOTTOM_TO_TOP 时调用
		// String s = commentList.get(position).getFilmName();
		// Toast.makeText(MainActivity.this, "草泥马！！！"+s+"被删除了！！" + position,
		// Toast.LENGTH_SHORT).show();
		// filmList.remove(position);
		// adapter.notifyDataSetChanged();
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

	public void toTop(View view) {
		louzhu.setVisibility(View.VISIBLE); // 使楼主可见
		listView.setSelection(0);// 跳至顶部
		imageView_totop.setVisibility(View.INVISIBLE); // 按钮不可见
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		
		if (e1.getX() - e2.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
			// 切换Activity
			// Intent intent = new Intent(ViewSnsActivity.this,
			// UpdateStatusActivity.class);
			// startActivity(intent);
			Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show();
			
		} else if (e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
			isCancel = true; // while不要下载数据了，不要读取了
			asynTask.cancel(true);//执行异步线程取消操作，避免耗费流量
			if (videoView != null && isCacheOk) {
				videoView.suspend();
				mc.hide();
				videoView = null;
			}
			finish();
		}

		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);  
	}
}
