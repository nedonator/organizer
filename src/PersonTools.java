public class PersonTools {

    //parses splitted string formatted as "insert (optional)<name> (optional)<email>" +
    //"(optional)<position> (optional)<organization>" +
    //"(optional)<phone 1> (optional)<phone 2> ...\n"
    public static Person formFromCommand(String[] input){
        Person p = new Person();
        for(int i = 5; i < input.length; i++){
            Long phone = parsePhoneNumber(input[i]);
            if(phone != null){
                p.phone.add(phone);
            } else{
                System.out.println("Phone number is incorrect");
            }
        }
        if(input.length > 4){
            p.organization = input[4];
        }
        if(input.length > 3){
            p.position = input[3];
        }
        if(input.length > 2){
            p.email = input[2];
        }
        if(input.length > 1){
            p.name = input[1];
        }
        return p;
    }

    public static Long parsePhoneNumber(String s){
        if(s.isEmpty()) return null;
        if(s.charAt(0) == '+') {
            s = s.substring(1);
        }
        if(s.charAt(0) == '-'){
            return null;
        }
        if(s.length() != 11) {
            return null;
        }
        try {
            return Long.parseLong(s);
        } catch (Exception e){
            return null;
        }
    }

    public static boolean update(Person p, String field, String value){
        switch(field) {
            case "id":
                System.out.println("id is immutable");
                return false;
            case "name":
                p.name = value;
                return true;
            case "position":
                p.position = value;
                return true;
            case "organization":
                p.organization = value;
                return true;
            case "email":
                p.email = value;
                return true;
            case "phone":
                Long phone = parsePhoneNumber(value);
                if (phone != null) {
                    p.phone.add(phone);
                    return true;
                } else {
                    System.out.println("Phone number is incorrect");
                    return false;
                }
            default:
                System.out.println("No such field: " + field);
                return false;
        }
    }

    public static Person findPersonFromCommand(Database database, String[] args){
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e){
            System.out.println("Id format is incorrect");
            return null;
        }
        Person p = database.findById(id);
        if(p == null){
            System.out.println("Person with id " + id + " is missing");
        }
        return p;
    }
}
