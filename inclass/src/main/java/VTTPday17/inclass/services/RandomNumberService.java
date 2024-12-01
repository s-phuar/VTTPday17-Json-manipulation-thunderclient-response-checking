package VTTPday17.inclass.services;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class RandomNumberService {
    
    private Random rnd = new SecureRandom();


    public int getRandom(){
        return this.getRandom(100);
    }


    public int getRandom(int bound){
        return rnd.nextInt(bound);
    }




}
