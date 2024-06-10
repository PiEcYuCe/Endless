function updateQuantity(itemId, quantityChange) {
    var inputField = document.getElementById('quantityInput' + itemId);
    var currentValue = parseInt(inputField.value);
    var newValue = currentValue + quantityChange;
    if (newValue < 1) {
        newValue = 1;
    }
    inputField.value = newValue;
}