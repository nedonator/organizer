import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;

public class DBTests {
    JSONDatabase database = new JSONDatabase("." + File.separator + "testDatabase.json");

    @Test
    public void addTest(){
        database.clear();
        Person p = new Person();
        database.add(p);
        Collection<Person> c = database.getPersons();
        assert(c.size() == 1);
        assert(c.contains(p));
    }

    @Test
    public void clearTest(){
        database.clear();
        Person p = new Person();
        database.add(p);
        database.clear();
        Collection<Person> c = database.getPersons();
        assert(c.size() == 0);
    }

    @Test
    public void removeTest(){
        database.clear();
        Person p = new Person();
        database.add(p);
        database.remove(p);
        Collection<Person> c = database.getPersons();
        assert(c.size() == 0);
    }

    @Test
    public void containsTest(){
        database.clear();
        Person p = new Person();
        assert(database.findById(p.id) == null);
        database.add(p);
        assert(database.findById(p.id) == p);
        database.remove(p);
        assert(database.findById(p.id) == null);
    }

    @Test
    public void findByPhoneTest(){
        database.clear();
        Person p = new Person();
        long phone = 88005553535L;
        p.phone.add(phone);
        assert(database.findByPhone(phone) == null);
        database.add(p);
        assert(database.findByPhone(phone) == p);
        database.remove(p);
        assert(database.findByPhone(phone) == null);
    }
}
