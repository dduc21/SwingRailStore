main_app.controller("brandController", function ($scope, $http) {

  $scope.currentPage = 1;
  $scope.itemsPerPage = 10;
  $scope.totalItems = 1;
  $scope.key = ""
  $scope.trang_thai = ""
  $scope.brands = []
  $scope.name_brand = "";
  $scope.brand = {}

  const loadData = function () {
    $http.get('http://localhost:8080/brand/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + '&trang_thai=',)
      .then(function (response) {
        $scope.brands = response.data
        $scope.totalItems = response.data.totalElements
        console.log($scope.totalItems)
      });
  }

  const fillter = function (key, trang_thai) {
    $http.get('http://localhost:8080/brand/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + key + '&trang_thai=' + trang_thai,)
      .then(function (response) {
        $scope.brands = response.data
        $scope.totalItems = response.data.totalElements
        console.log($scope.totalItems)
      });
  }

  loadData()

  $scope.pageChanged = function () {
    $http.get('http://localhost:8080/brand/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + $scope.key + '&trang_thai=' + $scope.trang_thai,)
      .then(function (response) {
        $scope.brands = response.data
      });
  };

  $scope.fillterByBrandKey = function () {
    fillter($scope.key, $scope.trang_thai)
  }

  $scope.addBrand = function () {
    var brandModal = document.querySelector("#brandModal")
    var addModal = bootstrap.Modal.getOrCreateInstance(brandModal)

    if ($scope.name_brand == "") {
      toastr.error("Bạn phải nhập tên thương hiệu !!!");
    } else {
      axios.post('http://localhost:8080/brand/add?name=' + $scope.name_brand
      ).then(function (response) {
        toastr.success("Bạn đã tạo thương hiệu thành công !!!");
        addModal.hide()
        loadData()
        $scope.reset()
      }).catch(function (error) {
        toastr.error("Tên thương hiệu này đã tồn tại.Vui lòng nhập tên thương hiệu khác!!!");
      })
    }
  }

  $scope.loadBrand = function (id) {
    var brandUpdateModal = document.querySelector("#brandUpdateModal")
    var modal = bootstrap.Modal.getOrCreateInstance(brandUpdateModal)

    axios.get('http://localhost:8080/brand/get-brand/' + id).then(function (response) {
      $scope.brand = response.data
      console.log($scope.brand)
      document.querySelector("#nameUpdateBrand").value = response.data.ten
      modal.show()
    }).catch(function (error) {
      console.log(error)
      toastr.error("Đã có lỗi trong quá trình tải.Vui lòng liên hệ admin !!!");
    })
  }

  $scope.updateBrand = function () {
    var brandUpdateModal = document.querySelector("#brandUpdateModal")
    var modal = bootstrap.Modal.getOrCreateInstance(brandUpdateModal)

    console.log($scope.brand)
    axios.put('http://localhost:8080/brand/update', $scope.brand)
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
    $scope.name_brand = ""
  }

})