package com.ohgiraffers.section01.view;

import com.ohgiraffers.section01.model.dto.CategoryDTO;
import com.ohgiraffers.section01.model.dto.MenuDTO;
import com.ohgiraffers.section01.model.dto.OrderDTO;
import com.ohgiraffers.section01.model.dto.OrderMenuDTO;
import com.ohgiraffers.section01.model.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OrderMenu {

    private OrderService orderService = new OrderService();

    public void displayMainMenu() {
        /* --------- 반복 ----------
        1. 카테고리 조회
        2. 카테고리 선택하면 해당 카테고리의 메뉴 조회
        3. 주문할 메뉴와 수량 입력 받기
        ---------- 반복 -------------
        4. 주문하기
        * */

        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        int totalOrderPrice = 0;

        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("===== 메뉴 주문 프로그램 =====");

            List<CategoryDTO> categoryList = orderService.selectAllCategory();

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
            List<MenuDTO> menuList = orderService.selectMenuByCategoryCode(categoryCode);
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

            System.out.print("주문하실 수량을 입력해주세요 : ");
            int orderAmount = sc.nextInt();

            OrderMenuDTO orderMenu = new OrderMenuDTO();
            orderMenu.setMenuCode(menuCode);
            orderMenu.setOrderAmount(orderAmount);

            orderMenuList.add(orderMenu);
            totalOrderPrice += (menuPrice * orderAmount);

            System.out.print("계속 주문하시겠습니까? (예/아니오) : ");
            sc.nextLine();

        } while(sc.nextLine().equals("예"));

        System.out.println("===== 주문 리스트 =====");
        for(OrderMenuDTO orderMenu : orderMenuList) {
            System.out.println(orderMenu);
        }

        Date orderTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(orderTime);
        String time = timeFormat.format(orderTime);

        OrderDTO order = new OrderDTO();
        order.setOrderDate(date);
        order.setOrderTime(time);
        order.setTotalOrderPrice(totalOrderPrice);
        order.setOrderMenuList(orderMenuList);

        int result = orderService.registOrder(order);

        if(result > 0) {
            System.out.println("주문이 완료 되었습니다.");
        } else {
            System.out.println("주문이 실패하였습니다.");
        }
    }













}
