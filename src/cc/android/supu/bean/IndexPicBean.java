package cc.android.supu.bean;

import java.io.Serializable;

/**
 * @author sss 首页顶部Gallery图片Bean
 */
public class IndexPicBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 图片下载路径 */
	public String picUrl;
	/** 图片排序序号(升序) */
	public String picSort;
	/** 图片链接类型(Product:商品页;List:商品列表页;Search:搜索页;Activity:活动页) */
	public String linkType;
	/**
	 * 链接内容,按照链接类型的不同内容格式不同(Product:商品编号;List:{分类ID}-{品牌ID},如"153-128";Search:
	 * 搜索关键词;Activity:活动ID)
	 */
	public String linkData;

//	public enum LinkData {
//		PRODUCTNO, LISTID, SEARCHKEY, ACTIVITYID;
//	}

	@Override
	public int hashCode() {
		return BeanTools.createHashcode(picUrl, picSort, linkType, linkData);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof IndexPicBean) {
			IndexPicBean other = (IndexPicBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

}
