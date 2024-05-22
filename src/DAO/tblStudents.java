/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.Students;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thien
 */
public class tblStudents {
   public static int LayDSSinhVien(Vector<Students> dsStudent)
   {
      Connection conn = DAO.Database.KetNoiCSDL();
    if (conn == null) {
        return -1;
    }

    String sql = "SELECT s.MASV, s.HOTEN, s.EMAIL, s.SDT, s.GIOITINH, s.DIACHI, s.HINH, g.TIENGANH, g.TINHOC, g.GDTC "
               + "FROM STUDENTS s LEFT JOIN GRADE g ON s.MASV = g.MASV";
    try {
        PreparedStatement stm = conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            Students st = new Students();
            st.setMasv(rs.getString("MASV"));
            st.setHoten(rs.getString("HOTEN"));
            st.setEmail(rs.getString("EMAIL"));
            st.setSdt(rs.getString("SDT"));
            st.setGioiTinh(rs.getBoolean("GIOITINH"));
            st.setDiaChi(rs.getString("DIACHI"));
            st.setHinh(rs.getString("HINH"));
            st.setTiengAnh(rs.getFloat("TIENGANH"));
            st.setTinHoc(rs.getFloat("TINHOC"));
            st.setGdtc(rs.getFloat("GDTC"));

            dsStudent.add(st);
        }

        conn.close();
        return dsStudent.size();
    } catch (SQLException ex) {
        Logger.getLogger(tblStudents.class.getName()).log(Level.SEVERE, null, ex);
        return -2;
    }
}
  public static int ThemSinhVien(String MaSV, String HoTen, float tienganh, float tinhoc, float gdtc) {
    Connection conn = DAO.Database.KetNoiCSDL();
    if (conn == null) {
        return -1; // Lỗi kết nối CSDL
    }

    String sqlSinhVien = "INSERT INTO STUDENTS (MASV, HOTEN) VALUES (?, ?)";
    String sqlDiem = "INSERT INTO GRADE (MASV, TIENGANH, TINHOC, GDTC) VALUES (?, ?, ?, ?)";
    
    try {
        // Thêm sinh viên
        PreparedStatement stmSinhVien = conn.prepareStatement(sqlSinhVien, Statement.RETURN_GENERATED_KEYS);
        stmSinhVien.setString(1, MaSV);
        stmSinhVien.setString(2, HoTen);
        int rowsAffected = stmSinhVien.executeUpdate();
        
        // Nếu việc thêm sinh viên thành công, lấy ID sinh viên và thêm điểm
        if (rowsAffected > 0) {
            ResultSet generatedKeys = stmSinhVien.getGeneratedKeys();
            int studentID = -1; // Initialize studentID
            if (generatedKeys.next()) {
                studentID = generatedKeys.getInt(1);
            }

            // Thêm điểm
            PreparedStatement stmDiem = conn.prepareStatement(sqlDiem);
            stmDiem.setString(1, MaSV);
            stmDiem.setFloat(2, tienganh);
            stmDiem.setFloat(3, tinhoc);
            stmDiem.setFloat(4, gdtc);
            int diemRowsAffected = stmDiem.executeUpdate();

            // Check if both inserts were successful
            if (rowsAffected > 0 && diemRowsAffected > 0) {
                conn.commit(); // Commit the transaction
                return rowsAffected;
            } else {
                conn.rollback(); // Rollback the transaction if either insert failed
                return -2; // Return appropriate error code
            }
        } else {
            conn.rollback(); // Rollback the transaction if student insert failed
            return -2; // Return appropriate error code
        }
    } catch (SQLException ex) {
        Logger.getLogger(tblStudents.class.getName()).log(Level.SEVERE, null, ex);
        return -2; // Lỗi SQL
    } finally {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(tblStudents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


   public static int XoaSinhVien(String masv) {
    Connection conn = DAO.Database.KetNoiCSDL();
    if (conn == null) {
        return -1; // Lỗi kết nối CSDL
    }

    String sqlDeleteGrade = "DELETE FROM GRADE WHERE MASV = ?";
    String sqlDeleteStudent = "DELETE FROM STUDENTS WHERE MASV = ?";

    try {
        conn.setAutoCommit(false); // Start transaction

        // Delete from GRADE
        PreparedStatement stmGrade = conn.prepareStatement(sqlDeleteGrade);
        stmGrade.setString(1, masv);
        stmGrade.executeUpdate();

        // Delete from STUDENTS
        PreparedStatement stmStudent = conn.prepareStatement(sqlDeleteStudent);
        stmStudent.setString(1, masv);
        int rowsAffected = stmStudent.executeUpdate();

        conn.commit(); // Commit transaction
        conn.close();
        return rowsAffected;
    } catch (SQLException ex) {
        try {
            if (conn != null) {
                conn.rollback(); // Rollback transaction on error
            }
        } catch (SQLException rollbackEx) {
            Logger.getLogger(tblStudents.class.getName()).log(Level.SEVERE, null, rollbackEx);
        }
        Logger.getLogger(tblStudents.class.getName()).log(Level.SEVERE, null, ex);
        return -2; // Lỗi SQL
    }
}
   public static int SuaSinhVien(String maSV, String hoTen, float tiengAnh, float tinHoc, float gdtc) {
    Connection conn = DAO.Database.KetNoiCSDL();
    if (conn == null) {
        return -1; // Lỗi kết nối CSDL
    }

    String sqlUpdateStudent = "UPDATE STUDENTS SET HOTEN = ? WHERE MASV = ?";
    String sqlUpdateGrade = "UPDATE GRADE SET TIENGANH = ?, TINHOC = ?, GDTC = ? WHERE MASV = ?";
    
    try {
        conn.setAutoCommit(false); // Start transaction

        // Update STUDENTS table
        PreparedStatement stmStudent = conn.prepareStatement(sqlUpdateStudent);
        stmStudent.setString(1, hoTen);
        stmStudent.setString(2, maSV);
        int rowsAffectedStudent = stmStudent.executeUpdate();

        // Update GRADE table
        PreparedStatement stmGrade = conn.prepareStatement(sqlUpdateGrade);
        stmGrade.setFloat(1, tiengAnh);
        stmGrade.setFloat(2, tinHoc);
        stmGrade.setFloat(3, gdtc);
        stmGrade.setString(4, maSV);
        int rowsAffectedGrade = stmGrade.executeUpdate();

        conn.commit(); // Commit transaction
        conn.close();
        return (rowsAffectedStudent > 0 && rowsAffectedGrade > 0) ? 1 : 0;
    } catch (SQLException ex) {
        try {
            if (conn != null) {
                conn.rollback(); // Rollback transaction on error
            }
        } catch (SQLException rollbackEx) {
            Logger.getLogger(tblStudents.class.getName()).log(Level.SEVERE, null, rollbackEx);
        }
        Logger.getLogger(tblStudents.class.getName()).log(Level.SEVERE, null, ex);
        return -2; // Lỗi SQL
    }
}
   public static Students TimSinhVienTheoMASV(String masv) {
    Connection conn = DAO.Database.KetNoiCSDL();
    if (conn == null) {
        return null; // Lỗi kết nối CSDL
    }

    String sql = "SELECT s.MASV, s.HOTEN, g.TIENGANH, g.TINHOC, g.GDTC " +
                 "FROM STUDENTS s LEFT JOIN GRADE g ON s.MASV = g.MASV " +
                 "WHERE s.MASV = ?";
    try {
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, masv);

        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            Students student = new Students();
            student.setMasv(rs.getString("MASV"));
            student.setHoten(rs.getString("HOTEN"));
            student.setTiengAnh(rs.getFloat("TIENGANH"));
            student.setTinHoc(rs.getFloat("TINHOC"));
            student.setGdtc(rs.getFloat("GDTC"));
            conn.close();
            return student;
        }
        conn.close();
    } catch (SQLException ex) {
        Logger.getLogger(tblStudents.class.getName()).log(Level.SEVERE, null, ex);
        return null; // Lỗi SQL
    }
    return null;
}
   public static Vector<Students> LayTop3SinhVienDiemTBCaoNhat() {
        Vector<Students> topStudents = new Vector<>();

        Connection conn = DAO.Database.KetNoiCSDL();
        if (conn == null) {
            return topStudents; // Return empty vector on database connection error
        }

        String sql = "SELECT TOP 3 s.MASV, s.HOTEN, g.TIENGANH, g.TINHOC, g.GDTC "
                + "FROM STUDENTS s LEFT JOIN GRADE g ON s.MASV = g.MASV "
                + "ORDER BY (g.TIENGANH + g.TINHOC + g.GDTC) DESC";

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Students student = new Students();
                student.setMasv(rs.getString("MASV"));
                student.setHoten(rs.getString("HOTEN"));
                student.setTiengAnh(rs.getFloat("TIENGANH"));
                student.setTinHoc(rs.getFloat("TINHOC"));
                student.setGdtc(rs.getFloat("GDTC"));

                topStudents.add(student);
            }

            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(tblStudents.class.getName()).log(Level.SEVERE, null, ex);
        }

        return topStudents;
    }


}
