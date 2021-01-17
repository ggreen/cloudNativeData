package io.pivotal.gemfire.domain;

import java.util.Arrays;

public class ProductAssociate
{
	private String[] post;
	private String pre;
	
	public ProductAssociate()
	{}

	
	public ProductAssociate( String pre,String[] post)
	{
		super();
		this.post = post;
		this.pre = pre;
	}


	/**
	 * @return the post
	 */
	public String[] getPost()
	{
		return post;
	}
	/**
	 * @return the pre
	 */
	public String getPre()
	{
		return pre;
	}
	/**
	 * @param post the post to set
	 */
	public void setPost(String[] post)
	{
		this.post = post;
	}
	/**
	 * @param pre the pre to set
	 */
	public void setPre(String pre)
	{
		this.pre = pre;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ProductAssociate [post=").append(Arrays.toString(post)).append(", pre=").append(pre)
		.append("]");
		return builder.toString();
	}
	

}
