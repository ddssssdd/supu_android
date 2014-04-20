package cc.android.supu;

import org.json.JSONException;
import org.json.JSONObject;

import com.bshare.BShare;
import com.bshare.activity.AuthorizationActivity;
import com.bshare.core.BSShareItem;
import com.bshare.core.BShareMessageDispatcher;
import com.bshare.core.Config;
import com.bshare.core.DefaultHandler;
import com.bshare.core.PlatformType;
import com.bshare.core.TokenInfo;
import com.bshare.platform.Platform;
import com.bshare.platform.PlatformFactory;
import com.umeng.analytics.MobclickAgent;

import cc.android.supu.handler.WeiboHandler;
import cc.android.supu.tools.ConstantUrl;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author sss 新浪微博分享
 */
public class WeiboActivity extends BaseActivity {

	private String SUPU_URL = "http://www.supuy.com";
	private TextView fontsLabel;
	private EditText weiboContentLabel;

	private String content;

	private final static int MAXLEN = 140;
	private BShare bShare;

	/** 分享底部弹出的popupWindow */
	private PopupWindow popupWindow;

	private DefaultHandler defaultHandler;
	
	private View popupView;

	@Override
	protected void initPage() {
		content = getIntent().getStringExtra("shareText");
		fontsLabel = (TextView) findViewById(R.id.weibo_len);
		weiboContentLabel = (EditText) findViewById(R.id.weibo_content);
		weiboContentLabel.addTextChangedListener(textWatcher);
		if (content != null) {
			weiboContentLabel.setText(resetContent(content));
			int len = MAXLEN - content.length();
			fontsLabel.setText(len > 0 ? len + "" : "0");
			if (content.contains("http://"))
				SUPU_URL = content.substring(content.indexOf("http://"), content.length());
		}
		//System.setProperty("debug", "true");
		/** 设置腾讯 */
		Config.configObject().setTencentAppkey(ConstantUrl.TXWEIBO_APP_KEY);
		Config.configObject().setTencentAppSecret(ConstantUrl.TXWEIBO_SECRET_KEY);
		/** 设置新浪 */

		Config.configObject().setSinaAppkey(ConstantUrl.SINAWEIBO_APP_KEY);
		Config.configObject().setSinaAppSecret(ConstantUrl.SINAWEIBO_SECRET_KEY);
		Config.configObject().setSinaCallbackUrl(ConstantUrl.SINAREDIRECT_URL);
		/** 设置人人 */
		Config.configObject().setRenrenAppkey(ConstantUrl.RENREN_APP_KEY);
		Config.configObject().setRenrenAppSecret(ConstantUrl.RENREN_SECRET_KEY);
		bShare = BShare.instance(this);
		bShare.allowVerify(true);
		bShare.removeAllCredential(this);
		defaultHandler = new WeiboHandler(handler);
		BShareMessageDispatcher.registerHandler(defaultHandler);
		initAnimation();
	}

