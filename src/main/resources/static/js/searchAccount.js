
    // Lắng nghe sự kiện khi nhấn nút Search
    document.querySelector('.btn-primary').addEventListener('click', function () {
    filterTable();
});

    // Lắng nghe sự kiện khi nhấn phím "Enter"
    document.getElementById('searchInput').addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
    filterTable();
}
});

    function filterTable() {
        var searchText = document.getElementById('searchInput').value.toLowerCase();
        var rows = document.getElementById('userTable').getElementsByTagName('tbody')[0].getElementsByTagName('tr');

        // Lặp qua từng dòng trong bảng
        for (var i = 0; i < rows.length; i++) {
            var username = rows[i].getElementsByTagName('td')[1].textContent.toLowerCase();
            // Kiểm tra nếu username không chứa chuỗi tìm kiếm thì ẩn dòng đó, ngược lại hiển thị
            if (username.indexOf(searchText) === -1) {
                rows[i].style.display = 'none';
            } else {
                rows[i].style.display = '';
            }
        }
    }