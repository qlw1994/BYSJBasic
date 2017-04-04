package qlw.model;

import java.math.BigDecimal;

public class Drugorderdetail {
    private Long id;

    private Long drugorderid;

    private Long drugid;

    private Integer amount;

    private BigDecimal price;

    private String format;

    private BigDecimal money;

    private String drugname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDrugorderid() {
        return drugorderid;
    }

    public void setDrugorderid(Long drugorderid) {
        this.drugorderid = drugorderid;
    }

    public Long getDrugid() {
        return drugid;
    }

    public void setDrugid(Long drugid) {
        this.drugid = drugid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getDrugname() {
        return drugname;
    }

    public void setDrugname(String drugname) {
        this.drugname = drugname == null ? null : drugname.trim();
    }
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