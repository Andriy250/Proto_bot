package Model;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class IdRepository {
    private Set<Long> idset = new HashSet<>();
    private int capacity;
    private String path;


    public IdRepository(String path) {
        this.path = path;
    }

    public void fullfillFromFile() throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(path));
        String line = input.readLine();
        while(line != null) {
            idset.add(Long.parseLong(line));
            line = input.readLine();
        }
        input.close();
    }

    public void saveIdToFile(long id) throws IOException {
        idset.add(id);

        FileWriter output = new FileWriter(path);
        output.write(id +"\n");
        output.close();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Long> getIdset() {
        return idset;
    }
}

//think about removing