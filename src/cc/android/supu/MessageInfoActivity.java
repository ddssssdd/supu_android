package cc.android.supu;

import java.util.TreeMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.handler.CreateAddressHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

/**
 * 站内消息的详细页面
 * 
 * @author zsx
 * 
 */
public class MessageInfoActivity extends BaseActivity {

	/** 显示消息的内容 */
	private WebView messageinfo;
	/** 会员消息 ，关系编号，消息编号  */
	private String textInfo,MID,RID;
	/**是否已读的状态*/
	private int state = 0;
	/** 键值对 */
	private TreeMap<String, String> params;
	/**标记已读的handler*/
	private CreateAddressHandler createAddressHandler;

	@Override
	protected void initPage() {
		params = new TreeMap<String, String>();
		messageinfo = (WebView) findViewById(R.id.messageinfo);
		/** 得到传过来的 */
		Bundle bundle = getIntent().getExtras();
		textInfo = bundle.getString("MessageInfo");
		state = bundle.getInt("State");
		MID = bundle.getString("MID");
		RID = bundle.getString("RID");
		messageinfo.loadDataWithBaseURL(null, textInfo, "text/html", "utf-8", null);	
		System.out.println("messageinfo.getHeight()"+messageinfo.getHeight());
		System.out.println("RID"+RID);
		
		if (state == 0) {
			requestServer(requestMessageIsSee, false);
		}

	}

	/**
	 * 按钮的请求
	 */
	private PageRequest requestMessageIsSee = new PageRequest() {
		@Override
		public void requestServer() {
			params.put("Ids", RID);
			createAddressHandler = new CreateAddressHandler();
			Tools.requestToParse(MessageInfoActivity.this,
					ConstantUrl.USERMESSAGETOREAD, "UserMessageToRead", params,
					createAddressHandler, false);
			if (Tools.responseValue == 1) {
				if (createAddressHandler.result_code == 0) {
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
	protected void dealwithMessage(Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			//Toast.makeText(MessageInfoActivity.this, "已读", Toast.LENGTH_SHORT).show();
			break;
		case STATE_FAILURE:
			Toast.makeText(MessageInfoActivity.this, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case STATE_ERROR:
			Toast.makeText(MessageInfoActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	};

	@Override
	protected String setTitle() {
		return "消息";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	int setLayout() {
		return R.layout.messageinfo;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 3;
	}

}
