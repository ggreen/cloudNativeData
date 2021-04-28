package io.pivotal.gemfire.domain;

import java.beans.Transient;
import java.util.Arrays;


public class BeaconResponse  {

	private String customerId;
	private String deviceId;
	private String uuid;
	private int major;
	private int minor;
	private int signalPower;
	private String promotionId;
	private String marketingMessage;
	private byte[] marketingBitmap;

	public BeaconResponse() {
	}

	public BeaconResponse(String customerId, String deviceId, String uuid, int major, int minor, int signalPower,
			String promotionId, String marketingMessage, byte[] marketingBitmap) {
		super();
		this.customerId = customerId;
		this.deviceId = deviceId;
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.signalPower = signalPower;
		this.promotionId = promotionId;
		this.marketingMessage = marketingMessage;
		this.marketingBitmap = marketingBitmap;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public int getSignalPower() {
		return signalPower;
	}

	public void setSignalPower(int signalPower) {
		this.signalPower = signalPower;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getMarketingMessage() {
		return marketingMessage;
	}

	public void setMarketingMessage(String marketingMessage) {
		this.marketingMessage = marketingMessage;
	}

	public byte[] getMarketingBitmap() {
		return marketingBitmap;
	}

	public void setMarketingBitmap(byte[] marketingBitmap) {
		this.marketingBitmap = marketingBitmap;
	}

	@Transient
	public String getKey() {
		return this.customerId + "|" + this.deviceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		result = prime * result + major;
		result = prime * result + Arrays.hashCode(marketingBitmap);
		result = prime * result + ((marketingMessage == null) ? 0 : marketingMessage.hashCode());
		result = prime * result + minor;
		result = prime * result + ((promotionId == null) ? 0 : promotionId.hashCode());
		result = prime * result + signalPower;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeaconResponse other = (BeaconResponse) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		if (major != other.major)
			return false;
		if (!Arrays.equals(marketingBitmap, other.marketingBitmap))
			return false;
		if (marketingMessage == null) {
			if (other.marketingMessage != null)
				return false;
		} else if (!marketingMessage.equals(other.marketingMessage))
			return false;
		if (minor != other.minor)
			return false;
		if (promotionId == null) {
			if (other.promotionId != null)
				return false;
		} else if (!promotionId.equals(other.promotionId))
			return false;
		if (signalPower != other.signalPower)
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BeaconResponse [customerId=" + customerId + ", deviceId=" + deviceId + ", uuid=" + uuid + ", major="
				+ major + ", minor=" + minor + ", signalPower=" + signalPower + ", promotionId=" + promotionId
				+ ", marketingMessage=" + marketingMessage + ", marketingBitmap=" + Arrays.toString(marketingBitmap)
				+ "]";
	}


}
