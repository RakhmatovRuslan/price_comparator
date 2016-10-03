import entity.Drug;
import entity.Supplier;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import source.StandartMedecinesSource;

import java.io.File;
import java.io.IOException;

/**
 * Created by ruslan on 5/14/16.
 */
public class JacksonTest {
    ObjectMapper objectMapper;
    Supplier drugs;
    Drug drug;
    @Before
    public void init(){
        objectMapper = new ObjectMapper();
        drugs = new Supplier();
        drugs.addAll(new StandartMedecinesSource().getData());
        drugs.setSupplierName("Shams Navoi");
    }

    @Test
    public void objectToJsonTest() throws IOException {
        objectMapper.writeValue(new File("drugs.json"),drugs);
        String jsonString = objectMapper.writeValueAsString(drugs);
        System.out.println("Object ot json"+jsonString);
    }

    @Test
    public void objectFromJsonTest() throws IOException {
        drugs = objectMapper.readValue(new File("drugs.json"),Supplier.class);
        System.out.println("Json to object-->"+drugs.toString());
    }
}
