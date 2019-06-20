package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.domain.User;
import cn.itcast.itcaststore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 修改用户信息
 */
public class UpdateInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //通过session获取用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //根据用户名修改密码
        UserService service = new UserService();

        String psd = request.getParameter("psd");
        String psd2 = request.getParameter("psd2");
        String sex = request.getParameter("radiobutton");
        String text2 = request.getParameter("text2");


        if (psd.trim().length()>=6 && psd.equals(psd2) ){
               service.updateInfo(psd,sex,text2,user);
            //System.out.print("修改成功");
               String message="信息修改成功";
               request.setAttribute("message", message);
               request.getRequestDispatcher("/client/myAccount.jsp").forward(request,response);
        }else {
            response.getWriter().print("修改失败");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
