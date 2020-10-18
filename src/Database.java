import java.util.Collection;

public interface Database {
    void add(Person person);
    void addPhone(long phone, Person person);
    void remove(Person person);
    Person findByPhone(long phone);
    void clear();
    Collection<Person> getPersons();
    Person findById(int id);
}
