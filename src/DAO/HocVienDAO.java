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
import Model.NguoiHoc;

/**
 *
 * @author Jason
 */
public class HocVienDAO {

    //đọc 1 nhân viên từ 1 bản ghi (1 ResultSet)
    public NguoiHoc readFromResultSet(ResultSet rs) throws SQLException {
        NguoiHoc model = new NguoiHoc();
        model.setMaHV(rs.getInt("MaHV"));
        model.setMaKH(rs.getInt("KH"));
        model.setMaNH(rs.getString("MaNH"));
        model.setDiem(rs.getDouble("Diem"));
        return model;
    }

    //thực hiện truy vấn lấy về 1 tập ResultSet rồi điền tập ResultSet đó vào 1 List
    public List<NguoiHoc> select(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    list.add(readFromResultSet(rs));
                }
            } finally {
                rs.getStatement().getConnection().close();      //đóng kết nối từ resultSet
            }
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
        return list;
    }

    public void insert(NguoiHoc model) {
        String sql = "INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                model.getMaNH(),
                model.getDiem());
    }

    public void update(NguoiHoc model) {
        String sql = "UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                model.getMaNH(),
                model.getDiem(),
                model.getMaHV());
    }

    public void delete(Integer MaHV) {
        String sql = "DELETE FROM HocVien WHERE MaHV=?";
        JdbcHelper.executeUpdate(sql, MaHV);
    }

    public List<NguoiHoc> select() {
        String sql = "SELECT * FROM HocVien";
        return select(sql);             //trong 1 class có thể có 2 method trùng tên (nhưng param khác nhau)
    }

    public NguoiHoc findById(String id) {
        String sql = "SELECT * FROM HocVien WHERE MaHV=?";
        List<NguoiHoc> list = select(sql, id);
        return list.size() > 0 ? list.get(0) : null;               //có thể trả về là null
    }
}
