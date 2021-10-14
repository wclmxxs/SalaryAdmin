package cwall.club.core.Repository;

import cwall.club.common.Item.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long>{

    int countByUid(Long uid);

    List<Company> findByIdIn(List<Long> cid);
}
