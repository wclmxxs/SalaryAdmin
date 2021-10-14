package cwall.club.core.Repository;

import cwall.club.common.Item.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    Goods findByIdAndCid(Long goodsId, Long cid);

    List<Goods> findByCid(Long cid);
}
