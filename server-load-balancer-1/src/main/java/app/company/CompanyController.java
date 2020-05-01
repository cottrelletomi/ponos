package app.company;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyController {
    @Autowired
    private LoadBalancerClient loadBalancer;
    private RestTemplate restTemplate;
    private static final String MICRO_SERVICE = "micro-service-1";

    public CompanyController() {
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/companies")
    public void postCompanies(@RequestBody Company company) {
        this.restTemplate.postForEntity(this.getMicroService("/companies"), company, Company.class);
    }

    @HystrixCommand(fallbackMethod = "defaultGetCompanies")
    @GetMapping("/companies")
    public List<Company> getCompanies() {
        ResponseEntity<Company[]> response = this.restTemplate.getForEntity(this.getMicroService("/companies"), Company[].class);
        return Arrays.asList(response.getBody());
    }

    @PutMapping("/companies/{idCompany}")
    public void putCompanies(@PathVariable("idCompany") int idCompany,
                             @RequestBody Company company) {
        this.restTemplate.put(this.getMicroService("/companies/{idCompany}"), company, idCompany);
    }

    @DeleteMapping("/companies/{idCompany}")
    public void deleteCompanies(@PathVariable("idCompany") int idCompany) {
        this.restTemplate.delete(this.getMicroService("/companies/{idCompany}"), idCompany);
    }

    @HystrixCommand(fallbackMethod = "defaultAuth")
    @GetMapping("/companies/auth")
    public int auth(@RequestParam(value = "email") String email,
                    @RequestParam(value = "password") String password) {
        ResponseEntity<Integer> response = this.restTemplate.getForEntity(this.getMicroService("/companies/auth?email={email}&password={password}"), Integer.class, email, password);
        return response.getBody();
    }

    private String getMicroService(String path) {
        ServiceInstance serviceInstance = loadBalancer.choose(MICRO_SERVICE);
        return serviceInstance.getUri() + path;
    }

    public List<Company> defaultGetCompanies() {
        return new ArrayList<>();
    }

    public int defaultAuth(String email, String password) {
        return -1;
    }
}