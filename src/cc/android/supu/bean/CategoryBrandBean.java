package cc.android.supu.bean;

import java.io.Serializable;

/**
 * @author sss 类别、品牌Bean
 */
public class CategoryBrandBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 分类编号或品牌编号 */
	public String Id;
	/** 分类名称或品牌名称 */
	public String name;
	/** 分类排序或品牌排序 */
	public String sortOrder;
	/** 分类父类编号 */
	public String categroyParentId;
	/** 分类图片 */
	public String categoryImg;
	/** 是否是叶子节点 */
	public boolean categoryIsLeaf = true;

	@Override
	public int hashCode() {
		return BeanTools.createHashcode(Id, name, sortOrder, categroyParentId, categoryImg, categoryIsLeaf);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof CategoryBrandBean) {
			CategoryBrandBean other = (CategoryBrandBean) o;
			if (this.toString().equals(other.toString()))
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ID").append(Id).append(" name:").append(name).append(" sortOrder:").append(sortOrder)
				.append(" categoryParentId:").append(categroyParentId).append(" categoryImg:").append(categoryImg)
				.append(" categoryIsLeaf:").append(categoryIsLeaf);
		return builder.toString();
	}
}
