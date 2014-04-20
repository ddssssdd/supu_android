package cc.android.supu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.umeng.analytics.MobclickAgent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.adapter.GoodsDetailGalleryAdapter;
import cc.android.supu.bean.ArticleGoodsBean;
import cc.android.supu.bean.GoodsBean;
import cc.android.supu.bean.GoodsImageBean;
import cc.android.supu.datebase.RecentViewDbHelper;
import cc.android.supu.handler.DefaultJSONData;
import cc.android.supu.handler.GoodsActivitiesHandler;
import cc.android.supu.handler.GoodsDetailsHandler;
import cc.android.supu.handler.ModifyShopCartHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.ThreadInter;
import cc.android.supu.tools.ThreadManage;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;
import cc.android.supu.view.DrawLineTextView;
import cc.android.supu.view.GoodsDetailGallery;

/**
 * 商品详情页面
 * 
 * @author sheng
 * 
 */
public class GoodsDetailsActivity extends BaseActivity implements ThreadInter {

	private static final int STATE_SUCCESS_GOODSDETAIL = 13579;
	private static final int STATE_SUCCESS_ADDSHOPCART = 3579;
	private static final int STATE_SUCCESS_ADDFAVORIT = 5179;
	private static final int STATE_SUCCESS_ACTIVITIES = 5719;
	private static final int STATE_FAILURE_GOODSDETAIL = 97531;
	private static final int STATE_FAILURE_ADDSHOPCART = 9753;
	private static final int STATE_FAILURE_ADDFAVORIT = 9175;

	private LinearLayout layout;
	/** 顶部放置图片的Gallery */
	private GoodsDetailGallery mGallery;
	/** 圆点的布局 */
	private HashMap<Integer, GoodsImageBean> map;
	private LinearLayout dotLayout;
	/** 商品名、广告语、商品编号、市场价、为您节省、速普价、资讯数量、评论数量 */
	private TextView goodsNameTv, goodsSloganTv, goodsSNTv, saveMoneyTv,
			shopPriceTv, consultCountTv, commentCountTv;
	/** 显示市场价 */
	private DrawLineTextView marketPriceTv;
	/** 收藏、加入购物车按钮 */
	private TextView collectBtn, addShopCartBtn;
	/** 优惠活动、商品描述、评论、资讯 */
	private RelativeLayout activitiesLayout, descriptionLayout, commentLayout,
			consultLayout;
	/** 优惠活动上面的线 */
	private ImageView lineImage;
	/** 商品详细信息解析器 */
	private GoodsDetailsHandler jsonData;
	/** 加入购物车的信息解析器 */
	private ModifyShopCartHandler addHandlerJsonData;
	/** 收藏的信息解析器 */
	private DefaultJSONData addFavoriteData;
	/** 可享受的优惠活动的信息解析器 */
	private GoodsActivitiesHandler activitiesHandler;
	private GoodsDetailsActivity context = this;
	/** Gallery的适配器 */
	// private GoodsDetailGalleryAdapter adapter;
	/** 商品编号 */
	private String goodsSN;
	/** 商品图片 */
	private String imgFile;
	/** 添加到购物车时上传的参数 */
	private String goods;
	/** 商品详细bean */
	private GoodsBean goodsDetail;
	/** 图片list */
	// private ArrayList<GoodsImageBean> goodsImagList;
	/** 圆点list */
	private ArrayList<TextView> dotList;
	/** 圆点位置 */
	private int recorddot = 0;
	/** 最近浏览的列表 */
	private ArrayList<ArticleGoodsBean> recentlist;
	// /** 图片在mGallery中的位置*/
	// private int imgPosition;
	/** 商品名称 */
	private String reName = null;
	/** 商品名称 */
	private String Price = null;

	// private int imgCount = 0;

	@Override
	public void Threading() {

		if (IsSave(reName)) {
			addRecent();
		} else {
			insert();
		}
	}

