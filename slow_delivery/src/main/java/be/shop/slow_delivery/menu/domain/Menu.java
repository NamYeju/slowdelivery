package be.shop.slow_delivery.menu.domain;

import be.shop.slow_delivery.common.domain.DisplayInfo;
import be.shop.slow_delivery.shop.domain.Shop;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false, name="menu_name")
    private String menuName;

    @Column(nullable = true, name="introduction")
    private String introduction;

    @Embedded
    private DisplayInfo displayInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public Menu(Shop shop,String menuName, String introduction, DisplayInfo displayInfo){
        this.shop=shop;
        this.menuName=menuName;
        this.introduction=introduction;
        this.displayInfo = displayInfo;
    }

    public void updateMenu(String menuName,String introduction, DisplayInfo displayInfo){
        this.menuName=menuName;
        this.introduction=introduction;
        this.displayInfo=displayInfo;
    }

}