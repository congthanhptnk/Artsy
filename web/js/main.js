"user strict"

const endPointUrl ="http://10.114.34.13:8080/Artsy/"
const upLoadImgUrl = endPointUrl + 'UpLoadPic';

const logOutButton = document.querySelector('#buttonLogOut');
const uploadButton = document.querySelector('#buttonUpload');
const userID = localStorage.getItem('userID');



//////////////////Parse Json
const json = (res) => {
    return res.json();
    console.log(res);
}

//////////////////View Post
const loadPicture = () => {

    const loadPictureUrl = endPointUrl + `webresources/posts/${userID}/main`;
    fetch(loadPictureUrl, {
        method: 'GET'
    })
        .then(json)
        .then((data) => {
            // console.log(data);
            document.querySelector('#main-feed').innerHTML='';
            let postPicture = '';
            data.forEach((post, index) => {
                console.log(post);
                console.log(post.picture);
                let pictureUrl=post.picture;
                postPicture +=
                    `
                    <article class="card">
                        <div class="picture">
                            <img src="http://10.114.34.13/storage/${pictureUrl}" alt="${post.title}" data-toggle="modal" data-target="gmat">
                        </div>
                    </article>
                
                `
            })
            document.querySelector('#main-feed').innerHTML=postPicture;

        }).catch((error) => {
        console.log('error: ' + error);
    });

}

loadPicture();

//////////////////Logout
logOutButton.addEventListener('click', (evt) => {
    evt.preventDefault();
    console.log('log out clicked');
    localStorage.removeItem("userID");
    window.location.href = "index.html";

});

//////////////////Upload Post

//Upload picture
let uploadPic = () =>{
    const input = document.querySelector('#inputImg');
    const imgData = new FormData();
    const file = input.files[0];
    imgData.append('upFile', file);

// POST picture
    fetch(upLoadImgUrl, {
        method: 'POST',
        body: imgData
    })
        .then(json)
        .then((data) => {
            console.log(data);

            const caption = document.querySelector('#inputCaption').value;
            const title = document.querySelector('#inputTitle').value;
            const picture = data.picture;
            const postPicUrl = endPointUrl + `webresources/posts/${userID}/newpost?title=${title}&&caption=${caption}&&picture=${picture}`;

            //fetch post
            fetch(postPicUrl,
                {method: 'POST'})
                .then(json)
                .then((data) =>{
                    console.log(data);
                    document.querySelector('#formUpload').reset();
                    loadPicture();
                });

        }).catch((error) => {
        console.log('error: ' + error);
    });
}

//Upload Button
uploadButton.addEventListener('click', (evt) => {
    evt.preventDefault();
    console.log('upload button clicked');
    uploadPic();
});