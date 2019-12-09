import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Login from '@/components/login'
import Registry from '@/components/registry'
import UserInfo from '@/layout/components/navigation/userInfo'
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
      component: Login
    },
    {
      path: '/registry',
      name: 'registry',
      component: Registry
    },
    {
      path: '/',
      name: 'index',
      component: Layout,
      children: [{
        path: 'user/info',
        name: 'userInfo',
        component: UserInfo
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
