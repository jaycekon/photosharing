package com.sherlochao.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sherlochao.bean.BookApiBean;
import com.sherlochao.common.CommonConstants;
import com.sherlochao.common.Constants;
import com.sherlochao.model.Book;
import com.sherlochao.model.Favorites;
import com.sherlochao.model.Member;
import com.sherlochao.service.BookService;
import com.sherlochao.service.FavoritesService;
import com.sherlochao.service.MemberService;
import com.sherlochao.util.FileUtils;
import com.sherlochao.util.JsonUtils;
import com.sherlochao.util.MyBeanUtils;
import com.sherlochao.util.ParamsUtils;
import com.sherlochao.util.StringUtils;

@Slf4j
@Controller
@RequestMapping("/bookapi")
public class BookApi {

	@Resource
	private BookService bookService;

	@Resource
	private FavoritesService favoritesService;
	
	@Resource
	private MemberService memberService;

	/**
	 * 发布图书
	 *
	 * memberId 会员ID
	 * 
	 * @return JSONObject
	 */
	@RequestMapping("/publishBook")
	@ResponseBody
	public JSONObject publishBook(HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		try {
			Integer memberId = ParamsUtils.getInt(request
					.getParameter("memberId")); // 发布用户id
			String bookname = ParamsUtils.getString(request
					.getParameter("bookname"));
			bookname = new String(bookname.getBytes("ISO-8859-1"), "UTF-8"); 
			String bookauthor = ParamsUtils.getString(request
					.getParameter("bookauthor"));
			bookauthor = new String(bookauthor.getBytes("ISO-8859-1"), "UTF-8"); 
			Integer bookClass = ParamsUtils.getInt(request
					.getParameter("bookClass")); // 求购还是待换
			String booktype = ParamsUtils.getString(request
					.getParameter("booktype")); // 书的类别
			booktype = new String(booktype.getBytes("ISO-8859-1"), "UTF-8"); 
			String iswanted = ParamsUtils.getString(request
					.getParameter("iswanted")); // 想换什么或者我有什么
			iswanted = new String(iswanted.getBytes("ISO-8859-1"), "UTF-8"); 
			String remark = ParamsUtils.getString(request
					.getParameter("remark")); // 备注
			remark = new String(remark.getBytes("ISO-8859-1"), "UTF-8"); 
			String contact = ParamsUtils.getString(request
					.getParameter("contact")); // 联系方式
			contact = new String(contact.getBytes("ISO-8859-1"), "UTF-8"); 
			Member member = memberService.findById(memberId);
			if(member == null){
				jsonObj.put("result", 0);
				jsonObj.put("msg", "无此用户");
				return jsonObj;
			}
			Book book = new Book();
			book.setAuthor(bookauthor);
			book.setBookClass(bookClass);
			book.setBookCreateDate(System.currentTimeMillis());
			book.setBookName(bookname);
			book.setBookType(booktype);
			book.setContact(contact);
			book.setIsWanted(iswanted);
			book.setMemberId(memberId);
			book.setRemark(remark);
			book.setIsdel(0);
			book.setIsSussess(0);
			book.setBookCreateDate(System.currentTimeMillis());
			System.out.println(book.toString());

			if (ServletFileUpload.isMultipartContent(request)) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				// 得到上传的图片数据
				List<MultipartFile> portraits = multipartRequest
						.getFiles("bookImages");
				if (portraits.size() > 0) {
					MultipartFile[] portrait = portraits
							.toArray(new MultipartFile[portraits.size()]);
					Map<String, Object> map = FileUtils.fileUpload(portrait,
							CommonConstants.FILE_BASEPATH,
							Constants.BOOK_UPLOAD_URL, request, "images", 1);
					if ("true".equals(map.get("success") + "")) {
						book.setBookImageMore(map.get("result") + "");
					}
				}
			}
			bookService.save(book);
			jsonObj.put("result", 1);
			jsonObj.put("msg", "发布成功");
		} catch (Exception e) {
			log.error("发布图书信息API出错", e);
			jsonObj.put("result", 0);
			jsonObj.put("msg", "服务器异常");
		}

