function changingVisiabilityOfPassword() {
    var elementPassword = document.getElementById("pass");
    var elementEye = document.getElementById("eye");
    if (elementPassword.type === "password") {
       elementPassword.type = "text";
       elementEye.className = "fas fa-eye";
    } else {
       elementPassword.type = "password";
       elementEye.className = "fas fa-eye-slash";
    }
 }

