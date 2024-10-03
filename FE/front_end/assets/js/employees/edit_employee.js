main_app.controller("editEmployeeController", function ($scope, $http, $routeParams, $window) {
    var id = $routeParams.id
    var today = new Date();
    $scope.avatar = "";
    $scope.employee = {}

    // REGEX
    var phone_regex = /(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\b/;
    var email_regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // choose gender
    $scope.selectGender = function (gender) {
        $scope.employee.gioiTinh = gender
    }

    $scope.selectRole = function (role) {
        $scope.employee.chucVu = role
    }

    $http.get('http://localhost:8080/employee/get-employee/' + id).then(
        function (res) {
            var resp = res.data
            $scope.employee = {
                id: resp.id,
                ten: resp.ten,
                ngaySinh: resp.ngaySinh,
                cccd: resp.cccd,
                gioiTinh: resp.gioiTinh,
                matKhau: resp.matKhau,
                email: resp.email,
                soDienThoai: resp.soDienThoai,
                maTinh: resp.maTinh,
                maPhuong: resp.maPhuong,
                maXa: resp.maXa,
                tinh: resp.tinh,
                phuong: resp.phuong,
                xa: resp.xa,
                diaChi: resp.diaChi,
                avatar: resp.avatar,
                chucVu: Number(resp.chucVu.ma)
            }
            $scope.getAllprovideByCode($scope.employee.maPhuong, $scope.employee.maXa, $scope.employee.maTinh)
        }, function (error) {
            console.log('Không tìm thấy khách hàng này.Vui lòng nhập lại id!')
        }
    )

    // FAST DELIVERY
    const token = "2b4b5f3e-ac78-11ee-a6e6-e60958111f48";
    const serviceID = 53320;
    const shopDistrictId = 1482;
    const shopWardCode = 11007;

    const selectCityCustomer = document.querySelector("#city");
    const selectDistrict = document.querySelector("#district");
    const selectWardCodeCustomer = document.querySelector("#ward");

    $scope.getAllprovide = function () {
        // const thisOrder = document.getElementById(`hoaDon${orderId}`);
        fetch(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                token: token,
            },
        })
            .then((res) => res.json())
            .then((data) => {
                const defaultOption = document.createElement("option");
                defaultOption.value = -1; // Set the value as needed
                defaultOption.textContent = "--Chọn Tỉnh/Thành phố--"; // Set the text content
                // Set the 'disabled' and 'selected' attributes to make it the default option
                defaultOption.disabled = false;
                defaultOption.selected = true;
                selectCityCustomer.appendChild(defaultOption);
                const options = data.data;
                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    // option.value = options[i].ProvinceID; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].ProvinceName; // Set the text of the option
                    option.setAttribute("providecode", options[i].ProvinceID);
                    selectCityCustomer.appendChild(option); // Add the option to the select element
                }
            })
            .catch((error) => console.error("Error:", error));
    }

    $scope.getAllDistrict = function () {
        const selectedOption = selectCityCustomer.options[selectCityCustomer.selectedIndex];
        const customAttribute = selectedOption.getAttribute("providecode");
        const provinceid = customAttribute;
        const selectDistrict = document.querySelector(` #district`);

        // remove child districts
        var old_options = selectDistrict.querySelectorAll("option");
        for (var i = 1; i < old_options.length; i++) {
            selectDistrict.removeChild(old_options[i]);
        }

        // remove child wards
        var old_options = selectWardCodeCustomer.querySelectorAll("option");
        for (var i = 1; i < old_options.length; i++) {
            selectWardCodeCustomer.removeChild(old_options[i]);
        }

        $scope.employee.maTinh = provinceid
        $scope.employee.tinh = selectedOption.textContent

        axios
            .get(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district`, {
                params: {
                    province_id: provinceid,
                },
                headers: {
                    Accept: "application/json",
                    token: token,
                },

            })
            .then((res) => {
                const options = res.data.data;

                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    option.value = options[i].DistrictID; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].DistrictName; // Set the text of the option
                    option.setAttribute("districtcode", options[i].DistrictID);
                    selectDistrict.appendChild(option); // Add the option to the select element
                }
            })
            .catch((error) => console.error("Error:", error));
    }

    $scope.getFullWardCode = function () {
        const selectedOption = selectDistrict.options[selectDistrict.selectedIndex];
        const customAttribute = selectedOption.getAttribute("districtcode");
        const districtid = customAttribute;

        // remove child
        var old_options = selectWardCodeCustomer.querySelectorAll("option");
        for (var i = 1; i < old_options.length; i++) {
            selectWardCodeCustomer.removeChild(old_options[i]);
        }

        $scope.employee.maPhuong = districtid;
        $scope.employee.phuong = selectedOption.textContent

        axios.get(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward`, {
            headers: {
                Accept: "application/json",
                token: token,
            },
            params: {
                district_id: districtid,
            }
        })
            .then((res) => {
                //remove all child
                const options = res.data.data;
                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    option.value = options[i].WardCode; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].WardName; // Set the text of the option
                    option.setAttribute("WardCode", options[i].WardCode);
                    selectWardCodeCustomer.appendChild(option); // Add the option to the select element
                }
            })
            .catch((error) => console.error("Error:", error));
    }

    $scope.selectWard = function () {
        const ward_selected = selectWardCodeCustomer.options[selectWardCodeCustomer.selectedIndex];
        const ward_attribute = ward_selected.getAttribute("WardCode");
        const code_ward = ward_attribute;

        $scope.employee.maXa = code_ward
        $scope.employee.xa = ward_selected.textContent

        console.log($scope.employee)
    }

    $scope.getAllprovideByCode = function (district_code, ward_code, province_code) {

        // const thisOrder = document.getElementById(`hoaDon${orderId}`);
        fetch(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                token: token,
            },
        })
            .then((res) => res.json())
            .then((data) => {
                const defaultOption = document.createElement("option");
                defaultOption.value = -1; // Set the value as needed
                defaultOption.textContent = "Chọn Tỉnh"; // Set the text content
                // Set the 'disabled' and 'selected' attributes to make it the default option
                defaultOption.disabled = false;
                selectCityCustomer.appendChild(defaultOption);
                const options = data.data;
                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    // option.value = options[i].ProvinceID; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].ProvinceName; // Set the text of the option
                    option.setAttribute("providecode", options[i].ProvinceID);
                    if (province_code === String(options[i].ProvinceID)) {
                        option.selected = true;
                    }
                    selectCityCustomer.appendChild(option); // Add the option to the select element
                }
                $scope.getAllDistrictByCode(ward_code, district_code, province_code)
            })
            .catch((error) => console.error("Error:", error));
    }

    $scope.getAllDistrictByCode = function (ward_code, district_code, provinceCode) {

        console.log(ward_code, district_code, provinceCode)
        axios
            .get(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district`, {
                params: {
                    province_id: provinceCode,
                },
                headers: {
                    Accept: "application/json",
                    token: token,
                },

            })
            .then((res) => {
                const options = res.data.data;
                console.log(options)

                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    option.value = options[i].DistrictID; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].DistrictName; // Set the text of the option
                    option.setAttribute("districtcode", options[i].DistrictID);
                    if (district_code === String(options[i].DistrictID)) {
                        option.selected = true;
                    }
                    selectDistrict.appendChild(option); // Add the option to the select element
                }
                $scope.getFullWardCodeByCode(ward_code, district_code)
            })
            .catch((error) => console.error("Error:", error));
    }

    $scope.getFullWardCodeByCode = function (ward_code, district_code) {

        axios.get(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward`, {
            headers: {
                Accept: "application/json",
                token: token,
            },
            params: {
                district_id: district_code,
            }
        })
            .then((res) => {
                //remove all child
                const options = res.data.data;
                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    option.value = options[i].WardCode; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].WardName; // Set the text of the option
                    option.setAttribute("WardCode", options[i].WardCode);
                    if (ward_code === String(options[i].WardCode)) {
                        option.selected = true;
                    }
                    selectWardCodeCustomer.appendChild(option); // Add the option to the select element
                }
                validateAddress()
            })
            .catch((error) => console.error("Error:", error));
    }

    var readURL = function (input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('.profile_pic').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    $scope.changeImage = function (element) {

        if (element.files && element.files[0]) {
            const formData = new FormData();
            formData.append('file', element.files[0]);
            axios.post("http://localhost:8080/cloudinary/upload",
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    }
                })
                .then((res) => {
                    $scope.avatar = res.data.secure_url;
                }).catch((error) => console.error("Error:", error));
        }

        readURL(element)
        var icon = document.getElementsByClassName('icon-upload-button')[0]
        icon.classList.add('icon-upload-button-close')
    }

    $scope.updateEmployee = function () {
        $scope.employee.gioiTinh = Number($scope.employee.gioiTinh)

        Swal.fire({
            title: "Xác nhận thay đổi nhân viên này?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Xác nhận",
            cancelButtonText: "Hủy"
        }).then((result) => {
            if (result.isConfirmed) {

                if ($scope.employee.ten === "" ||
                    $scope.employee.ngaySinh === ""
                    || $scope.employee.cccd === ""
                    || $scope.employee.gioiTinh === ""
                    || $scope.employee.email === ""
                    || $scope.employee.soDienThoai === ""
                    || $scope.employee.maTinh === ""
                    || $scope.employee.maPhuong === ""
                    || $scope.employee.maXa === ""
                    || $scope.employee.diaChi === "") {
                    toastr.error('Bạn phải nhập đầy các trường có trên form ')
                    return;
                }

                if ($scope.employee.ten === null ||
                    $scope.employee.ngaySinh === null
                    || $scope.employee.cccd === null
                    || $scope.employee.gioiTinh === null
                    || $scope.employee.email === null
                    || $scope.employee.soDienThoai === null
                    || $scope.employee.maTinh === null
                    || $scope.employee.maPhuong === null
                    || $scope.employee.maXa === null
                    || $scope.employee.diaChi === null) {
                    toastr.error('Bạn phải nhập đầy các trường có trên form ')
                    return;
                }

                if ($scope.employee.ngaySinh > today) {
                    toastr.error('Ngày sinh phải nhỏ hơn ngày hôm nay')
                    return;
                }

                if ($scope.employee.cccd.length != 12) {
                    toastr.error('Nhập đủ 12 số căn cước công dân')
                    return;
                }

                if (!email_regex.test($scope.employee.email)) {
                    toastr.error('Bạn phải nhập đúng định dạng email')
                    return;
                }

                if (!phone_regex.test($scope.employee.soDienThoai)) {
                    toastr.error('Bạn phải nhập đúng định dạng số điện thoại')
                    return;
                }

                if($scope.avatar != "" ) {
                    $scope.employee.avatar = $scope.avatar
                }

                axios.put("http://localhost:8080/employee/update", $scope.employee)
                    .then((res) => {
                        toastr.success('Bạn đã thay đổi thông tin thành công!!!');
                    })
                    .catch((error) => console.error("Error:", error));

                setTimeout(() => {
                    location.href = "/html/router.html#!/nhan-vien"
                    $window.location.reload();
                }, 100)

            }
        });

    }
})