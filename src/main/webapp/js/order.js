function updateAmount() {
    document.getElementById("newAmount").value
            = document.getElementById("newPrice").value * document.getElementById("newQuantity").value;
}

function idUpdated() {
    var val = document.getElementById("newProductId").value;
    var list = document.getElementById("AllProducts");
    for (let i = 0; i < list.length; i++) {
        var productDetails = list.options[i].value.split("|");
        if (productDetails[0] === val) {
            console.log(productDetails[0] + ":" + productDetails[1] + ":" + productDetails[2] + ":" + productDetails[3] + ":" + productDetails[4]);
            document.getElementById("newProduct").value = productDetails[1];
            document.getElementById("newUnit").value = productDetails[3];
            document.getElementById("newPrice").value = productDetails[2];
            document.getElementById("newQuantity").max = productDetails[4];
            updateAmount();
        }
    }
}

function productUpdated(){
    var val = document.getElementById("newProduct").value;
    var list = document.getElementById("AllProducts");
    for (let i = 0; i < list.length; i++) {
        var productDetails = list.options[i].value.split("|");
        if (productDetails[1] === val) {
            console.log(productDetails[0] + ":" + productDetails[1] + ":" + productDetails[2] + ":" + productDetails[3] + ":" + productDetails[4]);
            document.getElementById("newProductId").value = productDetails[0];
            document.getElementById("newUnit").value = productDetails[3];
            document.getElementById("newPrice").value = productDetails[2];
            document.getElementById("newQuantity").max = productDetails[4];
            console.log(productDetails[4]);
            updateAmount();
        }
    }
}





