package app.remark;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RemarkRepository extends CrudRepository<Remark, String> {
    List<Remark> findRemarksByIdAd(int idAd);
    Remark findRemarkByIdAdAndIdRemark(int idAd, int idRemark);
}