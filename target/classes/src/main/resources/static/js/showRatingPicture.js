document.getElementById('imageInput').addEventListener('change', function(event) {
    var files = event.target.files;
    var imageContainer = document.getElementById('imageContainer');
    imageContainer.innerHTML = ''; // Xóa các hình ảnh cũ

    for (var i = 0; i < files.length; i++) {
        var file = files[i];
        var reader = new FileReader();

        reader.onload = function(e) {
            var img = document.createElement('img');
            img.src = e.target.result;
            img.className = 'img-fluid col-4 p-1';
            imageContainer.appendChild(img);
        }

        reader.readAsDataURL(file);
    }
});