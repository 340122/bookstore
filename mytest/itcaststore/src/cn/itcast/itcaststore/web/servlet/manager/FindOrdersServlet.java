package cn.itcast.itcaststore.web.servlet.manager;

import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
获取所有的订单信息，从数据库中查询所有的订单，将其存到域对象，最后显示在网页上
 */
public class FindOrdersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建service层对象
        OrderService service = new OrderService();
        //调用Service层对象findAllOrder（）的方法查询，获得订单列表
        List<Order> orders = service.findAllOrder();
        //将查询的订单信息添加到域对象(request)
        request.setAttribute("orders",orders);
        //请求转发到list.jsp页面
        request.getRequestDispatcher("/admin/orders/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
