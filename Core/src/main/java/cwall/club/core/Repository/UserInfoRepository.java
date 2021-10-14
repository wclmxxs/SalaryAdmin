package cwall.club.core.Repository;

import cwall.club.common.Item.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    int countByUid(String uid);
    int countByPhone(String phone);
    int countByUidAndPwd(String phone,String pwd);
    UserInfo findByPhone(String phone);

    UserInfo findByUid(String uid);
}
