package com.ohgiraffers.section01.problem;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application {

    public static void main(String[] args) {

        /* 1. Connection 생성 */
        Connection con = getConnection();

        /* 2. Properties 생성 */
        Properties prop = new Properties();

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rset = null;
        int result = 0;

        List<Map<Integer, String>> categoryList = null;

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query1 = prop.getProperty("selectAllCategoryList");
            String query2 = prop.getProperty("insertMenu");

            pstmt1 = con.prepareStatement(query1);
            pstmt2 = con.prepareStatement(query2);

            rset = pstmt1.executeQuery();

            categoryList = new ArrayList<>();

            while (rset.next()) {
                Map<Integer, String> category = new HashMap<>();
                category.put(rset.getInt("a.category_code"), rset.getString("a.category_name"));
                categoryList.add(category);
            }

            System.out.println("categoryList : " + categoryList);

            Scanner sc = new Scanner(System.in);
            System.out.print("등록할 메뉴의 이름을 입력하세요 : ");
            String menuName = sc.nextLine();
            System.out.print("신규 메뉴의 가격을 입력하세요 : ");
            int menuPrice = sc.nextInt();
            System.out.print("카테고리를 선택해주세요 : ");
            sc.nextLine();
            String categoryName = sc.nextLine();
            System.out.print("바로 판매 메뉴에 적용하시겠습니까?(예/아니오) : ");
            String answer = sc.nextLine();

            int categoryCode = 0;
            switch (categoryName) {
                case "한식" : categoryCode = 4; break;
                case "중식" : categoryCode = 5; break;
                case "일식" : categoryCode = 6; break;
                case "퓨전" : categoryCode = 7; break;
            }

            String orderableStatus = "";
            switch(answer) {
                case "예" : orderableStatus = "Y"; break;
                case "아니오" : orderableStatus = "N"; break;
            }

            pstmt2.setString(1, menuName);
            pstmt2.setInt(2, menuPrice);
            pstmt2.setInt(3, categoryCode);
            pstmt2.setString(4, orderableStatus);

            result = pstmt2.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt1);
            close(pstmt2);
            close(con);
        }

        if(result > 0) {
            System.out.println("메뉴 등록 성공!");
        } else {
            System.out.println("메뉴 등록 실패!");
        }

    }
}
