package com.example.demo.srt.sd77.entity;

//import com.tsh.sd43.entity.base.PrimaryEntity;

import com.example.demo.srt.sd77.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "hoa_don")
public class HoaDon extends PrimaryEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien idNhanVien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang idKhachHang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_voucher")
    private Voucher idVoucher;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ngay_xac_nhan")
    private Date ngayXacNhan;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_cap_nhat")
    private Date ngayCapNhat;

    @Column(name = "ten_nguoi_nhan")
    private String tenNguoiNhan;

    @Column(name = "sdt_nguoi_nhan")
    private String sdtNguoiNhan;

    @Column(name = "ngay_van_chuyen")
    private Date ngayVanChuyen;

    @Column(name = "ngay_hoan_thanh")
    private Date ngayHoanThanh;

    @Column(name = "phi_van_chuyen")
    private BigDecimal phiVanChuyen;

    @Column(name = "tong_tien")
    private BigDecimal tongTien;

    @Column(name = "so_phan_tram_khuyen_mai")
    private Integer soPhanTramKhuyenMai;

    @Column(name = "tong_tien_sau_giam")
    private BigDecimal tongTienSauGiam;

    @Column(name = "phuong_thuc_thanh_toan")
    private Integer phuongThucThanhToan;

    @Column(name = "loai_hoa_don")
    private Integer loaiHoaDon;

    @Column(name = "email")
    private String email;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "so_tien_khach_dua")
    private BigDecimal soTienKhachDua;

    @Column(name = "xa")
    @Nationalized
    private String xa;

    @Column(name = "phuong")
    @Nationalized
    private String phuong;

    @Column(name = "tinh")
    @Nationalized
    private String tinh;

    @Column(name = "dia_chi")
    @Nationalized
    private String diaChi;

    @Column(name = "ma_xa")
    private String maXa;

    @Column(name = "ma_phuong")
    private String maPhuong;

    @Column(name = "ma_tinh")
    private String maTinh;

}
