package com.ohgiraffers.section01.statement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

/* EmployeeDTO 형태로 객체 생성하여 데이터 조회하기 */
public class Application3 {

    public static void main(String[] args) {

        /* 1. Connection 생성 */
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;
        EmployeeDTO selectedEmp = null;

        try {
            /* 2. Connection의 createStatement()를 이용한 Statement 인스턴스 생성 */
            stmt = con.createStatement();

            Scanner sc = new Scanner(System.in);
            System.out.print("조회하려는 사번을 입력해주세요 : ");
            String empId = sc.nextLine();
            /* 조회 구문에 모든 컬럼이 있도록 처리 (SELECT * FROM) */
            String query = "SELECT * FROM employee WHERE emp_id = '" + empId + "'";

            /* 3. Statement의 executeQuery(sql)로 쿼리문 실행하고 결과를 ResultSet으로 반환 받음 */
            rset = stmt.executeQuery(query);

            /* 4. ResultSet에 담긴 값을 EmployeeDTO 타입의 객체에 설정 */
            if(rset.next()) {
                selectedEmp = new EmployeeDTO();

                selectedEmp.setEmpId(rset.getString("emp_id"));
                selectedEmp.setEmpName(rset.getString("emp_name"));
                selectedEmp.setEmpNo(rset.getString("emp_no"));
                selectedEmp.setEmail(rset.getString("email"));
                selectedEmp.setPhone(rset.getString("phone"));
                selectedEmp.setDeptCode(rset.getString("dept_code"));
                selectedEmp.setJobCode(rset.getString("job_code"));
                selectedEmp.setSalLevel(rset.getString("sal_level"));
                selectedEmp.setSalary(rset.getInt("salary"));
                selectedEmp.setBonus(rset.getDouble("bonus"));
                selectedEmp.setManagerId(rset.getString("manager_id"));
                selectedEmp.setHireDate(rset.getDate("hire_date"));
                selectedEmp.setEntDate(rset.getDate("ent_date"));
                selectedEmp.setEntYn(rset.getString("ent_yn"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            /* 5. 사용한 자원 반납 */
            close(rset);
            close(stmt);
            close(con);
        }

        System.out.println("selectedEmp : " + selectedEmp);

    }
}
