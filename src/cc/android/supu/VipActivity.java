package cc.android.supu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.adapter.IndexProductAdapter;
import cc.android.supu.bean.IndexGoodBean;
import cc.android.supu.bean.MemberBean;
import cc.android.supu.handler.IndexGoodsHandler;
import cc.android.supu.handler.LoginHandler;
import cc.android.supu.tools.AsyncImageLoader;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.PublicParams;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;

/**
 * @author sss 会员中心
 */
public class VipActivity extends BaseActivity {
	/** 成功 */
	private final static int GOODS_SUCCESS = 1;
	/** 失败 */
	private final static int GOODS_FAIL = 0;
	/** 无落网 */
	private final static int GOODS_ERROR = -1;

	/** 用户名 积分 账户余额 */
	private TextView vipName, vipScores, vipPrice;
	/** 用户头像 */
	private ImageView userPhoto;
	/** 小星星 */
	private ImageView Star;
	/** 订单 收藏 消息 优惠劵 地址管理 */
	private RelativeLayout layout_order, layout_collect, layout_message,
			layout_preferential, layout_adress;
	/** 用户名字后面的星星布局 */
	private ImageView ViplayoutTop;

	/** 解析下面商品的解析类 */
	private IndexGoodsHandler goodsHandler;

	/** 所有商品列表控件 */
	private ExpandableListView productListView;
	/** 适配器 */
	private IndexProductAdapter productAdapter;
	/** 会员中心的滚动布局 */
	private ScrollView vipScrollView;
	/** 中心下面的商品list */
	private LinkedHashMap<String, ArrayList<IndexGoodBean>> categoryGoods;
	/** 用户名 */
	private String account;
	/** 密码 */
	private String password;
	/** 剩余钱 */
	private String price;
	/** 用户头像 */
	private String userImage;
	/** 积分 */
	private int scores;
	/** 传给服务器的键值对 */
	private TreeMap<String, String> params;
	/** 登录handler */
	private LoginHandler LoginHandler;
	/** 头像的默认图片 */
	private Bitmap bitmap1;

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.vipcenter;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "会员中心";
	}

	@Override
	protected String setEnterBtn() {
		return "注销";
	}

	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		// getData();
		bitmap1 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.userphoto);
		vipName = (TextView) findViewById(R.id.vipName);
		vipScores = (TextView) findViewById(R.id.vipScores);
		vipPrice = (TextView) findViewById(R.id.vipPrice);
		userPhoto = (ImageView) findViewById(R.id.userPhoto);
		ViplayoutTop = (ImageView) findViewById(R.id.ViplayoutTop);
		vipScrollView = (ScrollView) findViewById(R.id.vipScrollView);
		// requestServer(requestLogin);

		goodsHandler = new IndexGoodsHandler();

		productListView = (ExpandableListView) findViewById(R.id.userproductgoods);
		productListView.setOnGroupClickListener(groupClickListener);

		layout_order = (RelativeLayout) findViewById(R.id.layout_order);
		layout_order.setOnClickListener(onClickLister);
		layout_collect = (RelativeLayout) findViewById(R.id.layout_collect);
		layout_collect.setOnClickListener(onClickLister);
		layout_message = (RelativeLayout) findViewById(R.id.layout_message);
		layout_message.setOnClickListener(onClickLister);
		layout_preferential = (RelativeLayout) findViewById(R.id.layout_preferential);
		layout_preferential.setOnClickListener(onClickLister);
		layout_adress = (RelativeLayout) findViewById(R.id.layout_adress);
		layout_adress.setOnClickListener(onClickLister);
		// requestServer(topGoodsRequest, false);
	}

	/**
	 * 为页面赋值
	 */
	private void setText() {
		vipName.setText("用户名：" + account + "  ");
		vipScores.setText("积分：" + scores + "");
		vipPrice.setText("现金账户余额：" + price + "");
		if (userImage != null) {
			System.out.println("userImage" + userImage);
			userPhoto.setTag(userImage);
			Bitmap bitmap = AsyncImageLoader.loadIndexDrawable(
					VipActivity.this, userImage,
					new AsyncImageLoader.ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap, String imageUrl) {
							String imgUrl = (String) userPhoto.getTag();
							if (bitmap != null && imageUrl.equals(imgUrl)) {
								userPhoto.setImageBitmap(bitmap);
							}
						}
					}, true, true, 1, false);
			if (bitmap != null) {
				userPhoto.setImageBitmap(bitmap);
			} else {
				userPhoto.setImageBitmap(bitmap1);
			}
		}

	}

	/**
	 * 得到用户的数据
	 */
	private void getData() {
		MemberBean memberBean = UserInfoTools.getUserBean(VipActivity.this);
		account = memberBean.getAccount();
		password = memberBean.getPassword();
	}

	/**
	 * 注册按钮的请求
	 */
	private PageRequest requestLogin = new PageRequest() {

		@Override
		public void requestServer() {
			params = new TreeMap<String, String>();
			params.put("Account", account);
			params.put("Password", password);
			LoginHandler = new LoginHandler();
			Tools.requestToParse(VipActivity.this, ConstantUrl.LOGINURL,
					"Login", params, LoginHandler, false);
			if (Tools.responseValue == 1) {
				if (LoginHandler.result_code == 0) {
					handler.sendMessage(handler.obtainMessage(STATE_SUCCESS));
				} else {
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE));
				}
			} else {
				handler.sendMessage(handler.obtainMessage(STATE_ERROR));
			}
		}
	};
	/**
	 * 获取促销商品的请求
	 */
	protected PageRequest topGoodsRequest = new PageRequest() {

		@Override
		public void requestServer() {
			Tools.requestToParse(VipActivity.this,
					ConstantUrl.GETMEMBERTOPGOODS, "GetMemberTopGoods", null,
					goodsHandler, false);
			if (Tools.responseValue == 1) {
				handler.sendEmptyMessage(GOODS_SUCCESS);
			} else if (Tools.responseValue == 2) {
				handler.sendEmptyMessage(GOODS_FAIL);
			} else {
				handler.sendEmptyMessage(GOODS_ERROR);
			}
		}
	};
	/**
	 * 监听事件
	 */
	Button.OnClickListener onClickLister = new Button.OnClickListener() {// 创建监听对象
		@Override
		public void onClick(View v) {
			Intent intent = null;
			switch (v.getId()) {
			case R.id.layout_order:
				intent = new Intent(VipActivity.this, OrderActivity.class);
				startActivity(intent);
				break;
			case R.id.layout_collect:
				intent = new Intent(VipActivity.this, FavoritesActivity.class);
				startActivity(intent);
				break;
			case R.id.layout_message:
				intent = new Intent(VipActivity.this, UserMessageActivity.class);
				startActivity(intent);
				break;
			case R.id.layout_preferential:
				intent = new Intent(VipActivity.this, TicketListActivity.class);
				intent.putExtra("shopCart", "");
				startActivity(intent);
				break;
			case R.id.layout_adress:
				intent = new Intent(VipActivity.this, AdressListActivity.class);
				intent.putExtra("TYPE", 1);
				startActivity(intent);
				break;
			default:
				break;
			}
		}

	};

	@Override
	int setBottomIndex() {
		return 3;
	}

	@Override
	protected void onResume() {
		super.onResume();
		getData();
		requestServer(requestLogin);
		requestServer(topGoodsRequest, false);
	}

	@Override
	protected void enterBtnOnClick() {
		// 这里清楚数据的代码
		clearData();
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra("shopcart", "");
		startActivity(intent);
		this.finish();
	}

	@Override
	protected void dealwithMessage(Message msg) {
		switch (msg.what) {
		case GOODS_SUCCESS:
			categoryGoods = goodsHandler.getCategoryGoods();
			productAdapter = new IndexProductAdapter(this, categoryGoods);
			productListView.setAdapter(productAdapter);
			for (int i = 0; i < categoryGoods.size(); i++) {
				productListView.expandGroup(i);
			}
			vipScrollView.fullScroll(View.FOCUS_UP);
			break;
		case GOODS_FAIL:
			Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case GOODS_ERROR:
			Toast.makeText(this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
			break;
		case STATE_SUCCESS:
			getHandler();
			break;
		case STATE_FAILURE:
			Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	/**
	 * 得到登录后的数据
	 */
	private void getHandler() {
		if (LoginHandler == null)
			return;
		price = LoginHandler.price;
		scores = LoginHandler.scores;
		userImage = LoginHandler.imageUrl;

		setText();
		starImage(LoginHandler.level);
		PublicParams.setMEMBERID(LoginHandler.memberId);

		MemberBean memberBean = new MemberBean();
		memberBean.setAccount(account);
		memberBean.setPassword(password);
		memberBean.setMemberId(LoginHandler.memberId);
		memberBean.setLevel(LoginHandler.level);
		memberBean.setPrice(LoginHandler.price);
		memberBean.setScores(LoginHandler.scores);
		// 登录成功后调用 这2个方法 保存用户数据
		UserInfoTools.saveUserInfo(this, memberBean, true);
	}

	/**
	 * 头像后面的小星星
	 */
	private void starImage(int count) {
		switch (count) {
		case 1:
			ViplayoutTop.setBackgroundResource(R.drawable.one);
			break;
		case 2:
			ViplayoutTop.setBackgroundResource(R.drawable.two);
			break;
		case 3:
			ViplayoutTop.setBackgroundResource(R.drawable.three);
			break;
		case 4:
			ViplayoutTop.setBackgroundResource(R.drawable.four);
			break;
		default:
			break;
		}
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// 按下返回键
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			Intent intent = new Intent(this, IndexActivity.class);
//			startActivity(intent);
//			this.finish();
//		}
//
//		return false;
//	}

	/**
	 * 清除数据
	 */
	private void clearData() {
		UserInfoTools.cleanUserInfo(VipActivity.this);
		PublicParams.setMEMBERID("");
	}

	/** 点击不关闭子ListView */
	private OnGroupClickListener groupClickListener = new OnGroupClickListener() {

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			return true;
		}
	};
}
