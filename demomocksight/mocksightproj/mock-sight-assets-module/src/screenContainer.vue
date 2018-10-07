<template>
 <div class = "image-container">
  <grid
      :center="false"
      :draggable="true"
      :sortable="true"
      :items="images"
      :height="500"
      :width="90"
      :cellWidth="320"
      :cellHeight="320"
    >
      <!-- each Grid item -->
      <template slot="cell" slot-scope="props">
        <div :index = "props.sort">
        <Screen  :image = "props.item" :projectId = "projectId" @deleted = "reload" :index = "props.sort" > </Screen>
        </div>
      </template>
    </grid>
  </div>
</template>

<script>
import Screen from './Screen'
import axios from 'axios'
export default {
  name: 'ScreenContainer',
  components: {
    Screen
  },
  props: {
    index: {
      type: Number
    },
    projectId:
    {
       type: String
    },
    images:
    {
      type: Array
    }
  },
  data() {
    return {
      selected: null,
      isError: false,
      one: 1
    }
  },
  methods:
  {   /* it will emit an render whenever it will catch reload */
      reload()
              {
                this.$emit('render')
                    },

      change: function ()
      {
        console.log('hello i am suresh')
        window.location.reload()
      },
        /* it will emit a pushimg whenever it will catch removemultiple */
                    removemultiple(image)
                    {


                       this.$emit('pushimg',image)
                      },

                      /* it will emit a popimg whenever it will catch pop */
                      pop(image)
                      {
                      this.$emit('popimg',image)

                           }



             }
   }
</script>

<style>
body,html
{
  height:100%;
  overflow-x:hidden;
}
.image-container
{
   margin-left:13px;
}
.v-grid
{
  height: 100%;
}
</style>
