import java.util.ArrayList;
import java.util.List;

//all fields except id can be modified without creating new object. It means that all local changes
//are visible throughout the program
public class Person {
    private static int freeId = 0;

    public final int id;
    public String name;
    public String position;
    public String organization;
    public String email;
    public final List<Long> phone;

    public Person(int id){
        this.id = id;
        phone = new ArrayList<>();
        name = "";
        organization = "";
        position = "";
        email = "";
    }

    public Person(){
        this(freeId++);
        if(freeId == 0)
            throw new RuntimeException("Out of unique ids");
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Person))return false;
        Person p = (Person)obj;
        return id == p.id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", organization='" + organization + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }

    public static int getNextId(){
        return freeId;
    }

    public static void setNextId(int id){
        freeId = id;
    }
}
