package cwall.club.core.Service;

import cwall.club.common.DTO.CompanyDTO;
import cwall.club.common.DTO.EmployeeInfoDTO;
import cwall.club.common.Enum.ExceptionCode;
import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.Company;
import cwall.club.common.Item.UserContext;
import cwall.club.common.Util.AssertUtil;
import cwall.club.common.Util.IDUtil;
import cwall.club.common.VO.CompanyVO;
import cwall.club.core.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeService employeeService;

    public CompanyVO createCompany(CompanyDTO companyDTO){
        AssertUtil.isEmpty(companyDTO.getName());
        AssertUtil.isEmpty(companyDTO.getDescription());
        UserContext user = UserContext.getUser();
        int size = companyRepository.countByUid(user.getId());
        if (size != 0){
            throw new SalaryException(ExceptionCode.COMPANY_EXIST);
        }
        Company company = new Company();
        company.setUid(user.getId());
        company.setDescription(companyDTO.getDescription());
        company.setName(companyDTO.getName());
        company.setId(IDUtil.generateID());
        EmployeeInfoDTO employeeInfoDTO = EmployeeInfoDTO.build(company.getId(),user.getId(),'2',0,0,0,0,'0',"未设置");
        companyRepository.save(company);
        employeeInfoDTO.setName(user.getUid());
        employeeService.createEmployeeInfo(employeeInfoDTO);
        return CompanyVO.copyFromCompany(company);
    }

    public List<CompanyVO> getCompany(List<Long> cid){
        return companyRepository.findByIdIn(cid).stream().map(company -> CompanyVO.copyFromCompany(company)).collect(Collectors.toList());
    }
}
