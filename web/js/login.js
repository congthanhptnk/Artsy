"user strict"

const endPointUrl ="http://10.114.34.13:8080/Artsy/webresources/"
const loginButton = document.querySelector('#buttonLogin');
const signUpButton = document.querySelector('#buttonRegister');

//Login
loginButton.addEventListener('click', (evt) => {
    evt.preventDefault();
    console.log('signin');
    const userName = document.querySelector('#signin-username').value;
    const password = document.querySelector('#signin-password').value;
    console.log(userName);
    console.log(password);
    const url = endPointUrl + `users/login?username=${userName}&&password=${password}`;

    fetch(url, {
        method: 'POST'
    })
        .then(json)
        .then((data) =>{
            console.log(data);
            if(data.username==='ERROR'){
                alert('Wrong username or password')
            }
            else{
                localStorage.setItem('userID',data.id);
                window.location.href ="main_page.html";
            }

        });
});

//Register
signUpButton.addEventListener('click', (evt) => {
    evt.preventDefault();
    console.log('signup');
    const userName = document.querySelector('#signup-username').value;
    const password = document.querySelector('#signup-password').value;
    console.log(userName);
    console.log(password);

    const url = endPointUrl + `users/registration?username=${userName}&&password=${password}`;


    fetch(url,
       {method: 'POST'})
       .then(json)
       .then((data) =>{
           console.log(data);
           if (data.username==='ERROR'){
               alert('This username has been register')
           }

           else if( data.username.trim()===null){
               alert('Please enter username')
           }
           else{
               alert('Signup successfully')
               localStorage.setItem('userID',data.id);
               window.location.href ="main_page.html";
           }

       });
});

const json = (res) => {
    return res.json();
    console.log(res);
}

