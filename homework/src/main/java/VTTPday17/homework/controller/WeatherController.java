package VTTPday17.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import VTTPday17.homework.model.Weather;
import VTTPday17.homework.service.WeatherService;

@Controller
@RequestMapping
public class WeatherController {
    
    @Autowired
    WeatherService weatherSVC;

    @GetMapping("/")
    public String landingPage(Model model){
        Weather weather = new Weather();
        model.addAttribute("weather", weather);
        return "index";
    }


    @GetMapping("/weather")
    public String getWeatherData(
        @ModelAttribute("weather") Weather weather,@RequestParam MultiValueMap<String, String> form, Model model){
            String choice = form.getFirst("choice");
            String query = weather.getCityName();

            if(query.equals(weatherSVC.get(query).getCityName())){
                //grab from cache
                weather = weatherSVC.get(query);
                System.out.println("getting cached data\n\n\n");
            }else{
            //else, update weather object
            String payload = weatherSVC.search(query);
            if(!weatherSVC.validation(payload)){
                return "index";
            }
            weatherSVC.apiToWeather(payload, weather);//part 1 of setting params for weather object
            weatherSVC.getImageUrl(weather);//part 2
            weatherSVC.save(payload, weather);//save to redis
            System.out.println("saving to cache\n\n\n");
            System.out.println(weather);
            }

            model.addAttribute("weather", weather);
            model.addAttribute("choice", choice);

            return "weather";
    }







}
