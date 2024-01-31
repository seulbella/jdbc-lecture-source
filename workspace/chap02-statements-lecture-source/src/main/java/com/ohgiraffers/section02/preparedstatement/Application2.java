package com.ohgiraffers.section02.preparedstatement;

import java.sql.*;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application2 {

    public static void main(String[] args) {

        /* 1. Connection 생성 */
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;


        Scanner sc = new Scanner(System.in);
        System.out.print("조회하려는 사번을 입력해주세요 : ");
        String empId = sc.nextLine();
        /* ?: 위치홀더 (자리를 잡아주는 것) */
        String query = "SELECT emp_id, emp_name FROM employee WHERE emp_id = ?";

        try {
            /* 2. Connection의 createStatement()를 이용한 Statement 인스턴스 생성 */
            pstmt = con.prepareStatement(query);

            /* 3. sql문의 위치홀더를 설정 */
            pstmt.setString(1, empId);

            /* 4. PreparedStatement의 executeQuery(sql)로 쿼리문 실행하고 결과를 ResultSet으로 반환 받음 */
            rset = pstmt.executeQuery();

            /* 5. ResultSet에 담긴 값을 컬럼명을 이용해 꺼내어 출력 */
            if(rset.next()) {
                System.out.println(rset.getString("emp_id") + ", " + rset.getString("emp_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            /* 6. 사용한 자원 반납 */
            close(rset);
            close(pstmt);
            close(con);
        }

    }
}
