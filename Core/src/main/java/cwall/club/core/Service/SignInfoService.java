package cwall.club.core.Service;

import cwall.club.common.DTO.SignInfoDTO;
import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.SignInfo;
import cwall.club.common.Item.UserContext;
import cwall.club.common.Util.IDUtil;
import cwall.club.common.VO.EmployeeInfoVO;
import cwall.club.common.VO.SignInfoVO;
import cwall.club.core.Repository.SignInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

//提交签到时候需要判断某个日期是否已经签到过,判断是否超过工时,将limit设置为默认24
@Service
public class SignInfoService {
    @Autowired
    private SignInfoRepository signInfoRepository;
    @Autowired
    private EmployeeService employeeService;

    public List<SignInfoVO> cancelSign(List<SignInfoDTO> signInfoDTOS, Long cid){
        EmployeeInfoVO employeeInfo = employeeService.getEmployeeInfo(cid, UserContext.getUser().getId());
        List<SignInfo> es = signInfoRepository.findByEmployeeId(employeeInfo.getId());
        Map<Integer,SignInfo> hasSign = new HashMap<>();
        for (SignInfo signInfo:es){
            hasSign.put(signInfo.getTime().getDate(), signInfo);
        }
        List<SignInfo> delete = new ArrayList<>();
        for(SignInfoDTO signInfoDTO:signInfoDTOS){
            if (hasSign.containsKey(signInfoDTO.getTime().getDate())){
                delete.add(hasSign.get(signInfoDTO.getTime().getDate()));
            }
        }
        signInfoRepository.deleteAll(delete);
        return delete.stream().map(signInfo -> SignInfoVO.copyFrom(signInfo)).collect(Collectors.toList());
    }

    public List<SignInfo> get(Long employeeId){
        List<SignInfo> es = signInfoRepository.findByEmployeeId(employeeId);
        return es;
    }

    public List<SignInfoVO> signDate(List<SignInfoDTO> signInfoDTOS, Long cid){
        EmployeeInfoVO employeeInfo = employeeService.getEmployeeInfo(cid, UserContext.getUser().getId());
        List<SignInfo> es = signInfoRepository.findByEmployeeId(employeeInfo.getId());
        Map<Integer,SignInfo> hasSign = new HashMap<>();
        for (SignInfo signInfo:es){
            hasSign.put(signInfo.getTime().getDate(), signInfo);
        }
        List<SignInfo> save = new ArrayList<>();
        for (SignInfoDTO signInfoDTO:signInfoDTOS){
            if (signInfoDTO.getLen() > employeeInfo.getLenLimit() || signInfoDTO.getLen() <= 0){
                throw new SalaryException(500, "更新错误,时间不满足要求: (0,"+employeeInfo.getLenLimit()+"]");
            }
            if (!signInfoDTO.getCid().equals(cid) || !signInfoDTO.getEmployeeId().equals(employeeInfo.getId())){
                continue;
            }
            if (!hasSign.containsKey(signInfoDTO.getTime().getDate())){
                SignInfo signInfo = SignInfo.copyFromDTO(signInfoDTO);
                signInfo.setId(IDUtil.generateID());
                save.add(signInfo);
            }else {
                SignInfo signInfo = hasSign.get(signInfoDTO.getTime().getDate());
                signInfo.setTime(signInfoDTO.getTime());
                signInfo.setLen(signInfoDTO.getLen());
                signInfo.setPid(signInfoDTO.getPid());
                save.add(signInfo);
            }
        }
        signInfoRepository.saveAll(save);
        return signInfoRepository.findByEmployeeId(employeeInfo.getId()).stream().map(signInfo -> SignInfoVO.copyFrom(signInfo)).collect(Collectors.toList());
    }
}
