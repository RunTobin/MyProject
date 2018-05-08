package com.linq.xinansmart.model;

public class Add_Device_Event {

	private long db_id;
	private String dev_nickname;
	private String dev_uid;
	private String view_acc;
	private String view_pwd;
	private int camera_channel;
	
	public Add_Device_Event(long Db_id,String Dev_nickname,String Dev_uid,String View_acc,String View_pwd,int Camera_channel){
		this.db_id=Db_id;
		this.dev_nickname=Dev_nickname;
		this.dev_uid=Dev_uid;
		this.view_acc=View_acc;
		this.view_pwd=View_pwd;
		this.camera_channel=Camera_channel;
	}

	public long getDb_id() {
		return db_id;
	}

	public String getDev_nickname() {
		return dev_nickname;
	}

	public String getDev_uid() {
		return dev_uid;
	}

	public String getView_acc() {
		return view_acc;
	}

	public String getView_pwd() {
		return view_pwd;
	}

	public int getCamera_channel() {
		return camera_channel;
	}
	
	
}
