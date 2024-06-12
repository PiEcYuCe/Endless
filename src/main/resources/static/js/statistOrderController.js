async function fetchData() {
    try {
        // Fetch orders awaiting confirmation
        let response = await fetch('/api/count-order-wating');
        let processingCount = await response.json();
        document.getElementById('processing').textContent = processingCount;

        // Fetch orders in transit
        response = await fetch('/api/count-order-shipping');
        let shippingCount = await response.json();
        document.getElementById('shipping').textContent = shippingCount;

        // Fetch orders delivered
        response = await fetch('/api/count-order-dlivered');
        let deliveredCount = await response.json();
        document.getElementById('delivered').textContent = deliveredCount;

        // Fetch orders cancelled
        response = await fetch('/api/count-order-cancel');
        let cancelledCount = await response.json();
        document.getElementById('cancelled').textContent = cancelledCount;
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

// Initial fetch
fetchData();

// Set interval to fetch data every 30 seconds
setInterval(fetchData, 30000);
