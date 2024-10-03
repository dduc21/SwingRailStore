main_app.controller("typeController", function ($scope, $http) {

    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.totalItems = 1;
    $scope.key = ""
    $scope.trang_thai = ""
    $scope.types = []
    $scope.name_type = "";
    $scope.type = {}
  
    const loadData = function () {
      $http.get('http://localhost:8080/type/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + '&trang_thai=',)
        .then(function (response) {
          $scope.types = response.data
          $scope.totalItems = response.data.totalElements
          console.log($scope.totalItems)
        });
    }
  
    const fillter = function (key, trang_thai) {
      $http.get('http://localhost:8080/type/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + key + '&trang_thai=' + trang_thai,)
        .then(function (response) {
          $scope.types = response.data
          $scope.totalItems = response.data.totalElements
          console.log($scope.totalItems)
        });
    }
  
    loadData()
  
    $scope.pageChanged = function () {
      $http.get('http://localhost:8080/type/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + $scope.key + '&trang_thai=' + $scope.trang_thai,)
        .then(function (response) {
          $scope.types = response.data
        });
    };
  
    $scope.fillterBytypeKey = function () {
      fillter($scope.key, $scope.trang_thai)
    }
  
    $scope.addtype = function () {
      var typeModal = document.querySelector("#typeModal")
      var addModal = bootstrap.Modal.getOrCreateInstance(typeModal)
  
      if ($scope.name_type == "") {
        toastr.error("Bạn phải nhập tên thương hiệu !!!");
      } else {
        axios.post('http://localhost:8080/type/add?name=' + $scope.name_type
        ).then(function (response) {
          toastr.success("Bạn đã tạo thể loại thành công !!!");
          addModal.hide()
          loadData()
          $scope.reset()
        }).catch(function (error) {
          toastr.error("Tên thể loại này đã tồn tại.Vui lòng nhập tên thể loại khác!!!");
        })
      }
    }
  
    $scope.loadtype = function (id) {
      var typeUpdateModal = document.querySelector("#typeUpdateModal")
      var modal = bootstrap.Modal.getOrCreateInstance(typeUpdateModal)
  
      axios.get('http://localhost:8080/type/get-type/' + id).then(function (response) {
        $scope.type = response.data
        console.log($scope.type)
        document.querySelector("#nameUpdatetype").value = response.data.ten
        modal.show()
      }).catch(function (error) {
        console.log(error)
        toastr.error("Đã có lỗi trong quá trình tải.Vui lòng liên hệ admin !!!");
      })
    }
  
    $scope.updatetype = function () {
      var typeUpdateModal = document.querySelector("#typeUpdateModal")
      var modal = bootstrap.Modal.getOrCreateInstance(typeUpdateModal)
  
      console.log($scope.type)
      axios.put('http://localhost:8080/type/update', $scope.type)
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
      $scope.name_type = ""
    }
  
  })