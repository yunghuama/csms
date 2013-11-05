package com.csms.domain;

import java.util.Date;

public class Warn {
	
	private String id;
	private int cpu;
	private int memusage;
	private int memtotal;
	private String createtime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCpu() {
		return cpu;
	}
	public void setCpu(int cpu) {
		this.cpu = cpu;
	}
	public int getMemusage() {
		return memusage;
	}
	public void setMemusage(int memusage) {
		this.memusage = memusage;
	}
	public int getMemtotal() {
		return memtotal;
	}
	public void setMemtotal(int memtotal) {
		this.memtotal = memtotal;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	
}
