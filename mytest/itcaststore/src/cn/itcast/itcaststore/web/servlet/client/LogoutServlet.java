package cn.itcast.itcaststore.web.servlet.client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //退出：销毁登录所产生的session对象
        //1.得到session
        HttpSession session = request.getSession();
        //销毁session
        session.invalidate();
        //flag标识
        String flag = request.getParameter("flag");
        if(flag == null || flag.trim().isEmpty()){
            //重新定向到首页
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
