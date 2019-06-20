package cn.itcast.itcaststore.dao;

import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.domain.User;
import cn.itcast.itcaststore.utils.DataSourceUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*

 */
public class OrderDao {
    //往order表中添加订单---插入数据
    public void addOrder(Order order) throws SQLException {
        //1.runner
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.sql
        String sql = "insert into orders(id,money,receiverAddress,receiverName,receiverPhone,paystate,user_id) values(?,?,?,?,?,0,?)";
        Object[] params = {order.getId(),order.getMoney(),order.getReceiverAddress(),order.getReceiverName(),order.getReceiverPhone(),order.getUser().getId()};
        int row = runner.update(sql,params);
        if(row == 0){
            throw new RuntimeException();
        }
    }
    //后台，在数据库中查询所有的订单信息
    public List<Order> findAllOrder() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select orders.*,user.* from orders,user "
                +"where orders.user_id = user.id order by orders.user_id";
        //利用匿名内部类去重写获取数据的方法
        return runner.query(sql, new ResultSetHandler<List<Order>>() {
            @Override
            public List<Order> handle(ResultSet rs) throws SQLException {
                //创建订单集合
                List<Order> orders = new ArrayList<>();
                //循环白遍历订单和用户信息
                while(rs.next()){
                    //设置订单信息
                    Order order = new Order();
                    order.setId(rs.getString("orders.id"));
                    order.setMoney(rs.getDouble("orders.money"));
                    order.setOrdertime(rs.getDate("orders.ordertime"));
                    order.setPaystate(rs.getInt("orders.paystate"));
                    order.setReceiverAddress(rs.getString("orders.receiverAddress"));
                    order.setReceiverName(rs.getString("orders.receiverName"));
                    order.setReceiverPhone(rs.getString("orders.receiverPhone"));
                    orders.add(order);

                    User user = new User();
                    user.setId(rs.getInt("user.id"));
                    user.setEmail(rs.getString("user.email"));
                    user.setGender(rs.getString("user.gender"));
                    user.setActiveCode(rs.getString("user.activecode"));
                    user.setIntroduce(rs.getString("user.introduce"));
                    user.setPassword(rs.getString("user.password"));
                    user.setRegistTime(rs.getDate("user.registtime"));
                    user.setRole(rs.getString("user.role"));
                    user.setState(rs.getInt("user.state"));
                    user.setTelephone(rs.getString("user.telephone"));
                    user.setUsername(rs.getString("user.username"));
                   //将用户对象放在订单对象中
                    order.setUser(user);
                }
                return orders;
            }
        });
    }
    //后台，在数据库中多条件查询
    public List<Order> findOrderByManyConditio(String id, String receiverName) throws SQLException {
        //创建集合对象
        List<Object> objs = new ArrayList<>();
        //sql
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select orders.*,user.* from orders,user where user.id = orders.user_id";
        //拼接sql语句
        if (id != null && id.trim().length()>0){
            sql += " and orders.id like " + " '%' ? '%' ";
            objs.add(id);
        }
        if (receiverName != null && receiverName.trim().length()>0){
            sql += " and receiverName like " + " '%' ? '%' ";
            objs.add(receiverName);
        }
        sql += "order by orders.user_id";
       return runner.query(sql, new ResultSetHandler<List<Order>>() {
           @Override
           public List<Order> handle(ResultSet rs) throws SQLException {
               List<Order> orders = new ArrayList<>();
               //循环遍历出订单和用户信息
               while(rs.next()){
                   Order order = new Order();
                   order.setId(rs.getString("orders.id"));
                   order.setMoney(rs.getDouble("orders.money"));
                   order.setOrdertime(rs.getDate("orders.ordertime"));
                   order.setPaystate(rs.getInt("orders.paystate"));
                   order.setReceiverAddress(rs.getString("orders.receiverAddress"));
                   order.setReceiverName(rs.getString("orders.receiverName"));
                   order.setReceiverPhone(rs.getString("orders.receiverPhone"));
                   orders.add(order);

                   User user = new User();
                   user.setId(rs.getInt("user.id"));
                   user.setEmail(rs.getString("user.email"));
                   user.setGender(rs.getString("user.gender"));
                   user.setActiveCode(rs.getString("user.activecode"));
                   user.setIntroduce(rs.getString("user.introduce"));
                   user.setPassword(rs.getString("user.password"));
                   user.setRegistTime(rs.getDate("user.registtime"));
                   user.setRole(rs.getString("user.role"));
                   user.setState(rs.getInt("user.state"));
                   user.setTelephone(rs.getString("user.telephone"));
                   user.setUsername(rs.getString("user.username"));
                   //将用户对象放在订单对象中
                   order.setUser(user);
               }
               return orders;
           }
       },objs.toArray());
    }
    //后台，根据id查找订单信息
    public Order dindOrderById(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders,user where user.id = orders.user_id and orders.id=?";
        return runner.query(sql, new ResultSetHandler<Order>() {
            @Override
            public Order handle(ResultSet rs) throws SQLException {
                //创建一个订单
                Order order = new Order();
                while (rs.next()){
                    order.setId(rs.getString("orders.id"));
                    order.setMoney(rs.getDouble("orders.money"));
                    order.setOrdertime(rs.getDate("orders.ordertime"));
                    order.setPaystate(rs.getInt("orders.paystate"));
                    order.setReceiverAddress(rs.getString("orders.receiverAddress"));
                    order.setReceiverName(rs.getString("orders.receiverName"));
                    order.setReceiverPhone(rs.getString("orders.receiverPhone"));

                    User user = new User();
                    user.setId(rs.getInt("user.id"));
                    user.setEmail(rs.getString("user.email"));
                    user.setGender(rs.getString("user.gender"));
                    user.setActiveCode(rs.getString("user.activecode"));
                    user.setIntroduce(rs.getString("user.introduce"));
                    user.setPassword(rs.getString("user.password"));
                    user.setRegistTime(rs.getDate("user.registtime"));
                    user.setRole(rs.getString("user.role"));
                    user.setState(rs.getInt("user.state"));
                    user.setTelephone(rs.getString("user.telephone"));
                    user.setUsername(rs.getString("user.username"));
                    //将用户对象放在订单对象中
                    order.setUser(user);
                }
                return order;
            }
        },id);
    }
    //后台，根据用户查询订单信息
    public List<Order> findOrderByUser(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders where user_id = ?";
        return runner.query(sql, new ResultSetHandler<List<Order>>() {
            @Override
            public List<Order> handle(ResultSet rs) throws SQLException {
                //创建订单集合
                List<Order> orders = new ArrayList<>();
                //循环白遍历订单和用户信息
                while(rs.next()){
                    //设置订单信息
                    Order order = new Order();
                    order.setId(rs.getString("orders.id"));
                    order.setMoney(rs.getDouble("orders.money"));
                    order.setOrdertime(rs.getDate("orders.ordertime"));
                    order.setPaystate(rs.getInt("orders.paystate"));
                    order.setReceiverAddress(rs.getString("orders.receiverAddress"));
                    order.setReceiverName(rs.getString("orders.receiverName"));
                    order.setReceiverPhone(rs.getString("orders.receiverPhone"));
                    orders.add(order);

                    //将用户对象放在订单对象中
                    order.setUser(user);
                }
                return orders;
            }
        },user.getId());
    }
    //根据id删除订单
    public void delOrderById(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from orders where id = ?";
        runner.update(sql,id);
    }

    public void updateStatus(String orderid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update orders set paystate=1 where id=?";
        runner.update(sql, orderid);
    }
}
