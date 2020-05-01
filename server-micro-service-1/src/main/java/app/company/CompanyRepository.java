package app.company;

import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
    Company findCompanyByIdCompany(int idCompany);
    Company findCompanyByEmailAndPassword(String email, String password);
}