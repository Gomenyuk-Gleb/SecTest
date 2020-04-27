package ua.kiev.prog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ToJson {

    public ToJson() {
    }
    @Autowired
    static Json jsonT;

    private static List<CustomUser> customUsers = jsonT.customUsers();

    public static List<CustomUser> getAllUsers() {
        return customUsers;
    }

    public CustomUser findUser(String s) {
        for (CustomUser customUser : customUsers)
            if (customUser.getLogin().equals(s))
                return customUser;
        return null;
    }

    public  boolean addUser(CustomUser customUserAdmin) {
        customUsers.add(customUserAdmin);
        return findUser(customUserAdmin.getLogin()) != null;
    }

    public static void delUser(List<String> names) {
        List<CustomUser> toDelete = new ArrayList<>();
        for (CustomUser user : customUsers) {
            for (String login : names)
                if (user.getLogin().equals(login) && !login.equals("admin")) {
                    toDelete.add(user);
                    break;
                }
        }
        customUsers.removeAll(toDelete);
    }

    public void updateUser(String login, String email, String phone) {
        CustomUser user = findUser(login);
        user.setEmail(email);
        user.setPhone(phone);
    }

    public List<CustomUser> getList() {
        return customUsers;
    }

}
