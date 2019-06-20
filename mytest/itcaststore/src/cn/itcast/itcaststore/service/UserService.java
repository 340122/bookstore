package cn.itcast.itcaststore.service;

import cn.itcast.itcaststore.dao.UserDao;
import cn.itcast.itcaststore.domain.User;
import cn.itcast.itcaststore.exception.LoginException;
import cn.itcast.itcaststore.exception.RegisterException;

import java.sql.SQLException;

/*
注册：将user对象添加到数据库中
调用操作数据库的对象
 */
public class UserService {
    // 1. 创建操作数据库的对象
    private UserDao dao = new UserDao();
    // 2. 注册
    public void register(User user) throws RegisterException {
        // 将用户添加到数据库
        try {
            dao.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegisterException("注册失败");
        }
    }
    //2.根据获取的用户名和密码登录 --->实际上就是在数据库中查询
    //是否有结果
    public User login(String username, String password) throws LoginException {

        try {
            User user = dao.findUserByUsernameAndPassword(username,password);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LoginException("用户名或密码错误");
        }
    }
    //修改用户信息
    public void updateInfo(String psd,String sex,String text2,User user) {
        try {
            dao.updateInfo(psd,sex,text2,user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
