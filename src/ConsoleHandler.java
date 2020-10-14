import java.util.ArrayList;
import java.util.List;

public class ConsoleHandler {
    private final Database database;

    private static final String guide = "Commands:\n" +
            "insert (optional)<name> (optional)<email> (optional)<position> (optional)<organization> " +
            "(optional)<phone 1> (optional)<phone 2> ...\n" +
            "delete <id>\n" +
            "find <id>\n" +
            "find phone <phone>\n" +
            "list (optional)<field 1> (optional)<field 2> ...\n" +
            "help\n" +
            "exit";

    public ConsoleHandler(Database database) {
        this.database = database;
    }

    public void handle(String command){
        String[] args = command.split(" ");
        if(args.length == 0)return;
        switch(args[0]){
            case "exit": App.terminate(); break;
            case "help":
                System.out.println(guide);break;
            case "insert":
                Person p = PersonTools.formFromCommand(args);
                if(database.add(p)) {
                    System.out.println("New person is inserted");
                    System.out.println(p);
                }
                else{
                    System.out.println("Person is already exist");
                }
                break;
            case "update":
                if(args.length != 4){
                    System.out.println("Update call is incorrect. See >help");
                    break;
                }
                p = PersonTools.findPersonFromCommand(database, args);
                if(p == null)break;
                if(PersonTools.update(p, args[2], args[3])) {
                    database.update(p);
                    System.out.println("Person is updated");
                }
                System.out.println(p);
                break;
            case "delete":
                if(args.length != 2){
                    System.out.println("Delete call is incorrect. See >help");
                    break;
                }
                p = PersonTools.findPersonFromCommand(database, args);
                if(p == null)break;
                database.remove(p);
                System.out.println("Person is deleted");
                System.out.println(p);
                break;
            case "list":
                List<Person> list = new ArrayList<>(database.read());
                for(int i = 1; i < args.length; i++){
                    PersonSorter.sort(list, args[i]);
                }
                System.out.println(list);
                break;
            case "find":
                if(args.length == 3){
                    if(!args[1].equals("phone")){
                        System.out.println("Find call is incorrect. See >help");
                        break;
                    }
                    Integer phone = PersonTools.parsePhoneNumber(args[2]);
                    if(phone == null)break;
                    p = database.findByPhone(phone);
                    System.out.println(p);
                }
                else {
                    if (args.length != 2) {
                        System.out.println("Find call is incorrect. See >help");
                        break;
                    }
                    p = PersonTools.findPersonFromCommand(database, args);
                    if (p != null) {
                        System.out.println(p);
                    }
                }
                break;
            default:
                System.out.println("Missing command: " + args[0]);
        }
    }
}
