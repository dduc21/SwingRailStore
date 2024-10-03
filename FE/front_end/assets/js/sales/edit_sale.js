main_app.controller("editSaleController", function ($scope, $http, $routeParams) {
    var id = $routeParams.id
    var today = new Date();
    $scope.sale = {}

    // product 
    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.totalItems = 1;
    $scope.keyProduct = ""
    $scope.trang_thai = ""
    $scope.products = []
    $scope.productChooses = []

    //old product
    $scope.oldProductDetails = []

    // product details
    $scope.currentPageDetail = 1
    $scope.itemsPerPageDetail = 10
    $scope.totalItemsDetail = 1
    $scope.productDetails = []
    $scope.productDetailChooses = []

    // checked 
    $scope.checkedProduct = false
    $scope.checkedAllProductDetails = false

    // filter
    $scope.colors = []
    $scope.sizes = []
    $scope.types = []
    $scope.key = {
        "ids": "",
        "pageNo": $scope.currentPageDetail - 1,
        "pageSize": $scope.itemsPerPageDetail,
        "key": "",
        "idMauSac": "",
        "idTheLoai": "",
        "idKichCo": ""
    }

    // today
    var today = new Date();

    const loadData = function () {
        $http.get('http://localhost:8080/product/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key='+ '&id_type=' + '&id_brand='  + '&trang_thai=',)
            .then(function (response) {
                $scope.products = response.data
                console.log($scope.products)
                $scope.totalItems = response.data.totalElements
            });

        $http.get('http://localhost:8080/dot-giam-gia/get-sale/' + id).then(
            function (res) {
                $scope.sale = res.data;
                console.log($scope.sale)
            }, function (error) {
                console.log('Không tìm thấy voucher này.Vui lòng nhập lại id!')
            }
        )

        $http.get('http://localhost:8080/product-detail/get-product-detail-by-id-sale/' + id).then((res) => {
            $scope.productDetailChooses = res.data

            res.data.forEach(e => {
                $scope.oldProductDetails.push(e)
                if ($scope.productChooses.length == 0 || $scope.productChooses.find(x => x.id == e.idSanPham.id) == undefined ) {
                    var input = document.getElementById("product-" + e.idSanPham.id)
                    input.checked = true  
                    $scope.chooseProduct(e.idSanPham)
                } 
            });

        }).catch((error) => {
            console.log(error)
        })
    }

    const fillter = function (keyProduct, trang_thai) {
        $http.get('http://localhost:8080/product/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + keyProduct+ '&id_type=' + '&id_brand='  + '&trang_thai=' + trang_thai,)
            .then(function (response) {
                $scope.products = response.data
                $scope.totalItems = response.data.totalElements
                setTimeout(function () {
                    for (var i = 0; i < $scope.productChooses.length; i++) {
                        if ($scope.products.content.find(x => x.id == $scope.productChooses[i].id) == undefined) {
                            $scope.productChooses.splice(i, 1)
                        } else {
                            var e = document.getElementById("product-" + $scope.productChooses[i].id)
                            e.checked = true
                        }
                    }

                    var ids = $scope.productChooses.map(x => x.id).join(",")
                    $scope.key.ids = ids
                    $scope.loadProductDetails()
                }, 100)


            });
    }

    loadData()

    $scope.pageChanged = function () {
        $http.get('http://localhost:8080/product/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + $scope.keyProduct + '&id_type=' + '&id_brand='  + '&trang_thai=' + $scope.trang_thai,)
            .then(function (response) {
                $scope.products = response.data
            });
    };

    $scope.searchProduct = function () {
        fillter($scope.keyProduct, "")
    }

    $scope.loadData = function () {

        $http.get('http://localhost:8080/type/get-all')
            .then(function (response) {
                $scope.types = response.data
            });

        $http.get('http://localhost:8080/color/get-all')
            .then(function (response) {
                $scope.colors = response.data
            });

        $http.get('http://localhost:8080/size/get-all')
            .then(function (response) {
                $scope.sizes = response.data
            });

    }

    $scope.chooseProduct = function (product) {

        if (product == 'all') {
            if ($scope.productChooses.length === $scope.products.content.length) {
                for (var i = 0; i < $scope.productChooses.length; i++) {
                    var e = document.getElementById("product-" + $scope.productChooses[i].id)
                    e.checked = false
                }
                $scope.productChooses = []
                $scope.productDetailChooses = []
            } else {
                $scope.productChooses = $scope.products.content
                for (var i = 0; i < $scope.productChooses.length; i++) {
                    var e = document.getElementById("product-" + $scope.productChooses[i].id)
                    e.checked = true
                }
            }
        } else {
            var result = $scope.productChooses.find(x => x.id == product.id)
            if (result != undefined) {
                $scope.productChooses = $scope.productChooses.filter(x => x.id != product.id)
                for (var i = 0; i < $scope.productDetailChooses.length; i++) {
                    console.log($scope.productDetailChooses[i].idSanPham.id)
                    if ($scope.productDetailChooses[i].idSanPham.id == result.id) {
                        $scope.productDetailChooses = $scope.productDetailChooses.filter(x => x.idSanPham.id != result.id)
                    }
                }
            } else {
                $scope.productChooses.push(product)
            }
        }

        if ($scope.productChooses.length === $scope.products.content.length) {
            $scope.checkedProduct = true
        } else {
            $scope.checkedProduct = false
        }

        if ($scope.productChooses.length > 0) {
            var ids = $scope.productChooses.map(x => x.id).join(",")
            $scope.key.ids = ids
            $scope.loadProductDetails()
        } else {
            $scope.key.ids = ""
            $scope.productDetails = []
        }
        console.log($scope.productDetailChooses)
    }

    $scope.getAllImagesByIDProductDetail = function (id, text) {
        var textFist = `
         <div id="carousel-${id}" class="carousel slide" data-bs-ride="carousel">
         <div class="carousel-inner">
             
         `
        var textCenter = ''
        var textLast = `
             </div>
             <button class="carousel-control-prev" type="button" data-bs-target="#carousel-${id}" data-bs-slide="prev">
             <span class="carousel-control-prev-icon" aria-hidden="true"></span>
             <span class="visually-hidden">Previous</span>
             </button>
             <button class="carousel-control-next" type="button" data-bs-target="#carousel-${id}" data-bs-slide="next">
             <span class="carousel-control-next-icon" aria-hidden="true"></span>
             <span class="visually-hidden">Next</span>
             </button>
         </div>
         `
        var html = document.getElementById("image-" + id)
        if (text !== undefined) {
            var html = document.getElementById("image-" + text + "-" + id)
        }

        axios.get('http://localhost:8080/image/get-all/' + id).then(function (response) {
            for (var i = 0; i < response.data.length; i++) {
                if (i == 0) {
                    textCenter += `
                         <div class="carousel-item active">
                             <img src="${response.data[i].duongDan}" class="d-block w-100" alt="...">
                         </div>`
                } else {
                    textCenter += `
                         <div class="carousel-item">
                             <img src="${response.data[i].duongDan}" class="d-block w-100" alt="...">
                         </div>`
                }

            }

            html.innerHTML = textFist + textCenter + textLast

        }).catch(function (error) {
            console.log(error)
        })
    }

    $scope.formatToVND = function (amount) {
        const formatter = new Intl.NumberFormat("vi-VN", {
            style: "currency",
            currency: "VND",
            minimumFractionDigits: 0, // Set to 0 to display whole numbers
        });
        return formatter.format(amount);
    }

    $scope.searchProductDetail = function () {
        $scope.loadProductDetails()
    }

    $scope.loadProductDetails = async () => {
        await axios.post('http://localhost:8080/product-detail/get-product-detail-sale', $scope.key).then(
            function (response) {
                $scope.productDetails = response.data
                $scope.totalItemsDetail = response.data.totalElements
            }
        ).catch(function (error) {
            console.log(error)
        })
        $scope.loadData()

        setTimeout(function () {
            for (var i = 0; i < $scope.productDetailChooses.length; i++) {
                if ($scope.productDetails.content.find(x => x.id == $scope.productDetailChooses[i].id) == undefined) {
                    $scope.productDetailChooses = $scope.productDetailChooses.filter(x => x.id != $scope.productDetailChooses[i].id)
                } else {
                    var e = document.getElementById("product-detail-" + $scope.productDetailChooses[i].id)
                    console.log(e.checked)
                    e.checked = true;
                }
            }
        }, 200)
    }

    $scope.chooseProductDetail = function (product) {
        if (product == 'all') {
            if ($scope.productDetailChooses.length === $scope.productDetails.content.length) {
                for (var i = 0; i < $scope.productDetailChooses.length; i++) {
                    var e = document.getElementById("product-detail-" + $scope.productDetailChooses[i].id)
                    e.checked = false
                }
                $scope.productDetailChooses = []
            } else {
                $scope.productDetailChooses = $scope.productDetails.content
                for (var i = 0; i < $scope.productDetailChooses.length; i++) {
                    var e = document.getElementById("product-detail-" + $scope.productDetailChooses[i].id)
                    e.checked = true
                }
            }
        } else {
            var result = $scope.productDetailChooses.find(x => x.id == product.id)
            if (result != undefined) {
                $scope.productDetailChooses = $scope.productDetailChooses.filter(x => x.id != product.id)
            } else {
                $scope.productDetailChooses.push(product)
            }
        }

        if ($scope.productDetailChooses.length === $scope.productDetails.content.length) {
            $scope.checkedAllProductDetails = true
        } else {
            $scope.checkedAllProductDetails = false
        }

        if ($scope.productDetailChooses.length > 0) {
            var ids = $scope.productDetailChooses.map(x => x.id).join(",")
        } else {
            $scope.productDetailChooses = []
        }

    }

    $scope.updateSale = function () {
        if ($scope.sale.ten === "" ||
            $scope.sale.phanTramGiam === '' ||
            $scope.sale.ngayBatDau === '' ||
            $scope.sale.ngayKetThuc === ''
        ) {
            toastr.error('Bạn phải nhập đủ các trường có trên giao diện')
            return;
        }

        if (isNaN($scope.sale.phanTramGiam)) {
            toastr.error('Giá trị phần trên phải là số')
            return;
        }

        if ($scope.sale.phanTramGiam < 0) {
            toastr.error('Giá trị phần trăm phải là số duơng')
            return;
        }

        if ($scope.sale.phanTramGiam >= 100) {
            toastr.error('Giá trị phần trăm phải nhỏ hon 100%')
            return;
        }

        if ($scope.sale.ngayBatDau > $scope.sale.ngayKetThuc) {
            toastr.error('Ngày bắt đầu phải nhỏ hơn ngày kết thúc')
            return;
        }

        if ($scope.sale.ngayKetThuc < today) {
            toastr.error('Ngày kết thúc phải lớn hơn ngày hôm nay')
            return;
        }

        if ($scope.productDetailChooses.length === 0) {
            toastr.error('Vui lòng chọn một sản phẩm.')
            return;
        }

        $scope.oldProductDetails.forEach(x => {
            if ($scope.productDetailChooses.find(y => y.id == x.id) != undefined) {
                $scope.oldProductDetails = $scope.oldProductDetails.filter(y => y.id != x.id)
            }
        })

        Swal.fire({
            title: "Xác nhận thay đổi đợt giảm giá này?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Xác nhận",
            cancelButtonText: "Hủy"
        }).then((result) => {
            if (result.isConfirmed) {
                axios.put('http://localhost:8080/dot-giam-gia/edit-sale', $scope.sale).then(
                    (response) => {
                        $scope.oldProductDetails.forEach(x => {
                            x.idDotGiamGia = null
                            x.giaTriGiam = 0
                            axios.post("http://localhost:8080/product-detail/add-sale", x).then(
                                function (response) {
                                }
                            ).catch((error) => {
                                console.log(error)
                            })
                        })
        
                        $scope.oldProductDetails = [] // reset product and show notification
        
                        for (var i = 0; i < $scope.productDetailChooses.length; i++) {
                            $scope.productDetailChooses[i].idDotGiamGia = response.data
                            $scope.productDetailChooses[i].giaTriGiam = (100 - $scope.sale.phanTramGiam) * $scope.productDetailChooses[i].donGia / 100
                            axios.post("http://localhost:8080/product-detail/add-sale", $scope.productDetailChooses[i]).then(
                                function (response) {
        
                                }
                            ).catch((error) => {
                                console.log(error)
                            })
                        }
        
                        $scope.productDetailChooses = [] // reset product detail and show notification
        
                        if($scope.oldProductDetails.length == 0 && $scope.productDetailChooses.length == 0){
                            toastr.success('Thay đổi đợt giảm giá thành công')
                            location.href = "/front_end/html/router.html#!/dot-giam-gia"
                        }
                    }
                ).catch((error) => {
                    console.log(error)
                    toastr.error('Đã có lỗi xảy ra.Vui lòng liên hệ quản trị viên')
                });
            }
        })
    }

})