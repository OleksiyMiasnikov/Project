function amountUpdate(e) {
      const valQuantity = document.getElementById('newQuantity').value;
      const valPrice = document.getElementById('newPrice').value;
      document.getElementById('newAmount').value = valQuantity * valPrice;
}
