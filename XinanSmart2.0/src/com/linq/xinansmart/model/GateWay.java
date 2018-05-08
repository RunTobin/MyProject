package com.linq.xinansmart.model;


public class GateWay {

	private int id;

	private String name;
	private int cid;
	private String IpAddress;
	private int port;
	private boolean isOnline;
	private int timeTick;
	private int code;// Œ®“ª±Í ∂

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getIpAddress() {
		return IpAddress;
	}

	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public int getTimeTick() {
		return timeTick;
	}

	public void setTimeTick(int timeTick) {
		this.timeTick = timeTick;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(" ,name=").append(name);
		sb.append(" ,cid=").append(cid);
		sb.append(" ,IpAddress=").append(IpAddress);
		sb.append(" ,port=").append(port);
		sb.append(" ,isOnline=").append(isOnline);
		sb.append(" ,timeTick=").append(timeTick);
		sb.append(" ,code=").append(code);
		return sb.toString();
	}

}
