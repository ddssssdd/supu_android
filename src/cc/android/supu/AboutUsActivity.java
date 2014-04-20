package cc.android.supu;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.handler.AboutUsHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

/**
 * 关于我们页面
 * @author sheng
 *
 */
public class AboutUsActivity extends BaseActivity {
	
	/** 关于我们 的信息解析器 */
	private AboutUsHandler jsonData;
	private AboutUsActivity context = this;
	/** 显示公司简介*/
	private WebView introTv;
	/** 显示联系方式*/
	private TextView contactTv;
	/** 拨打电话的按钮 */
	private TextView callBtn;
	
	private LinearLayout layout;
	/** 公司简介*/
	private String intro;
	
	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		introTv = (WebView) findViewById(R.id.aboutus_intro);
		contactTv = (TextView) findViewById(R.id.aboutus_contact);
		callBtn = (TextView) findViewById(R.id.aboutus_call);
		callBtn.setOnClickListener(this);
		
		layout = (LinearLayout) findViewById(R.id.aboutus_layout);
		requestServer(requestAboutUs);
	}
	@Override
	protected void onSubActivityClick(View view) {
		// TODO Auto-generated method stub
		if(view.getId() == R.id.aboutus_call){
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4006180055"));
			startActivity(intent);
		}
	}
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "关于我们";
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
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.aboutus;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 4;
	}
	
	/**
	 * 关于我们的信息的请求
	 */
	PageRequest requestAboutUs = new PageRequest() {
		
		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			jsonData = new AboutUsHandler();
			Tools.requestToParse(context, ConstantUrl.GETABOUTUS, "GetAboutUs", null, jsonData, false);
			if(Tools.responseValue == 1){
				if(jsonData.result_code == 0){
					handler.sendEmptyMessage(STATE_SUCCESS);
				}else{
					handler.sendEmptyMessage(STATE_FAILURE);
				}
			}else{
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};
	
	protected void dealwithMessage(android.os.Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			intro = jsonData.data;
//			introTv.setText(intro);
			
			introTv.loadDataWithBaseURL(null, intro, "text/html", "utf-8", null);	
			layout.setVisibility(View.VISIBLE);
			break;

		case STATE_FAILURE:
			Toast.makeText(context, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case STATE_ERROR:
			Toast.makeText(context, "请求异常！", Toast.LENGTH_SHORT).show();
			break;
		}
	};
}
