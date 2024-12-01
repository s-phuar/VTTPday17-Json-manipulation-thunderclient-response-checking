package VTTPday17.inclass.controllers;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;


//how to use thundclient:
    //new request
    //set GET/POST request
    //use localhost:8080 as server
    //set params for testing different request params for different response code


@RestController //generic Controller can produce more than just html. RestController is returning the response not view name
@RequestMapping
public class TimeControllers {

    // POST /customer
    // client Content-type: applcation/x-www/form/urlencoded
    // client Accept: text/html
    @PostMapping(path="/customer1", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> postCustomerAsHTML(
        @RequestBody MultiValueMap<String, String> form){ //request body is a form, response expected to be string

        System.out.printf(">>> form: %s\n", form);

        String html = "<h1> %s has been registered </h1>".formatted(form.getFirst("name"));

        return ResponseEntity.ok(html); //200
    }

    // POST /customer
    // client Content-type (should match produces): application/json
    // client Accept (should match consumes): application/json
    @PostMapping(path="/customer2", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCustomer(
        @RequestBody String payload){ //@RequestBody binds the HTTP request body(a String) to the payload variable

            System.out.printf(">>> PAYLOAD: %s\n", payload);

            // 202 Accepted
            // Content-Type: application/json
            // \r\n
            // {}
            return ResponseEntity
                    .accepted() //202
                    .header("X-MyHeader", "abc123") //custom header will appear in response
                    .body("{test}"); //http request body
        }
    

    // GET /time
    // produces = text/plain
    @GetMapping(path="/time1", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getTimeAsText(){

        String time = (new Date()).toString();
        return ResponseEntity.ok(time);         // Tue Nov 26 13:22:38 GMT+08:00 2024
    }

    // GET /time
    // client Accept: application/json, the below sets Content-Type of the 'response' to application/json
    // @GetMapping(path="/time", produces = "application/json")  //Note that this sets content-type of response to application/json
    @GetMapping(path="/time2", produces = MediaType.APPLICATION_JSON_VALUE) //REST is super sensitive to the content-type.
    public ResponseEntity<String> getTime(){

        /*
         * {"time": "a time"}
         * 
         */
        JsonObject resp = Json.createObjectBuilder()
                .add("time", (new Date()).toString())
                .build();

        // 200 OK
        // Content-Type: application/json
        // {"time": "a time"}

        //short forms
            // return ResponseEntity.ok(resp.toString()); 
            // return ResponseEntity.ok(new Time((new Date()).toString()));

        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON) //this line does the same as above, also sets the content-type of the response to application/json
                .body(resp.toString());

    }


}
