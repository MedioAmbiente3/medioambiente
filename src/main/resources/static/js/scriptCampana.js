var cards = $('#card-slider .slider-item').toArray();

startAnim(cards);

function startAnim(array) {
    if (array.length >= 4) {
        // Ajusta las posiciones iniciales para que todas comiencen desde la misma posición

        // Tarjeta 1
        TweenMax.fromTo(array[0], 0.5, { x: 50, y: 0, opacity: 0.75 }, { x: 50, y: 0, opacity: 0, zIndex: 0, delay: 0.10, ease: Cubic.easeInOut, onComplete: sortArray(array) });

        // Tarjeta 2
        TweenMax.fromTo(array[1], 0.5, { x: 50, y: 0, opacity: 1, zIndex: 1 }, { x: 50, y: 0, opacity: 0.75, zIndex: 0, boxShadow: '-5px 8px 8px 0 rgba(82, 89, 129, 0.05)', ease: Cubic.easeInOut });

        // Tarjeta 3
        TweenMax.to(array[2], 0.5, { bezier: [{ x: 50, y: 0 }, { x: 50, y: 0 }, { x: 50, y: 0 }], boxShadow: '-5px 8px 8px 0 rgba(82, 89, 129, 0.05)', zIndex: 1, opacity: 1, ease: Cubic.easeInOut });
    } else {
        $('#card-slider').append('<p>Sorry, carousel should contain more than 3 slides</p>');
    }
}

/*
function startAnim(array) {
    if (array.length >= 4) {
        // Ajusta las posiciones iniciales y finales para evitar superposición

        // Tarjeta 1
        TweenMax.fromTo(array[0], 0.5, { x: 50, y: 120, opacity: 0.75 }, { x: 50, y: 0, opacity: 0, zIndex: 0, delay: 0.10, ease: Cubic.easeInOut, onComplete: sortArray(array) });

        // Tarjeta 2
        TweenMax.fromTo(array[1], 0.5, { x: 50, y: 120, opacity: 1, zIndex: 1 }, { x: 50, y: 0, opacity: 0.75, zIndex: 0, boxShadow: '-5px 8px 8px 0 rgba(82, 89, 129, 0.05)', ease: Cubic.easeInOut });

        // Tarjeta 3
        TweenMax.to(array[2], 0.5, { bezier: [{ x: 50, y: 120 }, { x: 50, y: 0 }, { x: 50, y: 120 }], boxShadow: '-5px 8px 8px 0 rgba(82, 89, 129, 0.05)', zIndex: 1, opacity: 1, ease: Cubic.easeInOut });
    } else {
        $('#card-slider').append('<p>Sorry, carousel should contain more than 3 slides</p>');
    }
}
*/

function sortArray(array) {
    clearTimeout(delay);
    var delay = setTimeout(function(){
        var firstElem = array.shift();
        array.push(firstElem);
        return startAnim(array); 
    },3000)
}

