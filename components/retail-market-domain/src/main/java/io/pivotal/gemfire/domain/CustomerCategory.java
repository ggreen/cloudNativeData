package io.pivotal.gemfire.domain;

public class CustomerCategory {

	private String productId;
	private String categoryId;
	private String promotionId;
	private int weight;

	public CustomerCategory(String productId, String categoryId, String promotionId, int weight) {
		this.productId = productId;
		this.categoryId = categoryId;
		this.promotionId = promotionId;
		this.weight = weight;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