	/**
	 * 插入的方法
	 */
	private void insert() {
		try {
			RecentViewDbHelper recentViewDbHelper = new RecentViewDbHelper(this);
			ArticleGoodsBean articleGoodsBean = new ArticleGoodsBean();
			articleGoodsBean.GoodsSN = goodsSN;
			// if (imgFile == null) {
			// if (jsonData != null && jsonData.smallImagUrls.size() > 0)
			// imgFile = jsonData.smallImagUrls.get(0);
			// }
			articleGoodsBean.ImgFile = imgFile;
			articleGoodsBean.GoodsName = reName;
			articleGoodsBean.Price = Price;
			articleGoodsBean.GoodTime = getStringDateShort();
//			System.out.println("GoodsName" + reName);
//			System.out.println("ImgFilesss" + imgFile);
			recentViewDbHelper.updateGoods(articleGoodsBean);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void initPage() {

		recentlist = new ArrayList<ArticleGoodsBean>();
		layout = (LinearLayout) findViewById(R.id.goodsdetails_layout);
		mGallery = (GoodsDetailGallery) findViewById(R.id.goodsdetails_gallery);
		mGallery.setOnItemSelectedListener(mOnItemSelectedListener);

		mGallery.setOnItemClickListener(itemClickListener);
		// mGallery.setOnTouchListener(mOnTouchListener);
		dotLayout = (LinearLayout) findViewById(R.id.goodsdetails_dotLayout);

		goodsNameTv = (TextView) findViewById(R.id.goodsdetails_goodsName);
		goodsSloganTv = (TextView) findViewById(R.id.goodsdetails_goodsSlogan);
		goodsSNTv = (TextView) findViewById(R.id.goodsdetails_goodsSN);
		marketPriceTv = (DrawLineTextView) findViewById(R.id.goodsdetails_marketPrice);
		saveMoneyTv = (TextView) findViewById(R.id.goodsdetails_saveMoney);
		shopPriceTv = (TextView) findViewById(R.id.goodsdetails_shopPrice);
		consultCountTv = (TextView) findViewById(R.id.goodsdetails_ConsultCount);
		commentCountTv = (TextView) findViewById(R.id.goodsdetails_CommentCount);

		collectBtn = (TextView) findViewById(R.id.goodsdetails_collect);
		addShopCartBtn = (TextView) findViewById(R.id.goodsdetails_addShopCart);

		activitiesLayout = (RelativeLayout) findViewById(R.id.goodsdetails_activitise);
		descriptionLayout = (RelativeLayout) findViewById(R.id.goodsdetails_Description);
		commentLayout = (RelativeLayout) findViewById(R.id.goodsdetails_Comment);
		consultLayout = (RelativeLayout) findViewById(R.id.goodsdetails_Consult);
		lineImage = (ImageView) findViewById(R.id.goodsdetails_activitesTop_line);

		collectBtn.setOnClickListener(this);
		addShopCartBtn.setOnClickListener(this);
		activitiesLayout.setOnClickListener(this);
		descriptionLayout.setOnClickListener(this);
		commentLayout.setOnClickListener(this);
		consultLayout.setOnClickListener(this);

		// mGallery.setSpacing(30);

		if (getIntent() != null) {
			goodsSN = getIntent().getStringExtra("goodsSN");
			imgFile = getIntent().getStringExtra("imgFile");
			System.out.println("11111111111111");
			System.out.println("imgFile" + imgFile);
			System.out.println("2222222222222");
		}
		requestServer(requestGoodsDetails, true);
		requestServer(requestGoodsActivities);

	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "商品详情";
	}

	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}

	@Override
	protected void backBtnOnClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	protected String setEnterBtn() {
		// TODO Auto-generated method stub
		return "分享";
	}

	@Override
	protected void enterBtnOnClick() {
		// TODO Auto-generated method stub
		if (goodsDetail != null) {
			Intent intent = new Intent(context, WeiboActivity.class);
			intent.putExtra("shareText", goodsDetail.shareText);
			startActivity(intent);
		}
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.goodsdetails;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 1;
	}

	protected void onSubActivityClick(android.view.View view) {
		Intent intent = null;
		switch (view.getId()) {
		// 收藏
		case R.id.goodsdetails_collect:
			if (UserInfoTools.isLogin(context)) {
				MobclickAgent.onEvent(this, "um_collect"); 
				requestServer(requestAddFavorites);
			} else {
				showDialog();
			}

			break;
		// 加入购物车
		case R.id.goodsdetails_addShopCart:
			addShopCart();
			break;
		// 优惠活动
		case R.id.goodsdetails_activitise:
			intent = new Intent(context, GoodsActivitiesActivity.class);
			intent.putExtra("goodsSN", goodsSN);
			startActivity(intent);
			break;
		// 商品描述
		case R.id.goodsdetails_Description:
			intent = new Intent(context, GoodsDescriptionActivity.class);
			intent.putExtra("goodsSN", goodsSN);
			startActivity(intent);
			break;
		// 评论
		case R.id.goodsdetails_Comment:
			intent = new Intent(context, GoodsCommentActivity.class);
			intent.putExtra("goodsSN", goodsSN);
			startActivity(intent);
			break;
		// 资讯
		case R.id.goodsdetails_Consult:
			intent = new Intent(context, GoodsConsultActivity.class);
			intent.putExtra("goodsSN", goodsSN);
			startActivity(intent);
			break;
		}
	};

	/** 商品详情请求 */
	PageRequest requestGoodsDetails = new PageRequest() {

		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("GoodsSN", goodsSN);
			jsonData = new GoodsDetailsHandler();
			Tools.requestToParse(context, ConstantUrl.GETGOODFSDETAILS,
					"GetGoodsDetails", params, jsonData, false);
			if (Tools.responseValue == 1) {
				if (jsonData.result_code == 0) {
					if (jsonData.smallImagUrls.size() > 0) {
						if (imgFile == null) {
							imgFile = jsonData.smallImagUrls.get(0);
//							System.out.println("STATE_SUCCESS_GOODSDETAIL"
//									+ imgFile);
						}
					}
					handler.sendEmptyMessage(STATE_SUCCESS_GOODSDETAIL);
				} else {
					handler.sendEmptyMessage(STATE_FAILURE_GOODSDETAIL);
				}
			} else {
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};

	/** 加入购物车请求 */
	PageRequest requestAddShopCart = new PageRequest() {

		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("goods", goods);

			addHandlerJsonData = new ModifyShopCartHandler();
			Tools.requestToParse(context, ConstantUrl.GETMODIFYSHOPPINGCART,
					"ModifyShoppingCart", params, addHandlerJsonData, false);
			if (Tools.responseValue == 1) {
				if (addHandlerJsonData.result_code == 0) {
					handler.sendEmptyMessage(STATE_SUCCESS_ADDSHOPCART);
				} else {
					handler.sendEmptyMessage(STATE_FAILURE_ADDSHOPCART);
				}
			} else {
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};

	/** 添加到收藏夹请求 */
	PageRequest requestAddFavorites = new PageRequest() {

		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("GoodsSN", goodsSN);

			addFavoriteData = new DefaultJSONData();
			Tools.requestToParse(context, ConstantUrl.ADDFAVORITES,
					"AddFavorites", params, addFavoriteData, false);
			if (Tools.responseValue == 1) {
				if (addFavoriteData.result_code == 0) {
					handler.sendEmptyMessage(STATE_SUCCESS_ADDFAVORIT);
				} else {
					handler.sendEmptyMessage(STATE_FAILURE_ADDFAVORIT);
				}
			} else {
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};

	/**
	 * 请求可享受的优惠活动
	 */
	PageRequest requestGoodsActivities = new PageRequest() {

		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("GoodsSN", goodsSN);
			activitiesHandler = new GoodsActivitiesHandler();
			Tools.requestToParse(GoodsDetailsActivity.this,
					ConstantUrl.GETGOODSACTIVITIES, "GetGoodsActivities",
					params, activitiesHandler, false);
			if (Tools.responseValue == 1) {
				if (activitiesHandler.result_code == 0) {
					handler.sendEmptyMessage(STATE_SUCCESS_ACTIVITIES);
				} else {
					handler.sendEmptyMessage(STATE_FAILURE);
				}
			} else {
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};

	protected void dealwithMessage(android.os.Message msg) {

		switch (msg.what) {
		case STATE_SUCCESS_GOODSDETAIL:
			goodsDetail = jsonData.goodsDetail;
			setTextContent();
			if (jsonData.smallImagUrls != null) {
				int count = setGalleryContent(jsonData.smallImagUrls);
				showDot(count);
			}
			if (goodsDetail != null) {
				isNoStock(goodsDetail.isNoStock);
				reName = goodsDetail.goodsName;
				Price = goodsDetail.shopPrice;
				ThreadManage.addTask(GoodsDetailsActivity.this);
				ThreadManage.start();
			}
			layout.setVisibility(View.VISIBLE);// 待数据加载完成再显示整个页面
			break;
		case STATE_FAILURE_GOODSDETAIL:
			if (jsonData.result_code == 1) {
				Toast.makeText(context, "抱歉，您所查看的商品不存在！", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(context, "服务器异常！", Toast.LENGTH_SHORT).show();
			}
			break;
		case STATE_SUCCESS_ADDFAVORIT:
			Toast.makeText(context, "收藏成功！", Toast.LENGTH_SHORT).show();
			changeCollectBtnState();
			break;
		case STATE_FAILURE_ADDFAVORIT:
			if (addFavoriteData.result_code == 1) {
				Toast.makeText(context, "此商品已存在于收藏夹！", Toast.LENGTH_SHORT)
						.show();
				changeCollectBtnState();
			} else if (addFavoriteData.result_code == 100) {
				showDialog();
			} else {
				Toast.makeText(context, "服务器异常！", Toast.LENGTH_SHORT).show();
			}
			break;
		case STATE_SUCCESS_ADDSHOPCART:
			if (addHandlerJsonData.list == null) {
				Toast.makeText(context, "加入购物车失败！", Toast.LENGTH_SHORT).show();
				return;
			}

			GoodsBean goodsBean = addHandlerJsonData.list.get(0);
			if (goodsBean != null) {
				if (goodsBean.isNoStock) {
					Toast.makeText(context, "加入购物车失败，此商品缺货！",
							Toast.LENGTH_SHORT).show();
					isNoStock(goodsBean.isNoStock);
				} else {
					Toast.makeText(context, "加入购物车成功！", Toast.LENGTH_SHORT)
							.show();
					// addShopCartBtn.setClickable(true);
					isNoStock(goodsBean.isNoStock);
				}
			}

			break;
		case STATE_FAILURE_ADDSHOPCART:
			Toast.makeText(context, "加入购物车失败！", Toast.LENGTH_SHORT).show();
			break;

		case STATE_SUCCESS_ACTIVITIES:
			if (activitiesHandler.list == null
					|| activitiesHandler.list.size() == 0) {
				// activitiesLayout.setVisibility(View.GONE);
				lineImage.setVisibility(View.GONE);
			} else {
				activitiesLayout.setVisibility(View.VISIBLE);
			}

			break;
		case STATE_ERROR:
			Toast.makeText(context, "请求异常！", Toast.LENGTH_SHORT).show();
			break;
		}

	};

	/**
	 * 是否缺货，缺货隐藏加入购物车按钮
	 */
	private void isNoStock(boolean isNoStock) {
		if (isNoStock) {
			addShopCartBtn.setBackgroundResource(R.drawable.logisticsing);
			addShopCartBtn.setTextColor(Color.BLACK);
			addShopCartBtn.setClickable(false);
		} else {
			addShopCartBtn.setBackgroundResource(R.anim.logistics_bg);
			addShopCartBtn.setClickable(true);
			addShopCartBtn
					.setOnFocusChangeListener(new OnFocusChangeListener() {

						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							// TODO Auto-generated method stub
							addShopCartBtn.setTextColor(hasFocus ? Color.BLACK
									: Color.WHITE);
						}
					});
		}
	}

	/**
	 * 添加最近的浏览
	 */
	private void addRecent() {
		try {
			RecentViewDbHelper recentViewDbHelper = new RecentViewDbHelper(this);
			SQLiteDatabase db = recentViewDbHelper.openDb();
			List<ArticleGoodsBean> value = new ArrayList<ArticleGoodsBean>();
			ArticleGoodsBean articleGoodsBean = new ArticleGoodsBean();
			articleGoodsBean.GoodsSN = goodsSN;
			// if (imgFile == null) {
			// if (jsonData != null && jsonData.smallImagUrls.size() > 0)
			// imgFile = jsonData.smallImagUrls.get(0);
			// }
			articleGoodsBean.ImgFile = imgFile;
			articleGoodsBean.GoodsName = reName;
			articleGoodsBean.Price = Price;
			articleGoodsBean.GoodTime = getStringDateShort();
//			System.out.println("GoodsName" + reName);
//			System.out.println("ImgFileAAA" + imgFile);
			value.add(articleGoodsBean);
			recentViewDbHelper.saveSomeDatas(db, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	public String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 是否保存
	 * 
	 * @return
	 */
	private boolean IsSave(String goodName) {
		if (goodName == null)
			return false;
		RecentViewDbHelper recentViewDbHelper = new RecentViewDbHelper(this);
		recentlist = recentViewDbHelper.queryAllCity();
		if (recentlist != null && recentlist.size() > 0) {
			for (int i = 0; i < recentlist.size(); i++) {
				if (recentlist.get(i).GoodsName.equals(goodName)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 改变收藏按钮的状态
	 */
	private void changeCollectBtnState() {
		collectBtn.setBackgroundResource(R.drawable.collect_touching_btn);
		collectBtn.setTextColor(Color.BLACK);
		collectBtn.setClickable(false);
	}

	/**
	 * 设置文本内容
	 */
	private void setTextContent() {

		if (goodsDetail != null) {
			if (goodsDetail.goodsSlogan == null
					|| goodsDetail.goodsSlogan.equals("")) {// 没有优惠活动时隐藏的控件
				goodsSloganTv.setVisibility(View.GONE);
			}
			goodsNameTv.setText(goodsDetail.goodsName);
			goodsSloganTv.setText(goodsDetail.goodsSlogan);
			goodsSNTv.setText(goodsDetail.goodsSN);
			marketPriceTv.setText(goodsDetail.marketPrice);
			shopPriceTv.setText(goodsDetail.shopPrice);
			consultCountTv.setText("（" + goodsDetail.consultCount + "）");
			commentCountTv.setText("（" + goodsDetail.commentCount + "）");
			saveMoneyTv.setText(getSaveMoney() + "元");
		}

	}

	/**
	 * 给Gallery设置adapter
	 */
	private int setGalleryContent(ArrayList<String> smallUrls) {
		if (smallUrls != null) {
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			GoodsDetailGalleryAdapter adapter = new GoodsDetailGalleryAdapter(
					context, smallUrls, metrics);
			mGallery.setAdapter(adapter);
			return adapter.getCount();
		}
		return 0;
	}

	/**
	 * 将商品加入购物车
	 */
	private void addShopCart() {
		if (UserInfoTools.isLogin(this)) {
			MobclickAgent.onEvent(this, "um_addcart"); 
			goods = goodsSN + ":1:+=";
			requestServer(requestAddShopCart, true);
		} else {
			showDialog();
		}
	}

	/**
	 * 显示圆点
	 */
	private void showDot(int count) {
		if (count > 1) {
			// 圆点的数量
			dotLayout.removeAllViews();
			dotList = new ArrayList<TextView>();
			// 循化生成圆点dot，并添加到dotLayout中
			for (int i = 0; i < count; i++) {
				TextView dot = new TextView(context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						12, 12);
				lp.leftMargin = 5;
				dot.setLayoutParams(lp);
				dot.setBackgroundResource(R.drawable.point_gray);
				dotLayout.addView(dot);
				dotList.add(dot);
			}
		}
	}

	/**
	 * gallery的item选中时的监听器
	 */
	OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			if (dotList != null && dotList.size() > 0) {
				if (dotList.size() > recorddot) {
					dotList.get(recorddot).setBackgroundResource(
							R.drawable.point_gray);
				}
				if (dotList.size() > position) {// 将选中的dot设置为红色
					dotList.get(position).setBackgroundResource(
							R.drawable.point_red);
					recorddot = position;
				}
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * gallery点击事件
	 */
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			int x = mGallery.x;
			int y = mGallery.y;
			Rect rect = new Rect();
			mGallery.getGlobalVisibleRect(rect);
			y = y + rect.top;
			ImageView img1 = (ImageView) view
					.getTag(R.id.tag_goodsdetail_item_img1);
			ImageView img2 = (ImageView) view
					.getTag(R.id.tag_goodsdetail_item_img2);
			if (img1 != null) {
				Rect rect1 = new Rect();
				img1.getGlobalVisibleRect(rect1);
				if (x > rect1.left && x < rect1.right && y > rect1.top
						&& y < rect1.bottom) {
					// System.out.println("-----------------");
					position = position * 2;
					Intent intent = new Intent(GoodsDetailsActivity.this,
							ProductBigImageActivity.class);
					// intent.putStringArrayListExtra("images", getImageList());
					intent.putStringArrayListExtra("images",
							jsonData.bigImagUrls);
					intent.putExtra("position", position);
					startActivity(intent);
				}
			}

			if (img2 != null) {
				Rect rect2 = new Rect();
				img2.getGlobalVisibleRect(rect2);
				if (x > rect2.left && x < rect2.right && y > rect2.top
						&& y < rect2.bottom) {
					position = position * 2 + 1;
					Intent intent = new Intent(GoodsDetailsActivity.this,
							ProductBigImageActivity.class);
					intent.putStringArrayListExtra("images",
							jsonData.bigImagUrls);
					// intent.putStringArrayListExtra("images", getImageList());
					intent.putExtra("position", position);
					startActivity(intent);
				}
			}
		}
	};

	// /**
	// * 将List<GoodsImageBean>中的图片两个为一个单位封装到HashMap<Integer, GoodsImageBean>中，
	// * 再将HashMap<Integer, GoodsImageBean>封装到ArrayList中
	// *
	// * @param ImageBeanList
	// * 图片列表
	// * @return ArrayList<HashMap<Integer, GoodsImageBean>> 封装后的图片列表
	// */
	//
	// private ArrayList<HashMap<Integer, GoodsImageBean>> getList(
	// List<GoodsImageBean> ImageBeanList) {
	// boolean flag = false;
	// ArrayList<HashMap<Integer, GoodsImageBean>> list = new
	// ArrayList<HashMap<Integer, GoodsImageBean>>();
	// for (int i = 0; i < ImageBeanList.size(); i++) {
	// if (i == 0) {
	// map = new HashMap<Integer, GoodsImageBean>();
	// map.put(1, ImageBeanList.get(i));
	// flag = true;
	// } else if (i == 1) {
	// map.put(2, ImageBeanList.get(i));
	// list.add(map);
	// flag = false;
	// } else if (i >= 2 && i % 2 == 0) {
	// map = new HashMap<Integer, GoodsImageBean>();
	// map.put(1, ImageBeanList.get(i));
	// flag = true;
	// } else if (i >= 2 && i % 2 == 1) {
	// map.put(2, ImageBeanList.get(i));
	// list.add(map);
	// flag = false;
	// }
	// if (i % 2 == 1 && flag || i % 2 == 0
	// && i == ImageBeanList.size() - 1) {
	// list.add(map);
	// flag = false;
	// }
	// }
	// return list;
	// }

	//
	// /**
	// * 获得图片url的list
	// *
	// * @param list
	// * @return ArrayList<String> 图片url的list
	// */
	// private ArrayList<String> getImageList() {
	// ArrayList<HashMap<Integer, GoodsImageBean>> list =
	// getList(goodsImagList);
	// ArrayList<String> listImage = new ArrayList<String>();
	// for (int i = 0; i < list.size(); i++) {
	// HashMap<Integer, GoodsImageBean> map = list.get(i);
	// String imgUrl1 = map.get(1).bigImgUrl;
	// if (imgUrl1 != null) {
	// listImage.add(imgUrl1);
	// }
	// String imgUrl2 = null;
	// if (map.get(2) != null) {
	// imgUrl2 = map.get(2).bigImgUrl;
	// }
	// if (imgUrl2 != null) {
	// listImage.add(imgUrl2);
	// }
	// }
	// return listImage;
	// }

	/**
	 * 将String型的价格转化为int型
	 * 
	 * @param str
	 * @return
	 */
	private int floatFormat(String str) {
		int pointIndex = str.indexOf(".");
		int strToInt = 0;
		if (pointIndex != -1) {
			String afterStr = str.substring(pointIndex + 1, str.length());
			if (afterStr.length() == 1) {
				str = str + "0";
			}
			str = str.substring(0, pointIndex)
					+ str.substring(pointIndex + 1, str.length());
		} else {
			str = str + "00";
		}
		strToInt = Integer.parseInt(str);
		return strToInt;
	}

	/**
	 * 获得节省的钱
	 * 
	 * @return
	 */
	private String getSaveMoney() {
		int saveMoney = floatFormat(goodsDetail.marketPrice)
				- floatFormat(goodsDetail.shopPrice);
		String saveMoneyStr = saveMoney + "";
		if (saveMoneyStr.length() == 1) {
			saveMoneyStr = "00" + saveMoneyStr;
		} else if (saveMoneyStr.length() == 2) {
			saveMoneyStr = "0" + saveMoneyStr;
		}
		StringBuffer buff = new StringBuffer();
		buff.append(saveMoneyStr.substring(0, saveMoneyStr.length() - 2))
				.append(".")
				.append(saveMoneyStr.substring(saveMoneyStr.length() - 2,
						saveMoneyStr.length()));
		return buff.toString();
	}

	/**
	 * 提示登录对话框
	 */
	private void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("温馨提示").setMessage("您未登录，请先登录！")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// 跳到登录页面
						Intent intent = new Intent(context, LoginActivity.class);
						intent.putExtra("shopcart", "shopcart");
						startActivity(intent);
					}
				}).setNegativeButton("取消", null);
		dialog.create().show();
	}

}
