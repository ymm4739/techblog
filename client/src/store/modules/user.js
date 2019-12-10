const state = {
  token: '',
  username: '',
  email: '',
  isRememberMe: false,
  user: ''
}

const mutations = {
  setToken (state, token) {
    state.token = token
  },
  setUserInfo (state, user) {
    state.email = user.email || ''
    state.username = user.username || ''
    state.isRememberMe = user.isRememberMe || false
  }
}
const actions = {
  setToken ({ commit }, token) {
    commit('setToken', token)
  },
  setUserInfo ({ commit }, user) {
    commit('setUserInfo', user)
  },
  clearToken ({ commit }) {
    commit('setToken', '')
  },
  clear ({ commit }) {
    commit('setUserInfo', '')
    commit('setToken', '')
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
