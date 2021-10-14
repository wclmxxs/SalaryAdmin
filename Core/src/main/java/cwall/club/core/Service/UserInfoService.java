package cwall.club.core.Service;

import cwall.club.common.Config.RedisConfig;
import cwall.club.common.DTO.UserInfoDTO;
import cwall.club.common.Enum.ExceptionCode;
import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.UserContext;
import cwall.club.common.Item.UserInfo;
import cwall.club.common.VO.UserInfoVO;
import cwall.club.core.Repository.UserInfoRepository;
import cwall.club.common.Util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class UserInfoService {

    @Autowired
    private CodeService codeService;

    @Resource
    private UserInfoRepository userInfoRepository;
    @Resource
    private RedisConfig redisConfig;

    public boolean existUserByUid(String uid){
        int size = userInfoRepository.countByUid(uid);
        return size!=0;
    }
    public boolean existUserByPhone(String phone){
        int size = userInfoRepository.countByPhone(phone);
        return size!=0;
    }


    public UserInfo get(Long id){
        return userInfoRepository.findById(id).get();
    }

    public boolean registerUser(UserInfoDTO userInfoDTO){
        String phone = userInfoDTO.getPhone();
        if(phone !=null&& phone.matches("[1][0-9]{10}")){
            if(existUserByPhone(phone)){
                throw new SalaryException(ExceptionCode.EXIST_USER);
            }
            if(redisConfig.getValue("register."+phone)==null){
                if(userInfoDTO.getCode()!=0){
                    throw new SalaryException(ExceptionCode.CODE_INVALID);
                }
                int code = 1000+new Random().nextInt(8999);
                redisConfig.setExValue("register."+phone,code,120);
                // send mail
                codeService.sendCode(phone,code+"");
                System.out.println(code);
                return true;
            }else if(userInfoDTO.getCode()!=0){
                int code = (int) redisConfig.getValue("register."+phone);
                if(code != userInfoDTO.getCode()){
                    throw new SalaryException(ExceptionCode.CODE_ERROR);
                }
                userInfoDTO.setPwd(userInfoDTO.getPhone()+IDUtil.generatePWD());
                userInfoDTO.setUid("cwall_"+ IDUtil.generateID());
                UserInfo userInfo = UserInfo.copyFromDTO(userInfoDTO);
                userInfo.setId(IDUtil.generateID());
                redisConfig.removeKey("register."+phone);
                userInfoRepository.save(userInfo);
                UserContext user = UserContext.getUser();
                user.getSession().setAttribute("uid",userInfoDTO.getUid());
                user.getSession().setAttribute("pwd",userInfoDTO.getPwd());
                user.getSession().setAttribute("id",userInfo.getId());
                return true;
            }
        }
        throw new SalaryException(ExceptionCode.NEED_PARAMETER);
    }

    public boolean getUser(UserInfoDTO userInfoDTO, HttpServletRequest response) {
        UserContext user = UserContext.getUser();
        user.setId(null);
        String phone = userInfoDTO.getPhone();
        if(userInfoDTO.getUid() == null && phone !=null){
            if(userInfoRepository.countByPhone(phone)!=1){
                return false;
            }
            UserInfo userInfo = userInfoRepository.findByPhone(phone);
            if(userInfoDTO.getPwd() == null){
                if(userInfoDTO.getCode() == 0){
                    if(redisConfig.getValue("login."+phone) != null){
                        throw new SalaryException(ExceptionCode.REQUEST_AFTER);
                    }
                    int code = 1000+new Random().nextInt(8999);
                    redisConfig.setExValue("login."+ phone,code,120);
                    // send mail
                    codeService.sendCode(phone,code+"");
                    System.out.println(code);
                    return true;
                }else{
                    if(redisConfig.getValue("login."+phone) == null){
                        throw new SalaryException(ExceptionCode.CODE_INVALID);
                    }
                    int code = (int) redisConfig.getValue("login."+phone);
                    if (code != userInfoDTO.getCode()){
                        return false;
                    }
                    redisConfig.removeKey("login."+phone);
                    userInfoDTO.setPwd(userInfo.getPwd());
                }
            }
            user.setId(userInfo.getId());
            userInfoDTO.setUid(userInfo.getUid());
        }
        if(user.getId() == null){
            UserInfo userInfo = userInfoRepository.findByUid(userInfoDTO.getUid());
            if(userInfo == null){
                return false;
            }
            user.setId(userInfo.getId());
        }
        user.setUid(userInfoDTO.getUid());
        user.setPwd(userInfoDTO.getPwd());
        UserContext.setUser(user);
        if(!canLogin()){
            return false;
        }
        user.getSession().setAttribute("uid",user.getUid());
        user.getSession().setAttribute("pwd",user.getPwd());
        user.getSession().setAttribute("id",user.getId());
        return true;
    }

    public boolean canLogin() {
        UserContext user = UserContext.getUser();
        int size = userInfoRepository.countByUidAndPwd(user.getUid(),user.getPwd());
        if (size == 1){
           return true;
        }
        return false;
    }

    public UserInfoVO getUserInfo() {
        UserContext user = UserContext.getUser();
        Long id = user.getId();
        Optional<UserInfo> userInfo = userInfoRepository.findById(id);
        return UserInfoVO.copyFromUserInfo(userInfo.get());
    }

    public UserInfoVO setUserInfo(HashMap<String,Object> maps) {
        Map<String,Class> sets = new HashMap<String,Class>(){
            {
                put("name",String.class);
                put("sign",String.class);
                put("sex",Boolean.class);
                put("trueName",String.class);
                put("bornTime",Date.class);
                put("location",String.class);
            }
        };
        UserContext user = UserContext.getUser();
        UserInfo userInfo = userInfoRepository.findById(user.getId()).get();
        Class uClass = userInfo.getClass();
        for(Field f:uClass.getDeclaredFields()){
            if(maps.containsKey(f.getName())){
                if(!sets.containsKey(f.getName())||!maps.get(f.getName()).getClass().equals(sets.get(f.getName()))){
                    if (!sets.containsKey(f.getName())&&"uid".equalsIgnoreCase(f.getName())&&!maps.get(f.getName()).equals(user.getUid())){
                        String uid = (String) maps.get(f.getName());
                        if (userInfoRepository.findByUid(uid)!=null){
                            throw new SalaryException(500,"修改失败,因为您尝试使用的uid已经被使用");
                        }else {
                            f.setAccessible(true);
                            try {
                                f.set(userInfo, uid);
                            } catch (IllegalAccessException e) {
                                throw new SalaryException(500,"更新用户uid信息失败");
                            }
                            user.setUid(uid);
                            user.getSession().setAttribute("uid", uid);
                            userInfoRepository.save(userInfo);
                        }
                    }
                    continue;
                    //   throw new SalaryException(ExceptionCode.NOT_MATCH.getCode(),ExceptionCode.NOT_MATCH.getDescription()+" "+f.getName()+":"+maps.get(f.getName()).getClass().getSimpleName());
                }
                f.setAccessible(true);
                try {
                    f.set(userInfo,maps.get(f.getName()));
                } catch (IllegalAccessException e) {
                    throw new SalaryException(500,"更新用户信息失败");
                }
            }
        }
        userInfoRepository.save(userInfo);
        return UserInfoVO.copyFromUserInfo(userInfo);
    }

    public boolean changePassword(String code,String pwd) {
        UserContext user = UserContext.getUser();
        String phone = userInfoRepository.findById(user.getId()).get().getPhone();
        String key = "changepwd."+phone;
        if (redisConfig.getValue(key) == null){
            code = (1000+new Random().nextInt(8999))+"";
            redisConfig.setExValue(key,code,120);
            // send mail
            codeService.sendCode(phone,code+"");
            System.out.println(code);
            return true;
        }else {
            String nc = (String) redisConfig.getValue(key);
            if (!nc.equals(code)){
                return false;
            }
            UserInfo userInfo = userInfoRepository.findById(user.getId()).get();
            userInfo.setPwd(pwd);
            userInfoRepository.save(userInfo);
            user.setPwd(pwd);
            user.getSession().setAttribute("pwd", pwd);
            return true;
        }
    }
}
