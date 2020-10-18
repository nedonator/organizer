import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;

public class ConsoleTests {
    Database database = new JSONDatabase("." + File.separator + "testDatabase.json");
    ConsoleHandler consoleHandler = new ConsoleHandler(database);

    Method lastCalledMethod = null;
    Object[] lastCalledMethodArgs = null;
    class DullDatabase implements InvocationHandler{
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            lastCalledMethod = method;
            lastCalledMethodArgs = args;
            //noinspection SuspiciousInvocationHandlerImplementation
            return null;
        }
    }

    @Test
    public void insertTest(){
        database.clear();
        String name = "vasya";
        String email = "inbox@tanki.ru";
        String position = "farmer";
        String organization = "horns_and_hooves_inc";
        long phone1 = 88005553535L;
        long phone2 = 81005001337L;
        String queue = "insert " + name + " " + email + " " + position +
                " " + organization + " " + phone1 + " " + phone2;
        consoleHandler.handle(queue);
        Collection<Person> c = database.getPersons();
        assert(c.size() == 1);
        Person p = c.iterator().next();
        assert(name.equals(p.name) && email.equals(p.email) &&
                position.equals(p.position) && organization.equals(p.organization));
        assert(p.phone.size() == 2);
        assert(p.phone.contains(phone1));
        assert(p.phone.contains(phone2));
    }

    @Test
    public void deleteTest(){
        database.clear();
        Person p = new Person();
        database.add(p);
        String queue = "delete " + (p.id + 1);
        consoleHandler.handle(queue);
        assert(database.getPersons().size() == 1);
        queue = "delete " + p.id;
        consoleHandler.handle(queue);
        assert(database.getPersons().size() == 0);
    }

    @Test
    public void containsTest() throws NoSuchMethodException{
        Database database1 = (Database) Proxy.newProxyInstance(Database.class.getClassLoader(),
                new Class[]{Database.class},
                new DullDatabase());
        ConsoleHandler consoleHandler1 = new ConsoleHandler(database1);
        String queue = "find 42";
        consoleHandler1.handle(queue);
        assert(lastCalledMethod.equals(Database.class.getMethod("findById", int.class)));
        assert(lastCalledMethodArgs.length == 1);
        assert(lastCalledMethodArgs[0].equals(42));

        long phone = 88005553535L;
        queue = "find phone " + phone;
        consoleHandler1.handle(queue);
        assert(lastCalledMethod.equals(Database.class.getMethod("findByPhone", long.class)));
        assert(lastCalledMethodArgs.length == 1);
        assert(lastCalledMethodArgs[0].equals(phone));
    }

    @Test
    public void updateTest(){
        database.clear();
        Person p = new Person();
        database.add(p);
        String name = "vasya";
        String query = "update " + p.id + " name " + name;
        consoleHandler.handle(query);
        assert(p.name.equals(name));

        long phone = 88005553535L;
        query = "update " + p.id + " phone " + phone;
        consoleHandler.handle(query);
        assert(p.phone.size() == 1);
        assert(p.phone.get(0) == phone);
    }
}
