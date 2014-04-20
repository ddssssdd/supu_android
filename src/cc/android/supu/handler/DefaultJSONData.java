/**  
 * Filename:    DefaultJSONData.java     
 * Copyright:   Copyright (c)2012 
 * @author:     AW  
 * @version:    1.0
 * Create at:   2012-3-25  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2012-3-25    AW             1.0        1.0 Version  
 */
package cc.android.supu.handler;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author AW
 * 
 */
public class DefaultJSONData {

	/** 返回的状态码：0表示成功;1表示系统错误;2用户认证失败;3表示业务操作失败;4表示无效的业务请求... */
	public int result_code;
	/** 返回的提示信息 */
	public String result_message;

	// 解析json数组
	public void parse(JSONArray array) {
	};

	public final void parseJsonObject(JSONObject object) {
		result_code = object.optInt("ErrorCode");
		result_message = object.optString("Message");
		if (result_code == 0) {
			/** 在成功的情况下解析 */
			parse(object);
		}else if(result_code == 1){
			parseUpdateInfo(object);
		}
	}

	// 解析json对象
	public void parse(JSONObject object) {
	};
	
	public void parseUpdateInfo(JSONObject object){};
}
