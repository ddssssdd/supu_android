package cc.android.supu.tools;

import java.lang.Thread.UncaughtExceptionHandler;

import cc.android.supu.SupuApplication;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * 捕获程序Crash
 * 
 * @author sss
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {
	/** 系统默认的UncaughtExceptionHandler */
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	private static CrashHandler instance = new CrashHandler();

	private Context mContext;

	private CrashHandler() {
	};

	public static CrashHandler getInstance() {
		return instance;
	}

	public void init(Context context) {
		this.mContext = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else { // 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			 android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 * 
	 * @return true代表处理该异常，不再向上抛异常，
	 *         false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理，
	 *         简单来说就是true不会弹出那个错误提示框，false就会弹出
	 */
	private boolean handleException(final Throwable ex) {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出...", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		SupuApplication.crashApper();

		if (ex != null) {
			ex.printStackTrace();
			return true;
		} else
			return true;
	}

}
