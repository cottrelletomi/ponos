package app.remark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RemarkController {

    @Autowired
    private RemarkRepository remarkRepository;

    @PostMapping("/ads/{idAd}/remarks")
    public void postRemarks(@PathVariable int idAd, @RequestBody Remark remark) {
        Remark r = new Remark();
        r.setTitle(remark.getTitle());
        r.setRemark(remark.getRemark());
        r.setIdAd(idAd);
        remarkRepository.save(r);
    }

    @GetMapping("/ads/{idAd}/remarks")
    public List<Remark> getRemarks(@PathVariable int idAd) {
        return remarkRepository.findRemarksByIdAd(idAd);
    }

    @PutMapping("/ads/{idAd}/remarks/{idRemark}")
    public void putRemarks(@PathVariable("idAd") int idAd,
                           @PathVariable("idRemark") int idRemark,
                           @RequestBody Remark remark) {
        Remark r = remarkRepository.findRemarkByIdAdAndIdRemark(idAd, idRemark);
        r.setTitle(remark.getTitle());
        r.setRemark(remark.getRemark());
        remarkRepository.save(r);
    }

    @DeleteMapping("/ads/{idAd}/remarks/{idRemark}")
    public void deleteRemarks(@PathVariable("idAd") int idAd,
                              @PathVariable("idRemark") int idRemark) {
        remarkRepository.delete(remarkRepository.findRemarkByIdAdAndIdRemark(idAd, idRemark));
    }
}
