package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class Json {

    private static final String JSON = "Json.gson";
    private static String gsonSt;
    private static List<CustomUser> customUsers = new ArrayList<>();

    public  void toJson(List<CustomUser> customUser) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
         gsonSt = gson.toJson(customUser);
        try (PrintWriter pw = new PrintWriter(JSON)) {
            pw.println(gsonSt);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public List<CustomUser> Fromjson() {
        Gson gson = new Gson();
        customUsers = gson.fromJson(gsonSt, new TypeToken<ArrayList<CustomUser>>(){}.getType());
        return customUsers;
    }

    public static List<CustomUser> customUsers(){
        return customUsers;
    }
}
