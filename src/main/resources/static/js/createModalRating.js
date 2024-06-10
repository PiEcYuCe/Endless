var modalContainer = document.createElement('div');
modalContainer.innerHTML = `
                <div class="modal" id="evaluate" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content" >
                            <div class="modal-header">
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                            <div class="mb-3">
                                 <div class="p-2 text-center">
                                     <img src="${user?.avatar}" class="img-fluid col-2 mx-3 rounded-circle">
                                     <h6>${user?.fullname}</h6>
                                     <div class="mb-5 text-warning">
                                        <b><i class="fa-regular fa-star" id="start1" onclick="chooseStar(1)"></i></b>
                                        <b><i class="fa-regular fa-star" id="start2" onclick="chooseStar(2)"></i></b>
                                        <b><i class="fa-regular fa-star" id="start3" onclick="chooseStar(3)"></i></b>
                                        <b><i class="fa-regular fa-star" id="start4" onclick="chooseStar(4)"></i></b>
                                        <b><i class="fa-regular fa-star" id="start5" onclick="chooseStar(5)"></i></b>
                                     </div>

                                     <div class="mb-3">
                                        <textarea class="form-control" placeholder="Enter review" row="3"></textarea>
                                     </div>
                                     <div class="mb-3">
                                        <input type="file" id="imageInput" placeholder="add images"
                                        class="form-control" accept="image/*" multiple>
                                    </div>
                                     <div class="row d-flex justify-content-center" id="imageContainer">
                                     <img th:src="'rating/'" class="img-fluid col-4 p-1"  alt="">
                                     </div>
                                     <div>
                                     <button type="submit" class="btn btn-primary w-100">Evaluate</button>
                                     </div>
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
            `;
document.body.appendChild(modalContainer);