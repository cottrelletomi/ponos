package app.ad;

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
public class AdController {
    @Autowired
    private LoadBalancerClient loadBalancer;
    private RestTemplate restTemplate;
    private static final String MICRO_SERVICE = "micro-service-1";

    public AdController() {
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/companies/{idCompany}/ads")
    public void postAds(@PathVariable("idCompany") int idCompany, @RequestBody Ad ad) {
        this.restTemplate.postForEntity(this.getMicroService("/companies/{idCompany}/ads"), ad, Ad.class, idCompany);
    }

    @HystrixCommand(fallbackMethod = "defaultGetAds")
    @GetMapping("/companies/{idCompany}/ads/{idAd}")
    public Ad getAds(@PathVariable("idCompany") int idCompany, @PathVariable("idAd") int idAd) { ;
        ResponseEntity<Ad> response = this.restTemplate.getForEntity(this.getMicroService("/companies/{idCompany}/ads/{idAd}"), Ad.class, idCompany, idAd);
        return response.getBody();
    }

    @PutMapping("/companies/{idCompany}/ads/{idAd}")
    public void putAds(@PathVariable("idCompany") int idCompany, @PathVariable("idAd") int idAd, @RequestBody Ad ad) {
        this.restTemplate.put(this.getMicroService("/companies/{idCompany}/ads/{idAd}"), ad, idCompany, idAd);
    }

    @DeleteMapping("/companies/{idCompany}/ads/{idAd}")
    public void deleteAds(@PathVariable("idCompany") int idCompany, @PathVariable("idAd") int idAd) {
        this.restTemplate.delete(this.getMicroService("/companies/{idCompany}/ads/{idAd}"), idCompany, idAd);
    }

    @HystrixCommand(fallbackMethod = "defaultGetAds")
    @GetMapping("/companies/{idCompany}/ads")
    public List<Ad> getAds(@PathVariable("idCompany") int idCompany) {
        ResponseEntity<Ad[]> response = this.restTemplate.getForEntity(this.getMicroService("/companies/{idCompany}/ads"), Ad[].class, idCompany);
        return Arrays.asList(response.getBody());
    }

    @HystrixCommand(fallbackMethod = "defaultGetAllAds")
    @GetMapping("/companies/ads")
    public List<Ad> getAllAds() {
        ResponseEntity<Ad[]> response = this.restTemplate.getForEntity(this.getMicroService("/companies/ads"), Ad[].class);
        return Arrays.asList(response.getBody());
    }

    private String getMicroService(String path) {
        ServiceInstance serviceInstance = loadBalancer.choose(MICRO_SERVICE);
        return serviceInstance.getUri() + path;
    }

    public Ad defaultGetAds(int idCompany, int idAd) {
        return new Ad();
    }

    public List<Ad> defaultGetAds(int idCompany) {
        return new ArrayList<>();
    }

    public List<Ad> defaultGetAllAds() {
        return new ArrayList<>();
    }
}