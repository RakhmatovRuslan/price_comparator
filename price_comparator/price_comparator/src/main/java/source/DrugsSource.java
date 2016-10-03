package source;

import entity.Supplier;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by ruslan on 5/14/16.
 */
public class DrugsSource {

    private ObjectMapper objectMapper;
    private Supplier supplier;
    private File file;

    public DrugsSource(String fileName) {
        file = new File(fileName);
//        file = new File(this.getClass().getClassLoader().getResource("drugs.json").getPath());
        objectMapper = new ObjectMapper();
    }

    private void loadJson() {
        try {
            this.supplier = objectMapper.readValue(file, Supplier.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Supplier getSupplier() {
        if (this.supplier == null)
            loadJson();
        return this.supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void saveToJson() throws IOException {
        if (this.supplier != null) {
            objectMapper.writeValue(file, this.supplier);
        } else {
            throw new NullPointerException("Supplier object can not be null, please set its value");
        }
    }

    public void saveToJson(Supplier supplier) throws IOException {
        if (supplier != null) {
            this.supplier = supplier;
            saveToJson();
        }else{
            throw new IllegalArgumentException("The list of drugs can not be null");
        }
    }
    public void addToJson(Supplier supplier) throws IOException {
        if (supplier != null) {
            getSupplier();
            if(this.supplier == null){
                this.supplier = new Supplier();
            }
            this.supplier.addAll(supplier);
            saveToJson();
        }else{
            throw new IllegalArgumentException("The list of drugs can not be null");
        }
    }
}
