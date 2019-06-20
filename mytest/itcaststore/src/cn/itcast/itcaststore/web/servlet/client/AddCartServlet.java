package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.domain.Product;
import cn.itcast.itcaststore.exception.FindProductByIdException;
import cn.itcast.itcaststore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddCartServlet")
public class AddCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据选择的商品id找到对应的商品信息并添加到购物车
        //1.获取要添加到购物车中图书id
        String id = request.getParameter("id");
        //2.调用service，获取图书的对象，里面封装图书的相关信息
        ProductService service = new ProductService();
        try {
            //3.找到了id对应的图书
            Product p = service.findProductById(id);
            //3.1获取session对象
            HttpSession session = request.getSession();
            //3.2从session中获取购物车的对象
            Map<Product,Integer> cart = ( Map<Product,Integer>)session.getAttribute("cart");
            //3.3如果购物车cart为空，说明没有商品存在cart中，需要创建一个购物车
            if(cart == null){
                cart = new HashMap<Product,Integer>();
            }
            //3.4向购物车添加商品数量信息
            Integer count = cart.put(p,1);
            //3.5如果商品不为空，则商品数量+1，否则添加新的商品信息
            if (count != null){
                cart.put(p,count+1);
            }
            session.setAttribute("cart",cart);
            //跳转到显示购物车的页面
            response.sendRedirect(request.getContextPath()+"/client/cart.jsp");
            return;
        } catch (FindProductByIdException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
