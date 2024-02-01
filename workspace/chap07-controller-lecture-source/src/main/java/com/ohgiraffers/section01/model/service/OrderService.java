package com.ohgiraffers.section01.model.service;

import com.ohgiraffers.section01.model.dao.OrderDAO;
import com.ohgiraffers.section01.model.dto.CategoryDTO;
import com.ohgiraffers.section01.model.dto.MenuDTO;
import com.ohgiraffers.section01.model.dto.OrderDTO;
import com.ohgiraffers.section01.model.dto.OrderMenuDTO;

import java.sql.Connection;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class OrderService {

    private OrderDAO orderDAO = new OrderDAO();

    public List<CategoryDTO> selectAllCategory() {
        /* 1. Connection 생성 */
        Connection con = getConnection();

        /* 2. DAO 필요한 메소드 호출 */
        List<CategoryDTO> categoryList = orderDAO.selectAllCategory(con);

        /* 3. Connection 반납 */
        close(con);

        /* 4. 값 리턴 */
        return categoryList;
    }

    public List<MenuDTO> selectMenuByCategoryCode(int categoryCode) {
        /* 1. Connection 생성 */
        Connection con = getConnection();

        /* 2. DAO 필요한 메소드 호출 */
        List<MenuDTO> menuList = orderDAO.selectMenuByCategoryCode(con, categoryCode);

        /* 3. Connection 반납 */
        close(con);

        /* 4. 값 리턴 */
        return menuList;
    }

    public int registOrder(OrderDTO order) {

        /* 1. Connection 생성 */
        Connection con = getConnection();

        /* 2. DAO 필요한 메소드 호출 */
        /* 2-1. Order 테이블에 insert */
        int orderResult = orderDAO.insertOrder(con, order);

        /* 2-2. order_code 값 조회 */
        int orderCode = orderDAO.selectLastOrderCode(con);

        /* 2-3. order menu 테이블에 insert */
        int orderMenuResult = 0;
        List<OrderMenuDTO> orderMenuList = order.getOrderMenuList();
        for(OrderMenuDTO orderMenu : orderMenuList) {
            orderMenu.setOrderCode(orderCode);
            orderMenuResult += orderDAO.insertOrderMenu(con, orderMenu);
        }

        /* 3. 성공 여부 판단 후 트랜잭션 처리 */
        int result = 0;
        if(orderResult > 0 && orderMenuResult == orderMenuList.size()) {
            commit(con);
            result = 1;
        } else {
            rollback(con);
        }

        /* 4. Connection 반납 */
        close(con);

        /* 5. 결과 값 반환 */
        return result;
    }
}