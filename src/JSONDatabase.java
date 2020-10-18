import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;

public class JSONDatabase implements Database {
    //id - person
    private final Map<Integer, Person> persons;
    //phone - person
    private final Map<Long, Person> personsByPhone;
    private final File file;

    public JSONDatabase(String filepath){
        persons = new HashMap<>();
        file = new File(filepath);
        personsByPhone = new HashMap<>();
    }

    //adds new person to database. Its id must be unique
    @Override
    public void add(Person person) {
        persons.put(person.id, person);
        List<Long> unUniquePhones = new ArrayList<>();
        for (long phone : person.phone) {
            if (personsByPhone.containsKey(phone)) {
                System.err.println("Phone " + phone + " of user " + person.id + " " + person.name + " is not unique\n"
                        + "It will be removed");
                unUniquePhones.add(phone);
            } else {
                personsByPhone.put(phone, person);
            }
        }
        person.phone.removeAll(unUniquePhones);
    }

    @Override
    public void addPhone(long phone, Person person){
        personsByPhone.put(phone, person);
    }

    @Override
    public void remove(Person person) {
        persons.remove(person.id);
        for (long phone : person.phone) {
            personsByPhone.remove(phone);
        }
    }

    @Override
    public Person findByPhone(long phone){
        return personsByPhone.get(phone);
    }

    //save changes to file. should be called before the program ends
    @SuppressWarnings("unchecked") //JSON library does not support generics
    public void writeToFile(){
        JSONArray json = new JSONArray();
        json.add(Person.getNextId());
        for(Map.Entry<Integer, Person> i : persons.entrySet()){
            json.add(JSONTools.personToJson(i.getValue()));
        }
        try(FileWriter fileWriter = new FileWriter(file)){
            json.writeJSONString(fileWriter);
        } catch(IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void clear(){
        persons.clear();
        personsByPhone.clear();
    }

    //overwrite database with the file data. it makes empty database if file does not exist
    //but fails if file format is wrong
    public void readFromFile() {
        clear();
        if (file.exists()) {
            try(InputStream is = new FileInputStream(file)) {
                Object o = new JSONParser().parse(new String(is.readAllBytes()));
                JSONArray json = (JSONArray) o;
                @SuppressWarnings("rawtypes") Iterator it = json.iterator();  //JSON library does not support generics
                Person.setNextId(Integer.parseInt(it.next().toString()));
                while (it.hasNext()) {
                    Object p = it.next();
                    add(JSONTools.JSONToPerson((JSONObject) p));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //returns all persons
    @Override
    public Collection<Person> getPersons(){
        return persons.values();
    }

    @Override
    public Person findById(int id){
        return persons.get(id);
    }
}
