/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author thien
 */
public class Students {
    private String masv;
    private String hoten;
    private String email;
    private String sdt;
    private boolean gioiTinh;
    private String diaChi;
    private String hinh;
     private float tiengAnh;
    private float tinHoc;
    private float gdtc;
    public Students() {
    }
    // Constructor
    public Students(String masv, String hoten, String email, String sdt, boolean gioiTinh, String diaChi, String hinh,
                    float tiengAnh, float tinHoc, float gdtc) {
        this.masv = masv;
        this.hoten = hoten;
        this.email = email;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.hinh = hinh;
        this.tiengAnh = tiengAnh;
        this.tinHoc = tinHoc;
        this.gdtc = gdtc;
    }
     public float getTiengAnh() {
        return tiengAnh;
    }

    public void setTiengAnh(float tiengAnh) {
        this.tiengAnh = tiengAnh;
    }

    public float getTinHoc() {
        return tinHoc;
    }

    public void setTinHoc(float tinHoc) {
        this.tinHoc = tinHoc;
    }

    public float getGdtc() {
        return gdtc;
    }

    public void setGdtc(float gdtc) {
        this.gdtc = gdtc;
    }

    // Getters and Setters
    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
    public float tinhDiemTrungBinh() {
        // Tính tổng điểm
        float tongDiem = this.getTiengAnh() + this.getTinHoc() + this.getGdtc();
        
        // Tính điểm trung bình
        float diemTrungBinh = tongDiem / 3.0f;
        
        return diemTrungBinh;
    }
}
