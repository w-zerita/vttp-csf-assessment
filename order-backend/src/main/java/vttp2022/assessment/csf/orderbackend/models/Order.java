package vttp2022.assessment.csf.orderbackend.models;

import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private Integer orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }

	public static Order create(String json){
		JsonReader r = Json.createReader(new StringReader(json));
		JsonObject o = r.readObject();

		final Order order = new Order();
		order.setOrderId(o.getInt("orderId"));
		order.setName(o.getString("name"));
		order.setEmail(o.getString("email"));
		order.setSize(o.getInt("size"));
		order.setSauce(o.getString("sauce"));
		order.setComments(o.getString("comments"));

		// crust: thick (true), thin (false)
		if (o.getString("base").equals("thick"))
			order.setThickCrust(true);
		else
			order.setThickCrust(false);

		// get toppings
		JsonArray toppingsArr = o.getJsonArray("toppings");
		List<String> toppings = toppingsArr.getValuesAs(JsonString::getString);
		order.setToppings(toppings);

		return order;
	}

	public static Order convert(SqlRowSet rs) {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
		order.setName(rs.getString("name"));
		order.setEmail(rs.getString("email"));
		order.setSize(rs.getInt("pizza_size"));
		order.setThickCrust(rs.getBoolean("thick_crust"));
		order.setSauce(rs.getString("sauce"));
		order.setComments(rs.getString("comments"));
		List<String> toppings = Arrays.asList(
			rs.getString("toppings").split(","));
		// order.setToppings(rs.getString("toppings"));
		order.setToppings(toppings);
		return order;
    }

	public JsonObject toJson() {
		return Json.createObjectBuilder()
			.add("orderId", orderId)
			.add("name", name)
			.add("email", email)
			.add("size", size)
			.add("sauce", sauce)
			// .add("toppings", toppings)
			.add("comments", comments)
			.build();
	}
}
