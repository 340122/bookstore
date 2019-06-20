package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DelOrderByIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取订单id
       String id =  request.getParameter("id");
       //已支付的订单带有type值为admin的参数
        String type = request.getParameter("type");
        OrderService service = new OrderService();
        if (type != null && type.trim().length()>0){
            //后台系统，调用service层delOrderById()方法删除相应的订单
            service.delOrderById(id);
            request.getRequestDispatcher("/findOrders").forward(request,response);
            return;
        }else{
            //前台系统，调用service层delOrderByIdWitClient()方法删除相应的订单
            service.delOrderByIdWithClient(id);
        }
        response.sendRedirect(request.getContextPath()+"/client/delOrderSuccess.jsp");
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
