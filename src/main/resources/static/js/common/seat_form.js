const container = document.querySelector(".container");
const seats = document.querySelectorAll(".row .seat");
const count = document.getElementById("count");
const total = document.getElementById("total");

// Seat click event
container.addEventListener("click", (e) => {
	if (
		e.target.classList.contains("seat") &&
		!e.target.classList.contains("sold")
	) {
		e.target.classList.toggle("selected");
	}
});



let seatNum = 1;
for( const s of seats ){
	s.dataset.seatNum = seatNum;
	s.addEventListener("click", function(){
		alert(s.dataset.seatNum);
	})
	seatNum++;
}




/*
const seatList = document.getElementById("seatList");

let seatIndex = 0;
for( const s of seats ){
	s.dataset.seatCode = seatList[seatIndex].seatCode;
	if(seatList[seatIndex].seatStatus.indexOf('EMPTY')>-1)
		//s.classList.add("sold");
		//
		//
	
	seatIndex++;
}
*/