package com.kmungu.api.product;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kmungu.api.common.model.SimpleJsonResponse;
import com.kmungu.api.product.domain.Product;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/product")
public class ProductController implements InitializingBean{

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	private static final String IMG_BROWSE_CONTEXT = "/static/";
	
	public static String IMG_URI;
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${km.product.img.uri}")
	private String imgUri;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public ProductController() {
		// TODO Auto-generated constructor stub
	}
	/*
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public GridJsonResponse allList(GridJsonResponse jsonRes){
	
		List<Product> list = service.getProductAllList();

		jsonRes.setTotal(list.size());
		jsonRes.setList(list);
		
		return jsonRes;
	}*/
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	//public SimpleJsonResponse getList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	public DataTablesOutput<Product> getList(@Valid DataTablesInput input, String ctg1, String ctg2){
	/*
		Page<Product> list = service.getProductList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
		*/

		return service.getProductList(input, ctg1, ctg2);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, Product product, @RequestParam(name = "imgFiles", required = false) MultipartFile[] imgFiles){
		
		service.saveWithImages(product, imgFiles);
		
		return jsonRes;
	}
	
	
	
	@RequestMapping(value="/{productId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("productId") Integer productId){
		
		service.deleteProduct(productId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{productId}", method = RequestMethod.GET)
	public String getProduct(Model model, @PathVariable("productId") Integer productId){
	
		if (productId > 0) {
			Product product = service.getProduct(productId);
			
			//product.setImgUri(imgUri);
			model.addAttribute("product", product);
		}
		
		model.addAttribute("categories", service.getCategoryAll());
		
		return "productForm";
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		IMG_URI = imgUri;
	}
	
	@RequestMapping(value="/ckeditorImageUpload", method=RequestMethod.POST)
	public void ckeditorImageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload) {
		
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset-utf-8");
		
		String callback = request.getParameter("CKEditorFuncNum");
		String fileUrl = "";
		PrintWriter printWriter = null;
		
		try {
			String fileName = service.saveProductContentsImage(upload);
			
			fileUrl = IMG_BROWSE_CONTEXT + fileName; //url 경로
			
			printWriter = response.getWriter();
			printWriter.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
		               + callback
		               + ",'"
		               + fileUrl
		               + "','업로드 성공.')</script>");

		    printWriter.flush();

		} catch (IOException e) {

			LOGGER.error(e.toString(), e);
			
			try{
				printWriter = response.getWriter();
				printWriter.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
			               + callback
			               + ",'"
			               + fileUrl
			               + "','업로드 실패.')</script>");
	
			    printWriter.flush();
			} catch (IOException ex) {}

		} finally {
			if (printWriter != null) {
                printWriter.close();
            }
        }

	}

}
//end of ProductController.java