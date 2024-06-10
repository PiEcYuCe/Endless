
var modalContainer = document.createElement('div');
modalContainer.innerHTML = `
    <!-- Notification Modal -->
<div class="modal fade" id="notificationModal" tabindex="-1" aria-labelledby="notificationModalLabel" aria-hidden="true">
    <div class="modal-dialog text-center">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="notificationModalLabel">Order Notification</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                {{notifiMessage}}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

`;

document.body.appendChild(modalContainer);

var app = angular.module('myApp', []);

app.controller('myCtrl', function ($scope, $http) {
    // Khởi tạo các biến
    $scope.productVersionModels = [];
    $scope.selectedItems = [];
    $scope.vouchers = [];
    $scope.selectedCustomer = {};
    $scope.totalQuantity = 0;
    $scope.totalPrice = 0;
    $scope.totalAmount = 0;
    $scope.notifiMessage = '';

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
        $http.get("/api/userInfo", {params: {userKey: searchCustomer}})
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
        $http.get("/api/getVoucherByAmountOrder", {params: {amount: amount}})
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
            $scope.saleValueList.push({value: voucher.voucherCode}); // Thêm mã voucher vào danh sách
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

    $scope.showNotificationModal = function() {
        var modal = new bootstrap.Modal(document.getElementById('notificationModal'));
        modal.show();
    };

    // Hàm tạo đơn hàng và chi tiết đơn hàng
    $scope.submitOrder = function () {
        if (!$scope.selectedCustomer.id || $scope.selectedItems.length === 0) {
            $scope.notifiMessage = 'Please select a customer and add items to the cart before submitting the order.';
            $scope.showNotificationModal(); // Show modal with message
            return; // Ngăn người dùng tiếp tục thực hiện hành động
        }

        // Chuẩn bị dữ liệu đơn hàng
        var orderData = {
            userID: $scope.selectedCustomer.id,
            voucherID: $scope.selectedVoucher ? $scope.selectedVoucher.id : 1,
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
                } else {
                    $scope.notifiMessage = 'Failed to create order. Please try again later.';
                }
                $scope.showNotificationModal(); // Show modal with message
            })
            .catch(function (error) {
                console.error('Error creating order:', error);
                console.log(orderData, orderDetailsData);
                $scope.notifiMessage = 'Failed to create order. Please try again later.';
                $scope.showNotificationModal(); // Show modal with message
            });
    };
});

