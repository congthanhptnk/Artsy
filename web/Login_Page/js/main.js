const showImages = () => {
    const article = document.querySelector('article');

    fetch('images.json').then((response) => {
        return response.json();
}).then((json) => {
        let html = '';
    json.forEach((image) => {
        html +=
        `<div>
          <figure>
            <a href="#" onclick="openModal('img/${image.mediaUrl}')"></a>
            <figcaption>
                <h3>${image.mediaTitle}</h3>
            </figcaption>
          </figure>
        </div>`;
});
    article.innerHTML = article;
});
};

showImages();

var myModal = document.getElementById("myModal");

const openModal = (url) => {
    console.log(url)
    myModal.style.display = "block";
    var modalImg = document.getElementById("img");
    modalImg.src = url;
};

var span = document.getElementsByClassName("closeBtn")[0];
span.onclick = function() {
    myModal.style.display = "none";
}