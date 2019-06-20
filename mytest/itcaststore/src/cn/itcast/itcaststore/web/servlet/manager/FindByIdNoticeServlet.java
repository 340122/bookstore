package cn.itcast.itcaststore.web.servlet.manager;

import cn.itcast.itcaststore.domain.Notice;
import cn.itcast.itcaststore.service.NoticeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *	后台根据id查询公告
 */
public class FindByIdNoticeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NoticeService nService = new NoticeService();
        //获取公告id
        String id = request.getParameter("id");
        Notice notice = nService.findNoticeById(id);

        request.setAttribute("n", notice);

        request.getRequestDispatcher("/admin/notices/edit.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
