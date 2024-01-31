package com.ohgiraffers.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application1 {
    public static void main(String[] args) {

        Connection con = null;

        try {
            /* 사용할 드라이버 등록
            * 입력한 클래스명이 올바르지 않을 경우 문제가 생기므로 ClassNotFoundException 핸들링 */
            Class.forName("com.mysql.cj.jdbc.Driver");

            /* DriverManager 이용해 Connection 생성
            * 접속 url, 계정 정보를 전달하여 Connection 객체를 만들어서 반환 받는다.
            * 접속 정보가 틀리면 연결할 수 없으므로 SQLException이 발생할 수 있다. */
            con = DriverManager.getConnection("jdbc:mysql://localhost/employee","ohgiraffers","ohgiraffers");

            System.out.println("con : " + con);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
