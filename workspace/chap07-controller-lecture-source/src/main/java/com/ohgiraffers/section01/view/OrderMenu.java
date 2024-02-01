package com.ohgiraffers.section01.view;

import com.ohgiraffers.section01.controller.OrderController;
import com.ohgiraffers.section01.model.dto.CategoryDTO;
import com.ohgiraffers.section01.model.dto.MenuDTO;
import com.ohgiraffers.section01.model.dto.OrderMenuDTO;

import java.util.*;

public class OrderMenu {

    private OrderController orderController = new OrderController();

    public void displayMainMenu() {

        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        int totalOrderPrice = 0;

        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("===== 메뉴 주문 프로그램 =====");

            List<CategoryDTO> categoryList = orderController.selectAllCategory();
            for(CategoryDTO category : categoryList) {
                System.out.println(category.getCategoryName());
            }

            System.out.println("===========================");
            System.out.print("주문하실 카테고리를 선택해주세요 : ");
            String inputCategoryName = sc.nextLine();

            int categoryCode = 0;
            for(CategoryDTO category : categoryList) {
                if(category.getCategoryName().equals(inputCategoryName)) {
                    categoryCode = category.getCategoryCode();
                }
            }

            System.out.println("===== 주문 가능 메뉴 =====");
            List<MenuDTO> menuList = orderController.selectMenuByCategoryCode(categoryCode);
            for(MenuDTO menu : menuList) {
                System.out.println(menu.getMenuName() + " : " + menu.getMenuPrice() + "원");
            }

            System.out.print("주문하실 메뉴를 선택해주세요 : ");
            String inputMenuName = sc.nextLine();

            int menuCode = 0;
            int menuPrice = 0;
            for(MenuDTO menu : menuList) {
                if(menu.getMenuName().equals(inputMenuName)) {
                    menuCode = menu.getMenuCode();
                    menuPrice = menu.getMenuPrice();
                }
            }

            System.out.print("주문하실 수량을 입력하세요 : ");
            int orderAmount = sc.nextInt();

            OrderMenuDTO orderMenu = new OrderMenuDTO();
            orderMenu.setMenuCode(menuCode);
            orderMenu.setOrderAmount(orderAmount);

            orderMenuList.add(orderMenu);
            totalOrderPrice += (menuPrice * orderAmount);

            System.out.print("계속 주문하시겠습니까? (예/아니오) : ");
            sc.nextLine();

        } while(sc.nextLine().equals("예"));

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("totalOrderPrice", totalOrderPrice);
        requestMap.put("orderMenuList", orderMenuList);

        orderController.registOrder(requestMap);
    }

}