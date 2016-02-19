package com.gp.实体类;
/**
 * @author 高攀
 * @下午1:45:53
 */
public class FilmBean {

	/** 基本信息 **/
	public String filmName = null; // 电影名称
	public String filmType = null; // 电影类型
	public String filmTag = null; // 电影标签
	public String filmIntro = null; // 电影介绍
	public String filmTime = null; // 电影时长
	public String filmScore = null; // 电影评分
	public String filmImage = null; // 电影图片

	/** 下载信息 **/
	public String filmMagnetUrl = null; // 磁力链接
	public String filmPlayUrl = null; // 在线播放链接
	public String filmBaiDuUrl = null; // 百度链接
	public String filmBaiDuUrlPass = null; // 百度链接密码
	public String filmTorrent = null; // 种子文件

	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public String getFilmType() {
		return filmType;
	}

	public void setFilmType(String filmType) {
		this.filmType = filmType;
	}

	public String getFilmIntro() {
		return filmIntro;
	}

	public void setFilmIntro(String filmIntro) {
		this.filmIntro = filmIntro;
	}

	public String getFilmTime() {
		return filmTime;
	}

	public void setFilmTime(String filmTime) {
		this.filmTime = filmTime;
	}

	public String getFilmScore() {
		return filmScore;
	}

	public void setFilmScore(String filmScore) {
		this.filmScore = filmScore;
	}

	public String getFilmMagnetUrl() {
		return filmMagnetUrl;
	}

	public void setFilmMagnetUrl(String filmMagnetUrl) {
		this.filmMagnetUrl = filmMagnetUrl;
	}

	public String getFilmPlayUrl() {
		return filmPlayUrl;
	}

	public void setFilmPlayUrl(String filmPlayUrl) {
		this.filmPlayUrl = filmPlayUrl;
	}

	public String getFilmBaiDuUrl() {
		return filmBaiDuUrl;
	}

	public void setFilmBaiDuUrl(String filmBaiDuUrl) {
		this.filmBaiDuUrl = filmBaiDuUrl;
	}

	public String getFilmBaiDuUrlPass() {
		return filmBaiDuUrlPass;
	}

	public void setFilmBaiDuUrlPass(String filmBaiDuUrlPass) {
		this.filmBaiDuUrlPass = filmBaiDuUrlPass;
	}

	public String getFilmTorrent() {
		return filmTorrent;
	}

	public void setFilmTorrent(String filmTorrent) {
		this.filmTorrent = filmTorrent;
	}

	public String getFilmTag() {
		return filmTag;
	}

	public void setFilmTag(String filmTag) {
		this.filmTag = filmTag;
	}

	public String getFilmImage() {
		return filmImage;
	}

	public void setFilmImage(String filmImage) {
		this.filmImage = filmImage;
	}

	@Override
	public String toString() {
		return "{\"filmName\":\"" + filmName + "\",\"filmType\":\"" + filmType
				+ "\",\"filmTag\":\"" + filmTag + "\",\"filmIntro\":\""
				+ filmIntro + "\",\"filmTime\":\"" + filmTime
				+ "\",\"filmScore\":\"" + filmScore + "\",\"filmImage\":\""
				+ filmImage + "\",\"filmMagnetUrl\":\"" + filmMagnetUrl
				+ "\",\"filmPlayUrl\":\"" + filmPlayUrl
				+ "\",\"filmBaiDuUrl\":\"" + filmBaiDuUrl
				+ "\",\"filmBaiDuUrlPass\":\"" + filmBaiDuUrlPass
				+ "\",\"filmTorrent\":\"" + filmTorrent + "\"}";
	}

}
