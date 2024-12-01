package VTTPday17.inclass.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;

import VTTPday17.inclass.services.RandomNumberService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.security.SecureRandom;
import java.util.Random;


//use thunderclient
@Controller
@RequestMapping(path="/random")
public class RandomNumberController {

    @Autowired
    private RandomNumberService randomSvc;

    //standard MVC
    // GET /random
    // Accept: text:html

    //uncomment to test this block
            // @GetMapping
            // public ModelAndView getRandom(){

            //     ModelAndView mav = new ModelAndView("random-number");
            //     mav.addObject("rndNum", randomSvc.getRandom());
                
            //     return mav;
            // }

    // Get /random
    // we Accept: application/json
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody //do NOT convert into html
    public ResponseEntity<String> getRandomAsJson(
        @RequestParam(defaultValue = "100") int bounds){



        JsonObject result = Json.createObjectBuilder()
                .add("rndNum", randomSvc.getRandom(bounds))
                .build();

            return ResponseEntity.ok(result.toString());

    }
    
}
