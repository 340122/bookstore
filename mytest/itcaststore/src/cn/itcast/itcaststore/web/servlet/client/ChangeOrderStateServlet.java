package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeOrderStateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取订单号
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String orderid = request.getParameter("orderid");
        HttpSession session = request.getSession();

        String cart = (String) session.getAttribute("cart");
        session.setAttribute("cart",cart);
        if (orderid != null){
            //修改订单状态
            OrderService service = new OrderService();
            service.updateStatus(orderid);

            response.sendRedirect(request.getContextPath()+"/client/paySuccessInfo.jsp");

        }else {
            response.getWriter().print("订单状态修改失败");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
