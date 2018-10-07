<template>
  <div class = "comment" @click = "openInbox">
    <img class = "user-image" src = "https://www.elastic.co/assets/bltada7771f270d08f6/enhanced-buzz-1492-1379411828-15.jpg">
    <div class = "text-detail">
      <span class = "screen-name">{{comment.image.name}}</span>
      <span class = "comment-text">{{comment.comment}}</span>
      <span class = "user-name">Suresh</span>
      <span class = "time">21 days ago</span>
    </div>
  </div>
</template>



<script>
import axios from 'axios'

export default {
     props:
     {
         comment:
         {
            type: Object,
            default: null
         }
     },

     data: function () {
           return {

              }
         },
     methods:
     {
         openInbox()
         {
              this.$emit('openInbox', this.comment.image.id);
         }
     }
}
</script>



<style >
  .comment
  { font-family: "Open Sans",open-sans,sans-serif;
        -webkit-font-smoothing: antialiased;
    position:relative;
    padding-top: 15px;
    padding-bottom: 15px;
    padding:15px 15px 15px 20px;
    width:95%;
    height: auto;
    overflow: auto;
    background-color:#EAEFEF;
    border-bottom: 4px solid grey;
    cursor: pointer
  }
  .text-detail
  {
    float: left;
    margin-left: 20px;
    width:80%;
  }
  .screen-name
  {
         font-size: 13px;
         line-height: 16px;
         text-transform: uppercase;
         color: #8C96A9;
         margin-top:0px;
  }
  .comment-text
  {
        font-weight: 400;
        line-height: 1.54;
        font-size: 13px;
        cursor: pointer;
        color: #333c4;
        margin-top:8px;
  }
  .user-name
  {
    color: #8c96a9;
    font-weight: 400;
    float: left;
    font-size: 11.5px;
    cursor: pointer;
    line-height: 17px;
    margin-top:5px;
  }
  span
  {
    display: block;
  }
 .time
 {
   color: #8c96a9;
   font-weight: 300;
   font-size: 11.5px;
   cursor: pointer;
   line-height: 17px;
   margin-top:5px;
   float: left;
   margin-left:180px;
 }
 .user-image
 {
    display: block;
    width: 35px;
    height: 35px;
    border-radius: 35px;
    float:left;
    overflow: hidden;
    margin-top:25px;
 }
</style>
