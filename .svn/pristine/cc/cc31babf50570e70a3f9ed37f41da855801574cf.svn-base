package cc.android.supu;

import java.util.ArrayList;
import java.util.TreeMap;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cc.android.supu.adapter.GreedInfoAapter;
import cc.android.supu.bean.ArticleGoodsBean;
import cc.android.supu.handler.GreedInfoHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

/**
 * 
 * @author zsx 宝典的列表页
 * 
 */
public class GreedInfoActivity extends BaseActivity {
	/** 上下文 */
	private Context context = GreedInfoActivity.this;
	/** 上部的文本 */
	private TextView topGreedTitle;
	private WebView topGreedText;
	/** 下部的列表 */
	private ListView greedInfoList;
	/** 适配器 */
	private GreedInfoAapter greedInfoAapter;
	/** 传给服务器的键值对 */
	private TreeMap<String, String> params;
	/** 孕婴列表 */
	public ArrayList<ArticleGoodsBean> goodsList;
	/** 处理列表的handler */
	private GreedInfoHandler greedInfoHandler;
	/** 宝典Id */
	private String id = "";

	private LinearLayout topGreedLinearLayout, isShowLayout;
	/** 分享底部弹出的popupWindow */
	private PopupWindow popupWindow;
	/** 用于分享底部弹出的popupWindow的view的创建 */
	private LayoutInflater inflater;

	@Override
	protected void initPage() {
		if (getIntent() != null) {
			id = getIntent().getExtras().getString("Id");
		}
		topGreedLinearLayout = (LinearLayout) findViewById(R.id.topGreedLinearLayout);
		isShowLayout = (LinearLayout) findViewById(R.id.isShowLayout);
		topGreedText = (WebView) findViewById(R.id.topGreedText);
		topGreedTitle = (TextView) findViewById(R.id.topGreedTitle);
		greedInfoList = (ListView) findViewById(R.id.greedInfoList);
		ListenerItem();
		goodsList = new ArrayList<ArticleGoodsBean>();

		initAnimation();
		requestServer(requestGreedInfo);
	}

