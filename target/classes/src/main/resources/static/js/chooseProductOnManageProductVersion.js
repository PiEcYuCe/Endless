var inputElement = document.getElementById("exampleDataList");

// Lắng nghe sự kiện input
inputElement.addEventListener('input', function () {
    var options = document.querySelectorAll('#datalistOptions option');
    var prodIDInput = document.getElementById("prodID");
    var prodNameInput = document.getElementById("prodName");

    // Kiểm tra và thiết lập giá trị của các trường ẩn
    options.forEach(function (option) {
        if (option.value === inputElement.value) {
            prodIDInput.value = option.id;
            prodNameInput.value = option.value;
        }
    });
});

