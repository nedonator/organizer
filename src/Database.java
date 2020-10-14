import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;

public class Database {
    private final Map<Integer, Person> persons;
    private final Map<Integer, Person> personsByPhone;
    private final File file;

    public Database(String filepath){
        persons = new HashMap<>();
        file = new File(filepath);
        personsByPhone = new HashMap<>();

    }

    public boolean add(Person person){
        boolean result = persons.put(person.id, person) == null;
        if(result){
            List<Integer> unUniquePhones = new ArrayList<>();
            for(int phone : person.phone){
                if(personsByPhone.containsKey(phone)){
                    System.err.println("Phone " + phone +" of user " + person.id + " " + person.name + " is not unique\n"
                    + "It will be removed");
                    unUniquePhones.add(phone);
                }
                else{
                    personsByPhone.put(phone, person);
                }
            }
            person.phone.removeAll(unUniquePhones);
        }
        return result;
    }

    public boolean remove(Person person){
        boolean result = persons.remove(person.id) != null;
        if(result){
            for(int phone : person.phone){
                personsByPhone.remove(phone);
            }
        }
        return result;
    }

    public Person findByPhone(int phone){
        return personsByPhone.get(phone);
    }

    public boolean update(Person person){
        boolean result = remove(person);
        if(result){
            add(person);
        }
        return result;
    }

    public void writeToFile(){
        JSONArray json = new JSONArray();
        json.add(Person.getNextId());
        for(Map.Entry<Integer, Person> i : persons.entrySet()){
            json.add(JSONTools.personToJson(i.getValue()));
        }
        try{
            OutputStream os = new FileOutputStream(file);
            os.write(json.toString().getBytes());
        } catch(IOException e){
            throw new RuntimeException(e);
        }

    }

    public void clear(){
        persons.clear();
        personsByPhone.clear();
    }

    public void readFromFile(){
        clear();
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            else {
                InputStream is = new FileInputStream(file);
                Object o = new JSONParser().parse(new String(is.readAllBytes()));
                JSONArray json = (JSONArray) o;
                Iterator it = json.iterator();
                Person.setNextId(Integer.parseInt(it.next().toString()));
                while (it.hasNext()) {
                    Object p = it.next();
                    add(JSONTools.JSONToPerson((JSONObject) p));
                }
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public Collection<Person> read(){
        return persons.values();
    }

    public Person findById(int id){
        return persons.get(id);
    }
}
