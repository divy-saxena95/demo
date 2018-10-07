<template>
  <div class = "comment-page">
    <div class = "comment-list-section">
      <h2 class = "comment-project-name">{{ project.name }} </h2>
      <div class = "comment-block">
         <commentList @openInbox = "openInbox" :sortedComments = "sortedComments"> </commentList>
       </div>
    </div>
    <div class = "comment-inbox">
        <div class = "comment-header">
           <span class = "header-project-name text">{{project.name}}</span>
           <span class = "header-screen-name text">{{name}}</span>
        </div>
        <img class = "comment-screen" :src = " '/static/uploads/' + name">
        <div v-show = "!show" @click = "showForm" class = "add-comment">Add a New Comment</div>
        <div class = inbox >
        <div v-for = "comment in comments" class = "comment-of-screen">
            <img class = "screen-user-image" src = "https://www.elastic.co/assets/bltada7771f270d08f6/enhanced-buzz-1492-1379411828-15.jpg">
                <div class = "text-of-screen">
                  <span class = "screen-user-name">Suresh</span>
                  <span class = "screen-time">21 days ago</span>
                  <span class = "screen-comment-text">{{comment.comment}}</span>
                </div>
        </div>
        </div>
        <div v-show = "show" class = "form-popup">
             <textarea v-model = "commentText" placeholder="" rows="20" name="comment[text]" id="comment_text" cols="40" class="ui-autocomplete-input" autocomplete="off" role="textbox" aria-autocomplete="list" aria-haspopup="true"></textarea>
            <button class="btn" @click = "addComment">Reply</button>
        </div>
    </div>
  </div>
</template>



<script>
import axios from 'axios'
import commentList from './commentList'

export default {
     components:
     {
       commentList
     },

     data: function () {
           return {
                projectId: this.$route.params.id,
                project: null,
                imageId: this.$route.params.imageId,
                show: false,
                image:null,
                name: '',
                commentText: '',
                comment: {},
                comments: null,
                allComments: null,
                index:[],
                sortedComments: []
              }
         },
      methods:
      {
         showForm()
         {
            this.show = true
         },
         openInbox(id)
         {
             let routeData = this.$router.resolve({name: 'commentListing',params: { id: this.projectId,imageId: id }})
                                             window.open(routeData.href,'_self')
         },
         addComment()
         {
           axios
           .post('/mocksight/projects/' + this.projectId + '/images/' + this.imageId + '/comment',this.comment)
            .then(response => { this.show = false
                                this.commentText = ''
                                 axios
                                 .get('/mocksight/projects/'+ this.projectId+ '/images/'+ this.imageId + '/comment')
                                  .then(response => {this.comments = response.data.Comments
                                                                    }).catch(error => {
                                                     console.log(response)
                                                     })

                                    axios
                                     .get('/mocksight/projects/' + this.projectId + '/comment')
                                             .then(response => { this.allComments = response.data.Comments
                                                                 this.sortedComments = []
                                                                 this.index = []
                                                                 this.index.push(this.allComments[0].image.id)
                                                                 this.sortedComments.push(this.allComments[0])
                                                                 for(var i=0 ; i< this.allComments.length ; i++)
                                                                 {
                                                                      if(this.index.indexOf(this.allComments[i].image.id) == -1)
                                                                        {
                                                                            this.sortedComments.push(this.allComments[i])
                                                                            this.index.push(this.allComments[i].image.id)
                                                                        }
                                                                 }
                                                                                      }).catch(error => {
                                                                    console.log(response)
                                                                  })
                                }).catch(error => {
                                  console.log(response)
                                })
        }
      },
     mounted()
     {
           axios
           .get('/mocksight/projects/'+ this.projectId)
           .then(response => {this.project = response.data
                                         }).catch(error => {
                       console.log(response)
                     })

           axios
           .get('/mocksight/projects/' + this.projectId + '/comment')
           .then(response => { this.allComments = response.data.Comments
                               this.sortedComments = []
                               this.index = []
                               this.index.push(this.allComments[0].image.id)
                               this.sortedComments.push(this.allComments[0])
                               for(var i=0 ; i< this.allComments.length ; i++)
                               {
                                    if(this.index.indexOf(this.allComments[i].image.id) == -1)
                                      {
                                          this.sortedComments.push(this.allComments[i])
                                          this.index.push(this.allComments[i].image.id)
                                      }
                               }
                                                    }).catch(error => {
                                  console.log(response)
                                })

            axios
             .get('/mocksight/projects/'+ this.projectId+ '/images/'+ this.imageId)
             .then(response => {this.image = response.data
                                 this.name = this.image.name
                                 }).catch(error => {
              console.log(response)
                          })

             axios
             .get('/mocksight/projects/'+ this.projectId+ '/images/'+ this.imageId + '/comment')
              .then(response => {this.comments = response.data.Comments                                   }).catch(error => {
                          console.log(response)
                                      })

     },
     watch:
     {
        commentText: function()
        {
           this.comment.comment = this.commentText
         },
         imageId: function() {
               location.reload()
           }
     }
}
</script>



