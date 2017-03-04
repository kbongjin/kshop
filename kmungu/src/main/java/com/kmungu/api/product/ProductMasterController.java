package com.kmungu.api.product;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.web.PageableDefault;
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
import com.kmungu.api.product.domain.ProductMaster;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/productMaster")
public class ProductMasterController implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductMasterController.class);
	
	private static final String IMG_BROWSE_CONTEXT = "/static/";
	
	public static String IMG_URI;
	
	@Inject
	@Named("productMasterService")
	private ProductMasterService service;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@Value("${km.product.img.uri}")
	private String imgUri;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public ProductMasterController() {
		// TODO Auto-generated constructor stub
	}

	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public DataTablesOutput<ProductMaster> getList(@Valid DataTablesInput input, String ctg1, String ctg2){
	
		
		return service.getProductMasterList(input, ctg1, ctg2);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse save(SimpleJsonResponse jsonRes, ProductMaster productMaster, @RequestParam(name = "imgFiles", required = false) MultipartFile[] imgFiles,
			Integer[] ctgIds){
		
		service.saveWithImages(productMaster, imgFiles, ctgIds);
		
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{productMasterId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("productMasterId") Integer productMasterId){
		
		service.deleteProductMaster(productMasterId);
		//jsonRes.setMsg("사용자 정보가 정상적으로 삭제되었습니다.");
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{productMasterId}", method = RequestMethod.GET)
	public String getProductMaster(Model model, @PathVariable("productMasterId") Integer productMasterId){
	
		if (productMasterId != null && productMasterId > 0) {
			ProductMaster productMaster = service.getProductMaster(productMasterId);
			
			model.addAttribute("productMaster", productMaster);
			
			return "productMasterAllForm";
		}
		
		return "productMasterForm";
	}
	
	@RequestMapping(value="/{productMasterId}/json", method = RequestMethod.GET)
	@ResponseBody
	public ProductMaster getProductMasterJson(Model model, @PathVariable("productMasterId") Integer productMasterId){
	
		
		return service.getProductMaster(productMasterId);
	}
	
	@RequestMapping(value="/{productMasterId}/products", method = RequestMethod.GET)
	public String getProductList(Model model, @PathVariable("productMasterId") Integer productMasterId){
		
		ProductMaster productMaster = service.getProductMaster(productMasterId);
		List<Product> list = productMaster.getProducts();

		model.addAttribute("list", list);
		
		return "productList";
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
	
	@RequestMapping(value="/{productMasterId}/editor", method = RequestMethod.GET)
	public String getProductContentsEditor(Model model, @PathVariable("productMasterId") Integer productMasterId){
	
		ProductMaster product = service.getProductMaster(productMasterId);
		
		model.addAttribute("productMaster", product);
		
		return "productMasterContentsEditor";
	}
	
	@RequestMapping(value="/contents", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse saveContents(SimpleJsonResponse jsonRes, ProductMaster pp){
		
		ProductMaster product = service.getProductMaster(pp.getId());
		
		product.setContents(pp.getContents());
		
		service.save(product);
		
		return jsonRes;
	}

}
//end of ProductMasterController.java