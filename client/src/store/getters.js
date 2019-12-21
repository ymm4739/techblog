const getters = {
  user: state => {
    return {
      id: state.user.id,
      username: state.user.username,
      email: state.user.email,
      isValidEmail: state.user.isValidEmail
    }
  },
  token: state => state.user.token,
  timeout: state => state.user.timeout,
  isNeededLogin: state => state.user.isNeededLogin,
  userID: state => state.user.id
}

export default getters
