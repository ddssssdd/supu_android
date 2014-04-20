package cc.android.supu.handler;

import org.json.JSONObject;

/** 
 * 获取更新信息解析器
 * @author sheng
 *
 */
public class UpdateHandler extends DefaultJSONData{
	
	/** 新版本版本号*/
	public double versionNo;
	/** 新版本说明信息*/
	public String versionInfo = "";
	/** 下载地址*/
	public String downloadUrl = "";
	/** 是否强制升级*/
	public boolean forceUpdate;
	
	public void parseUpdateInfo(JSONObject object) {
		if(object != null){
			JSONObject data = object.optJSONObject("Data");
			if(data != null){
				versionNo = data.optDouble("VersionNo", 0);
				versionInfo = data.optString("VersionInfo", "");
				downloadUrl = data.optString("DownloadUrl", "");
				forceUpdate = data.optBoolean("ForceUpdate", false);
			}
		}
	}
}
