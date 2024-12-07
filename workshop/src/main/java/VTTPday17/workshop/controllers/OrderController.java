package VTTPday17.workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import VTTPday17.workshop.models.Order;
import VTTPday17.workshop.services.OrderService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

//how to use application
    //input personal details + groceries
    //save and copy orderId 39ae4d42 (your redis key)
    //check redis using get 39ae4d42


@Controller
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderSvc;

    @PutMapping(path="/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postOrder(
        @RequestBody String payload){ //@RequestBody binds the Request body (Json string) to the payload variable

        System.out.printf(">>> payload: %s\n", payload); //prints out the whole json string

        Order order = Order.toOrder(payload);
        String orderId = orderSvc.save(order);

        JsonObject resp = Json.createObjectBuilder()
                .add("orderId", orderId)
                .build();



        return ResponseEntity.ok(resp.toString()); // sends our orderId to chuk's code to display as alert
    }


}
