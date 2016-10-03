package util;

import entity.Drug;
import entity.Supplier;

import java.util.List;

/**
 * Created by RUSLAN on 19.06.2016.
 */
public class Converter {
    public static Supplier convetToSupplier(List<Drug> drugs){
        Supplier supplier = new Supplier();
        supplier.addAll(drugs);
        return supplier;
    }
}
