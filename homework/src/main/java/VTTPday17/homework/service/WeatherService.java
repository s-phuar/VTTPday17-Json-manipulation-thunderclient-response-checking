package VTTPday17.homework.service;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import VTTPday17.homework.model.Weather;
import VTTPday17.homework.repository.WeatherRepository;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

@Service
public class WeatherService {
    @Autowired
    WeatherRepository weatherRepo;

    public static final String GET_URL = "https://api.openweathermap.org/data/2.5/weather";
    public static final String IMAGE_URL = "https://openweathermap.org/img/wn/";

    
    @Value("${weather.api-key}")
    private String apiKey;

    //returns json string of original response (missing image url)
    public String search(String cityName){

        System.out.println("SEARCHING......\n\n\n");
        String url = UriComponentsBuilder
            .fromUriString(GET_URL)
            .queryParam("q", cityName)
            .queryParam("appid", apiKey)
            .toUriString();

        //construct request to weather website
        RequestEntity<Void> req = RequestEntity
            .get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        try{
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = template.exchange(req,String.class);
            String payload = resp.getBody();
            return payload;

        }catch(HttpClientErrorException | HttpServerErrorException ex){ //catches the 404 that 'fake country' sends back
            System.out.println("something wrong");
            System.out.println(url);
            return ex.getResponseBodyAsString(); //returns the responsebody of error json, so it can be used in validation method
        }
    }

    //error json pulled from API
    public boolean validation(String payload){
        System.out.println("payload:" + payload + "\n\n\n\n" );
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = reader.readObject(); 
        // System.out.println(jsonObj.toString());

        if(jsonObj.containsKey("message")){
            String errorMSG = jsonObj.getString("message");
            if(errorMSG.equals("city not found")){
                return false;
            }
        }

        return true;
    }
    

    //json String pulled from API, set into weather (missing image url)
    public static Weather apiToWeather(String payload, Weather weather){
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = reader.readObject();

        weather.setWeatherDescription(jsonObj
                .getJsonArray("weather")
                .getJsonObject(0)
                .getString("description"));

        weather.setKelvinTemp((float)jsonObj
                .getJsonObject("main")
                .getInt("temp"));

        weather.setMetricTemp(Weather.kelvinToMetric(weather.getKelvinTemp()));
        weather.setImperialTemp(Weather.kelvinToImperial(weather.getKelvinTemp()));
        weather.setIcon(jsonObj
                .getJsonArray("weather")
                .getJsonObject(0)
                .getString("icon"));   

        return weather;                
    }


    //adds image url to weather object
    public Weather getImageUrl(Weather weather){
        String url = UriComponentsBuilder
            .fromUriString(IMAGE_URL + weather.getIcon() + "@2x.png")
            .toUriString();

        weather.setImageUrl(url);
        return weather;
    }


    public void save(String cityName, Weather weather){
        JsonObject json = weather.toJson();
        weatherRepo.save(cityName, json.toString());
    }

    public Weather get(String cityName){
        return Weather.redisToWeather(weatherRepo.get(cityName));
    }






}
