<template>
  <div class = "comment-list">
    <div v-for = "comment in sortedComments">
       <comment @openInbox = "openInbox" :comment = "comment"> </comment>
    </div>
  </div>
</template>



<script>
import axios from 'axios'
import comment from './comment'

export default {
     props:
     {
        sortedComments:
        {
           type: Array,
           default: []
        }
     },
     components:
     {
       comment
     },

     data: function () {
           return {
              }
         },
      methods:
          {
              openInbox(id)
              {
                   this.$emit('openInbox', id);
              }
          }
}
</script>



<style>

</style>
