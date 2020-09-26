function validate(form) {
    let userName = form.userName;
    let email = form.email;
    let password = form.password;
    let passwordConfirmation = form.passwordConfirmation;
    const errorBox = document.getElementById("errorBox");

    if (userName.value === "" || password.value === ""
        || email.value === "" || passwordConfirmation.value === "") {
        errorBox.innerHTML = "Wszystkie pola muszą być wypełnione!";
        errorBox.style.display = "block";
        userName.focus();

        if (userName.value === "") {
            document.getElementById("username").style.background = "lightcoral";
        }
        if (email.value === "") {
            document.getElementById("email").style.background = "lightcoral";
        }
        if (password.value === "") {
            document.getElementById("password").style.background = "lightcoral";
        }
        if (passwordConfirmation.value === "") {
            document.getElementById("passwordConfirmation").style.background = "lightcoral";
        }
        return false;
    }

    if(password.value.length < 5 || password.value.length > 16) {
        document.getElementById("password").style.background = "lightcoral";
        errorBox.style.display = "block";
        errorBox.innerHTML = "Hasło musi zawierać co najmniej 5 znaków, maksymalnie 16";
        return false;
    }

    if (password.value !== passwordConfirmation.value) {
        errorBox.innerHTML = "Hasła różnią się od siebie!";
        errorBox.style.display = "block";
        passwordConfirmation.focus();
        if (password.value === "") {
            document.getElementById("password").style.background = "lightcoral";
        }
        if (passwordConfirmation.value === "") {
            document.getElementById("passwordConfirmation").style.background = "lightcoral";
        }
        return false;
    }

    const re = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if(!email.value.match(re)) {
        errorBox.innerHTML = "Email musi wyglądać na przykład tak : xxx@yyy.pl!";
        errorBox.style.display = "block";
        document.getElementById("email").style.background = "lightcoral";
        return false;
    }
    return true;
}

function reColor(f) {
   if(f.style.background === "lightcoral") {
       f.style.background = "white";
   }

}