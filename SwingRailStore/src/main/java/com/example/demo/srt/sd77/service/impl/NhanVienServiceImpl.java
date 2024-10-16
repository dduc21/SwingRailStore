package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.ChucVu;
import com.example.demo.srt.sd77.entity.NhanVien;
import com.example.demo.srt.sd77.entity.request.EmployeeAddRequest;
import com.example.demo.srt.sd77.repository.IChucVuRepository;
import com.example.demo.srt.sd77.repository.INhanVienRepository;
import com.example.demo.srt.sd77.service.INhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NhanVienServiceImpl implements INhanVienService {

    @Autowired
    private INhanVienRepository nhanVienRepo;

    @Autowired
    private IChucVuRepository chucVuRepo;
    @Autowired
    private ChucVuServiceImpl chucVuService;

    @Override
    public Page<NhanVien> getEmployeesWithPanigation(int pageNo, int pageSize, String key, String trangThai) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return nhanVienRepo.findEmployees(pageable,
                "%" + trangThai + "%",
                "%" + key + "%");
    }

    @Override
    public ArrayList<NhanVien> getAllCustomers() {
        return (ArrayList<NhanVien>) nhanVienRepo.findAll();
    }

    @Override
    public NhanVien getEmployeeByID(Long id) {
        return nhanVienRepo.findEmployeeByID(id);
    }

    @Override
    public NhanVien addEmployee(EmployeeAddRequest req) {

        if (nhanVienRepo.findNhanVienByEmail(req.getEmail()) != null) {
            throw new RuntimeException("Email đã tồn tại");
        } else {
            NhanVien employee = new NhanVien();

            employee.setCccd(req.getCccd());
            employee.setDiaChi(req.getDiaChi());
            employee.setEmail(req.getEmail());
            employee.setGioiTinh(req.getGioiTinh());
            employee.setMa(generateCode());
            employee.setMaPhuong(req.getMaPhuong());
            employee.setMaTinh(req.getMaTinh());
            employee.setMaXa(req.getMaXa());
            employee.setXa(req.getXa());
            employee.setPhuong(req.getPhuong());
            employee.setTinh(req.getTinh());
            employee.setNgaySinh(req.getNgaySinh());
            employee.setSoDienThoai(req.getSoDienThoai());
            employee.setTrangThai(true);
            employee.setTen(req.getTen());
            employee.setAvatar(req.getAvatar());
            employee.setMatKhau("123");
//            employee.setChucVu(chucVuService.findById(req.getChucVu()));
            if (req.getChucVu() == 0) {
                if (chucVuRepo.findByMa("0") != null) {
                    employee.setChucVu(chucVuRepo.findByMa("0"));
                } else {
                    ChucVu chucVu = new ChucVu();
                    chucVu.setMa("0");
                    chucVu.setTen("Nhân viên");
                    chucVuRepo.save(chucVu);
                    employee.setChucVu(chucVu);
                }
            } else if (req.getChucVu() == 1) {
                if (chucVuRepo.findByMa("1") != null) {
                    employee.setChucVu(chucVuRepo.findByMa("1"));
                } else {
                    ChucVu chucVu = new ChucVu();
                    chucVu.setMa("1");
                    chucVu.setTen("Quản trị viên");
                    chucVuRepo.save(chucVu);
                    employee.setChucVu(chucVu);
                }
            }

            return nhanVienRepo.save(employee);
        }
    }

    @Override
    public String generateCode() {
        // generate code
        String newestCode = nhanVienRepo.generateNewestCode();
        if (newestCode == null || newestCode.equals("")) {
            return "EMPLOYEE_" + 0;
        }
        return "EMPLOYEE_" + (Integer.parseInt(newestCode.substring(9)) + 1);
    }

    @Override
    public NhanVien updateEmployee(EmployeeAddRequest req) {
        NhanVien employee = new NhanVien();

        employee.setCccd(req.getCccd());
        employee.setDiaChi(req.getDiaChi());
        employee.setEmail(req.getEmail());
        employee.setGioiTinh(req.getGioiTinh());
        employee.setMa(req.getMa());
        employee.setMaPhuong(req.getMaPhuong());
        employee.setMaTinh(req.getMaTinh());
        employee.setMaXa(req.getMaXa());
        employee.setXa(req.getXa());
        employee.setPhuong(req.getPhuong());
        employee.setTinh(req.getTinh());
        employee.setNgaySinh(req.getNgaySinh());
        employee.setSoDienThoai(req.getSoDienThoai());
        employee.setTrangThai(true);
        employee.setTen(req.getTen());
        employee.setAvatar(req.getAvatar());
        employee.setId(req.getId());
        employee.setMatKhau(req.getMatKhau());

        if (req.getChucVu() == 0) {
            if (chucVuRepo.findByMa("0") != null) {
                employee.setChucVu(chucVuRepo.findByMa("0"));
            } else {
                ChucVu chucVu = new ChucVu();
                chucVu.setMa("0");
                chucVu.setTen("Nhân viên");
                chucVuRepo.save(chucVu);
                employee.setChucVu(chucVu);
            }
        } else if (req.getChucVu() == 1) {
            if (chucVuRepo.findByMa("1") != null) {
                employee.setChucVu(chucVuRepo.findByMa("1"));
            } else {
                ChucVu chucVu = new ChucVu();
                chucVu.setMa("1");
                chucVu.setTen("Quản trị viên");
                chucVuRepo.save(chucVu);
                employee.setChucVu(chucVu);
            }
        }

        return nhanVienRepo.save(employee);
    }

    @Override
    public NhanVien login(String email, String matKhau) {
        NhanVien nv = nhanVienRepo.findNhanVienByEmailAndPass(email, matKhau);

        if (nv == null) {
            if (email.equals("admin@gmail.com") && matKhau.equals("admin")) {
                NhanVien employee = new NhanVien();
                employee.setEmail("admin@gmail.com");
                employee.setMatKhau("admin");
                employee.setMa(generateCode());
                employee.setTen("Admin");
                employee.setTrangThai(true);
                if (chucVuRepo.findByMa("1") != null) {
                    employee.setChucVu(chucVuRepo.findByMa("1"));
                } else {
                    ChucVu chucVu = new ChucVu();
                    chucVu.setMa("1");
                    chucVu.setTen("Quản trị viên");
                    chucVuRepo.save(chucVu);
                    employee.setChucVu(chucVu);
                }
                return nhanVienRepo.save(employee);
            } else {
                throw new RuntimeException("Tài khoản hoặc mật khẩu không đúng");
            }
        }
        return nv;
    }

    @Override
    public String changePass(String oldPass, String newPass, Long id) {
        NhanVien employee = nhanVienRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        if (employee.getMatKhau().equals(oldPass)) {
            employee.setMatKhau(newPass);
            nhanVienRepo.save(employee);
            return "Đổi mật khẩu thành công";
        } else {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }

    }

}
