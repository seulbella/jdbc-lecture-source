package com.ohgiraffers.section03.sqlinjection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

/* 보안에 취약한 기존의 SQL 인젝션 */
public class Application1 {

    /* 일치하는 정보를 입력하면 성공하고 불일치 하는 정보를 입력하면 실패하는 것이 정상 수행 흐름 */
//    private static String empId = "200";
//    private static String empName = "홍길동"; //회원 정보가 없습니다.

    /* 조건절에 들어갈 내용을 예상하여 OR 1=1을 삽입하여 일부 정보만 하는 회원의 정보를 얻어올 수 있음 */
    private static String empId = "200";
    private static String empName = "' OR 1=1 AND emp_id = '200";

    public static void main(String[] args) {
        String query = "SELECT * FROM employee WHERE emp_id = '" + empId + "' AND emp_name = '" + empName + "'";
        System.out.println(query);

        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            if(rset.next()) {
                System.out.println(rset.getString("emp_name") + "님 환영합니다.");
            } else {
                System.out.println("회원 정보가 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
            close(con);
        }
    }
}
