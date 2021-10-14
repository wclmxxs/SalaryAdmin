package cwall.club.core.Service;

import com.google.common.collect.Sets;
import cwall.club.common.DTO.EmployeeInfoDTO;
import cwall.club.common.Enum.ExceptionCode;
import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.EmployeeInfo;
import cwall.club.common.Item.UserContext;
import cwall.club.common.Item.UserInfo;
import cwall.club.common.Util.ClassUtil;
import cwall.club.common.Util.IDUtil;
import cwall.club.common.VO.CompanyVO;
import cwall.club.common.VO.EmployeeInfoVO;
import cwall.club.core.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserInfoService userInfoService;

    public EmployeeInfoVO createEmployeeInfo(EmployeeInfoDTO employeeInfoDTO){
        EmployeeInfo employeeInfo0 = employeeRepository.findByCidAndUid(employeeInfoDTO.getCid(), employeeInfoDTO.getUid());
        if (employeeInfo0 != null){
            throw new SalaryException(500,"该用户已经在公司内");
        }
        UserInfo userInfo = userInfoService.get(employeeInfoDTO.getUid());
        if (userInfo==null){
            throw new SalaryException(200,"用户不存在");
        }
        EmployeeInfo employeeInfo = new EmployeeInfo();
        ClassUtil.copyOneFromOne(employeeInfo, employeeInfoDTO, employeeInfo.getClass(), employeeInfoDTO.getClass());
        employeeInfo.setId(IDUtil.generateID());
        employeeRepository.save(employeeInfo);
        return EmployeeInfoVO.copyFromEmployeeInfo(employeeInfo);
    }

    public EmployeeInfoVO updateEmployeeInfo(Map<String, Object> maps, Long uid, Long cid){
        Map<String,Class> sets = new HashMap<String,Class>(){
            {
                put("type",String.class);
                put("location",String.class);
                put("socialCode",String.class);
                put("tax",Double.class);
                put("otherTax", Double.class);
                put("salary",Double.class);
                put("percent",Double.class);
                put("limit",Integer.class);
                put("payType",String.class);
                put("name", String.class);
            }
        };
        Set<String> antiSelf = Sets.newHashSet("type","tax","otherTax","salary","percent","limit");
        boolean isSelf = uid.equals(UserContext.getUser().getId());
        EmployeeInfo employeeInfo = employeeRepository.findByCidAndUid(cid, uid);
        if (employeeInfo == null){
            throw new SalaryException(500,"该用户不在选择的公司内");
        }
        Class uClass = employeeInfo.getClass();
        for(Field f:uClass.getDeclaredFields()){
            if(maps.containsKey(f.getName())){
                if(isSelf&&antiSelf.contains(f.getName())){
                    continue;
                   // throw new SalaryException(500, "无法更新该信息");
                }
                if(!sets.containsKey(f.getName())||!maps.get(f.getName()).getClass().equals(sets.get(f.getName()))){
                    continue;
                  //  throw new SalaryException(ExceptionCode.NOT_MATCH.getCode(),ExceptionCode.NOT_MATCH.getDescription()+" "+f.getName()+":"+maps.get(f.getName()).getClass().getSimpleName());
                }
                if (f.getName().equals("type")){
                    if (((String) maps.get("type")).length() == 1){
                        maps.put("type", ((String) maps.get("type")).charAt(0));
                    }else {
                        continue;
                    }
                }
                if (f.getName().equals("payType")){
                    if (((String) maps.get("payType")).length() == 1){
                        maps.put("payType", ((String) maps.get("payType")).charAt(0));
                    }else {
                        continue;
                    }
                }
                f.setAccessible(true);
                try {
                    f.set(employeeInfo,maps.get(f.getName()));
                } catch (IllegalAccessException e) {
                    throw new SalaryException(500,"更新用户信息失败");
                }
            }
        }
        employeeRepository.save(employeeInfo);
        return EmployeeInfoVO.copyFromEmployeeInfo(employeeInfo);
    }

    public boolean deleteEmployeeInfo(Long cid, Long uid){
        EmployeeInfo employeeInfo = employeeRepository.findByCidAndUid(cid, uid);
        if (employeeInfo == null){
            return false;
        }
        if (employeeInfo.getType() == '2'){
            throw new SalaryException(500,"无法离职管理人员");
        }
        employeeRepository.deleteById(employeeInfo.getId());
        return true;
    }

    public EmployeeInfoVO getEmployeeInfo(Long cid, Long uid) {
        EmployeeInfo employeeInfo = employeeRepository.findByCidAndUid(cid, uid);
        if (employeeInfo == null){
            throw new SalaryException(500,"该用户不在选择的公司内");
        }
        return EmployeeInfoVO.copyFromEmployeeInfo(employeeInfo);
    }

    public List<CompanyVO> getCompanyInfo() {
        Long id = UserContext.getUser().getId();
        List<CompanyVO> company = companyService.getCompany(employeeRepository.findByUid(id).stream().map(employeeInfo -> employeeInfo.getCid()).collect(Collectors.toList()));
        return company;
    }

    public List<EmployeeInfoVO> getEmployeeInfo(Long cid) {
        return employeeRepository.findByCid(cid).stream().map(employeeInfo -> EmployeeInfoVO.copyFromEmployeeInfo(employeeInfo)).collect(Collectors.toList());
    }
}
