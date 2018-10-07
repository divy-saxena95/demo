<template>
  <div class="screen">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <div class = "screen-header">
      <img @click.prevent = "goToHome" class = "logo" src = "/static/assets/zemoso_logo.png">

      <span v-show="show" @click="show = false">{{ project.name }}</span>

      <input v-model="project.name" v-show="!show" @change = "check"  @focus="show = false" @blur="showHeader" @keyup.enter="showHeader">
      <i class="fa fa-edit" style="font-size:24px"></i>
      <button @click = "openCommentPage">Comments</button>
    </div>

    <input v-validate="'size:10'" accept=".png,.gif,.jpg,.jpeg" name="file" type="file" id="files" ref="files" class="file" multiple @change="handleFilesUpload" />
    <label class="UploadButton">+</label>
    <i class="fa fa-search" style="font-size:24px;margin-left:30px ;margin-right:-27px;"></i>
    <input v-model = "filter" @focus = "loadImages" type="text" id="myInput"  placeholder="Search for screens.." title="Type in a name">
    <screenContainer :images = "filteredImages" :projectId = "id" @render = "reloadImages" @pushimg="pushimg" @popimg="popimg" > </screenContainer>
    <!-- whenever a checkbox is checked,the footer will appear -->
    <div v-if="checked.length>=1">
      <footerpage @remove="remove"></footerpage>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import axios from 'axios'
import screenContainer from './screenContainer'
import footerpage from './footerpage'
import VeeValidate from 'vee-validate'
Vue.use(VeeValidate);
export default {
        components:
        {
           screenContainer,
           footerpage
        },
        data()
           {
              return {
                   show:true,
                   id: this.$route.params.id,
                   project: null,
                   images: null,
                   isError: false,
                      files:'',
                   prevName: '',
                   filter: '',
                   filterText:'',
                   filteredImages:[],
                   a: 0,
                   flag: true,
                   checked:[]

              }
           },
           methods:
           {
             /* it will make an put request to edit project name */
             showHeader()
                {
                this.show = true
               axios.put('/mocksight/projects/'+this.id, this.project)
              .then(response => {})
              .catch(e => {
                this.errors.push(e)})
               },
               /* it will check if new project name is empty than it will set
                  new project name as previos project name
               */
               check()
               {
                  if(this.project.name != '')
                     this.prevName = this.project.name
                  else
                      this.project.name = this.prevName
               },
                openCommentPage()
               {
                  let routeData = this.$router.resolve({name: 'CommentPage',params: { id: this.id}})
                                                         window.open(routeData.href,'_self')
               },
               /* it will make an get request to load all images of an project and render it */
               reloadImages()
               {
                    axios
                    .get('/mocksight/projects/' + this.id + '/images' )
                     .then(response => {this.images = response.data
                                        this.filteredImages = this.images.Images
                                         this.flag = false
                                         axios
                                         .get('/mocksight/projects/' + this.id + '/images' )
                                          .then(response => {this.images = response.data
                                                             this.flag = true
                       }).catch(error => {
                       this.isError = true;
                        })
               })
               },
               /* it will make an get request to load all image of an project */
               loadImages()
               {
                axios.get('/mocksight/projects/' + this.id + '/images' )
                     .then(response => {this.images = response.data
                       }).catch(error => {
                       this.isError = true;
                        })
               },
               /* it will make an post request to submit images */
               submitFiles(){
                let formData = new FormData();
                for( var i = 0; i < this.files.length; i++ ){
                let file = this.files[i];
                formData.append(file.name, file);
                           }
                 axios.post( '/mocksight/projects/' + this.id + '/images',
                 formData,
                 {
                   headers: {
                   'Content-Type': 'multipart/form-data'
            }
           })
        .then(
          response =>
          {
           axios
                 .get('/mocksight/projects/' + this.id + '/images' )
                  .then(response => {this.images = response.data
                   this.filteredImages = this.images.Images
                        }).catch(error => {
                        this.isError = true;
                                    })
}
)
.catch(function(){
          console.log('FAILURE!!');
          });

      },
      handleFilesUpload(){
        this.files = this.$refs.files.files;
        if(this.files.length > 0)
          this.submitFiles();
      },
       /* this method will push the selected images in the checked array*/
      pushimg(image){
      this.checked.push(image)

      },
      /* method for removing the image from the array whenever the box is unchecked */
      popimg(image){
      var index = this.checked.indexOf(image);
                        if (index > -1) {
                        this.checked.splice(index, 1);
      }},

     /* method for deleting the selected images from the screen */
      remove(){
      console.log("remove")
         let options = {
                        projectId: this.id,
                        errors: this.errors,
                        this: this
                           }
         this.$dialog.confirm('Do you want to delete the selected images',options)
          	.then(function (dialog)
          	{
       for(var i=0;i<options.this.checked.length;i++){

 axios.delete('/mocksight/projects/'+ options.projectId + '/images/' + options.this.checked[i].id)
                                        .then( response => {
                                        axios
          .get('/mocksight/projects/' + options.this.id + '/images' )
          .then(response => {options.this.images = response.data
                             options.this.filteredImages = options.this.images.Images }).catch(error => {
                   this.isError = true;
             })


                                          })
                                        .catch(e => {
                                                     option.errors.push(e)})

       }

})

      },
      goToHome()
      {
         let routeData = this.$router.resolve({name: 'Home'})
         window.open(routeData.href,'_self')
      }
      },
          /* it will watch on filter data variable and whenever it will change it will
             render images according to search filter
           */
           watch: {

                    a: function()
                    {
                       this.flag = true
                    },
                    filter: function()
                        {
                           this.filterText  = this.filter.toUpperCase()
                           this.filteredImages = []
                           for(var i = 0 ; i< this.images.Images.length; i++)
                           {  console.log("for loop")
                              if(this.images.Images[i].name.toUpperCase().indexOf(this.filterText) > -1)
                              {
                                  console.log("if")
                                  this.filteredImages.push(this.images.Images[i])
                              }
                           }
                           this.flag = false
                           axios
                             .get('/mocksight/projects/' + this.id + '/images' )
                             .then(response => {this.images = response.data
                                                this.flag = true
                                                            }).catch(error => {
                                                  this.isError = true;
                                                   })
                        }
                    },
      /* whenever this component get loaded an get request is made to fetch an project and images
         of that project
       */
      mounted() {
       axios
      .get('/mocksight/projects/'+ this.id)
      .then(response => {this.project = response.data
                         this.prevName = response.data.name
                          }).catch(error => {
      console.log(response)
      })
      axios
          .get('/mocksight/projects/' + this.id + '/images' )
          .then(response => {this.images = response.data
                             this.filteredImages = this.images.Images }).catch(error => {
                   this.isError = true;
             })
      }
       }
