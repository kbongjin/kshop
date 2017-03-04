package com.kmungu.images;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

public class ImgResize {
	public static void main(String[] args) throws Exception {
		
		String orgFilePath = "G:\\project\\kmungu\\webapps\\static\\a\\Document-page-001.jpg";
		String targetFilePath = "G:\\project\\kmungu\\webapps\\static\\a\\Document-page-001_1.jpg";
		String imageType = "jpg";
		
		BufferedImage originalImage = ImageIO.read(new File(orgFilePath));
		
		//int imgwidth = Math.min(originalImage.getHeight(),  originalImage.getWidth());
		//int imgheight = imgwidth;
			
		//BufferedImage scaledImage = Scalr.crop(originalImage, (originalImage.getWidth() - imgwidth)/2, (originalImage.getHeight() - imgheight)/2, imgwidth, imgheight, null);
			
		BufferedImage resizedImage = Scalr.resize(originalImage, Mode.FIT_TO_WIDTH, 1500,  null);
			
		ImageIO.write(resizedImage, imageType, new File(targetFilePath));
		System.out.println("resized!!");
	}
}
