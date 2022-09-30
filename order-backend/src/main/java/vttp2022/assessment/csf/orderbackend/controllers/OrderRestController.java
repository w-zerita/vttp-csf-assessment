package vttp2022.assessment.csf.orderbackend.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.services.OrderService;

@RestController
@RequestMapping(path = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    private Logger logger = Logger.getLogger(OrderRestController.class.getName());

    @Autowired
    private OrderService orderSvc;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postOrder(@RequestBody String payload) {

        logger.info("Payload: %s".formatted(payload));

        Order o;

        try {
            o = Order.create(payload);
            logger.info("Create order: %s".formatted(o.getEmail()));
            orderSvc.createOrder(o);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
        }

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(o.toJson().toString());
    }

    @GetMapping(path = "/{email}/all")
    public ResponseEntity<String> getListOfOrders(@PathVariable String email) {

        List<OrderSummary> orderSummary = orderSvc.getOrdersByEmail(email);
        JsonArrayBuilder osArr = Json.createArrayBuilder();
        for (OrderSummary os: orderSummary) {
            osArr.add(os.toJson());
        }
        JsonObject osList = Json.createObjectBuilder()
            .add("orderSummary", osArr)
            .build();
            return ResponseEntity.ok(osList.toString());
    }

}
