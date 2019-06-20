package cn.itcast.itcaststore.web.servlet.manager;

import cn.itcast.itcaststore.domain.Notice;
import cn.itcast.itcaststore.service.NoticeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *	后台添加公告
 */
public class AddNoticeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        NoticeService nService = new NoticeService();
        Notice notice = new Notice();
        //获取表单参数
        String title = request.getParameter("title");
        String details = request.getParameter("details");

        //将当前时间设为添加公告的时间
        String t = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        notice.setTitle(title);
        notice.setDetails(details);
        notice.setN_time(t);
        //调用addNotice方法
        nService.addNotice(notice);

        request.getRequestDispatcher("/manager/ListNoticeServlet").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
