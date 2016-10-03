package service;

import java.io.*;
import java.util.Properties;

/**
 * Created by RUSLAN on 18.06.2016.
 */
public class ConfigParamService {

    private Properties properties;

    public ConfigParamService() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
        try {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void saveProperties(Properties properties) throws IOException {
        this.properties = properties;
        properties.store(new FileOutputStream(new File("config.properties")),"Updating properties file!");
    }
}
