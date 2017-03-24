$(document).ready(function(){
	$('.btn-primary').click(function(){
		var button = $(this);
		var product = $(button).parent('.go-to-cart');
		console.log(product);
		console.log(product.children('.product_id').val());
		console.log(product.children('.qty').val());
		if(product.children('.qty').val() > 0){
			$.post( "addtocart.php", 
			{ 
				product_id: product.children('.product_id').val(), 
				quantity: product.children('.qty').val()
			})
			.done(function( data ) {
				data = JSON.parse(data);
				product.html('You bought '+ data.quantity +' of this product.');
			});
		}
	});
});