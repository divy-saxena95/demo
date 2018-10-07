<template>
  <div class = "a-project">
    <a @click = "openProject(project[0])" class = "block-link" @mouseover="showMenu"  @mouseleave="hide"></a>
    <div  class = "link" v-show = "show" @mouseover="showMenu" @mouseleave="hide">
      <i class="material-icons" style="font-index:36px;" @click="appear">more_horiz</i>
    </div>
    <div  class = "link" v-show = "showDelete" @click.prevent = "remove">
      <i class="material-icons" style="font-index:24px;">delete</i>
    </div>
    <div class="hover">
      <div class = "background-image" v-bind:style="{ backgroundImage: 'url('+imageUrl+')' }"  >
        <div class = "link">
        </div>
      </div>
    </div>
    <div class = "text">
      <span class = "project-name">{{ project[1] }}</span>
      <span class = "project-date">{{ project[2] }}</span>
      <span></span>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
name: 'aProject',
props: {
     project:
        {
       type: Array,
          default: []
}
},
data()
{
return {
show: false,
        images: [],
        allImages: null,
        showDelete: false,
        isError: false,
        imageUrl: ''
                 }
},
methods:
{
showMenu()
      {
           this.show = true
           this.showDelete = false
      },
      hide()
      {
            this.show = false
      },
      appear()
      {
           this.show = false
           this.showDelete = true
      },
      remove()
      {
          let options = {
                        projectid:this.project[0],
                        errors: this.errors,
                        this: this
                           }

          this.$dialog.confirm('Do you want to delete ' + this.project[1] + '?',options)
          	.then(function (dialog) {
                     axios.delete('/mocksight/projects/'+ options.projectid)
                                        .then( response => {
                                        options.this.$emit('deleted')} )
                                        .catch(e => {
                                                     option.errors.push(e)})

          	});
       },
             openProject(id)
               {
         let routeData = this.$router.resolve({name: 'projectpage',params: { id: id }})
          window.open(routeData.href,'_self')
}
},
mounted()
{
    axios
    .get('/mocksight/projects/' + this.project[0] + '/images' )
     .then(response => {this.images = response.data.Images
       }).catch(error => {
      this.isError = true;
     })
},
   watch:
   {
     images: function()
     {
               this.imageUrl = 'http://localhost:8080/mocksight/images/' +  this.images[0].name
                     }
   }
}
</script>

<style scoped>

  .show-project
{  display:none;
   text-decoration:none;
   position:absolute;
   top:100px;
   left:150px;
   z-index:4;
}

 .block-link {
    position:absolute;
    top:0px;
    z-index:3;
    width:400px;
    height:300px;

 }

.background-image {
   margin:0px;
   width:400px;
   height:240px;
   opacity: .7;
   background-size: 100% 100%;
}

 .block-link:hover + .hover {
     background-color:#010101;
     opacity:1;
     z-index:5;
 }

 .block-link:hover ~ .show-project
 {
     display: block;
 }


.text
{  position:relative;
   width:400px;
   height:60px;
   text-align:center;
   font-family:"Open Sans",open-sans,sans-serif;
   color:#434C5E;
}
.project-name
{   position:absolute;
    top:2px;
    left:30px;
    padding:10px;
    font-index: 18px;
}
.project-date
{  position:absolute;
   top:35px;
   left:41px;
   font-index: 13px;
}
.link
{
 position:absolute;
 top:2px;
 right:7px;
 z-index: 9;
 color: green;
}

</style>
