package com.ohgiraffers.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {

        /* 1. DB와 연결 한다.*/
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;

        try {
            /* 2. Connection 객체를 이용하여 Statement 인스턴스 생성 */
            stmt = con.createStatement();

            /* 3. Statement 객체를 이용해 쿼리 구문을 저장하고 실행할 수 있다.
            * 4. (만약 조회라면) ResultSet 객체를 이용해 select의 결과 집합을 저장할 수 있다. */
            rset = stmt.executeQuery("SELECT emp_id, emp_name FROM employee");

            while (rset.next()) {
                /* next() : ResultSet의 커서 위치를 하나 내리면서 행이 존재하면 true, 존재하지 않으면 false를 반환 */
                /* getXXX(컬럼명) : 커서가 가리키는 행의 컬럼을 XXX  데이터 타입으로 반환한다. */
                System.out.println(rset.getString("emp_id") + ", " + rset.getString("emp_name"));
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