<style >
body,html
{
  overflow-y: hidden;
  height:100%;
}
.comment-list-section
{
  position:absolute;
  left:300px;
}
.comment-block
{
  overflow-y: scroll;
  height:85vh;
  width:30vw;
  overflow-x: visible;
  display:inline-block;
  padding-right: 17px;
  margin-top:10px;
}
.comment-project-name
{      font-family: "Open Sans",open-sans,sans-serif;
               -webkit-font-smoothing: antialiased;
       font-weight: 600;
       color: #3D4356;
       font-size: 19px;
       line-height: 19px;
       display: block;
       padding: 29px 31px 2px 29px;
       white-space: nowrap;
       overflow: visible;
       width: 190px;
       text-align: left;
       text-overflow: visible;
       box-sizing: border-box;
}
/* width */
::-webkit-scrollbar {
    width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
    background: #f1f1f1;
}

/* Handle */
::-webkit-scrollbar-thumb {
    background: #888;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
    background: #555;
}
.comment-inbox
{       bottom: 0;
        widht:600px;
        position: absolute;
        right: 20px;
        top: 38px;
        display: inline-block;
        font-family: "Open Sans",open-sans,sans-serif;
            -webkit-font-smoothing: antialiased;
            font-size: 12px;
            color: #5f697a;
        line-height: 17px;
 }
 .comment-screen
 {
        width: 520px;
        height: 220px;
        box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
        border-radius:5px;
 }
 .text
 {
    font-family: "Open Sans",open-sans,sans-serif;
                   -webkit-font-smoothing: antialiased;
           font-weight: 600;
           color: #3D4356;
           font-size: 17px;
           line-height: 19px;
           display: block;
           white-space: nowrap;
           overflow: hidden;
           width: 190px;
           text-align: center;
           text-overflow: visible;
           box-sizing: border-box;
           margin-left:120px;
           padding:0 0px 5px 0px;
 }
 .comment-header {

       margin-bottom: 5px;
    }
 .header-project-name
 {
    opacity:.7;
    font-size: 15px;
 }
 .add-comment
 {
    position: absolute;
        bottom: 29px;
        left: 32px;
        right: 32px;
        margin-bottom: 0;
      border: 1px solid grey;
          border-radius: 3px;
          color: #a0a8b7;
          cursor: pointer;
          font-size: 13px;
          height: 40px;
          line-height: 40px;
          overflow: hidden;
          text-indent: 15px;
          background: #fff;

 }

 .form-popup {
   display: block;
   position: absolute;
   bottom: 10px;
   right: 5px;
   z-index: 9;
 }


 .btn {
   background-color: #4CAF50;
   color: white;
   padding: 10px 20px 10px 20px;
   border: none;
   cursor: pointer;
   margin-bottom:10px;
   opacity: 0.8;
   margin-left:435px;
   border-radius: 2px;
 }

 /* Add some hover effects to buttons */
 .btn:hover {
   opacity: 1;
 }
 textarea {
   display: block;
   width: 495px;
   height: 100px;
   -moz-border-bottom-colors: none;
   -moz-border-left-colors: none;
   -moz-border-right-colors: none;
   -moz-border-top-colors: none;
   background: none repeat scroll 0 0 white;
   border-color: -moz-use-text-color #FFFFFF #FFFFFF -moz-use-text-color;
   border-image: none;
   border-radius: 6px 6px 6px 6px;
   border-style: none solid solid none;
   border-width: medium 1px 1px medium;
   box-shadow: 0 1px 2px rgba(0, 0, 0, 0.12) inset;
   color: #555555;
   font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
   font-size: 1em;
   line-height: 1.4em;
   padding: 5px 8px;
   transition: background-color 0.2s ease 0s;
   resize: none;
 }


 textarea:focus {
     background: none repeat scroll 0 0 #FFFFFF;
     outline-width: 0;
 }

 .comment-of-screen
 {
 font-family: "Open Sans",open-sans,sans-serif;
         -webkit-font-smoothing: antialiased;
     position:relative;
     padding-top: 15px;
     padding-bottom: 15px;
     width:500px;
     height: auto;
     overflow: auto;
     margin-top:10px;
 }
 .screen-user-image
 {
        display: block;
        width: 35px;
        height: 35px;
        border-radius: 35px;
        float:left;
        overflow: hidden;
        margin-top:5px;
 }
.screen-user-name
{
   display:inline-block;
   margin-left: 10px;
   color:black;
   font-size:15px;
   margin-bottom:2px;
}
.screen-time
{
display:inline-block;
margin-left:100px;
margin-bottom:5px;
font-size:11px;
}
.screen-comment-text
{
margin-left:45px;
}
.inbox
{
   height: 200px;
   overflow: auto;
}
</style>
