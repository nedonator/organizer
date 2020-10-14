import java.util.ArrayList;
import java.util.List;

public class Person {
    private static int freeId = 0;

    public final int id;
    public String name;
    public String position;
    public String organization;
    public String email;
    public final List<Integer> phone;

    Person(int id){
        this.id = id;
        phone = new ArrayList<>();
    }

    public Person(){
        this(freeId++);
        if(freeId == 0)
            throw new RuntimeException("Out of unique ids");
        name = "";
        organization = "";
        position = "";
        email = "";
    }

    public Person(String name, String position, String organization, String email) {
        this();
        this.name = name;
        this.position = position;
        this.organization = organization;
        this.email = email;
    }

    public Person(String name, String position, String organization, String email, int phone) {
        this(name, position, organization, email);
        this.phone.add(phone);
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
