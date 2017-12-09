"user strict"


const logOutButton = document.querySelector('#buttonLogOut');

logOutButton.addEventListener('click', (evt) => {
    evt.preventDefault();
    console.log('log out clicked');
    localStorage.removeItem("userID");
    window.location.href = "index.html";

});