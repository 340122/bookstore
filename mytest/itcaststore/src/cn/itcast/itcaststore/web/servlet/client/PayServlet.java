package cn.itcast.itcaststore.web.servlet.client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //获得订单号和支付金额
        String orderid = request.getParameter("orderid");
        String money = request.getParameter("money");
        //获取银行数据
        String bank = request.getParameter("yh");
        request.setAttribute("bank", bank);
        request.setAttribute("orderid", orderid);
        request.setAttribute("money", money);
        //转发
        request.getRequestDispatcher("/client/confirm.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
