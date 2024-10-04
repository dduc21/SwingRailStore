
main_app.controller("addProductController", function ($scope, $http) {

    // modal
    $scope.listChooseSizeModal = []
    $scope.listChooseColorId = []

    // list
    $scope.listChooseColor = []
    $scope.listChooseSize = []

    // product
    $scope.product = {
        'ten': '',
        'moTa': '',
        'idThuongHieu': '',
        'idTheLoai': ''
    }

    // colors
    $scope.colors = [
        { 'id': 1, 'ten': 'Đen' },
        { 'id': 2, 'ten': 'Trắng' },
        { 'id': 3, 'ten': 'ghi' }]
    $scope.name_color = ''
    $scope.colorSelected = {}

    // sizes
    $scope.sizes = []
    $scope.newSize = ''
    $scope.sizeSelected = ""

    // brandsF
    $scope.brands = []
    $scope.name_brand = ""

    // types
    $scope.types = []
    $scope.name_type = ""

    // product details
    $scope.productDetails = []

    // images
    $scope.images = new Map();

    // load data
    $scope.loadData = function () {
        $http.get('http://localhost:8080/brand/get-all')
            .then(function (response) {
                $scope.brands = response.data
            });

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

    $scope.loadData()

    $scope.resetProductDetails = function () {
        $scope.productDetails.splice(0, $scope.productDetails.length);
    }

    $scope.chooseSize = function (size) {

        var html = document.getElementById("size-" + size)

        if (html.classList.contains('btn-primary')) {
            html.classList.remove('btn-primary')
            html.classList.add('btn-outline-primary')
            toastr.success("Bạn đã xóa kích cỡ " + size + " thành công.")
            $scope.removeChooseSize(size)
        } else {
            html.classList.add('btn-primary')
            html.classList.remove('btn-outline-primary')
            $scope.listChooseSizeModal.push(size)
            toastr.success("Bạn đã thêm kích cỡ " + size + " thành công.")
            setTimeout(() => {
                $scope.addProductDetail()
            }, 100);
        }
        console.log($scope.listChooseSizeModal)

    }

    $scope.chooseColor = function (color) {

        var html = document.getElementById("color-" + color);

        if (html.classList.contains('btn-primary')) {
            html.classList.remove('btn-primary')
            html.classList.add('btn-outline-primary')
            toastr.success("Bạn đã xóa màu sắc " + $scope.colors.find(x => x.id == color).ten + " thành công.")
            $scope.removeChooseColor(color)
        } else {
            html.classList.add('btn-primary')
            html.classList.remove('btn-outline-primary')
            $scope.listChooseColorId.push(color)
            toastr.success("Bạn đã thêm màu sắc " + $scope.colors.find(x => x.id == color).ten + " thành công.")
            setTimeout(() => {
                $scope.addProductDetail()
            }, 100);
        }

        var temp = []
        for (var id of $scope.listChooseColorId) {
            temp.push($scope.colors.find(x => x.id == id))
        }
        $scope.listChooseColor = temp;

    }

    $scope.chooseSizeAddProduct = function () {
        var e = document.getElementById("sizeListProductModal")
        var modal = bootstrap.Modal.getOrCreateInstance(e)
        $scope.listChooseSize = $scope.listChooseSizeModal
        modal.hide()
        toastr.success(`Bạn đã thêm kích cỡ thành công  !!!`);
        $scope.addProductDetail()

    }

    $scope.chooseColorAddProduct = function () {
        var e = document.getElementById("colorListProductModal")
        var modal = bootstrap.Modal.getOrCreateInstance(e)
        $scope.listChooseColor.splice(0, $scope.listChooseColor.length);
        modal.hide()
        toastr.success(`Bạn đã thêm màu sắc thành công  !!!`);

        for (var i = 0; i < $scope.listChooseColorId.length; i++) {
            var result = $scope.colors.find(x => x.id == $scope.listChooseColorId[i])
            if (result) {
                $scope.listChooseColor.push(result)
            }
        }

        $scope.addProductDetail()
    }

    $scope.removeChooseSize = function (size, state) {
        for (var i = $scope.listChooseSize.length; i--;) {
            if ($scope.listChooseSize[i] === size)
                $scope.listChooseSize.splice(i, 1);
        }

        if (state != 1) {
            for (var i = 0; i < $scope.productDetails.length; i++) {
                if ($scope.productDetails[i].size == size) {
                    $scope.productDetails.splice(i, 1)
                }
            }
        }

        $scope.addProductDetail()
    }

    $scope.removeChooseColor = function (color, state) {
        for (var i = $scope.listChooseColor.length; i--;) {

            if ($scope.listChooseColor[i].id === color.id) {
                $scope.listChooseColor.splice(i, 1);
            }

            if ($scope.listChooseColorId[i] === color.id) {
                $scope.listChooseColorId.splice(i, 1);
            }
        }

        if (state != 1) {
            for (var i = 0; i < $scope.productDetails.length; i++) {
                if ($scope.productDetails[i].color.id == color.id) {
                    $scope.productDetails.splice(i, 1)
                }
            }
        }

        $scope.addProductDetail()
    }

    $scope.buttonChooseSize = function () {
        var sizes = document.getElementsByClassName('btn-list-size')
        $scope.listChooseSizeModal = $scope.listChooseSize
        for (var i = 0; i < sizes.length; i++) {
            var html = sizes[i]

            html.classList.remove('btn-primary')
            html.classList.add('btn-outline-primary')
        }

        for (var i = 0; i < $scope.listChooseSize.length; i++) {
            var size = $scope.listChooseSize[i]
            var html = document.getElementById("size-" + size)

            html.classList.add('btn-primary')
            html.classList.remove('btn-outline-primary')
        }
    }

    $scope.buttonChooseColor = function () {
        var colors = document.getElementsByClassName('btn-list-color')

        var temp = []
        for (var id of $scope.listChooseColorId) {
            temp.push($scope.colors.find(x => x.id == id))
        }
        $scope.listChooseColor = temp;

        for (var i = 0; i < colors.length; i++) {
            var html = colors[i]

            html.classList.remove('btn-primary')
            html.classList.add('btn-outline-primary')
        }

        for (var i = 0; i < $scope.listChooseColor.length; i++) {
            var color = $scope.listChooseColor[i].id
            var html = document.getElementById("color-" + color)

            html.classList.add('btn-primary')
            html.classList.remove('btn-outline-primary')
        }
    }

    $scope.removeChooseByColorAndSize = function (colorId, size, index) {
        var productDetailHtml = document.getElementById("color-size-" + colorId + "-" + size)
        var productDetailTabColor = document.getElementById("modal-product-detail-color-" + colorId)

        productDetailHtml.remove()
        console.log(colorId)
        for (var i = 0; i < $scope.productDetails.length; i++) {
            if ($scope.productDetails[i].color.id == colorId && $scope.productDetails[i].size == size) {
                $scope.productDetails.splice(i, 1)
            }
        }
        console.log($scope.productDetails)
        var productByColor = $scope.productDetails.find(x => x.color.id == colorId)
        var productBySize = $scope.productDetails.find(x => x.size == size)

        if (productByColor === undefined) {
            productDetailTabColor.remove()
            $scope.removeChooseColor($scope.colors.find(x => x.id == colorId), 1)
        }

        if (productBySize === undefined) {
            $scope.removeChooseSize(size, 1)
        }

    }

    $scope.addProductDetail = function () {
        // $scope.resetProductDetails()

        if ($scope.listChooseColor.length > 0 && $scope.listChooseSize.length > 0) {
            var id = 0;
            for (var i = 0; i < $scope.listChooseColor.length; i++) {
                for (var j = 0; j < $scope.listChooseSize.length; j++) {
                    var productDetail = $scope.productDetails.find(x => x.color.id == $scope.listChooseColor[i].id && x.size == $scope.listChooseSize[j]);
                    if (productDetail != undefined) {
                        productDetail.id = id;
                    } else {
                        $scope.productDetails.push({
                            'id': id,
                            'color': $scope.listChooseColor[i],
                            'size': $scope.listChooseSize[j],
                            'quantity': 0,
                            'price': 0,
                            'product': {}
                        })
                    }

                    id++;
                }
            }
            console.log($scope.productDetails)
        }

    }

    $scope.addBrandAddProduct = function () {
        var brandModal = document.querySelector("#brandAddProductModal")
        var addModal = bootstrap.Modal.getOrCreateInstance(brandModal)

        if ($scope.name_brand == "") {
            toastr.error("Bạn phải nhập tên thương hiệu !!!");
        } else {
            axios.post('http://localhost:8080/brand/add?name=' + $scope.name_brand
            ).then(function (response) {
                toastr.success("Bạn đã tạo thương hiệu thành công !!!");
                addModal.hide()
                $scope.loadData()
                $scope.name_brand = ""
            }).catch(function (error) {
                console.log(error)
                toastr.error("Tên thương hiệu này đã tồn tại.Vui lòng nhập tên thương hiệu khác!!!");
            })
        }
    }

    $scope.addTypeAddProduct = function () {
        var typeModal = document.querySelector("#typeAddProductModal")
        var addModal = bootstrap.Modal.getOrCreateInstance(typeModal)

        if ($scope.name_type == "") {
            toastr.error("Bạn phải nhập tên thể loại !!!");
        } else {
            axios.post('http://localhost:8080/type/add?name=' + $scope.name_type
            ).then(function (response) {
                toastr.success("Bạn đã tạo thể loại thành công !!!");
                addModal.hide()
                $scope.loadData()
                $scope.name_type = ""
            }).catch(function (error) {
                console.log(error)
                toastr.error("Tên thể loại này đã tồn tại.Vui lòng nhập tên thể loại khác!!!");
            })
        }
    }

    $scope.addColorAddProduct = function () {
        var colorModal = document.querySelector("#colorAddProductModal")
        var addModal = bootstrap.Modal.getOrCreateInstance(colorModal)

        var listColorHtml = document.querySelector("#colorListProductModal")
        var listColorModal = bootstrap.Modal.getOrCreateInstance(listColorHtml)

        if ($scope.name_color == "") {
            toastr.error("Bạn phải nhập tên màu sắc !!!");
        } else {
            axios.post('http://localhost:8080/color/add?name=' + $scope.name_color
            ).then(function (response) {
                toastr.success("Bạn đã tạo màu sắc thành công !!!");
                addModal.hide()
                $scope.name_color = ""
                listColorModal.show()
                $scope.loadData()
                setTimeout(function () {
                    for (var i = 0; i < $scope.listChooseColor.length; i++) {

                        var html = document.getElementById("color-" + $scope.listChooseColor[i].id)
                        html.classList.add('btn-primary')
                        html.classList.remove('btn-outline-primary')
                    }
                }, 400)
            }).catch(function (error) {
                toastr.error("Tên màu sắc này đã tồn tại.Vui lòng nhập tên màu sắc khác!!!");
            })
        }
    }

    $scope.addSizeAddProduct = function () {
        var sizeModal = document.querySelector("#sizeAddProductModal")
        var addModal = bootstrap.Modal.getOrCreateInstance(sizeModal)

        var listSizeHtml = document.querySelector("#sizeListProductModal")
        var listSizeModal = bootstrap.Modal.getOrCreateInstance(listSizeHtml)

        if ($scope.newSize == "") {
            toastr.error("Bạn phải nhập kích cỡ giày !!!");
            return;
        }

        if (isNaN($scope.newSize)) {
            toastr.error("Kích cỡ phải là số !!!");
            return;
        }

        if (Number($scope.newSize) < 0) {
            toastr.error("Kích cỡ phải là số nguyên dương !!!");
            return;
        }

        axios.post('http://localhost:8080/size/add?size=' + $scope.newSize
        ).then(function (response) {
            toastr.success("Bạn đã tạo kích cỡ thành công !!!");
            addModal.hide()
            $scope.newSize = ""
            listSizeModal.show()
            $scope.loadData()
            setTimeout(function () {
                for (var i = 0; i < $scope.listChooseSize.length; i++) {

                    var html = document.getElementById("size-" + $scope.listChooseSize[i])
                    html.classList.add('btn-primary')
                    html.classList.remove('btn-outline-primary')
                }
            }, 400)

        }).catch(function (error) {
            console.log(error)
            toastr.error("Kích cỡ này đã tồn tại.Vui lòng nhập tên thương hiệu khác!!!");
        })
    }

    var readURL = function (input) {
        console.log($scope.images.get($scope.colorSelected.id))
        if ($scope.images.get($scope.colorSelected.id) === undefined) {
            if (input.files.length > 6) {
                toastr.error("Bạn chỉ được chọn 6 ảnh")
                return;
            }
        } else {
            if ($scope.images.get($scope.colorSelected.id).length + input.files.length > 6) {
                toastr.error("Bạn chỉ được chọn 6 ảnh")
                return;
            }
        }

        if (input.files) {
            if ($scope.images.get($scope.colorSelected.id)) {
                var oldFiles = $scope.images.get($scope.colorSelected.id)
                oldFiles = $scope.addFileList(oldFiles, input.files)

                $scope.images.set($scope.colorSelected.id, oldFiles);
            } else {
                var list = [];
                list = $scope.addFileList(list, input.files);
                $scope.images.set($scope.colorSelected.id, list)
            }

            console.log($scope.images.get($scope.colorSelected.id))

            var imageZone = document.querySelector("#image-" + $scope.colorSelected.id )

            for (var i = 0; i < input.files.length; i++) {
                var e = input.files[i]

                var reader = new FileReader();

                reader.onload = function (e) {

                    var html = imageZone.innerHTML + `
                    <div style="position: relative;width:100px;height: 100px;margin-right: 20px;margin-bottom:10px" id="image-${$scope.colorSelected.id}-${e.total}"
                         
                    >
                    <img src="${e.target.result}" style="width: 100px; height: 100px; margin-right: 5px; margin-bottom: 5px;">
                    <i class="ti ti-x" style="
                        font-size: 12px;
                        position: absolute;
                        top: -8px;
                        right: -7px;
                        background: #d11a2a;
                        color: white;
                        border-radius: 50%;
                        padding: 4px;" 
                        onclick="angular.element(this).scope().removeImage(${$scope.colorSelected.id},${e.total})"
                        ></i>
                     </div>
                    `
                    imageZone.innerHTML = html
                }

                reader.readAsDataURL(input.files[i]);
            }

        }
    }

    $scope.changeImage = function (element) {
        readURL(element)
    }

    $scope.getColorSelected = function (color, size) {
        $scope.colorSelected = color;
        $scope.sizeSelected = size
    }

    $scope.findProductDetailByColorIdAndSize = function (colorId, size) {
        return $scope.productDetails.find(x => x.color.id == colorId && x.size == size)
    }

    $scope.addFileList = function (newList, items) {
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
                    newList.push(res.data);
                }).catch((error) => console.error("Error:", error));
        }

        return newList;
    }

    $scope.removeImage = function (colorId, image) {
        console.log(image)
        var listImage = $scope.images.get(colorId);
        for (var i = 0; i < listImage.length; i++) {
            if (listImage[i].bytes === image) {
                var imageZone = document.querySelector("#image-" + colorId + "-" + image)
                imageZone.remove()
                listImage.splice(i, 1);
            }
        }
        $scope.images.set(colorId, listImage);
    }

    $scope.changeQuantity = function (color, size) {
        var quantityHtml = document.querySelector("#quantity-" + color.id + "-" + size)

        if (isNaN(quantityHtml.value)) {
            toastr.error("Số lượng phải là số")
            return;
        }

        if (Number(quantityHtml.value) < 0) {
            toastr.error("Số lượng phải lớn hơn 0")
            quantityHtml.value = 0;
            return;
        }

        for (var i = 0; i < $scope.productDetails.length; i++) {
            if ($scope.productDetails[i].color.id === color.id && $scope.productDetails[i].size === size) {
                $scope.productDetails[i].quantity = Number(quantityHtml.value)
            }
        }

    }

    $scope.changePrice = function (color, size) {
        var quantityHtml = document.querySelector("#price-" + color.id + "-" + size)

        if (isNaN(quantityHtml.value)) {
            toastr.error("Giá tiền phải là số")
            return;
        }

        if (Number(quantityHtml.value) < 0) {
            toastr.error("Giá tiền phải lớn hơn 0")
            quantityHtml.value = 0;
            return;
        }

        for (var i = 0; i < $scope.productDetails.length; i++) {
            if ($scope.productDetails[i].color.id === color.id && $scope.productDetails[i].size === size) {
                $scope.productDetails[i].price = Number(quantityHtml.value)
            }
        }

    }

    $scope.addProduct = function () {

        if ($scope.product.ten === "") {
            toastr.error("Bạn phải nhập tên sản phẩm.")
            return;
        }

        if ($scope.product.idThuongHieu === "") {
            toastr.error("Bạn phải chọn thương hiệu.")
            return;
        }

        if ($scope.product.idTheLoai === "") {
            toastr.error("Bạn phải chọn thể loại.")
            return;
        }

        if ($scope.product.moTa === "") {
            toastr.error("Bạn phải nhập mô tả.")
            return;
        }

        if ($scope.listChooseColor.length === 0 || $scope.listChooseColorId.length === 0) {
            toastr.error("Bạn phải chọn màu sắc.")
            return;
        }

        if ($scope.listChooseSize.length === 0) {
            toastr.error("Bạn phải chọn kích cỡ.")
            return;
        }

        $scope.product.idThuongHieu = $scope.brands.find(x => x.id == $scope.product.idThuongHieu)
        $scope.product.idTheLoai = $scope.types.find(x => x.id == $scope.product.idTheLoai)

        if ($scope.product.idThuongHieu === undefined) {
            toastr.error("Bạn phải chọn thương hiệu.")
            return;
        }

        if ($scope.product.idTheLoai === undefined) {
            toastr.error("Bạn phải chọn thể loại.")
            return;
        }

        for (var productDetail of $scope.productDetails) {
            if (productDetail.quantity == "" || productDetail.price == "") {
                toastr.error("Bạn phải nhập đầy đủ số lượng và giá của sản phẩm")
                return;
            }
        }

        Swal.fire({
            title: "Xác nhận tạo sản phẩm này?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Xác nhận",
            cancelButtonText: "Hủy"
        }).then((result) => {
            if (result.isConfirmed) {
                axios.post('http://localhost:8080/product/add', $scope.product)
                    .then(function (response) {
                        console.log(response)
                        console.log($scope.productDetails)
                        for (var i = 0; i < $scope.productDetails.length; i++) {
                            $scope.productDetails[i].product = response.data
                            axios.post('http://localhost:8080/product-detail/add', {
                                "soLuongTon": $scope.productDetails[i].quantity,
                                "donGia": $scope.productDetails[i].price,
                                "idMauSac": $scope.productDetails[i].color,
                                "idKichCo": $scope.sizes.find(x => x.kichCo == $scope.productDetails[i].size),
                                "idSanPham": response.data
                            })
                                .then(function (resOfProductDetail) {
                                    var imageOfProductDetails = $scope.images.get(resOfProductDetail.data.idMauSac.id)
                                    for (var j = 0; j < imageOfProductDetails.length; j++) {
                                        var imageUrl = imageOfProductDetails[j].secure_url;
                                        axios.post('http://localhost:8080/image/add', {
                                            "duongDan": imageUrl,
                                            "idSanPhamChiTiet": resOfProductDetail.data.id
                                        }).then(function (res) {
                                        }).catch(function (error) {
                                            console.log(error)
                                        })

                                    }

                                }).catch(function (error) {
                                    console.log(error)
                                })

                            if (i === $scope.productDetails.length - 1) {
                                toastr.success("Thêm sản phẩm mới thành công")
                                setTimeout(function () {
                                    location.href = "/front_end/html/router.html#!/san-pham"
                                }, 200)
                            }
                        }

                    }).catch(function (error) {
                        console.log(error)
                        toastr.error("Tên sản phẩm đã có trong hệ thống.Vui lòng nhập sản phẩm khác")
                    })
            }
        });


    }

})
