package vttp2022.assessment.csf.orderbackend.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;

@Repository
public class OrderRepository {

    private static final String SQL_INSERT_NEW_ORDER = 
        "insert into orders (name, email, pizza_size, thick_crust, sauce, toppings, comments) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_GET_ORDERS_BY_EMAIL = 
        "select * from orders where email = ?";

    @Autowired
    private JdbcTemplate template;

    public void insertNewOrder(Order order) {
        template.update(SQL_INSERT_NEW_ORDER,
            order.getName(), order.getEmail(), order.getSize(), 
            order.isThickCrust(), order.getSauce(), 
            String.join(",", order.getToppings()), order.getComments());

        System.out.printf(">>> Inserting %s to DB\n", order.toString());
    }

    public List<Order> getOrdersByEmail(String email) {
        List<Order> orders = new ArrayList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_GET_ORDERS_BY_EMAIL, email);
        while (rs.next()) {
            Order order = Order.convert(rs);
            orders.add(order);
        }
        return orders;
    }
    
}
