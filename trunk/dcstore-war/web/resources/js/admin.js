function calcPrice(formName, bton, sufix) {    
    var price = document.getElementById(formName.name+':price'+sufix);
    var priceWithTax = document.getElementById(formName.name+':priceWithTax'+sufix);
    var tax = document.getElementById(formName.name+':tax'+sufix);    
    var vtax = tax.options[tax.options.selectedIndex].text;    
    vtax/=100;
    
    
    if (bton) {
        price.value = priceWithTax.value/(1+vtax);
        price.value = new Number(price.value).toFixed(2);
    }
    else {
        priceWithTax.value = price.value*(1+vtax);
        priceWithTax.value = new Number(priceWithTax.value).toFixed(2);
    }        
}