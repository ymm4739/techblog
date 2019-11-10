import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import login from '@/components/login'
import registry from '@/components/registry'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/registry',
      name: 'registry',
      component: registry
    },
    {
      path: '/api/movie/top250',
      name: 'top250'
    },
    {
      path: '/api'
    },
    {
      path: '/api/registry'
    }
  ]
})
