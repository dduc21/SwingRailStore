main_app.controller("sizeController", function ($scope, $http) {

  $scope.currentPage = 1;
  $scope.itemsPerPage = 10;
  $scope.totalItems = 1;
  $scope.key = ""
  $scope.trang_thai = ""
  $scope.sizes = []
  $scope.size_input = "";
  $scope.size = {}

  const loadData = function () {
    $http.get('http://localhost:8080/size/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + '&trang_thai=',)
      .then(function (response) {
        $scope.sizes = response.data
        $scope.totalItems = response.data.totalElements
        console.log($scope.sizes)
      });
  }

  const fillter = function (key, trang_thai) {
    $http.get('http://localhost:8080/size/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + key + '&trang_thai=' + trang_thai,)
      .then(function (response) {
        $scope.sizes = response.data
        $scope.totalItems = response.data.totalElements
        console.log($scope.totalItems)
      });
  }

  loadData()

  $scope.pageChanged = function () {
    $http.get('http://localhost:8080/size/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + $scope.key + '&trang_thai=' + $scope.trang_thai,)
      .then(function (response) {
        $scope.sizes = response.data
      });
  };

  $scope.fillterByBrandKey = function () {
    fillter($scope.key, $scope.trang_thai)
  }

  $scope.addSize = function () {
    var sizeModal = document.querySelector("#sizeAddModal")
    var addModal = bootstrap.Modal.getOrCreateInstance(sizeModal)

    if ($scope.size_input == "") {
      toastr.error("Bạn phải nhập kích cỡ giày !!!");
      return;
    }

    if(isNaN($scope.size_input)){
      toastr.error("Kích cỡ phải là số !!!");
      return;
    }

    if(Number($scope.size_input) < 0){
      toastr.error("Kích cỡ phải là số nguyên dương !!!");
      return;
    }

    axios.post('http://localhost:8080/size/add?size=' + $scope.size_input
    ).then(function (response) {
      toastr.success("Bạn đã tạo kích cỡ thành công !!!");
      addModal.hide()
      loadData()
      $scope.reset()
    }).catch(function (error) {
      toastr.error("Kích cỡ này đã tồn tại.Vui lòng nhập tên thương hiệu khác!!!");
    })

  }

  $scope.loadSize = function (id) {
    var sizeUpdateModal = document.querySelector("#sizeUpdateModal")
    var modal = bootstrap.Modal.getOrCreateInstance(sizeUpdateModal)

    axios.get('http://localhost:8080/size/get-size/' + id).then(function (response) {
      $scope.size = response.data
      console.log($scope.size)
      document.querySelector("#sizeUpdateBrand").value = response.data.kichCo
      modal.show()
    }).catch(function (error) {
      console.log(error)
      toastr.error("Đã có lỗi trong quá trình tải.Vui lòng liên hệ admin !!!");
    })
  }

  $scope.updateBrand = function () {
    var sizeUpdateModal = document.querySelector("#sizeUpdateModal")
    var modal = bootstrap.Modal.getOrCreateInstance(sizeUpdateModal)

    axios.put('http://localhost:8080/size/update', $scope.size)
      .then(function (response) {
        modal.hide()
        toastr.success("Bạn đã thay đổi thông tin thành công !!!");
        loadData()
      }).catch(function (error) {
        console.log(error)
        toastr.error("Kích cỡ đã tồn tại trong hệ thống !!!");
      })
  }

  $scope.reset = function () {
    $scope.size_input = ""
  }

})