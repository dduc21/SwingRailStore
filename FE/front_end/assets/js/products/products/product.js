main_app.controller("productController", function ($scope, $http) {

    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.totalItems = 1;
    $scope.keyProduct = ""
    $scope.trang_thai = ""
    $scope.products = []

    //brands
    $scope.brands = []
    $scope.idBrand = ""

    //types
    $scope.types = []
    $scope.idType = ""
    $scope.stateEdit = 0;

    const loadData = function () {
        $http.get('http://localhost:8080/product/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + '&id_type=' + '&id_brand=' + '&trang_thai=',)
            .then(function (response) {
                $scope.products = response.data
                console.log($scope.products)
                $scope.totalItems = response.data.totalElements
            });

        $http.get('http://localhost:8080/brand/get-all')
            .then(function (response) {
                $scope.brands = response.data
            });

        $http.get('http://localhost:8080/type/get-all')
            .then(function (response) {
                $scope.types = response.data
            });
    }

    const fillter = function (keyProduct, trang_thai) {
        $http.get('http://localhost:8080/product/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + keyProduct + '&id_type=' + $scope.idType + '&id_brand=' + $scope.idBrand + '&trang_thai=' + trang_thai,)
            .then(function (response) {
                $scope.products = response.data
                $scope.totalItems = response.data.totalElements
            });
    }

    loadData()

    $scope.pageChanged = function () {
        $http.get('http://localhost:8080/product/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + $scope.keyProduct + '&id_type=' + $scope.idType + '&id_brand=' + $scope.idBrand + '&trang_thai=' + $scope.trang_thai,)
            .then(function (response) {
                $scope.products = response.data
            });
    };

    $scope.fillterByProductKey = function () {
        var key = document.getElementById("searchKey").value
        fillter(key, $scope.trang_thai)
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

    $scope.updateproduct = function () {
        var productUpdateModal = document.querySelector("#productUpdateModal")
        var modal = bootstrap.Modal.getOrCreateInstance(productUpdateModal)

        console.log($scope.product)
        axios.put('http://localhost:8080/product/update', $scope.product)
            .then(function (response) {
                modal.hide()
                toastr.success("Bạn đã thay đổi thông tin thành công !!!");
                loadData()
            }).catch(function (error) {
                console.log(error)
                toastr.error("Tên đã tồn tại trong hệ thống !!!");
            })
    }

    $scope.reset = function () {
        $scope.name_product = ""
    }

    $scope.searchProductDetail = () => {
        var key = document.getElementById("searchKey").value
        fillter(key, $scope.trang_thai)
    }

    $scope.editProduct = (product) => {
        console.log(product)

        //product
        var name_product = document.querySelector(`#name_${product.id}`)
        var name_edit_product = document.querySelector(`#name_edit_${product.id}`)

        // brand
        var brand_product = document.querySelector(`#brand_${product.id}`)
        var brand_edit_product = document.querySelector(`#brand_edit_${product.id}`)

        // type
        var type_product = document.querySelector(`#type_${product.id}`)
        var type_edit_product = document.querySelector(`#type_edit_${product.id}`)

        //status
        var status_product = document.querySelector(`#status_product_${product.id}`)
        var status_product_edit = document.querySelector(`#status_product_edit_${product.id}`)

        // button
        var edit_button = document.getElementById("edit_button_" + product.id)
        var save_button = document.getElementById("save_button_" + product.id)
        var cancel_button = document.getElementById("cancel_button_" + product.id)

        if ($scope.stateEdit == 0) {
            name_product.style.display = "none"
            brand_product.style.display = "none"
            type_product.style.display = "none"
            status_product.style.display = "none"

            name_edit_product.style.display = "block"

            // brand
            brand_edit_product.style.display = "block"
            brand_edit_product.innerHTML = ""
            var textBrandHeader = `
            <select class="form-select" style="width: 100%;" id="brand_select_${product.id}" name="brand"
                   >
            `
            var textBrandMiddle = ""
            $scope.brands.forEach(brand => {
                if (brand.ten == product.tenThuongHieu) {
                    textBrandMiddle += `
                    <option selected value=${brand.id} >${brand.ten}</option>
                    `
                } else {
                    textBrandMiddle += `
                    <option value=${brand.id} >${brand.ten}</option>
                    `
                }

            })
            var textBrandFooter = `
            </select>
            `
            brand_edit_product.innerHTML = textBrandHeader + textBrandMiddle + textBrandFooter

            //type
            type_edit_product.style.display = "block"
            type_edit_product.innerHTML = ""
            var textTypeHeader = `
            <select class="form-select" style="width: 100%;" id="type_select_${product.id}" name="brand"
                   >
            `
            var textTypeMiddle = ""
            $scope.types.forEach(type => {
                if (type.ten == product.tenTheLoai) {
                    textTypeMiddle += `
                    <option selected value=${type.id} >${type.ten}</option>
                    `
                } else {
                    textTypeMiddle += `
                    <option value=${type.id} >${type.ten}</option>
                    `
                }

            })
            var textTypeFooter = `
            </select>
            `
            type_edit_product.innerHTML = textTypeHeader + textTypeMiddle + textTypeFooter

            //status
            status_product_edit.style.display = "block"
            status_product_edit.innerHTML = ""
            var textStatusHeader = `
            <select class="form-select" style="width: 100%;" id="status_select_${product.id}" name="brand"
                   >
            `
            var textStatusMiddle = ""
            if (0 == product.trangThai) {
                textStatusMiddle += `
                    <option selected value=0 >Ngừng kinh doanh</option>
                    <option value=1 >Đang kinh doanh</option>
                    `
            } else {
                textStatusMiddle +=  `
                <option value=0 >Ngừng kinh doanh</option>
                <option selected value=1 >Đang kinh doanh</option>
                `
            }
            var textStatusFooter = `
            </select>
            `
            status_product_edit.innerHTML = textStatusHeader + textStatusMiddle + textStatusFooter

            $scope.stateEdit = 1

            // button
            edit_button.style.display = "none"
            save_button.style.display = "inline-block"
            cancel_button.style.display = "inline-block"
        } else {
            name_product.style.display = "block"
            brand_product.style.display = "block"
            type_product.style.display = "block"
            status_product.style.display = "block"

            name_edit_product.style.display = "none"
            brand_edit_product.style.display = "none"
            type_edit_product.style.display = "none"
            status_product_edit.style.display = "none"

            $scope.stateEdit = 0

            // button
            edit_button.style.display = "inline-block"
            save_button.style.display = "none"
            cancel_button.style.display = "none"
        }

    }

    $scope.saveProduct = (product) => {
        var brand_id = document.querySelector(`#brand_select_${product.id}`).value
        var type_id = document.querySelector(`#type_select_${product.id}`).value
        var trang_thai = document.querySelector(`#status_select_${product.id}`).value
        var brand = $scope.brands.find(brand => brand.id == brand_id)
        var type = $scope.types.find(type => type.id == type_id)

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
                axios.put('http://localhost:8080/product/update', {
                    id: product.id,
                    ten: product.ten,
                    idThuongHieu: brand,
                    idTheLoai: type,
                    trangThai: trang_thai == "0" ? false : true
                }).then(function (response) {
                    setTimeout(() => {
                        loadData()
                        $scope.editProduct(product)
                        toastr.success("Thay đổi sản phẩm thành công!!!")
                    }, 10)
                }).catch(function (error) {
                    console.log(error)
                })
            }
        })

    }

})