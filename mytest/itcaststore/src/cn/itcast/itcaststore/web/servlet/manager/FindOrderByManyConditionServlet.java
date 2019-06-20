package cn.itcast.itcaststore.web.servlet.manager;

import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindOrderByManyConditionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从页面获取订单编号和收件人名称
        String id = request.getParameter("id");
        String receiverName = request.getParameter("receiverName");
        //创建Service层对象
        OrderService service = new OrderService();
        //调用Service层查询订单列表的方法
        List<Order> orders = service.findOrderByManyConditio(id,receiverName);
        //将查询结果添加到域对象中（request）
        request.setAttribute("orders",orders);
        //请求转发
        request.getRequestDispatcher("/admin/orders/list.jsp").forward(request,response);
        //response.sendRedirect("admin/orders/list.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
