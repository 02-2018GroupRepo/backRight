package hello

import spock.lang.Specification


class CompanyName extends Specification {
    def "Returns company name correctly, from a correctly initialized machine"() {
        given: "a company"
        and: "a machine"
        BadCompany company = new BadCompany("Bad Company");
        SoftMachine machine = new SoftMachine();
        machine.setCompanyName(company);
        when: "company name is called"
        String result = machine.getCompanyName();
        then: "company name is returned"
        result.equals("Bad Company");
    }

    def "Company name can be reset in company"() {
        given: "a company"
        BadCompany company = new BadCompany()
        when: "company changes name"
        company.setCompanyName("The New Company")
        then: "company name variable is changed"
        company.getCompanyName() == "The New Company"
    }

    def "machine returns null name"() {
        given: "a company"
        BadCompany company = new BadCompany()
        and: "a machine declared without defining company name"
        SoftMachine machine = new SoftMachine()
        when: "company name is called from machine"
        String shouldBeNull = machine.getCompanyName()
        then: "name is returned null"
        shouldBeNull == null
    }

    def "machine returns erroneous name"() {
        given: "a company"
        BadCompany company = new BadCompany()
        and: "a machine with an incorrect company name"
        SoftMachine machine = new SoftMachine()
        machine.setCompanyName(company)
        company.setCompanyName("I should fail")
        when: "machine's company name is called"
        String maCoName = machine.getCompanyName()
        then: "it does not equal company's name"
        maCoName != company.getCompanyName()
    }
}