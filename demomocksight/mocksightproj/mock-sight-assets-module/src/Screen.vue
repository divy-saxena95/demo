<template>
  <div class = "image" >
    <a  draggable="false" @dblclick = "openScreen" class = "block-link" @mouseover="showMenu"  @mouseleave="hide">
    </a>

    <!-- three dots on each screen -->
    <div  class = "link" v-show = "show" @mouseover="showMenu" @mouseleave="hide">
      <i class="material-icons" style="font-index:36px;" @click="appear">more_horiz</i>
    </div>



    <!-- delete icon that will appear byclicking on three dots -->
    <div  class = "link" v-show = "showDelete" @click.prevent = "remove" >
      <i class="material-icons" style="font-index:24px;">delete</i>
    </div>

    <!-- the checkboxes that appear on the image on hovering on the image-->
    <div class="container" name="image" v-show="showcheck" @mouseover="showMenu" @mouseleave="hide"  >
      <input type="checkbox"class="checkbox" id="check1" @click="check"/>
    </div>


    <!-- backgroud image of each screen cell -->
    <div class="hover">
      <div class = "background-image" draggable="false" v-bind:style="{ backgroundImage: 'url('+imageUrl+')' }">
      </div>
    </div>


    <!-- text section of each screen cell -->
    <div  class = "text">
      <span class = "image-name" v-show="imageShow" @click.prevent = "imageShow = false ">{{image.name}}</span>
      <input class = "image-name" type="text" v-model="image.name" v-show="!imageShow"  @blur.prevent="showName" @keyup.enter.prevent="showName">
      <span class = "image-date">{{date}}</span>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import moment from 'moment'
export default {
  name: 'Screen',
  props: {
    index: {
      type: Number
    },
    projectId:
    {
       type: String
    },
    image: {
      type: Object
    },
    selected:{
    type: Array
    }
  },
  data: function () {
    return {
        show: false,
        imageShow : true,
        showDelete: false,
        showcheck: false,
        checked: false,
        errors: null,
        imageUrl : 'http://localhost:8080/mocksight/images/' + this.image.name
    }
  },
  methods:
  {
      /* it will make an PUT api call on hitting enter to input name */
      showName()
      {
         this.imageShow = true
          axios.put('/mocksight/projects/'+ this.projectId + '/images/' + this.image.id , this.image)
                       .then(response => {})
                       .catch(e => {
                         this.errors.push(e)})
      },
      /* onhover on image it will show three dots */
      showMenu()
      {
           this.show = true
           this.showDelete = false
           this.showcheck=true

      },
      /* onmouseleave on image it will hide three dots */
      hide()
      {
            if(this.checked==true)
            this.showcheck=true
            else
            this.showcheck=false
            this.show = false
      },
      /* onclicking on three dots it will show delete icon */
      appear()
      {
           this.show = false
           this.showDelete = true
      },
      /* onclicking on delete icon an confirm dialog will appear and
         onclicking on yes it will make an delete api call for deleting that screen
       */
      remove()
      {
          let options = { imageId: this.image.id,
                        projectId: this.projectId,
                        errors: this.errors,
                        this: this
                           }
          this.$dialog.confirm('Do you want to delete ' + this.image.name + '?',options)
          	.then(function (dialog) {
                     axios.delete('/mocksight/projects/'+ options.projectId + '/images/' + options.imageId)
                                        .then( response => { options.this.$emit('deleted')} )
                                        .catch(e => {
                                                     option.errors.push(e)})
          	});
       },
       /* by clicking on any image it will redirect to a new page showing a full image */
       openScreen()
       {
             let routeData = this.$router.resolve({name: 'screenPage',params: { id: this.projectId, imageId: this.image.id }})
                             window.open(routeData.href,'_self')
       },
       /* this will perform the emitting depending on when the check box is clicked or not */
       check()
       {
      if(this.checked == true)
      {
           this.checked = false
           this.$emit('pop', this.image)

      }
      else
      {
       this.checked = true
      this.$emit('check',this.image)
      }
       }

  },
     /* it will watch for index props and whenever it will change it will make an put request to save
        that index of image
      */
  watch: {
          index: function()
             {
                this.image.index = this.index
                axios.put('/mocksight/projects/'+this.projectId+ '/images/' + this.image.id, this.image)
                              .then(response => { })
                              .catch(e => {
                                this.errors.push(e)})
             },
             image: function() {
                            this.imageUrl = '/static/uploads/' +  this.image.name
                                  }
         },
    /*
       it will return date in format i.e. 11 Aug, 2018
     */
   computed:
   {
        date() {
               return moment(this.image.dateTime).format('MMM Do, YYYY')
           }
   }
}
</script>

<style scoped>
body,html
{
   background-color: #f9f9fb;
}
.image
{
  position: absolute;
  background-color: rgba(255, 255, 255, 0.8);
  width:300px;
  height:300px;
  font-size: 20px;
  overflow:hidden;
  border-radius:5px;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
  transition: 0.3s;
  overflow:auto;
  overflow-x:hidden;
}
.image:hover
{
    box-shadow: 0 32px 64px 0 rgba(0,0,0,0.2);
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
   width:300px;
   height:230px;
   opacity: .8;
   background-size: 100% 100%;
   }
 .block-link:hover ~ .hover {
     background-color:#010101;
     opacity:1;
     transition: 0.3s;
 }
.link:hover + .hover
{
     background-color:#010101;
     transition: 0.3s;
}
.text
{  z-index:5;
   position:relative;
   width:300px;
   height:60px;
   text-align:center;
   font-family:"Open Sans",open-sans,sans-serif;
   color:#434C5E;
   background-color: #F1FAFD;
   border-top: 10px solid #0F1D20;
}
.image-name
{   position:absolute;
    top:10px;
    left:140px;
    font-size: 18px;
}

input {
  border: 0;
  outline: 0;
  background: transparent;
  padding:0px;
  font-size:30px;
}
.image-date
{  position:absolute;
   top:35px;
   left:120px;
   font-size: 15px;
}
.v-grid
{
  margin:20px;
  overflow-x: hidden;
}
.link
{
 position:absolute;
 top:2px;
 right:7px;
 z-index: 9;
 color: #F1FAFD;
}
body,html {
        overflow-x:hidden;
     }
.dg
{
   width:400px;
   height:400px;
}
.container {
 position: absolute;
 top:2px;
 z-index:9;
  width: 100px;
   height: 100px;
    float: left;
     margin-left: 4px;
      }
.checkbox
{
position: absolute;
 top: 0px;
  left: 0px;
  width: 40px;
    height: 25px;
    color:black;
    }


</style>
