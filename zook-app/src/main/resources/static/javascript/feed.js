// Launch URL
const url = "http://ec2-54-241-44-71.us-west-1.compute.amazonaws.com:9039";
// Dev URL
// const url = "http://localhost:9039";

window.onload = function() {
    console.log("feed pg. has loaded!")

    getAllPosts();

    //document.getElementById("logout").addEventListener('click', logout);

    //document.getElementById("like").onclick(changeLikeColor);
}

// function changeLikeColor() {
//     document.body.style.color = "green";
//     return false;
// }

function logout() {

}

function getAllPosts() {

    console.log("Show ALL user posts");
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {

        if (xhttp.readyState == 4 && xhttp.status == 200) {
            let userPosts = (JSON.parse(xhttp.responseText));


            DOMManipulation(userPosts);
            console.log(userPosts);
        }
    }

    xhttp.open('Post', `${url}/seeAllPost`);


    xhttp.send();

}

function DOMManipulation(posts) {
    for(i=0; i < posts.length; i++){
    //create elements needed for a post

    //bootstrap divs
        let div1 = document.createElement('div');
        div1.setAttribute('class', 'bg-white border mt-2');
        let div2 = document.createElement('div');
        div1.appendChild(div2);
        let div3 = document.createElement('div');
        div3.setAttribute('class', 'd-flex flex-row justify-content-between align-items-center p-2 border-bottom');
        div2.appendChild(div3);
        let div4 = document.createElement('div');
        div4.setAttribute('class', 'd-flex flex-row align-items-center feed-text px-2');
        div3.appendChild(div4);

    //displays profile image in top left corner of posts    
        let img1 = document.createElement('img');
        img1.setAttribute('class', 'rounded-circle');
        img1.setAttribute('width', '45');

        let profilePic = (posts[i].postOwner.userProfilePic);
        img1.setAttribute('src', `${url}/documents/presigned-url/${profilePic}`);
        div4.appendChild(img1);
        let div5 = document.createElement('div');
        div5.setAttribute('class', 'd-flex flex-column flex-wrap ml-2');
        div4.appendChild(div5);

    //displays name of post user    
        let span1 = document.createElement('span');
        span1.setAttribute('class', 'font-weight-bold');
        span1.setAttribute('id', 'userPost')
        
        span1.innerText = (posts[i].postOwner.userFName + " " + posts[i].postOwner.userLName);
        
        div5.appendChild(span1);

    //displays time since post was posted
        let span2 = document.createElement('span');
        span2.setAttribute('class', 'text-black-50 time');
        span2.setAttribute('id', 'timePost');
        span2.innerText = ("Posted on " + posts[i].postTimeStamp);
        div5.appendChild(span2);    

    //another bootstrap div
        let div6 = document.createElement('div');
        div6.setAttribute('class', 'p-2 px-3');
        div1.appendChild(div6);
        
    //Post Description/text
        let span3 = document.createElement('span');
        span3.setAttribute('id', 'postDescr');
        span3.innerText = (posts[i].postDescrip);
        div6.appendChild(span3);

    //if user posts image
    if(posts[i].postImage != null){
        let divA = document.createElement('div');
        divA.setAttribute('class', 'feed-image p-2 px-3');
        div1.appendChild(divA);
        let img2 = document.createElement('img');
        img2.setAttribute('class', 'img-fluid img-responsive');
        img2.setAttribute('id', "postImg");
        let postI = posts[i].postImage;
        img2.setAttribute('src', `${url}/documents/presigned-url/${postI}`)
        divA.appendChild(img2);
    }
    //if user posts video
    if(posts[i].postYoutube != null){
        let divB = document.createElement('div');
        div1.appendChild(divB);
        let vid = document.createElement('VIDEO');
        vid.setAttribute('width', '320');
        vid.setAttribute('height', '240');
        vid.setAttribute('align', 'center');
        vid.setAttribute('controls', 'true');
        divB.appendChild(vid);
        let linkV = document.createElement('src');
        linkV.setAttribute('type', 'video/mp4');
        let postV = posts[i].postYoutube;
        linkV.setAttribute('src', '${postV}');
        vid.appendChild(linkV);

    }
        let div7 = document.createElement('div');    
        div7.setAttribute('class', 'd-flex justify-content-end socials p-2 py-3');
        div1.appendChild(div7);

    //Like icon and #Likes
        let icon = document.createElement('i');
        icon.setAttribute('class', 'fa fa-paw');
        icon.setAttribute('id', 'like');
        icon.innerText = (" " + posts[i].postLikes + " Likes");
        div7.appendChild(icon);
    //to append most recent post to the top
        let newPost = document.querySelector("#newPost");
        newPost.appendChild(div1);
    }

}

//Posts html format (want to recreate this for every post)
{/* <div class="bg-white border mt-2">   //div1
                    <div>   //div2
                        <div class="d-flex flex-row justify-content-between align-items-center p-2 border-bottom">  //div3
                            <div class="d-flex flex-row align-items-center feed-text px-2"> //div4
                                <img class="rounded-circle" src="../media/crazycat.jpg" width="45"></img>   //img1(profile pic)
                                <div class="d-flex flex-column flex-wrap ml-2"> //div5
                                    <span class="font-weight-bold">El Compa Garfield</span>  //span1
                                    <span class="text-black-50 time">40 minutes ago</span>  //span2
                                </div>
                            </div>
                            {/* <div class="feed-icon px-2">    //div6
                                <i class="fa fa-ellipsis-v text-black-50"></i> 
                            </div> */}
                //         </div>
                //     </div>
                //     <div class="p-2 px-3">  //div6
                //         <span>El alcohol no es la respuesta, pero te hace olvidar la pregunta.</span>   //span3
                //         </div>
              //  <div class="feed-image p-2 px-3"><img class="img-fluid img-responsive" src="../media/crazycat.jpg"></div>
                {/* //     <div class="d-flex justify-content-end socials p-2 py-3">   //div7
                //         <i class="fa fa-paw" id = "like"> Like</i>  //icon
                //     </div>
                // </div> */ }