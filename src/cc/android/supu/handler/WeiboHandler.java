package cc.android.supu.handler;

import android.os.Handler;
import android.os.Message;

import com.bshare.core.BSShareItem;
import com.bshare.core.DefaultHandler;
import com.bshare.core.PlatformType;
import com.bshare.core.ShareResult;

/**
 * @author sss 微博Handler
 */
public class WeiboHandler extends DefaultHandler {
	private Handler handler;
	public static final int SUCCESS = 123;
	public static final int ERROR = 124;
	public static final int FAIL = 125;
	public static final int START = 126;
	public static final int SHARED = 127;
	public static int record;
	public WeiboHandler(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void onShareComplete(PlatformType p, ShareResult sr) {
		Message msg = handler.obtainMessage(sr.isSuccess() ? SUCCESS : FAIL, sr);
		handler.sendMessage(msg);
	}

	@Override
	public void onShareStart(PlatformType p, BSShareItem shareItem) {
		handler.sendEmptyMessage(START);
	}

	@Override
	public void onVerifyError(PlatformType p) {
		handler.sendEmptyMessage(ERROR);
	}
}
