
document.addEventListener('DOMContentLoaded', function() {

	var calendar = new FullCalendar.Calendar(document.getElementById('calendar'), {
		locale: 'ko',
		headerToolbar: {
			left: 'prev,next today',
			center: 'title',
		},
		height: '850px',
		expandRows: true,
		events: function(info, successCallback, failureCallback) {
			//ajax start
			$.ajax({
				type: 'POST'
				, url: '/selectConcertListForCalendar'
				, dataType: 'json'
				, processData: false
				, contentType: false
				, cache: false
				//,data: {}, //필요한 데이터
				, success: function(result) {

					var events = [];

					for (const concert of result.concertList) {

						console.log(concert.concertName);
						console.log(yyyy_mm_ddToString(concert.concertDate));

						events.push({
							// https://fullcalendar.io/docs/event-object
							id: concert.concertCode,
							
							title: concert.concertName,
							start: yyyy_mm_ddToString(concert.concertDate),
							end: yyyy_mm_ddToString(concert.concertDate),
							//color: '#000000',
						})

					}

					console.log(events);

					successCallback(events);
				}
				, error: function() { alert('실패'); }
			});
		}
		, eventClick: function(info) {
			info.jsEvent.preventDefault(); // don't let the browser navigate
			
			//alert(info.event.id);
			
			
			showModal(document.querySelector('#concertDetailModalForReserve'));
			concertDetailModalForReserve(info.event.id); // = concert.concertCode);
		}
	});
	
	calendar.render();
});

function yyyy_mm_ddToString(date_str) {
	var yyyyMMdd = String(date_str);
	var sYear = yyyyMMdd.substring(0, 4);
	var sMonth = yyyyMMdd.substring(5, 7);
	var sDate = yyyyMMdd.substring(8, 10);

	date_str = `${sYear}-${sMonth}-${sDate}`;

	return date_str;
}

//function yyyy_mm_ddToDate(date_str)
//{
//	var yyyyMMdd = String(date_str);
//	var sYear = yyyyMMdd.substring(0,4);
//	var sMonth = yyyyMMdd.substring(5,7);
//	var sDate = yyyyMMdd.substring(8,10);
//
//	//alert("sYear :"+sYear +"   sMonth :"+sMonth + "   sDate :"+sDate);
//	return new Date(Number(sYear), Number(sMonth)-1, Number(sDate));
//}

