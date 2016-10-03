package entity;

import java.util.ArrayList;

/**
 * Created by ruslan on 5/14/16.
 */
public class Supplier extends ArrayList<Drug> {
    private String supplierName;


    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
