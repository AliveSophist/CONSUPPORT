
var stackedCardSlide = new stackedCards({
 	selector: '.stacked-cards-slide',
 	layout: "slide",
 	transformOrigin: "center",
 });

stackedCardSlide.init();

var stackedCardFanOut = new stackedCards({
 	selector: '.stacked-cards-fanOut',
 	layout: "fanOut",
 	transformOrigin: "bottom",
 });

stackedCardFanOut.init();