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
import Model.NhanVien;

/**
 *
 * @author Jason
 */
public class NhanVienDAO {
    
    //đọc 1 nhân viên từ 1 bản ghi (1 ResultSet)
    public NhanVien readFromResultSet(ResultSet rs) throws SQLException{
        NhanVien model=new NhanVien();
        model.setMaNV(rs.getString("MaNV"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setHoTen(rs.getString("HoTen"));
        model.setVaiTro(rs.getBoolean("VaiTro"));
        return model;
    }
    
    //thực hiện truy vấn lấy về 1 tập ResultSet rồi điền tập ResultSet đó vào 1 List
    public List<NhanVien> select(String sql,Object...args){
        List<NhanVien> list=new ArrayList<>();
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
     * @param entity là thực thể chứa thông tin bản ghi mới
     */
    public void insert(NhanVien entity) {
        String sql="INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro) VALUES (?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                entity.getMaNV(),
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.isVaiTro());
    }

    /**
     * Cập nhật thực thể vào CSDL
     * @param entity là thực thể chứa thông tin bản ghi cần cập nhật
     */
    public void update(NhanVien entity) {
        String sql="UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=? WHERE MaNV=?";
        JdbcHelper.executeUpdate(sql,
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.isVaiTro(),
                entity.getMaNV());
    }

    /**
     * Xóa bản ghi khỏi CSDL
     * @param maNV là mã của bản ghi cần xóa
     */
    public void delete(String maNV) {
        String sql="DELETE FROM NhanVien WHERE MaNV=?";
        JdbcHelper.executeUpdate(sql, maNV);
    }

    /**
     * Truy vấn tất cả các các thực thể
     * @return danh sách các thực thể
     */
    public List<NhanVien> select() {
        String sql="SELECT * FROM NhanVien";
        return select(sql);             //trong 1 class có thể có 2 method trùng tên (nhưng param khác nhau)
    }

    /**
     * Truy vấn thực thể theo mã id
     * @param id là mã của bản ghi được truy vấn
     * @return thực thể chứa thông tin của bản ghi
     */
    public NhanVien findById(String id) {
        String sql="SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list=select(sql, id);
        return list.size()>0?list.get(0):null;               //có thể trả về là null
    }
}
