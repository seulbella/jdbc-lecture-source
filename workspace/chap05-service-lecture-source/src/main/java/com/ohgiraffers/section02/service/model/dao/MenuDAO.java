package com.ohgiraffers.section02.service.model.dao;

import com.ohgiraffers.section02.model.dto.CategoryDTO;
import com.ohgiraffers.section02.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class MenuDAO {
    private Properties prop = new Properties();

    public MenuDAO () {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 신규 카테고리 등록용 메소드 */
    public int insertNewCategory(Connection con, CategoryDTO newCategory) {

        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertCategory");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, newCategory.getCategoryName());
            pstmt.setObject(2, newCategory.getRefCategoryCode());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }

        return result;
    }
    /* 마지막 카테고리 코드 조회용 메소드 */
    public int selectLastCategoryCode(Connection con) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int newCategoryCode = 0;
        String query = prop.getProperty("selectLastCategoryCode");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            if(rset.next()) {
                newCategoryCode = rset.getInt("category_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }

        return newCategoryCode;

    }

    /* 신규 메뉴 등록용 메소드 */
    public int insertNewMenu(Connection con, MenuDTO newMenu) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertMenu");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, newMenu.getMenuName());
            pstmt.setInt(2, newMenu.getMenuPrice());
            pstmt.setInt(3, newMenu.getCategoryCode());
            pstmt.setString(4, newMenu.getOrderableStatus());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }

        return result;

    }
}
