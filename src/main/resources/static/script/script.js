const carousel = document.querySelector('.genre-carousel');
let scrollPosition = 0;

document.querySelector('.genre-carousel-container').addEventListener('mouseenter', () => {
    carousel.style.transition = 'transform 0.3s ease-in-out';
});

document.querySelector('.genre-carousel-container').addEventListener('mouseleave', () => {
    carousel.style.transition = '';
});

document.querySelector('.genre-carousel-container').addEventListener('mousemove', (e) => {
    const containerWidth = document.querySelector('.genre-carousel-container').offsetWidth;
    const carouselWidth = carousel.offsetWidth;

    const maxX = carouselWidth - containerWidth;

    const mouseX = e.clientX - document.querySelector('.genre-carousel-container').getBoundingClientRect().left;

    const percentX = mouseX / containerWidth;

    scrollPosition = -percentX * maxX;

    // Добавим проверки для предотвращения скролла за пределы краев
    if (scrollPosition > 0) {
        scrollPosition = 0;
    } else if (scrollPosition < -maxX) {
        scrollPosition = -maxX;
    }

    updateCarousel();
});

function updateCarousel() {
    carousel.style.transform = translateX(${scrollPosition}px);
}
