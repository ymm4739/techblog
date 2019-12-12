import { sendActivatedEmail, login, getInfo, logout, activateEmail } from '@/api/user'
const state = {
  token: '',
  username: '',
  email: '',
  isValidEmail: false,
  timeout: false
}

const mutations = {
  setToken (state, token) {
    state.token = token
  },
  setUserInfo (state, user) {
    state.email = user.email || ''
    state.username = user.username || ''
    state.isValidEmail = !!user.isValidEmail
  },
  setValidEmail (state, isValidEmail) {
    state.isValidEmail = !!isValidEmail
  },
  setTimeout (state, value) {
    state.timeout = value
  }
}
const actions = {
  sendActivatedEmail ({ commit }, email) {
    return new Promise((resolve, reject) => {
      sendActivatedEmail(email).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  activateEmail ({ commit }, data) {
    return new Promise((resolve, reject) => {
      activateEmail(data).then(res => {
        commit('setValidEmail', true)
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  login ({ commit }, data) {
    return new Promise((resolve, reject) => {
      login(data).then(res => {
        console.log('data')
        console.log(res.data)
        commit('setToken', res.data.token)
        commit('setUserInfo', res.data)
        resolve(res.data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  getInfo ({ commit }) {
    return new Promise((resolve) => {
      getInfo().then(res => {
        commit('setUserInfo', res.data)
        resolve()
      })
    })
  },
  logout ({ commit }) {
    return new Promise((resolve) => {
      logout().then(res => {
        commit('setToken', '')
        commit('setUserInfo', '')
        resolve(res)
      })
    })
  },
  clear ({ commit }) {
    return new Promise((resolve) => {
      commit('setToken', '')
      commit('setUserInfo', '')
      resolve()
    })
  },
  relogin ({ commit }) {
    return new Promise(() => {
      commit('setToken', '')
      commit('setTimeout', true)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
