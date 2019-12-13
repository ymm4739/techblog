import { sendActivatedEmail, login, getInfo, logout, activateEmail, changePassword } from '@/api/user'
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
  },
  clearUserInfo (state) {
    state.email = ''
    state.username = ''
    state.isValidEmail = ''
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
      }).catch(() => { })
    })
  },
  login ({ commit }, data) {
    return new Promise((resolve, reject) => {
      login(data).then(res => {
        console.log('data')
        console.log(res.data)
        commit('setToken', res.data.token)
        commit('setUserInfo', res.data)
        commit('setTimeout', false)
        resolve(res.data)
      }).catch((_) => { })
    })
  },
  getInfo ({ commit }) {
    return new Promise((resolve, reject) => {
      getInfo().then(res => {
        commit('setUserInfo', res.data)
        resolve()
      }).catch((_) => { })
    })
  },
  logout ({ commit }) {
    return new Promise((resolve) => {
      logout().then(res => {
        commit('setToken', '')
        commit('clearUserInfo')
        resolve(res)
      })
    })
  },
  clear ({ commit }) {
    return new Promise((resolve) => {
      commit('setToken', '')
      commit('clearUserInfo')
      resolve()
    })
  },
  relogin ({ commit }) {
    return new Promise(() => {
      commit('setToken', '')
      commit('clearUserInfo')
      commit('setTimeout', true)
    })
  },
  changePassword ({ state }, data) {
    const { oldPassword, newPassword } = data
    return new Promise((resolve, reject) => {
      let data = {
        username: state.username,
        oldPassword,
        newPassword
      }
      changePassword(data).then(res => {
        resolve(res)
      }).catch(() => { })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
