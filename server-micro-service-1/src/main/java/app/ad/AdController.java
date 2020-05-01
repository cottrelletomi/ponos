package app.ad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdController {

    @Autowired
    private AdRepository adRepository;

    @PostMapping("/companies/{idCompany}/ads")
    public void postAds(@PathVariable("idCompany") int idCompany,
                             @RequestBody Ad ad) {
        Ad a = new Ad();
        a.setTitle(ad.getTitle());
        a.setDescription(ad.getDescription());
        a.setRemuneration(ad.getRemuneration());
        a.setIdCompany(idCompany);
        adRepository.save(a);
    }

    @GetMapping("/companies/{idCompany}/ads/{idAd}")
    public Ad getAds(@PathVariable("idCompany") int idCompany,
                   @PathVariable("idAd") int idAd) { ;
        return adRepository.findAdsByIdCompanyAndIdAd(idCompany, idAd);
    }

    @PutMapping("/companies/{idCompany}/ads/{idAd}")
    public void putAds(@PathVariable("idCompany") int idCompany,
                               @PathVariable("idAd") int idAd,
                               @RequestBody Ad ad) {
        Ad a = adRepository.findAdsByIdCompanyAndIdAd(idCompany, idAd);
        a.setTitle(ad.getTitle());
        a.setDescription(ad.getDescription());
        a.setRemuneration(ad.getRemuneration());
        adRepository.save(a);
    }

    @DeleteMapping("/companies/{idCompany}/ads/{idAd}")
    public void deleteAds(@PathVariable("idCompany") int idCompany,
                                  @PathVariable("idAd") int idAd) {
        adRepository.delete(adRepository.findAdsByIdCompanyAndIdAd(idCompany, idAd));
    }

    @GetMapping("/companies/{idCompany}/ads")
    public List<Ad> getAds(@PathVariable("idCompany") int idCompany) { ;
        return adRepository.findAdsByIdCompany(idCompany);
    }

    @GetMapping("/companies/ads")
    public Iterable<Ad> getAllAds() {
        return adRepository.findAll();
    }
}