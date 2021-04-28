package io.pivotal.gemfire.domain;

import java.beans.Transient;
import java.math.BigInteger;


public class BeaconRequest {
	private CustomerIdentifier customerId;
	private String deviceId;
	private String uuid;
	private int major;
	private int minor;
	private int signalPower;

	public BeaconRequest() {
	}

	public BeaconRequest(CustomerIdentifier customerId, String deviceId, String uuid, int major, int minor, int signalPower) {
		this.customerId = customerId;
		this.deviceId = deviceId;
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.signalPower = signalPower;
	}

	/**
	 * @return the customerId
	 */
	public CustomerIdentifier getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(CustomerIdentifier customerId)
	{
		this.customerId = customerId;
	}

	public String getHexUuid() {
		return toHex(uuid);
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

	public int getSignalPower() {
		return signalPower;
	}

	public void setSignalPower(int signalPower) {
		this.signalPower = signalPower;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String userDeviceId) {
		this.deviceId = userDeviceId;
	}

	public String toHex(String arg) {
		return String.format("%040x", new BigInteger(1, arg.getBytes()));
	}

	@Transient
	public String getKey() {
		return this.customerId + "|" + this.deviceId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		result = prime * result + major;
		result = prime * result + minor;
		result = prime * result + signalPower;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeaconRequest other = (BeaconRequest) obj;
		if (customerId == null)
		{
			if (other.customerId != null)
				return false;
		}
		else if (!customerId.equals(other.customerId))
			return false;
		if (deviceId == null)
		{
			if (other.deviceId != null)
				return false;
		}
		else if (!deviceId.equals(other.deviceId))
			return false;
		if (major != other.major)
			return false;
		if (minor != other.minor)
			return false;
		if (signalPower != other.signalPower)
			return false;
		if (uuid == null)
		{
			if (other.uuid != null)
				return false;
		}
		else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("BeaconRequest [customerId=").append(customerId).append(", deviceId=").append(deviceId)
		.append(", uuid=").append(uuid).append(", major=").append(major).append(", minor=").append(minor)
		.append(", signalPower=").append(signalPower).append("]");
		return builder.toString();
	}

	
}