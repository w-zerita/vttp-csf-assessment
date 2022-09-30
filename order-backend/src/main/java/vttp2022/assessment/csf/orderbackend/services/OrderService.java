package vttp2022.assessment.csf.orderbackend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private PricingService priceSvc;

	@Autowired
	private OrderRepository orderRepo;

	// POST /api/order
	// Create a new order by inserting into orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public void createOrder(Order order) {
		orderRepo.insertNewOrder(order);
	}

	// GET /api/order/<email>/all
	// Get a list of orders for email from orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public List<OrderSummary> getOrdersByEmail(String email) {
		List<Order> orders = orderRepo.getOrdersByEmail(email);
		List<OrderSummary> orderSummaries = new ArrayList<>();

		for (Order o: orders) {
			OrderSummary os = new OrderSummary();
			os.setOrderId(o.getOrderId());
			os.setName(o.getName());
			os.setEmail(o.getEmail());

			// Use priceSvc to calculate the total cost of an order
			Float amount = 0f;
			amount += priceSvc.size(o.getSize());
			amount += priceSvc.sauce(o.getSauce());
			if (o.isThickCrust() == true) {
				amount += priceSvc.thickCrust();
			} else {
				amount += priceSvc.thinCrust();
			}
			for (String t: o.getToppings())
				amount += priceSvc.topping(t);
			
			os.setAmount(amount);
			orderSummaries.add(os);
		}
		return orderSummaries;
	}
}
