package cc.android.supu.bean;

import java.io.Serializable;

/**
 * 用户bean
 * 
 * @author zsx
 * 
 */
public class MemberBean implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/** 用户编号 */
	public String memberId;

	/** 用户账号 */
	public String account;
	
	/** 用户密码 */
	public String password;

	/** 用户等级 */
	public int level;

	/** 用户账户余额 */
	public String price;

	/** 用户积分 */
	public int scores;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}




	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(memberId, account, password,level, price, scores);
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof MemberBean) {
			MemberBean other = (MemberBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
