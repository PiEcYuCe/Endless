
var addNewAttributeRow = function (element) {
    var container = document.getElementById('versionAttributesContainer');
    let id = parseInt(element.id.replace(/\D/g, "") + 1);
    var newVersionAttribute = document.createElement('div');
    newVersionAttribute.classList.add('version-attribute', 'mb-3');
    let attributeName = document.getElementById("attributeName"+element.id.replace(/\D/g, ""))
    attributeName.id = "attributeName"+id;
    newVersionAttribute.innerHTML = `
            <div class="row ">
                <div class="col-6">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="attributeName" class="form-label">Attribute Name</label>
                            ${attributeName.outerHTML}
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="attributeValue" class="form-label">Attribute Value</label>
                            <select class="form-select" id="attributeValue${id}" name="attributeValue">
                                <option selected>Choose a value...</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-6 mt-4 pt-2">
                    <button type="button" class="btn btn-secondary mb-3" id="attribute${id}"
                            onclick="addNewAttributeRow(this)">Add new Attribute
                    </button>
                </div>
            </div>
        `;
    container.appendChild(newVersionAttribute);
    var newButton = document.createElement('button');
    newButton.id = `attributeButton${id}`;
    newButton.type = 'button';
    newButton.classList = 'btn btn-danger mb-3';
    newButton.innerHTML = 'Remove';
    newButton.addEventListener('click', function () {
        deleteAttributeRow(this);
    });
    element.parentNode.replaceChild(newButton, element);

    // Populate attribute dropdown options
    var attributeNameSelect = document.getElementById('attributeName' + id);
    attributes.forEach(function (attribute) {
        var option = document.createElement('option');
        option.value = attribute.id;
        option.text = attribute.attributeName;
        attributeNameSelect.appendChild(option);
    });
}
