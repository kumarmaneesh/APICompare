import java.util.Objects;

/**
 * Created by 1556780 on 8/24/2018.
 */
public class User {

    private int id;
    private String first_name;
    private String last_name;
    private String avatar;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(first_name, user.first_name) &&
                Objects.equals(last_name, user.last_name) &&
                Objects.equals(avatar, user.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, avatar);
    }
}
