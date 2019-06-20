package cn.itcast.itcaststore.web.servlet.manager;

import cn.itcast.itcaststore.domain.Product;
import cn.itcast.itcaststore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class FindProductByManyConditionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取表单中查询的参数
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");
        //2.创建ProductService对象
        ProductService service = new ProductService();
        //3.调用service层用于条件查询的方法
        List<Product> ps = service.findProductByManyConfition(id,name,category,minprice,maxprice);
        //将ps中的数据存入域对象
        request.setAttribute("ps",ps);
        //5.请求重定向到商品管理首页list.jsp
        request.getRequestDispatcher("/admin/products/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
