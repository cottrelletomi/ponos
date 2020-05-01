package app.remark;

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
public class RemarkController {
    @Autowired
    private LoadBalancerClient loadBalancer;
    private RestTemplate restTemplate;
    private static final String MICRO_SERVICE = "micro-service-2";

    public RemarkController() {
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/ads/{idAd}/remarks")
    public void postRemarks(@PathVariable int idAd, @RequestBody Remark remark) {
        this.restTemplate.postForEntity(this.getMicroService("/ads/{idAd}/remarks"), remark, Remark.class, idAd);
    }

    @HystrixCommand(fallbackMethod = "defaultGetRemarks")
    @GetMapping("/ads/{idAd}/remarks")
    public List<Remark> getRemarks(@PathVariable int idAd) {
        ResponseEntity<Remark[]> response = this.restTemplate.getForEntity(this.getMicroService("/ads/{idAd}/remarks"), Remark[].class, idAd);
        return Arrays.asList(response.getBody());
    }

    @PutMapping("/ads/{idAd}/remarks/{idRemark}")
    public void putRemarks(@PathVariable("idAd") int idAd,
                           @PathVariable("idRemark") int idRemark,
                           @RequestBody Remark remark) {
        this.restTemplate.put(this.getMicroService("/ads/{idAd}/remarks/{idRemark}"), remark, idAd, idRemark);
    }

    @DeleteMapping("/ads/{idAd}/remarks/{idRemark}")
    public void deleteRemarks(@PathVariable("idAd") int idAd,
                              @PathVariable("idRemark") int idRemark) {
        this.restTemplate.delete(this.getMicroService("/ads/{idAd}/remarks/{idRemark}"), idAd, idRemark);
    }

    private String getMicroService(String path) {
        ServiceInstance serviceInstance = loadBalancer.choose(MICRO_SERVICE);
        return serviceInstance.getUri() + path;
    }

    public List<Remark> defaultGetRemarks(int idAd) {
        return new ArrayList<>();
    }
}