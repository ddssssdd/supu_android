package cc.android.supu;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cc.android.supu.adapter.ShopCarListAdapter;
import cc.android.supu.bean.GoodsBean;
import cc.android.supu.handler.ModifyShopCartHandler;
import cc.android.supu.handler.ShopCarHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author sss 购物车
 */
public class ShopCarActivity extends BaseActivity implements
		OnFocusChangeListener {

	private static final int STATE_SUCCESS_GETGOODSLIST = 11;
	private static final int STATE_FAILURE_GETGOODSLIST = 12;
	private static final int STATE_SUCCESS_MODIFY = 21;
	private static final int STATE_FAILURE_MODIFY = 22;

	/** 显示商品的listview */
	private ListView listview;
	/** 购物车为空时显示 */
	private LinearLayout noGoodsView;
	/** 底部栏 */
	private RelativeLayout bottomView;
	/** 总金额 */
	private TextView sumAmountTv;
	/** 优惠金额 */
	private TextView discountAmountTv;
	/** 小计中的件数 */
	private TextView countTv;
	/** 加载时显示的view */
	private LinearLayout loadingView;
	/** 加载购物车的进度条 */
	private ProgressBar progressBar;
	/** 加载购物车时显示的文字 */
	private TextView loadingTv;
	/** 购物车信息解析器 */
	private ShopCarHandler shopCarHandler;
	/** 购物车修改信息解析器 */
	private ModifyShopCartHandler modifyHandler;
	/** 购物车列表适配器 */
	private ShopCarListAdapter adapter;
	/** 购物车中商品列表 */
	private ArrayList<GoodsBean> goodslist;
	/** 进度条 */
	private ProgressDialog progres = null;
	/** 小计金额 */
	private String sumAmount = "";
	/** 优惠金额 */
	private String disAmount = "";
	/** 小计数量 */
	private int bottomCount;
	/** 0:删除操作； 1：修改操作 */
	private int MODIFY = 0;
	/** 修改购物车上传的参数 */
	private String goods;
	/** 删除的位置 */
	private int position = -1;

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "购物车";
	}

	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "编辑";
	}

	@Override
	protected String setEnterBtn() {
		// TODO Auto-generated method stub
		return "去结算";
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.shopcar;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	protected void backBtnOnClick() {
		hitSoftInput();
		if (adapter == null)
			return;

		if (ShopCarHandler.isEditting) {// 当前是编辑状态则，取消编辑
			cancelEdit();
			ShopCarHandler.isEditting = false;
		} else {// 否则编辑
			for (int i = 0; i < goodslist.size(); i++) {// 点击"编辑"时，将tempCount的值还原为count
				if (!goodslist.get(i).isGift) {
					goodslist.get(i).tempCount = goodslist.get(i).count + "";
				}
			}
			changeEditState(true);
			ShopCarHandler.isEditting = true;
		}

	}

	@Override
	protected void enterBtnOnClick() {
		hitSoftInput();
		if (adapter == null)
			return;

		if (ShopCarHandler.isEditting) {
			edited();
		} else {
			settlement();
		}
	}

	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.shopcar_listview);
		// listview.setOnItemClickListener(mOnItemClickListener);
		listview.setOnScrollListener(scrollListener);
		noGoodsView = (LinearLayout) findViewById(R.id.shopcart_noproductLayout);
		bottomView = (RelativeLayout) findViewById(R.id.shopcar_bottom_layout);
		sumAmountTv = (TextView) findViewById(R.id.shopcar_SumAmount);
		discountAmountTv = (TextView) findViewById(R.id.shopcar_discountAmount);
		countTv = (TextView) findViewById(R.id.shopcar_count);
		loadingView = (LinearLayout) findViewById(R.id.shopcart_loadLayout);
		progressBar = (ProgressBar) findViewById(R.id.shopcart_progressBar);
		loadingTv = (TextView) findViewById(R.id.shopcart_loadText);
	}

	@Override
	protected void onSubActivityClick(View view) {
		if (view.getId() == R.id.shopcart_item_deleteBtn) {// 点击删除
			position = (Integer) view.getTag(R.id.tag_shopcart_position);
			if (position != -1) {
				showDeleteDialog(position);
			}
		} else if (view.getId() == R.id.shopcart_item_countEt) {// 点击数量编辑框时将光标位置设置到最后
			// System.out.println("+++++-=-=-=-=-=-=_+_+_+_+");
			EditText countEt = (EditText) view;
			int strLength = countEt.getText().toString().trim().length();
			countEt.setSelection(strLength == 0 ? 0 : strLength);
			// System.out.println("countEt.getSelectionEnd()="+countEt.getSelectionEnd());
		}

	}

	// /**
	// * listview的item点击事件
	// */
	// private OnItemClickListener mOnItemClickListener = new
	// OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> arg0, View view, int position,
	// long id) {
	// if (!goodslist.get(position).isGift) {
	// Intent intent = new Intent(ShopCarActivity.this,
	// GoodsDetailsActivity.class);
	// intent.putExtra("goodsSN", goodslist.get(position).goodsSN);
	// startActivity(intent);
	// }
	// }
	// };

	/**
	 * 购物车请求
	 */
	private PageRequest requestShopCar = new PageRequest() {

		@Override
		public void requestServer() {
			shopCarHandler = new ShopCarHandler();
			Tools.requestToParse(ShopCarActivity.this,
					ConstantUrl.GETSHOPPINGCART, "GetShoppingCart", null,
					shopCarHandler, false);
			System.out.println("responseValue=" + Tools.responseValue);
			if (Tools.responseValue == 1) {
				if (shopCarHandler.result_code == 0) {
					handler.sendEmptyMessage(STATE_SUCCESS_GETGOODSLIST);
				} else {
					handler.sendEmptyMessage(STATE_FAILURE_GETGOODSLIST);
				}
			} else {
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};

	/**
	 * 编辑购物车请求
	 */
	private PageRequest requestModify = new PageRequest() {

		@Override
		public void requestServer() {
			
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("goods", goods);

			modifyHandler = new ModifyShopCartHandler();
			Tools.requestToParse(ShopCarActivity.this,
					ConstantUrl.GETMODIFYSHOPPINGCART, "ModifyShoppingCart",
					params, modifyHandler, false);
			if (progres != null && progres.isShowing())
				progres.cancel();
			if (Tools.responseValue == 1) {
				if (modifyHandler.result_code == 0) {
					handler.sendEmptyMessage(STATE_SUCCESS_MODIFY);
				} else {
					handler.sendEmptyMessage(STATE_FAILURE_MODIFY);
				}
			} else {
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};

	protected void dealwithMessage(android.os.Message msg) {
		loadingView.setVisibility(View.VISIBLE);
		switch (msg.what) {
		case STATE_SUCCESS_GETGOODSLIST:

			if (ShopCarHandler.isDeleting) {// 删除操作中
				getVisibleCountEtText();
				if (shopCarHandler.list != null && goodslist != null) {
					goodslist.remove(position);
					for (int i = 0; i < shopCarHandler.list.size(); i++) {
						shopCarHandler.list.get(i).tempCount = goodslist.get(i).tempCount;
					}
				}
				if (shopCarHandler.list == null
						|| shopCarHandler.list.size() == 0) {// 如果商品全部被删除，就该为非编辑状态
					ShopCarHandler.isEditting = false;
				}
				ShopCarHandler.isDeleting = false;
			}

			if (ShopCarHandler.isEdited && shopCarHandler.haveExit) {// 已被编辑过但未修改成功并处于编辑状态
				if (shopCarHandler.list != null && goodslist != null) {
					for (int i = 0; i < goodslist.size(); i++) {
						if (!shopCarHandler.list.get(i).isNoStock
								&& goodslist.get(i).isNoStock) {
							shopCarHandler.list.get(i).isNoStock = goodslist
									.get(i).isNoStock;
							shopCarHandler.list.get(i).stockCount = goodslist
									.get(i).stockCount;
							shopCarHandler.list.get(i).count = goodslist.get(i).count;
						}
					}
				}
				shopCarHandler.haveExit = false;
			} else {
				shopCarHandler.haveExit = false;
			}

			goodslist = shopCarHandler.list;
			if (goodslist == null) {
				return;
			}

			if (adapter == null) {
				adapter = new ShopCarListAdapter(this, goodslist);
				listview.setAdapter(adapter);
			} else {
				adapter.setData(goodslist);
				adapter.notifyDataSetChanged();
			}
			sumAmount = shopCarHandler.sumAmount;
			disAmount = shopCarHandler.discountAmount;
			bottomCount = getCount(goodslist);
			setBottomData();
			changeEditState(ShopCarHandler.isEditting);
			changeVisible(shopCarHandler.list, false);
			loadingView.setVisibility(View.GONE);
			break;
		case STATE_FAILURE_GETGOODSLIST:
			changeVisible(null, true);
			if (shopCarHandler.result_code == 1000) {
				showDialog();
			} else {
				Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
			}
			break;
		case STATE_SUCCESS_MODIFY:
			ArrayList<GoodsBean> modifiedList = modifyHandler.list;
			if (modifiedList != null) {
				if (MODIFY == 0) {// 删除
					if (modifiedList.get(0).count == 0) {
						getVisibleCountEtText();
						requestServer(requestShopCar, false);
					}
				} else {// 修改
					StringBuffer noStockBuff = new StringBuffer();
					noStockBuff.append("以下商品因缺货修改失败：<br/><br/>");
					for (int i = 0; i < goodslist.size(); i++) {
						if (i < modifiedList.size()) {
							if (modifiedList.get(i).isNoStock) {// 缺货
								goodslist.get(i).isNoStock = modifiedList
										.get(i).isNoStock;
								goodslist.get(i).stockCount = modifiedList
										.get(i).count;
								goodslist.get(i).count = modifiedList.get(i).count;
								noStockBuff.append("商品名：")
										.append(modifiedList.get(i).goodsName)
										.append("<br/>").append("商品编号：")
										.append(modifiedList.get(i).goodsSN)
//										.append("<br/>").append("库存量：")
//										.append(modifiedList.get(i).count)
										.append("<br/><br/>");

							} else if (modifiedList.get(i).count > 0) {// 修改成功
								goodslist.get(i).count = modifiedList.get(i).count;
								goodslist.get(i).tempCount = modifiedList
										.get(i).count + "";
							} else if (modifiedList.get(i).count == 0) {// 已删除
								goodslist.remove(i);
							}
						}
					}// for

					if (noStockBuff.toString().equals("以下商品因缺货修改失败：<br/><br/>")) {// 不缺货
						ShopCarHandler.isEditting = false;
						Toast.makeText(this, "操作成功！", Toast.LENGTH_SHORT)
								.show();
						requestServer(requestShopCar);
						ShopCarHandler.isEdited = false;
					} else {// 缺货
						ShopCarHandler.isEditting = true;
						showAlertDialog(noStockBuff.toString(), 1);
					}
				}
			}
			loadingView.setVisibility(View.GONE);
			break;
		case STATE_FAILURE_MODIFY:
			loadingView.setVisibility(View.GONE);
			if (modifyHandler.result_code == 1) {
				Toast.makeText(this, "操作失败！", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
			}
			break;
		case STATE_ERROR:
			Toast.makeText(this, "请求异常", Toast.LENGTH_SHORT).show();
			if (enterLabel.getText().toString().equals("完成")
					&& !ShopCarHandler.isEditting) {// 当网络异常时，导致修改或取消的操作失败，要将状态置为编辑中
				ShopCarHandler.isEditting = true;
			}
			if (!ShopCarHandler.isEditting) {
				changeVisible(null, true);
				System.out.println("请求异常");
			}

			break;
		}
		// loadingView.setVisibility(View.GONE);
	};

	/**
	 * 取消编辑
	 */
	private void cancelEdit() {
		if (!ShopCarHandler.isEdited) {
			for (int i = 0; i < goodslist.size(); i++) {
				goodslist.get(i).tempCount = goodslist.get(i).count + "";
			}
			changeEditState(false);
			return;
		}

		StringBuffer cancelBuff = new StringBuffer();
		for (int i = 0; i < goodslist.size(); i++) {
			if (goodslist.get(i).isGift) {
				continue;
			}
			goodslist.get(i).tempCount = goodslist.get(i).count + "";

			cancelBuff.append(goodslist.get(i).goodsSN).append(":")
					.append(goodslist.get(i).tempCount).append(":").append("=");
			if (i < goodslist.size()) {
				cancelBuff.append(",");
			}
		}

		goods = cancelBuff.toString();
		// System.out.println("2--cancelBuff----goods="+goods);
		MODIFY = 1;
		showD();
		requestServer(requestModify);
	}
	/**
	 * 显示进度条
	 */
	private void showD(){
		if (progres != null) {
			progres.cancel();
		}
		progres = new ProgressDialog(ShopCarActivity.this);
		progres.setIndeterminate(true);
		progres.setCancelable(true);
		progres.setMessage("正在处理,请稍候...");
		progres.show();
	}
	/**
	 * 改变编辑状态
	 * 
	 * @param isEdit
	 */
	private void changeEditState(boolean isEdit) {
		setTopLabelText(isEdit);
		changeListEditState(isEdit);
	}

	/**
	 * 编辑完成
	 */
	private void edited() {

		String reg = "[1-9]{1}[0-9]{0,}|[0]";
		StringBuffer goodsBuff = new StringBuffer();
		int firstVisiblePosition = listview.getFirstVisiblePosition();
		int childCount = listview.getChildCount();
		int j = 0;
		for (int i = 0; i < goodslist.size(); i++) {
			if (goodslist.get(i).isGift) {
				continue;
			}

			if (i >= firstVisiblePosition && j < childCount) {// 将可见item中的countEt中的text取出并赋给tempCount
				EditText countEt = (EditText) listview.getChildAt(j).getTag(
						R.id.tag_shopcart_countEt);
				String text = countEt.getText().toString().trim();
				goodslist.get(i).tempCount = text;
				System.out.println("111---goodslist.get(i).tempCount="
						+ goodslist.get(i).tempCount);
				j++;
			}

			if (!goodslist.get(i).tempCount.matches(reg)) {// 对输入的内容进行验证是否符合规范
				Toast.makeText(this, "输入的数量不能以0开头！", Toast.LENGTH_SHORT).show();
				return;
			}

			goodsBuff.append(goodslist.get(i).goodsSN).append(":")
					.append(goodslist.get(i).tempCount).append(":").append("=");
			if (i < goodslist.size()) {
				goodsBuff.append(",");
			}

		}

		goods = goodsBuff.toString();
		// System.out.println("1----goodsBuff-----goods="+goods);
		ShopCarHandler.isEditting = false;
		MODIFY = 1;
		showD();
		requestServer(requestModify);
		ShopCarHandler.isEdited = true;
	}

	/**
	 * 去计算
	 */
	private void settlement() {
		boolean isNoStock = false;
		for (int i = 0; i < goodslist.size(); i++) {
			if (goodslist.get(i).isNoStock) {
				isNoStock = true;
				break;
			}
		}
		if (isNoStock) {
			showAlertDialog("有缺货商品，请修改后再结算！", 2);
			return;
		}

		Intent intent = new Intent(this, SettleAccountActivity.class);
		intent.putExtra("sumAmount", sumAmount);
		intent.putExtra("discountAmount", disAmount);
		startActivity(intent);
	}

	/**
	 * 改变列表的编辑状态
	 * 
	 * @param isEditting
	 */
	private void changeListEditState(boolean isEditting) {
		if (adapter != null) {
			adapter.edit(isEditting);
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 设置顶部Label的text
	 */
	private void setTopLabelText(boolean isEdit) {
		if (!isEdit) {
			backLabel.setText("编辑");
			enterLabel.setText("去结算");
			titleLabel.setText("购物车");
		} else {
			backLabel.setText("取消");
			enterLabel.setText("完成");
			titleLabel.setText("变更购物车清单");
		}
	}

	/**
	 * 获取购物车中的商品件数
	 * 
	 * @return
	 */
	private int getCount(List<GoodsBean> list) {
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			count += list.get(i).count;
		}
		return count;
	}

	/**
	 * 设置底部栏的数据
	 */
	private void setBottomData() {
		sumAmountTv.setText("￥" + sumAmount);
		discountAmountTv.setText("￥" + disAmount);
		countTv.setText("(" + bottomCount + "件)：");
	}

	/**
	 * 显示顶部控件
	 */
	private void showTopLabel() {
		backLabel.setVisibility(View.VISIBLE);
		enterLabel.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏顶部控件
	 */
	private void hideTopLabel() {
		backLabel.setVisibility(View.INVISIBLE);
		enterLabel.setVisibility(View.INVISIBLE);
	}

	/**
	 * 改变控件的可见性
	 */
	private void changeVisible(ArrayList<GoodsBean> list, boolean requestError) {
		if (requestError) {
			loadingView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			loadingTv.setVisibility(View.VISIBLE);
			loadingTv.setText("购物车请求异常！");
			return;
		}

		if (list != null && list.size() > 0) {
			listview.setVisibility(View.VISIBLE);
			bottomView.setVisibility(View.VISIBLE);
			noGoodsView.setVisibility(View.GONE);
			showTopLabel();
		} else {
			listview.setVisibility(View.GONE);
			bottomView.setVisibility(View.GONE);
			noGoodsView.setVisibility(View.VISIBLE);
			hideTopLabel();
		}
	}

	/**
	 * 取得可见的item中的CountEt中的text,并赋给tempCount；
	 */
	private void getVisibleCountEtText() {
		if (goodslist != null && adapter != null && adapter.isEditting()) {
			int firstVisiblePosition = listview.getFirstVisiblePosition();
			int childCount = listview.getChildCount();
			int j = 0;
			for (int i = 0; i < goodslist.size(); i++) {
				if (goodslist.get(i).isGift) {
					continue;
				}

				if (i >= firstVisiblePosition && j < childCount) {
					EditText countEt = (EditText) listview.getChildAt(j)
							.getTag(R.id.tag_shopcart_countEt);
					String text = countEt.getText().toString().trim();
					goodslist.get(i).tempCount = text;
					j++;
				}
			}
		}
	}

	/**
	 * 保存当前状态
	 */
	private void saveState() {
		// ShopCarHandler.isEditting = isEditting;
		// ShopCarHandler.isEdited = isEdited;
		ShopCarHandler.goodsList = goodslist;
		if (ShopCarHandler.isEdited) {
			ShopCarHandler.haveExit = true;
		}
	}

	/**
	 * 获取上次的状态
	 */
	private void getState() {
		// isEditting = ShopCarHandler.isEditting;
		// isEdited = ShopCarHandler.isEdited;
		if (ShopCarHandler.goodsList != null) {
			goodslist = ShopCarHandler.goodsList;
		}
	}

	@Override
	protected void onResume() {
		hideTopLabel();
		listview.setVisibility(View.INVISIBLE);
		noGoodsView.setVisibility(View.INVISIBLE);
		loadingView.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		loadingTv.setText("正在加载购物车....");
		if (UserInfoTools.isLogin(this)) {
			requestServer(requestShopCar);
		} else {
			showDialog();
		}
		getState();
		super.onResume();
	}

	@Override
	protected void onPause() {
		hitSoftInput();
		getVisibleCountEtText();
		saveState();
		super.onPause();
	}

	/**
	 * 隐藏软键盘
	 */
	private void hitSoftInput() {
		if (this.getCurrentFocus() != null) {// 隐藏软键盘
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(this.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 提示登录对话框
	 */
	private void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("温馨提示").setMessage("您未登录，请先登录！")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 跳到登录页面
						Intent intent = new Intent(ShopCarActivity.this,
								LoginActivity.class);
						intent.putExtra("shopcart", "shopcart");
						startActivity(intent);
						dialog.dismiss();
					}
				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						loadingView.setVisibility(View.VISIBLE);
						progressBar.setVisibility(View.GONE);
						loadingTv.setText("登录后才能访问购物车");
					}
				});
		dialog.create().show();
	}

	/**
	 * 提示对话框
	 * 
	 * @param msg
	 *            需要显示的信息
	 * @param which
	 *            点击确定后执行的操作，1：表示购物车的修改 2： 表示无操作，并取消对话框
	 */
	private void showAlertDialog(String msg, final int which) {
		AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
		dialog2.setTitle("温馨提示").setMessage(Html.fromHtml(msg))
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int w) {
						if (which == 1) {
							changeEditState(true);
						} else if (which == 2) {
							dialog.dismiss();
						}

					}
				});
		dialog2.create().show();

	}

	/**
	 * 删除对话框
	 * 
	 * @param position
	 */
	private void showDeleteDialog(final int position) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("提示对话框").setMessage("您确定删除此商品！")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringBuffer deletebuf = new StringBuffer();
						deletebuf.append(goodslist.get(position).goodsSN)
								.append(":").append("0").append(":")
								.append("=");
						goods = deletebuf.toString();
						System.out.println("delete---goods=" + goods);
						ShopCarHandler.isDeleting = true;
						MODIFY = 0;
						showD();
						requestServer(requestModify);
					}
				}).setNegativeButton("取消", null);
		dialog.create().show();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		// System.out.println("-=-=-=-=-=-=_+_+_+_+");
		// 数量编辑框聚焦时设置光标的位置
		EditText countEt = (EditText) v;
		int strLength = countEt.getText().toString().trim().length();
		countEt.setSelection(strLength == 0 ? 0 : strLength);

		// System.out.println("countEt.getSelectionEnd()="+countEt.getSelectionEnd());
	}

	private OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
//			if (scrollState == SCROLL_STATE_IDLE) {
//				adapter.setScrolling(false);
//			} else {
//				adapter.setScrolling(true);
//			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	};
}
