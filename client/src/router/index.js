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
Vue.use(Router)
// 解决重复点击导航路由报错
const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
const router = new Router({
  routes: [
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
          path: 'user/profile',
          name: 'profile',
          component: Profile
        },
        {
          path: ''
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
          path: '/',
          component: AdminLayout,
          children: [
            {
              path: 'article/edit/:articleID',
              component: ArticleEditView
            },
            {
              path: 'article/list',
              component: ArticleListView
            },
            {
              path: 'article/create',
              component: ArticleEditView
            }
          ]
        },
        {
          path: 'home',
          name: 'home',
          component: Home
        }]
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
