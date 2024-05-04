const slide = document.querySelectorAll('.slide'),
    dot = document.querySelectorAll('.dot');
let counter = 1;
sliding(counter);

let timer = setInterval(autoSlide, 8000);
function autoSlide() {
    counter += 1;
    sliding(counter);
}
function plusSlides(n) {
    counter += n;
    sliding(counter);
    resetTimer();
}
function currentSlide(n) {
    counter = n;
    sliding(counter);
    resetTimer();
}
function resetTimer() {
    clearInterval(timer);
    timer = setInterval(autoSlide, 8000);
}

function sliding(n) {
    let i;
    for(i = 0;i<slide.length;i++){
        slide[i].style.display = "none";
    }
    for(i = 0;i<dot.length;i++) {
        dot[i].className = dot[i].className.replace(' active', '');
    }
    if(n > slide.length){
        counter = 1;
    }
    if(n < 1){
        counter = slide.length;
    }
    slide[counter - 1].style.display = "block";
    dot[counter - 1].className += " active";
}