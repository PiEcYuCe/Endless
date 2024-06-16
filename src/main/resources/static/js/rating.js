// Function to highlight the selected star
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

// Function to handle form submission
function submitEvaluation() {
    const orderId = document.getElementById("orderIdInput").value;
    const productVersionID = document.getElementById('prodVersionSelect').value;
    const review = document.getElementById('reviewTextarea').value;
    const selectedStars = document.querySelectorAll('.fas.fa-star').length;
    const selectedImages = document.getElementById('imageInput').files;

    // Create FormData object
    const formData = new FormData();
    formData.append('orderId', orderId);
    formData.append('productVersionID', productVersionID);
    formData.append('comment', review);
    formData.append('rating', selectedStars.toString());
    for (let i = 0; i < selectedImages.length; i++) {
        formData.append('image', selectedImages[i]);
    }

    // Make API call to save rating
    fetch('/api/save-rating', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to save rating');
            }
            return response.json();
        })
        .then(data => {
            alert('Rating saved successfully!');
            // Reset form and close modal
            resetForm();
            closeModal();
        })
        .catch(error => {
            console.error('Error saving rating:', error);
            alert('Error saving rating. Please try again.');
        });
}

// Function to reset form fields
function resetForm() {
    document.getElementById('orderIdInput').value = '';
    document.getElementById('prodVersionSelect').selectedIndex = 0;
    document.getElementById('reviewTextarea').value = '';
    document.querySelectorAll('.fas.fa-star').forEach(star => {
        star.classList.remove('fas', 'fa-star');
        star.classList.add('far', 'fa-star');
    });
    document.getElementById('imageContainer').innerHTML = '';
}

// Function to close modal
function closeModal() {
    const modal = new bootstrap.Modal(document.getElementById('ratingFormModal'));
    modal.hide();
}

// Function to handle image upload
document.getElementById('imageInput').addEventListener('change', function(event) {
    const files = event.target.files;
    const imageContainer = document.getElementById('imageContainer');
    imageContainer.innerHTML = ''; // Clear old images

    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();

        reader.onload = function(e) {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.className = 'img-fluid col-4 p-1';
            imageContainer.appendChild(img);
        }

        reader.readAsDataURL(file);
    }
});

// Function to show rating modal and populate product versions
function showRatingModal(button) {
    const buttonId = button.id;
    const orderId = buttonId.split('-')[1];
    document.getElementById("orderIdInput").value = orderId;
    fetch(`/api/get-order?orderId=${orderId}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);

            if (data && data.orderDetails) {
                const select = document.getElementById('prodVersionSelect');
                if (!select) {
                    console.error('Element with id "prodVersionSelect" not found.');
                    return;
                }

                // Clear previous options
                select.innerHTML = '<option value="" disabled selected>Select product version</option>';

                // Populate options
                data.orderDetails.forEach(orderDetail => {
                    const option = document.createElement('option');
                    option.value = orderDetail.productVersionID.id;
                    option.textContent = orderDetail.productVersionID.versionName;
                    select.appendChild(option);
                });

                // Show modal
                const modal = new bootstrap.Modal(document.getElementById('ratingFormModal'));
                modal.show();
            } else {
                console.error('Empty or invalid data received from API.');
            }
        })
        .catch(error => {
            console.error('Error fetching product versions:', error);
        });
}
