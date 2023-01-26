package Response.Serializers;

import Response.EventIDResponse;
import com.google.gson.*;

import java.lang.reflect.Type;


public class EventSerializer implements JsonSerializer<EventIDResponse> {

  @Override
  public JsonElement serialize(EventIDResponse eventIDResponse, Type type, JsonSerializationContext jsonSerializationContext) {
    Gson gson=new Gson();
    JsonObject obj = gson.toJsonTree(eventIDResponse).getAsJsonObject();
    if(eventIDResponse.getLatitude() == 0.0 && eventIDResponse.getLongitude() == 0.0 && eventIDResponse.getYear() == 0) {
      obj.remove("year");
      obj.remove("latitude");
      obj.remove("longitude");
    }
    return obj;
  }
}
