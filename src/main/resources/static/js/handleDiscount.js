var app = angular.module('myApp', []);

app.controller('myCtrl', function($scope, $http) {
    $scope.promotions = [];
    $scope.selectedPromotion = null;
    $scope.selectedDiscountLevel = null;
    $scope.selectedStartDate = null;
    $scope.selectedEndDate = null;
    $scope.discountLevels = [];
    $scope.selectedProducts = [];
    $scope.selectedProductIds = []; // Array to hold selected product IDs
    $scope.filterName = '';
    $scope.filterCategory = null;
    $scope.filterBrand = null;
    $scope.sortOption = 'id_asc';
    $scope.showCreateButton = false;
    $scope.showApplyButton = false;

    $scope.categories = [];
    $scope.brands = [];

    // Function to load promotions and reset product-related data
    $scope.loadPromotions = function() {
        $http.get('/api/get-all-discount')
            .then(function(response) {
                $scope.promotions = response.data;
                initializeFilters($scope.promotions);
                $scope.selectedProducts = [];
                $scope.selectedProductIds = [];
                $scope.selectedDiscountLevel = null;
                $scope.showCreateButton = false;
                $scope.showApplyButton = false;
            })
            .catch(function(error) {
                console.error('Error fetching promotions:', error);
            });
    };

    function initializeFilters(promotions) {
        let uniqueCategories = new Map();
        let uniqueBrands = new Map();

        promotions.forEach(promotion => {
            promotion.promotionDetails.forEach(detail => {
                detail.promotionProducts.forEach(product => {
                    let categoryKey = product.productVersionID.productID.categoryID.id;
                    let brandKey = product.productVersionID.productID.brandID.id;

                    if (!uniqueCategories.has(categoryKey)) {
                        uniqueCategories.set(categoryKey, product.productVersionID.productID.categoryID);
                    }

                    if (!uniqueBrands.has(brandKey)) {
                        uniqueBrands.set(brandKey, product.productVersionID.productID.brandID);
                    }
                });
            });
        });

        $scope.categories = Array.from(uniqueCategories.values());
        $scope.brands = Array.from(uniqueBrands.values());
    }

    $scope.updatePromotionDetails = function() {
        if ($scope.selectedPromotion) {
            $scope.selectedStartDate = new Date($scope.selectedPromotion.startDate);
            $scope.selectedEndDate = new Date($scope.selectedPromotion.endDate);

            let uniqueLevels = new Set();
            $scope.selectedPromotion.promotionDetails.forEach(detail => {
                uniqueLevels.add(detail.discountLevel);
            });

            $scope.discountLevels = Array.from(uniqueLevels);
            $scope.selectedDiscountLevel = null;
            $scope.selectedProducts = [];
            $scope.selectedProductIds = []; // Reset selected product IDs
            $scope.showCreateButton = false;
            $scope.showApplyButton = false;
        }
    };

    $scope.updateProductDetails = function() {
        if ($scope.selectedPromotion && $scope.selectedDiscountLevel !== null) {
            let selectedDetail = $scope.selectedPromotion.promotionDetails.find(detail => detail.discountLevel === $scope.selectedDiscountLevel);
            if (selectedDetail) {
                $scope.selectedProducts = selectedDetail.promotionProducts.map(product => ({
                    id: product.productVersionID.id,
                    promotionProductId: product.id, // Add promotionProductId to the product object
                    category: product.productVersionID.productID.categoryID,
                    brand: product.productVersionID.productID.brandID,
                    name: product.productVersionID.versionName,
                    price: product.productVersionID.price,
                    quantity: product.productVersionID.quantity,
                }));
            } else {
                $scope.selectedProducts = [];
            }

            // Check if selectedDiscountLevel is in datalistOptions
            let foundInDatalist = $scope.discountLevels.includes($scope.selectedDiscountLevel);

            // Show Create button only if selectedDiscountLevel is not found in datalistOptions
            $scope.showCreateButton = !foundInDatalist;
            // Show Apply button only if selectedDiscountLevel is found in datalistOptions
            $scope.showApplyButton = foundInDatalist;
        }
    };

    $scope.customFilter = function(product) {
        return (!$scope.filterName || product.name.toLowerCase().includes($scope.filterName.toLowerCase())) &&
            (!$scope.filterCategory || product.category.id === $scope.filterCategory.id) &&
            (!$scope.filterBrand || product.brand.id === $scope.filterBrand.id);
    };

    $scope.sortFunction = function(product) {
        switch ($scope.sortOption) {
            case 'id_asc':
                return product.id;
            case 'id_desc':
                return -product.id;
            case 'name_asc':
                return product.name.toLowerCase();
            case 'name_desc':
                return -product.name.toLowerCase();
            default:
                return product.id;
        }
    };

    $scope.removeSelectedProducts = function() {
        if ($scope.selectedProductIds.length > 0) {
            $http.post('/api/remove-promotion-product', null, {
                params: {
                    promotionProductID: $scope.selectedProductIds
                }
            }).then(function(response) {
                // API call success
                if (response.data) {
                    $scope.notifiMessage = "Products removed successfully.";
                    $('#notificationModal').modal('show');
                    $scope.loadPromotions(); // Reload promotions data
                } else {
                    $scope.notifiMessage = "Failed to remove products. Please try again.";
                    $('#notificationModal').modal('show');
                }
            }).catch(function(error) {
                // API call failed
                console.error('Error removing promotion products:', error);
                $scope.notifiMessage = "Failed to remove products due to server error. Please try again later.";
                $('#notificationModal').modal('show');
            });
        } else {
            $scope.notifiMessage = "No products selected.";
            $('#notificationModal').modal('show');
        }
    };

    $scope.createDiscount = function() {
        // Check if selectedPromotion and selectedDiscountLevel are valid
        if ($scope.selectedPromotion && $scope.selectedDiscountLevel !== null) {
            // Perform API call to add new promotion detail
            $http.post('/api/add-new-promotion-detail', null, {
                params: {
                    promotionID: $scope.selectedPromotion.id,
                    DiscountLevel: $scope.selectedDiscountLevel
                }
            }).then(function(response) {
                // API call success
                if (response.data) {
                    $scope.notifiMessage = "Promotion detail added successfully.";
                    $scope.loadPromotions(); // Reload promotions data
                } else {
                    $scope.notifiMessage = "Failed to add promotion detail. Please try again.";
                }
                $('#notificationModal').modal('show');
            }).catch(function(error) {
                // API call failed
                console.error('Error adding promotion detail:', error);
                $scope.notifiMessage = "Failed to add promotion detail due to server error. Please try again later.";
                $('#notificationModal').modal('show');
            });
        }
    };

    $scope.toggleProductSelection = function(product) {
        const index = $scope.selectedProductIds.indexOf(product.promotionProductId);
        if (index > -1) {
            $scope.selectedProductIds.splice(index, 1);
        } else {
            $scope.selectedProductIds.push(product.promotionProductId);
        }
    };

    // Load initial promotions data
    $scope.loadPromotions();
});
