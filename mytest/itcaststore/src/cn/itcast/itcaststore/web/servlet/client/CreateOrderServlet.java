package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.domain.*;
import cn.itcast.itcaststore.service.OrderService;
import cn.itcast.itcaststore.utils.IdUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Map;

public class CreateOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.得到当前的用户信息---通过session
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        //2.从购物车中获取商品信息
        Map<Product,Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        //3.1将数据(页面提交)封装到订单对象中
        Order order = new Order();
        try {
            BeanUtils.populate(order,request.getParameterMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        order.setUser(user); //封装用户信息到订单
        order.setId(IdUtils.getUUID());//封装订单的id
        //3.2  先把购物车中对应的商品封装到订单条目中，再把订单条目添加到订单中
        for(Product p : cart.keySet()){
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setBuynum(cart.get(p));
            item.setP(p);
            order.getOrderItems().add(item);
        }
        //4.生成订单--在数据库里创建订单
        OrderService service = new OrderService();
        service.addOrder(order);

        String Address = request.getParameter("receiverAddress");
        String Name = request.getParameter("receiverName");
        String Phone = request.getParameter("receiverPhone");

        session.setAttribute("order", order);

        //5.通过4，订单生成成功，跳转到成功页面，回显订单的数据
        response.sendRedirect(request.getContextPath()+"/client/showOrderInfo.jsp?receiverAddress="+ URLEncoder.encode(Address,"UTF-8")+"&receiverName="+Name+"&receiverPhone="+Phone+"&order="+order+"&");
        session.removeAttribute("cart");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
