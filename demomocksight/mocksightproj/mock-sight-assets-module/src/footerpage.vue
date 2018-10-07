<template>
  <!-- footer template which will appear whenever a minimum of 1 checkbox is checked-->
  <div class="footerpage">

    <!-- Delete button -->
    <div  class = "deletebutton"  >
      <i class="material-icons" style="font-index:70px;" @click="removeimages" >delete</i>
    </div>



    </div>

  </div>
</template>
 <script>

  import Vue from 'vue'
   export default{
   name:'footerpage',
   methods:
   {
   /*  this method is emitted whenever the delete button is clicked */
   removeimages(){
   this.$emit('remove')
   }
   }


    }
 </script>

<style scoped>
  .footerpage {
  background-color: #2C4947;
  opacity: 0.5;
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    height: 50px;
}
.deletebutton{
color: white;
margin-left: 500px;
margin-top:6px;
}
</style>
