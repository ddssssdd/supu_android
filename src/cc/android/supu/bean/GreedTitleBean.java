package cc.android.supu.bean;

import java.io.Serializable;

/**
 * @author zsx 孕育宝典标题Bean
 */
public class GreedTitleBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**标题ID**/
	public String ID;
	/**标题名字**/
	public String ArticleTitle;


	@Override
	public int hashCode() {
		return BeanTools.createHashcode(ID, ArticleTitle);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof GreedTitleBean) {
			GreedTitleBean other = (GreedTitleBean) o;
			if (this.toString().equals(other.toString()))
				return true;
			else
				return false;
		} else
			return false;
	}
}
