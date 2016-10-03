package validate;

import entity.Supplier;

import java.util.List;

/**
 * Created by RUSLAN on 24.06.2016.
 */
public class SuppliersValidation {

    public static boolean isSuppliersNameValid(List<Supplier> supplierList) {
        for (int i = 0; i < supplierList.size(); i++) {
            for (int j = 1+i; j < supplierList.size(); j++) {
                if (supplierList.get(i).getSupplierName().toLowerCase().trim().
                        equals(supplierList.get(j).getSupplierName().toLowerCase().trim())) {
                    return false;
                }
            }
        }
        return true;
    }
}
