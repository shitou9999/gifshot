package com.gp.gifshot.entity;

/**
 * @author 高攀
 * @下午4:56:31 首页itemBean
 */
public class MainItem {

	private long id = 0; // 视频资源id
	private String time = null; // 时间
	private String head = null; // 头像
	private String thumurl = null; // 封面
	private String watchnum = null; // 浏览数量

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getThumurl() {
		return thumurl;
	}

	public void setThumurl(String thumurl) {
		this.thumurl = thumurl;
	}

	public String getWatchnum() {
		return watchnum;
	}

	public void setWatchnum(String watchnum) {
		this.watchnum = watchnum;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\", \"time\":\"" + time + "\", \"head\":\""
				+ head + "\", \"thumurl\":\"" + thumurl + "\", \"watchnum\":\""
				+ watchnum + "\"}";
	}
}
