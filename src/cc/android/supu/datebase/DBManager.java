package cc.android.supu.datebase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import cc.android.supu.R;

/**
 * 备用数据库
 * 
 * @author zsx
 * 
 */
public class DBManager {
	private final int BUFFER_SIZE = 1024;
	public static final String DB_NAME = "supu2.db";
	public static final String PACKAGE_NAME = "cc.android.supu";
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME;
	private SQLiteDatabase database;
	private Context context;
	private File file = null;

	DBManager(Context context) {
		this.context = context;
	}

	/**
	 * 打开数据库
	 */
	public void openDatabase() {
		if (database != null && database.isOpen()) {
			database.close();
		}
		this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
	}

	/**
	 * 得到数据库
	 * 
	 * @return
	 */
	public SQLiteDatabase getDatabase() {
		return this.database;
	}

	/**
	 * 得到数据库
	 * 
	 * @param dbfile
	 * @return
	 */
	private SQLiteDatabase openDatabase(String dbfile) {
		try {
			file = new File(dbfile);
			if (!file.exists()) {
				InputStream is = context.getResources().openRawResource(
						R.raw.supu2);
				if (is != null) {
				} else {
				}
				FileOutputStream fos = new FileOutputStream(dbfile);
				if (is != null) {
				} else {
				}
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
					fos.flush();
				}
				fos.close();
				is.close();
			}
			database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
			return database;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 关闭数据库
	 */
	public void closeDatabase() {
		if (this.database != null)
			this.database.close();
	}
}