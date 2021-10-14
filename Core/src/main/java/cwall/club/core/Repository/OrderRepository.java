package cwall.club.core.Repository;

import cwall.club.common.Item.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo,Long> {
    List<OrderInfo> findByEmployeeId(Long id);

    List<OrderInfo> findByCid(Long cid);
}
