function idUpdated() {
    alert(document.getElementById("newGoodsId").value);
}
function goodsUpdated(){
    var val = document.getElementById("newGoods").value;
    alert(val);

    var list = document.getElementById("AllGoods");
    console.log(list);
    console.log(list.length);

    for (let i = 0; i < list.length; i++) {
        console.log(list.options[i].value);
        var partsArray = list.options[i].value.split("|");
        console.log(partsArray);
    }

}

