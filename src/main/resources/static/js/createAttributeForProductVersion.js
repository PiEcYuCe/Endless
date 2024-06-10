var chooseAttribute = function (element) {
    let options = document.createElement('div');
    let attributeId = element.value;
    let id =  parseInt(element.id.replace(/\D/g, ""));
    let AttributeValues = [];
    let attributeValuesDropdown = document.getElementById('attributeValue'+id);

    fetch('/api/attribute-value?attributeID=' + attributeId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            if (!Array.isArray(data)) {
                throw new TypeError('Expected an array but got ' + typeof data);
            }
            AttributeValues.push(...data);
            attributeValuesDropdown.innerHTML = '<option selected value="-1">Choose a value...</option>';
            AttributeValues.forEach(function (attributeValue) {
                let option = document.createElement('option');
                option.value = attributeValue.id;
                option.text = attributeValue.value;
                attributeValuesDropdown.appendChild(option);
            });
        })
        .catch(error => console.error('There has been a problem with your fetch operation:', error));
};
