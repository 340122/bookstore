package cn.itcast.itcaststore.web.servlet.manager;

import cn.itcast.itcaststore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数的id
        String id = request.getParameter("id");
        //创建ServiceProduct对象
        ProductService service = new ProductService();
        //调用ProductService对象的deleteProduct()方法完成删除商品操作
        service.deleteProduct(id);
        //重定向回商品列表页面
        response.sendRedirect(request.getContextPath()+"/listProduct");
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
