package com.mycompany.myapp.service;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IPStackService {
    @Value("${ipstack:}")
    private String apiKey;

    public String retrieveCountryCodeFromIp(String ip) {
        try {
            ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://api.ipstack.com/" + ip + "?access_key=" + apiKey, String.class);
            return JsonPath.read(responseEntity.getBody(), "$.country_code");
        } catch (Exception e) {
            return null;
        }
    }
}
