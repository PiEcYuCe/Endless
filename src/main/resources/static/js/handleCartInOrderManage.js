    var app = angular.module('myApp', []);

    app.controller('myCtrl', function ($scope, $http, $compile, $timeout) {
        // Khởi tạo các biến
        $scope.productVersionModels = [];
        $scope.selectedItems = [];
        $scope.vouchers = [];
        $scope.selectedCustomer = {};
        $scope.totalQuantity = 0;
        $scope.totalPrice = 0;
        $scope.totalAmount = 0;
        $scope.notifiMessage = '';
        $scope.orderDetails = [];

        // Hàm gọi API để lấy dữ liệu product versions
        $http.get("/api/productVersions")
            .then(function (response) {
                $scope.productVersionModels = response.data;
            })
            .catch(function (error) {
                console.error('Error fetching product versions:', error);
            });

        // Hàm gọi API để lấy thông tin của khách hàng dựa trên tên hoặc ID
        $scope.getUserInfo = function (searchCustomer) {
            $http.get("/api/userInfo", { params: { userKey: searchCustomer } })
                .then(function (response) {
                    $scope.selectedCustomer = response.data;
                    $scope.updateTotalAmount(); // Cập nhật tổng tiền sau khi lấy thông tin khách hàng
                    $scope.getVouchersByAmountOrder($scope.totalAmount); // Lấy danh sách voucher
                })
                .catch(function (error) {
                    console.error('Error fetching customer info:', error);
                });
        };

        // Hàm gọi API để lấy danh sách voucher dựa trên tổng số tiền
        $scope.getVouchersByAmountOrder = function (amount) {
            $http.get("/api/getVoucherByAmountOrder", { params: { amount: amount } })
                .then(function (response) {
                    $scope.vouchers = response.data;
                    $scope.updateSaleValueList(); // Cập nhật danh sách voucher
                })
                .catch(function (error) {
                    console.error('Error fetching vouchers:', error);
                });
        };

        // Cập nhật danh sách voucher vào datalist
        $scope.updateSaleValueList = function () {
            $scope.saleValueList = [];
            angular.forEach($scope.vouchers, function (voucher) {
                $scope.saleValueList.push({ value: voucher.voucherCode }); // Thêm mã voucher vào danh sách
            });
        };

        // Cập nhật tổng số tiền từ các sản phẩm đã chọn
        $scope.updateTotalAmount = function () {
            $scope.totalAmount = $scope.selectedItems.reduce(function (total, item) {
                return total + (item.quantity * item.discountedPrice);
            }, 0).toFixed(2);
        };

        // Cập nhật tổng số tiền khi thay đổi số lượng sản phẩm hoặc giá
        $scope.$watch('selectedItems', function (newVal, oldVal) {
            if (newVal !== oldVal) {
                $scope.updateTotalAmount();
                $scope.getVouchersByAmountOrder($scope.totalAmount);
            }
        }, true);

        // Hàm cập nhật mảng productVersionModels khi có sự thay đổi trong ô input
        $scope.updateList = function (val) {
            $scope.filteredItems = [];
            if (!val) return;

            let count = 0;
            $scope.productVersionModels.forEach(function (item) {
                if (item.versionName.toUpperCase().includes(val.toUpperCase()) && count < 10) {
                    $scope.filteredItems.push(item);
                    count++;
                }
            });
        };

        // Cập nhật mảng productVersionModels khi có sự thay đổi trong ô input
        $scope.$watch('searchQuery', function (newVal, oldVal) {
            $scope.updateList(newVal);
        });

        // Đóng danh sách khi click bên ngoài ô tìm kiếm
        angular.element(document).on('click', function (event) {
            if (!event.target.closest('#productInput') && !event.target.closest('#productList')) {
                $scope.$apply(function () {
                    $scope.filteredItems = [];
                });
            }
        });

        // Hàm thêm sản phẩm vào giỏ hàng
        $scope.addProductToCart = function (item) {
            var existingItem = $scope.selectedItems.find(function (selectedItem) {
                return selectedItem.id === item.id;
            });

            if (existingItem) {
                existingItem.quantity++;
            } else {
                item.quantity = 1;
                $scope.selectedItems.push(item);
            }

            $scope.updateTotal(); // Cập nhật tổng số sản phẩm và tổng giá trị
        };

        // Hàm giảm số lượng sản phẩm trong giỏ hàng
        $scope.decrementQuantity = function (index) {
            if ($scope.selectedItems[index].quantity > 1) {
                $scope.selectedItems[index].quantity--;
                $scope.updateTotal();
            }
        };

        // Hàm tăng số lượng sản phẩm trong giỏ hàng
        $scope.incrementQuantity = function (index) {
            $scope.selectedItems[index].quantity++;
            $scope.updateTotal();
        };

        // Hàm xóa sản phẩm khỏi giỏ hàng
        $scope.removeFromCart = function (index) {
            $scope.selectedItems.splice(index, 1);
            $scope.updateTotal();
        };

        // Cập nhật tổng số sản phẩm và tổng giá trị
        $scope.updateTotal = function () {
            $scope.totalQuantity = $scope.selectedItems.reduce(function (total, item) {
                return total + item.quantity;
            }, 0);

            $scope.totalPrice = $scope.selectedItems.reduce(function (total, item) {
                return total + (item.quantity * item.discountedPrice);
            }, 0).toFixed(2);

            // Gọi API để cập nhật danh sách voucher nếu cả hai thông tin userID và amount đều có dữ liệu
            if ($scope.totalPrice) {
                $scope.getVouchersByAmountOrder($scope.totalPrice); // Cập nhật danh sách voucher
            }
        };

        // Hiển thị modal thông báo
        $scope.showNotificationModal = function() {
            var modal = new bootstrap.Modal(document.getElementById('notificationModal'));
            modal.show();
        };

        // Hàm tạo đơn hàng và chi tiết đơn hàng
        $scope.submitOrder = function () {
            if (!$scope.selectedCustomer.id || $scope.selectedItems.length === 0) {
                $scope.notifiMessage = 'Please select a customer and add items to the cart before submitting the order.';
                $scope.showNotificationModal(); // Hiển thị modal với thông báo
                return; // Ngăn người dùng tiếp tục thực hiện hành động
            }

            // Chuẩn bị dữ liệu đơn hàng
            var orderData = {
                userID: $scope.selectedCustomer.id,
                voucherID: $scope.selectedVoucher ? $scope.selectedVoucher.id : -1,
                orderDate: new Date(),
                totalMoney: $scope.totalPrice,
                orderStatus: 'Shipping'
            };

            var orderDetailsData = $scope.selectedItems.map(function (item) {
                return {
                    productVersionID: item.id,
                    quantity: item.quantity,
                    price: item.price,
                    discountPrice: item.discountedPrice
                };
            });

            // Gửi dữ liệu đơn hàng và chi tiết đơn hàng lên server
            $http.post("/api/addNewOrder", {
                orderData: orderData,
                orderDetailData: orderDetailsData
            })
                .then(function (response) {
                    if (response.data === true) {
                        $scope.notifiMessage = 'Order created successfully!';
                        // Xóa giỏ hàng sau khi tạo đơn hàng thành công
                        $scope.selectedItems = [];
                        $scope.updateTotal();
                        // Cập nhật danh sách đơn hàng
                        $scope.getOrderDetails();
                    } else {
                        $scope.notifiMessage = 'Failed to create order. Please try again later.';
                    }
                    $scope.showNotificationModal(); // Hiển thị modal với thông báo
                })
                .catch(function (error) {
                    console.error('Error creating order:', error);
                    console.log(orderData, orderDetailsData);
                    $scope.notifiMessage = 'Failed to create order. Please try again later.';
                    $scope.showNotificationModal(); // Hiển thị modal với thông báo
                });
        };

        // Hàm để lấy chi tiết đơn hàng từ API
        $scope.fetchOrderDetails = function (orderId) {
            $http.get('/api/get-order?orderId=' + orderId)
                .then(function (response1) {
                    $scope.order = response1.data;
                    $http.get('/api/get-order-detail?orderId=' + orderId)
                        .then(function (response2) {
                            $scope.carts = response2.data;
                            createAndShowModal($scope.order, $scope.carts); // Truyền order và orderDetails vào hàm createAndShowModal
                        })
                        .catch(function (error) {
                            console.error('Error:', error);
                        });
                })
                .catch(function (error) {
                    console.error('Error:', error);
                });
        };

        // Hàm gọi khi nút được nhấn
        $scope.getID = function (event) {
            var orderId = event.target.id || event.target.parentElement.id;
            $scope.fetchOrderDetails(orderId);
        };

        // Hàm để tạo và hiển thị modal
        function createAndShowModal(order, orderDetails) { // Nhận order và orderDetails làm tham số
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
                                            <tr ng-repeat="item in carts" >
                                                <td class="px-0 mx-0 col-1 py-3" ng-if="item.quantity>0">
                                                    <div class="d-flex align-items-center">
                                                        <img ng-src="{{item.image}}" class="img-fluid w-50">
                                                    </div>
                                                </td>
                                                <td class="pt-4"  ng-if="item.quantity>0">
                                                    <h6>{{item.productVersionName}}</h6>
                                                </td>
                                                <td class="py-4"  ng-if="item.quantity>0">{{item.quantity}}</td>
                                                <td class="py-2"  ng-if="item.quantity>0">
                                                    <s style="font-size: 12px">{{item.price}}</s>
                                                    <h6>{{item.disCountPrice | number: 2}}</h6>
                                                </td>
                                                <td class="py-4"  ng-if="item.quantity>0">
                                                    <h6>{{item.price - item.disCountPrice | number: 2}}</h6>
                                                </td>
                                                <td class="py-4"  ng-if="item.quantity>0">
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
                                            <div class="col-6 offset-4 text-end d-flex">
                                                <span class="text-danger">$ {{order.totalMoney}}</span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-2 d-flex">
                                                <h6>Discount:</h6>
                                            </div>
                                            <div class="col-6 offset-4 text-end d-flex">
                                                <span class="text-danger">$ {{ order.voucherID ? (order.totalMoney * order.voucherID.discountLevel / 100) : 0 }}</span>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-2 d-flex">
                                                <h3 class="text-danger">Total:</h3>
                                            </div>
                                            <div class="col-6 offset-4 text-end d-flex">
                                                <h3 class="text-danger">$ {{ order.voucherID ? (order.totalMoney - (order.totalMoney * order.voucherID.discountLevel / 100)) : order.totalMoney }}</h3>
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

        // Hàm gọi API để lấy danh sách đơn hàng
        $scope.getOrderDetails = function () {
            $http.get("/api/get-all-order")
                .then(function (response) {
                    $scope.orderDetails = response.data;
                })
                .catch(function (error) {
                    console.error('Error fetching order details:', error);
                });
        };

        $scope.getOrderDetails();
    });
