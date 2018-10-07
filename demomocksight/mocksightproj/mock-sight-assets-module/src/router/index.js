import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/Home'
import projectpage from '@/projectpage'
import screenPage from '@/screenPage'
import commentListing from '@/commentListing'
import CommentPage from '@/CommentPage'

Vue.use(Router)
export default new Router({
  routes: [
    {
          path: '/',
          name: 'Home',
          component :Home,
          props: true
        },
    {
      path: '/project/:id',
      name: 'projectpage',
      component :projectpage,
      props: true
    },

    {
      path: '/project/:id/images/:imageId',
      name: 'screenPage',
      component: screenPage,
      props: true
    },

   {
          path: '/project/:id/images/:imageId/comments',
          name: 'commentListing',
          component: commentListing,
          props: true
    },

    {
          path: '/project/:id/comments',
          name: 'CommentPage',
          component: CommentPage
        }


  ]
})
