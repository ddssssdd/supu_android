package cc.android.supu;

import java.util.ArrayList;
import java.util.TreeMap;

import cc.android.supu.adapter.ActivityAdapter;
import cc.android.supu.bean.ActivityBean;
import cc.android.supu.bean.ActivityItemBean;
import cc.android.supu.handler.ActivityHandler;
import cc.android.supu.tools.AsyncImageLoader;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.Tools;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author sss 活动标题
 */
public class ActivityTitle extends BaseActivity {

	private ListView listView;

	private String activity_code;
	//
	private ActivityBean activityBean;
	private ArrayList<ActivityItemBean> itemBeans;
	private ActivityAdapter adapter;

	private ImageView imageView;
	private ProgressBar progressBar;

	@Override
	protected void initPage() {
		activity_code = getIntent().getStringExtra("linkdata");
		imageView = (ImageView) findViewById(R.id.titleIcon);
		progressBar = (ProgressBar) findViewById(R.id.titleprogress);
		listView = (ListView) findViewById(R.id.activity_list);
		itemBeans = new ArrayList<ActivityItemBean>();
		adapter = new ActivityAdapter(this, listView, itemBeans);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(itemClickListener);
		requestServer(defaultPageRequest);
	}

	@Override
	protected String setTitle() {
		return "活动详情";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	int setLayout() {
		return R.layout.activity_title;
	}

	@Override
	int setBottomIndex() {
		return 0;
	}

	private void showData() {
		adapter.notifyDataSetChanged();
		if (activityBean != null) {
			Bitmap bitmap = AsyncImageLoader.loadDrawable(ActivityTitle.this, activityBean.activityImage,
					new AsyncImageLoader.ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap, String imageUrl) {
							if (bitmap != null) {
								imageView.setScaleType(ScaleType.FIT_XY);
								imageView.setImageBitmap(bitmap);
							}
							progressBar.setVisibility(View.GONE);
						}
					}, false, 3);
			if (bitmap != null) {
				imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setImageBitmap(bitmap);
				progressBar.setVisibility(View.GONE);
			} else {
				imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setImageResource(R.drawable.default_activity);
			}
		}
	}

	@Override
	protected void dealwithMessage(Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			showData();
			break;
		case STATE_FAILURE:

		case STATE_ERROR:
			Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}

	@Override
	protected void defaultRequest() {
		TreeMap<String, String> map = new TreeMap<String, String>();
		ActivityHandler activityHandler = new ActivityHandler();
		map.put("ActivityId", activity_code);
		Tools.requestToParse(this, ConstantUrl.GETACTIVITYLIST, "GetActivity", map, activityHandler, false);
		if (Tools.responseValue == 1) {
			activityBean = activityHandler.activityBean;
			if (activityHandler.activityItemBeans != null) {
				itemBeans.addAll(activityHandler.activityItemBeans);
			}
			handler.sendEmptyMessage(STATE_SUCCESS);
		} else if (Tools.responseValue == 3) {
			handler.sendEmptyMessage(STATE_FAILURE);
		} else {
			handler.sendEmptyMessage(STATE_ERROR);
		}

	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ActivityItemBean bean = itemBeans.get(position);
			Intent intent = new Intent(ActivityTitle.this, GoodsDetailsActivity.class);
			intent.putExtra("goodsSN", bean.goodsSN);
			intent.putExtra("imgFile", bean.imgFile);
			ActivityTitle.this.startActivity(intent);
		}
	};

}
