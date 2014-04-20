package cc.android.supu;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.LinearLayout;
import cc.android.supu.adapter.WelcomeGalleryAdapter;
import cc.android.supu.bean.BannerBean;
import cc.android.supu.bean.CityBean;
import cc.android.supu.bean.MemberBean;
import cc.android.supu.handler.GetAllDistrictHandler;
import cc.android.supu.handler.GetBannerHandler;
import cc.android.supu.handler.UpdateHandler;
import cc.android.supu.service.PushMsg;
import cc.android.supu.tools.AsyncImageLoader;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PublicParams;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.Update;
import cc.android.supu.tools.UserInfoTools;
import cc.android.supu.view.FllingGallery;

public class WelcomeActivity extends Activity {
	/** 成功 */
	private final static int SUCCESS = 1;
	/** 成功 */
	private final static int ERROR = 2;
	/** 失败 */
	private final static int FAIL = 0;

	private final static int UPDATE = 521;
	/** 是否推送消息 */
	private boolean isMessage;
	/** 是否推送消息 */
	private boolean isImage;
	/** 省市区列表 */
	public List<CityBean> cityBeanList;
	/** 获得全部的省市 */
	private GetAllDistrictHandler getAllDistrictHandler;
	/** 升级信息解析器 */
	private UpdateHandler updateHandler;

	private ArrayList<String> cityState;

	private String version2;
	/** 获取引导页信息解析器 */
	private GetBannerHandler bannerHandler;
	private FllingGallery gallery;
	private LinearLayout pointLayout;
	private WelcomeGalleryAdapter adapter;
	/** 获取引导页bannerList */
	private ArrayList<BannerBean> bannerList;
	private String PATH = "";
	private String FILENAME = "welcome.png";

	final TimerTask task = new TimerTask() {

		@Override
		public void run() {
			handler.sendEmptyMessage(100);
		}
	};
	Timer timer = new Timer();
	Drawable drawable;

