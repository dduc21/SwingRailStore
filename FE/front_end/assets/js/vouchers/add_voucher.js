// add voucher
main_app.controller("addVoucherController", function ($scope, $http) {

    var today = new Date();
    $scope.voucher = {
        max_price: '',
        min_price: '',
        percent: '',
        quantity: '',
        name: "",
        date_start: "",
        date_end: ""
    }

    // customer
    $scope.customers = []
    $scope.customerChoose = []
    $scope.currentPageCustomer = 1;
    $scope.itemsPerPageCustomer = 5;
    $scope.totalItemCustomers = 1;
    $scope.keyCustomer = ""
    $scope.stateVoucher = 0;

    // checkbox
    $scope.checkedAllProductDetails = false;

    $scope.loadEmployee = () => {
        $http.get('http://localhost:8080/customer/find-all-panigation?page=' + ($scope.currentPageCustomer - 1) + '&size=' + $scope.itemsPerPageCustomer + '&key=' + '&trang_thai=',)
            .then(function (response) {
                $scope.customers = response.data
            });
    }

    $scope.loadEmployee()

    $scope.addVoucher = function () {

        if ($scope.voucher.name === "" ||
            $scope.voucher.max_price === '' ||
            $scope.voucher.min_price === '' ||
            $scope.voucher.percent === '' ||
            $scope.voucher.quantity === '' ||
            $scope.voucher.date_start === '' ||
            $scope.voucher.date_end === ''
        ) {
            toastr.error('Bạn phải nhập đủ các trường có trên form')
            return;
        }

        if (isNaN($scope.voucher.max_price)) {
            toastr.error('Giá trị tối đa phải là số')
            return;
        }

        if (isNaN($scope.voucher.min_price)) {
            toastr.error('Giá trị đơn tối thiểu phải là số')
            return;
        }

        if (isNaN($scope.voucher.percent)) {
            toastr.error('Giá trị phần trên phải là số')
            return;
        }

        if (isNaN($scope.voucher.quantity)) {
            toastr.error('Số lần phải là số')
            return;
        }

        if ($scope.voucher.max_price < 0) {
            toastr.error('Giá trị tối đa phải là số duơng')
            return;
        }

        if ($scope.voucher.min_price < 0) {
            toastr.error('Giá trị đơn tối thiểu phải là số duơng')
            return;
        }

        if ($scope.voucher.percent < 0) {
            toastr.error('Giá trị phần trăm phải là số duơng')
            return;
        }

        if ($scope.voucher.percent >= 100) {
            toastr.error('Giá trị phần trăm phải nhỏ hon 100%')
            return;
        }

        if ($scope.voucher.quantity < 0) {
            toastr.error('Số lượng phải là số duơng')
            return;
        }

        if ($scope.voucher.date_start > $scope.voucher.date_end) {
            toastr.error('Ngày bắt đầu phải nhỏ hơn ngày kết thúc')
            return;
        }

        if ($scope.voucher.date_end < today) {
            toastr.error('Ngày kết thúc phải lớn hơn ngày hôm nay')
            return;
        }

        var data = {
            ma: '',
            ten: $scope.voucher.name,
            phanTramGiam: $scope.voucher.percent,
            giaTriToiThieu: $scope.voucher.min_price,
            giaTriToiDa: $scope.voucher.max_price,
            soLanDung: $scope.voucher.quantity,
            ngayBatDau: $scope.voucher.date_start,
            ngayKetThuc: $scope.voucher.date_end,
            loaiVoucher: $scope.stateVoucher
        }

        Swal.fire({
            title: "Xác nhận tạo phiếu giảm giá này?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Xác nhận",
            cancelButtonText: "Hủy"
        }).then((result) => {
            if (result.isConfirmed) {
                axios.post('http://localhost:8080/voucher/add-voucher', data).then(
                    (response) => {
                        console.log($scope.customerChoose)
                        if ($scope.stateVoucher == 1) {
                            $scope.customerChoose.forEach((e, index) => {
                                axios.post('http://localhost:8080/voucher-detail/add-voucher-detail', {
                                    voucher: response.data,
                                    khachHang: e
                                }).then((response) => {

                                }).catch((error) => {
                                    console.log(error)
                                    toastr.error('Đã có lỗi xảy ra.Vui không liên hệ quản trị này')
                                })

                                axios.post('http://localhost:8080/email/send-html-voucher-email', {
                                    voucher: response.data,
                                    khachHang: e
                                }).then((response) => {

                                }).catch((error) => {
                                    console.log(error)
                                    toastr.error('Đã có lỗi xảy ra.Vui không liên hệ quản trị này')
                                })

                                if ($scope.customerChoose.length - 1 == index) {
                                    toastr.success('Bạn đã tạo voucher thành công')
                                    setTimeout(() => {
                                        location.href = "/front_end/html/router.html#!/phieu-giam-gia"
                                    }, 200)
                                }
                            });
                        } else {
                            toastr.success('Bạn đã tạo voucher thành công')
                            setTimeout(() => {
                                location.href = "/front_end/html/router.html#!/phieu-giam-gia"
                            }, 200)
                        }
                    }
                ).catch((error) => {
                    console.log(error)
                    toastr.error('Đã có lỗi xảy ra.Vui lòng liên hệ quản trị viên')
                });
            }
        });


    }

    $scope.selectSate = (state) => {
        $scope.stateVoucher = state
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