</script>

<style scoped>
span
{   position: absolute;
    margin:10px;
    font-size:30px;
    color: #DEE7E8;
    font-weight:200;
    font-family:"Comic Sans MS", cursive, sans-serif;
}
#myInput {
  background-image: url('/css/searchicon.png');
  background-position: 10px 12px;
  background-repeat: no-repeat;
  width: 20%;
  font-size: 16px;
  padding: 6px 10px 6px 0px;
  border: 0;
  margin-bottom: 20px;
  margin-left:28px;
  border-bottom: 1px solid #ddd;
  color:black;
}
input {
  border: 0;
  outline: 0;
  background: transparent;
  border-bottom: 1px solid grey;
  color: white;
  margin:10px;
  padding:0px;
  font-size:30px;
}
.screen-header {
    position:relative;
    color: #DEE7E8;
    background-color: #2C4947;
    padding: 8px 0px 8px 0px;
    margin-bottom:20px;
    font-family:"Comic Sans MS", cursive, sans-serif;
    overflow-x:hidden;
}
button{
margin-top: 78px;
height: 50px;
width: 70px;
}
div.fileinputs {
	position: fixed;
	top:56px;
  height:48px;
}
input.file {
	position: absolute;
	right:30px;
	text-align: right;
  padding: 52px 25px 8px 25px;
  width: 103px;
  height: 36px;
  opacity:0;
	z-index: 2;
  top: 6px;
  background-color:blue;
}

.logo
{
   width:4%;
   height:auto;
   float:left;
   margin-left: 15px;
   margin-top:0px;
}
.logo:hover {
     cursor: pointer;
  }
  .UploadButton {
  	position: absolute;
      top: 62px;
      border-radius: 50%;

      margin-left: 1135px;
      margin-top: -8px;
      width: 38px;
      height: 39px;
      padding: 8px;
      background: teal;

      color: white;
      text-align: center;
      font: 38px Arial, sans-serif;
  }
</style>