	private void changeImage() {
		Bitmap bitmap = BitmapFactory.decodeFile(PATH + FILENAME);
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_bg);
		}
		drawable = new BitmapDrawable(bitmap);
		timer.schedule(task, 0);
	}

	private void saveLoadBitmap(final String url) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Bitmap bitmap = AsyncImageLoader.loadImageFromUrl(url);

				if (bitmap != null) {
					Tools.saveImageToSD(bitmap, PATH, FILENAME);
				}
			}
		}).start();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobclickAgent.onError(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			PATH = Environment.getExternalStorageDirectory().toString() + "/welcome/";
		} else {
			PATH = "/data/data/cc.android.supu/files/welcome/";
		}
		changeImage();
		requestUpdateInfo();
		cityState = new ArrayList<String>();
		MemberBean memberBean = UserInfoTools.getUserBean(this);
		if (memberBean != null) {
			String memberId = memberBean.getMemberId();
			if (!"".equals(memberId.trim())) {
				PublicParams.setMEMBERID(memberId);
			}
		}
		// SQLiteHelper helper = new SQLiteHelper(this);
		// SQLiteHelper.createTable();
		// SupuApplication.setMcontext(this);
		// if (helper != null && SQLiteHelper.db.isOpen()) {
		// helper.close();
		// }

		isMessage = UserInfoTools.getMessage(this);
		System.out.println("isMessage" + isMessage);
		isImage = UserInfoTools.getImage(this);
		if (isImage) {
			AsyncImageLoader.isShow = true;
		} else {
			AsyncImageLoader.isShow = false;
		}
		if (isMessage) {
			// 开启消息
			// startService(new Intent(WelcomeActivity.this,
			// Messageservice.class));
			PushMsg pushMsg = PushMsg.getInstance();
			pushMsg.openMsgPush(WelcomeActivity.this);
		}
		// startService(new Intent(WelcomeActivity.this,
		// CityDataService.class));
		intPage();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitDialog();
		}
		return false;
	}

	/**
	 * 退出对话框
	 */
	private void showExitDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setMessage("您确定要退出?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						SupuApplication.exit();
						finish();
					}

				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}

				}).create();
		dialog.show();
	}

	/**
	 * 跳转首页
	 */
	private void Start() {
		if (updateHandler.result_code == 1) {// 需要升级
			return;
		}
		startActivity(new Intent(this, IndexActivity.class));
		this.finish();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				if (drawable != null) {
					gallery.setBackgroundDrawable(drawable);
				}
				break;
			case SUCCESS:
				bannerList = bannerHandler.list;
				handler.sendEmptyMessageDelayed(5, 3000);
				if (bannerList != null && bannerList.size() > 0) {
					// adapter = new WelcomeGalleryAdapter(WelcomeActivity.this,
					// bannerList);
					// gallery.setAdapter(adapter);
					// gallery.setSelection(0);
					// creatPoint(0);
					saveBannerInfo(WelcomeActivity.this, bannerList, false);
					saveLoadBitmap(bannerList.get(0).picUrl);
				} /*
				 * else { gallery.setBackgroundResource(R.drawable.welcome_bg);
				 * Start(); }
				 */
				break;
			case FAIL:
			case ERROR:
				// bannerList = getBannerList(WelcomeActivity.this);
				// if (bannerList.size() == 0) {
				// gallery.setBackgroundResource(R.drawable.welcome_bg);
				// Start();
				// } else {
				handler.sendEmptyMessageDelayed(5, 3000);
				// adapter = new WelcomeGalleryAdapter(WelcomeActivity.this,
				// bannerList);
				// gallery.setAdapter(adapter);
				// gallery.setSelection(0);
				// creatPoint(0);
				// }

				break;
			case 5:
				Start();
				break;
			case UPDATE:
				new Update(WelcomeActivity.this, updateHandler, handler);
				break;
			}
		};
	};

	private void intPage() {
		gallery = (FllingGallery) findViewById(R.id.gallery);
		// gallery.setOnItemSelectedListener(mOnItemSelectedListener);
		pointLayout = (LinearLayout) findViewById(R.id.pointLayout);
		// bannerList = new ArrayList<BannerBean>();
		// adapter = new WelcomeGalleryAdapter(this, bannerList);
		if (!Tools.isAccessNetwork(this)) {
			Tools.netError(this);
			return;
		} else {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					bannerHandler = new GetBannerHandler();
					Tools.requestToParse(WelcomeActivity.this, ConstantUrl.GETBANNER, "GetBanner", null, bannerHandler,
							false);
					if (Tools.responseValue == 1) {
						if (bannerHandler.result_code == 0) {
							handler.sendEmptyMessage(SUCCESS);
						} else {
							handler.sendEmptyMessage(FAIL);
						}
					} else {
						handler.sendEmptyMessage(ERROR);
					}
				}
			}).start();
		}
	}

	/**
	 * 保存banner相关信息
	 * 
	 * @param context
	 * @param list
	 *            banner列表
	 * @param isfirst
	 *            是否第一次
	 */
	private void saveBannerInfo(Context context, ArrayList<BannerBean> list, boolean isfirst) {
		SharedPreferences sp = context.getSharedPreferences("banner_info", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("isfirst", isfirst);
		editor.putInt("count", list.size());
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				StringBuffer buff = new StringBuffer();
				buff.append(list.get(i).picUrl).append(";").append(list.get(i).beginTime).append(";")
						.append(list.get(i).endTime);
				editor.putString("i", buff.toString());
			}
		}
		System.out.println("-----------------");
		editor.commit();
	}

	/**
	 * 获取banner的信息
	 * 
	 * @param context
	 * @return
	 */
	private ArrayList<BannerBean> getBannerList(Context context) {
		SharedPreferences sp = context.getSharedPreferences("banner_info", Context.MODE_PRIVATE);

		int count = sp.getInt("count", 0);
		ArrayList<BannerBean> list = new ArrayList<BannerBean>();
		for (int i = 0; i < count; i++) {
			String bannerStr = sp.getString(i + "", "");
			String[] strArr = bannerStr.split(";");
			if (strArr.length == 3) {
				BannerBean banner = new BannerBean();
				banner.picUrl = strArr[0];
				banner.beginTime = strArr[1];
				banner.endTime = strArr[2];

				list.add(banner);
			}
		}
		return list;
	}

	/**
	 * 获取是否是第一次进入
	 * 
	 * @param context
	 * @return
	 */
	private boolean isFirst(Context context) {
		SharedPreferences sp = context.getSharedPreferences("banner_info", Context.MODE_PRIVATE);
		return sp.getBoolean("isfirst", true);
	}

	// /**
	// * 画点
	// */
	// private void creatPoint(int position) {
	// if (bannerList != null && bannerList.size() > 1) {
	// pointLayout.removeAllViews();
	// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(12, 12);
	// for (int i = 0; i < bannerList.size(); i++) {
	// ImageView point = new ImageView(this);
	// point.setBackgroundResource(i == position ? R.drawable.point_red :
	// R.drawable.point_gray);
	// if (i > 0 && i < bannerList.size()) {
	// lp.leftMargin = 10;
	// }
	// pointLayout.addView(point, i, lp);
	// }
	//
	// }
	// }

	// private OnItemSelectedListener mOnItemSelectedListener = new
	// OnItemSelectedListener() {
	//
	// @Override
	// public void onItemSelected(AdapterView<?> parent, View view, int
	// position, long id) {
	// // TODO Auto-generated method stub
	// creatPoint(position);
	// if (position == bannerList.size() - 1) {
	// Message msg = handler.obtainMessage();
	// msg.what = 5;
	// handler.sendMessageDelayed(msg, 3000);
	// }
	// }
	//
	// @Override
	// public void onNothingSelected(AdapterView<?> parent) {
	// // TODO Auto-generated method stub
	//
	// }
	// };

	/**
	 * 升级信息请求
	 */
	private void requestUpdateInfo() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				updateHandler = new UpdateHandler();
				Tools.requestToParse(WelcomeActivity.this, ConstantUrl.UPDATE, "Update", null, updateHandler, false);

				if (Tools.responseValue == 1 && updateHandler.result_code == 1) {
					handler.sendEmptyMessage(UPDATE);
				}
			}
		}).start();
	}
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
}
