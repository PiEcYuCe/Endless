var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope, $http) {
    // Lấy dữ liệu từ API sử dụng Angular $http service
    $http.get('/api/get-all-attribute')
        .then(function(response) {
            console.log(response.data); // Kiểm tra dữ liệu trả về từ API

            // Tạo nội dung HTML cho modal sau khi nhận dữ liệu từ API
            var modalHTML = `
            <!-- The Modal -->
            <div class="modal fade" id="viewModal" tabindex="-1" aria-labelledby="viewModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="viewModalLabel">Product Version Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="row col-10" style="margin-left: 5vw">
                                <div class="d-flex align-items-center py-2 mb-3" style="box-shadow: 0px 5px 10px gray;">
                                    <div class="col-2 offset-3">
                                        <img src="/images/productVersion/SamSungS22Utral5G.png" class="img-fluid me-3" alt="Product Image">
                                    </div>
                                    <div class="col-4 offset-1">
                                        <h5 class="mb-0">Version Name: <small id="versionName">Samsung S22 Ultra 5G</small></h5>
                                        <p class="mb-0">Status: <small class="text-muted">Active</small></p>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-12">
                                    <hr>
                                    <!-- Row 1 -->
                                    <div class="row mb-3">`;

            // Duyệt qua từng cặp key-value trong dữ liệu map
            angular.forEach(response.data, function(attribute, key) {
                modalHTML += `
                    <div class="col-4">
                        <label for="comboBox${key}">${attribute.attributeName}</label>
                        <select id="comboBox${key}" class="form-select">`;

                // Duyệt qua mảng các giá trị của thuộc tính và thêm vào combobox tương ứng
                angular.forEach(attribute.attributeValues, function(value) {
                    modalHTML += `<option value="${value.id}">${value.value}</option>`;
                });

                modalHTML += `
                        </select>
                    </div>`;
            });

            modalHTML += `
                                    </div>
                                    <!-- Add more rows of select boxes here -->
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </div>
            </div>`;

            // Tạo một đối tượng div để chứa nội dung HTML của modal
            var modalContainer = angular.element(document.createElement('div'));
            // Gán nội dung HTML đã tạo vào modalContainer
            modalContainer.html(modalHTML);
            // Chèn đối tượng div vào cuối thẻ body
            angular.element(document.body).append(modalContainer);
        })
        .catch(function(error) {
            console.error('Error fetching attribute data:', error);
        });
});
