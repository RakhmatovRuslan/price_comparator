package service;

import entity.Drug;
import entity.Supplier;
import levenshtein.Levenshtein;
import source.DrugsSource;
import util.FileURL;

import java.io.IOException;
import java.util.*;

/**
 * Created by ruslan on 5/19/16.
 */
public class CompareDataService {

    private HashMap<String, List<Drug>> tableData = null;
    private List<Drug> suppliersDrug;
    private Properties properties;

    public CompareDataService() {
        properties = new ConfigParamService().getProperties();
    }

    public HashMap<String, List<Drug>> prepareDataForComparison(List<Supplier> suppliers) throws IOException {
        if (suppliers != null) {
            Supplier customDrugs = new DrugsSource(FileURL.DRUGS_JSON_URL.getValue()).getSupplier();
            tableData = new HashMap<>();
            Boolean isExist = false;
            for (Drug customDrug : customDrugs) {
                String customDrugName = customDrug.getName();
                suppliersDrug = new ArrayList<Drug>();
                for (Supplier supplier : suppliers) {
                    for (Drug drug : supplier) {
                        if (isDrugsTheSame(customDrugName, drug.getName())) {
                            suppliersDrug.add(drug);
                            isExist = true;
                            //TODO заменить break на дургое, а то логика не будет работать
                            break;
                        }
                    }
                    if (!isExist) {
                        Drug drug = new Drug();
                        drug.setSuppliyerName(supplier.getSupplierName());
                        suppliersDrug.add(drug);
                    } else {
                        isExist = false;
                    }
                }
                tableData.put(customDrugName, suppliersDrug);
            }
        }
        return tableData;
    }

    public Supplier getNonExistingDrugsInEtalon(List<Supplier> suppliers) throws IOException {
        Supplier customDrugs = new DrugsSource(FileURL.DRUGS_JSON_URL.getValue()).getSupplier();
        Supplier unregisteredDrugs = new Supplier();
        for (Supplier supplier : suppliers) {
            Iterator<Drug> iterator = supplier.iterator();
            while (iterator.hasNext()) {
                Drug drug = iterator.next();
                for (Drug customDrug : customDrugs) {
                    String customDrugName = customDrug.getName();
                    if (isDrugsTheSame(customDrugName, drug.getName())) {
                        iterator.remove();
                    }
                }
            }
            unregisteredDrugs.addAll(supplier);
        }
        return unregisteredDrugs;
    }

    public Supplier getNonExistingDrugsInUnregisteredDrugs(Supplier supplier) {
        Supplier unregisteredDrugs = new DrugsSource(FileURL.DRUGS_UNREGISTERED_JSON_URL.getValue()).getSupplier();
        Supplier nonExistingDrugs = new Supplier();
        if (unregisteredDrugs != null) {
            Iterator<Drug> iterator = supplier.iterator();
            while(iterator.hasNext()){
                Drug drug = iterator.next();
                for (Drug unregisteredDrug : unregisteredDrugs) {
                    String drugName = unregisteredDrug.getName();
                    if (isDrugsTheSame(drugName, drug.getName())) {
                        iterator.remove();
                    }
                }
            }
        nonExistingDrugs.addAll(supplier);
        } else {
            nonExistingDrugs.addAll(supplier);
        }
        return nonExistingDrugs;
    }

    private Boolean isDrugsTheSame(String customDrugName, String supplierDrugName) {
        Double allowedDiffPercentage = Double.valueOf(properties.getProperty("allowedDiffPercentage"));
        int levenchteinDistance = Levenshtein.distance(customDrugName, supplierDrugName);
        double diffPercent = 0.0;
        if (supplierDrugName.length() >= levenchteinDistance) {
            diffPercent = 100 * levenchteinDistance / customDrugName.length();
        } else {
            diffPercent = 100 * levenchteinDistance / supplierDrugName.length();
        }
        if (diffPercent <= allowedDiffPercentage)
            return true;
        else
            return false;
    }

    public List<Drug> sortBestPriceOfDrug(List<Drug> drugs){
//        Drug drugWithLowestPrice;
//        = drugWithLowestPrice
        if(drugs == null || drugs.size() == 0){
            throw new IllegalArgumentException("The drugs can not be null!");
        }
        Drug drugWithBestPrice;
        List<Integer> indexs = new ArrayList<>();
        drugWithBestPrice  = drugs.get(0);
        indexs.add(0);
        if (drugs.size() > 1) {
            for (int i = 1; i < drugs.size(); i++) {
                if ((drugWithBestPrice.getPrice() > drugs.get(i).getPrice() || drugWithBestPrice.getPrice() == 0) && (drugs.get(i).getPrice() != 0)) {
                    drugWithBestPrice = drugs.get(i);
                    indexs.clear();
                    indexs.add(i);
                } else if (drugWithBestPrice.getPrice() == drugs.get(i).getPrice()) {
                    indexs.add(i);
                }
                for (Integer index : indexs) {
                    if(drugs.get(index).getPrice() != 0){
                        drugs.get(index).setBestPrice(true);
                    }
                }
//            if(drugWithLowestPrice.getPrice() > drugs.get(i).getPrice()){
//                drugWithLowestPrice = drugs.get(i);
//            }
            }
        }else{
            drugWithBestPrice.setBestPrice(true);
        }
        return drugs;
    }
}
