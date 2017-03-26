package qlw.model.copy;

import qlw.model.Drug;
import qlw.model.Drugorder;

/**
 * Created by wiki on 2017/3/15.
 */
public class DrugorderdetailCopy {

    Drugorder drugorder;

    public Drugorder getDrugorder() {
        return drugorder;
    }

    public void setDrugorder(Drugorder drugorder) {
        this.drugorder = drugorder;
    }

    Drug drug;

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
}
