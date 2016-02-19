package com.gp.gifshot.entity;

/**
 * @author 高攀
 * @下午12:36:57
 */
public class User {

	private long userid; //
	private String realname;// 真实姓名
	private String nickname;// 别名
	private String head;// 头像
	private String phonenum;// 手机号码
	private String password;
	private String email;// 邮箱
	private String qq;//
	private String microblog;// 微博
	private String sex;
	private String brith;// 生日
	private Integer privince;
	private Integer city;
	private Integer county;
	private String privinceStr;
	private String cityStr;
	private String countyStr;
	private String address;// 详细地址
	private String otherphonenum;// 其他联系电话
	private String registtime;// 注册时间
	private Integer ulevel;// 级别
	private Integer verify;// 审核情况0为待审核,1为通过,2为不通过

	private String weixin; // 微信号码
	private String zipcode; // 邮编
	private String signature; // 个性签名
	private Integer fraction; // 用户分数
	private Integer star; // 用户星级
	private String id_number; // 身份证号码

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMicroblog() {
		return microblog;
	}

	public void setMicroblog(String microblog) {
		this.microblog = microblog;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBrith() {
		return brith;
	}

	public void setBrith(String brith) {
		this.brith = brith;
	}

	public Integer getPrivince() {
		return privince;
	}

	public void setPrivince(Integer privince) {
		this.privince = privince;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getCounty() {
		return county;
	}

	public void setCounty(Integer county) {
		this.county = county;
	}

	public String getPrivinceStr() {
		return privinceStr;
	}

	public void setPrivinceStr(String privinceStr) {
		this.privinceStr = privinceStr;
	}

	public String getCityStr() {
		return cityStr;
	}

	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}

	public String getCountyStr() {
		return countyStr;
	}

	public void setCountyStr(String countyStr) {
		this.countyStr = countyStr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOtherphonenum() {
		return otherphonenum;
	}

	public void setOtherphonenum(String otherphonenum) {
		this.otherphonenum = otherphonenum;
	}

	public String getRegisttime() {
		return registtime;
	}

	public void setRegisttime(String registtime) {
		this.registtime = registtime;
	}

	public Integer getUlevel() {
		return ulevel;
	}

	public void setUlevel(Integer ulevel) {
		this.ulevel = ulevel;
	}

	public Integer getVerify() {
		return verify;
	}

	public void setVerify(Integer verify) {
		this.verify = verify;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Integer getFraction() {
		return fraction;
	}

	public void setFraction(Integer fraction) {
		this.fraction = fraction;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

}
