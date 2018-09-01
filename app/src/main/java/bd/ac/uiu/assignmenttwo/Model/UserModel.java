package bd.ac.uiu.assignmenttwo.Model;

public class UserModel {
    private String Name;
    private String Email;
    private String Password;
    private String Phone;
    private String Gender;
    private String City;
    private String ID;

    public UserModel(String name, String email, String password, String phone, String gender, String city, String ID) {
        Name = name;
        Email = email;
        Password = password;
        Phone = phone;
        Gender = gender;
        City = city;
        this.ID = ID;
    }

    public UserModel(String name, String email, String password, String phone, String gender, String city) {
        Name = name;
        Email = email;
        Password = password;
        Phone = phone;
        Gender = gender;
        City = city;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
