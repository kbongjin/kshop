package com.kmungu.images;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
public class Pdftoimg {
	public static void main(String[] args) {
		extractPagesAsImage("G:\\project\\kmungu\\상품자료\\미쓰비시_연필\\제트스트림.pdf", 150, "");
	}

	/**
	 * PDF파일 이미지 출력
	 * @param sourceFile 대상 PDF파일 경로 및 파일
	 * @param dpi 출력 해상도
	 * @param password 문서 비밀번호
	 * @return
	 */
	public static boolean extractPagesAsImage(String pdfFilename, int dpi, String password) {
		boolean result = false;
		
		
		PDDocument pdfDoc = null;
		try {
			//PDF파일 정보 취득
			pdfDoc = PDDocument.load(new File(pdfFilename));
			
			PDFRenderer pdfRenderer = new PDFRenderer(pdfDoc);
			for (int page = 0; page < pdfDoc.getNumberOfPages(); ++page)
			{ 
			    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, dpi, ImageType.RGB);

			    // suffix in filename will be used as the file format
			    ImageIOUtil.writeImage(bim, pdfFilename + "-" + (page+1) + ".jpg", 300);
			}
			pdfDoc.close();
			
		} catch (IOException ioe) {
			System.out.println("PDF 정보취득 실패 : " + ioe.getMessage());
		}
		
		System.out.println("finished!!");
		return result;
	}
}