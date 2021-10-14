package cwall.club.common.VO;

import cwall.club.common.Item.Goods;
import cwall.club.common.Util.ClassUtil;
import lombok.Data;

@Data
public class GoodsVO {
    String name;
    String description;
    Long cid;
    double price;
    int last;
    int buy;
    Long id;

    public static GoodsVO copyFrom(Goods goods) {
        GoodsVO goodsVO = new GoodsVO();
        ClassUtil.copyOneFromOne(goodsVO,goods,goodsVO.getClass(),goods.getClass());
        goodsVO.setId(goods.getId());
        return goodsVO;
    }
}
