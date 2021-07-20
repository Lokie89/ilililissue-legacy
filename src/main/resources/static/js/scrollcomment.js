let page = 1;
$(function () {
    loadComment(page);
    moreAction();
});

function moreAction() {
    // $(window).scroll(function () {
    //     if ($(window).scrollTop() === $(document).height() - $(window).height()) {
    //         loadComment(++page);
    //     }
    // })

    $('#more-btn').on('click', function () {
        loadComment(++page);
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

    $.get('/api/v1/comments' /*+ '?page=' + page*/, (commentList) => {
        renderComment(commentList);
    })
}

function renderComment(commentList) {
    const front_left_div = '<div class="disagree"><span>';
    const front_right_div = '<div class="agree"><span>';
    const front_center_div = '<div class="abstention"><span>';
    const end_div = '</span></div>';
    commentList.forEach(comment => {
        let appendHtml = ''
        if (comment.position === 'AGREE') {
            appendHtml += front_right_div;
        } else if (comment.position === 'DISAGREE') {
            appendHtml += front_left_div;
        } else {
            appendHtml += front_center_div;
        }
        appendHtml += comment.comment;
        appendHtml += end_div;
        $('.comment').append(appendHtml);
    })
}