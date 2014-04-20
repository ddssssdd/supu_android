package cc.android.supu.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Audio;
import cc.android.supu.DialogActivity;
import cc.android.supu.R;
import cc.android.supu.WelcomeActivity;
import cc.android.supu.handler.MessagePushHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;

public class Push_Service extends BroadcastReceiver {
	// int iconLevel=3;
	private Context context;
	static boolean isFirst = true;
	/** 通知推送handler */
	private MessagePushHandler messagePushHandler;

	@Override
	public void onReceive(Context context, Intent intent) {
		// SharedPreferences sharedPreferences = context.getSharedPreferences(
		// "alarm_record", Activity.MODE_PRIVATE);
		this.context = context;
		DownloadPushInfo();
		// System.out.println("----Push_Service已启动");
	}

	final Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == 500) {
				String servermessage = messagePushHandler.PushMessage;
				String id = messagePushHandler.Id;
				String getId = UserInfoTools.getMessageId(context);
				if (!id.trim().equals(getId)) {
					UserInfoTools.saveMessageId(context, id);
					if (servermessage != null && !"".equals(servermessage)) {
						// 更新通知栏 
						String title = "速普商城";
						String content = servermessage;
						pushMesg(title, content, R.drawable.ic_launcher);
					
					}
				}

			}

		}

	};

	/**
	 * 开启推送服务 有新活动
	 * 
	 * @param title
	 * @param content
	 * @param icon
	 */
	private void pushMesg(String title, String content, int icon) {
		// 创建一个NotificationManager的引用

		String ns = Context.NOTIFICATION_SERVICE;

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);

		// 定义Notification的各种属性

		// 通知图标 icon

		CharSequence tickerText = title; // 状态栏显示的通知文本提示

		long when = System.currentTimeMillis(); // 通知产生的时间，会在通知信息里显示

		// 用上面的属性初始化Nofification

		Notification notification = new Notification(icon, tickerText, when);

		// 添加声音

		notification.defaults |= Notification.DEFAULT_SOUND;

		// 或者使用以下几种方式
		/*
		 * notification.sound = Uri .parse("file:///sdcard/rings/铃声.mp3");
		 */

		notification.sound = Uri.withAppendedPath(
				Audio.Media.INTERNAL_CONTENT_URI, "3");

		// 如果想要让声音持续重复直到用户对通知做出反应，则可以在notification的flags字段增加"FLAG_INSISTENT"

		// 如果notification的defaults字段包括了"DEFAULT_SOUND"属性，则这个属性将覆盖sound字段中定义的声音

		// 添加振动

		// notification.defaults |= Notification.DEFAULT_VIBRATE;

		// 或者可以定义自己的振动模式：

		// long[] vibrate = { 0, 100, 200, 300 }; //
		// 0毫秒后开始振动，振动100毫秒后停止，再过200毫秒后再次振动300毫秒

		// notification.vibrate = vibrate;

		// long数组可以定义成想要的任何长度

		// 如果notification的defaults字段包括了"DEFAULT_VIBRATE",则这个属性将覆盖vibrate字段中定义的振动

		/*
		 * // 添加LED灯提醒
		 * 
		 * notification.defaults |= Notification.DEFAULT_LIGHTS;
		 * 
		 * // 或者可以自己的LED提醒模式:
		 * 
		 * notification.ledARGB = 0xff00ff00;
		 * 
		 * notification.ledOnMS = 300; // 亮的时间
		 * 
		 * notification.ledOffMS = 1000; // 灭的时间
		 * 
		 * notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		 */

		// 更多的特征属性

		notification.flags |= Notification.FLAG_AUTO_CANCEL; // 在通知栏上点击此通知后自动清除此通知

		// notification.flags |= Notification.FLAG_INSISTENT; //
		// 重复发出声音，直到用户响应此通知

		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		// 将此通知放到通知栏的"Ongoing"即"正在运行"组中

		// notification.flags |= Notification.FLAG_NO_CLEAR; //
		// 表明在点击了通知栏中的"清除通知"后，此通知不清除，

		// 经常与FLAG_ONGOING_EVENT一起使用

		notification.number = 1; // number字段表示此通知代表的当前事件数量，它将覆盖在状态栏图标的顶部

		// 如果要使用此字段，必须从1开始

		notification.iconLevel = 1;
		// iconLevel++;

		// 设置通知的事件消息

		Context contexts = context.getApplicationContext(); // 上下文

		Intent notificationIntent = new Intent(context, DialogActivity.class); // 点击该通知后要跳转的Activity

		 notificationIntent.putExtra("title", title);
		 notificationIntent.putExtra("content", content);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification
				.setLatestEventInfo(contexts, title, content, contentIntent);

		// 把Notification传递给NotificationManager

		mNotificationManager.notify(0, notification);
	}

	/**
	 * 请求接口，获得推送消息
	 */
	private void DownloadPushInfo() {
		Thread th1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (Tools.isAccessNetwork(context)) {
					messagePushHandler = new MessagePushHandler();
					Tools.requestToParse(context, ConstantUrl.GETPUSHMESSAGE,
							"GetPushMessage", null, messagePushHandler, false);
					if (Tools.responseValue == 1) {
						if (messagePushHandler.result_code == 0) {
							System.out.println("有推送消息");
							handler.sendEmptyMessage(500);
						}
					}

				} else {
					System.out.println("无推送消息");
				}
			}
		});
		th1.start();
	}

}
