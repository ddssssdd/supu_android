package cc.android.supu;

import java.util.ArrayList;
import java.util.TreeMap;

import cc.android.supu.adapter.CategoryBrandAdapter;
import cc.android.supu.bean.CategoryBrandBean;
import cc.android.supu.handler.CategoryBrandHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.Tools;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author sss分类
 */
public class CategoryActivity extends BaseActivity {

	private TextView categoryLabel, brandLabel;
	private ListView listView;
	private CategoryBrandAdapter adapter;
	private ArrayList<CategoryBrandBean> beans;
	private ArrayList<ArrayList<CategoryBrandBean>> historyBeans;

	public static boolean onClick = false;
	private boolean isCategory = true, hasImg = true;
	/** 父级分类为0 */
	private String parentId = "0";

	// /** 品牌Id */
	// private String brandId = "337";

	@Override
	int setLayout() {
		return R.layout.category;
	}

	// @Override
	// protected String setTitle() {
	// return "分类";
	// }

	@Override
	protected void onSubActivityClick(View view) {
		switch (view.getId()) {
		case R.id.categorylabel:
			changeLabel(0);
			break;
		case R.id.brandlabel:
			changeLabel(1);
			break;
		default:
			break;
		}
	}

	private void changeLabel(int type) {
		if (type == 0) {
			isCategory = true;
			hasImg = true;
			parentId = "0";
			categoryLabel.setTextColor(Color.WHITE);
			categoryLabel.setBackgroundResource(R.drawable.partion_btn_normal);
			brandLabel.setTextColor(Color.BLACK);
			brandLabel.setBackgroundResource(R.drawable.partion_btn_toching);
		} else {
			isCategory = false;
			brandLabel.setTextColor(Color.WHITE);
			brandLabel.setBackgroundResource(R.drawable.partion_btn_normal);
			categoryLabel.setTextColor(Color.BLACK);
			categoryLabel.setBackgroundResource(R.drawable.partion_btn_toching);
		}
	}

	@Override
	protected void initPage() {
		categoryLabel = (TextView) findViewById(R.id.categorylabel);
		categoryLabel.setOnClickListener(this);
		brandLabel = (TextView) findViewById(R.id.brandlabel);
		brandLabel.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.categorylist);
		isBottom = true;
		listView.setOnItemClickListener(itemClickListener);
		requestServer(defaultPageRequest);
		titleLabel.setText("分类");
		titleLabel.setBackgroundColor(Color.parseColor("#00000000"));
	}

	@Override
	int setBottomIndex() {
		return 1;
	}

	@Override
	protected void defaultRequest() {
		TreeMap<String, String> map = new TreeMap<String, String>();
		CategoryBrandHandler brandHandler = new CategoryBrandHandler(isCategory);
		if (isCategory) {
			map.put("ParentId", parentId);
			Tools.requestToParse(this, ConstantUrl.GETCATEGORYLIST, "GetCategoryList", map, brandHandler, false);
		} else {
			map.put("CategoryId", parentId);
			Tools.requestToParse(this, ConstantUrl.GETBRANDLIST, "GetBrandList", map, brandHandler, false);
		}
		if (Tools.responseValue == 1) {
			if (brandHandler.getBeans() != null) {
				beans = brandHandler.getBeans();
			}
			handler.sendEmptyMessage(STATE_SUCCESS);
		} else if (Tools.responseValue == 3) {
			handler.sendEmptyMessage(STATE_FAILURE);
		} else {
			handler.sendEmptyMessage(STATE_ERROR);
		}

	}

	@Override
	protected void dealwithMessage(Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			if (isCategory)
				adapter = new CategoryBrandAdapter(this, beans, hasImg);
			else
				adapter = new CategoryBrandAdapter(this, beans, false);
			listView.setAdapter(adapter);
			break;
		case STATE_FAILURE:
			Toast.makeText(this, "请求失败!", Toast.LENGTH_LONG).show();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "请求错误!", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			CategoryBrandBean bean = beans.get(position);
			if (!bean.categoryIsLeaf) {
				categoryLabel.setVisibility(View.GONE);
				brandLabel.setVisibility(View.GONE);
				parentId = bean.Id;
				if (historyBeans == null)
					historyBeans = new ArrayList<ArrayList<CategoryBrandBean>>();
				hasImg = false;
				categoryHistory = true;
				historyBeans.add(beans);
				titleLabel.setText(bean.name);
				listView.setAdapter(null);
				requestServer(defaultPageRequest);
				backLabel.setOnClickListener(CategoryActivity.this);
				backLabel.setText("返回");
				isBottom = false;
				backLabel.setVisibility(View.VISIBLE);
			} else {
				Intent intent = new Intent(CategoryActivity.this, SearchResult.class);
				if (isCategory) {
					intent.putExtra("categoryId", bean.Id);
					// intent.putExtra("brandId", parentId);
				} else {
					intent.putExtra("brandId", bean.Id);
					intent.putExtra("categoryId", parentId);
				}
				intent.putExtra("iscategory", isCategory);
				intent.putExtra("key", bean.name);
				intent.putExtra("index", 1);
				CategoryActivity.this.startActivity(intent);
			}
		}
	};

	protected void backBtnOnClick() {
		onKeyDown(KeyEvent.KEYCODE_BACK, null);
	};

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		if (onClick && historyBeans != null && historyBeans.size() > 0) {
			int len = historyBeans.size();
			beans = historyBeans.remove(len - 1);
			if (len == 1) {
				categoryHistory = true;
				categoryLabel.setVisibility(View.VISIBLE);
				brandLabel.setVisibility(View.VISIBLE);
			} else {
				categoryHistory = false;
			}
			adapter = new CategoryBrandAdapter(this, beans, true);
			listView.setAdapter(adapter);
			backLabel.setVisibility(View.GONE);
		}
		onClick = false;
		super.onStart();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (historyBeans == null || historyBeans.size() == 0)
				return super.onKeyDown(keyCode, event);
			else {
				int len = historyBeans.size();
				beans = historyBeans.remove(len - 1);
				if (len == 1) {
					categoryHistory = true;
					categoryLabel.setVisibility(View.VISIBLE);
					brandLabel.setVisibility(View.VISIBLE);
				} else {
					categoryHistory = false;
				}
				titleLabel.setText("分类");
				adapter = new CategoryBrandAdapter(this, beans, true);
				listView.setAdapter(adapter);
				backLabel.setVisibility(View.GONE);
				return false;
			}
		} else
			return super.onKeyDown(keyCode, event);
	};
}
