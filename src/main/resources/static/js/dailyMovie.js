function getMovieRank() {
     console.log("✅ 문서 로드 완료");

    $('#dateBtn').click(function () {
        console.log("📌 조회 버튼 클릭됨");

        let selectedDate = $('#dateInput').val();
        console.log("📌 선택된 날짜:", selectedDate);

        if (!selectedDate) {
            console.log("🚨 선택된 날짜 없음! API URL 생성 중단");
            alert("날짜를 선택해주세요.");
            return;
        }

        console.log("✅ API URL 생성 전");
        let apiUrl = `/daily_movie?date=${selectedDate}`;
        console.log("✅ API URL:", apiUrl);

        $.ajax({
            url: apiUrl,
            method: "GET",
            success: function (data) {
                console.log("🎉 API 호출 성공:", data);
                $('#movieList').empty();
                let movieListHtml = data.boxOfficeResult.dailyBoxOfficeList.map(function (movie) {
                    return `
                        <div class="movie-card" data-movieid="${movie.movieCd}">
                            <h3>${movie.movieNm}</h3>
                            <p>개봉일: ${movie.openDt}</p>
                            <p>순위: ${movie.rank}</p>
                        </div>
                    `
                }).join('');
                $('#movieList').html(movieListHtml);
            },
            error: function (error) {
                console.error("🚨 API 호출 실패:", error);
            }
        });
    });

    //data- 가 문법이다 ex -> data-movieId=값 을 넣으면
    //$(this).data("movieId"); 이런 식으로 값을 지정할 수 있다.

     $(document).on("click", ".movie-card", function () {
         let movieId = $(this).data("movieid");
         console.log("영화 ID:", movieId);
         window.location.href = "/movieInfo/" + movieId;
     });
}

$(document).ready(function() {
    getMovieRank();
});