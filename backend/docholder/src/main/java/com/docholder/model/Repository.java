package com.docholder.model;

import java.util.Hashtable;
import java.util.Map;

public class Repository {
    // Клиенты
    static Map<Integer, Client> clients = new Hashtable<>();

    static {
        clients.put(1, new Client(1, "Игнат"));
        clients.put(2, new Client(2, "Christopher"));
        clients.put(3, new Client(3, "Егор"));
    }

    public Client clientById(Integer id) {
        return clients.get(id);
    }

    public Client addClient(Client cl) {
        clients.put(cl.getId(), cl);
        return cl;
    }

    public Client deleteClient(Integer id){
        return clients.remove(id);
    }
    // Предприятия
    // private List<Map<String, String>> companies = new ArrayList<Map<String, String>>() {{
    //     add(new HashMap<String, String>() {{ put("id", "1"); put("name", "Рога и Копыта"); }});
    //     add(new HashMap<String, String>() {{ put("id", "2"); put("name", "Пе'шков Limited"); }});
    //     add(new HashMap<String, String>() {{ put("id", "3"); put("name", "Stuff"); }});
    // }};
}
