<template>
  <div v-if="projects != null" class = "project-container">
    <div v-for = "p in projects" class = "project">
      <aProject :project = "p" @deleted="reload" ></aProject>
    </div>
  </div>
</template>


<script>
import aProject from './aProject'

export default {
           components: {
                aProject
           },
           props:{
                projects: {
                    type: Object,
                    default: null
                }
           },
          data()
           {
              return {
              }
           },
           methods:{
           reload()
              {   this.$emit('render')
                    }

           }
    }

</script>

<style scoped>

.project-container {
  display: inline-grid;
  grid-template-columns: auto auto auto;
  grid-row-gap: 22px;
  grid-column-gap: 22px;
  margin:20px 0px 20px 18px;
}

.project {
  position:relative;
  background-color: rgba(255, 255, 255, 0.8);
  width:400px;
  height:300px;
  font-index: 20px;
  overflow:auto;
  border-radius:5px;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
   transition: 0.3s;
    }

 .project:hover {
   box-shadow: 0 32px 64px 0 rgba(0,0,0,0.2);
}
</style>


