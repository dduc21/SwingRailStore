main_app.controller("productDetailController", function ($scope, $http, $routeParams) {

    $scope.currentPageProductDetail = 1;
    $scope.itemsPerPageProductDetail = 10;
    var idProduct = $routeParams.id
    $scope.totalItemsProductDetail = 1;
    $scope.trang_thai = ""
    $scope.productDetails = []

    // color and size
    $scope.colors = []
    $scope.sizes = []

    // filter
    $scope.idSize = ""
    $scope.idColor = ""
    $scope.maxPrice = 0;
    $scope.minPrice = 0;
    $scope.valuePrice = 0;

    //product detail
    $scope.product_detail = {}

    //images
    $scope.images = []

    // load data
    $scope.idColorProductDetail = ""
    $scope.idSizeProductDetail = ""

    const loadData = function () {
        $http.get('http://localhost:8080/product-detail/find-all-panigation?page=' + ($scope.currentPageProductDetail - 1) + '&size=' + $scope.itemsPerPageProductDetail + '&trang_thai=' + '&idSize=' + '&idColor=' + '&id=' + idProduct,)
            .then(function (response) {
                $scope.productDetails = response.data
                console.log($scope.productDetails)
                $scope.totalItemsProductDetail = response.data.totalElements

            });

        $http.get('http://localhost:8080/color/get-all')
            .then(function (response) {
                $scope.colors = response.data
            });

        $http.get('http://localhost:8080/size/get-all')
            .then(function (response) {
                $scope.sizes = response.data
            });

        $http.get('http://localhost:8080/product/get-max-don-gia')
            .then(function (response) {
                $scope.maxPrice = response.data
            });

    }

    const fillter = function (trang_thai) {
        $http.get('http://localhost:8080/product-detail/find-all-panigation?page=' + ($scope.currentPageProductDetail - 1) + '&size=' + $scope.itemsPerPageProductDetail + '&trang_thai=' + trang_thai + '&id=' + idProduct + '&idSize=' + $scope.idSize + '&idColor=' + $scope.idColor,)
            .then(function (response) {
                $scope.productDetails = response.data
                $scope.totalItemsProductDetail = response.data.totalElements
            });
    }

    loadData()

    $scope.pageChangedProductDetail = function () {

        $http.get('http://localhost:8080/product-detail/find-all-panigation?page=' + ($scope.currentPageProductDetail - 1) + '&size=' + $scope.itemsPerPageProductDetail + '&trang_thai=' + $scope.trang_thai + '&id=' + idProduct,)
            .then(function (response) {
                $scope.productDetails = response.data
            });
    };

    $scope.fillterByProductKey = function () {
        fillter($scope.trang_thai)
    }


    $scope.loadproduct = function (id) {
        var productUpdateModal = document.querySelector("#productUpdateModal")
        var modal = bootstrap.Modal.getOrCreateInstance(productUpdateModal)

        axios.get('http://localhost:8080/product/get-product/' + id).then(function (response) {
            $scope.product = response.data
            console.log($scope.product)
            document.querySelector("#nameUpdateproduct").value = response.data.ten
            modal.show()
        }).catch(function (error) {
            console.log(error)
            toastr.error("Đã có lỗi trong quá trình tải.Vui lòng liên hệ admin !!!");
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

    $scope.getAllImagesByIDProductDetail = function (id) {
        var textFist = `
        <div id="carousel-${id}" class="carousel slide carousel-fade" data-bs-ride="carousel">
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
        axios.get('http://localhost:8080/image/get-all/' + id).then(function (response) {
            for (var i = 0; i < response.data.length; i++) {
                if (i == 0) {
                    textCenter += `
                        <div class="carousel-item active "  >
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

    $scope.searchProductDetail = () => {
        fillter($scope.trang_thai)
    }

    $scope.setProductDetail = (product) => {
        $scope.product_detail = product
        $scope.idColorProductDetail = product.idMauSac.id
        $scope.idSizeProductDetail = product.idKichCo.id

        document.getElementById("qrcode").innerHTML = ""
        var qrcode = new QRCode(document.getElementById("qrcode"), {
            text: product.id + "",
            width: 128,
            height: 128
        });

        //size
        var sizeHeader = `
        <select class="form-select" style="width: 100%;" id="size" name="size"
                ng-model="idSizeProductDetail">
        `
        var sizeMiddler = ""
        var sizeFooter = `
        </select>
        `
        $scope.sizes.forEach(size => {
            if (size.id == $scope.idSizeProductDetail) {
                sizeMiddler += `
                <option selected value=${size.id} >${size.kichCo}</option>
                `
            } else {
                sizeMiddler += `
                <option value=${size.id} >${size.kichCo}</option>
                `
            }

        })

        document.getElementById("sizeProductDetail").innerHTML = sizeHeader + sizeMiddler + sizeFooter

        // color
        var colorHeader = `
        <select class="form-select" style="width: 100%;" id="color" name="color"
                ng-model="idColorProductDetail">
        `
        var colorMiddler = ""
        var colorFooter = `
        </select>
        `
        $scope.colors.forEach(color => {
            if (color.id == $scope.idColorProductDetail) {
                colorMiddler += `
                <option selected value=${color.id} >${color.ten}</option>
                `
            } else {
                colorMiddler += `
                <option value=${color.id} >${color.ten}</option>
                `
            }

        })

        document.getElementById("colorProductDetail").innerHTML = colorHeader + colorMiddler + colorFooter

        $scope.loadImageById($scope.product_detail.id);
    }

    $scope.updateProductDetail = () => {
        var brandModal = document.querySelector("#editProductDetailModal")
        var modal = bootstrap.Modal.getOrCreateInstance(brandModal)

        if ($scope.product_detail.donGia == null || $scope.product_detail.donGia == "") {
            toastr.error("Đơn giá không được trống")
            return;
        }

        if (Number($scope.product_detail.donGia) < 0) {
            toastr.error("Đơn giá phải lớn hơn 0")
            return;
        }

        if ($scope.product_detail.soLuongTon == null || $scope.product_detail.soLuongTon == "") {
            toastr.error("Số lượng không được trống")
            return;
        }

        if (Number($scope.product_detail.soLuongTon) < 0) {
            toastr.error("Số lượng phải lớn hơn 0")
            return;
        }

        $scope.product_detail.idMauSac = $scope.colors.find(color => color.id == document.getElementById("color").value)
        $scope.product_detail.idKichCo = $scope.sizes.find(size => size.id == document.getElementById("size").value)

        Swal.fire({
            title: "Xác nhận thay đổi sản phẩm này?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Xác nhận",
            cancelButtonText: "Hủy"
        }).then((result) => {
            if (result.isConfirmed) {
                axios.post('http://localhost:8080/product-detail/add-sale', $scope.product_detail).then(function (response) {
                    console.log(response.data)
                    modal.hide()
                    toastr.success("Thay đổi thành công")
                    $scope.searchProductDetail()
                }).catch(function (error) {
                    console.log(error)
                })
            }
        });

    }

    $scope.loadImageById = (id) => {
        // load image
        axios.get('http://localhost:8080/image/get-all/' + id).then(function (response) {
            $scope.images = response.data
            loadData()
        }).catch(function (error) {
            console.log(error)
        })
    }

    $scope.removeImage = function (image) {
        axios.delete('http://localhost:8080/image/delete/' + image.id).then(function (response) {
            $scope.images = response.data
            console.log(response.data)
            $scope.loadImageById($scope.product_detail.id)
        }).catch(function (error) {
            console.log(error)
        })
    }

    $scope.changeImage = function (element) {
        readURL(element)
    }

    var readURL = function (input) {
        if (input.files.length + $scope.images.length > 6) {
            toastr.error("Vui lòng chọn tối đa 6 hình ảnh")
            return
        }

        if (input.files) {
            $scope.addFileList(input.files)
        }
    }

    $scope.addFileList = function (items) {
        for (var i = 0; i < items.length; i++) {
            const formData = new FormData();
            formData.append('file', items[i]);
            axios.post("http://localhost:8080/cloudinary/upload",
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    }
                })
                .then((res) => {
                    axios.post('http://localhost:8080/image/add', {
                        "duongDan": res.data.secure_url,
                        "idSanPhamChiTiet": $scope.product_detail.id
                    }).then(function (res) {
                        setTimeout(() => {
                            $scope.loadImageById($scope.product_detail.id)
                        }, 10)
                    }).catch(function (error) {
                        console.log(error)
                    })
                }).catch((error) => console.error("Error:", error));
        }

    }


})