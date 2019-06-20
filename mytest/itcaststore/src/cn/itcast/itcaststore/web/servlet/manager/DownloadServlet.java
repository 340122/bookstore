package cn.itcast.itcaststore.web.servlet.manager;

import cn.itcast.itcaststore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String year = request.getParameter("year");//年份
        String month = request.getParameter("month");//月份
        //创建Service层对象
        ProductService service = new ProductService();
        //调用service层用于查询销售数据的方法download()
        List<Object[]> ps = service.download(year,month);
        //拼接文件名
        String fileName = year + "年" + month + "月销售榜.csv";
        //使客户端浏览器区分不同种类的数据
        response.setContentType(this.getServletContext().getMimeType(fileName));
        //设置文件名
        response.setHeader("Content-Disposition","attachment;filename=" +
                new String(fileName.getBytes("GBK"),"iso8859-1"));
        response.setCharacterEncoding("gbk");
        //向文件名写入数据
        PrintWriter out = response.getWriter();
        out.println("商品名称，销售数量");
        for (int i=0;i<ps.size();i++){
            Object[] arr = ps.get(i);
            out.println(arr[0]+","+arr[1]);
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
