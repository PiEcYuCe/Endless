async function fetchProductStatistics() {
    try {
        // Fetch product statistics
        const [soldProducts, shippingProducts, lowStockProducts, outOfStockProducts] = await Promise.all([
            fetch('/api/product-sold').then(response => response.json()),
            fetch('/api/product-shipping').then(response => response.json()),
            fetch('/api/product-low-stock').then(response => response.json()),
            fetch('/api/product-out-of-stock').then(response => response.json())
        ]);

        // Update HTML elements with fetched data
        document.getElementById('soldProducts').innerText = soldProducts;
        document.getElementById('shippingProducts').innerText = shippingProducts;
        document.getElementById('lowStockProducts').innerText = lowStockProducts;
        document.getElementById('outOfStockProducts').innerText = outOfStockProducts;
    } catch (error) {
        console.error('Error fetching product statistics:', error);
    }
}

// Function to fetch product statistics
function fetchData() {
    fetchProductStatistics();

    // Set interval to fetch data every 30 seconds
    setInterval(fetchProductStatistics, 30000);
}

// Initial fetch
fetchData();
