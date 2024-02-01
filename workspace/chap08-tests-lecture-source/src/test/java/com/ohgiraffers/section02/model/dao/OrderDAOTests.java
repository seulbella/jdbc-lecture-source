package com.ohgiraffers.section02.model.dao;

import com.ohgiraffers.section02.model.dto.CategoryDTO;
import com.ohgiraffers.section02.model.dto.OrderDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderDAOTests {

    private Connection con;
    private OrderDAO orderDAO;

    @BeforeEach
    public void setup() {
        con = getConnection();
        orderDAO = new OrderDAO();
    }

    @Test
    public void 하위_카테고리_조회_테스트() {

        List<CategoryDTO> categoryList = orderDAO.selectAllCategory(con);

        /* assert메소드명 : 테스트 성공 여부를 판별해주는 키워드 */
        assertNotNull(categoryList);
        categoryList.forEach(System.out::println);
    }

    @Test
    public void 주문_입력_테스트() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDate("24/02/01");
        orderDTO.setOrderTime("10:46:55");
        orderDTO.setTotalOrderPrice(32000);

        int result = orderDAO.insertOrder(con, orderDTO);

        assertEquals(1, result);
    }
}
