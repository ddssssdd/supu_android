package cc.android.supu.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cc.android.supu.tools.UserInfoTools;

/**
 * 开机重新设置闹铃广播
 * 
 */
public class AlarmInitReceiver extends BroadcastReceiver {

	private Context context;
	/** 是否推送消息 */
	private boolean isMessage;

	@Override
	public void onReceive(Context context, Intent intent1) {
		this.context = context;
		String action = intent1.getAction();
		if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
			startPushMsg();
		}
	}
	/**
	 * 开启消息推送
	 */
	private void startPushMsg() {
		isMessage = UserInfoTools.getMessage(context);
		System.out.println("111111111isMessage"+isMessage);
		if (isMessage) {
			// 开启消息
			PushMsg pushMsg = PushMsg.getInstance();
			pushMsg.openMsgPush(context);
		}
	}

}