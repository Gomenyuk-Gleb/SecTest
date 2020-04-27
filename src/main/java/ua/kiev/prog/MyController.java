package ua.kiev.prog;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;


@Controller
public class MyController {

    @Autowired
    private Json json;

    @Autowired
    private ToJson toJson;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String index(Model model){
        User user = getCurrentUser();

        String login = user.getUsername();

        CustomUser customUser = toJson.findUser(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("admin", isAdmin(user));
        model.addAttribute("moder", isModer(user));
        model.addAttribute("email", customUser.getEmail());
        model.addAttribute("phone", customUser.getPhone());

        return "index";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone) {

        User user = getCurrentUser();
        String login = user.getUsername();
        toJson.updateUser(login, email, phone);
        json.toJson(toJson.getList());
        return "redirect:/";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         Model model) {

        String passHash = passwordEncoder.encode(password);

        if ( ! toJson.addUser(new CustomUser(login, passHash, UserRole.USER, email, phone))) {

            model.addAttribute("exists", true);
            model.addAttribute("login", login);
            return "register";
        }

        json.toJson(toJson.getList());
        return "redirect:/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(name = "toDelete[]", required = false) List<String> ids,
                         Model model) {
        toJson.delUser(ids);
        model.addAttribute("users", toJson.getAllUsers());

        return "admin";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // !!!
    public String admin(Model model) {

        model.addAttribute("users", toJson.getAllUsers());
        return "admin";
    }

    @RequestMapping("/moder")
    @PreAuthorize("hasRole('ROLE_MODER')") // !!!
    public String moder(Model model) {

        model.addAttribute("users", toJson.getAllUsers());
        return "moder";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    private User getCurrentUser() {
        return (User)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private boolean isAdmin(User user) {
        Collection<GrantedAuthority> roles = user.getAuthorities();

        for (GrantedAuthority auth : roles) {
            if ("ROLE_ADMIN".equals(auth.getAuthority()))
                return true;
        }
        return false;
    }

    private boolean isModer(User user) {
        Collection<GrantedAuthority> roles = user.getAuthorities();

        for (GrantedAuthority auth : roles) {
            if ("ROLE_MODER".equals(auth.getAuthority()))
                return true;
        }
        return false;
    }
}
