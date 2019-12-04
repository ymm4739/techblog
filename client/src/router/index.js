import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import login from '@/components/login'
import registry from '@/components/registry'
import userInfo from '@/components/userInfo'
import Layout from '@/layout'
Vue.use(Router)

const router = new Router({
  routes: [
    /*
    {
      path: '/',
      name: 'Home',
      component: Home
    }, */
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
    /*
    {
      path: '/userInfo',
      name: 'userInfo',
      component: userInfo
    }, */
    {
      path: '/',
      name: 'index',
      component: Layout,
      children: [{
        path: 'user/info',
        name: 'userInfo',
        component: userInfo
      },
      {
        path: 'home',
        name: 'home',
        component: Home
      }
      ]
    }

  ]
})

router.beforeEach((to, from, next) => {
  if (to.fullPath === '/login') {
    next()
  } else {
    next()
  }
})
export default router
