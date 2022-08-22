package be.shop.slow_delivery.menu.presentation;

import be.shop.slow_delivery.menu.application.MenuService;
import be.shop.slow_delivery.menu.application.dto.request.MenuUpdateRequestDto;
import be.shop.slow_delivery.menu.application.dto.response.MenuListResponseDto;
import be.shop.slow_delivery.menu.presentation.dto.MenuDtoMapper;
import be.shop.slow_delivery.menu.presentation.dto.MenuFormDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuService menuService;
    private final MenuDtoMapper mapper;

    @ApiOperation(value = "메뉴 목록 보기")
    @GetMapping("/shop/{shopId}/menus")
    public MenuListResponseDto getMenuList(@PathVariable("shopId") long shopId){
        return menuService.findShopMenuList(shopId);
    }

    @ApiOperation(value = "메뉴 등록")
    @PostMapping("/shop/{shopId}/menu/")
    public Long createMenu(@RequestBody @Valid MenuFormDto menuForm,
                           @PathVariable("shopId") long shopId){
        return menuService.createMenu(mapper.toCreateRequestDto(menuForm),shopId);
    }

    @ApiOperation(value = "메뉴 수정")
    @PutMapping("/shop/{shopId}/menu/{menuId}")
    public void updateMenu(@RequestBody @Valid MenuUpdateRequestDto menuUpdateRequestDto,
                           @PathVariable("menuId") long menuId){
        menuService.updateMenu(menuId,menuUpdateRequestDto);
    }

    @ApiOperation(value = "메뉴 삭제")
    @DeleteMapping("/shop/{shopId}/menu/{menuId}")
    public void deleteMenu(@PathVariable("menuId") long menuId){
        menuService.deleteMenu(menuId);
    }


}
