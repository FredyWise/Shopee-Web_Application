var mainElement = document.querySelector('main');
var navElement = document.querySelector('.nav');

window.onload = function () {
    const mainHeight = mainElement.offsetHeight;
    const windowHeight = window.screen.height;

    if (mainHeight > 0.9 * windowHeight) {
        navElement.classList.add('fixed-bottom');
    }
}

window.onscroll = function () {
    const mainHeight = mainElement.offsetHeight;
    const windowHeight = window.screen.height;

    if (window.scrollY > (mainHeight - 0.9 * windowHeight)) {
        navElement.classList.remove('fixed-bottom');
    } else {
        navElement.classList.add('fixed-bottom');
    }
}
