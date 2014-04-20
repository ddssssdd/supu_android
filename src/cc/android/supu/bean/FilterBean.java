package cc.android.supu.bean;

import java.io.Serializable;

/**
 * @author sss 筛选Bean
 */
public class FilterBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public String name;

	public String id;
	/** -1缺货 0常态 1选中 */
	public int type = 0;

	@Override
	public int hashCode() {
		return BeanTools.createHashcode(name, id);
	}

	@Override
	public String toString() {
		return "name:" + name + " id:" + id;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof FilterBean) {
			FilterBean other = (FilterBean) o;
			if (other.toString().equals(this.toString()))
				return true;
			else
				return false;
		} else
			return false;
	}

	/**
	 * @author sss 类别
	 */
	public static class CategoryBean extends FilterBean implements Serializable {
		private static final long serialVersionUID = 1L;
	}

	/**
	 * @author sss 价格
	 */
	public static class PriceBean extends FilterBean implements Serializable {
		private static final long serialVersionUID = 1L;
		public float min;
		public float max;

		@Override
		public int hashCode() {
			return BeanTools.createHashcode(name, min, max);
		}

		@Override
		public String toString() {
			return super.toString() + " min:" + min + " max:" + max;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (this == o)
				return true;
			if (o instanceof FilterBean) {
				FilterBean other = (FilterBean) o;
				if (other.toString().equals(this.toString()))
					return true;
				else
					return false;
			} else
				return false;
		}

	}

	/**
	 * @author sss 品牌
	 */
	public static class BrandBean extends FilterBean implements Serializable {
		private static final long serialVersionUID = 1L;
		/** 类别ID，以,号分隔成 */
		public String categoryIds;

		@Override
		public int hashCode() {
			return BeanTools.createHashcode(name, categoryIds);
		}

		@Override
		public String toString() {
			return super.toString() + " categoryIds:" + categoryIds;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (this == o)
				return true;
			if (o instanceof FilterBean) {
				FilterBean other = (FilterBean) o;
				if (other.toString().equals(this.toString()))
					return true;
				else
					return false;
			} else
				return false;
		}

	}
}