	/**
	 * 子列表响应事件
	 */
	private void ListenerItem() {

		greedInfoList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(GreedInfoActivity.this,
						GoodsDetailsActivity.class);
				intent.putExtra("goodsSN", goodsList.get(arg2).GoodsSN);
				intent.putExtra("imgFile", goodsList.get(arg2).ImgFile);
				GreedInfoActivity.this.startActivity(intent);

			}
		});

	}

	// @Override
	// protected void enterBtnOnClick() {
	// // TODO Auto-generated method stub
	// // Intent intent = new Intent(context, WeiboActivity.class);
	// // intent.putExtra("shareText", goodsDetail.shareText);
	// // startActivity(intent);
	// }
	/**
	 * 服务器的请求
	 */
	private PageRequest requestGreedInfo = new PageRequest() {

		@Override
		public void requestServer() {
			params = new TreeMap<String, String>();
			params.put("Id", id + "");

			greedInfoHandler = new GreedInfoHandler();
			Tools.requestToParse(GreedInfoActivity.this,
					ConstantUrl.GETARTICLEINFO, "GetArticleInfo", params,
					greedInfoHandler, false);
			if (Tools.responseValue == 1) {
				if (greedInfoHandler.result_code == 0) {
					handler.sendMessage(handler.obtainMessage(STATE_SUCCESS));
				} else {
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE));
				}
			} else {
				handler.sendMessage(handler.obtainMessage(STATE_ERROR));
			}
		}
	};

	@Override
	protected void dealwithMessage(android.os.Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			setText();
			if (greedInfoHandler.goodsList != null
					&& greedInfoHandler.goodsList.size() > 0) {
				goodsList = greedInfoHandler.goodsList;
				greedInfoAapter = new GreedInfoAapter(GreedInfoActivity.this,
						goodsList);
				// setListViewHeight();
				greedInfoList.setAdapter(greedInfoAapter);
			}
			new Handler().postDelayed(new Runnable() {
				public void run() {
					topGreedLinearLayout.setVisibility(View.VISIBLE);
				}
			}, 300);
			new Handler().postDelayed(new Runnable() {
				public void run() {
					greedInfoList.setVisibility(View.VISIBLE);
				}
			}, 500);
			break;
		case STATE_FAILURE:

			break;
		case STATE_ERROR:

			break;

		default:
			break;
		}
	};

	/**
	 * 设置标题
	 */
	private void setText() {
		String title = greedInfoHandler.Title;
		String content = greedInfoHandler.Content;
		topGreedText.loadDataWithBaseURL(null, content, "text/html", "utf-8",
				null);
		topGreedTitle.setText(title);
	}

	@Override
	protected void onSubActivityClick(android.view.View view) {
		Intent intent = null;
		switch (view.getId()) {
		// 新浪微博
		case R.id.share_sina:
			intent = new Intent(context, WeiboActivity.class);
			intent.putExtra("weibo", "sina");
			startActivity(intent);
			break;
		// 腾讯微博
		case R.id.share_qq:
			intent = new Intent(context, WeiboActivity.class);
			intent.putExtra("weibo", "qq");
			startActivity(intent);
			break;
		// 人人
		case R.id.share_renren:
			intent = new Intent(context, WeiboActivity.class);
			intent.putExtra("weibo", "renren");
			startActivity(intent);
			break;
		// 取消
		case R.id.share_cancle:
			openShareMenu();
			break;
		}
	};

	/**
	 * popupWindow的动画
	 */
	private void initAnimation() {
		inflater = LayoutInflater.from(context);
		View popupView = inflater.inflate(R.layout.sharemenu, null);
		popupWindow = new PopupWindow(popupView, LayoutParams.FILL_PARENT, 400,
				false);
		TextView sinaBtn = (TextView) popupView.findViewById(R.id.share_sina);
		TextView qqBtn = (TextView) popupView.findViewById(R.id.share_qq);
		TextView renrenBtn = (TextView) popupView
				.findViewById(R.id.share_renren);
		TextView cancleBtn = (TextView) popupView
				.findViewById(R.id.share_cancle);
		sinaBtn.setOnClickListener(this);
		qqBtn.setOnClickListener(this);
		renrenBtn.setOnClickListener(this);
		cancleBtn.setOnClickListener(this);
	}

	/**
	 * 标记是否显示分享
	 */
	private boolean flag;

	public void openShareMenu() {
		if (!flag) {
			popupWindow.setAnimationStyle(R.style.dialog_animation);
			popupWindow.showAtLocation(topGreedText, Gravity.BOTTOM, 0, 0);
			popupWindow.setFocusable(true);
			popupWindow.update();
			flag = true;
		} else {
			popupWindow.dismiss();
			popupWindow.setFocusable(false);
			flag = false;
		}
	}

	@Override
	protected String setTitle() {
		return "孕婴宝典";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	// @Override
	// protected String setEnterBtn() {
	// return "分享";
	// }

	@Override
	int setLayout() {
		return R.layout.greedinfo;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 4;
	}

	// /**
	// * 设置ListView的高度（动态的）
	// * */
	// public void setListViewHeight() {
	// int tatalHeight = 0;
	// int itemHeight = 0;
	// int addHeight = 0;
	// DisplayMetrics metrics = this.getResources().getDisplayMetrics();
	// itemHeight = metrics.heightPixels;
	// if (0 <= itemHeight && itemHeight <= 500) {
	// addHeight = 85;
	// } else if (500 <= itemHeight && itemHeight <= 850) {
	// addHeight = 106;
	// } else {
	// addHeight = 120;
	// }
	// for (int i = 0; i < goodsList.size(); i++) {
	// tatalHeight += addHeight;
	// }
	// // LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)
	// greedInfoList
	// // .getLayoutParams();
	// // linearParams.height = tatalHeight;
	// // greedInfoList.setLayoutParams(linearParams);
	// }
}
