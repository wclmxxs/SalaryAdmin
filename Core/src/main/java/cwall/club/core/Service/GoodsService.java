package cwall.club.core.Service;

import cwall.club.common.DTO.GoodsDTO;
import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.Goods;
import cwall.club.common.Item.UserContext;
import cwall.club.common.Util.ClassUtil;
import cwall.club.common.Util.IDUtil;
import cwall.club.common.VO.GoodsVO;
import cwall.club.core.Repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoodsService {
    @Autowired
    private GoodsRepository goodsRepository;

    public GoodsVO createGoods(GoodsDTO goodsDTO,Long cid){
        if (!goodsDTO.getCid().equals(cid)){
            throw new SalaryException(500, "请传入正确的公司id");
        }
        if (goodsDTO.getBuy() < 0 || goodsDTO.getLast()<0){
            throw new SalaryException(500, "商品数量错误");
        }
        Goods goods = Goods.copyFromDTO(goodsDTO);
        goods.setId(IDUtil.generateID());
        goodsRepository.save(goods);
        return GoodsVO.copyFrom(goods);
    }

    public GoodsVO updateGoods(GoodsDTO goodsDTO,Long cid){
        if (goodsDTO.getId() == null || !goodsDTO.getCid().equals(cid)){
            throw new SalaryException(500, "请传入正确参数");
        }
        if (goodsDTO.getBuy() < 0 || goodsDTO.getLast()<0){
            throw new SalaryException(500, "商品数量错误");
        }
        Goods goods = Goods.copyFromDTO(goodsDTO);
        goods.setId(goodsDTO.getId());
        Optional<Goods> byId = goodsRepository.findById(goods.getId());
        if (!byId.isPresent()){
            throw new SalaryException(500, "物品id错误");
        }
        goods.set_id(byId.get().get_id());
        goodsRepository.save(goods);
        return GoodsVO.copyFrom(goods);
    }

    public boolean deleteGoods(Long goodsId,Long cid){
        Goods goods = goodsRepository.findByIdAndCid(goodsId, cid);
        if (goods == null){
            throw new SalaryException(500, "物品不存在");
        }
        goodsRepository.deleteById(goodsId);
        return true;
    }

    public List<GoodsVO> findAllGoods(Long cid){
        List<Goods> goods = goodsRepository.findByCid(cid);
        List<GoodsVO> result = goods.stream().map(good -> GoodsVO.copyFrom(good)).collect(Collectors.toList());
        return result;
    }

    public GoodsVO findGoods(Long goodsId, Long cid) {
        Optional<Goods> goods = goodsRepository.findById(goodsId);
        if (!goods.isPresent()){
            throw new SalaryException(500, "物品不存在");
        }
        return GoodsVO.copyFrom(goods.get());
    }
}
