package cn.itcast.itcaststore.web.servlet.manager;

import cn.itcast.itcaststore.domain.Product;
import cn.itcast.itcaststore.exception.ListProductException;
import cn.itcast.itcaststore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
将商品的信息显示到后台管理页面上
 */
public class ListProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建service层对的对象
        ProductService service = new ProductService();

        try {
            //2.调用service层用户查询所有的商品，并返回所有的商品
            List<Product> ps = service.listAll();
            //3.将上述得到的商品集存到域对象
            request.setAttribute("ps",ps);
            //4.重定向到list.jsp页面
            request.getRequestDispatcher("/admin/products/list.jsp").forward(request,response);
            return;
        } catch (ListProductException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
