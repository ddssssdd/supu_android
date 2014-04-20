package cc.android.supu;

/**
 * 最近浏览页面
 * zsx
 */
import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cc.android.supu.adapter.RecentViewAdapter;
import cc.android.supu.bean.ArticleGoodsBean;
import cc.android.supu.datebase.RecentViewDbHelper;

public class RecentViewActivity extends BaseActivity {
	/** 最近浏览列表布局 */
	private ListView recentViewList;
	/** 适配器 */
	private RecentViewAdapter recentViewAdapter;
	/** 最近浏览列表 */
	private ArrayList<ArticleGoodsBean> list;
	/** 列表为空时的布局 */
	private RelativeLayout recent_empty;

	@Override
	protected void initPage() {
		recentViewList = (ListView) findViewById(R.id.recentViewList);
		recent_empty = (RelativeLayout) findViewById(R.id.recent_empty);

		list = new ArrayList<ArticleGoodsBean>();
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		GetList();
		if (list != null && list.size() > 0) {
			recent_empty.setVisibility(View.GONE);
			recentViewList.setVisibility(View.VISIBLE);
			recentViewAdapter = new RecentViewAdapter(this, list);
			recentViewList.setAdapter(recentViewAdapter);
			enterLabel.setVisibility(View.VISIBLE);
		} else {
			recent_empty.setVisibility(View.VISIBLE);
			recentViewList.setVisibility(View.GONE);
			enterLabel.setVisibility(View.GONE);
		}

		ListenerItem();
	}
	/**
	 * 得到List从数据库中
	 */
	private void GetList() {
		RecentViewDbHelper recentViewDbHelper = new RecentViewDbHelper(this);
		//排序
		list = getListSort(recentViewDbHelper.queryAllCity());
	}

	/**
	 * 列表响应事件
	 */
	private void ListenerItem() {
		recentViewList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(RecentViewActivity.this,
						GoodsDetailsActivity.class);
				intent.putExtra("goodsSN", list.get(arg2).GoodsSN);
				intent.putExtra("imgFile", list.get(arg2).ImgFile);
				RecentViewActivity.this.startActivity(intent);
			}
		});

	}

	/**
	 * 将得到的时间排序
	 * 得到的是从大到小的顺序
	 */
	private ArrayList<ArticleGoodsBean> getListSort(
			ArrayList<ArticleGoodsBean> list) {
		if (list == null || list.size() <= 0)
			return null;
		/** 冒泡排序 */
		int len = list.size();
		if (list.size() == 1)
			return list;
//		System.out.println("len" + len);
		ArticleGoodsBean GoodsBean;
		for (int i = 0; i < len; ++i) {
			for (int j = len - 1; j > i; --j) {
				if (list.get(j).GoodTime != null
						&& list.get(i).GoodTime != null) {
//					System.out.println("list.get(j).GoodTime"+list.get(j).GoodTime);
					if (CompareTime(list.get(j).GoodTime,
							list.get(j - 1).GoodTime)) {
						GoodsBean = list.get(j);
						list.set(j, list.get(j - 1));
						list.set(j - 1, GoodsBean);
					}
				}
			}
		}
		return list;
	}

	/**
	 * yyyy-MM-dd-hh-mm-ss 时间的比较 要是返回true 就是前一个数大 返回false 就是后一个数大
	 */
	private boolean CompareTime(String time1, String time2) {
		if(time1==null||time2==null)return false;
		int start[] = { 0, 5, 8, 11, 14, 17 };
		int end[] = { 4, 7, 10, 13, 16, 19 };
		for (int i = 0; i < 6; i++) {
			
			String yyyy1 = time1.substring(start[i], end[i]);
			String yyyy2 = time2.substring(start[i], end[i]);
			int time11 = Integer.parseInt(yyyy1);
			int time22 = Integer.parseInt(yyyy2);
			if (time11 > time22) {
				return true;
			} else if (time11 < time22) {
				return false;
			}else{
				continue;
			}
		}
	
		return false;
	}

	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.recentview;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "最近浏览";
	}

	@Override
	protected String setEnterBtn() {
		// TODO Auto-generated method stub
		return "清除";
	}

	@Override
	protected void enterBtnOnClick() {
		if (list != null && list.size() > 0) {
			// 点击清除 清除数据库中的全部数据
			RecentViewDbHelper recentViewDbHelper = new RecentViewDbHelper(this);
			if (!recentViewDbHelper.deleteAllGoods()) {
				Toast.makeText(RecentViewActivity.this, "删除失败",
						Toast.LENGTH_SHORT).show();
				enterLabel.setVisibility(View.VISIBLE);
			} else {
				Toast.makeText(RecentViewActivity.this, "删除成功",
						Toast.LENGTH_SHORT).show();
				enterLabel.setVisibility(View.GONE);
				list.clear();
				recent_empty.setVisibility(View.VISIBLE);
				recentViewList.setVisibility(View.GONE);
			}
		}
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 4;
	}

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
//	public String getStringDateShort() {
//		Date currentTime = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//		String dateString = formatter.format(currentTime);
//		return dateString;
//	}

}
