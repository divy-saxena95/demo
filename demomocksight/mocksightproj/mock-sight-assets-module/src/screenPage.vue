<template>
  <!-- An full image-->
  <div class="an-image">

    <div id="container" @mousedown="activity($event)">
    </div>
    <img :src = "'http://localhost:8080/mocksight/images/' + name" usemap="#imagemap">

      <map name="imagemap" >
        <area v-for="h in Hotspots" shape="rect" coords="0,0,82,126" href="https://in.pinterest.com/pin/747667975614936693/"  alt=" " >
      </map>


    <div class="screen-footer">
      <i class="fa fa-eye" style="font-size:24px; margin-left: 500px;" href=""></i>
      <i class="fa fa-credit-card" style="font-size:24px;margin-left: 12px;"></i>
      <i class="fa fa-comment" style="font-size:24px;margin-left: 12px;"></i>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import $ from 'jquery'
import JQuery from 'jquery'
import 'jquery-ui-dist/jquery-ui';

export default {
     data: function () {
           return {
            imageId: this.$route.params.imageId,
            projectId: this.$route.params.id,
             Hotspots:[],
             image: null,
            name:'',
             hotspot:{
              x1:0,
               y1:0,
               x2:0,
               y2:0,
               imageId: this.$route.params.imageId

            }

                       }
         },
methods:
{
  activity(event) {
  var id = $(event.target).attr("id");

  if ((id === "container")) {
    var form = document.getElementsByClassName("complete-block")
    for(var i=0;i<form.length;i++) {
      form[i].style.display = "none";
    }
    var _this=this
    var $target = $(event.target),
      $block = $("<div class= 'complete-block' />")
        .addClass("block")
        .css({
          top: event.pageY - $target.offset().top,
          left: event.pageX - $target.offset().left
        })
        .resizable({resize: function(event, ui) {
          var x1=ui.position.left;
          var y1=ui.position.top;
          var x2=ui.position.left + ui.size.width;
          var y2=ui.size.height+ui.position.top;
          console.log(x1+" "+y1+" "+x2+" "+y2)
          _this.hotspot.x1=x1;
          _this.hotspot.y1=y1;
          _this.hotspot.x2=x2;
          _this.hotspot.y2=y2;


          }})
        .draggable({
          containment: $target,
          start: function (e, ui) {
            var parentOffset = $(this).parent().offset();
            var relX = e.pageX - parentOffset.left;
            var relY = e.pageY - parentOffset.top;
            console.log(" x: " + relX + ", y: " + relY);

          },
          stop: function (e, ui) {
            var parentOffset = $(this).parent().offset();
            var relX = e.pageX - parentOffset.left;
            var relY = e.pageY - parentOffset.top;
            console.log(" x: " + relX + ", y: " + relY);

          }
        });
    $target.append($block);

    $block.mouseup(function (event) {
      var id = $(event.target).parent().attr("id");
      console.log(id);
      if (!(id === "container")) {
        var modal =
          '<div class = "form-model">' +
          '<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">' +
          '<h2>Link To:</h2>' +
          '<div class="dropdown">' +
          '<select>'+
          '<option value="volvo">Volvo</option>'+
          '<option value="saab">Saab</option>'+
          '<option value="vw">VW</option>'+
          '<option value="Select a Destination" selected>Select a Destination</option>'+
        '</select>'+
          '<button id="savebutton">SAVE</button>' +
          '</ul>' +
          '</div>' +
          '</div>';
        $(modal).appendTo($block);

        $("#savebutton").click(function(e)
        {
          console.log("save button clicked")
          e.preventDefault();
          _this.savehotspots();

        });
      }
    })

    $block.find(".ui-resizable-" + "se")
      .trigger("mouseover")
      .trigger({
        type: "mousedown",
        which: 1,
        pageX: event.pageX,
        pageY: event.pageY
      }
      )
  }

  },
  savehotspots(){
    axios.post('/mocksight/hotspot/'+this.imageId,this.hotspot)
      .then(response => {
        console.log(response)
    })
  .catch(e => {
      this.errors.push(e)})
  }
},
  watch:{
       tap:function(){

       }
  },

  /* whenever this component get loaded a GET request will sent to fetch the project */
      mounted()
      {
          axios.get('/mocksight/projects/'+ this.projectId+ '/images/'+ this.imageId)
               .then(response => {this.image = response.data
                                  this.name = this.image.name

                                   }).catch(error => {
               console.log(response)
               })


        axios
          .get('/mocksight/hotspot/'+this.imageId)
          .then(response => {

          this.hotspot=response.data
        console.log("reponse",response.data)
          this.Hotspots = this.hotspot.Hotspots
        console.log("hotspot array",this.hotspot.Hotspots)

      })
      .catch(error => {
        console.log(response)
      })



      }
}
</script>


<style>
html,body
{
  background-color: white;
}
p{
  z-index: 8;
}
.screen-footer{
 background-color: #2C4947;

    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    height: 50px;
  z-index:6;
}
#container {
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: transparent;
    top:0px;
    left: 0px;
    z-index:3;

}

.block {
    position: absolute;
    width: 5px;
    height: 5px;
    background-color: rgba(0,173,255,.54);
    opacity: 1;
    z-index: 4;
}
img
{
  position: absolute;
  top:0px;
  left: 0px;
width: 100%;
  height:100%;
  z-index:1;
  opacity:1;
}
  .form-model
  {
    position: absolute;
    z-index: 7;
    background-color: white;
    height:239px;
    width:212px;
    margin-top:120px;
    margin-left: 20px;
    opacity:0.75;

  }
select,option{
    width:197px;
    height: 40px;
  }
  button{
    margin-top: 50px;
    width: 70px;
    height: 32px;
    font-size: 16px;
    margin-left: 40px;
  }
  area{
    background-color: red;
  }


</style>
