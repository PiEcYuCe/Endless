
function chooseStar(number) {
    for (let i = 1; i <= 5; i++) {
        const starElement = document.getElementById('star' + i);
        if (starElement) {
            if (i <= number) {
                starElement.classList.remove('far', 'fa-star');
                starElement.classList.add('fas', 'fa-star');
            } else {
                starElement.classList.remove('fas', 'fa-star');
                starElement.classList.add('far', 'fa-star');
            }
        }
    }
}

// JavaScript function to handle form submission
function submitEvaluation() {
    const review = document.getElementById('reviewTextarea').value;
    const selectedStars = document.querySelectorAll('.fas.fa-star').length;
    const selectedImages = document.getElementById('imageInput').files;

    // Your logic to handle form submission
    console.log('Review:', review);
    console.log('Stars:', selectedStars);
    console.log('Images:', selectedImages);

    // Close the modal after submission if needed
    const modal = document.getElementById('ratingFormModal');
    const bootstrapModal = bootstrap.Modal.getInstance(modal);
    bootstrapModal.hide();
}

// JavaScript function to handle image upload
document.getElementById('imageInput').addEventListener('change', function(event) {
    var files = event.target.files;
    var imageContainer = document.getElementById('imageContainer');
    imageContainer.innerHTML = ''; // Clear old images

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