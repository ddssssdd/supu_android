//package cc.android.supu.service;
//
///**
// * 消息推送
// * zsx
// */
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.ContextWrapper;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import cc.android.supu.IndexActivity;
//import cc.android.supu.R;
//import cc.android.supu.WelcomeActivity;
//import cc.android.supu.handler.MessagePushHandler;
//import cc.android.supu.tools.ConstantUrl;
//import cc.android.supu.tools.Tools;
//import cc.android.supu.tools.UserInfoTools;
//
//public class Messageservice extends Service {
//	/** 获取消息线程 */
//	private messagethread messagethread = null;
//	// private Context context = (ContextWrapper) Messageservice.this
//	// .getBaseContext();
//	/** 点击查看 */
//	private Intent messageIntent = null;
//	/** 处理即将发生的事情 */
//	private PendingIntent messagependingintent = null;
//	/** 通知栏消息  */
//	private int messagenotificationid = 1000;
//	/** 通知 */
//	private Notification messagenotification = null;
//	/** 通知管理 */
//	private NotificationManager messagenotificatiomanager = null;
//	/** 通知推送handler */
//	private MessagePushHandler messagePushHandler;
//	private boolean isrunning = true;
//
//	@Override
//	public int onStartCommand(Intent intent, int flags, int startid) {
//		// 初始化
//		messagenotification = new Notification();
//		messagenotification.icon = R.drawable.ic_launcher;
//		messagenotification.tickerText = "速普商城";
//		messagenotification.defaults = Notification.DEFAULT_SOUND;
//		messagenotificatiomanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		messageIntent = new Intent(Messageservice.this, WelcomeActivity.class);
//		messagependingintent = PendingIntent.getActivity(this, 0,
//				messageIntent, 0);
//		// 开启线程
//		messagethread = new messagethread();
//		messagethread.start();
//		return super.onStartCommand(intent, flags, startid);
//	}
//
//	/**
//	 ** 从服务器端获取消息  *      
//	 */
//	class messagethread extends Thread {
//		// 运行状态 
//
//		public void run() {
//			synchronized (this) {
//				while (isrunning) {
//					try {
//						System.out.println("推送开始0000");
//						// isrunning = false;
//						// Message msg = MessageHandler.obtainMessage();
//						// msg.what = 5;
//						// MessageHandler.sendMessageDelayed(msg, 10000);
//						// 休息30分
//						// Thread.sleep(1800000);
//						messagethread.wait(10000);
//						// messagethread.join(10000);
//						/**
//						 * 这里是得到推送的消息
//						 */
//						messagePushHandler = new MessagePushHandler();
//						Tools.requestToParse(Messageservice.this,
//								ConstantUrl.GETPUSHMESSAGE, "GetPushMessage",
//								null, messagePushHandler, false);
//						if (Tools.responseValue == 1) {
//							if (messagePushHandler.result_code == 0) {
//								MessageHandler.sendEmptyMessage(1);
//							}
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//
//			}
//		}
//
//		/**
//		 * 推送消息的handler
//		 */
//		final Handler MessageHandler = new Handler() {
//
//			public void handleMessage(Message msg) {
//				if (msg.what == 1) {
//					String servermessage = messagePushHandler.PushMessage;
//					String id = messagePushHandler.Id;
//					String getId = UserInfoTools
//							.getMessageId(Messageservice.this);
//					if (!id.trim().equals(getId)) {
//						UserInfoTools.saveMessageId(Messageservice.this, id);
//						if (servermessage != null && !"".equals(servermessage)) {
//							// 更新通知栏 
//							messagenotification.setLatestEventInfo(
//									Messageservice.this, "速普商城", servermessage,
//									messagependingintent);
//							messagenotificatiomanager.notify(
//									messagenotificationid, messagenotification);
//							// 每次通知完，通知id递增一下，避免消息覆盖掉 
//							messagenotificationid++;
//						}
//					}
//				}
//			}
//		};
//
//	}
//
//	@Override
//	public void onDestroy() {
//		System.exit(0);
//		super.onDestroy();
//	}
//
//	@Override
//	public IBinder onBind(Intent intent) {
//		return null;
//	}
//}