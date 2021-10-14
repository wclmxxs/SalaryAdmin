package cwall.club.core.Repository;

import cwall.club.common.Item.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeInfo,Long> {
    EmployeeInfo findByCidAndUid(Long cid, Long uid);
    List<EmployeeInfo> findByUid(Long uid);

    List<EmployeeInfo> findByCid(Long cid);

    List<EmployeeInfo> findByType(char c);

    List<EmployeeInfo> findByTypeIn(List<Character> asList);
}
