var app = angular.module('myApp', []);

app.controller('myCtrl', function ($scope, $http, $compile, $timeout) {
    $scope.orders = [];

    // Function to fetch all orders
    $scope.fetchAllOrders = function () {
        $http.get('/api/get-all-order')
            .then(function (response) {
                $scope.orders = response.data;
            })
            .catch(function (error) {
                console.error('Error fetching orders:', error);
            });
    };



    // Function to fetch order details by orderId
    $scope.fetchOrderDetails = function (orderId) {
        $http.get('/api/get-order?orderId=' + orderId)
            .then(function (response1) {
                $scope.order = response1.data;
                console.log($scope.order);
                $http.get('/api/get-order-detail?orderId=' + orderId)
                    .then(function (response2) {
                        $scope.carts = response2.data;
                        createAndShowModal($scope.order, $scope.carts);
                    })
                    .catch(function (error) {
                        console.error('Error fetching order details:', error);
                    });
            })
            .catch(function (error) {
                console.error('Error fetching order:', error);
            });
    };

    // Hàm để lấy orderId từ sự kiện click
    $scope.getID = function (event) {
        var orderId = event.target.id || event.target.parentElement.id;
        $scope.fetchOrderDetails(orderId);
    };

    // Hàm để tạo và hiển thị modal
    function createAndShowModal(order, carts) {
        var modalContainer = document.createElement('div');
        modalContainer.innerHTML = `
            <div class="modal modal-xl" id="detail-order">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Invoice Details</h4>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="row modal-body px-4">
                            <div class="col-8" style="min-height: 480px; overflow-y: auto;">
                                <table class="table text-center table-hover">
                                    <thead class="table-dark">
                                        <tr>
                                            <th scope="col" colspan="2" class="px-1">Product</th>
                                            <th scope="col" class="px-5">Quantity</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Discount</th>
                                            <th scope="col">Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr ng-repeat="item in carts" ng-if="item.quantity > 0">
                                            <td class="px-0 mx-0 col-1 py-3">
                                                <div class="d-flex align-items-center">
                                                    <img ng-src="{{item.image}}" class="img-fluid w-50">
                                                </div>
                                            </td>
                                            <td class="pt-4">
                                                <h6>{{item.productVersionName}}</h6>
                                            </td>
                                            <td class="py-4">{{item.quantity}}</td>
                                            <td class="py-2">
                                                <s style="font-size: 12px">{{item.price}}</s>
                                                <h6>{{item.disCountPrice | number: 2}}</h6>
                                            </td>
                                            <td class="py-4">
                                                <h6>{{item.price - item.disCountPrice | number: 2}}</h6>
                                            </td>
                                            <td class="py-4">
                                                <h6>{{item.quantity * item.disCountPrice | number: 2}}</h6>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-4 p-3 rounded-3" style="box-shadow: 2px 5px 8px gray; max-height: 50vh">
                                    <form class="custom-form">
                                        <h3>Customer Information</h3>
                                        <small class="d-block">Name: {{order.userID.fullname}}</small>
                                        <small class="d-block">Phone: {{order.userID.phone}}</small>
                                        <small class="d-block">Address: {{order.userID.address}}</small>
                                        <hr>
                                        <div class="">
                                            <label class="form-label">Voucher code: {{order.voucherID ? order.voucherID.voucherCode : 'None'}}</label>
                                        </div>
                                        <div class="row mt-2">
                                            <div class="col-2 d-flex">
                                                <h6>Amount:</h6>
                                            </div>
                                            <div class="col-8 offset-2 text-end d-flex">
                                                <span class="text-danger">$ {{order.totalMoney}}</span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-2 d-flex">
                                                <h6>Discount:</h6>
                                            </div>
                                            <div class="col-8 offset-2 text-end d-flex">
                                                <span class="text-danger">$ {{ order.voucherID ? (order.totalMoney * order.voucherID.discountLevel / 100) : 0 | number: 2}}</span>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-2 d-flex">
                                                <h3 class="text-danger">Total:</h3>
                                            </div>
                                            <div class="col-8 offset-2 text-end d-flex">
                                                <h3 class="text-danger">$ {{ order.voucherID ? (order.totalMoney - (order.totalMoney * order.voucherID.discountLevel / 100)) : order.totalMoney | number: 2 }}</h3>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
        `;

        // Chèn đối tượng div vào cuối thẻ body
        document.body.appendChild(modalContainer);

        // Compile lại modal với AngularJS để nó nhận biết các biến scope
        var modalElement = angular.element(document.getElementById('detail-order'));
        $compile(modalElement)($scope);

        // Sử dụng $timeout để đảm bảo $apply được gọi sau khi vòng lặp $digest hiện tại hoàn thành
        $timeout(function () {
            $scope.$apply();
        });

        // Hiển thị modal
        var modal = new bootstrap.Modal(document.getElementById('detail-order'));
        modal.show();
    }
    $scope.fetchAllOrders();
});
