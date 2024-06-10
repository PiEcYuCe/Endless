document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("selectAll").addEventListener("change", function () {
        var checkboxes = document.querySelectorAll('.checkbox-item');
        checkboxes.forEach(function (checkbox) {
            checkbox.checked = document.getElementById("selectAll").checked;
        });
        updateDeleteButtonVisibility();
    });

    var checkboxes = document.querySelectorAll('.checkbox-item');
    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener("change", function () {
            updateDeleteButtonVisibility();
        });
    });

    function updateDeleteButtonVisibility() {
        var deleteButton = document.getElementById("deleteButton");
        var checkedCheckboxes = document.querySelectorAll('.checkbox-item:checked');
        if (checkedCheckboxes.length > 0) {
            deleteButton.style.display = "block";
        } else {
            deleteButton.style.display = "none";
        }
    }
});