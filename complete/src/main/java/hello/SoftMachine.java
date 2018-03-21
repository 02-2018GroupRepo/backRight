package hello;

public class SoftMachine {

    private String companyName;

    public SoftMachine() {}

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(BadCompany company) {
        this.companyName = company.getCompanyName();
    }
}
