package cwall.club.common.Item;

import cwall.club.common.DTO.GoodsDTO;
import cwall.club.common.Util.ClassUtil;
import cwall.club.common.Util.IDUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "goods")
public class Goods extends BaseItem{
    String name;
    String description;
    Long cid;
    double price;
    int last;
    int buy;

    public static Goods copyFromDTO(GoodsDTO goodsDTO) {
        Goods goods = new Goods();
        ClassUtil.copyOneFromOne(goods,goodsDTO,goods.getClass(),goodsDTO.getClass());
        return goods;
    }
}
