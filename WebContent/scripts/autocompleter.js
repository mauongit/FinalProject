var cache = {};

$(document).ready(function() {
        $(function() {
                $("#search").autocomplete({     
                source : function(request, response) {
                	var term          = request.term.toLowerCase(),
                    element       = this.element,
                    cache         = this.element.data('autocompleteCache') || {},
                    foundInCache  = false;

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
                    /*var end = new Date().getTime();
                    var time = end - start;
                    console.log('From Execution time: ' + time);*/
                    return;
                  }                  
                });
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
                              /*var end = new Date().getTime();
                              var time = end - start;
                              console.log('Initial Cache Execution time: ' + time);*/
                              return;     
                        }
                });
            }
        });
    });
});