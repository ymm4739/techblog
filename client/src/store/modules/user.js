import { sendActivatedEmail, login, getInfo, logout, activateEmail } from '@/api/user'
import { Message } from 'element-ui'
const state = {
  token: '',
  username: '',
  email: '',
  isValidEmail: false
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
  }
}
const actions = {
  sendActivatedEmail ({ commit }, email) {
    return new Promise((resolve, reject) => {
      sendActivatedEmail(email).then(res => {
        Message.success(res.message)
        resolve()
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
    return new Promise((resolve, reject) => {
      logout().then(res => {
        commit('setToken', '')
        commit('setUserInfo', '')
        Message.success(res.message)
        resolve()
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
