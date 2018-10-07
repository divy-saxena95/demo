<template>
   <div>

    <Header></Header>

    <!--Div for searching comments-->
    <div class="search-wrapper">
      <input type="text" v-model="searchText" placeholder="Start typing to search comments..."/>
    </div>

    <!--Looping through unique comments to display them-->
    <div class="grid" v-for ="comment in filteredComments">
      <div class="thumbnail">
        <img @click="routeToCommentList(comment)" class="center" :src="'/static/uploads/' + comment.image.name">
      </div>
      <div class="dp">
        <img class="center" :src="comment.userImage">
      </div>
      <div class="userName">
        <p>comment.userName</p>
      </div>
      <div class="content">
        <p>comment.comment</p>
      </div>
      <div class="datePosted">
        <p>{{comment.date}}</p>
        <!--Substring used to hide time of date object-->
      </div>
     </div>
   </div>
</template>


<script>
import axios from 'axios'
import Header from './Header'

export default{
  name: 'CommentPage',
  components: {
    Header
  },
  data() {
    return{
      comments: [],
      uniqueComments: [],
      searchText: '',
      projectId: this.$route.params.id
    }
  },

  methods:{
    routeToCommentList: function(comment){
      let routeData = this.$router.resolve({name: 'commentListing',params: { id: this.projectId,imageId: comment.image.id}})
                                       window.open(routeData.href,'_self')
    }
  },

  computed:{
    filteredComments: function(){
      //Display only those comments where searched text matches comment's content
      return this.uniqueComments.filter((comment) => {
        return comment.comment.match(this.searchText);
      });
    }
  },

  created() {
    axios.get('/mocksight/projects/'+this.projectId+'/comment').then
    (response => {this.comments = response.data.Comments

                  //Loop to remove duplicate comments based on image's id
                  var temp = [];
                  for(var j=0;j<this.comments.length;j++){
                    var element = this.comments[j];

                    if(temp.indexOf(element.image.id)===-1){
                      temp.push(element.image.id);
                      this.uniqueComments.push(element);
                    }
                  }
    }).catch
    (error => { this.isError = true;})
  }
}
</script>

<style>
  .search-wrapper{
    margin-top: 1%;
    margin-left: 2%;
  }

  input[type=text] {
    width: 25%;
    box-sizing: border-box;
    border: 2px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    padding: 12px 20px 12px 40px;
    -webkit-transition: width 0.4s ease-in-out;
    transition: width 0.4s ease-in-out;
    cursor: pointer;
  }

  input[type=text]:focus {
      width: 70%;
  }

  .grid{
    display: flex;
    margin: 4% 0%;
    height: 15vh;
    width: 96%;
    margin-left: 2%;
    border: 1px solid #999;
  }

  .thumbnail{
    float: left;
    width: 10%;
    height: 100%;
    position: relative;
  }

  .thumbnail img:hover{
    cursor: pointer;
  }

  .thumbnail img{
    height: 90%;
    width: 100%;
    border: solid 1px #CCC;
  }

  .dp{
    float: left;
    width: 5%;
    height: 100%;
    position: relative;
  }

  .dp img{
    height: 50%;
    width: 60%;
  }

  .userName{
    float: left;
    width: 10%;
    display: flex;
    align-items: center;
  }

  .content{
    float: left;
    width: 65%;
    display: flex;
    align-items: center;
  }

  .datePosted{
    float: left;
    width: 10%;
    height: 100%;
    display: flex;
    align-items: center;
  }

  .center{
    //To center elements vertically and horizontally
    margin: 0;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
</style>
