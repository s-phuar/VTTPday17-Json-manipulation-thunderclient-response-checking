package VTTPday17.workshop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import VTTPday17.workshop.models.Order;
import jakarta.json.JsonObject;



@Repository
public class OrderRepository {

    @Autowired @Qualifier("redis-object")
    private RedisTemplate<String, Object> template;

    //redis-cli
    //set orderId "{....}"
    //use get orderId in cli to check
    public void save(String orderId, Order order){
        JsonObject json = order.toJson(); //convert order to Json

        ValueOperations<String, Object> valueOps = template.opsForValue();
        valueOps.set(orderId, json.toString());
    }

    //Value Operations
        //SET <key> <value> / GET <key>
        //Suitable for simple use cases where you want to store and retrieve a single value associated with a key.
        //It treats the entire value as a "single string"
            //(e.g., storing an entire object as a serialized JSON string or a primitive value):
                //valueOps.set(orderId, orderJsonString);
    
    //Hash Operations
        //HSET <key> <field> <value> / HGET <key> <field>
        //Ideal for more complex use cases where the data can be broken down into multiple attributes (fields) that need to be stored under a single key.
        //Each field is stored as a separate value and can be accessed, modified, or deleted independently.
            //(e.g., Storing individual fields of an Order):
            //// Storing fields as separate hash fields under the same orderId
                //hashOps.put(orderId, "orderDate", order.getOrderDate());
                //hashOps.put(orderId, "amount", order.getAmount());

    
}
