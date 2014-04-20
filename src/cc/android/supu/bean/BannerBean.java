package cc.android.supu.bean;

import java.io.Serializable;

public class BannerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 图片下载路径*/
	public String picUrl;
	/** 使用图片的开始时间*/
	public String beginTime;
	/** 使用图片的截止时间*/
	public String endTime;
	
}
