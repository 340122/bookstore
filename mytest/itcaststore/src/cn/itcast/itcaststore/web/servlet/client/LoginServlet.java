package cn.itcast.itcaststore.web.servlet.client;

import cn.itcast.itcaststore.domain.User;
import cn.itcast.itcaststore.exception.LoginException;
import cn.itcast.itcaststore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取表单提交用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //2.调用Servlet来判断此用户名和密码是否正确（数据库中是否存在）
        UserService service = new UserService();
        try{
            User  user= service.login(username,password);
            //登录成功
            //3.1将上述用户的信息存入session中
            request.getSession().setAttribute("user",user);
            //3.2根据用户角色role跳转到不同的页面
            String role = user.getRole();
            if("超级用户".equals(role)){
                response.sendRedirect(request.getContextPath()+"/admin/login/home.jsp");
                return;
            }else{
                response.sendRedirect(request.getContextPath()+"/client/myAccount.jsp");
                return;
            }

        }catch (LoginException e){
            e.printStackTrace();
            request.setAttribute("register",e.getMessage());
            //当用户名和密码不正确时，重新填写用户名和密码，进行再次登录
            request.getRequestDispatcher("/client/login.jsp").forward(request,response);
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
