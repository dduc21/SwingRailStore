main_app.controller("editVoucherController", function ($scope, $http, $routeParams) {
    var id = $routeParams.id
    var today = new Date();
    $scope.voucher = {}

    // customer
    $scope.customers = []
    $scope.customerChoose = []
    $scope.currentPageCustomer = 1;
    $scope.itemsPerPageCustomer = 5;
    $scope.totalItemCustomers = 1;
    $scope.keyCustomer = ""
    $scope.stateVoucher = 0;

    // voucher detail
    $scope.voucherDetails = []

    // checkbox
    $scope.checkedAllProductDetails = false;

    $scope.loadEmployee = () => {
        $http.get('http://localhost:8080/customer/find-all-panigation?page=' + ($scope.currentPageCustomer - 1) + '&size=' + $scope.itemsPerPageCustomer + '&key=' + '&trang_thai=',)
            .then(function (response) {
                $scope.customers = response.data
            });
    }

    $http.get('http://localhost:8080/voucher/get-voucher/' + id).then(
        function (res) {
            $scope.voucher = res.data;
            $scope.stateVoucher = res.data.loaiVoucher
            if (res.data.loaiVoucher == 1) {
                $scope.loadEmployee()

                setTimeout(() => {
                    $http.get('http://localhost:8080/voucher-detail/get-by-id-voucher/' + id).then((res) => {
                        $scope.voucherDetails = res.data
                        $scope.voucherDetails.forEach(e => {
                            $scope.chooseProductDetail(e.idKhachHang)
                            var e = document.getElementById("product-detail-" + e.idKhachHang.id)
                            e.checked = true
                        });

                    })
                }, 100);

            }


        }, function (error) {
            console.log('Không tìm thấy voucher này.Vui lòng nhập lại id!')
        }
    )

    $scope.updateVoucher = function () {
        $scope.voucher.loaiVoucher = $scope.stateVoucher

        if ($scope.voucher.ten === "" ||
            $scope.voucher.giaTriToiDa === '' ||
            $scope.voucher.giaTriToiThieu === '' ||
            $scope.voucher.phanTramGiam === '' ||
            $scope.voucher.soLanDung === '' ||
            $scope.voucher.ngayBatDau === '' ||
            $scope.voucher.ngayKetThuc === ''
        ) {
            toastr.error('Bạn phải nhập đủ các trường có trên form')
            return;
        }

        if (isNaN($scope.voucher.giaTriToiDa)) {
            toastr.error('Giá trị tối đa phải là số')
            return;
        }

        if (isNaN($scope.voucher.giaTriToiThieu)) {
            toastr.error('Giá trị đơn tối thiểu phải là số')
            return;
        }

        if (isNaN($scope.voucher.phanTramGiam)) {
            toastr.error('Giá trị phần trên phải là số')
            return;
        }

        if (isNaN($scope.voucher.soLanDung)) {
            toastr.error('Số lần phải là số')
        }

        if ($scope.voucher.giaTriToiDa < 0) {
            toastr.error('Giá trị tối đa phải là số duơng')
            return;
        }

        if ($scope.voucher.giaTriToiThieu < 0) {
            toastr.error('Giá trị đơn tối thiểu phải là số duơng')
            return;
        }

        if ($scope.voucher.phanTramGiam < 0) {
            toastr.error('Giá trị phần trăm phải là số duơng')
            return;
        }

        if ($scope.voucher.phanTramGiam >= 100) {
            toastr.error('Giá trị phần trăm phải nhỏ hơn 100%')
            return;
        }

        if ($scope.voucher.soLanDung < 0) {
            toastr.error('Số lượng phải là số duơng')
            return;
        }

        if ($scope.voucher.ngayBatDau > $scope.voucher.ngayKetThuc) {
            toastr.error('Ngày bắt đầu phải nhỏ hơn ngày kết thúc')
            return;
        }

        if ($scope.voucher.ngayKetThuc < today) {
            toastr.error('Ngày kết thúc phải lớn hơn ngày hôm nay')
            return;
        }

        Swal.fire({
            title: "Xác nhận thay đổi phiếu giảm giá này?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Xác nhận",
            cancelButtonText: "Hủy"
        }).then((result) => {
            if (result.isConfirmed) {
                axios.put('http://localhost:8080/voucher/edit-voucher', $scope.voucher).then(
                    (response) => {
                        if ($scope.voucher.loaiVoucher == 1) {
                            $scope.voucherDetails.forEach(e => {
                                var e = $scope.customerChoose.find(x => x.id == e.idKhachHang.id)
                                if (e !== undefined) {

                                } else {
                                    axios.delete('http://localhost:8080/voucher-detail/delete-by-id/' + e.id).then((res) => {
                                    }).catch((res) => {
                                        console.log(res)
                                    })
                                }
                            })

                            console.log($scope.customerChoose)
                            $scope.customerChoose.forEach((e, index) => {
                                var voucherDetail = $scope.voucherDetails.find(x => x.idKhachHang.id == e.id)
                                if (voucherDetail === undefined) {
                                    axios.post('http://localhost:8080/email/send-html-email-voucher', {
                                        voucher: response.data,
                                        khachHang: e
                                    }).then((response) => {

                                    }).catch((error) => {
                                        console.log(error)
                                        toastr.error('Đã có lỗi xảy ra.Vui không liên hệ quản trị này')
                                    })

                                    axios.post('http://localhost:8080/voucher-detail/add-voucher-detail', {
                                        voucher: response.data,
                                        khachHang: e
                                    }).then((response) => {

                                    }).catch((error) => {
                                        console.log(error)
                                        toastr.error('Đã có lỗi xảy ra.Vui không liên hệ quản trị này')
                                    })
                                } else {
                                    // khoong cos gi xay ra ca hhiih
                                }

                                if (index == $scope.customerChoose.length - 1) {
                                    toastr.success('Bạn đã thay đổi voucher thành công')
                                    setTimeout(() => {
                                        location.href = "/front_end/html/router.html#!/phieu-giam-gia"
                                    }, 200)
                                }
                            })
                        } else {
                            if($scope.voucherDetails.length == 0){
                                toastr.success('Bạn đã thay đổi voucher thành công')
                                setTimeout(() => {
                                    location.href = "/front_end/html/router.html#!/phieu-giam-gia"
                                }, 200)
                            }else{
                                $scope.voucherDetails.forEach((e, index) => {
                                    var e = $scope.customerChoose.find(x => x.id == e.idKhachHang.id)
    
                                    axios.delete('http://localhost:8080/voucher-detail/delete-by-id/' + e.id).then((res) => {
                                    }).catch((res) => {
                                        console.log(res)
                                    })
    
                                    if (index == $scope.voucherDetails.length - 1) {
                                        toastr.success('Bạn đã thay đổi voucher thành công')
                                        setTimeout(() => {
                                            location.href = "/front_end/html/router.html#!/phieu-giam-gia"
                                        }, 200)
                                    }
                                })
                            }
                           
                        }
                    }
                ).catch((error) => {
                    console.log(error)
                    toastr.error('Đã có lỗi xảy ra.Vui lòng liên hệ quản trị viên')
                });
            }
        })

    }

    $scope.selectSate = (state) => {
        $scope.stateVoucher = state
        if ($scope.stateVoucher == 1) {
            $scope.loadEmployee()
        }
    }

    $scope.chooseProductDetail = function (product) {
        if (product == 'all') {
            if ($scope.customerChoose.length === $scope.customers.content.length) {
                for (var i = 0; i < $scope.customerChoose.length; i++) {
                    var e = document.getElementById("product-detail-" + $scope.customerChoose[i].id)
                    e.checked = false
                }
                $scope.customerChoose = []
            } else {
                $scope.customerChoose = $scope.customers.content
                for (var i = 0; i < $scope.customerChoose.length; i++) {
                    var e = document.getElementById("product-detail-" + $scope.customerChoose[i].id)
                    e.checked = true
                }
            }
        } else {
            var result = $scope.customerChoose.find(x => x.id == product.id)
            if (result != undefined) {
                $scope.customerChoose = $scope.customerChoose.filter(x => x.id != product.id)
            } else {
                $scope.customerChoose.push(product)
            }
        }

        if ($scope.customerChoose.length === $scope.customers.content.length) {
            $scope.checkedAllProductDetails = true
        } else {
            $scope.checkedAllProductDetails = false
        }

        if ($scope.customerChoose.length > 0) {
            var ids = $scope.customerChoose.map(x => x.id).join(",")
        } else {
            $scope.customerChoose = []
        }

    }
})