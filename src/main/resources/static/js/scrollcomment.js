let page = 1;
$(function () {
    loadComment(page);
    scrollEvent();
});

function scrollEvent() {
    $(window).scroll(function () {
        if ($(window).scrollTop() === $(document).height() - $(window).height()) {
            loadComment(++page);
        }
    })
}

function loadComment(page) {

    // let issueComment = {
    //     author : {
    //         id : 4,
    //     },
    //     issue : {
    //         id : 1,
    //     },
    //     comment : '4444',
    //     position : 'ABSTENTION',
    // }
    //
    // let issueMember = {
    //     name : '작성자44',
    // }
    //
    // $.ajax({
    //     url : '/api/v1/member',
    //     type : 'post',
    //     contentType : 'application/json',
    //     data : JSON.stringify(issueMember),
    // });
    //
    // $.ajax({
    //     url : '/api/v1/comment',
    //     type : 'post',
    //     contentType : 'application/json',
    //     data : JSON.stringify(issueComment),
    // });

    $.get('/api/v1/comment' /*+ '?page=' + page*/, (commentList) => {
        renderComment(commentList);
    })
}

function renderComment(commentList) {
    const front_left_div = '<div class="comment left">';
    const front_right_div = '<div class="comment right">';
    const front_center_div = '<div class="comment center">';
    const end_div = '</div>';
    commentList.forEach(comment => {
        let appendHtml = ''
        if (comment.position === 'AGREE') {
            appendHtml += front_left_div;
        } else if (comment.position === 'DISAGREE') {
            appendHtml += front_right_div;
        } else {
            appendHtml += front_center_div;
        }
        appendHtml += comment.comment;
        appendHtml += end_div;
        $('#comment').append(appendHtml);
    })
}