	/**
	 * popupWindow的动画
	 */
	private void initAnimation() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View popupView = inflater.inflate(R.layout.sharemenu, null);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		popupWindow = new PopupWindow(popupView, LayoutParams.FILL_PARENT, (int) (270 * metrics.density), false);
		TextView sinaBtn = (TextView) popupView.findViewById(R.id.share_sina);
		TextView qqBtn = (TextView) popupView.findViewById(R.id.share_qq);
		TextView renrenBtn = (TextView) popupView.findViewById(R.id.share_renren);
		TextView cancleBtn = (TextView) popupView.findViewById(R.id.share_cancle);
		sinaBtn.setOnClickListener(this);
		qqBtn.setOnClickListener(this);
		renrenBtn.setOnClickListener(this);
		cancleBtn.setOnClickListener(this);
	}

	public void openShareMenu() {
		popupWindow.setAnimationStyle(R.style.dialog_animation);
		popupWindow.showAtLocation(findViewById(R.id.bottom_layout), Gravity.BOTTOM, 0, 0);
		popupWindow.setFocusable(false);
		//popupWindow.update();
	}

	@Override
	int setLayout() {
		return R.layout.weibo;
	}

	@Override
	int setBottomIndex() {
		return 0;
	}

	@Override
	protected String setTitle() {
		return "微博分享";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	protected String setEnterBtn() {
		return "分享";
	}

	@Override
	protected void onSubActivityClick(View view) {

		content = weiboContentLabel.getText().toString();
		TokenInfo tokenInfo;
		switch (view.getId()) {
		case R.id.share_sina:
			popupWindow.dismiss();
			popupWindow.setFocusable(false);
			tokenInfo = getTokenInfo(PlatformType.SINAMINIBLOG);
			if (tokenInfo == null) {
				auth(PlatformType.SINAMINIBLOG);
			} else {
				BSShareItem shareItem = new BSShareItem(PlatformType.SINAMINIBLOG, "来自速普商城", content, SUPU_URL);
				Platform platform = PlatformFactory.getPlatform(this, PlatformType.SINAMINIBLOG);
				platform.setShareItem(shareItem);
				platform.setTokenInfo(tokenInfo);
				share(platform);
				MobclickAgent.onEvent(this, "um_share_sina"); 
			}
			break;
		case R.id.share_qq:
			popupWindow.dismiss();
			popupWindow.setFocusable(false);
			tokenInfo = getTokenInfo(PlatformType.QQMB);
			if (tokenInfo == null) {
				auth(PlatformType.QQMB);
			} else {
				BSShareItem shareItem = new BSShareItem(PlatformType.QQMB, "来自速普商城", content, SUPU_URL);
				Platform platform = PlatformFactory.getPlatform(this, PlatformType.QQMB);
				platform.setShareItem(shareItem);
				platform.setTokenInfo(tokenInfo);
				share(platform);
				MobclickAgent.onEvent(this, "um_share_tencent"); 
			}
			break;
		case R.id.share_renren:
			popupWindow.dismiss();
			popupWindow.setFocusable(false);
			tokenInfo = getTokenInfo(PlatformType.RENREN);
			if (tokenInfo == null) {
				auth(PlatformType.RENREN);
			} else {
				BSShareItem shareItem = new BSShareItem(PlatformType.RENREN, "来自速普商城", content, SUPU_URL);
				Platform platform = PlatformFactory.getPlatform(this, PlatformType.RENREN);
				platform.setShareItem(shareItem);
				platform.setTokenInfo(tokenInfo);
				share(platform);
				MobclickAgent.onEvent(this, "um_share_renren"); 
			}
			break;
		case R.id.share_cancle:
			popupWindow.dismiss();
			popupWindow.setFocusable(false);
			break;
		}
	}

	private void share(final Platform platform) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				platform.share();
			}
		});
		thread.start();
	}

	private void auth(final PlatformType platformType) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				handler.sendEmptyMessage(2);
				Platform platform = PlatformFactory.getPlatform(WeiboActivity.this, platformType);
				String url = platform.getUrl();
				handler.sendEmptyMessage(3);
				if (TextUtils.isEmpty(url)) {
					return;
				} else {
					Intent i = new Intent(WeiboActivity.this, AuthorizationActivity.class);
					Bundle data = new Bundle();
					data.putString("url", url);
					data.putParcelable("platform", platformType);
					data.putInt("position", 0);
					i.putExtras(data);
					startActivityForResult(i, 1);
					return;
				}
			}
		}).start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			switch (resultCode) {
			case -1:
				Bundle extras = data.getExtras();
				TokenInfo tokenInfo = (TokenInfo) extras.getParcelable("tokenInfo");
				if (tokenInfo != null) {
					Toast.makeText(this, "认证成功!", Toast.LENGTH_LONG).show();
					notifyAuthComplete(tokenInfo, true, tokenInfo.getPlatform());
				} else {
					Toast.makeText(this, "认证失败!", Toast.LENGTH_LONG).show();
				}
				break;
			default:
				Toast.makeText(this, "认证失败!", Toast.LENGTH_LONG).show();
				break;
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void notifyAuthComplete(TokenInfo tokenInfo, boolean success, PlatformType platformType) {
		if (success) {
			SharedPreferences preferences = getSharedPreferences(platformType.getPlatfromName(), 0);
			Editor editor = preferences.edit();
			editor.putString("tokenInfo", tokenInfo.getJsonString());
			editor.putLong("time", System.currentTimeMillis());
			editor.commit();
			BSShareItem shareItem = new BSShareItem(platformType, "来自速普商城", content, SUPU_URL);

			bShare.share(this, shareItem, defaultHandler);
		} else
			Toast.makeText(this, "认证失败!", Toast.LENGTH_LONG).show();
	}

	private TokenInfo getTokenInfo(PlatformType platformType) {
		SharedPreferences preferences = getSharedPreferences(platformType.getPlatfromName(), 0);
		String tokenInfo = preferences.getString("tokenInfo", null);
		if (tokenInfo == null)
			return null;
		try {
			TokenInfo info = new TokenInfo(new JSONObject(tokenInfo));
			long addTime = preferences.getLong("time", 0);
			if (info.getExpiredTime() == null || info.getExpiredTime().equals("")) {
				return info;
			} else if ((Long.parseLong(info.getExpiredTime()) + addTime) > System.currentTimeMillis())
				return info;
			else
				return null;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void enterBtnOnClick() {
		content = weiboContentLabel.getText().toString();
		if (TextUtils.isEmpty(content)) {
			Toast.makeText(this, "请输入内容!", Toast.LENGTH_LONG).show();
			return;
		}
		openShareMenu();
	}

	@Override
	protected boolean showBottom() {
		return false;
	}

	private TextWatcher textWatcher = new TextWatcher() {
		private CharSequence temp;
		private int selectionStart;
		private int selectionEnd;

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			temp = s;
		}

		@Override
		public void afterTextChanged(Editable s) {
			selectionStart = weiboContentLabel.getSelectionStart();
			selectionEnd = weiboContentLabel.getSelectionEnd();
			if (temp.length() > MAXLEN) {
				s.delete(selectionStart - 1, selectionEnd);
				int tempSelection = selectionStart;
				weiboContentLabel.setText(s);
				weiboContentLabel.setSelection(tempSelection);
				Toast.makeText(WeiboActivity.this, "最大长度为10", Toast.LENGTH_LONG).show();
			} else {
				fontsLabel.setText((MAXLEN - temp.length()) + "");
			}
		}
	};

	@Override
	protected void dealwithMessage(Message msg) {
		switch (msg.what) {
		case WeiboHandler.START:
			showProgressDialog("分享到微博,请稍候...");
			break;
		case WeiboHandler.SUCCESS:
			Toast.makeText(this, "分享成功!", Toast.LENGTH_LONG).show();
			finish();
			break;
		case WeiboHandler.FAIL:
			Toast.makeText(this, "分享失败!", Toast.LENGTH_LONG).show();
			break;
		case WeiboHandler.ERROR:
			Toast.makeText(this, "分享错误!", Toast.LENGTH_LONG).show();
			break;
		}
	}
	
	/**
	 * 保证分享内容长度在140字以内,并且不影响到url
	 * @param content
	 * @return
	 */
	private String resetContent(String content){
		if(content.length() > MAXLEN){
			String url = content.substring(content.indexOf("http://"), content.length());
			int textLength = MAXLEN - url.length();
			if(textLength > 0){
				String text = content.substring(0, textLength);
				return text+url;
			}else{
				return content;
			}
		}else{
			return content;
		}
	}
}
