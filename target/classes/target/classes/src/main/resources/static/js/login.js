const sign_in = document.querySelector("#sign-in-btn");
const sign_up = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");

sign_up.addEventListener('click', () => {
    container.classList.add('sign-up-mode');
});

sign_in.addEventListener('click', () => {
    container.classList.remove('sign-up-mode');
});

document.addEventListener('DOMContentLoaded', function() {
    const inputFields = document.querySelectorAll('.errorInput');

    inputFields.forEach(function(inputField) {
        // Xử lý kích thước ban đầu cho placeholder
        updatePlaceholderSize(inputField);

        // Xử lý sự kiện khi nhập vào inputField
        inputField.addEventListener('input', function() {
            updatePlaceholderSize(inputField);
        });
    });
});

function updatePlaceholderSize(inputField) {
    const placeholderTextLength = inputField.getAttribute('placeholder').length;
    let newFontSize = 1.3 - (placeholderTextLength * 0.1);

    // Giảm kích thước của placeholder nếu số lượng ký tự lớn hơn 15
    if (placeholderTextLength > 40) {
        newFontSize = 0.75; // Kích thước tối thiểu
    }

    // Đặt kích thước cho placeholder
    inputField.style.setProperty('--placeholder-font-size', newFontSize + 'rem');
}



