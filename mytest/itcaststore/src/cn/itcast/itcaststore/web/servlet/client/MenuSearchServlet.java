package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.domain.PageBean;
import cn.itcast.itcaststore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
根据输入的内容来查找对应的图书信息
 */
public class MenuSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //定义当前的页面，默认为1
        int currentPage = 1;
        //可以从网页中获取当前的页面
        String _currentPage = request.getParameter("currentPage");
        if(_currentPage!=null){
            currentPage = Integer.parseInt(_currentPage);
        }
        //2.定义每页显示的条数，默认为4
        int currentCount = 4;
        //获取网页中当前页面显示的条数
        String _currentCount = request.getParameter("currentCount");
        if(_currentCount!=null){
            currentCount = Integer.parseInt(_currentCount);
        }
        //3.获取搜索框输入的值
        String searchfield = request.getParameter("textfield");
        //如果搜索框中没有输入内容，即默认-->请输入书名，相当于显示全部的图书信息
        if("请输入书名".equals(searchfield)){
            request.getRequestDispatcher("/showProductBuPage").forward(request,response);
            return;
        }
        //有输入内容
        //4.调用service层的方法，通过书名进行模糊查询，查找相应的图书
        ProductService service = new ProductService();
        PageBean bean = service.findBookByName(currentPage, currentCount, searchfield);
        //5.将上述bean存入域对象，并跳转到product显示的页面
        request.setAttribute("bean",bean);
        request.getRequestDispatcher("/client/product_search_list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
