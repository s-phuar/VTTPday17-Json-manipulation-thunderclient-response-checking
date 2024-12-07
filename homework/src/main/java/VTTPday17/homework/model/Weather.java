package VTTPday17.homework.model;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Weather {

    private String cityName;
    private String weatherDescription;
    private float kelvinTemp;
    private float metricTemp;
    private float imperialTemp;
    private String icon;
    private String imageUrl;
    
    public String getCityName() {return cityName;}
    public void setCityName(String cityName) {this.cityName = cityName;}
    public String getWeatherDescription() {return weatherDescription;}
    public void setWeatherDescription(String weatherDescription) {this.weatherDescription = weatherDescription;}
    public float getKelvinTemp() {return kelvinTemp;}
    public void setKelvinTemp(float kelvinTemp) {this.kelvinTemp = kelvinTemp;}
    public float getMetricTemp() {return metricTemp;}
    public void setMetricTemp(float metricTemp) {this.metricTemp = metricTemp;}
    public float getImperialTemp() {return imperialTemp;}
    public void setImperialTemp(float imperialTemp) {this.imperialTemp = imperialTemp;}
    public String getIcon() {return icon;}
    public void setIcon(String icon) {this.icon = icon;}
    public String getImageUrl() {return imageUrl;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    @Override
    public String toString() {
        return "Weather [cityName=" + cityName + ", weatherDescription=" + weatherDescription + ", kelvinTemp="
                + kelvinTemp + ", metricTemp=" + metricTemp + ", imperialTemp=" + imperialTemp + ", icon=" + icon
                + ", imageUrl=" + imageUrl + "]";
    }

    //convert Weather to custom Json object
    public JsonObject toJson(){
        JsonObject jsonObj = Json.createObjectBuilder()
                                .add("cityName", this.getCityName())
                                .add("weatherDescription", this.getWeatherDescription())
                                .add("kelvinTemp", this.getKelvinTemp())
                                .add("metricTemp", this.getMetricTemp())
                                .add("imperialTemp", this.getImperialTemp())
                                .add("icon", this.getIcon())
                                .add("imageUrl", this.getImageUrl())
                                .build();
        return jsonObj;
    }

    //json String pulled from redis, to obj, to weather object
    public static Weather redisToWeather(String json){

        if(json == null){
            return new Weather();
        }
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jsonObj = reader.readObject();


        Weather weather = new Weather();
        weather.setCityName(jsonObj.getString("cityName"));
        weather.setWeatherDescription(jsonObj.getString("cityName"));
        weather.setKelvinTemp((float)Integer.parseInt(jsonObj.getString("cityName")));
        weather.setMetricTemp((float)Integer.parseInt(jsonObj.getString("cityName")));
        weather.setImperialTemp((float)Integer.parseInt(jsonObj.getString("cityName")));
        weather.setIcon(jsonObj.getString("cityName"));
        weather.setImageUrl(jsonObj.getString("cityName"));
        
        return weather;
    }


    
    public static float kelvinToMetric(float kelvinTemp){
        return (float) (kelvinTemp - 273.15);
    }

    public static float kelvinToImperial(float kelvinTemp){
        float celsius = (float)(kelvinTemp - 273.15);
        float imperial = celsius * 9 / 5 + 32;
        return imperial;
    }

    

}
