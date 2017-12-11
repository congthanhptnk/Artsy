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
    var target = e.target || e.srcElement;


    if (target.hasAttribute('data-toggle') && target.getAttribute('data-toggle') == 'modal') {
        if (target.hasAttribute('data-target')) {
            e.preventDefault();
            var m_ID = target.getAttribute('data-target');
            pictureID = target.getAttribute('pictureID');
            console.log(m_ID)
            document.getElementById(m_ID).classList.add('open');

            getPictureDetail(pictureID);
            getComment(pictureID);
        }
    }

    // Close modal window with 'data-dismiss' attribute or when the backdrop is clicked
    if ((target.hasAttribute('data-dismiss') && target.getAttribute('data-dismiss') == 'modal') || target.classList.contains('modal')) {
        var modal = document.querySelector('[class="modal open"]');
        modal.classList.remove('open');
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
            document.querySelector('#reviewpost').innerHTML = postDetail;

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
                    <div >
                        <img src="http://10.114.34.13/storage/${postImgUrl}" alt="${postTitle}" >
                    </div>
                    
                    <div >
                        <img id="likebtn" src="img/like_icon.png" style="width: 30px;height: 30px;">
                        <h2>${postTitle}</h2>
                        <p><b>Author:</b> ${username}<br>
                        <b>Caption:</b> ${postCaption}</p>
                        <b>Rate:</b> ${postCaption}</p>
                    </div>   
                        
                        `

                    document.querySelector('#reviewpost').innerHTML = postDetail;


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
                    data.forEach((comment) => {
                        let userID = comment.id;
                        let content = comment.comment;
                        const loadUserName = endPointUrl + `webresources/users/${userID}/profile`;

                        fetch(loadUserName,{
                            method:'GET'
                        }).then(json)
                            .then((data) => {
                                console.log(data);
                                            let username = data.username;
                                            listComment +=
                                                `
                                          <div class="comment">
                                              <div class="sideinfo">
                                                 <h4>${username}</h4>
                                              </div>
                                              <div class="showcomment">
                                                  <pre>${content}</pre>
                                              </div>
                                          </div>
                                        `

                                document.querySelector('#commentList').innerHTML = listComment;
                        })
                            .catch((error) => {
                            console.log('error: ' + error);
                        });
                        console.log('abs: '+listComment);






                    })






                    // data.forEach((comment) => {
                    //     let userID = comment.id;
                    //     let content = comment.comment;
                    //
                    //     const loadUserName = endPointUrl + `webresources/users/${userID}/profile`;
                    //     let listComment = ''
                    //     fetch(loadUserName, {
                    //         method: 'GET'
                    //     })
                    //         .then(json)
                    //         .then((data) => {
                    //             console.log(data);
                    //             let username = data.username;
                    //             listComment +=
                    //                 `
                    //           <div class="comment">
                    //               <div class="sideinfo">
                    //                  <h4>${username}</h4>
                    //               </div>
                    //               <div class="showcomment">
                    //                   <pre>${content}</pre>
                    //               </div>
                    //           </div>
                    //         `
                    //         });
                    //     console.log(listComment);
                    //     document.querySelector('#commentList').innerHTML = listComment;
                    //
                    //
                    // }).catch((error) => {
                    //     console.log('error: ' + error);
                    // });

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
                getComment(pictureID);
                document.querySelector('#inputComment').value = '';

        }).catch((error) => {
        console.log('error: ' + error);
    });
}