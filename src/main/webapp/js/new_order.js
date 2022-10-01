function updateAmount() {
    document.getElementById("newAmount").value
            = document.getElementById("newPrice").value * document.getElementById("newQuantity").value;
}

function quantityUpdated() {
    var unit = document.getElementById("newUnit").value;
    var pcs = document.getElementById("pcs").value;
    if (pcs === unit) {
        var val = document.getElementById("newQuantity").value;
        document.getElementById("newQuantity").value =
            Math.round(val);
    }
    updateAmount();
}

function idUpdated() {
    console.log("idUpdated");
    var val = document.getElementById("newProductId").value;
    var list = document.getElementById("AllProducts");
    for (let i = 0; i < list.length; i++) {
        var productDetails = list.options[i].value.split("|");
        if (productDetails[0] === val) {
            document.getElementById("newProduct").value = productDetails[1];
            document.getElementById("newUnit").value = productDetails[3];
            document.getElementById("newPrice").value = productDetails[2];
            document.getElementById("newQuantity").max = parseFloat(productDetails[4]);
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
            document.getElementById("newProductId").value = productDetails[0];
            document.getElementById("newUnit").value = productDetails[3];
            document.getElementById("newPrice").value = productDetails[2];
            document.getElementById("newQuantity").max = parseFloat(productDetails[4]);
            updateAmount();
        }
    }
}







