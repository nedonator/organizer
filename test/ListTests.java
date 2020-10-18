import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListTests {
    Database database = new JSONDatabase("." + File.separator + "testDatabase.json");

    @Test
    public void test() {
        database.clear();
        List<Person> list = new ArrayList<>();
        int len = 10;
        for(int i = 0; i < len; i++){
            Person p = new Person();
            p.name = "" + (char)('a' + i);
            p.email = "" + (char)('z' - i);
            list.add(p);
        }
        PersonSorter.sort(list, "name");
        for(int i = 0; i < len; i++){
            assert(list.get(i).name.equals("" + (char)('a' + i)));
        }

        PersonSorter.sort(list, "email");
        for(int i = 0; i < len; i++){
            assert(list.get(i).name.equals("" + (char)('a' + len - 1 - i)));
        }
    }
}
