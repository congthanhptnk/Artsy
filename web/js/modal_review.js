//////////////////User view
const loadUserNameModal = () => {

    const loadUserName = endPointUrl + `webresources/users/${userID}/profile`;
    fetch(loadUserName, {
        method: 'GET'
    })
        .then(json)
        .then((data) => {
            console.log(data);
        }).catch((error) => {
        console.log('error: ' + error);
    });

}

loadUserNameModal();


//////////////////Open modal
let pictureID;

document.addEventListener('click', function (e) {
    e = e || window.event;
    const target = e.target || e.srcElement;

    if (target.hasAttribute('data-toggle') && target.getAttribute('data-toggle') == 'modal') {
        if (target.hasAttribute('data-target')) {
            e.preventDefault();
            document.body.style.overflow='hidden';
            const m_ID = target.getAttribute('data-target');
            pictureID = target.getAttribute('pictureID');
            console.log(m_ID)
            document.getElementById(m_ID).classList.add('open');

            getPictureDetail(pictureID);
            getComment(pictureID);
            getLike(pictureID);
        }
    }

    // Close modal window with 'data-dismiss' attribute or when the backdrop is clicked
    if ((target.hasAttribute('data-dismiss') && target.getAttribute('data-dismiss') == 'modal') || target.classList.contains('modal')) {
        const modal = document.querySelector('[class="modal open"]');
        modal.classList.remove('open');
        document.body.style.overflow='auto';
    }
}, false);

//////////////////Get Picture Detail
const getPictureDetail = (pictureID) => {

    const loadPictureModalUrl = endPointUrl + `webresources/posts/${pictureID}/post/`;
    fetch(loadPictureModalUrl, {
        method: 'GET'
    })
        .then(json)
        .then((data) => {
            console.log(data);
            let postDetail = '';
            let pictureDetail = '';
            document.querySelector('#reviewpost').innerHTML = postDetail;
            document.querySelector('#postPic').innerHTML = pictureDetail;

            const userID = data.id;
            const postCaption = data.caption;
            const postImgUrl = data.picture;
            const postTitle = data.title;
            const loadUserName = endPointUrl + `webresources/users/${userID}/profile`;

            fetch(loadUserName, {
                method: 'GET'
            })
                .then(json)
                .then((data) => {
                    console.log(data);
                    let username = data.username
                    postDetail =
                        `
                        <h2 class ="title">${postTitle}</h2>
                        <p class="author">by ${username}</p>
                        <p class="caption">${postCaption}</p>
                        `

                    document.querySelector('#reviewpost').innerHTML = postDetail;

                    pictureDetail =
                        `
                        <img src="http://10.114.34.13/storage/${postImgUrl}" alt="${postTitle}" >
                        `

                    document.querySelector('#postPic').innerHTML = pictureDetail;


                }).catch((error) => {
                console.log('error: ' + error);
            });
        }).catch((error) => {
        console.log('error: ' + error);
    });
}


//////////////////Get Comment Detail
const getComment = (pictureID) => {
    const loadCommentDetailURL = endPointUrl + `webresources/comments/${userID}/${pictureID}`;


    fetch(loadCommentDetailURL, {
        method: 'GET'
    })
        .then(json)
        .then((data) => {
                //clear comment list
                document.querySelector('#commentList').innerHTML = '';

                if(data.length === 0) { //No comment
                    document.querySelector('#commentList').innerHTML =
                        `<p>There are no comments yet. Be the first to comment.</p>`;
                } else {
                    let listComment = '';
                    data.reverse();
                    data.forEach((com) => {
                        let userID = com.id;
                        let content = com.comment;
                        const loadUserName = endPointUrl + `webresources/users/${userID}/profile`;

                        const div = document.createElement('div');
                        div.className = 'comment';

                        document.querySelector('#commentList').appendChild(div);
                        fetch(loadUserName,{
                            method:'GET'
                        }).then(json)
                            .then((data) => {
                                console.log(data);
                                            let username = data.username;
                                            div.innerHTML =
                                                `
                                                 <h1><b>${username}: </b> ${content}</h1>
                                        `


                        })
                            .catch((error) => {
                            console.log('error: ' + error);
                        });

                    })
                    console.log(listComment);
                    // document.querySelector('#commentList').innerHTML = listComment;

                }


        }).catch((error) => {
        console.log('error: ' + error);
    });
}

//////////////////Post Comment Button
const buttonComment = document.querySelector("#buttonComment");
buttonComment.addEventListener("click", (evt) => {
    evt.preventDefault();
    postComment(pictureID);
})

//////////////////Post Comment
const postComment  = (pictureID) => {
    let comment = document.querySelector('#inputComment').value;
    const postCommentURL = endPointUrl + `webresources/comments/${userID}/${pictureID}?comment=${comment}`;

    fetch(postCommentURL, {
        method: 'POST'
    })
        .then(json)
        .then((data) => {
                //reload comment list
                document.querySelector('#commentList').innerHTML ='';
                getComment(pictureID);
                document.querySelector('#inputComment').value = '';

        }).catch((error) => {
        console.log('error: ' + error);
    });
}

//////////////////Get Like Detail
const getLike = (pictureID) => {

    const getLikeUrl = endPointUrl + `webresources/likes/${userID}/${pictureID}`;
    fetch(getLikeUrl, {
        method: 'GET'
    })
        .then(json)
        .then((data) => {
            console.log(data);
            document.querySelector('#like-display').innerHTML=data;

        }).catch((error) => {
        console.log('error: ' + error);
    });

}



//////////////////POST  Like

const postLike  = (pictureID) => {
    const postLikeURL = endPointUrl + `webresources/likes/${userID}/${pictureID}`;
    fetch(postLikeURL, {
        method: 'POST',
    }).then(json).then((data) => {
            //reload rating list
            getLike(pictureID);
    }).catch((error) => {
        console.log('error: ' + error);
    });
}

//////////////////POST  Like Button
const buttonLike = document.querySelector("#like-button");
buttonLike.addEventListener("click", (evt) => {
    evt.preventDefault();
    postLike(pictureID);
})