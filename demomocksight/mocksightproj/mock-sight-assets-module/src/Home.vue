<template>
  <div>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
  <div v-if = "!isError">
    <Header>
    </Header>
    <button class="w3-button w3-circle w3-teal" id="show-modal" @click="showModal = true">+</button>
    <i class="fa fa-search" style="font-size:24px;margin-left:30px ;margin-right:-27px;"></i>
    <input v-model = "filter" @focus = "reloadProjects" type="text" id="myinput"  placeholder="Search for projects..">
    <ProjectContainer v-bind:projects = "filteredProjects"  @render="reloadProjects" ></ProjectContainer>
  </div>
  <Error v-if = "isError"></Error>
    <modal v-if="showModal" @close="showModal = false">
  </modal>
  </div>
</template>

<script>
import Header from './Header'
import axios from 'axios'
import modal from './modal'
import ProjectContainer from './ProjectContainer'
import Error from './Error'
import projectpage from './projectpage'

export default {
  name: 'App',
components: {
          modal,
          Header,
          ProjectContainer,
          Error,
          projectpage


           },
  data:function() {
  return{
      showModal: false,
      allProjects: [],
      isError: false,
      filter: '',
      filterText:'',
      filteredProjects:[]

    }
    },
    methods:{
  reloadProjects(){
   axios
          .get('/mocksight/projects' )
          .then(response => {this.allProjects = response.data
                             this.filteredProjects = this.allProjects.Project }).catch(error => {
                   this.isError = true;
             })
  }
  },
  watch: {
            filter: function()
                        {

                           this.filterText  = this.filter.toUpperCase()
                           this.filteredProjects = []
                           for(var i = 0 ; i< this.allProjects.Project.length; i++)
                           {  console.log("for loop")
                              if(this.allProjects.Project[i][1].toUpperCase().indexOf(this.filterText) > -1)
                              {
                                  console.log("if")
                                  this.filteredProjects.push(this.allProjects.Project[i])
                              }
                           }
                        }
                    },
    mounted() {
    axios
          .get('/mocksight/projects' )
          .then(response => {this.allProjects = response.data
                             this.filteredProjects = this.allProjects.Project }).catch(error => {
                   this.isError = true;
             })

      }

  }


</script>
<style>
  body,html {
        margin:0px;
        background-color:#ACE5FE;
        height:100%;
        overflow-x:hidden;
     }
     #myinput {
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
   #show-modal{

   height: 1px;
    width: 61px;
    font-size: 36px;
    padding-bottom: 57px;
    padding-top: 1px;
    float: right;
    margin-right: 200px;
    margin-top: -26px;
    position: relative;}

</style>