		return jsonObj;
	}

	/**
	 * 书本详细信息api
	 * 
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/bookdetail")
	@ResponseBody
	public JSONObject bookApi(
			@RequestParam int bookId,
			@RequestParam(required = false, value = "memberId", defaultValue = "") String memberId) {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj = new JSONObject();
			BookApiBean bean = new BookApiBean();
			Book book = bookService.findBookById(bookId);
			if (null == book) {
				jsonObj.put("result", 1);
				jsonObj.put("msg", "无数据");
				jsonObj.put("data", "[]");
				return jsonObj;
			}
			MyBeanUtils.copyBeanNotNull2Bean(book, bean);

			/**
			 * 查询图书是否收藏
			 */
			int isFav = 0;
			if (StringUtils.isNotBlank(memberId)) {
				isFav = favoritesService.findcountFav(bookId,
						Integer.valueOf(memberId));
			}
			bean.setIsFav(isFav); // 非0即是已收藏

			jsonObj.put("result", 1);
			jsonObj.put("msg", "获取成功");
			jsonObj.put("data",
					JSONArray.fromObject(bean, JsonUtils.getJsonConfig()));
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("result", 0);
			jsonObj.put("msg", "服务器异常");
			jsonObj.put("data", "[]");
		}

		return jsonObj;
	}

	/**
	 * 图书收藏
	 * 
	 * @param memberId
	 * @param bookId
	 * @return
	 */
	@RequestMapping("/bookcollection")
	@ResponseBody
	public JSONObject bookCollection(
			@RequestParam(value = "memberId") Integer memberId,
			@RequestParam(value = "bookId") Integer bookId) {
		JSONObject jsonObj = new JSONObject();
		try {
			// 1已收藏，0未收藏
			int isFav = favoritesService.findcountFav(bookId, memberId);

			if (isFav == 0) {
				favoritesService.saveFavBook(bookId, memberId);
				jsonObj.put("result", 1);
				jsonObj.put("isfav", 1);
				jsonObj.put("msg", "收藏成功");
			}
//			 else if (isFav == 1) {
//				favoritesService.deleteFavBook(bookId, memberId);
//				jsonObj.put("result", 1);
//				jsonObj.put("isfav", 0);
//				jsonObj.put("msg", "取消收藏成功");
//			}
		} catch (Exception e) {
			log.error("收藏API出错", e);
			jsonObj.put("result", 0);
			jsonObj.put("msg", "服务器异常");
		}
		return jsonObj;
	}
	
	/**
	 * 取消收藏
	 * @param bookId
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/bookcanclecollection")
	@ResponseBody
	public JSONObject bookCancleCollection(
			@RequestParam(value = "bookId") Integer bookId,
			@RequestParam(value = "memberId") Integer memberId
			){
		JSONObject jsonObj = new JSONObject();
		try {
			Favorites favorites = new Favorites();
			favorites.setMemberId(memberId);
			favorites.setFavId(bookId);
			favoritesService.deleteAllFav(favorites);
			jsonObj.put("result", 1);
			jsonObj.put("msg", "取消收藏成功");
		} catch (Exception e) {
			log.error("取消用户收藏API出错", e);
			jsonObj.put("result", 0);
			jsonObj.put("msg", "服务器异常");
		}
		return jsonObj;
	}
	
	
	/**
	 * 已交易成功 or 已删除
	 * @param isSussess
	 * @param isdel
	 * @return
	 */
	@RequestMapping("/updateBookInfo")
    @ResponseBody
    public JSONObject updateMember(
            @RequestParam(value = "isSussess", required = false) Integer isSussess,
            @RequestParam(value = "isdel", required = false) Integer isdel,
            @RequestParam(value = "bookId") Integer bookId) {
		JSONObject jsonObj = new JSONObject();
		try {
			Book book = bookService.findBookById(bookId);
			if(book == null){
				jsonObj.put("result", 0);
				jsonObj.put("msg", "该书本不存在");
				return jsonObj;
			}
			if(isSussess != null)
				book.setIsSussess(1);
			if(isdel != null)
				book.setIsdel(1);
			bookService.update(book);
			jsonObj.put("result", 1);
			jsonObj.put("msg", "操作成功");
		} catch (Exception e) {
			log.error("更新书籍信息API出错", e);
			jsonObj.put("result", 0);
			jsonObj.put("msg", "服务器异常");
		}
		return jsonObj;

    }
	
	

	/**
	 * 展现书本的列表 所有的都写在了一起
	 * @param searchType  所有中搜索 还是 分类搜索
	 * @param keyword  搜索关键词
	 * @param memberId  用户id
	 * @param isFav  用户是否收藏  传1是收藏  传1是非收藏
	 * @param bookClass  求书还是待换
	 * @return
	 */
	@RequestMapping("/goodslist")
	@ResponseBody
	public JSONObject goodsListApi(
			@RequestParam(value="searchType",required=false) String searchType,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="memberId",required=false)Integer memberId,
			@RequestParam(value="isFav",required=false)Integer isFav,
			@RequestParam(value="bookClass")Integer bookClass){
		JSONObject jsonObj = new JSONObject();
		try {
			List<Book> bookList = new ArrayList<Book>();
			if(searchType != null & keyword != null & isFav == 0){
				bookList = bookService.findBookByKeywordAndSearchType(bookClass,keyword,searchType); //根据分类搜索
			}else if(keyword != null & isFav == 0){
				bookList = bookService.findBookByKeyword(bookClass,keyword); //搜索所有的 求购或者待换的书
			}else if(isFav == 0 && searchType != null){
				bookList = bookService.findBookByClassAndType(bookClass,searchType); //主页 根据分类来显示所有待换 或者 求购的书
			}else if(isFav == 0){
				bookList = bookService.findBookByBookClass(bookClass);  //主页 单纯的显示所有 待换 或者 求换的书
			}else
				bookList = bookService.findBookByIsFav(bookClass,isFav,memberId); //显示用户收藏的 待换 或者 求换的书

			List<BookApiBean> beanList = new ArrayList<>();
			if(null != bookList){
				for (Book book : bookList) {
					BookApiBean detail = new BookApiBean();
					MyBeanUtils.copyBeanNotNull2Bean(book, detail);
					beanList.add(detail);
				}
				jsonObj.put("searchType", searchType);
				jsonObj.put("keyword", keyword);
				jsonObj.put("memberId", memberId);
				jsonObj.put("bookClass", bookClass);
				jsonObj.put("isFav", isFav);
				jsonObj.put("result", 1);
				jsonObj.put("data", JSONArray.fromObject(beanList, JsonUtils.getJsonConfig()));
			}else{
				jsonObj.put("result", 1);
				jsonObj.put("msg", "无数据");
				jsonObj.put("data", "[]");
			}
		} catch (Exception e) {
			log.error("书本列表API出错", e);
			jsonObj.put("result", 0);
			jsonObj.put("msg", "服务器异常");
			jsonObj.put("data", "[]");
		}
		
		return jsonObj;
	}
	
	

}
