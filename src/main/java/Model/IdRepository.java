package Model;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class IdRepository {
    private Set<Long> idset = new HashSet<>();
    private int capacity;
    private String path;


    public IdRepository(String path) {
        this.path = path;
    }

    public void fullfillFromFile() throws IOException {
        /*InputStream is = getClass().getResourceAsStream(path);
        BufferedReader input = new BufferedReader(new InputStreamReader(is));*/
        BufferedReader input = new BufferedReader(new FileReader(path));
        String line = input.readLine();
        while(line != null) {
            idset.add(Long.parseLong(line));
            line = input.readLine();
        }
        input.close();
    }

    public void saveIdToFile(long id) throws IOException {

        if (idset.add(id)) {

            /*URL url = getClass().getResource(path);
            File file = new File(url.getFile());*/
            FileWriter output = new FileWriter(path);
            Locale.setDefault(Locale.forLanguageTag("uk-UA"));
            output.append(id + "\n");
            output.close();

        }
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