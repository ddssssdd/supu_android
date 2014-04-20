package cc.android.supu;

import java.util.ArrayList;
import java.util.TreeMap;

import cc.android.supu.adapter.GreedAdapter;
import cc.android.supu.bean.GreedBean;
import cc.android.supu.handler.GreedHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.Tools;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author sss 孕育宝典
 */
public class GreedActivity extends BaseActivity {

	private ListView greedListView;

	private ArrayList<GreedBean> greedBeans;
	private GreedAdapter adapter;

	private String parentId;

	@Override
	protected String setTitle() {
		return "孕婴宝典";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	protected void initPage() {
		parentId = "0";
		greedBeans = new ArrayList<GreedBean>();
		adapter = new GreedAdapter(this, greedBeans);
		greedListView = (ListView) findViewById(R.id.greedlist);
		greedListView.setAdapter(adapter);
		greedListView.setOnItemClickListener(itemClickListener);
		requestServer(defaultPageRequest);
	}

	@Override
	int setLayout() {
		return R.layout.greed_list;
	}

	@Override
	int setBottomIndex() {
		return 4;
	}

	@Override
	protected void defaultRequest() {
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("ParentId", parentId);
		GreedHandler greedHandler = new GreedHandler();
		Tools.requestToParse(this, ConstantUrl.GETGREEDLIST, "GetArticleCategoryList", map, greedHandler, false);
		if (Tools.responseValue == 1) {
			greedBeans.addAll(greedHandler.greedBeans);
			handler.sendEmptyMessage(STATE_SUCCESS);
		} else if (Tools.responseValue == 3) {
			handler.sendEmptyMessage(STATE_ERROR);
		} else {
			handler.sendEmptyMessage(STATE_FAILURE);
		}
	}

	@Override
	protected void dealwithMessage(Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			adapter.notifyDataSetChanged();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "请求错误!", Toast.LENGTH_LONG).show();
			break;
		case STATE_FAILURE:
			Toast.makeText(this, "请求失败!", Toast.LENGTH_LONG).show();
			break;
		}
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			GreedBean bean = greedBeans.get(position);
			Intent intent = new Intent(GreedActivity.this, GreedSubActivity.class);
			intent.putExtra("parentId", bean.ID);
			GreedActivity.this.startActivity(intent);
		}
	};

}
