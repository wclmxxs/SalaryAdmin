package cwall.club.common.DTO;

import lombok.Data;

@Data
public class GoodsDTO {
    Long id;
    String name;
    String description;
    Long cid;
    double price;
    int last;
    int buy;
}
