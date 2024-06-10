const productVersionModels = [];

fetch('/api/productVersions')
    .then(response => response.json())
    .then(data => {
        productVersionModels.push(...data);
        console.log(productVersionModels);
    });


