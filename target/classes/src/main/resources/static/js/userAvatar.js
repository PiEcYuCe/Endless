document.getElementById('userModel-avatar').addEventListener('click', function(event) {
    event.preventDefault();
    var element = document.getElementById('input-file');
    element.click();
});

document.getElementById('input-file').addEventListener('change', function(event) {
    var file = event.target.files[0];
    if (file) {
        var reader = new FileReader();
        reader.onload = function(e) {
            var img = document.getElementById('userModel-avatar');
            img.src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
});