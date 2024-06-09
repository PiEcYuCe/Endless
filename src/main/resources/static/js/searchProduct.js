const productVersionModels = [];

fetch('/api/productVersions')
    .then(response => response.json())
    .then(data => {
        productVersionModels.push(...data);
        console.log(productVersionModels);
    });

function updateTotal() {
    let productQuantity = 0;
    let totalQuantity = 0;
    let totalPrice = 0;

    const rows = document.querySelectorAll('tbody tr');
    productQuantity = rows.length;
    rows.forEach(row => {
        const quantity = parseInt(row.querySelector('input[type="number"]').value, 10);
        const price = parseFloat(row.querySelector('.price').innerText);
        totalQuantity += quantity;
        totalPrice += quantity * price;
    });

    document.getElementById('product-quantity').innerText = productQuantity;
    document.getElementById('total-quantity').innerText = totalQuantity;
    document.getElementById('total-price').innerText = totalPrice.toFixed(2);
}

function deleteRow(rowId) {
    const row = document.getElementById(rowId);
    if (row) {
        row.remove();
        updateTotal();
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById('productInput');
    const ul = document.getElementById('productList');
    const tableBody = document.querySelector('tbody');

    function closeAllLists() {
        ul.innerHTML = '';
    }

    function addProductToTable(item) {
        let existingRow = document.getElementById(`row_${item.id}`);
        if (existingRow) {
            const quantityInput = document.getElementById(`quantity_${item.id}`);
            quantityInput.value = parseInt(quantityInput.value, 10) + 1;
        } else {
            const newRow = createTableRow(item);
            tableBody.appendChild(newRow);
        }
        updateTotal();
    }

    function createTableRow(item) {
        const newRow = document.createElement('tr');
        newRow.id = `row_${item.id}`;
        newRow.innerHTML = `
            <td class="px-0 mx-0 col-1 py-3">
                <div class="d-flex align-items-center">
                    <img src="${item.image}" class="img-fluid w-50">
                </div>
            </td>
            <td class="pt-4">
                <h6>${item.versionName}</h6>
            </td>
            <td class="d-flex py-4">
                <div class="input-group-prepend">
                    <button class="btn btn-outline-secondary minus-btn" type="button" data-id="quantity_${item.id}" data-price="${item.price}">-</button>
                </div>
                <input type="number" class="form-control col-6 text-end" id="quantity_${item.id}" min="0" value="1" style="width: 60px; max-height: 40px" onchange="updateTotal()">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary plus-btn" type="button" data-id="quantity_${item.id}" data-price="${item.price}">+</button>
                </div>
            </td>
            <td class="py-4 price">${item.price}</td>
            <td class="py-4">
                <button class="btn btn-danger delete-btn" data-row="row_${item.id}">
                    <i class="bi bi-trash-fill"></i>
                </button>
            </td>
        `;
        return newRow;
    }

    function updateList(val) {
        closeAllLists();
        if (!val) return;

        let count = 0;
        productVersionModels.forEach(item => {
            if (item.versionName.toUpperCase().includes(val.toUpperCase()) && count < 10) {
                const li = document.createElement('li');
                li.innerHTML = `
                    <img src="${item.image}" style="width: 50px; height: auto; margin-right: 10px;">
                    <span>${item.versionName}</span>
                `;
                li.addEventListener('mousedown', () => addProductToTable(item));
                ul.appendChild(li);
                count++;
            }
        });
    }

    function debounce(func, delay) {
        let debounceTimer;
        return function(...args) {
            clearTimeout(debounceTimer);
            debounceTimer = setTimeout(() => func.apply(this, args), delay);
        };
    }

    input.addEventListener('input', debounce(() => {
        updateList(input.value);
    }, 300));

    document.addEventListener('click', event => {
        if (!event.target.closest('#productInput') && !event.target.closest('#productList')) {
            closeAllLists();
        }
    });

    document.addEventListener('click', event => {
        const target = event.target;
        if (target.classList.contains('plus-btn')) {
            const input = document.getElementById(target.getAttribute('data-id'));
            input.value = parseInt(input.value, 10) + 1;
            updateTotal();
        }
        if (target.classList.contains('minus-btn')) {
            const input = document.getElementById(target.getAttribute('data-id'));
            const value = parseInt(input.value, 10);
            if (value > 0) {
                input.value = value - 1;
                updateTotal();
            }
        }
        if (target.classList.contains('delete-btn')) {
            const rowId = target.getAttribute('data-row');
            deleteRow(rowId);
        }
    });
});
