document.addEventListener("DOMContentLoaded", function() {
    // Kiểm tra và hiển thị Success Modal
    if (document.querySelector("#successModal")) {
        var successModal = new bootstrap.Modal(document.getElementById('successModal'));
        successModal.show();
    }

    // Kiểm tra và hiển thị Error Modal
    if (document.querySelector("#errorModal")) {
        var errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
        errorModal.show();
    }

    // Kiểm tra và hiển thị Warning Modal
    if (document.querySelector("#warningModal")) {
        var warningModal = new bootstrap.Modal(document.getElementById('warningModal'));
        warningModal.show();
    }
});
