package cn.itcast.itcaststore.service;

import cn.itcast.itcaststore.dao.OrderDao;
import cn.itcast.itcaststore.dao.OrderItemDao;
import cn.itcast.itcaststore.dao.ProductDao;
import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.domain.OrderItem;
import cn.itcast.itcaststore.domain.User;
import cn.itcast.itcaststore.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao odao = new OrderDao(); //添加订单
    private OrderItemDao oidao = new OrderItemDao(); //添加订单条目
    private ProductDao pdao = new ProductDao(); //修改库存
    //需要将订单的信息添加到数据库中(订单表，订单条目表，商品（库存）表)
    public void addOrder(Order order) {
        //添加订单和添加订单条目--同时添加成功或者不成功------事务处理

        try {
            //1.开启事务
            DataSourceUtils.startTransaction();
            //2.1向order表中添加订单
            odao.addOrder(order);
            //2.2向orderItem表中添加条目
            oidao.addOrderItems(order.getOrderItems());
            //2.3修改商品表的库存
            pdao.changeProductNum(order);
        } catch (SQLException e) {
            e.printStackTrace();
            //执行不成功--回滚
            try {
                DataSourceUtils.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                DataSourceUtils.releaseAndCloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
        //查询所有的订单,通过oOrderDao来查询数据库中的订单信息
    public List<Order> findAllOrder() {
        List<Order> orders = null;
        try {
            orders = odao.findAllOrder();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    //多条件查询订单信息
    public List<Order> findOrderByManyConditio(String id, String receiverName) {
        List<Order> orders = null;
        try {
            orders = odao.findOrderByManyConditio(id,receiverName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    //根据id查找订单
    public Order findOrderById(String id) {
        Order order = null;
        try {
            order = odao.dindOrderById(id);
            List<OrderItem> items = oidao.findOrderItemByOrder(order);
            order.setOrderItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }
    //根据用户 查询订单信息
    public List<Order> findOrderByUser(User user) {
        List<Order> orders= null;
        try {
            orders = odao.findOrderByUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
        //根据id删除订单，管理员删除订单
    public void delOrderById(String id) {
        try {
            DataSourceUtils.startTransaction();//开启事务
            oidao.delOrderItems(id);//删除订单项
            odao.delOrderById(id);//删除订单
        } catch (SQLException e) {
            e.printStackTrace();
            try{
                DataSourceUtils.rollback();
            }catch(SQLException e1){
                e1.printStackTrace();
            }
        }finally {
            try {
                DataSourceUtils.releaseAndCloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    //普通用户删除订单
    public void delOrderByIdWithClient(String id) {
        try {
            DataSourceUtils.startTransaction();//开启事务
            //从订单项中查找商品购买数量
            Order order=new Order();
            order.setId(id);
            List<OrderItem> items=oidao.findOrderItemByOrder(order);
            //修改商品数量
            pdao.updateProductNum(items);
            oidao.delOrderItems(id); //删除订单项
            odao.delOrderById(id); //删除订单
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DataSourceUtils.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally{
            try {
                DataSourceUtils.releaseAndCloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //修改订单状态
    public void updateStatus(String orderid) {
        try {
            odao.updateStatus(orderid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
