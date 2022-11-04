function insertItem(){
  
  var formTag = document.querySelector('#formInsertItem');
  
  //ajax start
  $.ajax({
    url: '/admin/insertItem', //요청경로
    type: 'post',
    enctype: 'multipart/form-data',
    
    //이런것도 가능하네
    data: new FormData( formTag ),
    
    /*============== FormData 쓰려면 필요함 ==============*/
    processData:false,
    contentType:false,
    type: 'POST',
      cache: false,
      /*============== FormData 쓰려면 필요함 ==============*/
      
    success: function(result) {
      
      if(result>0)
        alert('상품 추가 완료~');
      else
        alert('추가 실패??????');
        
      formTag.reset();
    },
    error: function() { alert('실패'); }
  });
  //ajax end
}




