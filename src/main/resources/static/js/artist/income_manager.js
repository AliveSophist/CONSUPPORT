drawChart()

function drawChart(){

	//ajax start
	$.ajax({
	   url: '/artist/loadIncomeManager', //요청경로
	    type: 'post',
	    data:{}, //필요한 데이터
	    success: function(chartMap) {
	      drawPieChart(chartMap)
	      drawBarChart(chartMap)
	    },
	    error: function(){
	       alert('실패');
	    }
	});
	//ajax end
}

//====================================================================================================================================

// 판매된 좌석 비율 차트
function drawPieChart(chartMap) {

	var options = {
		// R석 S석 A석 좌석수
		series: [ 	chartMap.soldSeatCntList[0].SOLD_SEAT_R_CNT
					, chartMap.soldSeatCntList[0].SOLD_SEAT_S_CNT
					, chartMap.soldSeatCntList[0].SOLD_SEAT_A_CNT
				 ],
		chart: {
			width: 700,
			type: 'pie',
		},
		labels: [	'R석'
					, 'S석'
					, 'A석'
				 ],
		responsive: [{
			breakpoint: 480,
			options: {
				chart: {
					width: 200
				},
				legend: {
					position: 'bottom'
				}
			}
		}]
	};

	var chart = new ApexCharts(document.querySelector("#pieChart"), options);
	chart.render();
}

//====================================================================================================================================

// 공연 매출 정보 차트
function drawBarChart(chartMap){
	
	var options = {
		series: [{ // (chartMap.concertNameList)
			name: '총 팔린 가격',
			data: [	chartMap.soldSeatAmountList[0].SOLD_SEAT_R_AMOUNT
					, chartMap.soldSeatAmountList[0].SOLD_SEAT_S_AMOUNT
					, chartMap.soldSeatAmountList[0].SOLD_SEAT_A_AMOUNT

					, chartMap.soldSeatAmountList[0].SOLD_SEAT_R_AMOUNT
					+ chartMap.soldSeatAmountList[0].SOLD_SEAT_S_AMOUNT
					+ chartMap.soldSeatAmountList[0].SOLD_SEAT_A_AMOUNT
					]
		}],
		xaxis: {
			categories: [	'R석'
							, 'S석'
							, 'A석'
							, '총 수익'
						 ],
		},
		yaxis: [{
			}, {
			opposite: true,
		}],

		chart: {
			type: 'bar',
			height: 600,
			foreColor: '#ffffff',

		},
		plotOptions: {
			bar: {
				horizontal: false,
				columnWidth: '50%',
				endingShape: 'rounded'
			},
		},
		dataLabels: {
			enabled: false,

		},
		stroke: {
			show: true,
			width: 2,
			colors: ['transparent']
		},
		fill: {
			opacity: 1,

		},
		tooltip: {
			y: {
				formatter: function(val) {
					return val
				}
			}
		},


	};

	var chart = new ApexCharts(document.querySelector("#barChart"), options);
	chart.render();



	for(tooltipTag of document.querySelectorAll('.apexcharts-tooltip')){
		tooltipTag.classList.remove('apexcharts-theme-light')
		tooltipTag.classList.add('apexcharts-theme-dark')
	}

	// x축 폰트 컬러 화이트로
	$('.apexcharts-xaxis-label').attr("font-size","1rem");

	// y축 폰트 컬러 화이트로
	$('.apexcharts-yaxis-label').attr("font-size","1rem");

	// 안쓰는 툴바 숨김
	$('.apexcharts-toolbar').css("display","none");

}






