var modalContainer = document.createElement('div');
modalContainer.innerHTML = `
                    <div class="modal" id="cart">
                        <div class="modal-dialog">
                            <div class="modal-content" >
                                <div class="modal-header">
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>
                                <div class="modal-body text-center">
                                    <i class="bi bi-emoji-smile w-50"></i>
                                    <br>
                                    <span>Success !!!</span>
                                </div>

                            </div>
                        </div>
                    </div>
                `;
document.body.appendChild(modalContainer);
