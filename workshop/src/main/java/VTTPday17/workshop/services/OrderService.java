package VTTPday17.workshop.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTPday17.workshop.models.Order;
import VTTPday17.workshop.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    public String save(Order order){
        String orderId = UUID.randomUUID().toString().substring(0, 8);

        orderRepo.save(orderId, order); //simple K:V using value operation

        return orderId;

    }
    



    
}
