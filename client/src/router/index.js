import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'

import Profile from '@/views/profile'
import Layout from '@/layout'
import ActivateEmail from '@/components/email'
import ArticleShowView from '@/views/article/show'
import ArticleEditView from '@/views/article/edit'
import ArticleListView from '@/views/article/list'
import ArticleIndexView from '@/views/article'
import AdminLayout from '@/views/admin'
import store from '@/store'
Vue.use(Router)
// 解决重复点击导航路由报错
const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
const router = new Router({
  routes: [
    {
      path: '/login',
      component: () => import('@/views/login')
    },
    {
      path: '/403',
      component: () => import('@/views/403')
    },
    {
      path: '/user/email/activate',
      name: 'activateEmail',
      component: ActivateEmail
    },
    {
      path: '/',
      component: Layout,
      children: [
        {
          path: 'user/profile/:userID',
          name: 'profile',
          component: Profile
        },
        {
          path: 'article',
          component: () => import('@/views/article/home')
        },
        {
          path: 'article/show/:articleID',
          component: ArticleShowView
        },
        {
          path: 'article/draft/:articleID',
          component: ArticleShowView
        },
        {
          path: 'article/index/:authorID',
          component: ArticleIndexView
        },
        {
          path: 'home',
          name: 'home',
          component: Home
        },
        {
          path: '/article',
          component: AdminLayout,
          meta: {
            roles: ['user']
          },
          children: [
            {
              path: 'edit/:articleID',
              component: ArticleEditView
            },
            {
              path: 'list',
              component: ArticleListView
            },
            {
              path: 'create',
              component: ArticleEditView
            },
            {
              path: 'thumbs/list',
              component: () => import('@/views/admin/thumbsList')
            }
          ]
        }
      ]
    },
    {
      path: '/*',
      component: () => import('@/views/404')
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.fullPath === '/') {
    next({
      path: '/home'
    })
  }
  if (to.matched.some(recored => recored.meta.roles)) {
    let requestRoles = []
    to.matched.forEach(record => {
      const { meta } = record
      if (meta.roles) {
        meta.roles.forEach(role => {
          requestRoles.push(role)
        })
      }
    })
    if (!store.getters.userID) {
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    } else if (requestRoles.every(role => {
      let { roles } = store.getters.user
      // 数组有Observer无法取值，需要转化
      roles = JSON.parse(JSON.stringify(roles))
      return roles.includes(role)
    })) {
      next()
    } else {
      next({
        path: '/403'
      })
    }
  } else {
    next()
  }
})
export default router
