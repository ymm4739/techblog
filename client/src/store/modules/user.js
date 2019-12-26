import { sendActivatedEmail, login, getInfo, logout, activateEmail, changePassword, changeAvatar } from '@/api/user'
const state = {
  id: '',
  token: '',
  username: '',
  email: '',
  avatar: '',
  roles: [],
  isValidEmail: false,
  timeout: false,
  isNeededLogin: false
}

const mutations = {
  setToken (state, token) {
    state.token = token
  },
  setUserInfo (state, user) {
    state.id = user.id || ''
    state.email = user.email || ''
    state.username = user.username || ''
    state.isValidEmail = !!user.isValidEmail
    state.avatar = user.avatar || ''
    const roleList = user.roleList || []
    roleList.forEach(role => {
      const { name } = role
      state.roles.push(name)
    })
  },
  setValidEmail (state, isValidEmail) {
    state.isValidEmail = !!isValidEmail
  },
  setTimeout (state, value) {
    state.timeout = value
  },
  setNeededLogin (state, value) {
    state.isNeededLogin = value
  },
  setAvatar (state, avatar) {
    state.avatar = avatar
  },
  clearUserInfo (state) {
    state.id = ''
    state.email = ''
    state.username = ''
    state.isValidEmail = ''
    state.avatar = ''
    state.roles = []
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
        commit('setToken', res.data.token)
        commit('setUserInfo', res.data)
        commit('setTimeout', false)
        commit('setNeededLogin', false)
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
  relogin ({ commit }, timeout) {
    return new Promise(() => {
      commit('setToken', '')
      commit('clearUserInfo')
      if (timeout) {
        commit('setTimeout', true)
      } else {
        commit('setNeededLogin', true)
      }
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
  },
  changeAvatar ({ commit }, data) {
    return new Promise((resolve) => {
      changeAvatar(data).then(res => {
        commit('setAvatar', res.data)
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
