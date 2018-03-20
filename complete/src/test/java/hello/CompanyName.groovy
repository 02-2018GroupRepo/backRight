package hello

import spock.lang.Specification


class CompanyName extends Specification {
    def "Returns company name"() {
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
}