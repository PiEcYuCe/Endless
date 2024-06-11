var modalContainer = document.createElement('div');
modalContainer.innerHTML = `
    <!-- Notification Modal -->
    <div class="modal fade" id="notificationModal" tabindex="-1" aria-labelledby="notificationModalLabel" aria-hidden="true">
        <div class="modal-dialog text-center">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="notificationModalLabel">Purchase Notification</h5>
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
    // Initialize variables
    $scope.productVersionModels = [];
    $scope.selectedItems = [];
    $scope.totalQuantity = 0;
    $scope.totalPrice = 0;
    $scope.notifiMessage = '';

    // Fetch product versions
    $http.get("/api/productVersions")
        .then(function (response) {
            $scope.productVersionModels = response.data;
        })
        .catch(function (error) {
            console.error('Error fetching product versions:', error);
        });

    // Fetch user info
    $http.get("/user-data")
        .then(function (response) {
            $scope.userData = response.data;
        })
        .catch(function (error) {
            console.error('Error fetching user data:', error);
        });

    // Update total amount
    $scope.updateTotalAmount = function () {
        $scope.totalPrice = $scope.selectedItems.reduce(function (total, item) {
            return total + (item.quantity * item.purchasePrice);
        }, 0).toFixed(2);
    };

    // Watch for changes in selected items
    $scope.$watch('selectedItems', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.updateTotalAmount();
        }
    }, true);

    // Update product list based on input value
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

    // Watch for changes in search query
    $scope.$watch('searchQuery', function (newVal, oldVal) {
        $scope.updateList(newVal);
    });

    // Close filtered list when clicking outside
    angular.element(document).on('click', function (event) {
        if (!event.target.closest('#productInput') && !event.target.closest('#productList')) {
            $scope.$apply(function () {
                $scope.filteredItems = [];
            });
        }
    });

    // Add product to cart
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

        $scope.updateTotal(); // Update total items and price
    };

    // Decrease product quantity in cart
    $scope.decrementQuantity = function (index) {
        if ($scope.selectedItems[index].quantity > 1) {
            $scope.selectedItems[index].quantity--;
            $scope.updateTotal();
        }
    };

    // Increase product quantity in cart
    $scope.incrementQuantity = function (index) {
        $scope.selectedItems[index].quantity++;
        $scope.updateTotal();
    };

    // Remove product from cart
    $scope.removeFromCart = function (index) {
        $scope.selectedItems.splice(index, 1);
        $scope.updateTotal();
    };

    // Update total items and price
    $scope.updateTotal = function () {
        $scope.totalQuantity = $scope.selectedItems.reduce(function (total, item) {
            return total + item.quantity;
        }, 0);

        $scope.totalPrice = $scope.selectedItems.reduce(function (total, item) {
            return total + (item.quantity * item.purchasePrice);
        }, 0).toFixed(2);
    };

    $scope.showNotificationModal = function () {
        var modal = new bootstrap.Modal(document.getElementById('notificationModal'));
        modal.show();
    };

    // Submit purchase order
    $scope.submitOrder = function () {
        if ($scope.selectedItems.length === 0) {
            $scope.notifiMessage = 'Please add items to the cart before submitting the order.';
            $scope.showNotificationModal(); // Show modal with message
            return; // Prevent further action
        }

        // Prepare purchase data
        var purchaseData = {
            purchaseDate: new Date(),
            purchaseStatus: 'Active',
            purchaseTotal: $scope.totalPrice
        };

        var purchaseDetailsData = $scope.selectedItems.map(function (item) {
            return {
                productVersionID: item.id,
                quantity: item.quantity,
                price: item.purchasePrice
            };
        });

        // Send purchase data to server
        $http.post("/api/addNewPurchaser", {
            purchaseData: purchaseData,
            purchaseDetailsData: purchaseDetailsData
        })
            .then(function (response) {
                if (response.data === true) {
                    $scope.notifiMessage = 'Purchase order created successfully!';
                    // Clear cart after successful order creation
                    $scope.selectedItems = [];
                    $scope.updateTotal();
                } else {
                    $scope.notifiMessage = 'Failed to create purchase order. Please try again later.';
                }
                $scope.showNotificationModal(); // Show modal with message
            })
            .catch(function (error) {
                console.error('Error creating purchase order:', error);
                $scope.notifiMessage = 'Failed to create purchase order. Please try again later.';
                $scope.showNotificationModal(); // Show modal with message
            });
    };
});
