var SERVER_URL = "http://localhost:8000/api";

function updateLeaderBoard() {
    $.ajax({
        url: SERVER_URL + "/leaders"
    }).then(function(data) {
        $('#leaderboard-body').empty();
        data.forEach(function(row) {
            $('#leaderboard-body').append('<tr><td>' + row.userId + '</td>' +
                '<td>' + row.totalScore + '</td>');
        });
    });
}

function updateStats(userId) {
    $.ajax({
        url: SERVER_URL + "/stats?userId=" + userId
    }).then(function(data) {
        $('#stats-div').show();
        $('#stats-score').empty().append(data.score);
        $('#stats-badges').empty().append(data.badges);
    });
}

$(document).ready(function() {

    updateLeaderBoard();

    $("#refresh-leaderboard").click(function( event ) {
        updateLeaderBoard();
    });

});
