import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
  public static String toJsonString(Object obj) {
    ObjectMapper objectMapper = new ObjectMapper();
    String json = "";
    try {
      json = objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }

  public static <T> T toObject(String s, Class<T> tClass) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(s, tClass);
  }
}
