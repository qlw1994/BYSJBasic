package qlw.model;

public class Inspectitems {
    private Long id;

    private Long inspectionid;

    private String name;

    private String result;

    private String refrange;

    private String unit;

    private Integer abnormal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInspectionid() {
        return inspectionid;
    }

    public void setInspectionid(Long inspectionid) {
        this.inspectionid = inspectionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getRefrange() {
        return refrange;
    }

    public void setRefrange(String refrange) {
        this.refrange = refrange == null ? null : refrange.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(Integer abnormal) {
        this.abnormal = abnormal;
    }

    private Inspectionreport inspectionreport;

    public Inspectionreport getInspectionreport() {
        return inspectionreport;
    }

    public void setInspectionreport(Inspectionreport inspectionreport) {
        this.inspectionreport = inspectionreport;
    }
}