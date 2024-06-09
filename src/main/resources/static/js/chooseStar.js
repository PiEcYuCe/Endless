let chooseStar = function (number) {
    for (let i = 0; i < number; i++) {
        const starElement = document.getElementById('start' + i);
        if (starElement) {
            starElement.classList.remove('fa-regular', 'fa-star');
            starElement.classList.add('fa-regular', 'fa-star');
        }
    }
};