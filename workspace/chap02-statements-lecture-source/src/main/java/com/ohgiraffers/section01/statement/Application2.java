package com.ohgiraffers.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application2 {

    public static void main(String[] args) {

        /* 1. Connection 생성 */
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;

        try {
            /* 2. Connection의 createStatement()를 이용한 Statement 인스턴스 생성 */
            stmt = con.createStatement();

            Scanner sc = new Scanner(System.in);
            System.out.print("조회하려는 사번을 입력해주세요 : ");
            String empId = sc.nextLine();
            String query = "SELECT emp_id, emp_name FROM employee WHERE emp_id = '" + empId + "'";

            /* 3. Statement의 executeQuery(sql)로 쿼리문 실행하고 결과를 ResultSet으로 반환 받음 */
            rset = stmt.executeQuery(query);

            /* 4. ResultSet에 담긴 값을 컬럼명을 이용해 꺼내어 출력 */
            if(rset.next()) {
                System.out.println(rset.getString("emp_id") + ", " + rset.getString("emp_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            /* 5. 사용한 자원 반납 */
            close(rset);
            close(stmt);
            close(con);
        }

    }

}
