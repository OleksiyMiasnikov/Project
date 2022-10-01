function updateAmount() {
    document.getElementById("newAmount").value
            = document.getElementById("newPrice").value * document.getElementById("newQuantity").value;
}

function idUpdated() {
    console.log("idUpdated");
    var val = document.getElementById("newProductId").value;
    var list = document.getElementById("AllProducts");
    console.log(list);
    for (let i = 0; i < list.length; i++) {
        var productDetails = list.options[i].value.split("|");
        if (productDetails[0] === val) {
            document.getElementById("newProduct").value = productDetails[1];
            document.getElementById("newUnit").value = productDetails[3];
            document.getElementById("newPrice").value = productDetails[2];
            document.getElementById("newQuantity").max = parseFloat(productDetails[4]);
            console.log(document.getElementById("newQuantity").max);
            updateAmount();
        }
    }
}

function productUpdated(){
    console.log("productUpdated");
    var val = document.getElementById("newProduct").value;
    var list = document.getElementById("AllProducts");
    for (let i = 0; i < list.length; i++) {
        var productDetails = list.options[i].value.split("|");
        if (productDetails[1] === val) {
            document.getElementById("newProductId").value = productDetails[0];
            document.getElementById("newUnit").value = productDetails[3];
            document.getElementById("newPrice").value = productDetails[2];
            document.getElementById("newQuantity").max = parseFloat(productDetails[4]);
            console.log(document.getElementById("newQuantity").max);
            updateAmount();
        }
    }
}







