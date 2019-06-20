package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.service.OrderService;
import cn.itcast.itcaststore.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindOrderByUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取user对象
        User user = (User) request.getSession().getAttribute("user");
        //创建Service层，调用service层中的方法，根据用户信息查询订单
        OrderService service = new OrderService();
        List<Order> orders = service.findOrderByUser(user);
        //将查询到的订单信息保存到域对象中
        request.setAttribute("orders",orders);
        //转发
        request.getRequestDispatcher("/client/orderlist.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
