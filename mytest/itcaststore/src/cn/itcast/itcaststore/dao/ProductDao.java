package cn.itcast.itcaststore.dao;

import cn.itcast.itcaststore.domain.Notice;
import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.domain.OrderItem;
import cn.itcast.itcaststore.domain.Product;
import cn.itcast.itcaststore.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    // 根据id查找商品
    public Product findProductById(String id) throws SQLException {
        String sql = "select * from products where id=?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        Product p = runner.query(sql, new BeanHandler<Product>(Product.class), id);
        return p;
    }

    //前台，用于搜索框根据书名来模糊查询相应的图书
    public List<Product> findBookByName(int currentPage, int currentCount,
                                        String searchfield) throws SQLException {
        //根据名字模糊查询图书
        String sql = "SELECT * FROM products WHERE name LIKE '%"+searchfield+"%' LIMIT ?,?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
//		//用于分页查询的数据
//		Object obj = new Object[] { (currentPage - 1) * currentCount, currentCount };
        return runner.query(sql,
                new BeanListHandler<Product>(Product.class),(currentPage-1)*currentCount,currentCount);
    }

    //前台搜索框，根据书名模糊查询出的图书总数量
    public int findBookByNameAllCount(String searchfield) throws SQLException {
        String sql = "SELECT COUNT(*) FROM products WHERE name LIKE '%"+searchfield+"%'";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        //查询出满足条件的总数量，为long类型
        Long count = (Long)runner.query(sql, new ScalarHandler());
        return count.intValue();
    }
    // 获取数据总条数
    public int findAllCount(String category) throws SQLException {
        String sql = "select count(*) from products";

        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

        if (!"全部商品".equals(category)) {
            sql += " where category=?";

            Long count = (Long) runner
                    .query(sql, new ScalarHandler(), category);
            return count.intValue();
        } else {
            Long count = (Long) runner.query(sql, new ScalarHandler());

            return count.intValue();
        }
    }
    // 获取当前页数据
    public List<Product> findByPage(int currentPage, int currentCount,
                                    String category) throws SQLException {
        // 要执行的sql语句
        String sql = null;
        // 参数
        Object[] obj = null;
        // 如果category不为null,代表是按分类查找
        if (!"全部商品".equals(category)) {
            sql = "select * from products  where category=? limit ?,?";
            obj = new Object[] { category, (currentPage - 1) * currentCount,
                    currentCount, };
        } else {
            sql = "select * from products  limit ?,?";
            obj = new Object[] { (currentPage - 1) * currentCount,
                    currentCount, };
        }
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql, new BeanListHandler<Product>(Product.class),
                obj);
    }
    //修改商品表的库存
    public void changeProductNum(Order order) throws SQLException {
        //1.runner
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        //2.sql
        String sql = "update products set pnum = pnum - ? where id = ?";
        //首先从order中获取订单条目
        List<OrderItem> items = order.getOrderItems(); //条数不知
        for (OrderItem item : items){
            String p_id = item.getP().getId();
            int item_num = item.getBuynum();
           int row =  runner.update(sql,item_num,p_id);
           if (row==0){
               throw new RuntimeException();
           }
        }

    }
    //查询所有的商品信息
    public List<Product> listAll() throws SQLException {
      QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
      String sql = "select * from products";
      return runner.query(sql,new BeanListHandler<>(Product.class));
    }
    //多条件查询，根据指定的条件来查询数据库并获取数据
    public List<Product> findProductByManyCondition(String id, String name, String category, String minprice, String maxprice) throws SQLException {
        //难点，在于查询的条件是可变的，至少是一个    ----拼接sql
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from products where 1=1";
        List<Object> list = new ArrayList<>();//用来保存查询的参数
        //拼接sql语句
        //id
        if (id!=null && id.trim().length()>0){
            sql += " and id like "+" '%' ? '%' ";
            list.add(id);
        }
        //name
        if(name!=null && name.trim().length()>0){
            sql += " and name like "+" '%' ? '%' ";
            list.add(name);
        }
        //category
        if (category!=null && category.trim().length()>0){
            sql += " and category=?";
            list.add(category);
        }
        //price
        if (minprice != null && maxprice != null && minprice.trim().length()>0 && maxprice.trim().length()>0){
            sql += " and price between ? and ?";
            list.add(minprice);
            list.add(maxprice);
        }
        //将集合转化为对象数组类型
        Object[] params = list.toArray();
        return runner.query(sql,new BeanListHandler<>(Product.class),params);
    }

        //后台添加商品的方法
    public void addProduct(Product p) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into products values(?,?,?,?,?,?,?)";
        runner.update(sql,p.getId(),p.getName(),p.getPrice(),p.getCategory(),p.getPnum(),p.getImgurl(),p.getDescription());
    }

    //删除订单时，修改商品数量
    public void updateProductNum(List<OrderItem> items) throws SQLException {

        String sql = "update products set pnum=pnum+? where id=?";
        QueryRunner runner = new QueryRunner();

        Object[][] params = new Object[items.size()][2];

        for (int i = 0; i < params.length; i++) {
            params[i][0] = items.get(i).getBuynum();
            params[i][1] = items.get(i).getP().getId();
        }

        runner.batch(DataSourceUtils.getConnection(), sql, params);
    }
    //修改商品信息
    public void editProduct(Product p) throws SQLException {
        //创建集合并将商品信息添加到集合中
        List<Object> obj = new ArrayList<>();
        obj.add(p.getName());
        obj.add(p.getPrice());
        obj.add(p.getCategory());
        obj.add(p.getPnum());
        obj.add(p.getDescription());
        //创建QueryRunner
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        //创建sql语句
        String sql = "update products set name=?,price=?,category=?,pnum=?,description=?";
        //判断是否有图片
        if(p.getImgurl() != null && p.getImgurl().trim().length()>0){
            sql += " ,imgurl=?";
            obj.add(p.getImgurl());
        }
        sql += " where id = ?";
        obj.add(p.getId());

        runner.update(sql,obj.toArray());
    }
    //后台系统，根据id删除商品信息
    public void deleteProduct(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from products where id = ?";
        runner.update(sql,id);
    }
    //销售榜单
    public List<Object[]> salesList(String year, String month) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select products.name,sum(orderitem.buynum) " +
                " totalsalnum  " +
                " from orders,products,orderItem " +
                " where orders.id = orderitem.order_id " +
                " and products.id = orderitem.product_id " +
                " and orders.paystate=1 and year(ordertime)=? " +
                " and month(ordertime) = ? " +
                " group by products.name order by totalsalnum desc";

        return runner.query(sql,new ArrayListHandler(),year,month);
    }
    //前台，获取本周热销商品
    public List<Object[]> getWeekHotProduct() throws SQLException {
        String sql = "select products.id,products.name, "+
                " products.imgurl,SUM(orderitem.buynum) totalsalnum "+
                " from orderitem,orders,products "+
                " WHERE orderitem.order_id = orders.id "+
                " AND products.id = orderitem.product_id "+
                " AND orders.paystate=1 "+
                " AND orders.ordertime > DATE_SUB(NOW(), INTERVAL 7 DAY) "+
                " GROUP BY products.id,products.name,products.imgurl "+
                " ORDER BY totalsalnum DESC "+
                " LIMIT 0,2 ";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql, new ArrayListHandler());
    }

}