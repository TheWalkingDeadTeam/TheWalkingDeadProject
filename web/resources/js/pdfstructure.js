$( "#fields > div").each( function( index, element) {
	switch ($(element).attr( "id")) {
		case 'b' + i :
			console.log("block");
			break;
	}
	console.log( "id:", $(element).attr( "id"));
});