function addToCartWithQuantity(productId, quantity) {
    const payload = {
        id: productId,
        quantity: quantity
    };

    axios.post('/api/cart/add-to-cart', null, {
        params: payload
    })
        .then(function(response) {
            if (response.status === 200 && response.data === true) {
                move(productId);
            } else {
                console.error('Failed to add product to cart with quantity:', quantity);
            }
        })
        .catch(function(error) {
            console.error('Error adding product to cart with quantity:', quantity, error);
        });
}

function addToCart(productId) {
    const defaultQuantity = 1;
    addToCartWithQuantity(productId, defaultQuantity);
}

function move(id) {
    let imgID = 'prodImg' + id;
    var productImage = document.getElementById(imgID);
    var cartIcon = document.getElementById('cart-icon');

    if (!productImage || !cartIcon) {
        console.error('Product image or cart icon not found!');
        return;
    }

    var productImageRect = productImage.getBoundingClientRect();
    var cartIconRect = cartIcon.getBoundingClientRect();

    var newProductImage = productImage.cloneNode(true);
    newProductImage.style.position = 'absolute';
    newProductImage.style.width = productImageRect.width + 'px';
    newProductImage.style.height = productImageRect.height + 'px';
    newProductImage.style.display = "flex";
    newProductImage.style.top = '0';
    newProductImage.style.left = '0';
    newProductImage.style.transition = 'transform 1.5s ease-in-out, opacity 1.5s ease-in-out';
    newProductImage.style.zIndex = '9999';
    newProductImage.style.pointerEvents = 'none';

    // Lấy ra phần tử cha của productImage
    var parentElement = productImage.parentNode;

    parentElement.appendChild(newProductImage);

    var offsetX = cartIconRect.left - productImageRect.left + (cartIconRect.width - productImageRect.width) / 2;
    var offsetY = cartIconRect.top - productImageRect.top + (cartIconRect.height - productImageRect.height) / 2;

    setTimeout(function() {
        newProductImage.style.transform = `translate(${offsetX}px, ${offsetY}px) scale(0.1)`;
        newProductImage.style.opacity = '0';
    }, 100);

    setTimeout(function() {
        parentElement.removeChild(newProductImage);
    }, 1600);
}