package cn.itcast.itcaststore.dao;

import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.domain.OrderItem;
import cn.itcast.itcaststore.domain.Product;
import cn.itcast.itcaststore.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDao {
    //将订单条目添加到订单条目表中--插入数据
    public void addOrderItems(List<OrderItem> orderItems) throws SQLException {
        //1.runner
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.sql
        String sql ="insert into orderItem values(?,?,?)";
        //将单条订单条目数据添加到数据表中
        for(OrderItem item:orderItems){
            String order_id = item.getOrder().getId();
            String p_id = item.getP().getId();
            int num = item.getBuynum();
            int row = runner.update(sql,order_id,p_id,num);
            if (row == 0){
                throw new RuntimeException();
            }
        }
    }
    //根据订单查询订单项，并将订单项中商品查找到
    public List<OrderItem> findOrderItemByOrder(Order order) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql ="select * from orderItem,Products where products.id = orderItem.product_id and order_id = ?";
        return runner.query(sql, new ResultSetHandler<List<OrderItem>>() {
            @Override
            public List<OrderItem> handle(ResultSet rs) throws SQLException {
                List<OrderItem> items = new ArrayList<>();
                while (rs.next()){

                    OrderItem item  = new OrderItem();
                    //得到订单信息
                    item.setOrder(order);
                    item.setBuynum(rs.getInt("buynum"));
                    //获取商品信息
                    Product p = new Product();
                    p.setCategory(rs.getString("category"));
                    p.setId(rs.getString("id"));
                    p.setDescription(rs.getString("description"));
                    p.setImgurl(rs.getString("Imgurl"));
                    p.setName(rs.getString("name"));
                    p.setPnum(rs.getInt("pnum"));
                    p.setPrice(rs.getDouble("price"));
                    item.setP(p);
                    items.add(item);
                }
                return items;
            }
        },order.getId());
    }
    //根据订单id删除订单项
    public void delOrderItems(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from orderItem where order_id = ?";
        runner.update(sql,id);
    }
}
