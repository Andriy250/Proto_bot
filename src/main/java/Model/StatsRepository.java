package Model;

import java.util.*;
import java.util.stream.Collectors;

public class StatsRepository {

    /**
     *  map of all users nicknames in the chat
     *  and amount of being test for each one
     */
    private Map<String, Integer> users;

    public StatsRepository() {
        users = new LinkedHashMap<>();
    }

    public void add(String user) {
        if (!users.containsKey(user))
            users.put(user, 0);
    }

    public void increment(String user) {
        users.replace(user, users.get(user) + 1);
    }

    public Map<String, Integer> getUsers() {
        return users;
    }

    private void sort() {
        users = users.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(
                        Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));
    }

    @Override
    public String toString() {
        sort();
        String output = "";
        output += "Рейтинг петухів:\n";
        for (String key : users.keySet())
            output += key + " - " + users.get(key) + "\n";
        return  output;
    }
}
