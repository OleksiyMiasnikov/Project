 if (document.getElementById("userRole").value === "senior cashier") {
    var elements = document.getElementsByClassName('item');
    console.log(elements);
    for (var i = 0, length = elements.length; i < length; i++) {
        if (elements[i].id === 'checkSpan') {
            elements[i].hidden = false;
        }
    }
    document.getElementById("delete_button").hidden = false;
}