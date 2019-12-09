const state = {
  token: '',
  username: '',
  email: '',
  isRememberMe: '',
  user: ''
}
const getters = {
  token: state => {
    return state.token
  },
  user: state => {
    return {
      username: state.username,
      email: state.email,
      isRememberMe: state.isRememberMe
    }
  }
}

const mutations = {
  setToken (state, token) {
    state.token = token
  },
  setUserInfo (state, user) {
    state.email = user.email
    state.username = user.username
    state.token = user.token
    state.isRememberMe = user.isRememberMe
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
  getters,
  mutations,
  actions
}
