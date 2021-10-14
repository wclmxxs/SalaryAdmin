package cwall.club.common.Item;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "company")
public class Company extends BaseItem{
    String name;
    String description;
    Long uid;
}
