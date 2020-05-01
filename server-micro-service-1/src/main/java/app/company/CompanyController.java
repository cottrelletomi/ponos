package app.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/companies")
    public void postCompanies(@RequestBody Company company) {
        Company e = new Company();
        e.setCorporateName(company.getCorporateName());
        e.setSiren(company.getSiren());
        e.setAddress(company.getAddress());
        e.setEmail(company.getEmail());
        e.setPassword(company.getPassword());
        companyRepository.save(e);
    }

    @GetMapping("/companies")
    public Iterable<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @PutMapping("/companies/{idCompany}")
    public void putCompanies(@PathVariable("idCompany") int idCompany,
                               @RequestBody Company company) {
        Company e = companyRepository.findCompanyByIdCompany(idCompany);
        e.setCorporateName(company.getCorporateName());
        e.setSiren(company.getSiren());
        e.setAddress(company.getAddress());
        e.setEmail(company.getEmail());
        e.setPassword(company.getPassword());
        companyRepository.save(e);
    }

    @DeleteMapping("/companies/{idCompany}")
    public void deleteCompanies(@PathVariable("idCompany") int idCompany) {
        companyRepository.delete(companyRepository.findCompanyByIdCompany(idCompany));
    }

    @GetMapping("/companies/auth")
    public int auth(@RequestParam(value="email", required = true) String email,
                    @RequestParam(value="password", required = true) String password) {
        Company e = companyRepository.findCompanyByEmailAndPassword(email, password);
        return e == null ? -1 : e.getIdCompany();
    }
}
