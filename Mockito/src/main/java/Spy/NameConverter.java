package Spy;

public class NameConverter {

    /** this method returns the string last name + first name from the passed parameters **/
    public String getFullName(String name,
                              String surname){
        if (surname != null && !surname.isBlank() && name != null && !name.isBlank()){
            return surname + " " + name;
        }
        if (surname != null && !surname.isBlank()){
            return surname;
        }
        if (name != null && !name.isBlank()){
            return name;
        }
        throw new RuntimeException("No value entered");
    }

    /** this method uses the logic of the first method **/
    public String getFullNameByName(String name){
        return getFullName(name, null);
    }

    /** this method uses the logic of the first method **/
    public String getFullNameBySurname(String surname){
        return getFullName(null, surname);
    }
}