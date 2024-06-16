document.getElementById('userModel-avatar').addEventListener('click', function (event) {
    event.preventDefault();
    var element = document.getElementById('input-file');

    // Tạo phần tử input nếu nó chưa tồn tại
    if (!element) {
        element = document.createElement('input');
        element.type = 'file';
        element.name = 'file';
        element.id = 'input-file';
        element.style.display = 'none';
        document.getElementById('form-my-info').appendChild(element);

        // Thêm sự kiện change chỉ một lần khi phần tử được tạo
        element.addEventListener('change', function (event) {
            var file = event.target.files[0];
            if (file) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    var img = document.getElementById('userModel-avatar');
                    img.src = e.target.result;
                }
                reader.readAsDataURL(file);
            }
        });
    }

    // Kích hoạt sự kiện click của input file
    element.click();
});