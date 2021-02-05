let page = 1;
$(function () {
    loadComment(page);
    scrollEvent();
});

function scrollEvent() {
    $(window).scroll(function () {
        if ($(window).scrollTop() == $(document).height() - $(window).height()) {
            loadComment(++page);
        }
    })
}

function loadComment(page) {

    $.get('/api/v1/comment' + '?page=' + page, (commentList) => {
        renderComment(commentList);
    })
}

function renderComment(commentList) {
    $('#comment').append('야!');
}