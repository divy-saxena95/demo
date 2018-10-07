<template>
  <div class="header">
  <img @click = "goToHome" class = "logo" src = "/static/assets/zemoso_logo.png">
  <span class = "heading">Mock Sight </span>
  <div class="dropdown">
     <h3 @click = "showMenu" v-click-outside = "hide" class="dropbtn">Suresh Choudhary
      <i class="fa fa-caret-down"></i></h3>
      <div v-show = "show" class="dropdown-content">
        <div class = "image">
        </div>
        <h4 class="a">Suresh Choudhary</h4>
        <button href="#" class="a">Sign Out</button>
     </div>
    </div>
  </div>
 </template>

 <script>
 import Vue from 'vue'

 import ClickOutside from 'vue-click-outside'

export default {

      props: {  show:
                   {  type: Boolean,
                       default: false
                   }
             },
      methods:
               {
                showMenu()
                  {
                    if(this.show==false)
                    this.show = true
                    else
                    this.show = false
                  },
                  hide(e)
                  {
                  if(e.target.className != "dropdown-content" && e.target.className != "a" && e.target.className != "image")
                  this.show = false
                  },
                   goToHome()
                           {
                             let routeData = this.$router.resolve({name: 'Home'})
                              window.open(routeData.href,'_self')
                                }

               },
            directives: {
               ClickOutside
            }

}
</script>

<style scoped>

.header {
    position: relative;
    color: #DEE7E8;
    background-color: #2C4947;
    padding: 15px 0px 15px 0px;
    font-family:"Comic Sans MS", cursive, sans-serif;
}

.heading
{    font-weight:200;
     font-size: 30px;
     margin-left:75px;
}
.image
{
   float:left;
   margin-right:20px;
   padding:30px;
   background-color: #B3BAB9;
   width:0px;
}

h4
{
  color:black;
   white-space: nowrap;
   margin-top:5px;
   font-weight:200;
}

#view_profile
{
  white-space: nowrap;
  float:right;
  margin-top:-25px;
  margin-left: 15px;
    }

h3 {
    color: #DEE7E8;
    font-size: 16px;
    border: none;
    font-weight:200;
}

/* The container <div> - needed to position the dropdown content */
.dropdown {
    position: absolute;
    top:20px;
    right:70px;
    }

/* Dropdown Content (Hidden by Default) */
.dropdown-content {
    position:absolute;
    right:-10px;
    padding:30px;
    padding-right:90px;
    background-color: #F9FDFC;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
    border-radius:2px;
}

/* Links inside the dropdown */
.dropdown-content button {
    color: black;
    text-decoration: none;
    display: block;
    float:left;
    margin-top:20px;
    }

/* Change color of dropdown links on hover */
.dropdown-content button:hover {background-color: #ddd;}

/* Change the background color of the dropdown button when the dropdown content is shown */
.dropdown:hover h3 {color: #B8C8C7;}

.logo
{  position:absolute;
   top: 6px;
   width:4%;
   height:auto;
   float:left;
   margin-left: 15px;
}
.logo:hover {
    cursor: pointer;
}
</style>




















