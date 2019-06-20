package cn.itcast.itcaststore.web.servlet.manager;

import cn.itcast.itcaststore.domain.Product;
import cn.itcast.itcaststore.exception.AddProductException;
import cn.itcast.itcaststore.service.ProductService;
import cn.itcast.itcaststore.utils.FileUploadUtils;
import cn.itcast.itcaststore.utils.IdUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProductServlet extends HttpServlet {
    /*
    获取表单中的数据，封装到javaBean中，调用service方法往数据库中添加内容
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建Product对象，用于封装提交的数据
        Product p = new Product();
        //创建一个Map集，用于保存各种商品数据，统一赋值给product对象中
        //键值对
        Map<String,String> map = new HashMap<>();
        //封装商品的id
        map.put("id", IdUtils.getUUID());
        //上传图片--封装图片到map中
        //创建工厂对象
        DiskFileItemFactory dfif = new DiskFileItemFactory();
        //设置临时文件储存位置
        String temp = this.getServletContext().getRealPath("/temp");
        dfif.setRepository(new File(temp));
        //设置临时文件大小
        dfif.setSizeThreshold(1024*1024*10);

        //根据工厂对象，得到解析器对象
        ServletFileUpload upload = new ServletFileUpload(dfif);
        //处理中文乱码问题
        upload.setHeaderEncoding("utf-8");
        try {
            //获取表单提交的数据,即得到FileItem
            List<FileItem> items = upload.parseRequest(request);
            //遍历----对于单个记录进行处理
            for (FileItem item:items){
                //判断组件的类型（即提交上来的数据类型）
                Boolean flag = item.isFormField();
                if (flag){//是普通组件---非上传，直接输入的内容
                    //得到组件的名称(name属性对应的值需要跟数据表中字段名一致)
                    String filename = item.getFieldName();
                    //解决乱码问题
                    String value = item.getString("utf-8");
                    map.put(filename,value);

                }else {
                    //不是普通组件--是上传文件(文档，图片，视频)
                    //去掉路径，得到上传文件真实名称
                    String fileName = item.getName();
                    fileName = FileUploadUtils.subFileName(fileName);
                    //避免文件重名，得到随机名称
                    String randomName = FileUploadUtils.generateRandonFileName(fileName);
                    //创建随机目录，避免同一个目录下存放文件过多
                    String randomDir = FileUploadUtils.generateRandomDir(randomName);
                    //图片存储父目录
                    String imgurl_parent = "/productImg" + randomDir;
                    String imgPath = this.getServletContext().getRealPath(imgurl_parent);
                    File parentDir = new File(imgPath);
                    //验证目录是否存在，若不存在，则创建
                    if (!parentDir.exists()){
                        parentDir.mkdirs();
                    }
                    //拼接图片存在的地址
                    String imgurl = imgurl_parent + "/" + randomName;
                    map.put("imgurl",imgurl);
                    //将文件从本地上传到服务器---输入输出流
                    InputStream input = item.getInputStream();
                    FileOutputStream output = new FileOutputStream(new File(parentDir,randomName));
                    IOUtils.copy(input,output);//
                    item.delete();//删除临时文件
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        try {
            //把上传的商品数据保存到product对象中
            BeanUtils.populate(p,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service层方法完成商品添加
        ProductService service = new ProductService();
        try {
            service.addProduct(p);
            //添加后台商品信息成功，重新查询显示
            response.sendRedirect(request.getContextPath()+"/listProduct");
            return;
        } catch (AddProductException e) {
            e.printStackTrace();
            response.getWriter().print("添加商品失败");
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
