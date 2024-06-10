function saveAttributes() {
    const attributeContainer = document.getElementById('versionAttributesContainer');
    const attributes = attributeContainer.getElementsByClassName('version-attribute');
    const attributeData = [];

    for (let attribute of attributes) {
        const attributeName = attribute.querySelector('select[name="attributeName"]').value;
        const attributeValue = attribute.querySelector('select[name="attributeValue"]').value;

        if (attributeName !== '-1' && attributeValue !== '-1') {
            attributeData.push({
                attributeName: attributeName,
                attributeValue: attributeValue
            });
        }
    }

    // Send the data to the server
    fetch('/manage-product-version', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(attributeData)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            // Handle success response
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle error response
        });
}

function clearForm() {
    document.getElementById('versionAttributesContainer').innerHTML = '';
}