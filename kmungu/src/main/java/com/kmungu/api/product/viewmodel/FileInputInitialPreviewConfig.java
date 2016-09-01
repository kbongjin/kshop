/**
 * 
 */
package com.kmungu.api.product.viewmodel;

import com.kmungu.api.product.domain.ProductImg;

/**
 * @author Administrator
 *
 */
public class FileInputInitialPreviewConfig {
	
	private String caption;
	
	private Long size;
	
	private String width = "100px";
	
	private int key;

	public FileInputInitialPreviewConfig(ProductImg img) {
		this.caption = img.getImgPath();
		this.size = img.getFileSize();
		this.key = img.getKey();
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
}
