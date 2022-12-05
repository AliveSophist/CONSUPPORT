chartList()

function chartList(){
	
	//ajax start
	$.ajax({
	   url: '/admin/loadIncomeManager', //요청경로
	    type: 'post',
	    data:{}, //필요한 데이터
	    success: function(chartMap) {
	      drawChartList(chartMap)
	    },
	    error: function(){
	       alert('실패');
	    }
	});
	//ajax end
}

function drawChartList(chartMap){
	var options = {
		series: [{ // (chartMap.concertNameList)
			name: '총 팔린 가격',
			data: chartMap.totalSoldAmountList
		}, {
			name: '총 팔린 좌석',
			data: chartMap.soldSeatCntList
		}],
		xaxis: {
			categories: chartMap.concertNameList,
		},
		yaxis: [{
			/*title: {			//y축 왼쪽 제목
				text: '학사경고',
				style: {
	            	fontSize: '18px',
          		}
			},*/}, {
			opposite: true,
			/*title: {		//y축 오른쪽 제목
				text: '제적',
				style: {
	            	fontSize: '18px',
          		}
			}*/
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

	var chart = new ApexCharts(document.querySelector("#chart"), options);
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





