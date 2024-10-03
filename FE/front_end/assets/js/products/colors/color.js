main_app.controller("colorController", function ($scope, $http) {

    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.totalItems = 1;
    $scope.key = ""
    $scope.trang_thai = ""
    $scope.colors = []
    $scope.name_color = "";
    $scope.color = {}
  
    const loadData = function () {
      $http.get('http://localhost:8080/color/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + '&trang_thai=',)
        .then(function (response) {
          $scope.colors = response.data
          $scope.totalItems = response.data.totalElements
          console.log($scope.totalItems)
        });
    }
  
    const fillter = function (key, trang_thai) {
      $http.get('http://localhost:8080/color/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + key + '&trang_thai=' + trang_thai,)
        .then(function (response) {
          $scope.colors = response.data
          $scope.totalItems = response.data.totalElements
          console.log($scope.totalItems)
        });
    }
  
    loadData()
  
    $scope.pageChanged = function () {
      $http.get('http://localhost:8080/color/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + $scope.key + '&trang_thai=' + $scope.trang_thai,)
        .then(function (response) {
          $scope.colors = response.data
        });
    };
  
    $scope.fillterBycolorKey = function () {
      fillter($scope.key, $scope.trang_thai)
    }
  
    $scope.addcolor = function () {
      var colorModal = document.querySelector("#colorModal")
      var addModal = bootstrap.Modal.getOrCreateInstance(colorModal)
  
      if ($scope.name_color == "") {
        toastr.error("Bạn phải nhập tên màu sắc !!!");
      } else {
        axios.post('http://localhost:8080/color/add?name=' + $scope.name_color
        ).then(function (response) {
          toastr.success("Bạn đã tạo màu sắc thành công !!!");
          addModal.hide()
          loadData()
          $scope.reset()
        }).catch(function (error) {
          toastr.error("Tên màu sắc này đã tồn tại.Vui lòng nhập tên màu sắc khác!!!");
        })
      }
    }
  
    $scope.loadcolor = function (id) {
      var colorUpdateModal = document.querySelector("#colorUpdateModal")
      var modal = bootstrap.Modal.getOrCreateInstance(colorUpdateModal)
  
      axios.get('http://localhost:8080/color/get-color/' + id).then(function (response) {
        $scope.color = response.data
        console.log($scope.color)
        document.querySelector("#nameUpdatecolor").value = response.data.ten
        modal.show()
      }).catch(function (error) {
        console.log(error)
        toastr.error("Đã có lỗi trong quá trình tải.Vui lòng liên hệ admin !!!");
      })
    }
  
    $scope.updatecolor = function () {
      var colorUpdateModal = document.querySelector("#colorUpdateModal")
      var modal = bootstrap.Modal.getOrCreateInstance(colorUpdateModal)
  
      console.log($scope.color)
      axios.put('http://localhost:8080/color/update', $scope.color)
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
      $scope.name_color = ""
    }
  
  })