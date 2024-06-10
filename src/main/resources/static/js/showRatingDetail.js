function removeModal() {
    var modalElement = document.getElementById("ratingModal");
    modalElement.remove();
}

function displayRating(buttonElement) {
    let avatar = document.getElementById('avatar'+buttonElement.id).value;
    let customerName = document.getElementById('fullname'+buttonElement.id).value;
    let comment = document.getElementById('comment'+buttonElement.id).value;
    let pictureName = 'picture'+buttonElement.id;
    let pictures = document.querySelectorAll('[name="'+pictureName+'"]');
    let firstPicture = pictures.length > 0 ? pictures[0].value : '';

    var modalContainer = document.createElement('div');
    modalContainer.innerHTML = `
       <div class="modal" id="ratingModal" tabindex="-1" aria-labelledby="ratingModalLabel" onclick="removeModal()">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="ratingModalLabel"></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="ratingContent">
                    <div class="mb-3">
                         <div class="p-2 text-center">
                             <img src="${avatar}" class="img-fluid col-2 mx-3 rounded-circle">
                             <h6>${customerName}</h6>
                             <small>${comment}</small>
                             <div class="row d-flex justify-content-center">
                                 <img src="${firstPicture}" class="img-fluid col-4 p-1" alt="">
                             </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `;

    document.body.appendChild(modalContainer);

    var modalElement = document.getElementById("ratingModal");
    var modal = new bootstrap.Modal(modalElement);

    modal.show();
}