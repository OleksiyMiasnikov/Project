function firstPage() {

    document.getElementById("current_page").value = 1;

    document.getElementById("controller_form").submit();
}

function decPage() {

   var page = document.getElementById("current_page").value;
   if (page > 1) {
        document.getElementById("current_page").value = page - 1;
   }

   document.getElementById("controller_form").submit();
}

function incPage() {
   var page = document.getElementById("current_page").value;
   if (page < document.getElementById("last_page").value) {
        document.getElementById("current_page").value = parseInt(page) + 1;
   }
   document.getElementById("controller_form").submit();
}

function lPage() {
   document.getElementById("current_page").value = document.getElementById("last_page").value;
   document.getElementById("controller_form").submit();
}