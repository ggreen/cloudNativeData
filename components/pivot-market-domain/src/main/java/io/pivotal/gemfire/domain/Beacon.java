package io.pivotal.gemfire.domain;

import java.beans.Transient;

public class Beacon {

	private String uuid;
	private int major;
	private int minor;
	private String category;
	private boolean entrance;
	private boolean checkout;

	public Beacon() {
	}

	public Beacon(String uuid, int major, int minor, String category, boolean entrance, boolean checkout) {
		super();
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.category = category;
		this.entrance = entrance;
		this.checkout = checkout;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isEntrance() {
		return entrance;
	}

	public void setEntrance(boolean entrance) {
		this.entrance = entrance;
	}

	public boolean isCheckout() {
		return checkout;
	}

	public void setCheckout(boolean checkout) {
		this.checkout = checkout;
	}

	@Transient
	public String getKey() {
		return this.uuid + "|" + this.major + "|" + this.minor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + (checkout ? 1231 : 1237);
		result = prime * result + (entrance ? 1231 : 1237);
		result = prime * result + major;
		result = prime * result + minor;
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
		Beacon other = (Beacon) obj;
		if (category == null)
		{
			if (other.category != null)
				return false;
		}
		else if (!category.equals(other.category))
			return false;
		if (checkout != other.checkout)
			return false;
		if (entrance != other.entrance)
			return false;
		if (major != other.major)
			return false;
		if (minor != other.minor)
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

	@Override
	public String toString() {
		return "Beacon [uuid=" + uuid + ", major=" + major + ", minor=" + minor + ", category=" + category
				+ ", entrance=" + entrance + ", checkout=" + checkout + "]";
	}


}
