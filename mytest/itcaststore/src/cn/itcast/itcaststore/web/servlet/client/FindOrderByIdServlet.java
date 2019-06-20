package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//根据订单编号去查询详细信息（订单信息，商品信息）
public class FindOrderByIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户类型
        String type = request.getParameter("type");
        //得到要查询的订单的id
        String id = request.getParameter("id");
        //创建Service层对象，并根据id查询订单
        OrderService service = new OrderService();
        Order order = service.findOrderById(id);
        //将查询到的订单信息结果添加到域对象（request）
        request.setAttribute("order",order);
        //如果用户类型不为null（admin）,则请求转发到view.jsp
        //否则转发到orderInfo.jsp
        if (type != null){
            request.getRequestDispatcher("/admin/orders/view.jsp").forward(request,response);
         return;
        }else {
            request.getRequestDispatcher("/client/orderInfo.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
