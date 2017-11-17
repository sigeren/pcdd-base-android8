package com.df.vip.pcdd.network.bean;

public class VersionInfo {
	public int id;
	public String version_no;
	public int version_code;
	public String update_content;
	public String version_url;
	public int status;			//1强制升级  0建议升级
	public long create_time;

	@Override
	public String toString() {
		return "VersionInfo{" +
				"id=" + id +
				", version_no='" + version_no + '\'' +
				", version_code=" + version_code +
				", update_content='" + update_content + '\'' +
				", version_url='" + version_url + '\'' +
				", status=" + status +
				", create_time=" + create_time +
				'}';
	}
}
