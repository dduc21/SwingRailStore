main_app.controller("employeeController", function($scope, $http){

    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.totalItems = 1;
    $scope.key = ""
    $scope.trang_thai = ""
    $scope.employees = []
  
    const loadData = function() {
      $http.get('http://localhost:8080/employee/find-all-panigation?page='+ ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + '&trang_thai=',)
      .then(function(response) {
        console.log(response)
        $scope.employees = response.data
        $scope.totalItems = response.data.totalElements
        console.log($scope.totalItems)
    });
    }
  
    const fillter = function(key, trang_thai) {
      $http.get('http://localhost:8080/employee/find-all-panigation?page='+ ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + key + '&trang_thai=' + trang_thai,)
      .then(function(response) {
        $scope.employees = response.data
        $scope.totalItems = response.data.totalElements
        console.log($scope.totalItems)
    });
    }
  
    loadData()
    
  
    $scope.pageChanged = function() {
      $http.get('http://localhost:8080/employee/find-all-panigation?page='+ ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + $scope.key + '&trang_thai=' + $scope.trang_thai,)
      .then(function(response) {
        $scope.employees = response.data
    });
    };

    $scope.fillterByEmployeeKey = function(){
      var key = document.getElementById("employeeKey").value
      fillter(key, $scope.trang_thai)
    }
  
  })