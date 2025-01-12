package com.tehila.coupons.logic;

import com.tehila.coupons.dal.ICompaniesDal;
import com.tehila.coupons.dto.Company;
import com.tehila.coupons.entities.CompanyEntity;
import com.tehila.coupons.enums.ErrorType;
import com.tehila.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompaniesLogic {

    private ICompaniesDal companiesDal;

    @Autowired
    public CompaniesLogic(ICompaniesDal companiesDal) {
        this.companiesDal = companiesDal;
    }

    public void createCompany(Company company, String userType) throws ServerException {
        if (isCompanyNameExist(company.getName())) {
            throw new ServerException(ErrorType.COMPANY_ALREADY_EXISTS, company.toString());
        }
        validateCompany(company, userType);
        CompanyEntity companyEntity = convertCompanyToCompanyEntity(company);
        this.companiesDal.save(companyEntity);
    }

    private CompanyEntity convertCompanyToCompanyEntity(Company company) {
        CompanyEntity companyEntity = new CompanyEntity(company.getId(), company.getName(), company.getAddress(), company.getPhone());
        return companyEntity;
    }

    public List<Company> getCompanies() {
        List<CompanyEntity> companyEntities = (List<CompanyEntity>) companiesDal.findAll();
        List<Company> companies = new ArrayList<>();
        for (CompanyEntity companyEntity : companyEntities) {
            companies.add(convertCompanyEntityToCompany(companyEntity));
        }
        return companies;
    }

    private Company convertCompanyEntityToCompany(CompanyEntity companyEntity) {
        Company company = new Company(companyEntity.getId(), companyEntity.getName(), companyEntity.getAddress(), companyEntity.getPhone());
        return company;
    }

    public void deleteCompany(int id, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
        if (!isCompanyIdExist(id)) {
            throw new ServerException(ErrorType.COMPANY_NOT_EXIST);
        }
        this.companiesDal.deleteById(id);
    }

    public void updateCompany(Company company, String userType) throws ServerException {
        if (!isCompanyIdExist(company.getId())) {
            throw new ServerException(ErrorType.COMPANY_NOT_EXIST, company.toString());
        }
        CompanyEntity companyBeforeUpdate = this.companiesDal.findById(company.getId()).get();
        if (companyBeforeUpdate.getName().equals(company.getName())) {
            if (isCompanyNameExist(company.getName())) {
                throw new ServerException(ErrorType.COMPANY_ALREADY_EXISTS, company.toString());
            }
        }

        validateCompany(company, userType);
        CompanyEntity companyEntity = convertCompanyToCompanyEntity(company);
//        try {
//            this.companiesDal.save(companyEntity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        this.companiesDal.save(companyEntity);
    }

    public Company getCompany(int id) throws ServerException {
        Company company = convertCompanyEntityToCompany(companiesDal.findById(id).get());
        if (company == null) {
            throw new ServerException(ErrorType.COMPANY_NOT_EXIST);
        }
        return company;
    }

    boolean isCompanyNameExist(String companyName) {
        return this.companiesDal.existsByName(companyName);
    }

    boolean isCompanyIdExist(int companyId) {
        return this.companiesDal.existsById(companyId);
    }

    private void validateCompany(Company company, String userType) throws ServerException {

        if (company.getName() == null) {
            throw new ServerException(ErrorType.INVALID_COMPANY_NAME, company.toString());
        }

        if (company.getName().length() > 45) {
            throw new ServerException(ErrorType.COMPANY_NAME_TOO_LONG, company.toString());
        }

        if (company.getAddress() != null && company.getAddress().length() > 45) {
            throw new ServerException(ErrorType.COMPANY_ADDRESS_TOO_LONG, company.toString());
        }

        if (company.getPhone() != null && company.getPhone().length() < 8) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER, company.toString());
        }

        if (company.getPhone() != null && company.getPhone().length() > 10) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER, company.toString());
        }

        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
    }
}
