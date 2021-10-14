package cwall.club.common.VO;

import cwall.club.common.Item.Company;
import cwall.club.common.Util.ClassUtil;
import lombok.Data;

@Data
public class CompanyVO {
    String name;
    String description;
    Long uid;
    Long cid;
    Long id;

    public static CompanyVO copyFromCompany(Company company) {
        CompanyVO companyVO = new CompanyVO();
        companyVO.setCid(company.getId());
        companyVO.setUid(company.getUid());
        companyVO.setDescription(company.getDescription());
        companyVO.setName(company.getName());
        companyVO.setId(company.getId());
        return companyVO;
    }
}
