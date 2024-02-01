package com.ohgiraffers.section01.transaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class Application4 {
    public static void main(String[] args) {

        /* 4. 2가지 이상의 DML이 하나의 논리적인 단위(트랜잭션)으로 묶일 경우
         * 모든 동작이 정상 수행 되었을 때 commit, 일부 동작이 비정상 수행 되었을 때는 rollback 처리 하여
         * 트랜잭션을 제어한다. */

        Connection con = getConnection();

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        int result1 = 0;
        int result2 = 0;
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query1 = prop.getProperty("insertCategory");
            String query2 = prop.getProperty("insertMenu");

            pstmt1 = con.prepareStatement(query1);
            pstmt1.setString(1, "신카테고리");
            pstmt1.setObject(2, null);

            result1 = pstmt1.executeUpdate();

            System.out.println("result1 : " + result1);

            pstmt2 = con.prepareStatement(query2);
            pstmt2.setString(1, "개구리뒷다리통조림");
            pstmt2.setInt(2, 70000);
            pstmt2.setInt(3, 0);    // 삽입 오류가 발생하도록 존재하지 않는 category code 입력
            pstmt2.setString(4, "Y");

            result2 = pstmt2.executeUpdate();

            System.out.println("result2 : " + result2);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt1);
            close(pstmt2);

            if(result1 > 0 && result2 > 0) {
                System.out.println("신규 카테고리와 메뉴 등록 성공!");
                commit(con);
            } else {
                System.out.println("신규 카테고리와 메뉴 등록 실패ㅠㅠ");
                rollback(con);
            }

            close(con);
        }

    }
}
