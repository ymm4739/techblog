const getters = {
  user: state => {
    return {
      username: state.user.username,
      email: state.user.email,
      isRememberMe: state.user.isRememberMe
    }
  },
  token: state => state.user.token
}

export default getters
