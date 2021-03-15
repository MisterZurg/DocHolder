package com.docholder.model;

import java.util.Hashtable;
import java.util.Map;

public class Repository {
    // Клиенты
    static Map<Integer, User> users = new Hashtable<>();

    static {
        users.put(1, new User(1, "Игнат"));
        users.put(2, new User(2, "Christopher"));
        users.put(3, new User(3, "Егор"));
        users.put(4, new User(4 ,"Гибон"));
    }

    public User userById(Integer id) {
        return users.get(id);
    }

    public User addUser(User usr) {
        users.put(usr.getId(), usr);
        return usr;
    }

    public User deleteUser(Integer id){
        return users.remove(id);
    }
    // Предприятия
    // private List<Map<String, String>> companies = new ArrayList<Map<String, String>>() {{
    //     add(new HashMap<String, String>() {{ put("id", "1"); put("name", "Рога и Копыта"); }});
    //     add(new HashMap<String, String>() {{ put("id", "2"); put("name", "Пе'шков Limited"); }});
    //     add(new HashMap<String, String>() {{ put("id", "3"); put("name", "Stuff"); }});
    // }};
}
