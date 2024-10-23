clientApp.controller("rankingCustomersController", function ($scope, $http) {
  // entity
  $scope.rankingCustomers = [];
  $scope.selectedCustomer = {};
  $scope.averagePoints = 0;

  // Hàm load data từ API
  $scope.loadData = () => {
    $http
      .get("http://localhost:8080/customer/find-all-ranking-customers")
      .then((response) => {
        // Lọc những khách hàng có điểm số > 0
        $scope.rankingCustomers = response.data.filter(customer => customer.tichDiem > 0);

        // Nếu không có khách hàng nào, không cần tính trung bình điểm
        if ($scope.rankingCustomers.length === 0) {
          $scope.averagePoints = 0;
          return; // Kết thúc hàm nếu không có khách hàng
        }

        // Tính tổng số điểm của tất cả khách hàng
        const totalPoints = $scope.rankingCustomers.reduce((sum, customer) => sum + customer.tichDiem, 0);

        // Tính trung bình cộng số điểm
        $scope.averagePoints = totalPoints / $scope.rankingCustomers.length;

        // Phân chia rank dựa trên trung bình cộng
        $scope.rankingCustomers.forEach(customer => {
          customer.rank = $scope.calculateRank(customer.tichDiem);
        });
      })
      .catch((error) => {
        console.log(error);
      });
  };

  // Hàm tính rank dựa trên trung bình cộng
  $scope.calculateRank = (tichDiem) => {
    if (tichDiem <= $scope.averagePoints * 0.5) {
      return 'Đồng'; // Điểm <= 50% trung bình cộng
    } else if (tichDiem <= $scope.averagePoints) {
      return 'Bạc'; // Điểm <= trung bình cộng
    } else if (tichDiem <= $scope.averagePoints * 1.5) {
      return 'Vàng'; // Điểm <= 150% trung bình cộng
    } else if (tichDiem <= $scope.averagePoints * 2) {
      return 'Bạch Kim'; // Điểm <= 200% trung bình cộng
    } else {
      return 'Kim Cương'; // Điểm > 200% trung bình cộng
    }
  };

  // Hàm tính ưu đãi dựa trên rank của khách hàng
  $scope.getDiscountByRank = (rank) => {
    switch (rank) {
      case 'Đồng':
        return '2%';
      case 'Bạc':
        return '3%';
      case 'Vàng':
        return '10%';
      case 'Bạch Kim':
        return '15%';
      case 'Kim Cương':
        return '20%';
      default:
        return '0%';
    }
  };

  // Hàm mở modal và hiển thị chi tiết ưu đãi
  $scope.openModal = (customer) => {
    $scope.selectedCustomer = customer;
    $scope.selectedCustomer.offerDetails = $scope.getDiscountByRank(customer.rank); // Gán mức ưu đãi dựa trên rank
    $('#offerModal').modal('show'); // Hiển thị modal (sử dụng jQuery)
  };

  $scope.closeModal = () => {
    $('#offerModal').modal('hide');
  };
  
  $scope.loadData();
});
