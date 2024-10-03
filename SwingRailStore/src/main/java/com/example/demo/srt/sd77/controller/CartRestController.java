package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.request.CartDetailRequest;
import com.example.demo.srt.sd77.service.impl.GioHangSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartRestController {

    @Autowired
    private GioHangSerImpl cartService;

    @GetMapping("/get-quantity-by-id-customer/{id}")
    public ResponseEntity<?> getQuantityOfCartDetailByIdCustomer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cartService.getQuantityOfCartDetailByIdCustomer(id), HttpStatus.OK);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody CartDetailRequest req) {
        try {
            return new ResponseEntity<>(cartService.addProductToCart(req), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-to-cart-quantity")
    public ResponseEntity<?> addToCartQuantity(@RequestBody CartDetailRequest req) {
        try {
            return new ResponseEntity<>(cartService.addProductToCartPlusQuantity(req), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getCause(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-cart-detail-by-id/{id}")
    public ResponseEntity<?> findCartDetailsByIdCart(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(cartService.findCartDetailsByIdCart(id), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-cart-detail-by-id-cart")
    public ResponseEntity<?> findCartDetailsByIdCartAndIdProductDetail(@RequestParam("id_customer") String idCustomer,
                                                                       @RequestParam("id_product_detail") String idProductDetail) {
        try {
            return new ResponseEntity<>(cartService.findCartDetailsByIdCartAndProductDetail(Long.valueOf(idCustomer), Long.valueOf(idProductDetail)), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
