clientApp.controller("rankingCustomersController", function ($scope, $http) {
  // entity
  $scope.rankingCustomers = [];
  $scope.productBestSellers = [];
  $scope.productDetailsWithSale = [];

  $scope.loadData = () => {
    $http
      .get("http://localhost:8080/customer/find-all-ranking-customers")
      .then((response) => {
        $scope.rankingCustomers = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  };
  $scope.loadData();
});
