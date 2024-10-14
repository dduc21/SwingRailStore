main_app.controller("rankingCustomersController", function ($scope, $http) {

    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.totalItems = 1;
    $scope.customers = []
    $scope.name_color = "";
    $scope.color = {}
  
    const loadData = function () {
      $http.get('http://localhost:8080/customer/find-all-ranking-customers-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage)
        .then(function (response) {
          $scope.customers = response.data
          $scope.totalItems = response.data.totalElements
          console.log($scope.totalItems)
        });
    }
    $scope.loadData();
  })