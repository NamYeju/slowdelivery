package be.shop.slow_delivery.product.domain;

import be.shop.slow_delivery.common.domain.Money;
import be.shop.slow_delivery.common.domain.Quantity;
import be.shop.slow_delivery.config.JpaQueryFactoryConfig;
import be.shop.slow_delivery.product.infra.ProductJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Import({JpaQueryFactoryConfig.class, ProductJpaRepository.class})
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductRepositoryTest {
    @PersistenceContext private EntityManager em;
    @Autowired private ProductRepository productRepository;

    @Test
    void findIngredientsMap() throws Exception{
        //given
        Product product = Product.builder()
                .stockId(1L)
                .name("product A")
                .description("~~~")
                .price(new Money(10_000))
                .maxOrderQuantity(new Quantity(5))
                .build();
        em.persist(product);

        IngredientGroup groupA = new IngredientGroup("groupA", new SelectCount(1, 1));
        IngredientGroup groupB = new IngredientGroup("groupB", new SelectCount(1, 2));
        em.persist(groupA);
        em.persist(groupB);

        product.addIngredientGroup(groupA, 1);
        product.addIngredientGroup(groupB, 2);

        Ingredient ingredientA = Ingredient.builder()
                .stockId(0L)
                .name("ingredientA")
                .price(new Money(1000))
                .build();
        Ingredient ingredientB = Ingredient.builder()
                .stockId(0L)
                .name("ingredientB")
                .price(new Money(2000))
                .build();
        Ingredient ingredientC = Ingredient.builder()
                .stockId(0L)
                .name("ingredientC")
                .price(new Money(3000))
                .build();
        Ingredient notShownIngredient = Ingredient.builder()
                .stockId(0L)
                .name("nowShownIngredient")
                .price(new Money(500))
                .build();

        em.persist(ingredientA);
        em.persist(ingredientB);
        em.persist(ingredientC);
        em.persist(notShownIngredient);

        groupA.addIngredient(ingredientA, 1);
        groupB.addIngredient(ingredientB, 1);
        groupB.addIngredient(ingredientC, 2);
        groupA.addIngredient(notShownIngredient, 2);

        em.createQuery("update IngredientInGroup ig set ig.displayInfo.isDisplay = false " +
                        "where ig.ingredientGroup.id =: groupId and ig.ingredient.id =: ingredientId")
                .setParameter("groupId", groupA.getId())
                .setParameter("ingredientId", notShownIngredient.getId())
                .executeUpdate();

        em.flush();
        em.clear();

        //when
        List<Long> ingredientIds = List.of(ingredientA.getId(), ingredientB.getId(), ingredientC.getId(), notShownIngredient.getId());
        Map<IngredientGroup, List<Ingredient>> ingredientsMap =
                productRepository.findIngredientsMap(product.getId(), ingredientIds);

        //then
        assertThat(ingredientsMap.size()).isEqualTo(2);
        assertThat(ingredientsMap.get(groupA).size()).isEqualTo(1);
        assertThat(ingredientsMap.get(groupB).size()).isEqualTo(2);
    }

    @Test
    void findOptionsMap() throws Exception{
        //given
        Product product = Product.builder()
                .stockId(1L)
                .name("product A")
                .description("~~~")
                .price(new Money(10_000))
                .maxOrderQuantity(new Quantity(5))
                .build();
        em.persist(product);

        OptionGroup group = new OptionGroup("groupA", new Quantity(10));
        em.persist(group);

        em.persist(new ProductOptionGroup(product, group, 1));

        Option optionA = Option.builder()
                .stockId(2L)
                .name("optionA")
                .price(new Money(1000))
                .build();
        Option optionB = Option.builder()
                .stockId(3L)
                .name("optionB")
                .price(new Money(2000))
                .build();
        Option optionC = Option.builder()
                .stockId(4L)
                .name("optionC")
                .price(new Money(3000))
                .build();
        Option notSaleOption = Option.builder()
                .stockId(5L)
                .name("notSaleOption")
                .price(new Money(500))
                .build();
        ReflectionTestUtils.setField(notSaleOption, "isSale", false);

        em.persist(optionA);
        em.persist(optionB);
        em.persist(optionC);
        em.persist(notSaleOption);

        group.addOption(optionA, 1);
        group.addOption(optionB, 2);
        group.addOption(optionC, 3);
        group.addOption(notSaleOption, 4);

        em.flush();
        em.clear();

        //when
        List<Long> optionIds = List.of(optionA.getId(), optionB.getId(), optionC.getId(), notSaleOption.getId());
        Map<OptionGroup, List<Option>> optionsMap = productRepository.findOptionsMap(product.getId(), optionIds);

        //then
        assertThat(optionsMap.size()).isEqualTo(1);
        assertThat(optionsMap.get(group).size()).isEqualTo(3);
    }
}




























