// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Grid from "vue-js-grid"
import VuejsDialog from "vuejs-dialog"

import 'vuejs-dialog/dist/vuejs-dialog.min.css';

Vue.use(Grid)
Vue.use(VuejsDialog)
Vue.config.productionTip = true

/* Creating root Vue instance */
new Vue({
     el: '#app',
     router,
     template: '<App/>',
     components:{
     App
     }
})


