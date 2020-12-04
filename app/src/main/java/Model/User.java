package Model;

public class User {
    private String email_id;
    private String user_name;
    private String full_name;
    private String imageurl;

    public User(String id, String username, String fullname, String imageurl, String bio) {
        this.email_id = id;
        this.user_name = user_name;
        this.full_name = full_name;
        this.imageurl = imageurl;
    }

    public User() {
    }

    public String getId() {
        return email_id;
    }

    public void setId(String id) {
        this.email_id = id;
    }

    public  String getUsername() {
        return user_name;
    }

    public void setUsername(String username) {
        this.user_name = username;
    }

    public String getFullname() {
        return full_name;
    }

    public void setFullname(String fullname) {
        this.full_name = fullname;
    }

    public  String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }


}
