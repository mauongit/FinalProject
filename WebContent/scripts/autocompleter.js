var cache = {};


$(document).ready(function() {
        $(function() {
                $("#search").autocomplete({     
                source : function(request, response) {
                	var term          = request.term.toLowerCase(),
                    element       = this.element,
                    cache         = this.element.data('autocompleteCache') || {},
                    foundInCache  = false;
                    //validation
                    var validFormat = new RegExp('^[A-Za-z0-9]');
                    //console.log("TErm::: " + term);
                    if(!validFormat.test(term)){
                    	$("#erroMessage").css("display","block");
                    	 $("#erroMessageNoAddress").css("display","none");
                    	return;
                    	}
                    $("#erroMessage").css("display","none");

                $.each(cache, function(key, data){
                	
                  if (term.indexOf(key) === 0 && data.length > 0) {
                	  /*var start = new Date().getTime();*/
                    var resultsArray =new Array();
                    $.each(data, function( index, value ) {
                   		if(value.toLowerCase().startsWith(term)){
                   			resultsArray.push(value);
                   		}
                   	});
                    response(resultsArray.slice(0,10));
                    foundInCache = true;
                    //console.log("Array Length from Cache :"+resultsArray.length);
                    if(resultsArray.length == 0){
                    	//console.log("Array Length from Cache :"+resultsArray.length);
                    	foundInCache = false;
                    	return;
                    }
                    $("#erroMessageNoAddress").css("display","none");
                    /*var end = new Date().getTime();
                    var time = end - start;
                    console.log('From Execution time: ' + time);*/
                    return;
                  }                  
                });
                //console.log("Value === "+foundInCache);
                  if (foundInCache) return;
                $.ajax({
                        url : "SearchController",
                        type : "GET",
                        data : {
                                term : request.term.charAt(0)
                        },
                        dataType : "json",
                        success : function(data) {
                        	cache[term.charAt(0)] = data;
                            element.data('autocompleteCache', cache);
                           /* var start = new Date().getTime();*/
                              var resultsArray =new Array();
                              $.each(data, function( index, value ) {
                             		if(value.toLowerCase().startsWith(term)){
                             			resultsArray.push(value);
                         			}

                             	});
                              response(resultsArray.slice(0,10));
                             
                              foundInCache = true;
                              if(resultsArray.length<= 0){
                            	  foundInCache = false;
                            	  $("#erroMessageNoAddress").css("display","block");
                              }else{
                            	  $("#erroMessageNoAddress").css("display","none");
                              }
                              
                              /*var end = new Date().getTime();
                              var time = end - start;
                              console.log('Initial Cache Execution time: ' + time);*/
                              return;     
                        }
                });
                if(!foundInCache){
                	//console.log(3444);
                	//$("#erroMessageNoAddress").css("display","block");
                	return;
                }
            }
        });
    });
});