import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;

public class JSONTests {
    String filepath = "." + File.separator + "JSONTestDatabase.json";
    JSONDatabase database = new JSONDatabase(filepath);

    @Test
    public void test(){
        File file = new File(filepath);
        boolean fileErased = !file.exists() || file.delete();
        assert(fileErased);
        Person p = new Person();
        String name = "vasya";
        String email = "inbox@tanki.ru";
        String position = "farmer";
        String organization = "horns_and_hooves_inc";
        long phone1 = 88005553535L;
        long phone2 = 81005001337L;
        p.name = name;
        p.email = email;
        p.organization = organization;
        p.position = position;
        p.phone.add(phone1);
        p.phone.add(phone2);
        database.add(p);
        database.readFromFile();
        assert(database.getPersons().size() == 0);
        database.add(p);
        database.writeToFile();
        database.readFromFile();
        Collection<Person> c = database.getPersons();
        assert(c.size() == 1);
        Person p1 = database.findById(p.id);
        assert(p1 != null);
        assert(p1.id == p.id);
        assert(p.position.equals(p1.position) && p.organization.equals(p1.organization) &&
                p.name.equals(p1.name) && p.email.equals(p1.email));
        assert(p.phone.equals(p1.phone));
    }
}
