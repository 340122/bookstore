package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.domain.Notice;
import cn.itcast.itcaststore.service.NoticeService;
import cn.itcast.itcaststore.service.ProductService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *	前台页面展示
 *	展示最新添加或修改的一条公告
 *  展示本周热销商品
 */
public class ShowIndexServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//查询最近一周一条公告
		NoticeService noticeService = new NoticeService();
		Notice notice = noticeService.getRecentNotices();
		request.setAttribute("n",notice);
		//查询本周热卖的两条商品
		ProductService productService = new ProductService();
		List<Object[]> pList = productService.getWeekHotProduct();
		request.setAttribute("pList",pList);
		//请求转发
		request.getRequestDispatcher("/client/index.jsp").forward(request, response);
	}
}
