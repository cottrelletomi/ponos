package app.ad;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableJpaRepositories
public interface AdRepository extends CrudRepository<Ad, Integer> {
    List<Ad> findAdsByIdCompany(int idCompany);
    Ad findAdsByIdCompanyAndIdAd(int idCompany, int idAd);
}
