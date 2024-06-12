var app = angular.module('myApp', []);

app.controller('myCtrl', function ($scope, $http) {
    $scope.cartItems = [];
    $scope.vouchers = [];
    $scope.customerInfo = {};
    $scope.totalAmount = 0;
    $scope.discount = 0;
    $scope.notifiMessage = '';
    $scope.selectedItems = [];
    $scope.productSelected = [];
    $scope.selectedItem = null;
    $scope.selectedVoucher = '';

    // Fetch cart items
    $http.get("/api/get-cart-list")
        .then(function (response) {
            if (response.status === 200 && response.data) {
                $scope.cartItems = response.data;
                $scope.updateTotalAmount();
            } else {
                console.error('Error fetching cart items:', response);
            }
        })
        .catch(function (error) {
            console.error('Error fetching cart items:', error);
        });

    // Fetch vouchers
    $http.get("/api/voucher-list")
        .then(function (response) {
            if (response.status === 200 && response.data) {
                $scope.vouchers = response.data;
            } else {
                console.error('Error fetching vouchers:', response);
            }
        })
        .catch(function (error) {
            console.error('Error fetching vouchers:', error);
        });

    // Fetch customer information
    $http.get("/api/user-infomation")
        .then(function (response) {
            if (response.status === 200 && response.data) {
                $scope.customerInfo = response.data;
            } else {
                console.error('Error fetching customer information:', response);
            }
        })
        .catch(function (error) {
            console.error('Error fetching customer information:', error);
        });

    // Update total amount
    $scope.updateTotalAmount = function () {
        $scope.totalAmount = $scope.selectedItems.reduce(function (total, item) {
            return total + (item.disCountPrice * item.quantity);
        }, 0);
        $scope.updateDiscount();
    };

    // Update discount
    $scope.updateDiscount = function () {
        $scope.discount = 0;
        if ($scope.selectedVoucher) {
            var selectedVoucher = $scope.vouchers.find(function (voucher) {
                return voucher.voucherCode === $scope.selectedVoucher;
            });
            if (selectedVoucher) {
                if (selectedVoucher.discountForm === 'fixed') {
                    $scope.discount = selectedVoucher.discountLevel;
                } else {
                    if ($scope.totalAmount < selectedVoucher.leastDiscount) {
                        $scope.discount = selectedVoucher.leastDiscount;
                    } else if ($scope.totalAmount > selectedVoucher.biggestDiscount) {
                        $scope.discount = selectedVoucher.biggestDiscount;
                    } else {
                        $scope.discount = 0.1 * selectedVoucher.discountLevel * $scope.totalAmount;
                    }
                }
                console.log(selectedVoucher);
            }
        }
    };

    $scope.$watch('selectedItems', function () {
        $scope.updateTotalAmount();
    }, true);

    $scope.$watch('selectedVoucher', function () {
        $scope.updateDiscount();
    });

    // Update quantity
    $scope.updateQuantity = function (item) {
        $http.put('/api/cart/update', null, {
            params: { id: item.id, quantity: item.quantity }
        }).then(function(response) {
            if (response.status === 200) {
                $scope.updateTotalAmount();
            } else {
                console.error('Error updating cart item quantity:', response);
            }
        }).catch(function(error) {
            console.error('Error updating cart item quantity:', error);
        });
    };

    // Decrement quantity
    $scope.decrementQuantity = function (item) {
        if (item.quantity > 1) {
            item.quantity--;
            $scope.updateQuantity(item);
        }
    };

    // Increment quantity
    $scope.incrementQuantity = function (item) {
        item.quantity++;
        $scope.updateQuantity(item);
    };

    // Remove item from cart
    $scope.removeItem = function (itemId) {
        $http.delete('/api/cart/delete', { params: { id: itemId } })
            .then(function (response) {
                if (response.status === 200) {
                    $scope.cartItems = $scope.cartItems.filter(function (item) {
                        return item.id !== itemId;
                    });
                    $scope.updateTotalAmount();
                } else {
                    console.error('Error deleting cart item:', response);
                }
            })
            .catch(function (error) {
                console.error('Error deleting cart item:', error);
            });
    };

    // Select voucher
    $scope.selectVoucher = function () {
        if ($scope.selectedVoucher) {
            var selectedVoucher = $scope.vouchers.find(function (voucher) {
                return voucher.voucherCode === $scope.selectedVoucher;
            });
            if (selectedVoucher) {
                $scope.updateDiscount();
            }
        }
    };

    // Toggle item selection
    $scope.toggleSelection = function (item) {
        item.isSelected = !item.isSelected;
        if (item.isSelected) {
            $scope.selectedItems.push(item);
            $scope.productSelected.push(item);
            $scope.selectedItem = angular.copy(item);
        } else {
            var index = $scope.selectedItems.indexOf(item);
            if (index !== -1) {
                $scope.selectedItems.splice(index, 1);
            }
            var selectedIndex = $scope.productSelected.findIndex(function (selectedItem) {
                return selectedItem.id === item.id;
            });
            if (selectedIndex !== -1) {
                $scope.productSelected.splice(selectedIndex, 1);
            }
            $scope.selectedItem = null;
        }
        $scope.updateDiscount();
    };
    $scope.showNotificationModal = function () {
        var modal = new bootstrap.Modal(document.getElementById('notificationModal'));
        modal.show();

        // Đóng modal sau một khoảng thời gian
        setTimeout(function () {
            modal.hide();
        }, 3000); // 5000 là số miligiây, tương ứng với 5 giây
    };

    // Submit order
    // Submit order
    $scope.submitOrder = function () {
        if ($scope.selectedItems.length === 0) {
            $scope.notifiMessage = 'Please select a customer and add items to the cart before submitting the order.';
            $scope.showNotificationModal();
            return;
        }

        var orderData = {
            userID: $scope.customerInfo.id,
            voucherID: $scope.selectedVoucher.id ? $scope.selectedVoucher.id : 1,
            orderDate: new Date(),
            totalMoney: $scope.totalAmount - $scope.discount,
            orderStatus: 'Processing'
        };

        var orderDetailsData = $scope.selectedItems.map(function (item) {
            return {
                productVersionID: item.id,
                quantity: item.quantity,
                price: item.price,
                discountPrice: item.disCountPrice
            };
        });

        $http.post("/api/addNewOrder", {
            orderData: orderData,
            orderDetailData: orderDetailsData
        })
            .then(function (response) {
                if (response.data === true) {
                    $scope.notifiMessage = 'Order created successfully!';

                    // Xóa các mục hàng đã chọn khỏi giỏ hàng
                    $scope.selectedItems.forEach(function (item) {
                        $scope.removeItem(item.id);
                    });

                    // Cập nhật lại tổng số tiền và chiết khấu
                    $scope.totalAmount = 0;
                    $scope.discount = 0;

                    // Cập nhật hiển thị giỏ hàng sau khi xóa
                    $http.get("/api/get-cart-list")
                        .then(function (response) {
                            if (response.status === 200 && response.data) {
                                $scope.cartItems = response.data;
                                $scope.updateTotalAmount();
                            } else {
                                console.error('Error fetching cart items:', response);
                            }
                        })
                        .catch(function (error) {
                            console.error('Error fetching cart items:', error);
                        });

                } else {
                    $scope.notifiMessage = 'Failed to create order. Please try again later.';
                    console.log(orderData, orderDetailsData);
                }
                $scope.showNotificationModal();
            })
            .catch(function (error) {
                console.error('Error creating order:', error);
                console.log(orderData, orderDetailsData);
                $scope.notifiMessage = 'Failed to create order. Please try again later.';
                $scope.showNotificationModal();
            });
    };

});
