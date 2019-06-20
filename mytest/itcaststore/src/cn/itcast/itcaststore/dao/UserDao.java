package cn.itcast.itcaststore.dao;

import cn.itcast.itcaststore.domain.User;
import cn.itcast.itcaststore.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
/*
将user对象中的数据添加到数据库对应的表中
 */

public class UserDao {
    // 添加用户---不能重复添加相同的用户名
    public void addUser(User user) throws SQLException {
        // 1. 创建java连接数据库连接池的对象
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        // 2. sql语句
        String sql = "insert into user(username,password,gender,email,telephone,introduce,activeCode)" +
                "values(?,?,?,?,?,?,?)";
        String sql2 = "select * from user where username=?";
        User st = runner.query(sql2, new BeanHandler<>(User.class),user.getUsername());
        Object[] params = {user.getUsername(), user.getPassword(), user.getGender(),
                user.getEmail(), user.getTelephone(), user.getIntroduce(), user.getActiveCode()};
        // 3.执行
        if (st == null){
            int row = runner.update(sql,params );
            if(row==0){
                throw new RuntimeException();
            }
        }else{
            throw new RuntimeException("该账号已注册过，请重新注册");
        }

    }
    //根据已给的username和password在数据库中进行查询 ， 看是否有对应的数据
    public User findUserByUsernameAndPassword(String username, String password) throws SQLException {
        //查询 DBUtils（QueryRunner）
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username=? and password=?";
        return runner.query(sql,new BeanHandler<>(User.class),username,password);
    }
    //修改用户信息
    public void updateInfo( String psd, String sex, String text2,User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update user set PASSWORD = ?,gender=?,telephone=? where username=? ";
        runner.update(sql,psd,sex,text2,user.getUsername());
    }
}
