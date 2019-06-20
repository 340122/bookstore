package cn.itcast.itcaststore.web.servlet.client;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import cn.itcast.itcaststore.domain.User;
import cn.itcast.itcaststore.exception.RegisterException;
import cn.itcast.itcaststore.service.UserService;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    // 填写代码：读取网页提交的数据，封装到User对象，注册并添加到数据库
        //乱码处理
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
		// 1. 创建接受表单数据的User对象
        User user = new User();
       // 2.使用BeanUtils将表单提交的数据一次性封装到user对象中
        try {
            BeanUtils.populate(user,request.getParameterMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 3. 调用Service完成注册操作
        UserService service = new UserService();
        try {
            service.register(user);
        } catch (RegisterException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
            return ; // 当注册失败时，退出程序
        }
        // 4. 注册成功，跳到到欢迎页面
        response.sendRedirect(request.getContextPath()+"/client/registersuccess.jsp");
        return ;
    }
}
