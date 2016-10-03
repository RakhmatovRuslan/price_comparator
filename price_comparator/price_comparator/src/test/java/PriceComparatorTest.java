import entity.Drug;
import levenshtein.Levenshtein;
import org.junit.Test;
import source.StandartMedecinesSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ruslan on 5/14/16.
 */
public class PriceComparatorTest {

    @Test
    public void testComparator(){
        double allowedDiffPercentage = 10;
        int levenchteinDistance;
        List<HashMap<String, Drug>> comparedDrugs = new ArrayList<>();

        List<Drug> drugsOfSuppliyerA = StandartMedecinesSource.getData();
        List<Drug> drugsOfSuppliyerB = StandartMedecinesSource.getDataA();

        for (Drug drug : drugsOfSuppliyerA) {
            Map<String, Drug> offeredDrugBySuppliers = new HashMap<String, Drug>();
            offeredDrugBySuppliers.put("Suppliyer A", drug);
            for (Drug drugB : drugsOfSuppliyerB) {
                levenchteinDistance = Levenshtein.distance(drug.getName(), drugB.getName());
                double diffPercent = 0.0;
                if (drug.getName().length() >= levenchteinDistance) {
                    diffPercent = 100 * levenchteinDistance / drug.getName().length();
                } else {
                    diffPercent = 100 * levenchteinDistance / drugB.getName().length();
                }
                if (diffPercent <= allowedDiffPercentage) {
                    offeredDrugBySuppliers.put("Suppliyer B", drugB);
                }
            }
            comparedDrugs.add((HashMap<String, Drug>) offeredDrugBySuppliers);
        }

        print(comparedDrugs);
    }

    public void print(List<HashMap<String, Drug>> comparedDrugs) {
        StringBuilder printingLine = new StringBuilder("");
        for (HashMap<String, Drug> comparedDrug : comparedDrugs) {
            for (Map.Entry<String, Drug> drugBySuppliers : comparedDrug.entrySet()) {
                String suppliyerName = drugBySuppliers.getKey();
                Drug drug = drugBySuppliers.getValue();
                printingLine.append(suppliyerName).append("::").append(drug.toString()).append("-->");
            }
            printingLine.append("\n");
        }
        System.out.println(printingLine.toString());
    }

}
