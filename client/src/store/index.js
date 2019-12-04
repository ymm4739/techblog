import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    token: '',
    username: '',
    password: '',
    email: '',
    isRememberMe: '',
    user: ''
  },
  getters: {
    token: state => {
      return state.token
    },
    user: state => {
      return {
        username: state.username,
        password: state.username,
        email: state.email,
        token: state.token,
        isRememberMe: state.isRememberMe
      }
    }
  },
  mutations: {
    setToken (state, token) {
      state.token = token
    },
    setUserInfo (state, user) {
      state.email = user.email
      state.username = user.username
      state.password = user.password
      state.token = user.token
      state.isRememberMe = user.isRememberMe
    }
  },
  actions: {
    setToken ({ commit }, token) {
      commit('setToken', token)
    },
    setUserInfo ({ commit }, user) {
      commit('setUserInfo', user)
    },
    clearToken ({ commit }) {
      commit('setToken', '')
    }
  }
})
export default store
