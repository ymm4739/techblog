import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Login from '@/components/login'
import Registry from '@/components/registry'
import Profile from '@/views/profile'
import Layout from '@/layout'
import ActivateEmail from '@/components/email'
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
      component: Login
    },
    {
      path: '/registry',
      name: 'registry',
      component: Registry
    },
    {
      path: '/user/email/activate',
      name: 'activateEmail',
      component: ActivateEmail
    },
    {
      path: '/',
      name: 'index',
      component: Layout,
      children: [{
        path: 'user/profile',
        name: 'profile',
        component: Profile
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
