package cwall.club.core.Repository;

import cwall.club.common.Item.SignInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignInfoRepository extends JpaRepository<SignInfo, Long> {
    List<SignInfo> findByEmployeeId(Long employeeId);
}
