package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.dao.OrderDao;
import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
        生成简单订单信息,作废
 */
public class showOrderInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        Object order = request.getAttribute("order");
        String  receiverAddress= (String) request.getAttribute("receiverAddress");
        String receiverName= (String) request.getAttribute("receiverName");
        String receiverPhone = (String) request.getAttribute("receiverPhone");



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
