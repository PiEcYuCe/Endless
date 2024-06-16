function showWarningModal(orderId) {
    fetch(`/api/show-waning-modal?orderId=${orderId}`)
        .then(response => response.json())
        .then(data => {
            // Update modal content with data received
            const modalTitle = document.getElementById('warningModalLabel');
            const modalMessage = document.getElementById('warningModalMessage');
            const modalButton = document.getElementById('warningModalButton');

            modalTitle.textContent = data.title;
            modalMessage.textContent = data.message;
            modalButton.href = data.button.link;
            modalButton.textContent = data.button.text;

            // Show the modal
            const modal = new bootstrap.Modal(document.getElementById('warningModal'));
            modal.show();
        })
        .catch(error => {
            console.error('Error fetching data:', error);
            // Handle errors if needed
        });
}



