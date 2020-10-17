import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONTools {
    public static Person JSONToPerson(JSONObject json){
        Person person = new Person(Integer.parseInt(json.get("id").toString()));
        person.name = json.get("name").toString();
        person.position = json.get("position").toString();
        person.organization = json.get("organization").toString();
        person.email = json.get("email").toString();
        JSONArray phones = (JSONArray) json.get("phone");
        for(Object o : phones){
            person.phone.add(Long.parseLong(o.toString()));
        }
        return person;
    }

    @SuppressWarnings("unchecked") //JSON library does not support generics
    public static JSONObject personToJson(Person person){
        JSONObject json = new JSONObject();
        json.put("id", person.id);
        json.put("name", person.name);
        json.put("position", person.position);
        json.put("organization", person.organization);
        json.put("email", person.email);
        json.put("phone", person.phone);
        return json;
    }
}
