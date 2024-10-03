main_app.controller("billController", function ($scope, $http) {

  $scope.bills = []
  $scope.currentPage = 1;
  $scope.itemsPerPage = 10;
  $scope.totalItems = 1;

  // current state
  $scope.current_state = -1

  // quantity
  $scope.quantityState1 = 0
  $scope.quantityState2 = 0
  $scope.quantityState3 = 0
  $scope.quantityState4 = 0
  $scope.quantityState5 = 0
  $scope.quantityState6 = 0
  $scope.quantityStateAll = 0

  $scope.loadBills = function (state) {
    $scope.quantityStateAll = 0
    $scope.current_state = state;

    // get bill
    $http.get('http://localhost:8080/bill/get-bill-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&state=' + state).then(function (response) {
      $scope.bills = response.data
      $scope.totalItems = response.data.totalElements
    }).catch(function (error) {
      console.log(error)
    })

    // get quantity bill
    $http.get('http://localhost:8080/bill/get-quantity-bills').then(function (response) {
      console.log(response.data)
      response.data.forEach((bill) => {
        if (bill.trangThai == 1) {
          $scope.quantityState1 = bill.soLuong
          $scope.quantityStateAll += bill.soLuong
        } else if (bill.trangThai == 2) {
          $scope.quantityState2 = bill.soLuong
          $scope.quantityStateAll += bill.soLuong
        } else if (bill.trangThai == 3) {
          $scope.quantityState3 = bill.soLuong
          $scope.quantityStateAll += bill.soLuong
        } else if (bill.trangThai == 4) {
          $scope.quantityState4 = bill.soLuong
          $scope.quantityStateAll += bill.soLuong
        } else if (bill.trangThai == 5) {
          $scope.quantityState5 = bill.soLuong
          $scope.quantityStateAll += bill.soLuong
        } else if (bill.trangThai == 6) {
          $scope.quantityState6 = bill.soLuong
          $scope.quantityStateAll += bill.soLuong
        }
      })

    }).catch(function (error) {
      console.log(error)
    })
  }

  $scope.pageChanged = (state) => {
    console.log(state)
    // get bill
    $http.get('http://localhost:8080/bill/get-bill-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&state=' + $scope.current_state).then(function (response) {
      $scope.bills = response.data
    }).catch(function (error) {
      console.log(error)
    })
  }

  $scope.loadBills(-1)

  var socket = new SockJS("http://localhost:8080/ws");
  var stompClient = Stomp.over(socket);

  stompClient.connect({}, function (frame) {

    stompClient.subscribe("/bill/bills", function (message) {
      $scope.loadBills(-1)
      console.log(message)
      if(message.body == "hehe"){
        toastr.success(`Có đơn hàng ${message.body} vừa được đặt.`)
      }
      return;
    });
  });

  // $scope.addBill = function () {
  //   var message = {
  //     name: 'hehe',
  //   };

  //   stompClient.send("/app/bills", {}, JSON.stringify(message));
  // };

  $scope.formatToVND = function (amount) {
    const formatter = new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
      minimumFractionDigits: 0, // Set to 0 to display whole numbers
    });
    return formatter.format(amount);
  }

})