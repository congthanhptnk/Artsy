"user strict"

if (localStorage.getItem("isOk") == true) {
    window.location.href = "main_page.html";
}

const endPointUrl ="http://10.114.34.13/Artsy/"
const loginButton = document.querySelector('#buttonLogin');


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
                console.log(data);
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