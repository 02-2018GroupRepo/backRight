package hello;


import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

public class SoftMachine {
    private String companyName;
    private String machineId;

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(BadCompany company) {
        this.companyName = company.getCompanyName();
    }

    public void requestRestock() {
        try {
            makeCall();
        } catch (Exception e) {
            System.out.println("Request for restock failed.");
        }
    }

    private void makeCall() throws ResourceAccessException {
            String transactionUrl = "http://192.168.88.123:8080/requestRestock";
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(transactionUrl).queryParam("id", machineId);

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(builder.toUriString(), String.class);
            System.out.println(response);
    }
}
