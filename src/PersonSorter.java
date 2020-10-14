import java.util.Comparator;
import java.util.List;

public class PersonSorter {
    public static void sort(List<Person> persons, String parameter){
        Comparator<Person> cmp = null;
        switch(parameter){
            case "id" : cmp = Comparator.comparingInt(a->a.id);break;
            case "name" : cmp = Comparator.comparing(a -> a.name);break;
            case "position" : cmp = Comparator.comparing(a -> a.position);break;
            case "organization" : cmp = Comparator.comparing(a -> a.organization);break;
            case "email" : cmp = Comparator.comparing(a -> a.email);break;
            default:
                System.out.println("No such field: " + parameter);
        }
        if(cmp != null) {
            persons.sort(cmp);
        }
    }
}
