package cc.android.supu;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author sss 更多
 */
public class MoreActivity extends BaseActivity {

	private LinearLayout  about;
	private RelativeLayout  baodian;
	/**最近浏览*/
	private RelativeLayout  scan;
	/**设置*/
	private RelativeLayout  set;
	private RelativeLayout  update;
	private RelativeLayout  y;

	@Override
	protected void onSubActivityClick(View view) {
		// TODO Auto-generated method stub
		Intent intent = null;
		if(view.getId() == baodian.getId()){
			intent = new Intent(this, GreedActivity.class);
			startActivity(intent);
		}else if(view.getId() == scan.getId()){
			intent = new Intent(this, RecentViewActivity.class);
			startActivity(intent);
		}else if(view.getId() == set.getId()){
			intent = new Intent(this, SetupActivity.class);
			startActivity(intent);
		}else if(view.getId() == update.getId()){
			intent = new Intent(this, UpdateActivity.class);
			startActivity(intent);
		}else if(view.getId() == y.getId()){
			startActivity(new Intent(this, FeedbackActivity.class));
		}else if(view.getId() == about.getId()){
			startActivity(new Intent(this, AboutUsActivity.class));
		}
		
	}
	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.more;
	}
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "更多";
	}
	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		baodian = (RelativeLayout)findViewById(R.id.more_baodian);
		baodian.setOnClickListener(this);
		about = (LinearLayout)findViewById(R.id.more_about);
		about.setOnClickListener(this);
		scan = (RelativeLayout)findViewById(R.id.more_scan);
		scan.setOnClickListener(this);
		set = (RelativeLayout)findViewById(R.id.more_set);
		set.setOnClickListener(this);
		update = (RelativeLayout)findViewById(R.id.more_update);
		update.setOnClickListener(this);
		y = (RelativeLayout)findViewById(R.id.more_y);
		y.setOnClickListener(this);
		
	}
	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 4;
	}

}
