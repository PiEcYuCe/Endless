async function fetchRevenue() {
    try {
        const [revenueToday, revenueThisWeek, revenueThisMonth, revenueThisYear] = await Promise.all([
            fetch('/api/revenue-today').then(response => response.json()),
            fetch('/api/revenue-this-week').then(response => response.json()),
            fetch('/api/revenue-this-month').then(response => response.json()),
            fetch('/api/revenue-this-year').then(response => response.json())
        ]);

        document.getElementById('today').innerText = `$${revenueToday.toFixed(2)}`;
        document.getElementById('week').innerText = `$${revenueThisWeek.toFixed(2)}`;
        document.getElementById('month').innerText = `$${revenueThisMonth.toFixed(2)}`;
        document.getElementById('year').innerText = `$${revenueThisYear.toFixed(2)}`;
    } catch (error) {
        console.error('Error fetching revenue data:', error);
    }
}

// Function to fetch revenue data
function fetchData() {
    fetchRevenue();

    // Set interval to fetch data every 30 seconds
    setInterval(fetchRevenue, 30000);
}

// Initial fetch
fetchData();
