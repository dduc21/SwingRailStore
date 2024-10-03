main_app.controller("saleController", function($scope, $http){

    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.totalItems = 1;
    $scope.keySale = ""
    $scope.trang_thai = ""
    $scope.sales = []
  
    const loadData = function() {
      $http.get('http://localhost:8080/dot-giam-gia/find-all-panigation?page='+ ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + '&trang_thai=',)
      .then(function(response) {
        console.log(response)
        $scope.sales = response.data
        $scope.totalItems = response.data.totalElements
        console.log($scope.totalItems)
    });
    }
  
    const fillter = function(keySale, trang_thai) {
      $http.get('http://localhost:8080/dot-giam-gia/find-all-panigation?page='+ ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + keySale + '&trang_thai=' + trang_thai,)
      .then(function(response) {
        $scope.sales = response.data
        $scope.totalItems = response.data.totalElements
        console.log($scope.totalItems)
    });
    }
  
    loadData()

    setInterval(() => {
      loadData()
      console.log("running")
    }, 1000);
    
    $scope.changeStatusSale = function(id, state){
  
      if(state === 2){
        Swal.fire({
          icon: "error",
          title: "Đợt giảm giá đã quá hạn, không thể khôi phục trạng thái",
        });
      }else if(state === 3){
        Swal.fire({
          title: "Xác nhận thay đổi trạng thái",
          text: "Bạn chắc chắn muốn khôi phục lại đợt giảm giá này !!!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Tiếp tục",
          cancelButtonText: "Hủy"
          }).then((result) => {
            
            if (result.isConfirmed) {
              axios.put("http://localhost:8080/dot-giam-gia/change-status/" + id).then(
                (res) => {
                  loadData()
                  Swal.fire({
                    title: "Thành công",
                    text: "Bạn đã thay đổi trạng thái thành công!",
                    icon: "success"
                  });
                }
              ).catch((error) => {
                console.log(error)
              })
            
            }
          });
      }else{
          Swal.fire({
          title: "Xác nhận thay đổi trạng thái",
          text: "Bạn chắc chắn muốn thay đổi trạng thái đợt giảm giá này !!!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Tiếp tục",
          cancelButtonText: "Hủy"
          }).then((result) => {
            
            if (result.isConfirmed) {
              axios.put("http://localhost:8080/dot-giam-gia/change-status/" + id).then(
                (res) => {
                  loadData()
                  Swal.fire({
                    title: "Thành công",
                    text: "Bạn đã thay đổi trạng thái thành công!",
                    icon: "success"
                  });
                }
              ).catch((error) => {
                console.log(error)
              })
            
            }
          });
      }
     
    }
  
    $scope.pageChanged = function() {
      $http.get('http://localhost:8080/dot-giam-gia/find-all-panigation?page='+ ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + $scope.keySale + '&trang_thai=' + $scope.trang_thai,)
      .then(function(response) {
        $scope.sales = response.data
    });
    };
    
    $scope.fillterByStateSale = function(trang_thai){
      console.log(trang_thai)
      $scope.trang_thai = trang_thai;
      fillter($scope.keySale, $scope.trang_thai)
    }
    
    $scope.fillterBySearchKey = function(){
      var e = document.getElementById("keySearchSale").value
      fillter(e, $scope.trang_thai)
    }
  
  })