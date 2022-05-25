let youtubeVideoId = '';
// window.onYouTubeIframeAPIReady;
window.addEventListener('load', function(){
    prepareYoutubeApi();
});

function prepareYoutubeApi(){
    document.getElementById('uploadLink').addEventListener('click', runScript);
    console.log('yt api prepped')
}

function parseLink(/*event*/){
//    event.preventDefault();
    
    youtubeVideoId = "";
    let link = document.getElementById('youtubeLink').value;
    let idIndex;

    if(link.indexOf('v=') == -1){
        idIndex = link.indexOf('be/') + 3;
    }else{
        idIndex = link.indexOf('v=') + 2;
    }

    let j = idIndex;
    while(j < idIndex + 11){
        youtubeVideoId += link[j];
        j++;
    }
    // createNewYoutubePlayer();
}

// Youtube API function 
function runScript(){
    // special string parser added to set videoId for loading player.
    parseLink();
    
    var tag = document.createElement('script');
    tag.src = "https://www.youtube.com/iframe_api";
    var firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
    if(typeof YT != 'undefined'){
        createNewYoutubePlayer(youtubeVideoId);
    }
}

// 3. This function creates an <iframe> (and YouTube player)
//    after the API code downloads.
var player;
function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        height: '312',
        width: '512',
        videoId: `${youtubeVideoId}`,
        playerVars: {
            'playsinline': 1
        },
        events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange
        }
    });
    clearPostBoxButtons();
}

function createNewYoutubePlayer(youtubeVideoId){
    player = new YT.Player('player', {
        height: '312',
        width: '512',
        videoId: `${youtubeVideoId}`,
        playerVars: {
            'playsinline': 1
        },
        events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange
        }
    });
    clearPostBoxButtons();
}

// 4. The API will call this function when the video player is ready.
function onPlayerReady(event) {
    // event.target.playVideo(); // turns off autoplay when the player is generated
}

// 5. The API calls this function when the player's state changes.
//    The function indicates that when playing a video (state=1),
//    the player should play for six seconds and then stop.
var done = false;
function onPlayerStateChange(event) {
    if (event.data == YT.PlayerState.PLAYING && !done) {
        // setTimeout(stopVideo, 1000); // might need to set eventually? 
        done = true;
    }
}
function stopVideo() {
    player.stopVideo();
}

function clearPostBoxButtons(){
    let postBox = document.getElementById('post-editor-content-box');
    while(postBox.firstChild){
        postBox.removeChild(postBox.firstChild);
    }
}