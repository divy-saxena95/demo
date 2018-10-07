<template>
  <div class="modal-mask">
    <div class="modal-wrapper">
      <div class="modal-container">

        <div class="modal-header">
          <slot name="header">
            PROJECT DETAILS
          </slot>
        </div>

        <div class="modal-body">
          <slot name="body">
            <form>
              <p>Name:
                <input v-model="project.name" placeholder="Enter Project Name">
              </p>
            </form>

          </slot>

        </div>

        <div class="modal-footer">
          <slot name="footer">
            <button class="modal-default-button" @click="processform">
            Add Project
            </button>


          </slot>


          </slot>
        </div>
      </div>
    </div>

  </div>
</template>

<script>

import Vue from 'vue';
import axios from 'axios';


export default {
    name: 'modal',
    data:function() {
      return{
      project: {
            name:'',
            },
       returnProject: null
    }
    },
    methods:{

               processform() {
                   axios.post('/mocksight/projects', this.project)
                  .then(response => { this.returnProject = response.data
                                     let routeData = this.$router.resolve({name: 'projectpage',params: { id: this.returnProject.id }})
                                                      window.open(routeData.href,'_self')  })
                  .catch(e => {
                       this.errors.push(e)})
                        }
},
    props: ['showModal' ]
  };
</script>


