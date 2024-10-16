package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.GioHang;
import com.example.demo.srt.sd77.entity.GioHangChiTiet;
import com.example.demo.srt.sd77.entity.request.CartDetailRequest;
import com.example.demo.srt.sd77.repository.IGioHangChiTietRepository;
import com.example.demo.srt.sd77.repository.IGioHangRepository;
import com.example.demo.srt.sd77.repository.IKhachHangRepository;
import com.example.demo.srt.sd77.service.IGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GioHangServiceImpl implements IGioHangService {

    @Autowired
    private IGioHangRepository gioHangRepo;

    @Autowired
    private IGioHangChiTietRepository gioHangChiTietRepo;

    @Autowired
    private IKhachHangRepository khachHangRepo;

    @Override
    public GioHang getCartByIdCustomer(Long id) {
        GioHang cart = new GioHang();
        if (id == -1) {
            if (gioHangRepo.getGioHangKhongDangNhap().size() != 0) {
                cart = gioHangRepo.getGioHangKhongDangNhap().get(0);
            } else {
                return gioHangRepo.save(cart);
            }
        } else {
            if (gioHangRepo.getCartByIdKhachHang(id).size() != 0) {
                cart = gioHangRepo.getCartByIdKhachHang(id).get(0);
            } else {
                cart.setIdKhachHang(khachHangRepo.findById(id).get());
                return gioHangRepo.save(cart);
            }
        }
        return cart;
    }

    @Override
    public Long getQuantityOfCartDetailByIdCustomer(Long id) {
        GioHang cart = getCartByIdCustomer(id);
        return gioHangChiTietRepo.getQuantityOfCartDetailByIdCart(cart.getId()) == null ? 0 : gioHangChiTietRepo.getQuantityOfCartDetailByIdCart(cart.getId());
    }

    @Override
    public GioHangChiTiet addProductToCart(CartDetailRequest req) {
        GioHang cart = getCartByIdCustomer(req.getIdKhachHang());
        try {
            ArrayList<GioHangChiTiet> productDetails = gioHangChiTietRepo.findCartDetailsByIdCart(cart.getId());

            if (productDetails.size() == 0) {
                GioHangChiTiet cartDetail = new GioHangChiTiet();
                cartDetail.setIdGioHang(cart);
                cartDetail.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                if (req.getSoLuong() > req.getSanPhamChiTiet().getSoLuongTon()) {
                    throw new RuntimeException("Số lượng còn lại không đủ");
                }
                cartDetail.setSoLuong(cartDetail.getSoLuong() == -1 ? 1 : cartDetail.getSoLuong());
                return gioHangChiTietRepo.save(cartDetail);
            } else {
                for (GioHangChiTiet item : productDetails) {
                    if (item.getIdSanPhamChiTiet().getId().equals(req.getSanPhamChiTiet().getId())) {

                        if (req.getSoLuong() == -1) {
                            if (item.getSoLuong() + 1 > req.getSanPhamChiTiet().getSoLuongTon()) {
                                throw new RuntimeException("Số lượng còn lại không đủ");
                            }

                            item.setSoLuong(item.getSoLuong() + 1);
                            return gioHangChiTietRepo.save(item);
                        } else {
                            if (req.getSanPhamChiTiet().getSoLuongTon() >= req.getSoLuong()) {
                                item.setSoLuong(req.getSoLuong());
                                return gioHangChiTietRepo.save(item);
                            } else {
                                throw new RuntimeException("Số lượng tồn không đủ");
                            }
                        }
                    }
                }

                if (req.getSoLuong() == -1) {
                    GioHangChiTiet cartDetail = new GioHangChiTiet();
                    cartDetail.setIdGioHang(cart);
                    cartDetail.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                    cartDetail.setSoLuong(1);
                    return gioHangChiTietRepo.save(cartDetail);
                } else {
                    GioHangChiTiet cartDetail = new GioHangChiTiet();
                    cartDetail.setIdGioHang(cart);
                    cartDetail.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                    cartDetail.setSoLuong(req.getSoLuong());
                    return gioHangChiTietRepo.save(cartDetail);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GioHangChiTiet addProductToCartPlusQuantity(CartDetailRequest req) {
        GioHang cart = getCartByIdCustomer(req.getIdKhachHang());
        try {
            ArrayList<GioHangChiTiet> productDetails = gioHangChiTietRepo.findCartDetailsByIdCart(cart.getId());
            Long quantity = gioHangChiTietRepo.getQuantityOfCartDetailByIdCart2(cart.getId()) == null ? 0 : gioHangChiTietRepo.getQuantityOfCartDetailByIdCart(cart.getId());

            Integer quantityProduct = req.getSoLuong() == -1 ? 1 : req.getSoLuong();
            if (quantity + quantityProduct > 5) {
                throw new RuntimeException("Tổng sản phẩm trong giỏ hàng chỉ được phép là 5.Vui lòng liên hệ 0968686868 để biết thêm chi tiết.");
            }

            if (productDetails.size() == 0) {
                GioHangChiTiet cartDetail = new GioHangChiTiet();
                cartDetail.setIdGioHang(cart);
                cartDetail.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                if (req.getSoLuong() > req.getSanPhamChiTiet().getSoLuongTon()) {
                    throw new RuntimeException("Số lượng còn lại không đủ");
                }
                cartDetail.setSoLuong(req.getSoLuong() == -1 ? 1 : req.getSoLuong());
                return gioHangChiTietRepo.save(cartDetail);
            } else {

                for (GioHangChiTiet item : productDetails) {
                    if (item.getIdSanPhamChiTiet().getId().equals(req.getSanPhamChiTiet().getId())) {

                        if (req.getSoLuong() == -1) {
                            if (item.getSoLuong() + 1 > req.getSanPhamChiTiet().getSoLuongTon()) {
                                throw new RuntimeException("Số lượng còn lại không đủ");
                            }

                            if (item.getSoLuong() + 1 > 3) {
                                throw new RuntimeException("Tối đa 3 sản phẩm trong một đơn hàng");
                            }

                            item.setSoLuong(item.getSoLuong() == null ? 0 : item.getSoLuong() + 1);
                            return gioHangChiTietRepo.save(item);
                        } else {
                            Integer quantityCart = item.getSoLuong() == null ? 0 : item.getSoLuong();
                            if (quantityCart + req.getSoLuong() >= req.getSanPhamChiTiet().getSoLuongTon()) {
                                if (quantityCart + req.getSoLuong() > 3) {
                                    throw new RuntimeException("Tối đa 3 sản phẩm trong một đơn hàng");
                                }

                                item.setSoLuong(req.getSoLuong() + quantityCart);
                                return gioHangChiTietRepo.save(item);
                            } else {
                                throw new RuntimeException("Số lượng tồn không đủ");
                            }
                        }
                    }
                }

                if (req.getSoLuong() == -1) {
                    GioHangChiTiet cartDetail = new GioHangChiTiet();
                    cartDetail.setIdGioHang(cart);
                    cartDetail.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                    cartDetail.setSoLuong(1);
                    return gioHangChiTietRepo.save(cartDetail);
                } else {
                    GioHangChiTiet cartDetail = new GioHangChiTiet();
                    cartDetail.setIdGioHang(cart);
                    cartDetail.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                    cartDetail.setSoLuong(req.getSoLuong());
                    return gioHangChiTietRepo.save(cartDetail);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<GioHangChiTiet> findCartDetailsByIdCart(Long id) {
        GioHang cart = getCartByIdCustomer(id);
        return gioHangChiTietRepo.findCartDetailsByIdCart(cart.getId());
    }

    @Override
    public ArrayList<GioHangChiTiet> findCartDetailsByIdCartAndProductDetail(Long id, Long idSanPham) {
        GioHang cart = getCartByIdCustomer(id);
        return gioHangChiTietRepo.findCartDetailsByIdCartAndProductDetail(cart.getId(), idSanPham);
    }
}
