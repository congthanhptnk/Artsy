"user strict"

if (localStorage.getItem("isOk") == true) {
    window.location.href = "main_page.html";
}

<<<<<<< HEAD
const endPointUrl ="http://10.114.34.13/Artsy/"
=======
const endPointUrl ="http://Localhost:63342/Login_Page/index.html/"
>>>>>>> parent of 1155122... Merge
const loginButton = document.querySelector('#buttonLogin');
const signUpButton = document.querySelector('#buttonRegister');

//Login
loginButton.addEventListener('click', (evt) => {
    evt.preventDefault();
    const userName = document.querySelector('#signin-username').value;
    const password = document.querySelector('#signin-password').value;

    const url = endPointUrl + `webresources/users/login?username=${userName}&&password=${password}`;

    fetch(url, {
        method: 'POST'
    })
        .then(json)
        .then((data) => {
            if (data.hasOwnProperty('error')) {
                alert(data.error);
            } else {
                localStorage.setItem("isOk", true);
                window.location.href = "main_page.html";
            }
        }).catch((error) => {
        console.log('error: ' + error);
    });
});

//Register
signUpButton.addEventListener('click', (evt) => {
    evt.preventDefault();
    const userName = document.querySelector('#signup-username').value;
    const password = document.querySelector('#signup-password').value;

    const url = endPointUrl + `webresources/users/register?username=${userName}&&password=${password}`;

    fetch(url, {
        method: 'POST'
    })
        .then(json)
        .then((data) => {
            if (data.hasOwnProperty('error')) {
                alert(data.error);
            } else {
                localStorage.setItem("isOk", true);
                window.location.href = "main_page.html";
            }
        }).catch((error) => {
        console.log('error: ' + error);
    });
});

const json = (res) => {
    return res.json();
}

