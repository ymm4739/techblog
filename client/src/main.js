import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import navigation from './components/navigation'
import axios from 'axios'
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(axios)
Vue.component('navigation', navigation)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
