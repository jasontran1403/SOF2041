/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.KhoaHoc;

/**
 *
 * @author Jason
 */
public class KhoaHocDAO {
    
    //đọc 1 nhân viên từ 1 bản ghi (1 ResultSet)
    public KhoaHoc readFromResultSet(ResultSet rs) throws SQLException{
        KhoaHoc model=new KhoaHoc();
         model.setMaKH(rs.getInt("MaKH"));
         model.setHocPhi(rs.getDouble("HocPhi"));
         model.setThoiLuong(rs.getInt("ThoiLuong"));
         model.setNgayKG(rs.getDate("NgayKG"));
         model.setGhiChu(rs.getString("GhiChu"));
         model.setMaNV(rs.getString("MaNV"));
         model.setNgayTao(rs.getDate("NgayTao"));
         model.setMaCD(rs.getString("MaCD"));
         return model;
    }
    
    //thực hiện truy vấn lấy về 1 tập ResultSet rồi điền tập ResultSet đó vào 1 List
    public List<KhoaHoc> select(String sql,Object...args){
        List<KhoaHoc> list=new ArrayList<>();
        try {
            ResultSet rs=null;
            try{
                rs=JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    list.add(readFromResultSet(rs));
                }
            }finally{
                rs.getStatement().getConnection().close();      //đóng kết nối từ resultSet
            }
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
        return list;
    }
    
    /**
     * Thêm mới thực thể vào CSDL
     * @param model
     */
    public void insert(KhoaHoc model) {
        String sql="INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaCD(),
                 model.getHocPhi(),
                 model.getThoiLuong(),
                 model.getNgayKG(),
                 model.getGhiChu(),
                 model.getMaNV());
    }

    /**
     * Cập nhật thực thể vào CSDL
     * @param model
     */
    public void update(KhoaHoc model) {
        String sql="UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=? WHERE MaKH=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaCD(),
                 model.getHocPhi(),
                 model.getThoiLuong(),
                 model.getNgayKG(),
                 model.getGhiChu(),
                 model.getMaNV(),
                 model.getMaKH());
    }

    /**
     * Xóa bản ghi khỏi CSDL
     * @param MaKH
     */
     public void delete(Integer MaKH){
     String sql="DELETE FROM KhoaHoc WHERE MaKH=?";
     JdbcHelper.executeUpdate(sql, MaKH);
     }

    /**
     * Truy vấn tất cả các các thực thể
     * @return danh sách các thực thể
     */
    public List<KhoaHoc> select() {
        String sql="SELECT * FROM KhoaHoc";
        return select(sql);             //trong 1 class có thể có 2 method trùng tên (nhưng param khác nhau)
    }

     public KhoaHoc findById(Integer makh){
     String sql="SELECT * FROM KhoaHoc WHERE MaKH=?";
     List<KhoaHoc> list = select(sql, makh);
     return list.size() > 0 ? list.get(0) : null;
     }    
}
