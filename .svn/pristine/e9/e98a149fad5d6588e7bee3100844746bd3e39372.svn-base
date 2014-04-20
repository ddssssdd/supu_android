package cc.android.supu.tools;

import java.util.ArrayList;

import android.content.Context;

/**
 * 
 * @author zsx
 * 
 */
public class ThreadManage implements ThreadInter{

	private static ArrayList<Context> TaskList  = new ArrayList<Context>();;
	/**
	 * @param context
	 */
	public static void start() {
		
		while (TaskList.size() > 0) {
			getTask();
		}

	}

	/**
	 * return a task item, will be delete on successful
	 * 
	 */
	public static synchronized Context getTask() {
		if (ThreadManage.TaskList.size() > 0) {
			Context task = ThreadManage.TaskList.get(0);
			((ThreadInter) task).Threading();
			ThreadManage.TaskList.remove(0);
			return task;
		} else {
			return null;
		}
	}

	/**
	 * 添加任务
	 */
	public static void addTask(Context task) {
		ThreadManage.TaskList.add(task);
	}

	@Override
	public void Threading() {
		//System.out.println("main");
	}
}
