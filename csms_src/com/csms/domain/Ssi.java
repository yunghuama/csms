package com.csms.domain;

import java.util.Date;

public class Ssi {

	public int sid;
	
	public int sequenceNumber;
	
	public String serviceType;
	
	public int sourceAddressTon;
	
	public int sourceAddressNpi;
	
	public String sourceAddress;//主叫号码
	
	public int destAddressTon;
	
	public int destAddressNpi;
	
	public String destinationAddress;//被叫号码
	
	public int csmClass;
	
	public int protocolId;
	
	public int priorityFlag;
	
	public String scheduleDeliverytime;
	
	public String validityPeroid;
	
	public String timeStamp;//短信发送时间
	
	public int registeredDeliveryFlag;
	
	public int replaceIfPresentFlag;
	
	public int serviceFlag;
	
	public int ssiparty;
	
	public int netid;
	
	public int msgtype;
	
	public int dataCoding;//短信编码格式
	
	public int smDefaultMsgId;
	
	public int smLength;//短信长度
	
	public String shortMessage;//解码后的短信内容
	
	public int sarMsgRefNumV;
	
	public int sarTotalSegmentsV;
	
	public int sarSegmentSeqnumV;
	
	public int state;
	
	public Date createtime;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public int getSourceAddressTon() {
		return sourceAddressTon;
	}

	public void setSourceAddressTon(int sourceAddressTon) {
		this.sourceAddressTon = sourceAddressTon;
	}

	public int getSourceAddressNpi() {
		return sourceAddressNpi;
	}

	public void setSourceAddressNpi(int sourceAddressNpi) {
		this.sourceAddressNpi = sourceAddressNpi;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public int getDestAddressTon() {
		return destAddressTon;
	}

	public void setDestAddressTon(int destAddressTon) {
		this.destAddressTon = destAddressTon;
	}

	public int getDestAddressNpi() {
		return destAddressNpi;
	}

	public void setDestAddressNpi(int destAddressNpi) {
		this.destAddressNpi = destAddressNpi;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public int getCsmClass() {
		return csmClass;
	}

	public void setCsmClass(int csmClass) {
		this.csmClass = csmClass;
	}

	public int getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(int protocolId) {
		this.protocolId = protocolId;
	}

	public int getPriorityFlag() {
		return priorityFlag;
	}

	public void setPriorityFlag(int priorityFlag) {
		this.priorityFlag = priorityFlag;
	}

	

	public String getScheduleDeliverytime() {
		return scheduleDeliverytime;
	}

	public void setScheduleDeliverytime(String scheduleDeliverytime) {
		this.scheduleDeliverytime = scheduleDeliverytime;
	}

	public String getValidityPeroid() {
		return validityPeroid;
	}

	public void setValidityPeroid(String validityPeroid) {
		this.validityPeroid = validityPeroid;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getRegisteredDeliveryFlag() {
		return registeredDeliveryFlag;
	}

	public void setRegisteredDeliveryFlag(int registeredDeliveryFlag) {
		this.registeredDeliveryFlag = registeredDeliveryFlag;
	}

	public int getReplaceIfPresentFlag() {
		return replaceIfPresentFlag;
	}

	public void setReplaceIfPresentFlag(int replaceIfPresentFlag) {
		this.replaceIfPresentFlag = replaceIfPresentFlag;
	}

	public int getServiceFlag() {
		return serviceFlag;
	}

	public void setServiceFlag(int serviceFlag) {
		this.serviceFlag = serviceFlag;
	}

	public int getSsiparty() {
		return ssiparty;
	}

	public void setSsiparty(int ssiparty) {
		this.ssiparty = ssiparty;
	}

	public int getNetid() {
		return netid;
	}

	public void setNetid(int netid) {
		this.netid = netid;
	}

	public int getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(int msgtype) {
		this.msgtype = msgtype;
	}

	public int getDataCoding() {
		return dataCoding;
	}

	public void setDataCoding(int dataCoding) {
		this.dataCoding = dataCoding;
	}

	public int getSmDefaultMsgId() {
		return smDefaultMsgId;
	}

	public void setSmDefaultMsgId(int smDefaultMsgId) {
		this.smDefaultMsgId = smDefaultMsgId;
	}

	public int getSmLength() {
		return smLength;
	}

	public void setSmLength(int smLength) {
		this.smLength = smLength;
	}

	public String getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	public int getSarMsgRefNumV() {
		return sarMsgRefNumV;
	}

	public void setSarMsgRefNumV(int sarMsgRefNumV) {
		this.sarMsgRefNumV = sarMsgRefNumV;
	}

	public int getSarTotalSegmentsV() {
		return sarTotalSegmentsV;
	}

	public void setSarTotalSegmentsV(int sarTotalSegmentsV) {
		this.sarTotalSegmentsV = sarTotalSegmentsV;
	}

	public int getSarSegmentSeqnumV() {
		return sarSegmentSeqnumV;
	}

	public void setSarSegmentSeqnumV(int sarSegmentSeqnumV) {
		this.sarSegmentSeqnumV = sarSegmentSeqnumV;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
