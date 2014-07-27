package thirdparty;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
public class PersistentDbReader implements DbReader {

    private static final Logger LOG = Logger.getAnonymousLogger();
    private static final File FILE = new File(System.getProperty("user.dir"), "news-feed-db.ser");
    private static final File README_FILE = new File(System.getProperty("user.dir"), "news-feed-db-readme.txt");

    private static final InMemoryDbReader inMemoryDbReader = new InMemoryDbReader();

    static {
        if (!FILE.exists()) {
            try {
                LOG.info("Creating db file: " + FILE);
                FILE.createNewFile();
                LOG.info("Creating readme file: " + README_FILE);
                README_FILE.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(README_FILE));
                bw.write("Users' news feed created by LearningTDD exercise. Safe to delete.");
                bw.close();
            } catch (IOException e) {
                LOG.severe("Could not create db file");
                throw new RuntimeException(e);
            }
        } else {
            LOG.info("loading from existing db file: " + FILE);
            try {
                FileInputStream fis = new FileInputStream(FILE);
                ObjectInputStream ois = new ObjectInputStream(fis);
                inMemoryDbReader.setData((Map<String, List<String[]>>) ois.readObject());
                ois.close();
                fis.close();
            } catch(Exception exception) {
                LOG.severe("Could not load db file");
                throw new RuntimeException(exception);
            }
        }
    }


    @Override
    public void put(String key, String... data) {
        inMemoryDbReader.put(key, data);
        save();
    }

    @Override
    public void delete(String key) {
        inMemoryDbReader.delete(key);
        save();
    }

    @Override
    public List<String[]> get(String key) {
        return inMemoryDbReader.get(key);
    }

    private void save() {
        try {
            FileOutputStream fos = new FileOutputStream(FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(inMemoryDbReader.getData());
            oos.close();
            fos.close();
        } catch(IOException exception) {
            LOG.severe("Could not save to db file");
            throw new RuntimeException(exception);
        }
    }

}
